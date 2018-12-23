package com.qpf.controller;

import com.qpf.bean.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qpf.service.HelloService;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

@Controller
public class HelloController {
    private static Queue<DeferredResult<Object>> queue = new ConcurrentLinkedQueue<DeferredResult<Object>>();

    @Autowired
    private ConfigProperties configProperties;
    @Autowired
	private HelloService helloService;
	@ResponseBody
	@RequestMapping("/tomcat")
	public String hello() {
		return helloService.hello("tomcat");
	}
	@GetMapping("/success")
	public String success() {
	    return "success";
    }
    @GetMapping("/async01")
    @ResponseBody
    public Callable<String> async01() {
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                return "Async_01";
            }
        };
        return callable;
	}
    @GetMapping("/order")
    @ResponseBody
    public DeferredResult<Object> order() {
        DeferredResult<Object> deferredResult = new DeferredResult<>((long) 3000, "超时");
        queue.add(deferredResult);
        return deferredResult;
    }
    @GetMapping("/create")
    @ResponseBody
    public String crete() {
        DeferredResult<Object> result = queue.poll();
        int i = 0;
        while(i < 10 && result == null) {
            result = queue.poll();
            i++;
            System.out.println("获取结果: " + i);
        }
        String order = "";
        if (result != null) {
            order = "订单: " + UUID.randomUUID().toString();
            result.setResult(order);
        } else {
            order = "创建订单超时";
        }
        return order;
    }

    @ResponseBody
    @RequestMapping(path = "/error")
    public Map<String, Object> handle(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", request.getAttribute("javax.servlet.error.status_code"));
        map.put("reason", request.getAttribute("javax.servlet.error.message"));
        return map;
    }

    @ResponseBody
    @RequestMapping(path = "/show")
    public String show() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "你好");
        map.put("reason", "世界");
        return "中文";
    }

    @Autowired
    private CommonsMultipartResolver multipartResolver;
    @PostMapping("/upload")
    public String upload(HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        // 获取根路径资源
        String imgs = request.getServletContext().getRealPath("imgs");
        System.out.println("Name: " + name);
        System.out.println("imgs: " + imgs);
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
            while (fileNames.hasNext()) {
                String fileName = fileNames.next();
                System.out.println("FileName: " + fileName);
                MultipartFile file = multipartHttpServletRequest.getFile(fileName);
                if (file != null && !"".equals(file.getOriginalFilename()) ) {
                    file.transferTo(new File(imgs + File.separator + file.getOriginalFilename()));
                } else {
                    System.out.println("上传失败...");
                }
            }
        }
	    return "success";
    }
    @GetMapping("/carousel")
    public String carousel(Map<String, Object> map, HttpServletRequest request) {
        String root = configProperties.getFileRoot();
        String[] imgs = new File(root + File.separator).list();
        String rootPath = "img/";
        List<String> list = new ArrayList<>();
        for (String img : imgs) {
            list.add(rootPath + img);
        }
        map.put("list", list);
        return "carousel";
    }

}
