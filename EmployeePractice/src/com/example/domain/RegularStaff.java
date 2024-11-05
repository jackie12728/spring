package com.example.domain;

import java.util.Random;

public interface RegularStaff {
	public static final String[] gifts = {"提拉米蘇", "10000元現金", "Google Pixel 9 Pro", "HP 14吋OLED翻轉觸控筆電", "2R 手工真皮", "銘謝惠顧"};
	
	public static String getLuckDraw() {
		int luck = new Random().nextInt(gifts.length);
		return gifts[luck];
	}
	
	public default double calcPerMultiplier() {
		return (Math.random()*5+1);
	}
	
	public double getBonus();
}
