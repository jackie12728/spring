package com.example;

import java.io.*;

public class ExceptionMain {

	public static void main(String[] args) {
		try {
			System.out.println("Reading from file:" + args[0]);
		} catch(ArrayIndexOutOfBoundsException ex) {
			System.err.println("檔名未輸入，程式結束！");
			System.exit(0);
		}
		
		try(BufferedReader b = new BufferedReader(new FileReader(args[0]))) {
			String s = null;
			while((s = b.readLine()) != null) {
			System.out.println(s);
			}
		} catch(FileNotFoundException ex) {
			System.err.println("檔名稱錯誤，程式結束！");
		} catch(IOException ex) {
			System.err.println("檔案讀取失敗，程式結束！");
		}
		
	}

}
