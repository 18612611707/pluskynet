package com.pluskynet.service;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Dictionary;
import com.pluskynet.domain.User;

public interface DictionaryService {

	List<Map> getDicList();

	String addDic(Dictionary dictionary, User user);

	String updateDic(Dictionary dictionary, User user);

	String deleteDic(Dictionary dictionary);

	List<Map> getDicname(String code);

	List<Map> getNextdic(Integer id);

	List<Map> getCodedic(String code);

}
