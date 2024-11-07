<%@ page import="javaweb.model.dto.ProductDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<%@ taglib uri="jakarta.tags.fmt" prefix="f" %> <!-- 格式化庫 -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>💻Product</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	<body style="padding: 15px">
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf" %>
		<form class="pure-form" method="post" action="/javaweb/product/add">
			<fieldset>
				<legend>Product 新增</legend>
				產品名稱: <input type="text" name="productName" placeholder="請輸入 productname" required /><p />
				產品價格: <input type="text" name="price" placeholder="請輸入 price" required /><p />
				庫存數量: <input type="number" name="stockQuantity" min="0" placeholder="請輸入 stock_quantity" required /><p />
				<button type="reset" class="button-warning pure-button">Reset</button>
				<button type="submit" class="button-success pure-button">Submit</button>
			</fieldset>
		</form>
		<p />
		<div class="pure-form">
			<fieldset>
				<legend>Product 列表</legend>
				<table border="1">
					<thead>
						<tr>
							<th>ID</th><th>產品名稱</th><th>產品價格</th><th>庫存數量</th><th>修改</th><th>刪除</th>
						</tr>
					</thead>
					<c:forEach var="productDto" items="${ productDtos }">
						<tr>
							<td align="center">${ productDto.productId }</td>
							<td>${ productDto.productName }</td>
							<td align="right">
									<f:formatNumber value="${ productDto.price }" type="currency" maxFractionDigits="0" />
							</td>
							<td align="right">
									<f:formatNumber value="${ productDto.stockQuantity }" />
							</td>
							<td><a href="/javaweb/product/get?productname=${ productDto.productName }" class="button-secondary pure-button">修改</a>
							<td><a href="/javaweb/product/delete?productId=${ productDto.productId }" class="button-secondary pure-button">刪除</a>
						</tr>
					</c:forEach>
				</table>
			</fieldset>
		</div>
	</body>
</html>