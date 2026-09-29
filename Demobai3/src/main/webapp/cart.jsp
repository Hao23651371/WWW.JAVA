<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>
    <style>
        /* Khung xanh lá bao ngoài cùng */
        .cart-container {
            border: 1px solid green;
            padding: 20px;
            width: 700px;
            font-family: Arial, sans-serif;
        }

        .cart-container h2 {
            margin-top: 0;
            font-size: 28px;
            font-weight: bold;
        }

        /* Bảng hiển thị sản phẩm */
        .cart-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 15px;
        }

        .cart-table th {
            text-align: left;
            padding: 8px 5px;
            font-weight: bold;
            font-size: 16px;
        }

        .cart-table td {
            padding: 10px 5px;
            border-bottom: 1px solid #eee;
            vertical-align: middle;
        }

        /* Dòng hiển thị Tổng tiền */
        .cart-table tr.total-row td {
            border-bottom: none;
            padding-top: 15px;
            font-weight: bold;
        }

        .input-qty {
            width: 180px;
            padding: 3px;
        }

        .btn-action {
            cursor: pointer;
        }

        .action-links {
            margin-top: 15px;
        }

        .action-links a {
            color: blue;
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="cart-container">
    <h2>Cart</h2>

    <c:choose>
        <c:when test="${empty cart || empty cart.items}">
            <p>Giỏ hàng của bạn đang trống.</p>
            <div class="action-links">
                <a href="${pageContext.request.contextPath}/product">Continute Shopping</a>
            </div>
        </c:when>
        <c:otherwise>
            <!-- Khởi tạo biến tính tổng tiền -->
            <c:set var="totalMoney" value="0" />

            <table class="cart-table">
                <thead>
                <tr>
                    <th style="width: 20%;">Model</th>
                    <th style="width: 40%;">Quantity</th>
                    <th style="width: 15%;">Price</th>
                    <th style="width: 15%;">SubTotal</th>
                    <th style="width: 10%;">Action</th>
                </tr>
                </thead>
                <tbody>
                <!-- Duyệt qua danh sách sản phẩm trong giỏ -->
                <c:forEach var="item" items="${cart.items}">
                    <!-- Cộng dồn tiền của từng sản phẩm -->
                    <c:set var="totalMoney" value="${totalMoney + (item.quantity * item.product.price)}" />
                    <tr>
                        <td>${item.product.model}</td>
                        <td>
                            <!-- Form Cập nhật số lượng -->
                            <form action="${pageContext.request.contextPath}/cart" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="update"/>
                                <input type="hidden" name="id" value="${item.product.id}"/>
                                <input type="number" name="quantity" value="${item.quantity}" min="1" class="input-qty"/>
                                <input type="submit" value="Cập nhật" class="btn-action"/>
                            </form>
                        </td>
                        <td>${item.product.price}</td>
                        <td>${item.quantity * item.product.price} VND</td>
                        <td>
                            <!-- Form Xóa 1 sản phẩm -->
                            <form action="${pageContext.request.contextPath}/cart" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="remove"/>
                                <input type="hidden" name="id" value="${item.product.id}"/>
                                <input type="submit" value="Xóa" class="btn-action"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

                <!-- Dòng hiển thị tổng tiền -->
                <tr class="total-row">
                    <td colspan="3">Total:</td>
                    <td colspan="2">${totalMoney} VND</td>
                </tr>
                </tbody>
            </table>

            <!-- Nút Xóa hết giỏ hàng -->
            <form action="${pageContext.request.contextPath}/cart" method="post" style="margin-bottom: 15px;">
                <input type="hidden" name="action" value="clear"/>
                <input type="submit" value="Xóa hết giỏ hàng" class="btn-action"/>
            </form>

            <div class="action-links">
                <a href="${pageContext.request.contextPath}/product">Tiếp tục mua</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>