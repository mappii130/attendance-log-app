<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤怠登録</title>
</head>
<body>
	<h2>勤怠一覧</h2>
	
	<table border="1">
	    <tr>
	        <th>日付</th>
	        <th>出勤時刻</th>
	        <th>退勤時刻</th>
	        <th>労働時間</th>
	    </tr>
	    <c:forEach var="att" items="${attendanceList}">
	        <tr>
	            <td>${att.dateString}</td>
	            <td>${att.clockInTimeString}</td>
	            <td>${att.clockOutTimeString}</td>
	            <td>${att.totalWorkTimeString}</td>
	        </tr>
	    </c:forEach>
	</table>
	
	<!-- 勤怠登録画面へ遷移するボタン -->
	<form action="AttendanceRegisterServlet" method="get">
	    <input type="submit" value="勤怠登録画面へ">
</body>
</html>

</form>
