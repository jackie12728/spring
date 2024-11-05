package com.example;

import java.util.List;
import java.util.function.*;

public class LambdaBuildInTest {

    public static void main(String[] args) {
        List<Person> personList = Person.createList();
        
        // 使用 Lambda Expression 定義以Function 函式介面,傳回稱謂(姓名前加上Ms./Mr.)
        Function<Person, String> nameFormat = p->(p.getGender()==Gender.MALE?"Mr. ":"Ms. ")+p.getLastName()
        for(Person p:personList)
        	System.out.println(nameFormat.apply(p));
        
        
        
        // 使用 Lambda Expression 定義Predicate 函式介面,篩選列印30歲以下的Person資訊
        Predicate<Person> youth = p->p.getAge()<30;
        for(Person p:personList)
        	if(youth.test(p))
        		System.out.println(p);
        
        
        // 使用 Lambda Expression 定義以Comsumer 函式介面以FirstName(age)格式來列印Person資訊           
        Consumer<Person> myPrint = p->System.out.printf("%s(%d)%n", p.getFirstName(), p.getAge());
        for(Person p:personList)
        	myPrint.accept(p);
        
        
        
        
        
    }
    
}
