package com.pluskynet.action;

import java.util.ArrayList;
import java.util.HashMap;
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
		return dictionary;
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

	public void getDicname(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		String[] codes = dictionary.getCode().split(",");
		List<Map> lists = new ArrayList<>();
		for (int i = 0; i < codes.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map> list = dictionaryService.getDicname(codes[i]);
			map.put(codes[i], list);
			lists.add(map);
		}
		outJsonByMsg(lists,"成功");
	}
	/*
	 * 根据父级id查询子集列表
	 */
	public void getNextdic(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		List<Map> list = dictionaryService.getNextdic(dictionary.getId());
		outJsonByMsg(list,"成功");
	}
	/*
	 * 根据code查询本级信息
	 */
	public void getCodedic(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		List<Map> list = dictionaryService.getCodedic(dictionary.getCode());
		outJsonByMsg(list,"成功");
	}
}
