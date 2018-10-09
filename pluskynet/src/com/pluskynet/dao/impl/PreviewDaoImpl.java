package com.pluskynet.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.PreviewDao;
import com.pluskynet.domain.Articleyl;
import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Preview;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.otherdomain.Otherdocrule;
import com.pluskynet.parsing.Parsing;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class PreviewDaoImpl extends HibernateDaoSupport implements PreviewDao {
	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pluskynet.dao.PreviewDao#getDocList(com.pluskynet.domain.Preview,
	 * java.util.List) 检索符合规则的文书 和 不符合规则的文书列表
	 */
	public List<StatsDoc> getDocList(Preview preview, List<Articleyl> listaArticles) {
		Parsing parsing = new Parsing();
		// 文书拆分，返回docid和文书主文章信息
		List<DocidAndDoc> docList = null;
		List<StatsDoc> statsDocs = new ArrayList<StatsDoc>();
		JSONArray jsonArray = new JSONArray();
		JSONObject ruleJson = new JSONObject();
		jsonArray = jsonArray.fromObject(preview.getRule());
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
						if (!startword.equals("")) {
							list.get(j).setStart(list.get(j).getStart() + ";" + startword);
						}
						if (!endword.equals("")) {
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
		for (int c = 0; c < list.size(); c++) {
			jsonArray = JSONArray.fromObject(list);
			preview.setRule(jsonArray.get(c).toString());
			docList = parsing.DocList(listaArticles, preview);
			docList.addAll(docList);
		}
		for (int i = 0; i < docList.size(); i++) {
			StatsDoc statsDoc = new StatsDoc();
			DocidAndDoc docidAndDoc = new DocidAndDoc();
			String docid = docList.get(i).getDocid();
			String docold = docList.get(i).getDoc();
			String doctitle = docList.get(i).getTitle();
			String docnew = null;
			int start = -1;
			int end = -1;
			String leftdoc = null;
			String rightdoc = null;
			String beginIndex1 = null;
			look:for (int c = 0; c < list.size(); c++) {
				ruleJson = jsonArray.getJSONObject(c);
				// System.out.println(ruleJson);
				String startWord = ruleJson.getString("start");
				String endWord = ruleJson.getString("end");
				String judge = ruleJson.getString("judge");
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
								// System.out.println(endWords.length);
								if (judge.equals("之前")) {
									end = start + rightdoc.indexOf(beginIndex) + beginIndex1.length();
								} else {
									end = start + rightdoc.indexOf(beginIndex) + beginIndex.length()
											+ beginIndex1.length();
								}
							} else {
								end = docold.length();
							}
							break look;
						}
					}
				}
			}
			if (end == -1) {
				statsDoc.setStats("不符合");
				docidAndDoc.setDocid(docid);
				docidAndDoc.setTitle(doctitle);
				statsDoc.setDocidAndDoc(docidAndDoc);
				statsDocs.add(statsDoc);
			} else {
				if (end != -1) {
					docnew = docold.substring(start, end);
				} else if (end == 0) {
					docnew = docold.substring(start, docold.length());
				}
				statsDoc.setStats("符合");
				docidAndDoc.setDoc(docnew);
				docidAndDoc.setDocid(docid);
				docidAndDoc.setTitle(doctitle);
				statsDoc.setDocidAndDoc(docidAndDoc);
				statsDocs.add(statsDoc);
				/*
				 * for (int j = 0; j < statsDocs.size(); j++) {
				 * if(statsDocs.get(j).getDocidAndDoc().getDocid().equals(
				 * "docid") && statsDocs.get(j).getStats().equals("不符合")){
				 * statsDocs.remove(j); } }
				 */
			}
		}
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return statsDocs;
	}

	// 开始规则格式化
	public Pattern startRuleFomat(String startWords) {
		String reg_charset = null;
		String[] start = startWords.split(",|，");
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
		String[] end = endWords.split(",|，");
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
