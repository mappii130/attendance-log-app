<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>勤怠編集</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/attendanceedit.css">
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
			<h2>勤怠編集</h2>
			
			<form action="AttendanceEditServlet" method="post">
			    <input type="hidden" name="id" value="${attendance.id}">
			    <input type="hidden" name="employeeId" value="${attendance.employeeId}">
			    <table class="edit-table">
			        <tr>
			            <th>出勤時刻</th>
			            <th>退勤時刻</th>
			            <th>休憩開始</th>
			            <th>休憩終了</th>
			            <th>残業時間</th>
			        </tr>
			        <tr>
			            <td><input type="datetime-local" name="clockIn" value="${clockInStr}"></td>
			            <td><input type="datetime-local" name="clockOut" value="${clockOutStr}"></td>
			            <td><input type="datetime-local" name="breakStart" value="${breakStartStr}"></td>
			            <td><input type="datetime-local" name="breakEnd" value="${breakEndStr}"></td>
			            <td><input type="number" name="overtimeHours" value="${attendance.overtimeHours}"></td>
			        </tr>
			    </table>
			        
			    <div class="button-area">
        			<input type="submit" value="更新">
        			<button type="submit" form="deleteForm"
        				onclick="return confirm('本当に削除しますか？')">
					    削除
					</button>
    			</div>
			</form>

			<form id="deleteForm" action="AttendanceDeleteServlet" method="post">
			    <input type="hidden" name="id" value="${attendance.id}">
<!-- 			    <button type="submit">削除</button> -->
			</form>
		</div>
	</body>
</html>
