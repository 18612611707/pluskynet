package com.pluskynet.parsing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pluskynet.domain.Articleyl;
import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Documentsbasic;
import com.pluskynet.domain.Preview;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class Parsing {
	String title;// 文书标题
	Date pubdate;// 发布日期
	String Html;// 全文内容
	int courtid; // 法院id
	String tbfotc;// 案件基本情况段原文
	String ato;// 附加原文
	String tprocedure;// 审判程序
	String caseno;// 案号
	String reasonsnd;// 不公开理由
	String city;// 法院地市
	String province;// 法院省份
	String documentsid; // 文书id
	String firstpar;// 文本首部段落原文
	String nameotcase;// 案件名称
	String nameotcourt;// 法院名称
	String rmmessage;// 裁判要旨段原文
	String county;// 法院区县
	String receivingdoc;// 补正文书
	String doccontent;// DocContent
	String typeoftext;// 文书全文类型
	String oricourtrecord;// 诉讼记录段原文
	String originaljudgment;// 判决结果段原文
	String endtext;// 文本尾部原文
	String casetype;// 案件类型
	String litigant;// 诉讼参与人信息部分原文
	String documenttype;// 文书类型
	Date thedataof;// 裁判日期
	String closedmanner;// 结案方式
	String effhierarchy;// 效力层级
	Date toactdate;// 受理日期
	Date trialdate;// 开庭日期
	String tcoa;// 案由
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<Documentsbasic> JosnPar(List<Articleyl> list) {
		List<Documentsbasic> listDoc = new ArrayList<Documentsbasic>();
		if (list.size() == 0) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			documentsid = list.get(i).getDocId();

			JSONObject jsonObject = new JSONObject().fromObject(list.get(i).getDecodeData());
			JSONObject jsonObject2 = jsonObject.getJSONObject("htmlData");
			JSONObject jsonObject3 = jsonObject.getJSONObject("caseinfo");
			title = jsonObject2.getString("Title");
			// if(title.contains("民事")){
			String pubdate01 = jsonObject2.getString("PubDate");
			// Html = jsonObject2.getString("Html");
			documentsid = jsonObject3.getString("文书ID");
			courtid = jsonObject3.getInt("法院ID");
			tbfotc = jsonObject3.getString("案件基本情况段原文");
			ato = jsonObject3.getString("附加原文");
			tprocedure = jsonObject3.getString("审判程序");
			caseno = jsonObject3.getString("案号");
			reasonsnd = jsonObject3.getString("不公开理由");
			city = jsonObject3.getString("法院地市");
			province = jsonObject3.getString("法院省份");
			firstpar = jsonObject3.getString("文本首部段落原文");
			nameotcase = jsonObject3.getString("案件名称");
			nameotcourt = jsonObject3.getString("法院名称");
			rmmessage = jsonObject3.getString("裁判要旨段原文");
			county = jsonObject3.getString("法院区县");
			receivingdoc = jsonObject3.getString("补正文书");
			doccontent = jsonObject3.getString("DocContent");
			typeoftext = jsonObject3.getString("文书全文类型");
			oricourtrecord = jsonObject3.getString("诉讼记录段原文");
			originaljudgment = jsonObject3.getString("判决结果段原文");
			endtext = jsonObject3.getString("文本尾部原文");
			casetype = jsonObject3.getString("案件类型");
			litigant = jsonObject3.getString("诉讼参与人信息部分原文");
			documenttype = jsonObject3.getString("文书类型");
			closedmanner = jsonObject3.getString("结案方式");
			effhierarchy = jsonObject3.getString("效力层级");
			try {
				pubdate = sdf.parse(pubdate01);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Documentsbasic documentsbasic = new Documentsbasic(documentsid, courtid, tbfotc, ato, tprocedure, caseno,
					reasonsnd, city, province, firstpar, nameotcase, nameotcourt, rmmessage, county, receivingdoc,
					doccontent, typeoftext, oricourtrecord, originaljudgment, endtext, pubdate, casetype, litigant,
					documenttype, thedataof, closedmanner, effhierarchy, toactdate, trialdate, tcoa);
			listDoc.add(documentsbasic);

			// }

		}
		return listDoc;
	}

	public List<DocidAndDoc> DocList(List<Articleyl> list, Preview preview) {
		List<DocidAndDoc> docList = new ArrayList<DocidAndDoc>();
		for (int i = 0; i < list.size(); i++) {
			DocidAndDoc docidAndDoc = new DocidAndDoc();
			documentsid = list.get(i).getDocId();
			docidAndDoc.setDocid(documentsid);
			docidAndDoc.setTitle(list.get(i).getTitle());
			JSONObject jsonObject = new JSONObject().fromObject(list.get(i).getDecodeData());
			JSONObject jsonObject2 = jsonObject.getJSONObject("htmlData");
			JSONObject jsonObject3 = jsonObject.getJSONObject("caseinfo");
			String title = jsonObject2.getString("Title");
			String spcx = jsonObject3.getString("审判程序");
			String value = null;
			JSONObject jsonObject4 = new JSONObject().fromObject(jsonObject.getString("dirData"));
			JSONArray jsonArray1 = new JSONArray().fromObject(jsonObject4.getString("RelateInfo"));
			// JSONObject js = new JSONObject().fromObject(preview.getRule());
			// String trialRound = js.getString("spcx");
			// String doctype = js.getString("doctype");
			// if (title.indexOf(doctype) != -1 && spcx.equals(trialRound)) {
			String docTextString = jsonObject2.getString("Html");
			docidAndDoc.setDoc(getTextFromHtml(docTextString));
			docList.add(docidAndDoc);
			// }

		}
		return docList;
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
