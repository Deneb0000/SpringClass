<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title><tiles:getAsString name="title"/></title>
		<link href="notice.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
		<!-- header 영역 -->
		<tiles:insertAttribute name="header"/>
		<!-- // header 영역 -->
		
		<!-- visual 영역 -->
		<tiles:insertAttribute name="visual"/>
		<!-- // visual 영역 -->
		
		<div id="main">
			<div class="top-wrapper clear">
				<!-- content 영역 -->
				<tiles:insertAttribute name="content"/>
				<!-- // content 영역 -->
				<!-- navi 영역 -->
				<tiles:insertAttribute name="aside"/>
				<!-- // navi 영역 -->
			</div>
		</div>
		<!-- footer 영역 -->
		<tiles:insertAttribute name="footer"/>
		<!-- // footer 영역 -->
	</body>
</html>
