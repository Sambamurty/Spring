<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Room Details</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
/* General Styling */
body {
	font-family: 'Roboto', sans-serif;
	margin: 0;
	padding: 0;
	display: flex;
	height: 100vh;
}

/* Left Filter Section */
.filters {
	width: 20%;
	background-color: #f4f8fb;
	border-right: 1px solid #ddd;
	padding: 20px;
	box-sizing: border-box;
	overflow-y: auto;
}

.filter-title {
	font-size: 18px;
	color: #333;
	margin-bottom: 20px;
	font-weight: bold;
}

.filter-item {
	margin-bottom: 15px;
}

.filter-item button {
	background: #007BFF;
	color: white;
	border: none;
	border-radius: 4px;
	padding: 10px;
	cursor: pointer;
	width: 100%;
	text-align: left;
	font-size: 16px;
}

.filter-item button:hover {
	background: #0056b3;
}

.filter-options {
	display: none; /* Initially hidden */
	margin-top: 10px;
	padding-left: 10px;
}

.filter-options input, .filter-options select {
	width: 100%;
	padding: 8px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

.filter-options button {
	background: #4CAF50;
	color: white;
	border: none;
	border-radius: 4px;
	padding: 8px;
	cursor: pointer;
	text-align: center;
	font-size: 14px;
}

.filter-options button:hover {
	background: #45a049;
}

/* Right Content Section */
.content {
	width: 80%;
	padding: 20px;
	box-sizing: border-box;
	overflow-y: auto;
}

.content h2 {
	font-size: 24px;
	color: #007BFF;
	margin-bottom: 20px;
}

.person-list {
	display: flex;
	flex-wrap: wrap;
	gap: 15px;
}

.person-card {
	background: #ffffff;
	padding: 15px;
	border-radius: 8px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	width: 200px;
	text-align: center;
}

.person-card h3 {
	font-size: 18px;
	margin: 0;
	color: #333;
}

/* Add Expenses Button */
.add-expenses-btn {
	padding: 10px 20px;
	background: #007BFF;
	color: white;
	text-decoration: none;
	border-radius: 5px;
	font-size: 16px;
	position: absolute;
	top: 10px;
	right: 10px;
	cursor: pointer;
	border: none;
}

.add-expenses-btn:hover {
	background: #0056b3;
}

/* Modal Overlay */
.modal-overlay {
	display: none; /* Hidden by default */
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.6); /* Transparent dark overlay */
	justify-content: center;
	align-items: center;
	z-index: 1000;
}

/* Modal Content */
.modal-content {
	background: white;
	padding: 20px;
	border-radius: 10px;
	width: 400px;
	text-align: center;
	box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.3);
}

.modal-content h2 {
	color: #007BFF;
	margin-bottom: 20px;
}

.modal-content form input[type="text"], .modal-content form input[type="number"]
	{
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 14px;
}

.modal-content form button {
	padding: 10px 20px;
	background: #28a745;
	color: white;
	border: none;
	border-radius: 5px;
	font-size: 16px;
	cursor: pointer;
}

.modal-content form button:hover {
	background: #218838;
}

.modal-content .close-btn {
	background: #dc3545;
	color: white;
	padding: 8px 15px;
	border: none;
	border-radius: 5px;
	font-size: 14px;
	cursor: pointer;
	margin-top: 15px;
}

.modal-content .close-btn:hover {
	background: #c82333;
}

.modal-content form select {
	width: 100%;
	/* Makes the dropdown stretch to occupy full width of its container */
	padding: 10px;
	/* Adds padding inside the dropdown for better spacing */
	margin-bottom: 15px;
	/* Adds spacing between dropdown and other form elements */
	border: 1px solid #ccc; /* Sets a light gray border for the dropdown */
	border-radius: 5px; /* Gives rounded corners to the dropdown */
	font-size: 14px; /* Sets font size for readable text */
}

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
	background-color: #f4f8fb;
	font-weight: bold;
}

button.edit-btn {
	background-color: #007BFF;
	color: white;
	border: none;
	border-radius: 5px;
	padding: 5px 10px;
	cursor: pointer;
	font-size: 14px;
}

button.edit-btn:hover {
	background-color: #0056b3;
}

button.delete-btn {
	background-color: #DC3545;
	color: white;
	border: none;
	border-radius: 5px;
	padding: 5px 10px;
	cursor: pointer;
	font-size: 14px;
}

button.delete-btn:hover {
	background-color: #C82333;
}

