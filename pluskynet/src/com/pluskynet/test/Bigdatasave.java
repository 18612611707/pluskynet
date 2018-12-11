package com.pluskynet.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pluskynet.action.DocsectionandruleAction;
import com.pluskynet.dao.ArticleDao;
import com.pluskynet.dao.BatchdataDao;
import com.pluskynet.dao.DocidandruleidDao;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Batchdata;
import com.pluskynet.domain.Docidandruleid;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.otherdomain.Otherdocrule;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Bigdatasave extends Thread {
	protected final Log logger = LogFactory.getLog(getClass());
	private List<Article01> articleList;
	private ArticleDao articleDao;
	private BatchdataDao batchdataDao;
	private DocsectionandruleAction docrule;
	private DocidandruleidDao docidandruleidDao;
	private String doctable;
	private List<Otherdocrule> lists;
	private String causename;
	private String causetable;
	private int ruleid;
	private String latitudename;
	volatile int a = 1;
	/*
	 * public Bigdatasave(String name){ super(name); }
	 */
	public void run() {
		String ruleString = JSONArray.fromObject(lists).toString();
		List<Article01> docList;
		docList = articleList;
		// 获取符合审判程序的文书
		JSONArray jsonArray = JSONArray.fromObject(lists);
		JSONObject ruleJson = null;
		for (int i1 = 0; i1 < docList.size(); i1++) {
			JSONObject jsonObject = new JSONObject().fromObject(docList.get(i1).getDecodeData());
			JSONObject jsonObject2 = jsonObject.getJSONObject("htmlData");
			JSONObject jsonObject3 = jsonObject.getJSONObject("caseinfo");
			String title = jsonObject2.getString("Title");
			String spcx = jsonObject3.getString("审判程序");
			String docid = docList.get(i1).getDocId();
			String docold = getTextFromHtml(jsonObject2.getString("Html"));
			String docnew = null;
			int start = -1;
			int end = -1;
			String leftdoc = null;
			String rightdoc = null;
			String beginIndex1 = null;
			String Startword = null; // 匹配到的开始词语
			String Endword = null; // 匹配到的结束词语
			String before = null;
			look: for (int a = 0; a < jsonArray.size(); a++) {
				JSONObject js = new JSONObject();
				js = jsonArray.getJSONObject(a);
				String trialRound = js.getString("spcx");
				String doctype = js.getString("doctype");
				if (!docList.get(i1).getDoctype().equals(doctype)) {
					continue;
				} else if (!spcx.equals(trialRound)) {
					continue;
				}
				ruleJson = jsonArray.getJSONObject(a);
				String startWord = ruleJson.getString("start");
				String endWord = ruleJson.getString("end");
				String judge = ruleJson.getString("judge");
				String[] startWords = startWord.split(";|；");
				String[] endWords = endWord.split(";|；");
				ss:for (int j1 = 0; j1 < startWords.length; j1++) {
					if (startWords[j1].contains("^")) {
						before = startWords[j1].substring(0, startWords[j1].indexOf("^"));
						startWords[j1] = startWords[j1].substring(startWords[j1].indexOf("^") + 1);
					} else {
						before = null;
					}
					Pattern patternstart = startRuleFomat(startWords[j1]);
					Matcher matcher = patternstart.matcher(docold);
					if (matcher.find()) {
						beginIndex1 = matcher.group();
						start = docold.indexOf(beginIndex1);
						leftdoc = docold.substring(0, docold.indexOf(beginIndex1) + beginIndex1.length());
						StringBuffer s = new StringBuffer(leftdoc);
						leftdoc = s.reverse().toString();
						if (before != null) {
							start = start + beginIndex1.length() - leftdoc.indexOf(before);
						}
						rightdoc = docold.substring(start);
						if (rightdoc != null && start != -1) {
							for (int x = 0; x < endWords.length; x++) {
								String endbefore = null;
								if (endWords[x].contains("^")) {
									endbefore = endWords[x].substring(0, endWords[x].indexOf("^"));
									endWords[x] = endWords[x].substring(endWords[x].indexOf("^") + 1);
								}else{
									endbefore = null;
								}
								Pattern patternend = endRuleFomat(endWords[x]);
								Matcher matcher1 = patternend.matcher(rightdoc);
								if (matcher1.find()) {
									String beginIndex = matcher1.group();
									if (endWords[x].length() > 0) {
										// System.out.println(endWords.length);
										if (judge.equals("之前")) {
											if (endbefore != null) {
												rightdoc = rightdoc.substring(0, rightdoc.indexOf(beginIndex));
												end = start + rightdoc.lastIndexOf(endbefore) + endbefore.length();
											} else {
												end = start + rightdoc.indexOf(beginIndex);
											}
										} else {
											end = start + rightdoc.indexOf(beginIndex) + beginIndex.length();
										}
									} else {
										end = docold.length();
									}
									break ss;
								}
							}
						}
					}
				}
				Docsectionandrule docsectionandrule = new Docsectionandrule();
				Batchdata batchdata = new Batchdata();
				if (end != -1) {
					try {
						docnew = docold.substring(start, end);
					} catch (Exception e) {
						System.out.println("文书编号："+docid+",规则id："+ruleid);
					}
					docsectionandrule.setRuleid(ruleid);
					docsectionandrule.setSectionname(latitudename);
					docsectionandrule.setSectiontext(docnew);
					docsectionandrule.setDocumentsid(docList.get(i1).getDocId());
					docsectionandrule.setTitle(docList.get(i1).getTitle());
					batchdata.setCause(causename);
					batchdata.setDocumentid(docList.get(i1).getDocId());
					batchdata.setEndword(Endword);
					batchdata.setRuleid(ruleid);
					batchdata.setStartword(Startword);
					batchdataDao.save(batchdata);
					docrule.save(docsectionandrule, doctable);
					Docidandruleid docidandruleid = new Docidandruleid(docList.get(i1).getDocId(), ruleid);
					docidandruleidDao.save(docidandruleid);
					break;
				} else if (end == 0) {
					docnew = docold.substring(start, docold.length());
					docsectionandrule.setRuleid(ruleid);
					docsectionandrule.setSectionname(latitudename);
					docsectionandrule.setSectiontext(docnew);
					docsectionandrule.setDocumentsid(docList.get(i1).getDocId());
					docsectionandrule.setTitle(docList.get(i1).getTitle());
					batchdata.setCause(causename);
					batchdata.setDocumentid(docList.get(i1).getDocId());
					batchdata.setEndword(Endword);
					batchdata.setRuleid(ruleid);
					batchdata.setStartword(Startword);
					batchdataDao.save(batchdata);
					docrule.save(docsectionandrule, doctable);
					Docidandruleid docidandruleid = new Docidandruleid(docList.get(i1).getDocId(), ruleid,0);
					docidandruleidDao.save(docidandruleid);
					break;
				}
			}
		}
	}

	public boolean save(List<Article01> articleList, ArticleDao articleDao, BatchdataDao batchdataDao,
			DocsectionandruleAction docrule, DocidandruleidDao docidandruleidDao, String doctable,
			List<Otherdocrule> lists, String causename, String causetable, int ruleid, String latitudename) {
		this.articleList = articleList;
		this.articleDao = articleDao;
		this.batchdataDao = batchdataDao;
		this.docrule = docrule;
		this.docidandruleidDao = docidandruleidDao;
		this.doctable = doctable;
		this.lists = lists;
		this.causename = causename;
		this.ruleid = ruleid;
		this.causetable = causetable;
		this.latitudename = latitudename;
		String a = getName();
		return true;
	}

	// 开始规则正则化
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

	// 结束规则正则化
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

	/*
	 * 规则格式化
	 */
	public static List<Otherdocrule> ruleformat(String rule) {
		List<Otherdocrule> list = new ArrayList<Otherdocrule>();
		JSONArray jsonArray = new JSONArray();
		JSONObject ruleJson = new JSONObject();
		jsonArray = JSONArray.fromObject(rule);
		String startword = null;
		String endword = null;
		String judges = null;
		String causet = null;
		String spcx = null;
		String doctype = null;
		for (int b = 0; b < jsonArray.size(); b++) {
			Otherdocrule otherdocrule = new Otherdocrule();
			ruleJson = jsonArray.getJSONObject(b);
			judges = ruleJson.getString("judge");
			startword = ruleJson.getString("start");
			endword = ruleJson.getString("end");
			spcx = ruleJson.getString("trialRound");
			doctype = ruleJson.getString("doctype");
			if (list.size() == 0) {
				otherdocrule.setJudge(ruleJson.getString("judge"));
				otherdocrule.setSpcx(ruleJson.getString("trialRound"));
				otherdocrule.setDoctype(ruleJson.getString("doctype"));
				otherdocrule.setStart(ruleJson.getString("start"));
				otherdocrule.setEnd(ruleJson.getString("end"));
				list.add(otherdocrule);
			} else {
				for (int j = 0; j < list.size(); j++) {
					if (list.get(j).getJudge().equals(judges) && list.get(j).getSpcx().equals(spcx)
							&& list.get(j).getDoctype().equals(doctype)) {
						list.get(j).setStart(list.get(j).getStart() + ";" + startword);
						list.get(j).setEnd(list.get(j).getEnd() + ";" + endword);
						break;
					} else if (j == list.size() - 1) {
						otherdocrule.setJudge(ruleJson.getString("judge"));
						otherdocrule.setSpcx(ruleJson.getString("trialRound"));
						otherdocrule.setDoctype(ruleJson.getString("doctype"));
						otherdocrule.setStart(ruleJson.getString("start"));
						otherdocrule.setEnd(ruleJson.getString("end"));
						list.add(otherdocrule);
					}
				}
			}
		}
		return list;
	}

	public static List<Article01> breakup(List<Article01> articleList, String rule)
			throws InvocationTargetException, IllegalAccessException {
		String trialRound = null;// 获取审判程序
		String doctype = null;// 获取文书类型
		String title = null;// 获取标题
		String spcx = null;// 获取审判类型
		JSONArray jsonArray = JSONArray.fromObject(rule);// 格式化规则
		List<Article01> list = new ArrayList<Article01>();
		for (int i = articleList.size() - 1; i >= 0; i--) {
			Article01 article01 = new Article01();
			BeanUtils.copyProperties(articleList.get(i), article01);
			for (int j = 0; j < jsonArray.size(); j++) {
				JSONObject js = new JSONObject();
				js = jsonArray.getJSONObject(j);
				trialRound = js.getString("spcx");
				doctype = js.getString("doctype");
				JSONObject jsonObject = new JSONObject().fromObject(article01.getDecodeData());
				JSONObject jsonObject2 = jsonObject.getJSONObject("htmlData");
				JSONObject jsonObject3 = jsonObject.getJSONObject("caseinfo");
				title = jsonObject2.getString("Title");
				spcx = jsonObject3.getString("审判程序");
				if (title.indexOf(doctype) != -1 && spcx.equals(trialRound)) {
					article01.setDecodeData(jsonObject2.getString("Html"));
					list.add(article01);
					break;

				}
			}
		}
		return list;

	}

	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	private static final String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符

	public static String delHTMLTag(String htmlStr) {
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
		htmlStr = htmlStr.replace(" ", "");
		htmlStr = htmlStr.replace("  ", "");
		htmlStr = htmlStr.replaceAll("　", "");
		return htmlStr.replaceAll("　　 ", ""); // 返回文本字符串
	}

	public static String getTextFromHtml(String htmlStr) {
		htmlStr = delHTMLTag(htmlStr);
		htmlStr = htmlStr.replaceAll("&nbsp;", "");
		// htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);
		return htmlStr;
	}

}
