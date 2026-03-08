<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>勤怠登録</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/attendanceregister.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
</head>
<body>
	<div  class="header__float">
		<h2>${employee.name}</h2>
		<nav>
			<ul>
				<li>
				    <!-- 勤怠登録画面へ遷移するボタン -->
				    <form action="AttendanceRegisterServlet" method="get">
				        <input type="submit" value="勤怠登録">
				    </form>
			    </li>
			    <li>
				    <button type="button" onclick="location.href='AttendanceListServlet'">勤怠一覧</button>	
				</li>
			    <li>
				    <!-- 残業管理画面へ遷移するボタン -->
				    <form action="OvertimeManageServlet" method="get">
				        <input type="submit" value="残業管理">
				    </form>
				</li>
				<li>
				    <!-- ログアウトしてログイン画面へ遷移するボタン -->
					<form action="${pageContext.request.contextPath}/LogoutServlet" method="get" style="display:inline;">
			        	<button type="submit">ログアウト</button>
			    	</form>
		    	</li>
			</ul>
		</nav>
	</div>
	
	<div class="container">
	    <h2>勤怠登録</h2>	
	    <div class="register_box">
		    <!-- 現在日時の表示 -->
		    <div class=datetime>
			    <span class="date">${currentDate}</span>
			    <span class="time">${currentTime}</span>
		    </div>
			
			<form action="AttendanceRegisterServlet" method="post">
			    <input type="submit" name="action" class="start_button" value="出勤">
			    <input type="submit" name="action" class="stop_button" value="退勤">
		        <input type="submit" name="action" class="start_button" value="休憩開始">
		        <input type="submit" name="action" class="stop_button" value="休憩終了">
			</form>
	    </div>
	</div>
</body>
</html>