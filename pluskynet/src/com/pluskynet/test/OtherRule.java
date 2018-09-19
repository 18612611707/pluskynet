package com.pluskynet.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.language.bm.RuleType;
import org.apache.struts2.components.ElseIf;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pluskynet.action.DocsectionandruleAction;
import com.pluskynet.action.LatitudeAction;
import com.pluskynet.action.LatitudeauditAction;
import com.pluskynet.action.PreviewAction;
import com.pluskynet.dao.ArticleDao;
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
import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.domain.LatitudedocKey;
import com.pluskynet.domain.LatitudedocTime;
import com.pluskynet.domain.LatitudedocWord;
import com.pluskynet.otherdomain.Otherrule;
import com.pluskynet.service.LatitudeService;

import freemarker.core.ReturnInstruction.Return;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OtherRule {

	static ClassPathXmlApplicationContext resource = null;
	static BatchdataDao batchdataDao;

	public static void main(String[] args) {
		resource = new ClassPathXmlApplicationContext("applicationContext.xml");
		LatitudeauditAction latitudeauditAction = (LatitudeauditAction) resource.getBean("latitudeauditAction");
		batchdataDao = (BatchdataDao) resource.getBean("batchdataDao");
		LatitudeAction latitudeAction = (LatitudeAction) resource.getBean("latitudeAction");
		CauseDao causeDao = (CauseDao) resource.getBean("causeDao");
		DocSectionAndRuleDao docSectionAndRuleDao = (DocSectionAndRuleDao) resource.getBean("docSectionAndRuleDao");
		DocsectionandruleAction docsectionandruleAction = (DocsectionandruleAction) resource.getBean("docsectionandruleAction");
		LatitudeKeyDao latitudeKeyDao = (LatitudeKeyDao) resource.getBean("latitudeKeyDao");
		LatitudewordDao latitudewordDao = (LatitudewordDao) resource.getBean("latitudeWordDao");
		LatitudetimeDao latitudetimeDao = (LatitudetimeDao) resource.getBean("latitudeTimeDao");
		DocidandruleidDao docidandruleidDao = (DocidandruleidDao) resource.getBean("docidandruleidDao");
		int batchstats = 1;// 1:已审批规则 2:剩余跑批规则
		List<Latitudeaudit> Lalist = latitudeauditAction.getLatitude(String.valueOf(batchstats), 1);// 获取已审批过的规则
		List<Cause> Causelists = causeDao.getArticleList();// 获取表名
		for (int i = 0; i < Lalist.size(); i++) {
			latitudeAction.setLatitudeId(Lalist.get(i).getLatitudeid());
			Latitude latitude = latitudeAction.getLatitudes();
			List<Otherrule> list = ruleFormat(latitude.getRule(), latitude.getRuletype());// 规则整理
			int rows = 10000;
			for (int j = 0; j < list.size(); j++) {
				String sectionname = list.get(j).getSectionname();
				int num = list.get(j).getNum();
				int timeformat = list.get(j).getTimeformat();
				for (int j2 = 0; j2 < Causelists.size(); j2++) {
					docsectionandruleAction.update(Causelists.get(j2).getDoctable(), sectionname);
					List<Docsectionandrule> docsectionandrulelist = null;
					do {
						docsectionandrulelist = docSectionAndRuleDao.listdoc(Causelists.get(j2).getDoctable(), rows,
								sectionname);
						for (int k = 0; k < docsectionandrulelist.size(); k++) {
							String sectiontext = docsectionandrulelist.get(k).getSectiontext();
							if (latitude.getRuletype() == 1) {
								String[] contain = list.get(j).getContains().split(";");// 对包含词语拆分
								String[] notcon = list.get(j).getNotcon().split(";");// 对不包含词语拆分
								for (int l = 0; l < contain.length; l++) {
									if (sectiontext.contains(contain[l])) {
										for (int l2 = 0; l2 < notcon.length; l2++) {
											if (!sectiontext.contains(notcon[l2])) {
												LatitudedocKey latitudedocKey = new LatitudedocKey();
												latitudedocKey
														.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
												latitudedocKey.setLatitudename(Lalist.get(i).getLatitudename());
												latitudedocKey.setLatitudeid(Lalist.get(j).getLatitudeid());
												latitudeKeyDao.save(latitudedocKey);
												Batchdata batchdata = new Batchdata();
												batchdata.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
												batchdata.setRuleid(Lalist.get(i).getLatitudeid());
												batchdata.setContain(contain[l]);
												batchdata.setNotcon(notcon[l2]);
												batchdataDao.save(batchdata);
												Docidandruleid docidandruleid = new Docidandruleid(docsectionandrulelist.get(k).getDocumentsid(),Lalist.get(i).getLatitudeid());
												docidandruleidDao.save(docidandruleid);
											}
										}
									}
								}

							} else if (latitude.getRuletype() == 2) {
								String[] startWords = list.get(j).getStart().split(";");// 对开始词语进行拆分
								String[] endWords = list.get(j).getEnd().split(";");// 对结束词语进行拆分
								String docid = docsectionandrulelist.get(k).getDocumentsid();
								String docold = docsectionandrulelist.get(k).getSectiontext();
								String docnew = null;
								int start = -1;
								int end = -1;
								String leftdoc = null;
								String rightdoc = null;
								String beginIndex1 = null;
								String startword = null;
								String endword = null;
								for (int j3 = 0; j3 < startWords.length; j3++) {
									Pattern patternstart = startRuleFomat(startWords[j3]);
									Matcher matcher = patternstart.matcher(docold);
									if (matcher.find()) {
										beginIndex1 = matcher.group();
										startword = startWords[j3];
										start = docold.indexOf(beginIndex1);
										leftdoc = docold.substring(0,
												docold.indexOf(beginIndex1) + beginIndex1.length());
										rightdoc = docold.substring(docold.indexOf(beginIndex1) + beginIndex1.length());
										break;
									}
								}
								if (rightdoc != null && start != -1) {
									for (int x = 0; x < endWords.length; x++) {
										Pattern patternend = endRuleFomat(endWords[x]);
										Matcher matcher = patternend.matcher(rightdoc);
										if (matcher.find()) {
											String beginIndex = matcher.group();
											endword = endWords[x];
											if (endWords[x].length() > 0) {
												end = start + rightdoc.indexOf(beginIndex) + beginIndex1.length();
											} else {
												end = docold.length();
											}
											break;
										}
									}
								
								if (end != -1) {
									docnew = docold.substring(start, end);
									LatitudedocWord latitudedocWord = new LatitudedocWord();
									latitudedocWord.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
									latitudedocWord.setLatitudename(Lalist.get(j).getLatitudename());
									latitudedocWord.setLatitudeid(Lalist.get(j).getLatitudeid());
									latitudewordDao.save(latitudedocWord);
									Batchdata batchdata = new Batchdata();
									batchdata.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
									batchdata.setStartword(startword);
									batchdata.setEndword(endword);
									batchdataDao.save(batchdata);
									Docidandruleid docidandruleid = new Docidandruleid(docsectionandrulelist.get(k).getDocumentsid(),Lalist.get(i).getLatitudeid());
									docidandruleidDao.save(docidandruleid);
									break;
								} else if (end == 0) {
									docnew = docold.substring(start, docold.length());
									LatitudedocWord latitudedocWord = new LatitudedocWord();
									latitudedocWord.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
									latitudedocWord.setLatitudename(Lalist.get(j).getLatitudename());
									latitudedocWord.setLatitudeid(Lalist.get(j).getLatitudeid());
									latitudewordDao.save(latitudedocWord);
									Batchdata batchdata = new Batchdata();
									batchdata.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
									batchdata.setStartword(startword);
									batchdata.setEndword(endword);
									batchdataDao.save(batchdata);
									Docidandruleid docidandruleid = new Docidandruleid(docsectionandrulelist.get(k).getDocumentsid(),Lalist.get(i).getLatitudeid());
									docidandruleidDao.save(docidandruleid);
									break;
								}
								
								}
							} else {
								Pattern patPunc = null;
								if (num != 0) {
									patPunc = Pattern
											.compile("[\u4e00-\u9fa5]{0,4}年[\u4e00-\u9fa5]{0,2}月[\u4e00-\u9fa5]{0,2}日");
								} else {
									patPunc = Pattern.compile("[0-9]{0,4}年[0-9]{0,2}月[0-9]{0,2}日");
								}
								Matcher matcher = patPunc.matcher(sectiontext);
								while (matcher.find()) {
									String time = matcher.group();
									timeformat--;
									if (timeformat == 0) {
										LatitudedocTime latitudedocTime = new LatitudedocTime();
										latitudedocTime.setDocumentid(docsectionandrulelist.get(k).getDocumentsid());
										latitudedocTime.setLatitudename(Lalist.get(j).getLatitudename());
										latitudedocTime.setLatitudeid(Lalist.get(j).getLatitudeid());
										latitudedocTime.setLatitudetime(time);
										latitudetimeDao.save(latitudedocTime);
										Batchdata batchdata = new Batchdata();
										batchdata.setNums(num);
										batchdataDao.save(batchdata);
										Docidandruleid docidandruleid = new Docidandruleid(docsectionandrulelist.get(k).getDocumentsid(),Lalist.get(i).getLatitudeid());
										docidandruleidDao.save(docidandruleid);
									}

								}
							}
						}
					} while (docsectionandrulelist.size() > 0);
				}
			}
			Latitudeaudit latitudeaudit = new Latitudeaudit();
			latitudeaudit.setBatchstats("3");
			latitudeauditAction.updatebatchestats(latitudeaudit);
		}
	}

	static List<Otherrule> ruleFormat(String rule, int ruletype) {
		JSONArray jsonArray = JSONArray.fromObject(rule);
		List<Otherrule> list = new ArrayList<Otherrule>();
		for (int i = 0; i < jsonArray.size(); i++) {
			Otherrule otherrule = new Otherrule();
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			if (ruletype == 1) {
				if (i == 0) {
					otherrule.setSectionname(jsonObject.getString("sectionname"));
					otherrule.setCond(jsonObject.getString("cond"));
					otherrule.setContains(jsonObject.getString("contains"));
					otherrule.setNocond(jsonObject.getString("nocond"));
					otherrule.setNotcon(jsonObject.getString("notcon"));
					list.add(otherrule);
				} else {
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).getSectionname().equals(jsonObject.getString("sectionname"))) {
							if (!jsonObject.getString("contains").equals("")) {
								list.get(j).setContains(
										list.get(j).getContains() + ";" + jsonObject.getString("contains"));
							}
							if (!jsonObject.getString("notcon").equals("")) {
								list.get(j).setNotcon(list.get(j).getNotcon() + ";" + jsonObject.getString("notcon"));
							}
						} else {
							otherrule.setSectionname(jsonObject.getString("sectionname"));
							otherrule.setCond(jsonObject.getString("cond"));
							otherrule.setContains(jsonObject.getString("contains"));
							otherrule.setNocond(jsonObject.getString("nocond"));
							otherrule.setNotcon(jsonObject.getString("notcon"));
							list.add(otherrule);
						}
					}
				}
			} else if (ruletype == 2) {
				if (i == 0) {
					otherrule.setSectionname(jsonObject.getString("sectionname"));
					otherrule.setStart(jsonObject.getString("beginText"));
					otherrule.setEnd(jsonObject.getString("endText"));
					list.add(otherrule);
				} else {
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).getSectionname().equals(jsonObject.getString("sectionname"))) {
							if (!jsonObject.getString("beginText").equals("")) {
								list.get(j).setStart(list.get(j).getStart() + ";" + jsonObject.getString("beginText"));
							}
							if (!jsonObject.getString("endText").equals("")) {
								list.get(j).setEnd(list.get(j).getEnd() + ";" + jsonObject.getString("endText"));
							}
						} else {
							otherrule.setSectionname(jsonObject.getString("sectionname"));
							otherrule.setStart(jsonObject.getString("beginText"));
							otherrule.setEnd(jsonObject.getString("endText"));
							list.add(otherrule);
						}
					}
				}

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
