package com.pluskynet.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.pluskynet.dao.ArticleDao;


public class Bigtest {
	static ClassPathXmlApplicationContext resource = null;
	static ArticleDao articleDao = null;

	public static void main(String[] args) {
		resource = new ClassPathXmlApplicationContext("applicationContext.xml");
		articleDao = (ArticleDao) resource.getBean("articleDao");
		for (int i = 5; i >1 ; i--) {
			final int a = i;
			Thread thread = new Thread(){
					public void run() {
						articleDao.delete(a);
					}
			};
			thread.start();
		}
	}
}
