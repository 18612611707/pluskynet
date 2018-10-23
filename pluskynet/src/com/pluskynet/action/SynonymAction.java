package com.pluskynet.action;

import java.util.List;

import com.pluskynet.domain.Synonymtypetable;
import com.pluskynet.domain.Synonymwordtable;
import com.pluskynet.domain.User;
import com.pluskynet.service.SynonymService;
import com.pluskynet.util.BaseAction;

@SuppressWarnings("all")
public class SynonymAction extends BaseAction {
	private Synonymtypetable synonymtypetable;
	private Synonymwordtable synonymwordtable;

	@Override
	public Object getModel() {
		synonymtypetable = new Synonymtypetable();
		return synonymtypetable;
	}

	private SynonymService synonymService;

	public void setSynonymService(SynonymService synonymService) {
		this.synonymService = synonymService;
	}

	public void getTypeList() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		List<Synonymtypetable> list = synonymService.getTypeList();
		outJsonByMsg(list, "成功");
	}

	public void getSynonym() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		List<Synonymwordtable> list = synonymService.getSynonym(synonymtypetable);
		outJsonByMsg(list, "成功");
	}

	public void saveType() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = synonymService.saveType(synonymtypetable);
		outJsonByMsg(msg);
	}

	public void save() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = synonymService.save(synonymtypetable);
		outJsonByMsg(msg);
	}
}
