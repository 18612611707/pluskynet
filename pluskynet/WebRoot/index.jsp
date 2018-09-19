<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="./js/jquery-1.8.2.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  <!-- from 提交  不需要返回值 -->
   <form action="LoginAction!login.action" id="login_form" method="post">
						<label >账 &nbsp;&nbsp;号：</label><input  name="username" type="text">
						<label >密 &nbsp;&nbsp;码：</label><input  name="password" type="text">
					<input type ="submit" value="提交">
				</form>
  </body>
   <!-- ajax 提交 获取返回值 -->
   <script type="text/javascript"> 
   //页面加载完成时执行ajax
   $(function(){
   $.ajax({
          url : 'DocRuleAction!getDocSectionList.action',
          data:{'sectionName':'2',
          		'username':'zhangsan'
          		},
          type : 'POST',
          dataType : 'json',
                success : function(result) { 
                      alert(result);
                },
                error : function() {
                 alert('error');
               }
                });
  })
   </script> 
</html>
