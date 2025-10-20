<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>残業管理画面</title>
</head>
<body>
<h2>残業管理画面 (${year})</h2>

<form action="OvertimeManageServlet" method="get">
    年を選択:
    <select name="year" onchange="this.form.submit()">
        <c:forEach var="y" items="${yearList}">
            <option value="${y}" <c:if test="${y == year}">selected</c:if>>${y}</option>
        </c:forEach>
    </select>
</form>

<br/>

<table>
    <thead>
        <tr>
            <th>月</th>
            <th>第1週</th>
            <th>第2週</th>
            <th>第3週</th>
            <th>第4週</th>
            <th>第5週</th>
            <th>合計</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="month" items="${monthList}">
            <tr>
                <td>${month}月</td>

                <!-- overtimeDataStr は String キーの map -->
                <c:set var="weekMap" value="${overtimeDataStr[month]}" />

                <td><c:out value="${weekMap['1']}" /></td>
                <td><c:out value="${weekMap['2']}" /></td>
                <td><c:out value="${weekMap['3']}" /></td>
                <td><c:out value="${weekMap['4']}" /></td>
                <td><c:out value="${weekMap['5']}" /></td>

                <td>
                    <c:out value="${weekMap['99']}" />
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<br/>
<a href="AttendanceListServlet">勤怠一覧に戻る</a>
</body>
</html>
