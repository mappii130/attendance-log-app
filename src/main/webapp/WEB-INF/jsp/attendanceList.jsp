<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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