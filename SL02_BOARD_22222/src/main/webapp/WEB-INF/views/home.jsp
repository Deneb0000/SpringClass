<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %> 
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  - ${user}
</h1>

<P>  The time on the server is ${serverTime}. </P>
<h3>
<a href="/board/list">/time</a>	<!-- 요청전송 -->
</h3>


</body>
</html>