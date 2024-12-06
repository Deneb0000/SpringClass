<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<title><tiles:getAsString name="title"/></title>
<!-- 
상황에 따라 css 파일이 바뀌어야 한다면? 
tile.xml에 설정해주고, 링크에 해당 태그를 추가 -->
<link href='<tiles:getAsString name="css"/>' type="text/css" rel="stylesheet" />

</head>
<body>
	<!-- header -->
	<tiles:insertAttribute name="header"/>
	<!-- // header -->
	<!--  visual -->
	<tiles:insertAttribute name="visual"/>
	<!-- // visual -->
	<div id="main">
		<div class="top-wrapper clear">
			<!-- content -->
			<tiles:insertAttribute name="content"/>
			<!-- // content -->
			<!-- navi -->
			<tiles:insertAttribute name="aside"/>
			<!-- // navi -->
		</div>
	</div>
	<!-- footer -->
	<tiles:insertAttribute name="footer"/>
	<!-- // footer -->
</body>
</html>
