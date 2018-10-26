package com.pluskynet.action;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Dictionary;
import com.pluskynet.domain.User;
import com.pluskynet.service.DictionaryService;
import com.pluskynet.util.BaseAction;
import com.sun.star.chart2.Break;

public class DictionaryAction extends BaseAction{
	private Dictionary dictionary;
	@Override
	public Object getModel() {
		dictionary = new Dictionary();
		return null;
	}
	private DictionaryService dictionaryService;
	
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public void getDicList(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		List<Map> list = dictionaryService.getDicList();
		outJsonByMsg(list, "成功");
	}
	public void addDic(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = dictionaryService.addDic(dictionary,user);
		outJsonByMsg(msg);
	}
	public void updateDic(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = dictionaryService.updateDic(dictionary,user);
		outJsonByMsg(msg);
	}
	public void deleteDic(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = dictionaryService.deleteDic(dictionary);
		outJsonByMsg(msg);
	}

	public List<Map> getDicname(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return null;
		}
		List<Map> list = dictionaryService.getDicname(dictionary.getCode());
		return list;
	}
}
