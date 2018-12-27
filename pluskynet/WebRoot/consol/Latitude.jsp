<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="cn">
<head>
<!-- jqGrid组件基础样式包-必要 -->
<link rel="stylesheet" href="../jqgrid/css/ui.jqgrid.css" />

<!-- jqGrid主题包-非必要 -->
<!-- 在jqgrid/css/css这个目录下还有其他的主题包，可以尝试更换看效果 -->
<link rel="stylesheet"
	href="../jqgrid/css/css/redmond/jquery-ui-1.8.16.custom.css" />

<!-- jquery插件包-必要 -->
<!-- 这个是所有jquery插件的基础，首先第一个引入 -->
<script type="text/javascript" src="../js/jquery-1.7.1.js"></script>

<!-- jqGrid插件包-必要 -->
<script type="text/javascript" src="../jqgrid/js/jquery.jqGrid.src.js"></script>

<!-- jqGrid插件的多语言包-非必要 -->
<!-- 在jqgrid/js/i18n下还有其他的多语言包，可以尝试更换看效果 -->
<script type="text/javascript" src="../jqgrid/js/i18n/grid.locale-cn.js"></script>
<title>http://blog.mn886.net/jqGrid</title>

<!-- 本页面初始化用到的js包，创建jqGrid的代码就在里面 -->
<script type="text/javascript" src="../js/latitude.js"></script>
</head>
<body>
<button id="m1" class="layui-btn" type="button">审核</button>&nbsp;&nbsp;
<button id="m4" class="layui-btn" type="button" disabled="true">段落增量跑批</button>&nbsp;&nbsp;
<button id="m2" class="layui-btn" type="button" disabled="true">段落跑批</button>&nbsp;&nbsp;
<button id="m5" class="layui-btn" type="button" disabled="true">维度增量跑批</button>&nbsp;&nbsp;
<button id="m3" class="layui-btn" type="button" disabled="true">维度跑批</button>&nbsp;&nbsp;
	<!-- <form action="LoginAction!login.action" method="post">
		<input type="submit" value="段落跑批">
	</form> -->
	<table id="list2"></table>
	<div id="pager2"></div>
	<br>
	<label >分段已跑批数 ：</label><input id="docnum" type="text" value="" readonly/><label >文书总数 ：</label><input id="docnums" type="text" value="" readonly/>
	<br>
	<label >维度已跑批数 ：</label><input id="lanum" type="text" value="" readonly/><label >段落总数 ：</label><input id="lanums" type="text" value="" readonly/>
</body>
</html>