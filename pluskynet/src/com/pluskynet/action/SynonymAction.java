package com.pluskynet.action;

import java.util.List;

import com.pluskynet.domain.Synonymtypetable;
import com.pluskynet.domain.Synonymwordtable;
import com.pluskynet.service.SynonymService;
import com.pluskynet.util.BaseAction;
@SuppressWarnings("all")
public class SynonymAction extends BaseAction{
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
	
	

	public void getTypeList(){
		List<Synonymtypetable> list = synonymService.getTypeList();
		outJsonByMsg(list, "成功");
	}
	public void getSynonym(){
		List<Synonymwordtable> list = synonymService.getSynonym(synonymtypetable);
		outJsonByMsg(list, "成功");
	}
	public void saveType(){
		String msg = synonymService.saveType(synonymtypetable);
		outJsonByMsg(msg);
	}
	public void save(){
		String msg = synonymService.save(synonymtypetable);
		outJsonByMsg(msg);
	}
}
