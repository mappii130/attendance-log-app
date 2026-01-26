<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
	<div class="title">
    	<h2>勤怠管理</h2>

	    <!-- エラーメッセージ表示 -->
	    <c:if test="${not empty error}">
	        <p style="color:red;">${error}</p>
	    </c:if>
	
	    <!-- ログインフォーム -->
	    <form action="LoginServlet" method="post">
	        <label for="email">メールアドレス：</label><br>
	        <input type="email" id="email" name="email" required><br><br>
	
	        <label for="password">パスワード：</label><br>
	        <input type="password" id="password" name="password" required><br><br>
	
	        <input type="submit" value="ログイン">
	    </form>
	</div>
</body>
</html>
