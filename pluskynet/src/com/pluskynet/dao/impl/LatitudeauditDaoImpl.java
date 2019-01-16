package com.pluskynet.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.pluskynet.dao.LatitudeauditDao;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.otherdomain.CauseAndName;
import com.pluskynet.util.PageNoUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class LatitudeauditDaoImpl extends HibernateDaoSupport implements LatitudeauditDao {

	@Override
	public void update(Latitudeaudit latitudeaudit) {
		if (latitudeaudit.getRule().contains("刑事")) {
			latitudeaudit.setCasetype(1);
		} else {
			latitudeaudit.setCasetype(0);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hql = "from Latitudeaudit where latitudeid = ? and latitudetype = ?";
		List<Latitudeaudit> latitudeaudits = this.getHibernateTemplate().find(hql, latitudeaudit.getLatitudeid(),
				latitudeaudit.getLatitudetype());
		if (latitudeaudits.size() > 0) {
			hql = "update Latitudeaudit set latitudeid = ? ,latitudetype = ?, latitudename = ? ,rule = ? ,stats = '0',subtime = ?,subuserid = ?,casetype = ? where id = ?";
			this.getHibernateTemplate().bulkUpdate(hql, latitudeaudit.getLatitudeid(), latitudeaudit.getLatitudetype(),
					latitudeaudit.getLatitudename(), latitudeaudit.getRule(), Timestamp.valueOf(df.format(new Date())),
					latitudeaudit.getSubuserid(), latitudeaudit.getCasetype(), latitudeaudits.get(0).getId());
		} else {
			String sql = "from Latitudeaudit where latitudetype = ? and casetype = ?";
			List<Latitudeaudit> list = this.getHibernateTemplate().find(sql, latitudeaudit.getLatitudetype(),
					latitudeaudit.getCasetype());
			if (list.size() > 0) {
				latitudeaudit.setBatchstats(list.get(0).getBatchstats());
			} else {
				latitudeaudit.setBatchstats("0");
			}
			latitudeaudit.setStats("0");
			latitudeaudit.setSubtime(Timestamp.valueOf(df.format(new Date())));
			this.getHibernateTemplate().save(latitudeaudit);
		}
	}

	@Override
	@Transactional
	public List<CauseAndName> getLatitudeauditList(int page, int rows) throws SQLException {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int toatl = 0;
		toatl = (page - 1) * rows;
		String sql = "select id,latitudeid,latitudename,latitudetype,stats,batchstats,subtime from latitudeaudit order by id asc";
		List<CauseAndName> list = new ArrayList<CauseAndName>();
		statement = conn.prepareStatement(sql);
		resultSet = statement.executeQuery();
		while (resultSet.next()) {
			CauseAndName causeAndName = new CauseAndName();
			causeAndName.setId(resultSet.getInt("id"));
			causeAndName.setLatitudeid(resultSet.getString("latitudeid"));
			causeAndName.setCausename(resultSet.getString("latitudename"));// 规则名称
			causeAndName.setLatitudetype(resultSet.getInt("latitudetype"));
			causeAndName.setRulestats(resultSet.getString("stats"));
			causeAndName.setBatchstat(resultSet.getString("batchstats"));
			causeAndName.setSubtime(resultSet.getString("subtime"));
			list.add(causeAndName);
		}
		Session session1 = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = "select * from latitude";
		List<Latitude> latitudelist = session1.createSQLQuery(hql).addEntity(Latitude.class).list();
		for (int i = 0; i < list.size(); i++) {
			String latitudeid = list.get(i).getLatitudeid();
			String latitudefname = null;
			for (int j = 0; j < latitudelist.size(); j++) {
				if (latitudelist.get(j).getLatitudeid().toString().equals(latitudeid)) {
					String latitudefid = latitudelist.get(j).getLatitudefid().toString();
					while (!latitudefid.equals("0")) {
						for (int k = 0; k < latitudelist.size(); k++) {
							if (latitudefid.equals(latitudelist.get(k).getLatitudeid().toString())) {
								if (latitudefname == null) {
									latitudefname = latitudelist.get(k).getLatitudename();
								} else {
									latitudefname = latitudelist.get(k).getLatitudename() + "-" + latitudefname;
								}
								latitudefid = latitudelist.get(k).getLatitudefid().toString();
								break;
							}
						}
						if (latitudelist.get(j).getLatitudefid().toString().equals(latitudefid)) {
							latitudefid = "0";
						}

					}
					list.get(i).setFcasename(latitudefname);
					break;
				}
			}
		}
		/*
		 * String hql =
		 * "select SUM(num) as num from (select COUNT(1) as num from article11 union all "
		 * + "select COUNT(1) as num from article12 union all " +
		 * "select COUNT(1) as num from article13 union all " +
		 * "select COUNT(1) as num from article14 union all " +
		 * "select COUNT(1) as num from article15 union all " +
		 * "select COUNT(1) as num from article16 union all " +
		 * "select COUNT(1) as num from article17 union all " +
		 * "select COUNT(1) as num from article18 union all " +
		 * "select COUNT(1) as num from article19 union all " +
		 * "select COUNT(1) as num from article20 )a ;";// 民事文书总数 statement =
		 * conn.prepareStatement(hql); resultSet = statement.executeQuery(); int
		 * znum = 0; while (resultSet.next()) { znum = resultSet.getInt("num");
		 * } String hqlString =
		 * "select latitudeid,nums as fhnum from latitudenum"; statement =
		 * conn.prepareStatement(hqlString); resultSet =
		 * statement.executeQuery(); while (resultSet.next()) { for (int i = 0;
		 * i < list.size(); i++) { list.get(i).setSunnum(znum); if
		 * (list.get(i).getLatitudeid().equals(resultSet.getString("latitudeid")
		 * )) { Integer cornum = resultSet.getInt("fhnum");
		 * list.get(i).setCornum(cornum); Integer ncornum = znum - cornum;
		 * list.get(i).setNcornum(ncornum); continue; } } }
		 */
		session.flush();
		session.clear();
		return list;
	}

	@Override
	@Transactional
	public int getCountBy() {
		String hqlString = "select latitudeid,latitudename,latitudetype,stats,batchstats from latitudeaudit";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<String> latitudeaudits = session.createSQLQuery(hqlString).list();
		return latitudeaudits.size();
	}

	@Override
	public String updateState(String latitudeids) {
		if (StringUtils.isNotBlank(latitudeids)) {
			String hql = "from Latitudeaudit where id in (";
			String[] latitudeid = latitudeids.split(",");
			for (int a = 0; a < latitudeid.length; a++) {
				if (a == latitudeid.length - 1) {
					hql = hql + latitudeid[a] + ")";
				} else {
					hql = hql + latitudeid[a] + ",";
				}
			}
			List<Latitudeaudit> latitudeaudits = this.getHibernateTemplate().find(hql);
			for (int i = 0; i < latitudeaudits.size(); i++) {
				hql = "update Latitudeaudit set stats = 1 where id = ?";
				this.getHibernateTemplate().bulkUpdate(hql, latitudeaudits.get(i).getId());
				if (i == latitudeaudits.size() - 1) {
					return "成功";
				}
			}

		}
		return "失败";
	}

	/**
	 * 
	 * 使用hql 语句进行操作
	 * 
	 * @param hql
	 *            需要执行的hql语句
	 * @param offset
	 *            设置开始位置
	 * @param length
	 *            设置读取数据的记录条数
	 * @return List 返回所需要的集合。
	 */

	public List<?> getListForPage(final String hql, final int offset, final int length) {
		List<?> list1 = getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List<?> list2 = PageNoUtil.getList(session, hql, offset, length);
				return list2;
			}
		});
		return list1;
	}

	@Override
	public List<Latitudeaudit> getLatitude(int latitudetype) {
		String hql = "from Latitudeaudit where stats = '1' and latitudetype=" + latitudetype;
		List<Latitudeaudit> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	@Transactional
	public void updatebatchestats(List<Latitudeaudit> latitudeaudit) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		for (int i = 0; i < latitudeaudit.size(); i++) {
			String hql = "from Latitudeaudit where latitudeid = ? and latitudetype = ? and casetype = 1";
			List<Latitudeaudit> latitudeaudits = this.getHibernateTemplate().find(hql,
					latitudeaudit.get(i).getLatitudeid(), latitudeaudit.get(i).getLatitudetype());
			if (latitudeaudits.size() > 0) {
				hql = "update latitudeaudit set batchstats = '" + latitudeaudit.get(i).getBatchstats() + "',stats='"
						+ latitudeaudit.get(i).getStats() + "' where id = '" + latitudeaudits.get(0).getId() + "'";
				session.createSQLQuery(hql).executeUpdate();
			}
		}
		System.out.println(latitudeaudit.get(0).getBatchstats());
		String sql = "update latitudeaudit set batchstats = '" + latitudeaudit.get(0).getBatchstats()
				+ "' where casetype = 1";
		session.createSQLQuery(sql).executeUpdate();
	}

	@Override
	@Transactional
	public List<DocidAndDoc> getDocList(String cause, int latitudetype, int num, int rows, int page, int ruleid) {
		String sql = null;
		String tablename = null;
		int toatl = 0;
		toatl = (page - 1) * rows;
		// int nums = page * rows;
		int tablenum = 0;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<DocidAndDoc> list = new ArrayList<DocidAndDoc>();
		String hql = "select DISTINCT(doctable) from cause where type = " + latitudetype + " and doctable<>''";
		List<String> causelist = session.createSQLQuery(hql).list();
		if (latitudetype == 0) {
			if (num == 1) {
				for (int i = 0; i < causelist.size(); i++) {
					String sqls = "select count(1) from " + causelist.get(i) + " where ruleid = " + ruleid;
					List<BigInteger> tanum = session.createSQLQuery(sqls).list();
					int nums = Integer.parseInt(tanum.get(0).toString());
					tablenum = tablenum + nums;
					if (toatl > tablenum) {
						rows = (toatl - tablenum) % rows;
						toatl = toatl - tablenum;
						continue;
					}
					sql = "SELECT `documentsid` ,`sectiontext` ,`title` from docidandruleid a  left join " + ""
							+ causelist.get(i) + " b on a.`docid` = b.`documentsid` " + "where a.ruleid = " + ruleid
							+ " LIMIT " + toatl + "," + rows + ";";
					try {
						statement = conn.prepareStatement(sql);
						resultSet = statement.executeQuery();
						while (resultSet.next()) {
							DocidAndDoc docidAndDoc = new DocidAndDoc();
							docidAndDoc.setDoc(resultSet.getString("sectiontext"));
							docidAndDoc.setDocid(resultSet.getString("documentsid"));
							docidAndDoc.setTitle(resultSet.getString("title"));
							list.add(docidAndDoc);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (list.size() == rows) {
						break;
					}
					/*
					 * else { rows = rows - list.size(); }
					 */
				}
			} else if (num == 2) {
				for (int i = 0; i < causelist.size(); i++) {
					String sqls = "select count(1) from " + causelist.get(i) + " where ruleid = " + ruleid;
					int tanum = session.createSQLQuery(sqls).executeUpdate();
					tablenum = tablenum + tanum;
					if (toatl > tablenum) {
						rows = (toatl - tablenum) % rows;
						toatl = toatl - tablenum;
						continue;
					}
					sql = "SELECT `documentsid` ,`sectiontext` ,`title` from " + causelist.get(i)
							+ " where a.ruleid <> " + ruleid + " LIMIT " + toatl + "," + rows + ";";
					try {
						statement = conn.prepareStatement(sql);
						resultSet = statement.executeQuery();
						while (resultSet.next()) {
							DocidAndDoc docidAndDoc = new DocidAndDoc();
							docidAndDoc.setDoc(resultSet.getString("sectiontext"));
							docidAndDoc.setDocid(resultSet.getString("documentsid"));
							docidAndDoc.setTitle(resultSet.getString("title"));
							list.add(docidAndDoc);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/*
					 * if (list.size() == rows) { break; } else { rows = rows -
					 * list.size(); }
					 */
				}
			}
			// else {
			// sql = "select doc_id documentsid,'' sectiontext,`title` from " +
			// causetable
			// + " group by `documentsid`,`title` LIMIT " + toatl + "," + rows +
			// ";";
			// }

		}
		return list;
	}

	@Override

	@Transactional
	public int getDocby(String cause, int latitudetype, int num, int ruleid) {
		String sql = null;
		String tablename = null;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<DocidAndDoc> list = new ArrayList<DocidAndDoc>();
		// String causetable = cause.getCausetable();
		// String doctable = cause.getDoctable();
		int count = 0;
		if (latitudetype == 0) {
			if (num == 1) {
				sql = "select ruleid,COUNT(1) as num from docidandruleid where ruleid = " + ruleid;
			} else if (num == 2) {
				sql = "select SUM(num) as num from (select COUNT(1) as num from article01 where ruleid <> " + ruleid
						+ " union all " + "select COUNT(1) as num from article02 where ruleid <> " + ruleid
						+ " union all " + "select COUNT(1) as num from article03 where ruleid <> " + ruleid
						+ " union all " + "select COUNT(1) as num from article04 where ruleid <> " + ruleid
						+ " union all " + "select COUNT(1) as num from article05 where ruleid <> " + ruleid
						+ " union all " + "select COUNT(1) as num from article06 where ruleid <> " + ruleid
						+ " union all " + "select COUNT(1) as num from article07 where ruleid <> " + ruleid
						+ " union all " + "select COUNT(1) as num from article08 where ruleid <> " + ruleid
						+ " union all " + "select COUNT(1) as num from article09 where ruleid <> " + ruleid
						+ " union all " + "select COUNT(1) as num from article10 where ruleid <> " + ruleid + " )a";
			} else {
				// sql = "select count(1) as num from " + causetable + ";";
			}
			try {
				statement = conn.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					count = resultSet.getInt("num");
				}
			} catch (SQLException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	@Transactional
	public String getDoc(String cause, int latitudetype, String docid) {
		String tablename = null;
		String html = null;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String hqls = "select DISTINCT(doctable) from cause where type = " + latitudetype + " and doctable<>''";
		List<String> doclist = session.createSQLQuery(hqls).list();
		String causehql = "select DISTINCT(causetable) from cause where type = " + latitudetype + " and doctable<>''";
		List<String> causelist = session.createSQLQuery(causehql).list();
		if (latitudetype == 0) {
			for (int i = 0; i < doclist.size(); i++) {
				String sql = "select sectiontext from " + doclist.get(i) + " where documentsid = '" + docid
						+ "' and sectionname = '" + cause + "';";
				List<String> list2 = session.createSQLQuery(sql).list();
				if (list2.size() > 0) {
					break;
				}
			}
			for (int i = 0; i < causelist.size(); i++) {
				String hql = "select decode_data from " + causelist.get(i) + " where doc_id = '" + docid + "'";
				List<String> list = session.createSQLQuery(hql).list();
				if (list.size() > 0) {
					html = list.get(0);
					break;
				}
			}
		}
		JSONObject jsonObject = new JSONObject().fromObject(html);
		JSONObject jsonObject2 = JSONObject.fromObject(jsonObject.getString("htmlData"));
		html = jsonObject2.getString("Html");
		return html;
	}
}
