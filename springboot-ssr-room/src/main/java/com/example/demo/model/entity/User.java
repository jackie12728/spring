package com.example.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// users 資料表的紀錄
/**
 * -- 建立 users 資料表
		create table if not exists web.users (
		user_id int primary key auto_increment comment '使用者ID',
	    username varchar(50) not null unique comment '使用者名稱',
	    password_hash varchar(255) not null comment '使用者Hash密碼',
	    salt varchar(255) not null comment '隨機鹽',
	    email varchar(255) comment '電子郵件',
	    active boolean default false comment '帳號啟動',
	    role varchar(50) not null default 'ROLE_USER' comment '角色權限'
	);
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // 實體類與資料表對應(會自動建立資料表)
@Table(name = "users") // 若資料表名與實體類一致可以不用設定此行
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId; // 使用者ID
	
	@Column(name = "username")
	private String username; // 使用者名稱
	
	@Column(name = "password_hash")
	private String passwordHash; // 使用者Hash密碼
	
	@Column(name = "salt")
	private String salt; // 隨機鹽
	
	@Column(name = "email")
	private String email; // 電子郵件
	
	@Column(name = "active")
	private Boolean active; // 帳號啟動
	
	@Column(name = "role")
	private String role; // 角色權限
		
}
