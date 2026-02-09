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
	<div class="container">
		<div class="title">
	    	<h2>勤怠管理</h2>
		</div>
	
		<!-- エラーメッセージ表示 -->
		<c:if test="${not empty error}">
		    <p style="color:red;">${error}</p>
		</c:if>
		
		<!-- ログインフォーム -->
		<form action="LoginServlet" method="post">
		    <!-- <label for="email">メールアドレス：</label> -->
		    <div class="field">
		    	<input type="email" id="email" name="email" placeholder="Email" required>
		    </div>
		
		    <!-- <label for="password">パスワード：</label> -->
		    <div class="field">
		    	<input type="password" id="password" name="password" placeholder="Password" required>
		    </div>
		
			<div class="field">
		    	<input class="button" type="submit" value="LOGIN">
			</div>
		</form>
    </div>
</body>
</html>
