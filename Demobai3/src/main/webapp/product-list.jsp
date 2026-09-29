<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Danh sách sản phẩm</title>
  <style>
    /* Khung bao ngoài cùng */
    .container {
      border: 1px solid green;
      padding: 15px;
      width: fit-content;
    }

    /* Danh sách sản phẩm dàn hàng ngang */
    .product-list {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      margin-top: 10px;
      align-items: stretch; /* Căn tất cả ô card cao bằng nhau */
    }

    /* Thẻ khung cho từng sản phẩm */
    .product-card {
      border: 1px solid #333;
      padding: 10px;
      width: 160px;
      text-align: center;
      box-sizing: border-box;
      display: flex;
      flex-direction: column;
      justify-content: space-between; /* Dàn đều các phần tử từ trên xuống */
    }

    /* Cố định chiều cao vùng tiêu đề tên sản phẩm */
    .product-card .title {
      font-weight: bold;
      min-height: 40px; /* Cố định không gian cho tên dài rớt dòng */
      display: flex;
      align-items: center;
      justify-content: center;
    }

    /* Cấu hình kích thước ảnh chuẩn */
    .product-card img {
      width: 120px;
      height: 100px;
      object-fit: contain; /* Giữ tỷ lệ ảnh, không bị méo */
      margin: 5px auto;
    }

    .product-card input[type="text"] {
      width: 50px;
      text-align: center;
    }
  </style>
</head>
<body>

<div class="container">
  <a href="${pageContext.request.contextPath}/cart">View Cart</a>

  <div class="product-list">
    <c:forEach var="p" items="${products}">
      <div class="product-card">
        <!-- Tiêu đề sản phẩm -->
        <div class="title">${p.model}</div>

        <!-- Hình ảnh sản phẩm lấy trực tiếp URL từ CSDL -->
        <img src="${p.imgURL}" alt="${p.model}"/>

        <!-- Giá sản phẩm -->
        <div>Price: ${p.price}</div>

        <!-- Form thêm vào giỏ hàng -->
        <form action="${pageContext.request.contextPath}/cart" method="post">
          <input type="hidden" name="action" value="add"/>
          <input type="hidden" name="id" value="${p.id}"/>
          <input type="text" name="quantity" value="1"/><br/><br/>
          <input type="submit" value="Add To Cart"/>
        </form>

        <!-- Link xem chi tiết -->
        <a href="${pageContext.request.contextPath}/product?id=${p.id}">Product Detail</a>
      </div>
    </c:forEach>
  </div>
</div>

</body>
</html>