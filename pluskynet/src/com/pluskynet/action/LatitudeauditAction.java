package com.pluskynet.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.otherdomain.CauseAndName;
import com.pluskynet.service.LatitudeauditService;
import com.pluskynet.test.OtherRule;
import com.pluskynet.util.BaseAction;

import javassist.expr.NewArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class LatitudeauditAction extends BaseAction {
	private Latitudeaudit latitudeaudit = new Latitudeaudit();

	@Override
	public Object getModel() {
		return latitudeaudit;
	}

	private String batchstat;

	public String getBatchstat() {
		return batchstat;
	}

	public void setBatchstat(String batchstat) {
		this.batchstat = batchstat;
	}

	private LatitudeauditService latitudeauditService;

	public void setLatitudeauditService(LatitudeauditService latitudeauditService) {
		this.latitudeauditService = latitudeauditService;
	}

	public void getLatitudeList() {
		List<CauseAndName> map = latitudeauditService.getLatitudeList(this.getPage(), this.getRows());
		int totalSize = latitudeauditService.getCountBy();
		outJsonByPage(map, totalSize, "成功", "yyyy-MM-dd HH:mm:ss");
		// outJsonByMsg(map,"成功");
	}

	/*
	 * 修改审核表规则
	 */
	public void update() {
		latitudeauditService.update(latitudeaudit);
		outJsonByMsg("成功");
	}

	private String latitudeids;

	public String getLatitudeids() {
		return latitudeids;
	}

	public void setLatitudeids(String latitudeids) {
		this.latitudeids = latitudeids;
	}

	public void updateStats() {
		String msg = latitudeauditService.updateStats(latitudeids);
		outJsonByMsg(msg);
	}

	/*
	 * 跑批修改状态
	 */
	private String list;
	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void updatebatchestats(List<Latitudeaudit> latitudeaudit) {
		latitudeauditService.updatebatchestats(latitudeaudit);
		// outJsonByMsg("成功");
	}

	/*
	 * 获取已审批规则
	 */
	public List<Latitudeaudit> getLatitude(int latitudetype) {
		List<Latitudeaudit> list = latitudeauditService.getLatitude(latitudetype);
		return list;
	}

	private int latitudetype;
	private int num; // 0，总数 1、匹配数 2、不匹配数
	private String causename;
	private int ruleid;

	public int getRuleid() {
		return ruleid;
	}

	public void setRuleid(int ruleid) {
		this.ruleid = ruleid;
	}

	public int getLatitudetype() {
		return latitudetype;
	}

	public void setLatitudetype(int latitudetype) {
		this.latitudetype = latitudetype;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCausename() {
		return causename;
	}

	public void setCausename(String causename) {
		this.causename = causename;
	}

	private String docid;

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	/*
	 * 获取符合、不符合列表
	 */
	public void getDocList() {
		List<DocidAndDoc> list = null;
		if (causename == null) {
			outJsonByMsg("成功");
		} else {
			list = latitudeauditService.getDocList(causename, latitudetype, num, this.getRows(), this.getPage(),
					ruleid);
			int size = latitudeauditService.getDocby(causename, latitudetype, num, ruleid);
			outJsonByPage(list, size, "成功", "");
		}
	}

	public void getDoc() {
		String htmlString = latitudeauditService.getDoc(causename, latitudetype, docid);
		Map<String, String> map = new HashMap<String, String>();
		map.put("data", htmlString);
		outJsonByMsg(map, "成功");
	}

	/*
	 * 循环跑批控制
	 */
	public void latitudeRun() {
		for (int i = 0; i < 50; i++) {
			OtherRule otherrule = new OtherRule("线程名称：" + i);
			String msg = otherrule.main(-1);
			if (msg==null) {
				otherrule.start();
			}
		}
		outJsonByMsg("成功");
	}

	/*
	 * 增量跑批控制
	 */
	public void newlatitudeRun() {
		for (int i = 0; i < 50; i++) {
			OtherRule otherrule = new OtherRule("线程名称：" + i);
			String msg = otherrule.main(0);
			if (msg==null) {
				otherrule.start();
			}
		}
		outJsonByMsg("成功");
	}
}
