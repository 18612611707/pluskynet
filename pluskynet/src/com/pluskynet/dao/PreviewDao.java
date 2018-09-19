package com.pluskynet.dao;



import java.util.List;

import com.pluskynet.domain.Articleyl;
import com.pluskynet.domain.Preview;
import com.pluskynet.domain.StatsDoc;



public interface PreviewDao {
	List<StatsDoc> getDocList(Preview preview,List<Articleyl> listaArticles);
}
