package com.example;

import java.util.*;
import java.util.stream.Collectors;

public class StreamOperationTest {

    public static void main(String[] args) {        
        List<Person> personList = Person.createList();
        
        // 顯示台北成員的性別稱謂, 以map()方法將性別轉換為先生或小姐
        
        // 問候所有台南的成員(你好, Sean 先生), 以peek()方法在性別稱謂前列印問候語
        
        // 使用findFirst()方法取得第一個住台南的年齡小於35歲的女性,
        
        // 南部成員個數
        
        //取得年紀最大的成員
        
        //取得年紀最小的成員
        
        //取得成員年紀總和
        
        //取得成員年紀平均    
        
        //女性成員排序
        
        //男性成員依城市排序
        
        //所有成員依年紀反向排序
        
        //所有成員依城市->年紀兩階段排序
                
        //以收集器取得將女性成員排序後轉為新序列
        
        //以收集器取得所有台北成員的電話序列
        
        //收集器產生計算台南成員平均年紀
        
        //收集器取得所有台北成員email字串用,隔開 
        
        //成員依城市分組
        
        //成員依城市計數
        
        //成員依年齡分組
        
    }           
}
