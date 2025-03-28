<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rooms List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script>
        function confirmDeletion(name) {
            if (confirm("Are you sure you want to delete '" + name + "'?")) {
                document.getElementById("deleteForm-" + name).submit();
            }
        }
    </script>
    <style>
        /* General Styling */
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f8fb;
            margin: 0;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #444;
            font-size: 2em;
            margin-bottom: 30px;
        }

        .container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }

        .card {
            background: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
            flex: 1 0 250px;
            max-width: 280px;
            text-align: center;
            box-sizing: border-box;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
        }

        .card h2 {
            font-size: 22px;
            color: #007BFF;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .card h4 {
            font-size: 16px;
            color: #666;
            margin-bottom: 15px;
        }

        .delete-btn, .view-details {
            display: inline-block;
            padding: 10px 15px;
            border-radius: 5px;
            border: none;
            font-size: 14px;
            cursor: pointer;
            margin: 10px 5px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        .delete-btn {
            background: #dc3545;
            color: white;
        }

        .delete-btn:hover {
            background: #c82333;
        }

        .view-details {
            background: #007BFF;
            color: white;
            text-decoration: none;
        }

        .view-details:hover {
            background: #0056b3;
            text-decoration: none;
        }

        @media (max-width: 600px) {
            .card {
                flex: 1 0 100%;
            }
        }
    </style>
</head>
<body>
    <h1>Rooms List</h1>
    <div class="container">
        <!-- Iterate through the list passed by the model -->
        <c:forEach var="item" items="${listOfRooms}">
            <div class="card">
                <h2>Room: ${item.roomName}</h2>
                <h4>Members Count: ${item.countOfMembers}</h4>

                <!-- Delete Button -->
                <form id="deleteForm-${item.roomName}" action="/deleteRoom" method="post" style="display:inline;">
                    <input type="hidden" name="name" value="${item.roomName}" />
                    <button type="button" class="delete-btn" onclick="confirmDeletion('${item.roomName}')">Delete</button>
                </form>

                <!-- View Details Button -->
                <a href="/getListOfRooms/roomDetails?roomName=${item.roomName}" class="view-details">View Details</a>
            </div>
        </c:forEach>
    </div>
</body>
</html>
