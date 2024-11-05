package javaweb.repository;

import java.util.List;
import java.util.Map;

import javaweb.model.entity.Product;

public interface ProductDao {
	// 多筆：查詢所有產品
	List<Product> findAllProducts();
	
	// Map<商品名稱(product_name), 銷售金額 (total_sales)>
	Map<String, Double> querySalesRanking(); // 銷售排行
	
	// 單筆：根據 productname 查詢該筆產品
	Product getProduct(String productname);
	
	// 新增
	void addProduct(Product product);
	
	// 刪除: 根據 productId 來刪除
	void deleteProduct(Integer productId);
	
	// 進貨：根據 productId 來修改庫存數量
	void addProductStock(Integer productId, Integer quantity);
	
	// 進貨：根據 productId 來查詢該筆商品
	Product findProductById(Integer productId);
	
	// 修改：product_name 名稱
	void updateProductName(Integer productId, String productname);
	
	// 修改：price 金額
	void updatePrice(Integer productId, Double price);
}
