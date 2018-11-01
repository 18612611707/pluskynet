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
	public List<StatsDoc> getDocList(Preview preview,User user) {
		List<Articleyl> listaArticles = articleylDao.getArticles(user);
		List<StatsDoc> jsonArray = previewDao.getDocList(preview, listaArticles);
//		docSectionAndRuleDao.saveyldelete(preview.getDocName());
//		for (int i = 0; i < jsonArray.size(); i++) {
//			Docsectionandrule docsectionandrule = new Docsectionandrule();
//			if(jsonArray.get(i).getStats()=="符合"){
//				jsonArray.get(i).getDocidAndDoc();
//				docsectionandrule.setSectionname(preview.getDocName());
//				docsectionandrule.setSectiontext(jsonArray.get(i).getDocidAndDoc().getDoc());
//				docsectionandrule.setDocumentsid(jsonArray.get(i).getDocidAndDoc().getDocid());
//				docsectionandrule.setTitle(jsonArray.get(i).getDocidAndDoc().getTitle());
//				docSectionAndRuleDao.saveyl(docsectionandrule);
//			}
//		}
		return jsonArray;

	}

	@Override
	public Map<String, Object> getDoc(String docid, String rule) {
		List<Articleyl> art = articleylDao.getArt(docid);
		Map<String, Object> map = new HashMap<String, Object>();
		if (art.size() > 0) {
			JSONObject jsonObject = JSONObject.fromObject(art.get(0).getDecodeData());
			String htmlString = jsonObject.getString("jsonHtml");
			JSONArray jsonArray = JSONArray.fromObject(rule);
			String matchStart = null; //匹配到的开始词语
			String matchEnd = null;//匹配到的结束词语
			String docnew = null;
			String judge = "包含";
			String newHtml = null;
			JSONObject ruleJson = new JSONObject();
			String startword = null;
			String endword = null;
			String judges = null;
			String spcx = null;
			String doctype = null;
			List<Otherdocrule> list = new ArrayList<Otherdocrule>();
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
							if(!startword.equals("")){
								list.get(j).setStart(list.get(j).getStart() + ";" + startword);
								}
								if(!endword.equals("")){
								list.get(j).setEnd(list.get(j).getEnd() + ";" + endword);
								}
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
			for (int i = 0; i < list.size(); i++) {
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
					if (startWords[j].contains("^")) {
						before = startWords[j].substring(0,startWords[j].indexOf("^"));
						startWords[j] = startWords[j].substring(startWords[j].indexOf("^")+1);
					}else{
						before = null;
					}
					Pattern patternstart = startRuleFomat(startWords[j]);
					Matcher matcher = patternstart.matcher(htmlString);
					if (matcher.find()) {
						beginIndex1 = matcher.group();
						start = htmlString.indexOf(beginIndex1);
						leftdoc = htmlString.substring(0, htmlString.indexOf(beginIndex1) + beginIndex1.length());
						StringBuffer s = new StringBuffer(leftdoc);
						leftdoc = s.reverse().toString();
						if (before!=null) {
							start = start+beginIndex1.length() - leftdoc.indexOf(before);	
						}
						rightdoc = htmlString.substring(start);
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
								// System.out.println(endWords.length);
								if (judge.equals("之前")) {
									end = start + rightdoc.indexOf(beginIndex) + beginIndex1.length();
								} else {
									end = start + rightdoc.indexOf(beginIndex) + beginIndex.length()
											+ beginIndex1.length();
								}
							} else {
								end = htmlString.length();
							}
							matchEnd = endWords[x];
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
			}
			if (docnew != null) {
				String[] html = docnew.split("</div>");
				for (int i = 0; i < html.length; i++) {
					String start = html[i].substring(0, 1);
					String p = "[\\u4e00-\\u9fa5]+";
					if (start.matches(p)) {
						newHtml = "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">" + html[i] + "</span></div>";
					} else {
						Document doc = Jsoup.parse(html[i]);
						String eles = doc.getElementsByTag("div").text();
						if (eles.equals("")) {
							newHtml = newHtml + html[i];
						} else {
							if(html[i].indexOf(eles)!=-1){
							String left = html[i].substring(0, html[i].indexOf(eles));
							String right = html[i].substring(left.length() + eles.length());
							newHtml = newHtml + left + "<span style=\"LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;color:red;\">" + eles + right + "</span></div>";
							}
							}
					}
				}
				newHtml = htmlString.replace(docnew, newHtml);
			} else {
				newHtml = htmlString;
			}
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
}
