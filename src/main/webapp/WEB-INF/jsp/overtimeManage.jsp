<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>残業管理画面</title>
	</head>
	<body>
		<form method="get" action="overtime-manage">
		    <select name="year" onchange="this.form.submit()">
		        <c:forEach var="y" items="${years}">
		            <option value="${y}" ${y == selectedYear ? 'selected' : ''}>${y}年</option>
		        </c:forEach>
		    </select>
		</form>
		
		<table border="1">
		    <tr>
		        <th></th>
		        <th>第1週</th>
		        <th>第2週</th>
		        <th>第3週</th>
		        <th>第4週</th>
		        <th>第5週</th>
		        <th>月間</th>
		    </tr>
		    <c:forEach var="month" items="${overtimeData}">
		        <tr>
		            <td>${month.key}月</td>
		            <c:forEach var="week" items="${month.value}">
		                <td>${week}</td>
		            </c:forEach>
		        </tr>
		    </c:forEach>
		</table>
		<a href="AttendanceListServlet">勤怠一覧に戻る</a>
	</body>
</html>