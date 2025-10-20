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
		
		<!-- ✅ 検索フォーム -->
<form method="get" action="AttendanceListServlet">
    開始日: <input type="date" name="startDate" value="${param.startDate}">
    終了日: <input type="date" name="endDate" value="${param.endDate}">
    <input type="submit" value="検索">
</form>

<br>
		
		<table border="1">
		    <tr>
		        <th>日付</th>
		        <th>出勤時刻</th>
		        <th>退勤時刻</th>
		        <th>休憩時間</th>
		        <th>労働時間</th>
		        <th></th>
		    </tr>
		    <c:forEach var="att" items="${attendanceList}">
		        <tr>
		            <td>${att.dateString}</td>
		            <td>${att.clockInTimeString}</td>
		            <td>${att.clockOutTimeString}</td>
		            <td>${att.breakTimeString}</td>
		            <td>${att.totalWorkTimeString}</td>
		            <td><a href="AttendanceEditServlet?id=${att.id}">編集</a></td>
		        </tr>
		    </c:forEach>
		</table>
		
		<!-- 勤怠登録画面へ遷移するボタン -->
		<form action="AttendanceRegisterServlet" method="get">
		    <input type="submit" value="勤怠登録画面へ">
		</form>
		<!-- 残業管理画面へ遷移するボタン -->
		<form action="OvertimeManageServlet" method="get">
		    <input type="submit" value="残業管理画面へ">
		</form>
	</body>
</html>