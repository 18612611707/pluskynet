package com.pluskynet.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Docrule;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Preview;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.domain.User;
import com.pluskynet.service.DocRuleService;
import com.pluskynet.service.PreviewService;
import com.pluskynet.util.BaseAction;

import javassist.expr.NewArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class DocRuleAction extends BaseAction {
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// 接收前端传过来的值
	private Integer ruleid;

	public Integer getRuleid() {
		return ruleid;
	}

	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	private String sectionName;
	private String rule;
	private JSONObject queryresult;
	private List<Map> sectionlist;

	public List<Map> getSectionlist() {
		return sectionlist;
	}

	public void setSectionlist(List<Map> sectionlist) {
		this.sectionlist = sectionlist;
	}

	public JSONObject getQueryresult() {
		return queryresult;
	}

	public void setQueryresult(JSONObject queryresult) {
		this.queryresult = queryresult;
	}

	private Docrule docrule = new Docrule();

	@Override
	public Docrule getModel() {
		return docrule;
	}

	private DocRuleService docRuleService;

	public void setDocRuleService(DocRuleService docRuleService) {
		this.docRuleService = docRuleService;
	}

	private List<Map> q_result;

	public List<Map> getQ_result() {
		return q_result;
	}

	public void setQ_result(List<Map> q_result) {
		this.q_result = q_result;
	}

	Map map = new HashMap();
	private PreviewService previewService;

	public PreviewService getPreviewService() {
		return previewService;
	}

	public void setPreviewService(PreviewService previewService) {
		this.previewService = previewService;
	}

	private String causeo;
	private String causet;
	private String spcx;
	private String doctype;

	public String getCauseo() {
		return causeo;
	}

	public void setCauseo(String causeo) {
		this.causeo = causeo;
	}

	public String getCauset() {
		return causet;
	}

	public void setCauset(String causet) {
		this.causet = causet;
	}

	public String getSpcx() {
		return spcx;
	}

	public void setSpcx(String spcx) {
		this.spcx = spcx;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	/*
	 * 保存段落名称
	 */
	public void save() {
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = null;
		if (docrule.getSectionname() == null) {
			docrule.setSectionname(sectionName);
			msg = docRuleService.save(docrule);
		} else {
			msg = docRuleService.save(docrule);
		}
		outJsonByMsg(msg);
	}

	/*
	 * 修改名称或规则
	 */
	public void update() {
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = null;
		if (docrule.getRuleid() == null) {
			msg = "失败";
		} else {
			msg = docRuleService.update(docrule);
			if (msg == "成功") {
				Preview preview = new Preview();
				preview.setRule(docrule.getRule());
				String sectionname = docrule.getSectionname();
				List<StatsDoc> list = previewService.getDocList(preview);
				int a = 0;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getStats().equals("符合")) {
						Docsectionandrule docsectionandrule = new Docsectionandrule();
						docsectionandrule.setRuleid(docrule.getRuleid());
						docsectionandrule.setTitle(list.get(i).getDocidAndDoc().getTitle());
						docsectionandrule.setDocumentsid(list.get(i).getDocidAndDoc().getDocid());
						docsectionandrule.setSectionname(sectionname);
						docsectionandrule.setSectiontext(list.get(i).getDocidAndDoc().getDoc());
						docsectionandrule.setBelonguser(user.getUsername());
						docsectionandrule.setBelongid(user.getUserid());
						if (a == 0) {
							docRuleService.saveyldelete(sectionname,user);
							docRuleService.saveyl(docsectionandrule);
							a++;
						} else {
							docRuleService.saveyl(docsectionandrule);
						}
					}
				}
			}
		}
		outJsonByMsg(msg);
	}

	/*
	 * 查询规则段落列表
	 */
	public void getDocSectionList() {
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		List<Map> list = docRuleService.getDcoSectionList();
		outJsonByMsg(list, "成功");
	}

	/*
	 * 根据ID查询规则详细
	 */
	public void getDocSection() {
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = "失败";
		if (docrule.getRuleid() == null) {
			outJsonByMsg(msg);
		} else {
			map = docRuleService.getDcoSection(docrule);
			msg = "成功";
			outJsonByMsg(map, msg);
		}
	}

	/*
	 * 根据段落名称查询
	 */
	public void getSecNameShow() {
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = "成功";
		if (docrule.getSectionname() == null) {
			docrule.setSectionname(sectionName);
			List<Map> list = docRuleService.getSecNameShow(docrule.getSectionname());
			outJsonByMsg(list, msg);
		} else {
			List<Map> list = docRuleService.getSecNameShow(docrule.getSectionname());
			msg = "成功";
			outJsonByMsg(list, msg);
		}
	}

	/*
	 * 按照条件查询规则
	 */
	public void getRuleShow() {
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = "失败";
		if (docrule.getRuleid() == null) {
			outJsonByMsg(msg);
		} else {
			List<Docrule> list = docRuleService.getRuleShow(docrule.getRuleid(), causeo, causet, spcx, doctype);
			msg = "成功";
			outJsonByMsg(list.get(0), msg);
		}
	}
}
