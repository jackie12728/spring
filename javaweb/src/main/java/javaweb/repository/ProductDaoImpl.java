package javaweb.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import javaweb.model.entity.Product;

public class ProductDaoImpl extends BaseDao implements ProductDao {

	@Override
	public List<Product> findAllProducts() {
		List<Product> products = new ArrayList<>();
		String sql = "select product_id, product_name, price, stock_quantity from product";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			// 逐筆尋訪
			while (rs.next()) {
				// 建立 product 物件並將資料配置進去
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setPrice(rs.getDouble("price"));
				product.setStockQuantity(rs.getInt("stock_quantity"));
				// 將 product 物件放到 products 集合中保存
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products; // 回傳有 product 物件的集合
	}

	@Override
	public Product getProduct(String productname) {
		String sql = "select product_id, product_name, price, stock_quantity from product where productname=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productname);

			// pstmt 設定好參數再取得查詢結果
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) { // 若有得到一筆
					// 建立 product 物件並將資料配置進去
					Product product = new Product();
					product.setProductId(rs.getInt("product_id"));
					product.setProductName(rs.getString("product_name"));
					product.setPrice(rs.getDouble("price"));
					product.setStockQuantity(rs.getInt("stock_quantity"));
					return product; // 返回 product 物件
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addProduct(Product product) {
		String sql = "insert into product(product_name, price, stock_quantity) values(?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, product.getProductName());
			pstmt.setDouble(2, product.getPrice());
			pstmt.setInt(3, product.getStockQuantity());

			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("新增失敗");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(Integer productId) {
		String sql = "delete from product where product_id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, productId);
			
			int rowcount = pstmt.executeUpdate();
			if(rowcount != 1)
				throw new RuntimeException("刪除失敗 productId：" + productId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addProductStock(Integer productId, Integer quantity) {

		Product pro = findProductById(productId);
		Integer nq = pro.getStockQuantity() + quantity;

		String sql = "update product set stock_quantity=? where product_id=? ";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, nq);
			pstmt.setInt(2, productId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Product findProductById(Integer productId) {
		String sql = "select * from product where product_id=? ";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, productId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setPrice(rs.getDouble("price"));
				product.setStockQuantity(rs.getInt("stock_quantity"));
				return product; // 返回 product 物件
			}
			throw new RuntimeException("失敗");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateProductName(Integer productId, String productname) {
		String sql = "update product set product_name = ? where product_id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productname);
			pstmt.setInt(2, productId);
			
			int rowcount = pstmt.executeUpdate();
			if(rowcount != 1) {
				throw new RuntimeException("修改失敗 productId: " + productId + " productname: " + productname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updatePrice(Integer productId, Double price) {
		String sql = "update product set price = ? where product_id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDouble(1, price);
			pstmt.setInt(2, productId);
			
			int rowcount = pstmt.executeUpdate();
			if(rowcount != 1) {
				throw new RuntimeException("修改失敗 productId: " + productId + " price: " + price);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Double> querySalesRanking() {
		String sql = """
						SELECT p.product_name, SUM(o.subtotal) AS total_sales
						FROM orders o
						LEFT JOIN product p ON o.product_id = p.product_id
						GROUP BY p.product_name
						ORDER BY total_sales DESC
					""".trim();
		// 存放銷售排行 map
				Map<String, Double> map = new LinkedHashMap<>();
				
				try(Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql)) {
					
					while (rs.next()) {
						
						String key = rs.getString("product_name");
						Double value = rs.getDouble("total_sales");
						// 將排行放到 map 集合中
						map.put(key, value);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return map;
	}

}
