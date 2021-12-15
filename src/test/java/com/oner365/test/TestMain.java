package com.oner365.test;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 测试类
 * 
 * @author zhaoyong
 *
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
    	List<String> list = Lists.newArrayList();
    	list.add("1");
    	List<String> list1 = list;
    	list1.stream().forEach(s ->{
    		s = "2";
    	});
        list.stream().forEach(s ->{
        	System.out.println(s);
        });
        list1.stream().forEach(s ->{
        	System.out.println(s);
    	});
    }
    

}
