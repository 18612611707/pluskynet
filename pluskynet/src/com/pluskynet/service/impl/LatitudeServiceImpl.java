package com.pluskynet.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Else;
import org.jsoup.helper.StringUtil;

import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.LatitudeDao;
import com.pluskynet.dao.LatitudeauditDao;
import com.pluskynet.dao.LatitudenumDao;
import com.pluskynet.dao.PreviewhisDao;
import com.pluskynet.dao.SampleDao;
import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.domain.Latitudenum;
import com.pluskynet.domain.Previewhis;
import com.pluskynet.domain.Sample;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.domain.User;
import com.pluskynet.otherdomain.OtherLatitude;
import com.pluskynet.otherdomain.Otherdocrule;
import com.pluskynet.otherdomain.Treelatitude;
import com.pluskynet.service.LatitudeService;
import com.pluskynet.util.HttpRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class LatitudeServiceImpl implements LatitudeService {
	protected final Log logger = LogFactory.getLog(getClass()); 
	/*
	 * 保存预览历史
	 */
	private PreviewhisDao previewhisDao;

	public void setPreviewhisDao(PreviewhisDao previewhisDao) {
		this.previewhisDao = previewhisDao;
	}

	/*
	 * 获取选择样本的规则
	 */
	private SampleDao sampleDao;

	public void setSampleDao(SampleDao sampleDao) {
		this.sampleDao = sampleDao;
	}

	/*
	 * 获取各个维度的文书数量
	 */
	private LatitudenumDao latitudenumDao;

	public void setLatitudenumDao(LatitudenumDao latitudenumDao) {
		this.latitudenumDao = latitudenumDao;
	}

	/*
	 * 获取样本文书
	 */
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

	private List<Latitudenum> numlist = null;

	@Override
	public Map save(Latitude latitude, User user) {
		Map msg = latitudeDao.save(latitude, user);
		return msg;
	}

	@Override
	public String update(Latitude latitude, User user) {
		String msg = latitudeDao.update(latitude, user);
		if (msg.equals("成功")) {
			HttpRequest httpRequest = new HttpRequest();
			/*
			 * 用于同步线上规则到线下数据库
			 */
			httpRequest.sendPost("http://114.242.17.135:8081/pluskynet/LatitudeAction!update.action",
					"&latitudeid=" + latitude.getLatitudeid() + "&latitudefid=" + latitude.getLatitudefid()
							+ "&latitudename=" + latitude.getLatitudename() + "&ruletype=" + latitude.getRuletype()
							+ "&rule=" + latitude.getRule() + "&reserved=" + latitude.getReserved() + "&userid="
							+ user.getUserid() + "&username=" + user.getUsername() + "&name=" + user.getName()
							+ "&rolecode=" + user.getRolecode());
			latitude = latitudeDao.getLatitude(latitude);
			Latitudeaudit latitudeaudit = new Latitudeaudit();
			latitudeaudit.setLatitudeid(latitude.getLatitudeid());
			latitudeaudit.setLatitudename(latitude.getLatitudename());
			latitudeaudit.setLatitudetype(1);
			latitudeaudit.setSubuserid(user.getUserid().toString());
			latitudeaudit.setRule(latitude.getRule());
			latitudeaudit.setReserved(latitude.getReserved());
			LatitudeauditDao.update(latitudeaudit);
			httpRequest.sendPost("http://114.242.17.135:8081/pluskynet/LatitudeauditAction!update.action",
					"rule=" + latitude.getRule() + "&latitudename=" + latitude.getLatitudename() + "&latitudeid="
							+ latitude.getLatitudeid() + "&latitudetype=1" + "&reserved=" + latitude.getReserved()
							+ "&subuserid=" + user.getUserid().toString());
		}
		return msg;
	}

	@Override
	public List<Map> getLatitudeList(User user) {
		List<Latitude> lists = latitudeDao.getLatitudeList();
		numlist = latitudenumDao.getnums(1);
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getLatitudefid().intValue() != 0) {
				continue;
			}
			Map<String, Object> treeMap = new HashMap<String, Object>();
			treeMap.put("latitudeid", lists.get(i).getLatitudeid());
			treeMap.put("latitudefid", lists.get(i).getLatitudefid());
			treeMap.put("latitudename", lists.get(i).getLatitudename() + "," + 0);
			for (int j = 0; j < numlist.size(); j++) {
				if (numlist.get(j).getLatitudeid().intValue() == lists.get(i).getLatitudeid().intValue()) {
					treeMap.put("latitudename", lists.get(i).getLatitudename() + "," + numlist.get(j).getNums());
					break;
				}
			}
			treeMap.put("creator", lists.get(i).getCreateruser());
			treeMap.put("stat", lists.get(i).getStats());
			treeMap.put("children", children(lists, lists.get(i).getLatitudeid()));
			list.add(treeMap);
		}

		// List<Treelatitude> friList = latitudeDao.getFirstLevel(user);
		// //获取一级内容
		//
		// for (int i = 0; i < friList.size(); i++) {
		// Map<String, Object> treeMap = new HashMap<String, Object>();
		// treeMap.put("latitudeid", friList.get(i).getLatitudeid());
		// treeMap.put("latitudefid", friList.get(i).getLatitudefid());
		// treeMap.put("latitudename", friList.get(i).getLatitudename());
		// treeMap.put("creator", friList.get(i).getCreator());
		// treeMap.put("stat", friList.get(i).getStat());
		// treeMap.put("children",
		// treeList(friList.get(i).getLatitudeid(),user));
		// list.add(treeMap);
		// }

		return list;

	}

	private List<Treelatitude> children(List<Latitude> lists, Integer latitudeid) {
		List<Treelatitude> list = new ArrayList<Treelatitude>();
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getLatitudefid().intValue() == latitudeid.intValue()) {
				Treelatitude treeMap = new Treelatitude();
				treeMap.setLatitudeid(lists.get(i).getLatitudeid());
				treeMap.setLatitudefid(lists.get(i).getLatitudefid());
				for (int j = 0; j < numlist.size(); j++) {
					if (numlist.get(j).getLatitudeid().intValue() == lists.get(i).getLatitudeid().intValue()) {
						treeMap.setLatitudename(lists.get(i).getLatitudename() + "," + numlist.get(j).getNums());
						break;
					} else if (j == numlist.size() - 1) {
						treeMap.setLatitudename(lists.get(i).getLatitudename() + "," + 0);
					}
				}
				treeMap.setCreator(lists.get(i).getCreateruser());
				treeMap.setStat(lists.get(i).getStats());
				List<Treelatitude> tree = children(lists, lists.get(i).getLatitudeid());
				treeMap.setChildren(tree);
				list.add(treeMap);
			}
		}
		return list;
	}

	@Override
	public List<Treelatitude> treeList(int latitudeid, User user) {
		List<Latitude> nextSubSet = new ArrayList<Latitude>();
		Treelatitude voteTree = new Treelatitude();
		voteTree.setLatitudeid(latitudeid);
		nextSubSet = latitudeDao.getNextSubSet(voteTree, user);
		List<Treelatitude> list = new ArrayList<Treelatitude>();
		for (int i = 0; i < nextSubSet.size(); i++) {
			// 遍历这个二级目录的集合
			Treelatitude treelatitude = new Treelatitude();
			treelatitude.setLatitudeid(nextSubSet.get(i).getLatitudeid());
			treelatitude.setLatitudefid(nextSubSet.get(i).getLatitudefid());
			treelatitude.setLatitudename(nextSubSet.get(i).getLatitudename());
			List<Treelatitude> ts = latitudeDao.getDeeptLevel(nextSubSet.get(i), user);
			// 将下面的子集都依次递归进来
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
	public List<StatsDoc> getDocList(Latitude latitude, User user) {
		List<StatsDoc> listsDocs = new ArrayList<StatsDoc>();
		JSONArray jsonArray = new JSONArray().fromObject(latitude.getRule());
		int b = latitude.getRuletype();
		if (b == 1) {
			// int sectionname = jsonObject.getInt("sectionname"); // 段落名
			String oldsectiontext = null;
			String newsectiontext = null;
			List<Docsectionandrule> list = docSectionAndRuleDao.getDocLists(user);
			for (int j = 0; j < list.size(); j++) {
				logger.info("userid"+user.getUserid()+":"+"文书编号:"+list.get(j).getRuleid());
				boolean a = false;
				DocidAndDoc docidAndDoc = new DocidAndDoc();
				StatsDoc statsDoc = new StatsDoc();
				oldsectiontext = list.get(j).getSectiontext();
				if (StringUtil.isBlank(oldsectiontext)) {
					continue;
				}
				look: for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					// if (!jsonObject.get("state").equals("新录")) {
					// continue;
					// }
					String contains = jsonObject.getString("contains");
					if (contains.equals("")) {
						newsectiontext = oldsectiontext;
						a = true;
					} else {
						if (contains.contains("*")) {
							Pattern containp = startRuleFomat(contains);
							Matcher matcher = containp.matcher(oldsectiontext);
							if (matcher.find()) {
								String beginIndex = matcher.group();
								if (beginIndex.contains("*")) {
									beginIndex.replaceAll("\\*", "\\\\*");
								}
								newsectiontext = oldsectiontext.replaceAll(beginIndex,
										"<span style=\"color:red\">" + beginIndex + "</span>");
								a = true;
							}
						} else if (contains.contains("&")) {
							String[] contain = contains.split("\\&");// 包含
							for (int x = 0; x < contain.length; x++) {
								if (oldsectiontext.contains(contain[x].toString())) {
									newsectiontext = oldsectiontext.replaceAll(contain[x].toString(),
											"<span style=\"color:red\">" + contain[x].toString() + "</span>");
									a = true;
								} else {
									a = false;
									break;
								}
							}
							if (a) {
								for (int x = 0; x < contain.length; x++) {
									newsectiontext = oldsectiontext.replaceAll(contain[x],
											"<span style=\"color:red\">" + contain[x] + "</span>");
								}
							} else {
								newsectiontext = oldsectiontext;
								a = false;
								break;
							}
						} else {
							if (oldsectiontext.contains(contains)) {
								newsectiontext = oldsectiontext.replaceAll(contains,
										"<span style=\"color:red\">" + contains + "</span>");
								a = true;
							} else {
								a = false;
							}
						}
					}
					if (a) {
						String[] notcon = jsonObject.getString("notcon").split(";;");
						for (int k = 0; k < notcon.length; k++) {
							if (notcon[k].contains("*")) {
								Pattern containp = endRuleFomat(notcon[k]);
								Matcher matcher = containp.matcher(oldsectiontext);
								if (!matcher.find()) {
									a = true;
									if (k == notcon.length - 1) {
										break look;
									}
								} else {
									a = false;
									break look;
								}
							} else if (!oldsectiontext.contains(notcon[k])) {
								a = true;
								if (k == notcon.length - 1) {
									break look;
								}
							} else if (notcon[k].equals("")) {
								a = true;
								break look;
							} else {
								a = false;
								break look;
							}
						}
					}
				}
				if (a) {
					statsDoc.setStats("符合");
					docidAndDoc.setDoc(newsectiontext);
					docidAndDoc.setDocid(list.get(j).getDocumentsid());
					docidAndDoc.setTitle(list.get(j).getTitle());
					statsDoc.setDocidAndDoc(docidAndDoc);
					listsDocs.add(statsDoc);
					continue;
				} else {
					statsDoc.setStats("不符合");
					docidAndDoc.setDoc(oldsectiontext);
					docidAndDoc.setDocid(list.get(j).getDocumentsid());
					docidAndDoc.setTitle(list.get(j).getTitle());
					statsDoc.setDocidAndDoc(docidAndDoc);
					listsDocs.add(statsDoc);
					continue;
				}
			}
		} else if (b == 3) {

			// int sectionname = jsonObject.getInt("sectionname"); // 段落名
			String sectiontext = null;
			List<Docsectionandrule> list = docSectionAndRuleDao.getDocLists(user);
			Pattern patPunc = null;
			for (int k = 0; k < list.size(); k++) {
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					StatsDoc statsDoc = new StatsDoc();
					DocidAndDoc docidAndDoc = new DocidAndDoc();
					sectiontext = list.get(k).getSectiontext();
					if (jsonObject.getString("timeFormat").equals("0")) {
						patPunc = Pattern.compile("[\u4e00-\u9fa5]{0,4}年[\u4e00-\u9fa5]{0,2}月[\u4e00-\u9fa5]{0,2}日");
					} else {
						patPunc = Pattern.compile("[0-9]{0,4}年[0-9]{0,2}月[0-9]{0,2}日");
					}
					int t = jsonObject.getInt("timeIndex");
					Matcher matcher = patPunc.matcher(sectiontext);
					while (matcher.find()) {
						String time = matcher.group();
						t--;
						if (t == 0) {
							statsDoc.setStats("符合");
							sectiontext = sectiontext.replaceAll(time, "<span style=\"color:red\">" + time + "</span>");
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
		} else {
			JSONObject ruleJson = new JSONObject();
			List<Docsectionandrule> docList = new ArrayList<Docsectionandrule>();
			List<Docsectionandrule> list = docSectionAndRuleDao.getDocLists(user);
			docList.addAll(list);
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
		int accord = 0;
		int noaccord = 0;
		for (int i = 0; i < listsDocs.size(); i++) {
			if (listsDocs.get(i).getStats().equals("符合")) {
				accord = accord + 1;
			} else {
				noaccord = noaccord + 1;
			}
		}
		Sample samObject = sampleDao.select(user);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Previewhis previewhis = new Previewhis(samObject.getRule().toString(), df.format(new Date()), listsDocs.size(), accord,
				noaccord, user.getUserid().toString(), user.getUsername());
		previewhisDao.save(previewhis);
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.sendPost("http://114.242.17.135:8081/pluskynet/PreviewhisAction!save.action",
				"sample=" + samObject.getRule() + "&createtime=" + df.format(new Date()) + "&sum=" + listsDocs.size()
						+ "&accord=" + accord + "&noaccord=" + noaccord + "&createuser=" + user.getUserid().toString()
						+ "&createname=" + user.getUsername());
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
		String[] start = startWords.split("\\*");
		if (start.length > 1) {
			for (int j = 0; j < start.length; j++) {
				int wordnum = 50;
				for (int i = 0; i < start[j].length(); i++) {
					if (start[j].charAt(i) == '(') {
						start[j] = start[j].substring(start[j].indexOf("(") + 1);
					} else if (start[j].charAt(i) == '（') {
						start[j] = start[j].substring(start[j].indexOf("（") + 1);
					} else if (start[j].charAt(i) == ')') {
						wordnum = Integer.valueOf(start[j].substring(0, start[j].indexOf(")")));
						start[j] = start[j].substring(start[j].indexOf(")") + 1);
					} else if (start[j].charAt(i) == '）') {
						wordnum = Integer.valueOf(start[j].substring(0, start[j].indexOf("）")));
						start[j] = start[j].substring(start[j].indexOf("）") + 1);
					}
				}
				if (reg_charset == null) {
					reg_charset = start[j];
				} else {
					reg_charset = reg_charset + "([\u4e00-\u9fa5_×Ｘa-zA-Z0-9_|\\pP，。？：；‘’！“”—……、]{0," + wordnum + "})"
							+ start[j];
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
		String[] end = endWords.split("\\*");
		for (int j = 0; j < end.length; j++) {
			if (end.length > 1) {
				int wordnum = 50;
				for (int i = 0; i < end[j].length(); i++) {
					if (end[j].charAt(i) == '(') {
						end[j] = end[j].substring(end[j].indexOf("(") + 1);
					} else if (end[j].charAt(i) == '（') {
						end[j] = end[j].substring(end[j].indexOf("（") + 1);
					} else if (end[j].charAt(i) == ')') {
						wordnum = Integer.valueOf(end[j].substring(0, end[j].indexOf(")")));
						end[j] = end[j].substring(end[j].indexOf(")") + 1);
					} else if (end[j].charAt(i) == '）') {
						wordnum = Integer.valueOf(end[j].substring(0, end[j].indexOf("）")));
						end[j] = end[j].substring(end[j].indexOf("）") + 1);
					}
				}
				if (reg_charset == null) {
					reg_charset = end[j];
				} else {
					reg_charset = reg_charset + "([\u4e00-\u9fa5_×Ｘa-zA-Z0-9_|\\pP，。？：；‘’！“”—……、]{0," + wordnum + "})"
							+ end[j];
				}
			} else {
				reg_charset = end[j];
			}
		}
		Pattern pattern = Pattern.compile(reg_charset);
		return pattern;
	}

	@Override
	public List<Map> getLatitudeShow(String latitudename, User user) {
		List<Latitude> friList = latitudeDao.getLatitudeShow(latitudename, user);
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < friList.size(); i++) {
			Map<String, Object> treeMap = new HashMap<String, Object>();
			treeMap.put("latitudeid", friList.get(i).getLatitudeid());
			treeMap.put("latitudefid", friList.get(i).getLatitudefid());
			treeMap.put("latitudename", friList.get(i).getLatitudename());
			treeMap.put("children", treeList(friList.get(i).getLatitudeid(), user));
			list.add(treeMap);
		}
		return list;
	}

	@Override
	public List<Latitude> getRuleShow(Integer latitudeid, String cause, String spcx, String sectionname) {
		List<Latitude> list = latitudeDao.getRuleShow(latitudeid, cause, spcx, sectionname);
		return list;
	}

	@Override
	public String updateName(Latitude latitude, User user) {
		String msg = latitudeDao.updateName(latitude, user);
		return msg;
	}

	@Override
	public String approve(Latitude latitude, User user) {
		String msg = latitudeDao.approve(latitude, user);
		return msg;
	}

}
