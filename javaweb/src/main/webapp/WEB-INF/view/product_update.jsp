<%@page import="javaweb.model.dto.ProductDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ProductDto productDto = (ProductDto)request.getAttribute("productDto");
%>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Product 修改</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf" %>
		
		<div style="padding: 15px">
			<table>
				<td valign="top">
					<form class="pure-form" method="post" action="/javaweb/product/update">
						<fieldset>
							<legend>Product 修改</legend>
							產品序號: <input type="text" name="productId" value="<%=productDto.getProductId() %>" readonly /><p /> 
							產品名稱: <input type="text" name="productName" value="<%=productDto.getProductName() %>" /><p /> 
							產品價格: <input type="number" name="price" value="<%=productDto.getPrice() %>" /><p />
							庫存數量: <input type="number" name="stockQuantity" value="<%=productDto.getStockQuantity() %>" readonly /><p />
							<button type="submit" class="button-success pure-button">Update</button>	  
						</fieldset>
					</form>
				</td>
			</table>
		</div>
	</body>
</html>