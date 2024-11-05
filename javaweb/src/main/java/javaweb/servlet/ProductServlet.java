package javaweb.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaweb.model.dto.ProductDto;
import javaweb.service.ProductService;

/**
 * -- 商品 product +------------+------------------+----------+----------------+ |
 * product_id | product_name | price | stock_quantity |
 * +------------+------------------+----------+----------------+ | 1 | PC |
 * 30000.00 | 50 | | 2 | Mobile | 15000.00 | 100 | | 3 | MusicBox | 3000.00 |
 * 200 | | 4 | Pad | 20000.00 | 75 | | 5 | Watch | 8000.00 | 150 |
 * +------------+------------------+----------+----------------+
 * 
 * -- 創建商品表 create table if not exists product ( product_id int primary key
 * auto_increment comment '商品Id', product_name varchar(50) not null unique
 * comment '商品名稱', price decimal(10, 2) not null comment '商品價格', stock_quantity
 * int not null default 0 comment '商品庫存' );
 * 
 * insert into product(product_name, price, stock_quantity) values('PC',
 * 30000.00, 50); insert into product(product_name, price, stock_quantity)
 * values('Mobile', 15000.00, 100); insert into product(product_name, price,
 * stock_quantity) values('MusicBox', 3000.00, 200); insert into
 * product(product_name, price, stock_quantity) values('Pad', 20000.00, 75);
 * insert into product(product_name, price, stock_quantity) values('Watch',
 * 8000.00, 150);
 * 
 * 
 * MVC + 自訂框架
 * 
 * request +----------------+ +----------------+ +------------+ ---------> |
 * ProductServlet | ----------> | ProductService | -------> | ProductDao |
 * -------> MySQL | (Controller) | <---------- | | <------- | | <-------
 * (web.product) +----------------+ ProductDto +----------------+ Product
 * +------------+ | (Dto) (Entity) | v +-------------+ <--------- | product.jsp
 * | response | (View) | +-------------+
 * 
 * 查詢全部: GET /product, /products
 * 
 */
@WebServlet(urlPatterns = { "/product/*", "/products", "/product/sales/ranking" })
public class ProductServlet extends HttpServlet {
	private ProductService productService = new ProductService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		// resp.getWriter().print(pathInfo); // 用於檢察 pathInfo

//		if (pathInfo.equals("/delete")) {
//			// 刪除
//			String productId = req.getParameter("productId");
//			productService.deleteProduct(productId);
//
//			// 刪除完畢之後, 重新執行指定頁面
//			resp.sendRedirect("/javaweb/product");
//			return;
//		} else if (pathInfo.equals("/get")) {
//			// 取得 product 資料並導入到修改頁面
//			String productname = req.getParameter("productname");
//			ProductDto productDto = productService.getProduct(productname);
//
//			// 將必要資料加入到 request 屬性中以便交由 jsp 進行分析與呈現
//			req.setAttribute("productDto", productDto);
//
//			// 內重導到 product.jsp / 刷新頁面
//			req.getRequestDispatcher("/WEB-INF/view/product.jsp").forward(req, resp);
//			return;
//		}

//		switch (pathInfo) {
//		case "/delete":
//			// 刪除
//			String productId = req.getParameter("productId");
//			productService.deleteProduct(productId);
//
//			// 刪除完畢之後, 重新執行指定頁面
//			resp.sendRedirect("/javaweb/product");
//			break;
//		case "/get":
//			// 取得 product 資料並導入到修改頁面
//			String productname = req.getParameter("productname");
//			ProductDto productDto = productService.getProduct(productname);
//
//			// 將必要資料加入到 request 屬性中以便交由 jsp 進行分析與呈現
//			req.setAttribute("productDto", productDto);
//
//			// 內重導到 product.jsp / 刷新頁面
//			req.getRequestDispatcher("/WEB-INF/view/product.jsp").forward(req, resp);
//			break;
//		}

		String servletPath = req.getServletPath();
		switch (servletPath) {
		case "/product/sales/ranking":
			req.setAttribute("salesRankingMap", productService.querySalesRanking());
			req.getRequestDispatcher("/WEB-INF/view/sales_ranking.jsp").forward(req, resp);
			break;
		case "/products":
		default:
			req.setAttribute("productDtos", productService.findAllProducts());
			req.getRequestDispatcher("/WEB-INF/view/product.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		// 取得表單資料
		String productId = req.getParameter("productId");
		String productName = req.getParameter("productName");
		String price = req.getParameter("price");
		String stockQuantity = req.getParameter("stockQuantity");

		switch (pathInfo) {
		case "/add":
			productService.appendProduct(productName, price, stockQuantity);
			break;
		case "/update":
			productService.updateProduct(productId, productName, price);
			break;
		}
		// 外重導到指定 URL 網頁
		resp.sendRedirect("/javaweb/product");
	}
}