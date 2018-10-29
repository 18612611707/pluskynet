package com.pluskynet.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.components.Else;

import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.LatitudeDao;
import com.pluskynet.dao.LatitudeauditDao;
import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.domain.User;
import com.pluskynet.otherdomain.OtherLatitude;
import com.pluskynet.otherdomain.Otherdocrule;
import com.pluskynet.otherdomain.Treelatitude;
import com.pluskynet.service.LatitudeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class LatitudeServiceImpl implements LatitudeService {

	private DocSectionAndRuleDao docSectionAndRuleDao;

	public void setDocSectionAndRuleDao(DocSectionAndRuleDao docSectionAndRuleDao) {
		this.docSectionAndRuleDao = docSectionAndRuleDao;
	}

	private LatitudeDao latitudeDao;

	public void setLatitudeDao(LatitudeDao latitudeDao) {
		this.latitudeDao = latitudeDao;
	}

	private LatitudeauditDao LatitudeauditDao;

	public void setLatitudeauditDao(LatitudeauditDao latitudeauditDao) {
		LatitudeauditDao = latitudeauditDao;
	}

	@Override
	public String save(Latitude latitude) {
		String msg = latitudeDao.save(latitude);
		return msg;
	}

	@Override
	public String update(Latitude latitude,User user) {
		String msg = latitudeDao.update(latitude,user);
		if (msg.equals("成功")) {
			latitude = latitudeDao.getLatitude(latitude);
			Latitudeaudit latitudeaudit = new Latitudeaudit();
			latitudeaudit.setLatitudeid(latitude.getLatitudeid());
			latitudeaudit.setLatitudename(latitude.getLatitudename());
			latitudeaudit.setLatitudetype(1);
			latitudeaudit.setRule(latitude.getRule());
			LatitudeauditDao.update(latitudeaudit);
		}
		return msg;
	}

	@Override
	public List<Map> getLatitudeList(User user) {
		List<Treelatitude> friList = latitudeDao.getFirstLevel(user); //获取一级内容
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < friList.size(); i++) {
			Map<String, Object> treeMap = new HashMap<String, Object>();
			treeMap.put("latitudeid", friList.get(i).getLatitudeid());
			treeMap.put("latitudefid", friList.get(i).getLatitudefid());
			treeMap.put("latitudename", friList.get(i).getLatitudename());
			treeMap.put("children", treeList(friList.get(i).getLatitudeid(),user));
			list.add(treeMap);
		}

		return list;

	}

	@Override
	public List<Treelatitude> treeList(int latitudeid, User user) {
		List<Latitude> nextSubSet = new ArrayList<Latitude>();
		Treelatitude voteTree = new Treelatitude();
		voteTree.setLatitudeid(latitudeid);
		nextSubSet = latitudeDao.getNextSubSet(voteTree,user);
		 List<Treelatitude> list = new ArrayList<Treelatitude>();
	        for (int i = 0; i < nextSubSet.size(); i++) {
	        	//遍历这个二级目录的集合
				Treelatitude treelatitude = new Treelatitude();
				treelatitude.setLatitudeid(nextSubSet.get(i).getLatitudeid());
				treelatitude.setLatitudefid(nextSubSet.get(i).getLatitudefid());
				treelatitude.setLatitudename(nextSubSet.get(i).getLatitudename());
				List<Treelatitude> ts = latitudeDao.getDeeptLevel(nextSubSet.get(i),user);  
	            //将下面的子集都依次递归进来 
	            treelatitude.setChildren(ts);
			    list.add(treelatitude);
			}
		return list;
	}

	@Override
	public Latitude getLatitude(Latitude latitude) {
		Latitude list = latitudeDao.getLatitude(latitude);
		return list;
	}

	@Override
	public List<String> getLatitudeName(Latitude latitude) {
		List<String> list = latitudeDao.getLatitudeName(latitude);
		return list;
	}

	@Override
	public List<StatsDoc> getDocList(Latitude latitude,User user) {
		List<StatsDoc> listsDocs = new ArrayList<StatsDoc>();
		JSONArray jsonArray = new JSONArray().fromObject(latitude.getRule());
		int b = latitude.getRuletype();
		if (b == 1) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				if (!jsonObject.get("state").equals("新录")) {
					continue;
				}
				String sectionname = jsonObject.getString("sectionname"); // 段落名
				String sectiontext = null;
				List<Docsectionandrule> list = docSectionAndRuleDao.getDocLists(sectionname,user);
				String contains = jsonObject.getString("contains");
				String[] contain = contains.split(";");// 包含
				boolean a = false;
				for (int j = 0; j < list.size(); j++) {
					DocidAndDoc docidAndDoc = new DocidAndDoc();
					StatsDoc statsDoc = new StatsDoc();
					sectiontext = list.get(j).getSectiontext();
					for (int x = 0; x < contain.length; x++) {
						if (sectiontext.contains(contain[x])) {
							sectiontext = sectiontext.replaceAll(contain[x],
									"<span style=\"color:red\">" + contain[x] + "</span>");
							a = true;
						} else {
							a = false;
							break;
						}
					}
					if (a) {
						String[] notcon = jsonObject.getString("notcon").split(";");
						for (int k = 0; k < notcon.length; k++) {
							if (notcon[k] == null || notcon[k].equals("")) {
								a = true;
								break;
							} else {
								if (!sectiontext.contains(notcon[k])) {
									a = true;
								} else {
									a = false;
									break;
								}
							}
						}
					}
					if (a) {
						statsDoc.setStats("符合");
						docidAndDoc.setDoc(sectiontext);
						docidAndDoc.setDocid(list.get(j).getDocumentsid());
						docidAndDoc.setTitle(list.get(j).getTitle());
						statsDoc.setDocidAndDoc(docidAndDoc);
						listsDocs.add(statsDoc);
						continue;
					} else {
						statsDoc.setStats("不符合");
						docidAndDoc.setDoc(sectiontext);
						docidAndDoc.setDocid(list.get(j).getDocumentsid());
						docidAndDoc.setTitle(list.get(j).getTitle());
						statsDoc.setDocidAndDoc(docidAndDoc);
						listsDocs.add(statsDoc);
						continue;
					}
				}
			}
		} else if (b == 3) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				StatsDoc statsDoc = new StatsDoc();
				DocidAndDoc docidAndDoc = new DocidAndDoc();
				String sectionname = jsonObject.getString("sectionname"); // 段落名
				String sectiontext = null;
				List<Docsectionandrule> list = docSectionAndRuleDao.getDocLists(sectionname,user);
				Pattern patPunc = null;
				for (int k = 0; k < list.size(); k++) {
					sectiontext = list.get(k).getSectiontext();
					if (jsonObject.getString("timeFormat").equals("0")) {
						patPunc = Pattern
								.compile("[\u4e00-\u9fa5]{0,4}年[\u4e00-\u9fa5]{0,2}月[\u4e00-\u9fa5]{0,2}日");
					}else{
						patPunc = Pattern
								.compile("[0-9]{0,4}年[0-9]{0,2}月[0-9]{0,2}日");
					}
						int t = jsonObject.getInt("timeIndex");
						Matcher matcher = patPunc.matcher(sectiontext);
						while (matcher.find()) {
							String time = matcher.group();
							t--;
							if (t == 0) {
								statsDoc.setStats("符合");
								sectiontext = sectiontext.replaceAll(time,"<span style=\"color:red\">" + time + "</span>");
								docidAndDoc.setDoc(sectiontext);
								docidAndDoc.setDocid(list.get(k).getDocumentsid());
								docidAndDoc.setTitle(list.get(k).getTitle());
								statsDoc.setDocidAndDoc(docidAndDoc);
								listsDocs.add(statsDoc);
							} else {
								statsDoc.setStats("不符合");
								docidAndDoc.setDoc(list.get(k).getSectiontext());
								docidAndDoc.setDocid(list.get(k).getDocumentsid());
								docidAndDoc.setTitle(list.get(k).getTitle());
								statsDoc.setDocidAndDoc(docidAndDoc);
								listsDocs.add(statsDoc);
							}
						}

					
				}
			}
		}else{
			JSONObject ruleJson = new JSONObject();		
			List<Docsectionandrule> docList = new ArrayList<Docsectionandrule>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				StatsDoc statsDoc = new StatsDoc();
				DocidAndDoc docidAndDoc = new DocidAndDoc();
				String sectionname = jsonObject.getString("sectionname"); // 段落名
				String sectiontext = null;
				List<Docsectionandrule> list = docSectionAndRuleDao.getDocLists(sectionname,user);
				docList.addAll(list);
			}
			for (int i = 0; i < docList.size(); i++) {
				StatsDoc statsDoc = new StatsDoc();
				DocidAndDoc docidAndDoc = new DocidAndDoc();
				String docid = docList.get(i).getDocumentsid();
				String docold = docList.get(i).getSectiontext();
				String doctitle = docList.get(i).getTitle();
				String docnew = null;
				int start = -1;
				int end = -1;
				String leftdoc = null;
				String rightdoc = null;
				String beginIndex1 = null;
				for (int a = 0; a < jsonArray.size(); a++) {
					ruleJson = jsonArray.getJSONObject(a);
					// System.out.println(ruleJson);
					String startWord = ruleJson.getString("start");
					String endWord = ruleJson.getString("end");
					String[] startWords = startWord.split(";|；");
					String[] endWords = endWord.split(";|；");
					for (int j = 0; j < startWords.length; j++) {
						Pattern patternstart = startRuleFomat(startWords[j]);
						Matcher matcher = patternstart.matcher(docold);
						if (matcher.find()) {
							beginIndex1 = matcher.group();
							start = docold.indexOf(beginIndex1);
							leftdoc = docold.substring(0, docold.indexOf(beginIndex1) + beginIndex1.length());
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
								if (endWords[x].length() > 0) {
										end = start + rightdoc.indexOf(beginIndex) + beginIndex1.length();
								} else {
									end = docold.length();
								}
								break;
							}
						}
					}
					if (end != -1) {
						docnew = docold.substring(start, end);
						statsDoc.setStats("符合");
						docidAndDoc.setDoc(docnew);
						docidAndDoc.setDocid(docid);
						docidAndDoc.setTitle(doctitle);
						statsDoc.setDocidAndDoc(docidAndDoc);
						listsDocs.add(statsDoc);
						// System.out.println(statsDoc);
						break;
					} else if (end == 0) {
						docnew = docold.substring(start, docold.length());
						statsDoc.setStats("符合");
						docidAndDoc.setDoc(docnew);
						docidAndDoc.setDocid(docid);
						docidAndDoc.setTitle(doctitle);
						statsDoc.setDocidAndDoc(docidAndDoc);
						listsDocs.add(statsDoc);
						// System.out.println(statsDoc);
						break;
					}
				}
				if (end == -1) {
					statsDoc.setStats("不符合");
					docidAndDoc.setDocid(docid);
					docidAndDoc.setTitle(doctitle);
					statsDoc.setDocidAndDoc(docidAndDoc);
					listsDocs.add(statsDoc);

				}
			}
		}
		return listsDocs;
	}

	@Override
	public List<Map> getScreeList(String latitudeName, Integer latitudeId) {
		List<Map> list = latitudeDao.getScreeList(latitudeName, latitudeId);
		return list;
	}
	// 开始规则格式化
		public Pattern startRuleFomat(String startWords) {
			String reg_charset = null;
			String[] start = startWords.split("*");
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
		public Pattern endRuleFomat(String endWords) {
			String reg_charset = null;
			String[] end = endWords.split("*");
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

		@Override
		public List<Map> getLatitudeShow(String latitudename,User user) {
			List<Latitude> friList = latitudeDao.getLatitudeShow(latitudename,user);
			List<Map> list = new ArrayList<Map>();
			for (int i = 0; i < friList.size(); i++) {
				Map<String, Object> treeMap = new HashMap<String, Object>();
				treeMap.put("latitudeid", friList.get(i).getLatitudeid());
				treeMap.put("latitudefid", friList.get(i).getLatitudefid());
				treeMap.put("latitudename", friList.get(i).getLatitudename());
				treeMap.put("children", treeList(friList.get(i).getLatitudeid(),user));
				list.add(treeMap);
			}
			return list;
		}

		@Override
		public List<Latitude> getRuleShow(Integer latitudeid, String cause, String spcx, String sectionname) {
			List<Latitude> list = latitudeDao.getRuleShow(latitudeid,cause,spcx,sectionname);
			return list;
		}

		@Override
		public String updateName(Latitude latitude) {
			String msg = latitudeDao.updateName(latitude);
			return msg;
		}

}
