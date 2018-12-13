package com.pluskynet.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.pluskynet.dao.ArticleylDao;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.PreviewDao;
import com.pluskynet.domain.Articleyl;
import com.pluskynet.domain.Preview;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.domain.User;
import com.pluskynet.otherdomain.Otherdocrule;
import com.pluskynet.rule.DocRule;
import com.pluskynet.service.PreviewService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class PreviewServiceImpl implements PreviewService {
	private PreviewDao previewDao;

	public void setPreviewDao(PreviewDao previewDao) {
		this.previewDao = previewDao;
	}

	private ArticleylDao articleylDao;

	public void setArticleylDao(ArticleylDao articleylDao) {
		this.articleylDao = articleylDao;
	}

	private DocSectionAndRuleDao docSectionAndRuleDao;

	public void setDocSectionAndRuleDao(DocSectionAndRuleDao docSectionAndRuleDao) {
		this.docSectionAndRuleDao = docSectionAndRuleDao;
	}

	@Override
	public List<StatsDoc> getDocList(Preview preview, User user) {
		List<Articleyl> listaArticles = articleylDao.getArticles(user);
		List<StatsDoc> jsonArray = previewDao.getDocList(preview, listaArticles);
		return jsonArray;

	}

	@Override
	public Map<String, Object> getDoc(String docid, String rule) {
		DocRule docRule = new DocRule();
		List<Articleyl> art = articleylDao.getArt(docid);
		Map<String, Object> map = new HashMap<String, Object>();
		if (art.size() > 0) {
			JSONObject jsonObject = JSONObject.fromObject(art.get(0).getDecodeData());
			String htmlString = jsonObject.getString("jsonHtml");
			JSONArray jsonArray = JSONArray.fromObject(rule);
			String matchStart = null; // 匹配到的开始词语
			String matchEnd = null;// 匹配到的结束词语
			String docnew = null;
			String judge = "包含";
			String newHtml = null;
			JSONObject ruleJson = new JSONObject();
			String startword = null;
			String endword = null;
			String judges = null;
			String spcx = null;
			String doctype = null;
			String olddoc = null;
			String docold = getTextFromHtml(htmlString);
			List<Otherdocrule> list = docRule.ruleFormat(jsonArray);
			List<String> intlist = new ArrayList<String>();
			intlist.add(0,"-1");
			intlist.add(1,"-1");
			docRule.doclist(docold, intlist);
			matchStart = intlist.get(2);
			matchEnd = intlist.get(3);
			String leftdoc = htmlString.substring(0,htmlString.indexOf(matchStart)+ matchStart.length());
			String rightdoc = htmlString.substring(htmlString.indexOf(matchStart)+ matchStart.length());
//			String innerdoc = htmlString.substring(leftdoc.length(), htmlString.indexOf(matchEnd)+ matchEnd.length());
			/*for (int i = 0; i < list.size(); i++) {
				JSONObject rulejson = JSONObject.fromObject(list.get(i));
				int start = -1;
				int end = -1;
				String leftdoc = null;
				String rightdoc = null;
				String beginIndex1 = null;
				String startWord = rulejson.getString("start");
				String endWord = rulejson.getString("end");
				judge = rulejson.getString("judge");
				String[] startWords = startWord.split(";|；");
				String[] endWords = endWord.split(";|；");
				String before = null;
				for (int j = 0; j < startWords.length; j++) {
					if (startWords[j].contains("#")) {
						String startandends = startWords[j].substring(0, startWords[j].lastIndexOf("#") + 1);
						startWords[j] = startWords[j].substring(startWords[j].lastIndexOf("#"));
						String[] startandend = startandends.split("#");
						for (int k = 0; k < startandend.length; k++) {
							if (htmlString.contains(startandend[k])) {
								olddoc = htmlString
										.substring(0,htmlString.indexOf(startandend[k]) + startandend[k].length());
								htmlString = htmlString
										.substring(htmlString.indexOf(startandend[k]) + startandend[k].length());
							}
						}
					} else {
						htmlString = jsonObject.getString("jsonHtml");
					}
					if (startWords[j].contains("^")) {
						before = startWords[j].substring(0, startWords[j].indexOf("^"));
						startWords[j] = startWords[j].substring(startWords[j].indexOf("^") + 1);
					} else {
						before = null;
					}
					Pattern patternstart = startRuleFomat(startWords[j]);
					Matcher matcher = patternstart.matcher(htmlString);
					if (matcher.find()) {
						beginIndex1 = matcher.group();
						matchStart = beginIndex1;
						start = htmlString.indexOf(beginIndex1);
						leftdoc = htmlString.substring(0, htmlString.indexOf(beginIndex1) + beginIndex1.length());
						StringBuffer s = new StringBuffer(leftdoc);
						leftdoc = s.reverse().toString();
						if (before != null) {
							start = start + beginIndex1.length() - leftdoc.indexOf(before);
						}
						rightdoc = htmlString.substring(start);
						if (rightdoc != null && start != -1) {
							for (int x = 0; x < endWords.length; x++) {
								String endbefore = null;
								if (endWords[x].contains("^")) {
									endbefore = endWords[x].substring(0, endWords[x].indexOf("^"));
									endWords[x] = endWords[x].substring(endWords[x].indexOf("^") + 1);
								}
								Pattern patternend = endRuleFomat(endWords[x]);
								Matcher matcher1 = patternend.matcher(rightdoc);
								if (matcher1.find()) {
									String beginIndex = matcher1.group();
									matchEnd = beginIndex;
									if (endWords[x].length() > 0) {
										// System.out.println(endWords.length);
										if (judge.equals("之前")) {
											if (endbefore != null) {
												rightdoc = rightdoc.substring(0, rightdoc.indexOf(beginIndex)+ beginIndex.length());
												end = start + rightdoc.length();
											} else {
												end = start + rightdoc.indexOf(beginIndex)+ beginIndex.length();
											}
										} else {
											end = start + rightdoc.indexOf(beginIndex) + beginIndex.length();
										}
									} else {
										end = htmlString.length();
									}
									break;
								}
							}
							break;
						}
					}
				}
				
				if (end != -1) {
					docnew = htmlString.substring(start, end);
					// System.out.println(statsDoc);
					break;
				} else if (end == 0) {
					docnew = htmlString.substring(start, htmlString.length());
					// System.out.println(statsDoc);
					break;
				}
			}*/
			if (!matchStart.equals("")) {
				for (int i = 0; i < matchStart.length(); i++) {
					String leftstart = matchStart.substring(0,matchStart.length() - i);
					String rightstart = matchStart.substring(matchStart.length() - i);
					if (leftdoc.contains(leftstart)) {
						leftdoc = leftdoc.replace(leftstart, "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">"
								+ leftstart + "</span>");
						if (i==0) {
							break;
						}else{
							leftdoc.replace(rightstart, "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">"
									+ rightstart + "</span>");
							break;
						}
					}
				}
			}
			if (!matchEnd.equals("")) {
				for (int i = 0; i < matchEnd.length(); i++) {
					String leftend = matchEnd.substring(0,matchEnd.length() - i);
					String rightend = matchEnd.substring(matchEnd.length() - i);
					if (rightdoc.contains(leftend)) {
						rightdoc = rightdoc.replaceFirst(leftend, "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">"
								+ leftend + "</span>");
						if (i==0) {
							break;
						}else{
							rightdoc = rightdoc.replaceFirst(rightend, "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">"
									+ rightend + "</span>");
							break;
						}
					}
				}
			}
			newHtml = leftdoc + rightdoc;
			
			/*	if (docnew != null) {
				if (!matchStart.equals("")) {
					newHtml = docnew.replaceFirst(matchStart, "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">"
							+ matchStart + "</span>");
				}else{
					newHtml = docnew;
				}
				if (!matchEnd.equals("")) {
					newHtml = newHtml.replace(matchEnd, "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">"
							+ matchEnd + "</span>");
				}else{
					newHtml = newHtml;
				}
				String[] html = docnew.split("</div>");
				for (int i = 0; i < html.length; i++) {
					if (html[i].equals("")) {
						continue;
					}
						String start = html[i].substring(0, 1);
					String p = "[\\u4e00-\\u9fa5]+";
					if (start.matches(p)) {
						newHtml = "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">"
								+ html[i] + "</span></div>";
					} else {
						Document doc = Jsoup.parse(html[i]);
						String eles = doc.getElementsByTag("div").text();
						if (eles.equals("")) {
							newHtml = newHtml + html[i];
						} else {
							if (html[i].indexOf(eles) != -1) {
								String left = html[i].substring(0, html[i].indexOf(eles));
								String right = html[i].substring(left.length() + eles.length());
								newHtml = newHtml + left
										+ "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">"
										+ eles + right + "</span></div>";
							}
						}
					}
				}
				newHtml = htmlString.replace(docnew, newHtml);
			} else {
				newHtml = htmlString;
			}*/
			map.put("data", newHtml);
		}
		return map;
	}

	// 开始规则格式化
	public Pattern startRuleFomat(String startWords) {
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
	public Pattern endRuleFomat(String endWords) {
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
		return htmlStr.replaceAll("　　", ""); // 返回文本字符串
	}

	public static String getTextFromHtml(String htmlStr) {
		htmlStr = delHTMLTag(htmlStr);
		htmlStr = htmlStr.replaceAll("&nbsp;", "");
		// htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);
		return htmlStr;
	}
}
