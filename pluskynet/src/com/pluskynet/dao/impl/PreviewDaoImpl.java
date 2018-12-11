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
import com.pluskynet.rule.DocRule;

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
		DocRule docRule = new DocRule();
		Parsing parsing = new Parsing();
		// 文书拆分，返回docid和文书主文章信息
		List<DocidAndDoc> docList = new ArrayList<DocidAndDoc>();
		List<DocidAndDoc> docLists = null;
		List<StatsDoc> statsDocs = new ArrayList<StatsDoc>();
		JSONArray jsonArray = new JSONArray();
		JSONObject ruleJson = new JSONObject();
		jsonArray = jsonArray.fromObject(preview.getRule());
		Integer fhnum = 0;
		Integer bfhnum = 0;
		List<Otherdocrule> list = docRule.ruleFormat(jsonArray);
		/*String startword = null;
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
					boolean statrstats = true;
					boolean endstats = true;
					if (list.get(j).getJudge().equals(judges) && list.get(j).getSpcx().equals(spcx)
							&& list.get(j).getDoctype().equals(doctype)) {
						if (!startword.equals("")) {
							String[] startwords = list.get(j).getStart().split(";");
							for (int i = 0; i < startwords.length; i++) {
								if (!startwords[i].equals(startword)) {
									statrstats = true;
								} else {
									statrstats = false;
									break;
								}
							}
							if (statrstats) {
								list.get(j).setStart(list.get(j).getStart() + ";" + startword);
							}
						}
						if (!endword.equals("")) {
							String[] endwords = list.get(j).getEnd().split(";");
							for (int i = 0; i < endwords.length; i++) {
								if (!endwords[i].equals(endword)) {
									endstats = true;
								} else {
									endstats = false;
									break;
								}
							}
							if (endstats) {
								list.get(j).setEnd(list.get(j).getEnd() + ";" + endword);
							}
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
		}*/
		// for (int c = 0; c < list.size(); c++) {
		// jsonArray = JSONArray.fromObject(list);
		// preview.setRule(jsonArray.get(c).toString());
		docLists = parsing.DocList(listaArticles, preview);
		docList.addAll(docLists);
		// }
		
		for (int i = 0; i < docList.size(); i++) {
			StatsDoc statsDoc = new StatsDoc();
			DocidAndDoc docidAndDoc = new DocidAndDoc();
			String docid = docList.get(i).getDocid();
			String docold = docList.get(i).getDoc();
			String doctitle = docList.get(i).getTitle();
			String docnew = null;
			if (docid.equals("ff04275d-f7ba-4679-9b5b-5b056ce64b3a")) {
				System.out.println("aaaaaaaaaaaaa");
			}
			List<Integer> intlist = new ArrayList<Integer>();
			intlist.add(-1);
			intlist.add(-1);
			docRule.doclist(docold,intlist);
			int start = intlist.get(0);
			int end = intlist.get(1);
			/*String leftdoc = null;
			String rightdoc = null;
			String beginIndex1 = null;
			String before = null;
			int startAndEnd = 0;
			look: for (int c = 0; c < list.size(); c++) {
				ruleJson = JSONObject.fromObject(list.get(c));
				// System.out.println(ruleJson);
				String startWord = ruleJson.getString("start");
				String endWord = ruleJson.getString("end");

				String judge = ruleJson.getString("judge");
				String[] startWords = startWord.split(";|；");
				String[] endWords = endWord.split(";|；");
				for (int j = 0; j < startWords.length; j++) {
					if (startWords[j].contains("#")) {
						String startandends = startWords[j].substring(0, startWords[j].lastIndexOf("#"));
						String[] startandend = startandends.split("#");
						for (int k = 0; k < startandend.length; k++) {
							if (docold.contains(startandend[k])) {
								startAndEnd = docold.indexOf(startandend[k]);
								docold = docold.substring(docold.indexOf(startandend[k]));
							}
						}
					}
					if (startWords[j].contains("^")) {
						before = startWords[j].substring(0, startWords[j].indexOf("^"));
						startWords[j] = startWords[j].substring(startWords[j].indexOf("^") + 1);
					} else {
						before = null;
					}
					Pattern patternstart = startRuleFomat(startWords[j]);
					Matcher matcher = patternstart.matcher(docold);
					if (matcher.find()) {
						beginIndex1 = matcher.group();
						start = docold.indexOf(beginIndex1);
						leftdoc = docold.substring(0, docold.indexOf(beginIndex1) + beginIndex1.length());
						// System.out.println(leftdoc.length());
						StringBuffer s = new StringBuffer(leftdoc);
						leftdoc = s.reverse().toString();
						if (before != null) {
							start = start + beginIndex1.length() - leftdoc.indexOf(before);
						}
						rightdoc = docold.substring(start);
						// rightdoc =
						// docold.substring(docold.indexOf(beginIndex1) +
						// beginIndex1.length());
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
									String beginIndex = matcher.group();
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
									break look;
								}
							}
						}
					}
				}
			}*/
			if (end == -1) {
				statsDoc.setStats("不符合");
				docidAndDoc.setDocid(docid);
				docidAndDoc.setTitle(doctitle);
				statsDoc.setDocidAndDoc(docidAndDoc);
				statsDoc.setNum(bfhnum++);
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
				statsDoc.setNum(fhnum++);
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
