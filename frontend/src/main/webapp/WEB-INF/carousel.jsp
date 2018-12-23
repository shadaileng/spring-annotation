<%--
  Created by IntelliJ IDEA.
  User: shadaileng
  Date: 18-12-22
  Time: 上午12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Carousel</title>
    <% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>
    <style>
        #carousel_main {
            position: absolute;
            width: 300px;
            height: 400px;
            border: 1px solid red;
            overflow: hidden;
        }
        #carousel_main img {
            position: absolute;
            width: 300px;
            height: 400px;
        }
    </style>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
    <script>
        $(function () {
            var len = document.querySelectorAll("#carousel_main img").length
            var width = 300
            document.querySelectorAll("#carousel_main img").forEach((el, i)=>{el.style.left=(i*width)+"px"})
            setInterval(()=>{
                document.querySelectorAll("#carousel_main img").forEach((el, i)=>{
                    $(el).animate({left: (parseInt($(el).css("left")) - width)+"px"}, ()=> {
                        if(parseInt($(el).css("left")) < 0) {
                            $(el).css("left", (len - 1) * width + "px")
                        }
                    })

                })
            }, 1000)
        })
    </script>
</head>
<body>
    <h1>Carousel</h1>
    <div id="carousel_main">
        <c:forEach items="${list}" var="img">
            <img src="${ APP_PATH }/${img}" alt="img">
        </c:forEach>
    </div>
</body>
</html>
