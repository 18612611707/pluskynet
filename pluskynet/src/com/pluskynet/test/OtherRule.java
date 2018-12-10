package com.pluskynet.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.pluskynet.action.LatitudeAction;
import com.pluskynet.action.LatitudeauditAction;
import com.pluskynet.dao.BatchdataDao;
import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.DocidandruleidDao;
import com.pluskynet.dao.LatitudeKeyDao;
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
		List<Cause> Causelists = causeDao.getArticleList(1);// 获取表名,0:民事 1:刑事
		List<Docsectionandrule01> docsectionandrulelist = null;
		for (int i = 0; i < Causelists.size(); i++) {
			do {
				int rows = 2000;
				int state = 5;//0和5状态改变为3:新增跑批状态  3、5循环跑批
				synchronized (ob) {
					System.out.println("线程名称：" + getName()+"开始取数据");
					docsectionandrulelist = docSectionAndRuleDao.listdoc(Causelists.get(i).getDoctable(), rows,state);
					System.out.println("线程名称：" + getName()+"结束取数据");
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

		// for (int i = 0; i < Lalist.size(); i++) {
		// latitudeAction.setLatitudeId(Lalist.get(i).getLatitudeid());
		// Latitude latitude = latitudeAction.getLatitudes();
		// List<Otherrule> list = ruleFormat(latitude.getRule(),
		// latitude.getRuletype());// 规则整理
		//
		// for (int j = 0; j < list.size(); j++) {
		// String sectionname = list.get(j).getSectionname();
		// int num = list.get(j).getNum();
		// int timeformat = list.get(j).getTimeformat();
		// for (int j2 = 0; j2 < Causelists.size(); j2++) {
		// // docsectionandruleAction.update(Causelists.get(j2).getDoctable(),
		// // sectionname);//需要重新跑批的数据状态改为0
		//
		// do {
		//
		// for (int k = 0; k < docsectionandrulelist.size(); k++) {
		// String sectiontext = docsectionandrulelist.get(k).getSectiontext();
		// if (latitude.getRuletype() == 1) {
		// String[] contain = list.get(j).getContains().split(";");// 对包含词语拆分
		// String[] notcon = list.get(j).getNotcon().split(";");// 对不包含词语拆分
		// for (int l = 0; l < contain.length; l++) {
		// if (sectiontext.contains(contain[l])) {
		// for (int l2 = 0; l2 < notcon.length; l2++) {
		// if (!sectiontext.contains(notcon[l2])) {
		// LatitudedocKey latitudedocKey = new LatitudedocKey();
		// latitudedocKey
		// .setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
		// latitudedocKey.setLatitudename(Lalist.get(i).getLatitudename());
		// latitudedocKey.setLatitudeid(Lalist.get(j).getLatitudeid());
		// latitudeKeyDao.save(latitudedocKey);
		// Batchdata batchdata = new Batchdata();
		// batchdata.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
		// batchdata.setRuleid(Lalist.get(i).getLatitudeid());
		// batchdata.setContain(contain[l]);
		// batchdata.setNotcon(notcon[l2]);
		// batchdataDao.save(batchdata);
		// Docidandruleid docidandruleid = new Docidandruleid(
		// docsectionandrulelist.get(k).getDocumentsid(),
		// Lalist.get(i).getLatitudeid());
		// docidandruleidDao.save(docidandruleid);
		// }
		// }
		// }
		// }
		// } else if (latitude.getRuletype() == 2) {
		// String[] startWords = list.get(j).getStart().split(";");// 对开始词语进行拆分
		// String[] endWords = list.get(j).getEnd().split(";");// 对结束词语进行拆分
		// String docid = docsectionandrulelist.get(k).getDocumentsid();
		// String docold = docsectionandrulelist.get(k).getSectiontext();
		// String docnew = null;
		// int start = -1;
		// int end = -1;
		// String leftdoc = null;
		// String rightdoc = null;
		// String beginIndex1 = null;
		// String startword = null;
		// String endword = null;
		// for (int j3 = 0; j3 < startWords.length; j3++) {
		// Pattern patternstart = startRuleFomat(startWords[j3]);
		// Matcher matcher = patternstart.matcher(docold);
		// if (matcher.find()) {
		// beginIndex1 = matcher.group();
		// startword = startWords[j3];
		// start = docold.indexOf(beginIndex1);
		// leftdoc = docold.substring(0,
		// docold.indexOf(beginIndex1) + beginIndex1.length());
		// rightdoc = docold.substring(docold.indexOf(beginIndex1) +
		// beginIndex1.length());
		// break;
		// }
		// }
		// if (rightdoc != null && start != -1) {
		// for (int x = 0; x < endWords.length; x++) {
		// Pattern patternend = endRuleFomat(endWords[x]);
		// Matcher matcher = patternend.matcher(rightdoc);
		// if (matcher.find()) {
		// String beginIndex = matcher.group();
		// endword = endWords[x];
		// if (endWords[x].length() > 0) {
		// end = start + rightdoc.indexOf(beginIndex) + beginIndex1.length();
		// } else {
		// end = docold.length();
		// }
		// break;
		// }
		// }
		//
		// if (end != -1) {
		// docnew = docold.substring(start, end);
		// LatitudedocWord latitudedocWord = new LatitudedocWord();
		// latitudedocWord.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
		// latitudedocWord.setLatitudename(Lalist.get(j).getLatitudename());
		// latitudedocWord.setLatitudeid(Lalist.get(j).getLatitudeid());
		// latitudewordDao.save(latitudedocWord);
		// Batchdata batchdata = new Batchdata();
		// batchdata.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
		// batchdata.setStartword(startword);
		// batchdata.setEndword(endword);
		// batchdataDao.save(batchdata);
		// Docidandruleid docidandruleid = new Docidandruleid(
		// docsectionandrulelist.get(k).getDocumentsid(),
		// Lalist.get(i).getLatitudeid());
		// docidandruleidDao.save(docidandruleid);
		// break;
		// } else if (end == 0) {
		// docnew = docold.substring(start, docold.length());
		// LatitudedocWord latitudedocWord = new LatitudedocWord();
		// latitudedocWord.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
		// latitudedocWord.setLatitudename(Lalist.get(j).getLatitudename());
		// latitudedocWord.setLatitudeid(Lalist.get(j).getLatitudeid());
		// latitudewordDao.save(latitudedocWord);
		// Batchdata batchdata = new Batchdata();
		// batchdata.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
		// batchdata.setStartword(startword);
		// batchdata.setEndword(endword);
		// batchdataDao.save(batchdata);
		// Docidandruleid docidandruleid = new Docidandruleid(
		// docsectionandrulelist.get(k).getDocumentsid(),
		// Lalist.get(i).getLatitudeid());
		// docidandruleidDao.save(docidandruleid);
		// break;
		// }
		//
		// }
		// } else {
		// Pattern patPunc = null;
		// if (num != 0) {
		// patPunc = Pattern
		// .compile("[\u4e00-\u9fa5]{0,4}年[\u4e00-\u9fa5]{0,2}月[\u4e00-\u9fa5]{0,2}日");
		// } else {
		// patPunc = Pattern.compile("[0-9]{0,4}年[0-9]{0,2}月[0-9]{0,2}日");
		// }
		// Matcher matcher = patPunc.matcher(sectiontext);
		// while (matcher.find()) {
		// String time = matcher.group();
		// timeformat--;
		// if (timeformat == 0) {
		// LatitudedocTime latitudedocTime = new LatitudedocTime();
		// latitudedocTime.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
		// latitudedocTime.setLatitudename(Lalist.get(j).getLatitudename());
		// latitudedocTime.setLatitudeid(Lalist.get(j).getLatitudeid());
		// latitudedocTime.setLatitudetime(time);
		// latitudetimeDao.save(latitudedocTime);
		// Batchdata batchdata = new Batchdata();
		// batchdata.setNums(num);
		// batchdataDao.save(batchdata);
		// Docidandruleid docidandruleid = new Docidandruleid(
		// docsectionandrulelist.get(k).getDocumentsid(),
		// Lalist.get(i).getLatitudeid());
		// docidandruleidDao.save(docidandruleid);
		// }
		//
		// }
		// }
		// }
		// } while (docsectionandrulelist.size() > 0);
		// }
		// }
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

	public static Pattern startRuleFomat(String startWords) {
		String reg_charset = null;
		String[] start = startWords.split("\\*");
		if (start.length > 1) {
			for (int j = 0; j < start.length; j++) {
				if (reg_charset == null) {
					reg_charset = start[j];
				} else {
					reg_charset = reg_charset + "([\u4e00-\u9fa5_×Ｘa-zA-Z0-9_|\\pP]{0,50})" + start[j];
				}
			}
		} else {
			reg_charset = startWords;
		}
		Pattern pattern = Pattern.compile(reg_charset);
		return pattern;
	}

	// 结束规则格式化
	public static Pattern endRuleFomat(String endWords) {
		String reg_charset = null;
		String[] end = endWords.split("\\*");
		for (int j = 0; j < end.length; j++) {
			if (end.length > 1) {
				if (reg_charset == null) {
					reg_charset = end[j];
				} else {
					reg_charset = reg_charset + "([\u4e00-\u9fa5_×Ｘa-zA-Z0-9_|\\pP]{0,50})" + end[j];
				}
			} else {
				reg_charset = end[j];
			}
		}
		Pattern pattern = Pattern.compile(reg_charset);
		return pattern;
	}

}
