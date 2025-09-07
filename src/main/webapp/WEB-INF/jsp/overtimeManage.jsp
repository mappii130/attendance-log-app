<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>残業管理画面</title>
	</head>
	<body>
    <!-- 年選択フォーム -->
    <form action="overtime-manage" method="get">
        年を選択:
        <select name="year" onchange="this.form.submit()">
            <c:forEach var="y" items="${yearList}">
                <option value="${y}" <c:if test="${y == year}">selected</c:if>>${y}</option>
            </c:forEach>
        </select>
    </form>

    <br>

    <!-- 残業時間テーブル -->
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
                    <!-- 月 -->
                    <td>${month}月</td>

                    <!-- 各週の残業時間 -->
                    <c:set var="monthTotal" value="0" scope="page" />
                    <c:forEach var="w" begin="1" end="5">
						<c:choose>
						    <c:when test="${overtimeData[month][w] != null}">
						        <td>${overtimeData[month][w]}</td>
						    </c:when>
						    <c:otherwise>
						        <td>-</td>
						    </c:otherwise>
						</c:choose>
                    </c:forEach>

                    <!-- 月合計 -->
                    <td>
                        <c:choose>
                            <c:when test="${monthTotal > 0}">${monthTotal}</c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
		<a href="AttendanceListServlet">勤怠一覧に戻る</a>
</body>
</html>