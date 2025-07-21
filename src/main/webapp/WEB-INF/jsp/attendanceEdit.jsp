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
		    出勤時刻: <input type="datetime-local" name="clockIn" value="${attendance.clockIn.format(DateTimeFormatter.ofPattern('yyyy-MM-dd\'T\'HH:mm'))}"><br>
		    退勤時刻: <input type="datetime-local" name="clockOut" value="${attendance.clockOut.format(DateTimeFormatter.ofPattern('yyyy-MM-dd\'T\'HH:mm'))}"><br>
		    休憩開始: <input type="datetime-local" name="breakStart" value="${attendance.breakStart.format(DateTimeFormatter.ofPattern('yyyy-MM-dd\'T\'HH:mm'))}"><br>
		    休憩終了: <input type="datetime-local" name="breakEnd" value="${attendance.breakEnd.format(DateTimeFormatter.ofPattern('yyyy-MM-dd\'T\'HH:mm'))}"><br>
		    残業時間: <input type="number" name="overtimeHours" value="${attendance.overtimeHours}"><br>
		    <input type="submit" value="更新">
		</form>

		
		<a href="attendance-list">勤怠一覧に戻る</a>
	</body>
</html>
