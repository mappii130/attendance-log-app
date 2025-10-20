<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤怠登録</title>
</head>
<body>

    <!-- 現在日時の表示 -->
    <p>日付：${currentDate}</p>
    <p>時刻：${currentTime}</p>
	
	<form action="AttendanceRegisterServlet" method="post">
	    <input type="submit" name="action" value="出勤">
	    <input type="submit" name="action" value="退勤">
        <input type="submit" name="action" value="休憩開始">
        <input type="submit" name="action" value="休憩終了">
	</form>
</body>
</html>