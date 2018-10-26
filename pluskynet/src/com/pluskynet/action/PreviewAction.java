package com.pluskynet.action;
import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Preview;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.domain.User;
import com.pluskynet.service.PreviewService;
import com.pluskynet.util.BaseAction;
@SuppressWarnings("all")
public class PreviewAction extends BaseAction{
	private List<StatsDoc> doclist;
	public List<StatsDoc> getDoclist() {
		return doclist;
	}

	public void setDoclist(List<StatsDoc> doclist) {
		this.doclist = doclist;
	}
	private String rule;

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	private Preview preview = new Preview();
	@Override
	public Preview getModel() {
		// TODO Auto-generated method stub
		return preview;
	}
	private PreviewService previewService;
	
	public void setPreviewService(PreviewService previewService) {
		this.previewService = previewService;
	}
	private String docid;
	

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	/*
	 * 规则预览
	 */
	public void getDocList(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		if(preview.getRule()==null || preview.getRule().equals("")){
			outJsonByMsg("失败");
		 }else{
		List<StatsDoc> list = previewService.getDocList(preview,user);
		outJsonByMsg(list, "成功");
		}
	}
	public void getDoc(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		Map<String, Object> map= previewService.getDoc(docid,preview.getRule());
//		outJsonByMsg(list, list.size());
		outJsonByMsg(map,"成功");
	}
}
