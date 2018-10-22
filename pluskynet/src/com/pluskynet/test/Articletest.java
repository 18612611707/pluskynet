package com.pluskynet.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pluskynet.action.ArticleAction;
/*
 * 清洗好的数据分表
 */
public class Articletest extends Thread {
	// 创建一个静态钥匙
		static Object ob = "aa";// 值是任意的

		public Articletest(String name) {
			super(name);// 给线程起名字
		}
	static ArticleAction articleAction ;
	@Autowired
	public static void main(String[] args) {
//		ClassPathXmlApplicationContext resource = new ClassPathXmlApplicationContext("applicationContext.xml");
//		articleAction = (ArticleAction) resource.getBean("articleAction");
//		String data = getValue("data");
//		System.out.println(data);
//		int i = 0;
//		do {
//			i = articleAction.BreakArticle(data, 200);
//			System.out.println(i);
//		} while (i > 0);
		for (int i = 0; i <50; i++) {
			Articletest articletest1 = new Articletest("线程名称："+i);
			articletest1.start();
		}
	}
	public void run() {
		ClassPathXmlApplicationContext resource = new ClassPathXmlApplicationContext("applicationContext.xml");
		articleAction = (ArticleAction) resource.getBean("articleAction");
		String data = getValue("data");
		System.out.println(data);
		int i = 0;
		do {
			System.out.println("线程名："+getName());
			i = articleAction.BreakArticle(data, 2000,ob);
			System.out.println(i);
		} while (i > 0);
	}


	public static String getValue(String key) {
		Properties prop = new Properties();
		File directory = new File("");// 设定为当前文件夹
		try {
			File f = new File(directory.getAbsolutePath());
			System.out.println(f.getParent());// 获取绝对路径

			InputStream in = new BufferedInputStream(new FileInputStream(f.getParent() + "/dbCfg.properties"));
			try {
				prop.load(in);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {

			// TODO

		}
		return prop.getProperty(key);
	}

}
