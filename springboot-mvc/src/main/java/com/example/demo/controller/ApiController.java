package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.bean.Book;
import com.example.demo.response.ApiResponse;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// 了解各種不同 URL 與參數的傳遞接收
@RestController // 免去撰寫 @ResponseBody，但若有要回應 jsp 則不可用
@RequestMapping("/api") // 統一 URL 前綴，可以不設置
public class ApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	/**
	 * 1.歡迎頁 
	 * 路徑：/welcome 
	 * 路徑：/home 
	 * 網址：http://localhost:8080/api/welcome
	 * 網址：http://localhost:8080/api/home
	 */
	@GetMapping(value = { "/welcome", "/home" })
	public String welcome() {
		// 確認是否有執行到此方法
		logger.info("這是一條日誌訊息");
		return "Welcome";
	}

	/**
	 * 2. ?帶參數 
	 * 路徑：/greet?name=John&age=18 
	 * 路徑：/greet?name=Mary
	 * 網址：http://localhost:8080/api/greet?name=John&age=18
	 * 結果：John, 18 (成年)
	 * 網址：http://localhost:8080/api/greet?name=Mary 
	 * 結果：Mary, 0 (未成年) 限制：name
	 * 參數一定要加，age 參數可不加(預設初始值為0)
	 */
	@GetMapping("/greet")
	public String greet(@RequestParam(value = "name", required = true) String username,
						@RequestParam(value = "age", required = false, defaultValue = "0") Integer userage) {
		// 觀察參數
		logger.info("username = " + username + ", userage = " + userage);
		return String.format("Hi %s, %d (%s)", username, userage, userage >= 18?"成年":"未成年");
	}

	// 3. 上述 2 的精簡寫法
	@GetMapping("/greet2")
	public String greet2(@RequestParam String name, @RequestParam(defaultValue = "0") Integer age) {

		return String.format("Hi %s, %d (%s)", name, age, age >= 18 ? "成年" : "未成年");

	}
	
	/**
	 * 4. Lab 練習 I
	 * 路徑: /bmi?h=170&w=60
	 * 網址: http://localhost:8080/api/bmi?h=170&w=60
	 * 執行結果: bmi = 20.76
	 * */
	@GetMapping("/bmi")
	public String bmi(@RequestParam Double h, @RequestParam Double w) {
		double bmi = w / Math.pow(h/100, 2);
		// return String.format("bmi = %.2f", bmi);
		return """
				{
				 "bmi": %.2f
				}
				""".formatted(bmi); // 手動拼寫 json 格式
	}

	/**
	 * 5. 同名多筆的資料
	 * 路徑: /age?age=17&age=21&age=20
	 * 網址: http://localhost:8080/api/age?age=17&age=21&age=20
	 * 計算出平均年齡
	 */
	//@GetMapping("/age")
	@GetMapping(value = "/age", produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<Object>> getAverageOfAge(@RequestParam("age") List<String> ages) {
		double avgOfAge = ages.stream().mapToInt(Integer::parseInt).average().getAsDouble();
		Object data = Map.of("平均年齡", String.format("%.1f", avgOfAge));
		//return ResponseEntity.status(200).body(ApiResponse.success("查詢成功", data));
		return ResponseEntity.ok(ApiResponse.success("查詢成功", data));
	}
	
	/*
	 * 6. Lab 練習: 得到多筆 score 資料
	 * 路徑: "/exam?score=80&score=100&score=50&score=70&score=30"
	 * 網址: http://localhost:8080/api/exam?score=80&score=100&score=50&score=70&score=30
	 * 請自行設計一個方法，此方法可以
	 * 印出: 最高分=?、最低分=?、平均=?、總分=?、及格分數列出=?、不及格分數列出=?
	 * (支援中文字印出) 
	 * 提示: IntSummaryStatistics, Collectors.partitioningBy
	 * */
	@GetMapping(value = "/exam", produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<Object>> getExamInfo(@RequestParam("score") List<String> scores) {
		// 統計資料
		IntSummaryStatistics stat = scores.stream().mapToInt(Integer::parseInt).summaryStatistics();
		// 利用 Collectors.partitioningBy 分組
		// key=true 及格分數, key=false 不及格分數
		Map<Boolean, List<String>> resultMap = scores.stream()
													 .collect(Collectors.partitioningBy(score -> Integer.parseInt(score) >= 60)); 
		
		Object data = Map.of(
				"最高分", stat.getMax(), 
				"最低分", stat.getMin(),
				"平均", stat.getAverage(),
				"總分", stat.getSum(),
				"及格分數", resultMap.get(true),
				"不及格分數", resultMap.get(false)
				);
		
		return ResponseEntity.ok(ApiResponse.success("查詢成功", data));
		
	}
	
	/**
	 * 7. 多筆參數轉 Map
	 * name 書名(String), price 價格(Double), amount 數量(Integer), pub 出刊/停刊(Boolean)
	 * 路徑: /book?name=Math&price=12.5&amount=10&pub=true
	 * 路徑: /book?name=English&price=10.5&amount=20&pub=false
	 * 網址: http://localhost:8080/api/book?name=Math&price=12.5&amount=10&pub=true
	 * 網址: http://localhost:8080/api/book?name=English&price=10.5&amount=20&pub=false
	 * 自動會轉為 Map 集合
	 * */
	// @GetMapping("/book")
	public ResponseEntity<ApiResponse<Object>> getBookInfo(@RequestParam Map<String, Object> bookMap) {
		return ResponseEntity.ok(ApiResponse.success("查詢成功", bookMap));
	}
	
	/**
	 * 8. 多筆參數轉指定 Bean 物件
	 */
	@GetMapping("/book")
	public ResponseEntity<ApiResponse<Book>> getBookInfo(Book book) {
		return ResponseEntity.ok(ApiResponse.success("查詢成功", book));
	}
	
	/**
	 * 9. 路徑參數
	 * 路徑: /book/1
	 * 路徑: /book/3
	 * 網址: http://localhost:8080/api/book/1
	 * 網址: http://localhost:8080/api/book/3
	 */
	@GetMapping("/book/{id}")
	public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Integer id) {
		List<Book> books = List.of(
				new Book(1, "Math1", 12.5, 20, true),
				new Book(2, "Maisy", 13.5, 21, false),
				new Book(3, "The Wild", 14.5, 22, true),
				new Book(4, "Rain", 15.5, 23, false),
				new Book(5, "Gustavo", 16.5, 24, true));
		
		Optional<Book> optBook = books.stream().filter(b -> b.getId().equals(id)).findAny();
		
		if(optBook.isEmpty()) {
			throw new RuntimeException("查無此書");
		}
		
		return ResponseEntity.ok(ApiResponse.success("查詢成功", optBook.get()));
	}
	
	/**
	 * Lab: 請列出書本價格介於 13~17 之間且目前仍在版的書名
	 * 如何設計 GET API ?
	 * http://localhost:8080/api/book/pub/true?min=13&max=17
	 * http://localhost:8080/api/book/pub/false?min=13&max=17
	 * */
	@GetMapping("/book/pub/{pub}")
	public ResponseEntity<ApiResponse<List<String>>> queryBook(@PathVariable Boolean pub,
			@RequestParam Double min, @RequestParam Double max) {
		
		List<Book> books = List.of(
				new Book(1, "Math1", 12.5, 20, true),
				new Book(2, "Math2", 13.5, 21, false),
				new Book(3, "Math3", 14.5, 22, true),
				new Book(4, "Math4", 15.5, 23, false),
				new Book(5, "Math5", 16.5, 24, true));
		
		List<String> bookNames = books.stream().filter(b -> b.getPub())
											   .filter(b -> b.getPrice() >= min && b.getPrice() <= max)
											   .map(b -> b.getName())
											   .collect(Collectors.toList());
		
		if(bookNames.size() == 0) {
			throw new RuntimeException("此範圍查無任何書籍");
		}
		
		return ResponseEntity.ok(ApiResponse.success("查詢成功", bookNames));
	}
}
