package com.qpf.service;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qpf.bean.ConfigProperties;

@Service
public class FileService {
    @Autowired
    private ConfigProperties configProperties;
	public List<String> collectImgs(String root, String host) {
		List<String> list = new ArrayList<String>();
		File[] files = new File(root).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return false;
				} else {
					return true;
				}
			}
		});
		if (files != null) Arrays.asList(files).forEach(file -> list.add(host + "/" + file.getName()));
		return list;
	}
	public List<String> collectDir(String root) {
		List<String> list = new ArrayList<String>();
		File[] files = new File(root).listFiles();
		if (files != null) Arrays.asList(files).forEach(file -> {
			if (file.exists()) {
				if (file.isDirectory()) {
//					list.add(file.getAbsolutePath());
					list.add(StringUtils.replace(file.getAbsolutePath(), "\\", "/"));
					list.addAll(collectDir(file.getAbsolutePath()));
				}
			}
		});
		return list;
	}
	public List<String> collectImgs(String root, String host, Map<String, Object> map) {
		List<String> list = new ArrayList<String>();
		File[] files = new File(root).listFiles();
		if (files != null) Arrays.asList(files).forEach(file -> {
			if (file.exists()) {
				if (file.isDirectory()) {
					collectImgs(file.getAbsolutePath(), host + file.getName() + "/", map);
				} else if (file.isFile()) {
					list.add(host + file.getName());
				}
			}
		});
		map.put(configProperties.getImgPre() + StringUtils.replace(root, "\\", "/").substring(configProperties.getFileRoot().length()), list);
//		map.put(configProperties.getFileHost() + configProperties.getImgPre() + StringUtils.replace(root, "\\", "/").substring(configProperties.getFileRoot().length()), list);
		return list;
	}
	public Map<String, Object> album() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String root = configProperties.getFileRoot();
		String hostimg = configProperties.getFileHost() + configProperties.getImgPre();
		collectImgs(StringUtils.replace(root, "\\", "/"), hostimg, map);
		return map;
	}
}
