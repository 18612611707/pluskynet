package com.pluskynet.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pluskynet.action.DocsectionandruleAction;
import com.pluskynet.action.LatitudeauditAction;
import com.pluskynet.action.LatitudenumAction;
import com.pluskynet.action.PreviewAction;
import com.pluskynet.dao.ArticleDao;
import com.pluskynet.dao.BatchdataDao;
import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.DocidandruleidDao;
import com.pluskynet.dao.LatitudenumDao;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Batchdata;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.Docidandruleid;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.domain.Latitudenum;
import com.pluskynet.otherdomain.Otherdocrule;
import com.pluskynet.rule.DocRule;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 已分好表的的数据进行分段规则跑批
 */
@SuppressWarnings("all")
public class Bigdatatest extends Thread {
	static ClassPathXmlApplicationContext resource = null;
	static ArticleDao articleDao = null;
	static DocsectionandruleAction docrule = null;
	static BatchdataDao batchdataDao;
	static LatitudenumDao latitudenumDao;
	// 创建一个静态钥匙
	static Object ob = "aa";// 值是任意的
	volatile private int a = 0;
	static ThreadPoolExecutor executor = null;

	static List<Latitudeaudit> Lalist = null;
	static LatitudeauditAction latitudeauditAction;
	int allorre = -1;// 0：新增跑批，（ 3：二次跑批 ,5:再次跑批）
	public Bigdatatest(String name) {
		super(name);// 给线程起名字
	}

	public void main(int batchstats) {
		executor = new ThreadPoolExecutor(100, Integer.MAX_VALUE, 200, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		System.gc();
		resource = new ClassPathXmlApplicationContext("applicationContext.xml");
		latitudeauditAction = (LatitudeauditAction) resource.getBean("latitudeauditAction");
		latitudenumDao = (LatitudenumDao) resource.getBean("latitudenumDao");
		Lalist = latitudeauditAction.getLatitude(0);// 获取已审批过的规则
		if (batchstats == -1 ) {
			allorre = Integer.valueOf(Lalist.get(0).getBatchstats());
		}else{
			allorre = 0;
		}
		for (int i = 0; i < 40; i++) {
			Bigdatatest bigdatatest = new Bigdatatest("线程名称：" + i);
			bigdatatest.start();
			try {
				// 休息一分钟
				sleep(6000);
				// System.out.println("休息一分钟");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		articleDao = (ArticleDao) resource.getBean("articleDao");
		PreviewAction previewAction = (PreviewAction) resource.getBean("previewAction");
		docrule = (DocsectionandruleAction) resource.getBean("docsectionandruleAction");
		batchdataDao = (BatchdataDao) resource.getBean("batchdataDao");
		DocidandruleidDao docidandruleidDao = (DocidandruleidDao) resource.getBean("docidandruleidDao");
		
		if (Lalist.size() > 0) {
			CauseDao causeDao = (CauseDao) resource.getBean("causeDao");
			// List<Article01> list = causeDao.getArticleList();
			String doctable = null;
			List<Cause> list = null;
			list = getValue();
			if (list.size() == 0 || list == null) {
				list = causeDao.getArticleList(1);// 获取表名,0:民事 1:刑事
			}
			System.out.println(list.get(0).getCausename());
			List<Article01> articleList = null;
			for (int i = 0; i < list.size(); i++) {
				doctable = list.get(i).getDoctable();
				boolean runs = true;
				int rows = 2000;
				while (runs) {
					synchronized (ob) {
						System.out.println("线程名称：" + getName());
						articleList = articleDao.getArticle01List(list.get(i).getCausetable(), allorre, rows);// 获取文书列表
					}
					if (articleList.size() == 0) {
						System.out.println(list.get(i).getCausetable() + "表无数据！！！");
						runs = false;
						continue;
					}
					Bigdatasave bigdatasave[] = new Bigdatasave[Lalist.size()];
					for (int j = 0; j < Lalist.size(); j++) {// 循环已审批规则
						List<Otherdocrule> lists = new ArrayList<Otherdocrule>();
						DocRule docRule = new DocRule();
						lists = docRule.ruleFormat(JSONArray.fromObject(Lalist.get(j).getRule())); // 规则整理
						int ruleid = Lalist.get(j).getLatitudeid();
						String latitudename = Lalist.get(j).getLatitudename();
						String startword = null;
						String endword = null;
						String judges = null;
						List<Article01> articleLists = articleList;
						bigdatasave[j] = new Bigdatasave();
						bigdatasave[j].save(articleLists, articleDao, batchdataDao, docrule, docidandruleidDao,
								list.get(i).getDoctable(), lists, list.get(i).getCausename(),
								list.get(i).getCausetable(), ruleid, latitudename);
						bigdatasave[j].setName("线程名称:" + getName() + "," + "规则线程：" + i + j);
						System.out.println(bigdatasave[j].getName());
						// executor.execute(bigdatasave[j]);
						// if(j==0){
						// System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
						// executor.getQueue().size()+"，已执行完任务数目："+executor.getCompletedTaskCount());
						// }
						bigdatasave[j].start();
						if (i == list.size() - 1) {
						for (int k = 0; k < Lalist.size(); k++) {
							if (allorre == 3) {
								Lalist.get(k).setBatchstats("5");
							}else{
								Lalist.get(k).setBatchstats("3");
							}
							Lalist.get(k).setStats("3");
						}	
						}
					}
					for (int j = 0; j < bigdatasave.length; j++) {
						try {
							bigdatasave[j].join();
							System.out.println("线程名称:" + getName() + "," + bigdatasave[j] + "结束");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			latitudeauditAction.updatebatchestats(Lalist);
		} else {
			System.out.println("无规则");
		}
		latitudenumDao.countlat(0);
	}

	public static List<Cause> getValue() {
		List<Cause> list = new ArrayList<Cause>();
		File directory = new File("");// 设定为当前文件夹
		try {
			File f = new File(directory.getAbsolutePath());
			System.out.println(f.getParent());// 获取绝对路径
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream("C:/users/administrator/bigdata.csv"), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] lines = line.split(";");
				for (int i = 0; i < lines.length; i++) {
					String[] tiao = lines[i].split(",");
					for (int b = 0; b < 1; b++) {
						Cause cause = new Cause();
						cause.setCausename(tiao[0].substring(1, tiao[0].length()));
						cause.setCausetable(tiao[1]);
						cause.setDoctable(tiao[2]);
						list.add(cause);
					}
				}
			}
		} catch (Exception e) {

			// TODO

		}
		return list;
	}

}
