<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>勤怠一覧</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/attendancelist.css">
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
	    <h2>勤怠記録一覧</h2>
	
		<div class="formbox">
		    <!-- ✅ 検索フォーム -->
		    <form method="get" action="AttendanceListServlet">
		        <input type="date" name="startDate" value="${param.startDate}">
		        <span class="range">～</span>
		        <input type="date" name="endDate" value="${param.endDate}">
		        <input type="submit" class="search_button" value="検索">
		    </form>
		</div>
	
	    <!-- ✅ 検索結果が0件の場合のメッセージ表示 -->
	    <c:if test="${empty attendanceList}">
	        <p style="color:red;">該当する勤怠記録はありません。</p>
	    </c:if>
	
	    <!-- ✅ 検索結果がある場合のみテーブルを表示 -->
	    <c:if test="${not empty attendanceList}">
	        <table class="t_design">
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
	                    <td><a class="a_button" href="AttendanceEditServlet?id=${att.id}">編集</a></td>
	                </tr>
	            </c:forEach>
	        </table>
	    </c:if>
	</div>
</body>
</html>
