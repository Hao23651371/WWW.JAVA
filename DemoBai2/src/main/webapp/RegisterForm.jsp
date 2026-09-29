<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>User Registration Form</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        body {
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .form-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 420px;
        }
        .form-title {
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 20px;
            color: #222;
        }
        .row-2col {
            display: flex;
            gap: 12px;
            margin-bottom: 12px;
        }
        .row-3col {
            display: flex;
            gap: 10px;
            margin-bottom: 12px;
        }
        .input-group {
            margin-bottom: 12px;
        }
        label {
            display: block;
            font-size: 13px;
            color: #333;
            margin-bottom: 6px;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"],
        select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #dcdcdc;
            border-radius: 6px;
            font-size: 13px;
            outline: none;
            background-color: #fff;
        }
        input:focus, select:focus {
            border-color: #0066ff;
        }
        .radio-group {
            display: flex;
            align-items: center;
            gap: 15px;
            margin-bottom: 20px;
        }
        .radio-option {
            display: flex;
            align-items: center;
            gap: 5px;
            font-size: 13px;
            color: #333;
            cursor: pointer;
        }
        .btn-submit {
            width: 100%;
            padding: 12px;
            background-color: #0066ff;
            color: #ffffff;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.2s ease;
        }
        .btn-submit:hover {
            background-color: #0052cc;
        }
    </style>
</head>
<body>

<div class="form-container">
    <div class="form-title">User Registration Form</div>

    <!-- Form hướng dẫn theo đúng yêu cầu -->
    <form action="${pageContext.request.contextPath}/registerform" method="post">

        <!-- First Name & Last Name -->
        <div class="row-2col">
            <input type="text" name="firstname" placeholder="First Name" required />
            <input type="text" name="lastname" placeholder="Last Name" required />
        </div>

        <!-- Email -->
        <div class="input-group">
            <input type="email" name="email" placeholder="Your Email" required />
        </div>

        <!-- Password -->
        <div class="input-group">
            <input type="password" name="password" placeholder="Password" required />
        </div>

        <!-- Birthday Selects -->
        <div class="input-group">
            <label>Birthday</label>
            <div class="row-3col">
                <select name="month" required>
                    <option value="" disabled selected hidden>Month</option>
                    <option value="1">Jan</option>
                    <option value="2">Feb</option>
                    <option value="3">Mar</option>
                    <option value="4">Apr</option>
                    <option value="5">May</option>
                    <option value="6">Jun</option>
                    <option value="7">Jul</option>
                    <option value="8">Aug</option>
                    <option value="9">Sep</option>
                    <option value="10">Oct</option>
                    <option value="11">Nov</option>
                    <option value="12">Dec</option>
                </select>

                <select name="day" required>
                    <option value="" disabled selected hidden>Day</option>
                    <% for(int i = 1; i <= 31; i++) { %>
                    <option value="<%= i %>"><%= i %></option>
                    <% } %>
                </select>

                <select name="year" required>
                    <option value="" disabled selected hidden>Year</option>
                    <% for(int i = 2026; i >= 1950; i--) { %>
                    <option value="<%= i %>"><%= i %></option>
                    <% } %>
                </select>
            </div>
        </div>

        <!-- Gender Radio -->
        <div class="input-group">
            <label>Gender</label>
            <div class="radio-group">
                <label class="radio-option">
                    <input type="radio" name="gender" value="Female" required /> Female
                </label>
                <label class="radio-option">
                    <input type="radio" name="gender" value="Male" /> Male
                </label>
            </div>
        </div>

        <!-- Submit Button -->
        <button type="submit" class="btn-submit">Sign Up</button>

    </form>
</div>

</body>
</html>