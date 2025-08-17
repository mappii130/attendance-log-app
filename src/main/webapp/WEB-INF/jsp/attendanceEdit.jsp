<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>勤怠編集</title>
	</head>
	<body>
		<h2>勤怠編集</h2>
		
		<form action="AttendanceEditServlet" method="post">
		    <input type="hidden" name="id" value="${attendance.id}">
		    <input type="hidden" name="employeeId" value="${attendance.employeeId}">
		    出勤時刻: <input type="datetime-local" name="clockIn" value="${clockInStr}"><br>
		    退勤時刻: <input type="datetime-local" name="clockOut" value="${clockOutStr}"><br>
		    休憩開始: <input type="datetime-local" name="breakStart" value="${breakStartStr}"><br>
		    休憩終了: <input type="datetime-local" name="breakEnd" value="${breakEndStr}"><br>
		    残業時間: <input type="number" name="overtimeHours" value="${attendance.overtimeHours}"><br>
		    <input type="submit" value="更新">
		        <button type="submit" name="action" value="delete" onclick="return confirm('本当に削除しますか？');">削除</button>
		</form>

		
		<a href="AttendanceListServlet">勤怠一覧に戻る</a>
	</body>
</html>
