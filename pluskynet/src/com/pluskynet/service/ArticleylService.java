package com.pluskynet.service;

import java.util.List;

import com.pluskynet.domain.Articleyl;

public interface ArticleylService {

	public List<Articleyl> findPageBy();

	/**
	 * �?��
	 * @return
	 */
	public List<Articleyl> getArticles();

	/**
	 * @param article
	 * @param page
	 * @param rows
	 * @param sidx
	 * @param sord
	 * @return 分页查询
	 */
	public List<Articleyl> findPageBy(Articleyl article, int page, int rows, String sidx, String sord);

	/**
	 * @param article
	 * @return 查询总记录数
	 */
	public int getCountBy(Articleyl article);
}
