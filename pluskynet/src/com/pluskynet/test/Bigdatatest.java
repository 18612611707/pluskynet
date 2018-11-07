package com.pluskynet.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pluskynet.action.DocsectionandruleAction;
import com.pluskynet.action.LatitudeauditAction;
import com.pluskynet.action.PreviewAction;
import com.pluskynet.dao.ArticleDao;
import com.pluskynet.dao.BatchdataDao;
import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.DocidandruleidDao;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Batchdata;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.Docidandruleid;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.otherdomain.Otherdocrule;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 已分好表的的数据进行分段规则跑批
 */
@SuppressWarnings("all")
public class Bigdatatest extends Thread {
	static ClassPathXmlApplicationContext resource = null;
	static ArticleDao articleDao = null;
	static DocsectionandruleAction docrule = null;
	static BatchdataDao batchdataDao;
	// 创建一个静态钥匙
	static Object ob = "aa";// 值是任意的
	volatile private int a = 0;
	static ThreadPoolExecutor executor = null;

	static List<Latitudeaudit> Lalist = null;
	static LatitudeauditAction latitudeauditAction;

	public Bigdatatest(String name) {
		super(name);// 给线程起名字
	}
	public static void main(String[] args) {
		executor = new ThreadPoolExecutor(100, Integer.MAX_VALUE, 200, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		System.gc();
		resource = new ClassPathXmlApplicationContext("applicationContext.xml");
		int batchstats = 1;// 1:全部跑批规则 2:剩余跑批规则
		latitudeauditAction = (LatitudeauditAction) resource.getBean("latitudeauditAction");
		Lalist = latitudeauditAction.getLatitude(String.valueOf(batchstats), 0);// 获取已审批过的规则
		for (int i = 0; i < 20; i++) {
			Bigdatatest bigdatatest = new Bigdatatest("线程名称：" + i);
			bigdatatest.start();
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
		articleDao = (ArticleDao) resource.getBean("articleDao");
		PreviewAction previewAction = (PreviewAction) resource.getBean("previewAction");
		docrule = (DocsectionandruleAction) resource.getBean("docsectionandruleAction");
		batchdataDao = (BatchdataDao) resource.getBean("batchdataDao");
		DocidandruleidDao docidandruleidDao = (DocidandruleidDao) resource.getBean("docidandruleidDao");
		int allorre = 0;// 1:全部跑批 3:剩余跑批 0：新增跑批
		if (Lalist.size() > 0) {
			CauseDao causeDao = (CauseDao) resource.getBean("causeDao");
			// List<Article01> list = causeDao.getArticleList();
			String doctable = null;
			List<Cause> list = null;
			list = getValue();
			if (list.size()==0 ||list == null) {
				list = causeDao.getArticleList(1);// 获取表名,0:民事 1:刑事
			}
			System.out.println(list.get(0).getCausename());
			List<Article01> articleList = null;
			for (int i = 0; i < list.size(); i++) {
				if (allorre == 1) {
					articleDao.articleState(list.get(i).getCausetable(), 0);
				} else if (allorre == 3) {
					articleDao.articleState(list.get(i).getCausetable(), 3);
				}
				doctable = list.get(i).getDoctable();
				boolean runs = true;
				int rows = 2000;
				while (runs) {
					synchronized (ob) {
						System.out.println("线程名称：" + getName());
						articleList = articleDao.getArticle01List(list.get(i).getCausetable(), allorre, rows);// 获取文书列表	
					}
					if (articleList.size() == 0) {
						System.out.println(list.get(i).getCausetable() + "表无数据！！！");
						runs = false;
						continue;
					}
					Bigdatasave bigdatasave[] = new Bigdatasave[Lalist.size()];
					for (int j = 0; j < Lalist.size(); j++) {// 循环已审批规则
						List<Otherdocrule> lists = new ArrayList<Otherdocrule>();
						if (Lalist.get(j).getReserved()==null) {
							lists = ruleformat(Lalist.get(j).getRule()); // 规则整理
						}else{
							JSONArray jsonArray = JSONArray.fromObject(Lalist.get(j).getRule());
							for (int k = 0; k < jsonArray.size(); k++) {
								JSONObject ruleJson = JSONObject.fromObject(jsonArray.get(k));
								Otherdocrule otherdocrule = new Otherdocrule();
								otherdocrule.setJudge(ruleJson.getString("judge"));
								otherdocrule.setSpcx(ruleJson.getString("trialRound"));
								otherdocrule.setDoctype(ruleJson.getString("doctype"));
								otherdocrule.setStart(ruleJson.getString("start"));
								otherdocrule.setEnd(ruleJson.getString("end"));
								lists.add(otherdocrule);
							}
						}
						int ruleid = Lalist.get(j).getLatitudeid();
						String latitudename = Lalist.get(j).getLatitudename();
						String startword = null;
						String endword = null;
						String judges = null;
						List<Article01> articleLists = articleList;
						bigdatasave[j] = new Bigdatasave();
						bigdatasave[j].save(articleLists, articleDao, batchdataDao, docrule, docidandruleidDao,
								list.get(i).getDoctable(), lists, list.get(i).getCausename(),
								list.get(i).getCausetable(), ruleid, latitudename);
						bigdatasave[j].setName("线程名称:"+getName()+","+"规则线程：" + i + j);
						System.out.println(bigdatasave[j].getName());
						// executor.execute(bigdatasave[j]);
						// if(j==0){
						// System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
						// executor.getQueue().size()+"，已执行完任务数目："+executor.getCompletedTaskCount());
						// }
						bigdatasave[j].start();
						if (i == list.size() - 1) {
							Lalist.get(j).setBatchstats("3");
							latitudeauditAction.updatebatchestats(Lalist.get(j));
						}
					}
					for (int j = 0; j < bigdatasave.length; j++) {
						try {
							bigdatasave[j].join();
							System.out.println("线程名称:"+getName()+","+bigdatasave[j]+"结束");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		} else {
			System.out.println("无规则");
		}
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
			// article01.setDocId(articleList.get(i).getDocId());
			// JSONObject jsonObject4 =
			// JSONObject.fromObject(articleList.get(i));
			// String docid = jsonObject4.getString("docId");
			// articleDao.updateArticleState(article01.getDocId(), table, 1);
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
					// article01.setDate(articleList.get(i).getDate());
					article01.setDecodeData(jsonObject2.getString("Html"));
					// article01.setId(articleList.get(i).getId());
					// article01.setTitle(articleList.get(i).getTitle());
					list.add(article01);
					break;

				}
			}
			// if (title.indexOf(doctype) == -1 && !spcx.equals(trialRound)){
			// articleList.remove(i);
			// }
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

	public static List<Cause> getValue() {
		List<Cause> list = new ArrayList<Cause>();
		File directory = new File("");// 设定为当前文件夹
		try {
			File f = new File(directory.getAbsolutePath());
			System.out.println(f.getParent());// 获取绝对路径
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream("C:/users/administrator/bigdata.csv"), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] lines = line.split(";");
				for (int i = 0; i < lines.length; i++) {
					String[] tiao = lines[i].split(",");
					for (int b = 0; b < 1; b++) {
						Cause cause = new Cause();
						cause.setCausename(tiao[0].substring(1, tiao[0].length()));
						cause.setCausetable(tiao[1]);
						cause.setDoctable(tiao[2]);
						list.add(cause);
					}
				}
			}
		} catch (Exception e) {

			// TODO

		}
		return list;
	}

}
