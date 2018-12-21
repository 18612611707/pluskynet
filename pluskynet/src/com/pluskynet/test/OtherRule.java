package com.pluskynet.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.pluskynet.action.LatitudeAction;
import com.pluskynet.action.LatitudeauditAction;
import com.pluskynet.dao.BatchdataDao;
import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.DocidandruleidDao;
import com.pluskynet.dao.LatitudeKeyDao;
import com.pluskynet.dao.LatitudenumDao;
import com.pluskynet.dao.LatitudetimeDao;
import com.pluskynet.dao.LatitudewordDao;
import com.pluskynet.domain.Batchdata;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.Docidandruleid;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Docsectionandrule01;
import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.domain.LatitudedocKey;
import com.pluskynet.domain.LatitudedocTime;
import com.pluskynet.domain.LatitudedocWord;
import com.pluskynet.otherdomain.Otherrule;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OtherRule extends Thread {
	static ClassPathXmlApplicationContext resource = null;
	static BatchdataDao batchdataDao = null;
	// 创建一个静态钥匙
	static Object ob = "aa";// 值是任意的
	static ThreadPoolExecutor executor = null;
	static List<Latitudeaudit> Lalist = null;
	static LatitudeauditAction latitudeauditAction = null;

	public OtherRule(String name) {
		super(name);// 给线程起名字
	}

	public static void main(String[] args) {
		System.gc();
		resource = new ClassPathXmlApplicationContext("applicationContext.xml");
		latitudeauditAction = (LatitudeauditAction) resource.getBean("latitudeauditAction");
		int batchstats = 1;// 1:已审批规则 
		Lalist = latitudeauditAction.getLatitude(String.valueOf(batchstats), 1);// 获取已审批过的规则
		if(Lalist.size()==0){
			System.out.println("无规则");
			return;
		}
		for (int i = 0; i < 60; i++) {
			OtherRule otherrule = new OtherRule("线程名称：" + i);
			otherrule.start();
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
		CauseDao causeDao = (CauseDao) resource.getBean("causeDao");
		batchdataDao = (BatchdataDao) resource.getBean("batchdataDao");
		LatitudeAction latitudeAction = (LatitudeAction) resource.getBean("latitudeAction");
		DocSectionAndRuleDao docSectionAndRuleDao = (DocSectionAndRuleDao) resource.getBean("docSectionAndRuleDao");
		LatitudeKeyDao latitudeKeyDao = (LatitudeKeyDao) resource.getBean("latitudeKeyDao");
		LatitudewordDao latitudewordDao = (LatitudewordDao) resource.getBean("latitudeWordDao");
		LatitudetimeDao latitudetimeDao = (LatitudetimeDao) resource.getBean("latitudeTimeDao");
		DocidandruleidDao docidandruleidDao = (DocidandruleidDao) resource.getBean("docidandruleidDao");
		LatitudenumDao latitudenumDao = (LatitudenumDao) resource.getBean("latitudenumDao");
		List<Cause> Causelists = causeDao.getArticleList(1);// 获取表名,0:民事 1:刑事
		List<Docsectionandrule01> docsectionandrulelist = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		for (int i = 0; i < Causelists.size(); i++) {
			do {
				int rows = 2000;
				int state = 5;//0和5状态改变为3:新增跑批状态  3、5循环跑批
				synchronized (ob) {
					System.out.println("线程名称：" + getName()+"开始取数据;"+df.format(new Date())+"");
					docsectionandrulelist = docSectionAndRuleDao.listdoc(Causelists.get(i).getDoctable(), rows,state);
					System.out.println("线程名称：" + getName()+"结束取数据;"+df.format(new Date())+"");
				}
				if (docsectionandrulelist.size()==0) {
					System.out.println(Causelists.get(i).getDoctable() + "表无数据！！！");
					continue;
				}
				OtherRuleSave otherRuleSave[] = new OtherRuleSave[Lalist.size()];
				for (int j = 0; j < Lalist.size(); j++) {
					latitudeAction.setLatitudeId(Lalist.get(j).getLatitudeid());
					Latitude latitude = latitudeAction.getLatitudes();
					List<Otherrule> list = ruleFormat(latitude.getRule(), latitude.getRuletype());// 规则整理
					otherRuleSave[j] = new OtherRuleSave();
					otherRuleSave[j].save(list, docsectionandrulelist, latitude, Lalist.get(j).getLatitudename(),
							Lalist.get(j).getLatitudeid(), latitudeKeyDao, batchdataDao, docidandruleidDao);
					otherRuleSave[j].setName("线程名称:"+getName()+","+"规则线程：" + i + j);
					System.out.println(otherRuleSave[j].getName());
					otherRuleSave[j].start();
					if (i == Lalist.size() - 1) {
						Lalist.get(j).setBatchstats("3");
						latitudeauditAction.updatebatchestats(Lalist.get(j));
					}
				}
				for (int j1 = 0; j1 < otherRuleSave.length; j1++) {
					try {
						otherRuleSave[j1].join();
						System.out.println("线程名称:" + getName() + "," + otherRuleSave[j1] + "结束");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} while (docsectionandrulelist.size() > 0);
		}
		latitudenumDao.countlat(0);
	}

	static List<Otherrule> ruleFormat(String rule, int ruletype) {
		JSONArray jsonArray = JSONArray.fromObject(rule);
		List<Otherrule> list = new ArrayList<Otherrule>();
		for (int i = 0; i < jsonArray.size(); i++) {
			Otherrule otherrule = new Otherrule();
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			if (ruletype == 1) {
				otherrule.setSectionname(jsonObject.getString("sectionname"));
				otherrule.setCond(jsonObject.getString("cond"));
				otherrule.setContains(jsonObject.getString("contains"));
				otherrule.setNocond(jsonObject.getString("nocond"));
				otherrule.setNotcon(jsonObject.getString("notcon"));
				list.add(otherrule);
			} else if (ruletype == 2) {
				otherrule.setSectionname(jsonObject.getString("sectionname"));
				otherrule.setStart(jsonObject.getString("beginText"));
				otherrule.setEnd(jsonObject.getString("endText"));
				list.add(otherrule);
			} else if (ruletype == 3) {
				otherrule.setSectionname(jsonObject.getString("sectionname"));
				otherrule.setNum(jsonObject.getInt("num"));
				otherrule.setTimeformat(jsonObject.getInt("timeformat"));
				list.add(otherrule);
			}
		}
		return list;
	}
}
