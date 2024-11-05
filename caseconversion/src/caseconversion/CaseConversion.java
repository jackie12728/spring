package caseconversion;

import java.io.*;

public class CaseConversion {

	public static void main(String[] args) {
		boolean toUpper = false;
		if(args.length==0) {
			System.err.println("程式用法：java CaseConversion -U/L");
			System.exit(0);
		} else if(args[0].equalsIgnoreCase("-U")) {
			toUpper = true;
		} else if(args[0].equalsIgnoreCase("-L")) {
			toUpper = false;
		} else {
			System.err.println("程式用法：java CaseConversion -U/L");
			System.exit(0);
		}
			
		try(FileReader fr = new FileReader("source.txt");
			FileWriter fw = new FileWriter("result.txt")) {
			char[] input = new char[32];
			int count = 0;
			while((count=fr.read(input))>0) {
				String line = new String(input, 0, count);
				String output = "";
				if(toUpper)
					output = line.toUpperCase();
				else
					output = line.toLowerCase();
				fw.write(output);
			}
			fw.flush();
			System.out.println("檔案輸出成功！");
		} catch(IOException ex) {
			ex.printStackTrace();
		}

	}

}
