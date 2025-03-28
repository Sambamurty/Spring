<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Payments</title>
<style>
table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: left;
}

th {
	background-color: #f2f2f2;
}
</style>
</head>
<body>
	<h1>Payment Details</h1>

	<table>
		<thead>
			<tr>
				<th>FromToWhom</th>
				<th>ToPay</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entry" items="${paymentsList.entrySet()}">
				<tr>
					<td>${entry.getKey()}</td>
					<td>${entry.getValue()}</td>
				</tr>
			</c:forEach>
		</tbody>


	</table>
</body>
</html>