.bottom-right-btn {
	position: fixed;
	bottom: 20px;
	right: 20px;
	background-color: #007BFF; /* Change color as per your theme */
	color: white;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	font-size: 16px;
	cursor: pointer;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.bottom-right-btn:hover {
	background-color: #0056b3; /* Hover color */
}

@media ( max-width : 768px) {
	.filters {
		width: 100%;
		border-right: none;
		border-bottom: 1px solid #ddd;
	}
	.content {
		width: 100%;
	}
}
</style>
<script>
	$(document).ready(function() {
		// Toggle filter options
		$(".filter-item button").click(function() {
			const options = $(this).next(".filter-options");
			$(".filter-options").not(options).slideUp(); // Hide other filters
			options.slideToggle(); // Toggle clicked filter
		});
	});

	// Open Modal
	function openModal() {
		document.querySelector(".modal-overlay").style.display = "flex";
	}

	
	// Close Modal
	function closeModal() {
		document.querySelector(".modal-overlay").style.display = "none";
	}
</script>
</head>
<body>
	<div class="filters">
		<div class="filter-title">Filters</div>

		<div class="filter-item">
			<button type="button">Get by Person</button>
			<div class="filter-options">
				<form action="/getListOfRooms/roomDetails/getByPerson" method="GET">
					<label for="person-select">Choose Person:</label> <select
						id="person-select" name="name" required>
						<option value="" disabled selected>Select a Person</option>
						<c:forEach var="person" items="${namesList}">
							<option value="${person}">${person}</option>
						</c:forEach>
					</select> <input type="hidden" id="room-name" name="roomName"
						value="${roomName}" />
					<button type="submit">Filter</button>
				</form>
			</div>
		</div>



		<!-- Get Between Dates -->
		<div class="filter-item">
			<button type="button">Get Between Dates</button>
			<div class="filter-options">
				<form action="/getListOfRooms/roomDetails/getBetweenDates"
					method="get">

					<label>From:</label> <input type="date" name="fromDate" required />
					<label>To:</label> <input type="date" name="toDate" required /> <input
						type="hidden" id="room-name" name="roomName" value="${roomName}" />
					<button type="submit">Filter</button>
				</form>
			</div>
		</div>


		<!-- Get by Price -->
		<div class="filter-item">
			<button type="button">Get by Price</button>
			<div class="filter-options">
				<form action="/getListOfRooms/roomDetails/getBetweenPrice"
					method="get">
					<label>Minimum Price:</label> <input type="number"
						placeholder="Min Price" name="floorPrice" required /> <label>Maximum
						Price:</label> <input type="number" placeholder="Max Price"
						name="ceilPrice" required /> <input type="hidden" id="room-name"
						name="roomName" value="${roomName}" />
					<button type="submit">Filter</button>
				</form>
			</div>
		</div>
	</div>


	<!-- Add Expenses Button -->
	<button class="add-expenses-btn" onclick="openModal()">Add
		Expenses</button>

	<!-- Modal for Adding Expenses -->
	<div class="modal-overlay">
		<div class="modal-content">
			<h2>Add Expenses</h2>
			<form action="/getListOfRooms/roomDetails/addExpenses" method="post">
				<input type="hidden" name="roomName" value="${roomName}" /> <select
					name="username" required>
					<option value="" disabled selected>Choose a Person</option>
					<c:forEach var="person" items="${namesList}">
						<option value="${person}">${person}</option>
					</c:forEach>
				</select> <input type="text" name="itemName" placeholder="Item Name" required />
				<input type="number" name="cost" placeholder="Cost of Item" required />
				<button type="submit">Add Expense</button>
			</form>
			<button class="close-btn" onclick="closeModal()">Cancel</button>
		</div>
	</div>
	<div class="content">
		<h2>Room Members</h2>
		<div class="person-list">
			<c:forEach var="name" items="${namesList}">
				<div class="person-card">
					<h3>${name}</h3>
				</div>
			</c:forEach>
		</div>

		<h2>Expenses</h2>
		<table
			style="width: 100%; border-collapse: collapse; margin-top: 20px;">
			<thead>
				<tr>
					<th style="border: 1px solid #ddd; padding: 8px; text-align: left;">Name</th>
					<th style="border: 1px solid #ddd; padding: 8px; text-align: left;">Item</th>
					<th style="border: 1px solid #ddd; padding: 8px; text-align: left;">Price</th>
					<th style="border: 1px solid #ddd; padding: 8px; text-align: left;">Time</th>
					<th style="border: 1px solid #ddd; padding: 8px; text-align: left;">Modify
						Item or Price</th>
					<th style="border: 1px solid #ddd; padding: 8px; text-align: left;">Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="expense" items="${expensesByRoom}">
					<tr>
						<td style="border: 1px solid #ddd; padding: 8px;">${expense.membersList.personName}</td>
						<td style="border: 1px solid #ddd; padding: 8px;">${expense.items}</td>
						<td style="border: 1px solid #ddd; padding: 8px;">${expense.cost}</td>
						<td style="border: 1px solid #ddd; padding: 8px;">${expense.date}</td>
						<td style="border: 1px solid #ddd; padding: 8px;">
							<!-- Buttons for Edit and Delete actions -->
							<button class="edit-btn"
								onclick="editExpense(${expense.enteryId})">Edit</button>
						</td>
						<td style="border: 1px solid #ddd; padding: 8px;">
							<!-- Button to delete record -->
							<form action="${pageContext.request.contextPath}/deleteRecord"
								method="post" style="display: inline;">
								<input type="hidden" name="personName"
									value="${expense.membersList.personName}" /> <input type="hidden"
									name="date" value="${expense.date}" /> <input
									type="hidden" name="roomId" value="${expense.roomId}" />
								<button type="submit" class="delete-btn">Delete</button>
							</form>
						</td>

					</tr>

				</c:forEach>
			</tbody>
		</table>
	</div>
	<div>
		<form action="/getListOfRooms/roomDetails/getMonthlyToPay"
			method="GET">
			<input type="hidden" name="roomName" value="${roomName}" />
			<button type="submit" class="bottom-right-btn">Whom to Pay</button>
		</form>
	</div>
</body>
</html>