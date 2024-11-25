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
<title>index</title>
<link href="join.css" type="text/css" rel="stylesheet" />
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
			<tiles:insertAttribute name="aside"/>
			<!-- // content -->
			<!-- navi -->
			<!-- // navi -->
		</div>
	</div>
	<!-- footer -->
	<tiles:insertAttribute name="footer"/>
	<!-- // footer -->
</body>
</html>
