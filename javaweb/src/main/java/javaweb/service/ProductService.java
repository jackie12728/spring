package javaweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javaweb.model.dto.ProductDto;
import javaweb.model.entity.Product;
import javaweb.repository.ProductDao;
import javaweb.repository.ProductDaoImpl;

//ProductService 是給 ProductServlet(Controller) 使用
public class ProductService {
	private ProductDao productDao = new ProductDaoImpl();
	
	// 所有使用者
	public List<ProductDto> findAllProducts() {
		List<ProductDto> productDtos = new ArrayList<>();
		// 將 Product 轉 ProductDto
		// 向 productDao 索取 List<Product> 集合
		List<Product> products = productDao.findAllProducts();
		for(Product product : products) {
			// 一個一個將 Product 轉成 ProductDto 並放在 productDtos 集合中
			ProductDto productDto = new ProductDto();
			productDto.setProductId(product.getProductId());
			productDto.setProductName(product.getProductName());
			productDto.setPrice(product.getPrice());
			productDto.setStockQuantity(product.getStockQuantity());
			
			productDtos.add(productDto);
		}
		
		// 或是這樣做
//		products.stream().forEach((p) -> {
//			productDtos.add(new ProductDto(p.getProductId(), p.getproductName(), p.getPrice(), p.getStockQuantity()));
//		});
		return productDtos;
	}
	
	// 取得指定產品
	public ProductDto getProduct(String productName) {
		Product product = productDao.getProduct(productName);
		if(product == null) {
			return null;
		}
		
		// 一個一個將 Product 轉成 ProductDto
		ProductDto productDto = new ProductDto();
		productDto.setProductId(product.getProductId());
		productDto.setProductName(product.getProductName());
		productDto.setPrice(product.getPrice());
		productDto.setStockQuantity(product.getStockQuantity());
		return productDto;
	}
	
	// 新增產品
	public void appendProduct(String productName, String price, String stockQuantity) {
		// 封裝到 Product 物件中
		Product product = new Product();
		product.setProductName(productName);
		product.setPrice(Double.parseDouble(price));
		product.setStockQuantity(Integer.parseInt(stockQuantity));
		
		productDao.addProduct(product);
	}
	
	// 修改產品
	public void updateProduct(String productId, String productName, String price) {
		if(!productName.isEmpty()) {
			productDao.updateProductName(Integer.parseInt(productId), productName);
		}
		if(!price.isEmpty()) {
			productDao.updatePrice(Integer.parseInt(productId), Double.parseDouble(price));
		}
	}
	
	// 刪除產品
	public void deleteProduct(String productId) {
		productDao.deleteProduct(Integer.parseInt(productId));
	}
	
	// 銷售排行
	public Map<String, Double> querySalesRanking() {
		return productDao.querySalesRanking();
	}
}
