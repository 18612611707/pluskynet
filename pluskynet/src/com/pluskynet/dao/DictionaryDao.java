package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Dictionary;
import com.pluskynet.otherdomain.Dictionarytree;

public interface DictionaryDao {

	List<Dictionarytree> first();

	List<Dictionarytree> getNextSubSet(Dictionarytree voteTree);

	String addDic(Dictionary dictionary);

	String updateDic(Dictionary dictionary);

	String deleteDic(Dictionary dictionary);

	List<Dictionarytree> getDicname(String code);

	List<Dictionary> getNextdic(Integer id);

	List<Dictionary> getCodedic(String code);

}
