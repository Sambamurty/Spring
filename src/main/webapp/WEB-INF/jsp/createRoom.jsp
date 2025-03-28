<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Room</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function generateTable() {
            const numMembers = document.getElementById("numMembers").value;
            if (!numMembers || numMembers <= 0) {
                alert("Please enter a valid number of members.");
                return;
            }
            const table = document.getElementById("membersTable");
            table.innerHTML = ""; // Clear existing table rows

            for (let i = 1; i <= numMembers; i++) {
                const row = table.insertRow();
                const cell1 = row.insertCell(0);
                const cell2 = row.insertCell(1);
                cell1.innerHTML = i; // ID column
                cell2.innerHTML = `<input type='text' name='names' placeholder='Enter Name' required />`; // Name column
            }

            // Show the modal (centered)
            $("#popupModal").css({
                display: "flex"
            }).hide().fadeIn(400); // Smooth fade-in animation
        }

        function closeModal() {
            // Hide the modal
            $("#popupModal").fadeOut(400);
        }
    </script>
    <style>
        /* General Styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        form {
            max-width: 500px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            color: #555;
        }

        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            display: inline-block;
            padding: 10px 20px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background: #45a049;
        }

        /* Modal Styling */
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            justify-content: center;
            align-items: center; /* Vertically center */
            background-color: rgba(0, 0, 0, 0.6); /* Transparent overlay */
            z-index: 1000;
        }

        .modal-content {
            background: white;
            padding: 20px;
            border-radius: 8px;
            width: 50%;
            max-width: 600px;
            box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.2);
            animation: slideIn 0.4s ease; /* Smooth animation */
        }

        @keyframes slideIn {
            from {
                transform: translateY(-20%);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        #tableContainer {
            max-height: 200px;
            overflow-y: auto;
            margin-top: 10px;
            border: 1px solid #ccc;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f4f4f4;
        }

        .close-btn {
            background: #f44336;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 8px 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .close-btn:hover {
            background: #e53935;
        }

        .message {
            text-align: center;
            margin: 20px auto;
            padding: 10px;
            color: green;
            font-weight: bold;
        }

        .error {
            text-align: center;
            margin: 20px auto;
            padding: 10px;
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h1>Create a Room</h1>

    <!-- Display Success Message -->
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <!-- Display Error Message -->
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="/createRoom" method="post">
        <label for="tableName">Room Name:</label>
        <input type="text" id="tableName" name="tableName" placeholder="Enter room name" required />

        <label for="numMembers">Number of Members:</label>
        <input type="number" id="numMembers" name="numMembers" placeholder="Enter number of members" required />

        <button type="button" onclick="generateTable()">Add Names</button>

        <!-- Modal Popup -->
        <div id="popupModal" class="modal">
            <div class="modal-content">
                <h2 style="text-align: center;">Enter Member Names</h2>
                <div id="tableContainer">
                    <table id="membersTable">
                        <!-- Dynamically generated rows will go here -->
                    </table>
                </div>
                <div class="modal-footer" style="text-align: center; margin-top: 20px;">
                    <button type="submit">Submit</button>
                    <button type="button" class="close-btn" onclick="closeModal()">Close</button>
                </div>
            </div>
        </div>
    </form>
</body>
</html>

