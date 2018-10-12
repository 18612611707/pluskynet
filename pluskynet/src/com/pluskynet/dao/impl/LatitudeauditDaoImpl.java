package com.pluskynet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.dao.LatitudeauditDao;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.otherdomain.CauseAndName;
import com.pluskynet.util.PageNoUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class LatitudeauditDaoImpl extends HibernateDaoSupport implements LatitudeauditDao {

	@Override
	public void update(Latitudeaudit latitudeaudit) {
		String hql = "from Latitudeaudit where latitudeid = ?";
		List<Latitudeaudit> latitudeaudits = this.getHibernateTemplate().find(hql, latitudeaudit.getLatitudeid());
		if (latitudeaudits.size() > 0) {
			hql = "update Latitudeaudit set latitudeid = ? ,latitudetype = ?, latitudename = ? ,rule = ? ,stats = '0',batchstats='0' where id = ?";
			this.getHibernateTemplate().bulkUpdate(hql, latitudeaudit.getLatitudeid(), latitudeaudit.getLatitudetype(),
					latitudeaudit.getLatitudename(), latitudeaudit.getRule(), latitudeaudits.get(0).getId());
		} else {
			latitudeaudit.setStats("0");
			latitudeaudit.setBatchstats("0");
			this.getHibernateTemplate().save(latitudeaudit);
		}
	}

	@Override
	@Transactional
	public List<CauseAndName> getLatitudeauditList(int page,int rows) throws SQLException {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int toatl = 0;
		toatl = (page - 1) * rows;
		String sql = "select latitudeid,latitudename,latitudetype,stats,batchstats from latitudeaudit order by latitudeid asc limit "+ toatl+", "+rows+";";
		List<CauseAndName> list = new ArrayList<CauseAndName>();
		statement = conn.prepareStatement(sql);
		resultSet = statement.executeQuery();
		while (resultSet.next()) {
			CauseAndName causeAndName = new CauseAndName();
			causeAndName.setLatitudeid(resultSet.getString("latitudeid"));
			causeAndName.setCausetable(resultSet.getString("latitudename"));//规则名称
			causeAndName.setLatitudetype(resultSet.getInt("latitudetype"));
			causeAndName.setRulestats(resultSet.getString("stats"));
			causeAndName.setBatchstat(resultSet.getString("batchstats"));
			list.add(causeAndName);
		}
		String hql = "select SUM(num) as num from (select COUNT(1) as num from article01 union all "
				+ "select COUNT(1) as num from article02 union all "
				+ "select COUNT(1) as num from article03 union all "
				+ "select COUNT(1) as num from article04 union all "
				+ "select COUNT(1) as num from article05 union all "
				+ "select COUNT(1) as num from article06 union all "
				+ "select COUNT(1) as num from article07 union all "
				+ "select COUNT(1) as num from article08 union all "
				+ "select COUNT(1) as num from article09 union all "
				+ "select COUNT(1) as num from article10 )a ;";//民事文书总数
		statement = conn.prepareStatement(hql);
		resultSet = statement.executeQuery();
		int znum = 0;
		while (resultSet.next()) {
			znum = resultSet.getInt("num");
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getLatitudetype()==0) {
				String hqlString = "select ruleid,COUNT(1) as fhnum from docidandruleid where ruleid = "+list.get(i).getLatitudeid()+" ";
				statement = conn.prepareStatement(hqlString);
				resultSet = statement.executeQuery();
				list.get(i).setSunnum(znum);
				while (resultSet.next()) {
					Integer cornum = resultSet.getInt("fhnum");
					list.get(i).setCornum(cornum);
					Integer ncornum = znum - cornum;
					list.get(i).setNcornum(ncornum);
				}
			}
			
		}
//		String hqlString = "select ruleid,COUNT(1) from docidandruleid group by ruleid;";
		
//		List<CauseAndName> list2 = new ArrayList<CauseAndName>();
//		while (resultSet.next()) {
//			CauseAndName causeAndName = new CauseAndName();
//			causeAndName.setLatitudeid(resultSet.getString("latitudeid"));
//			causeAndName.setLatitudetype(resultSet.getInt("latitudetype"));
//			causeAndName.setCausename(resultSet.getString("cause"));
//			causeAndName.setRulestats(resultSet.getString("rulestats"));
//			causeAndName.setBatchstat(resultSet.getString("batchstats"));
//			list2.add(causeAndName);
//		}
//		String sql3 = "select b.`ruleid` as ruleid ,COUNT(1) sunnum,COUNT( b.`documentid` IS NULL  or null ) ncornum from "+
//				"(select id,doc_id from article01 "+
// "UNION ALL select id,doc_id from article02 "+
// "UNION ALL select id,doc_id from article03 "+
// "UNION ALL select id,doc_id from article04 "+
// "UNION ALL select id,doc_id from article05 "+
// "UNION ALL select id,doc_id from article06 "+
// "UNION ALL select id,doc_id from article07 "+
// "UNION ALL select id,doc_id from article08 "+
// "UNION ALL select id,doc_id from article09 "+
// "UNION ALL select id,doc_id from article10 "+
//") a LEFT JOIN `batchdata` b on a.doc_id = b.`documentid`  group by ruleid";
//		sql3 = "SELECT `latitudeid` as ruleid,b.`sectionName` as cause ,`latitudetype` as latitudetype,COUNT(c.`ruleid`) as cornum,`stats` ,`batchstats`   FROM `latitudeaudit` a left join `docrule` b on a.`latitudeid` =b.`ruleid` left join  (select ruleid,documentsid from `docsectionandrule01` "+
//"UNION ALL select ruleid,documentsid from `docsectionandrule02` "+
//"UNION ALL select ruleid,documentsid from `docsectionandrule03` "+
//"UNION ALL select ruleid,documentsid from `docsectionandrule04` "+
//"UNION ALL select ruleid,documentsid from `docsectionandrule05` "+
//"UNION ALL select ruleid,documentsid from `docsectionandrule06` "+
//"UNION ALL select ruleid,documentsid from `docsectionandrule07` "+
//"UNION ALL select ruleid,documentsid from `docsectionandrule08` "+
//"UNION ALL select ruleid,documentsid from `docsectionandrule09` "+
//"UNION ALL select ruleid,documentsid from `docsectionandrule10` ) c on a.`latitudeid`  = c.ruleid where a.rule<>'' group by latitudeid";
//		statement = conn.prepareStatement(sql3);
//		resultSet = statement.executeQuery();
//		while (resultSet.next()) {
//			for (int j = 0; j < list2.size(); j++) {
//				if (list2.get(j).getLatitudeid()==resultSet.getString("ruleid")) {
//					list2.get(j).setNcornum(resultSet.getInt("ncornum"));
//					list2.get(j).setSunnum(resultSet.getInt("sunnum"));
//					list2.get(j).setCornum(resultSet.getInt("cornum"));
//				}
//			}
//		}
		session.flush();
		session.clear();
		return list;
	}

	@Override
	@Transactional
	public int getCountBy() {
		String hqlString = "select latitudeid,latitudetype,b.cause cause,a.stats rulestats,a.batchstats as batchstats from latitudeaudit a left join batchdata b on a.latitudeid = b.ruleid group by latitudeid,latitudetype,b.cause,a.stats,a.batchstats";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<String> latitudeaudits = session.createSQLQuery(hqlString).list();
		return latitudeaudits.size();
	}

	@Override
	public String updateState(Latitudeaudit latitudeaudit) {
		String hql = "from Latitudeaudit where latitudeid = ? and latitudetype = ?";
		List<Latitudeaudit> latitudeaudits = this.getHibernateTemplate().find(hql, latitudeaudit.getLatitudeid(),
				latitudeaudit.getLatitudetype());
		if (latitudeaudits.size() > 0) {
			hql = "update Latitudeaudit set stats = ? where id = ?";
			this.getHibernateTemplate().bulkUpdate(hql, latitudeaudit.getStats(), latitudeaudits.get(0).getId());
			return "成功";
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
	public List<Latitudeaudit> getLatitude(String batchstats, int latitudetype) {
		String hql = "from Latitudeaudit where stats = '1' and latitudetype=" + latitudetype;
		List<Latitudeaudit> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public void updatebatchestats(Latitudeaudit latitudeaudit) {
		String hql = "from Latitudeaudit where latitudeid = ? and latitudetype = ?";
		List<Latitudeaudit> latitudeaudits = this.getHibernateTemplate().find(hql, latitudeaudit.getLatitudeid(),
				latitudeaudit.getLatitudetype());
		if (latitudeaudits.size() > 0) {
			hql = "update Latitudeaudit set batchstats = ? where id = ?";
			this.getHibernateTemplate().bulkUpdate(hql, latitudeaudit.getBatchstats(), latitudeaudits.get(0).getId());
		}

	}

	@Override
	@Transactional
	public List<DocidAndDoc> getDocList(Cause cause, int latitudetype, int num, int rows, int page, int ruleid) {
		String sql = null;
		String tablename = null;
		int toatl = 0;
		toatl = (page - 1) * rows;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<DocidAndDoc> list = new ArrayList<DocidAndDoc>();
		String causetable = cause.getCausetable();
		String doctable = cause.getDoctable();
		if (latitudetype == 0) {
			if (num == 1) {
				sql = "select `documentsid` ,`sectiontext` ,`title`  from " + doctable + "  where `ruleid` = " + ruleid
						+ " group by `documentsid` LIMIT " + toatl + "," + rows + ";";
			} else if (num == 2) {
				sql = "select doc_id documentsid,'' sectiontext,`title` from " + causetable
						+ " WHERE `doc_id`  not in (select documentsid from " + doctable + "  where `ruleid` " + ruleid
						+ ")group by `documentsid` LIMIT " + toatl + "," + rows + ";";
			} else {
				sql = "select doc_id documentsid,'' sectiontext,`title` from " + causetable
						+ "  group by `documentsid`,`title` LIMIT " + toatl + "," + rows + ";";
			}
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
		}
		return list;
	}

	@Override
	@Transactional
	public int getDocby(Cause cause, int latitudetype, int num, int ruleid) {
		String sql = null;
		String tablename = null;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<DocidAndDoc> list = new ArrayList<DocidAndDoc>();
		String causetable = cause.getCausetable();
		String doctable = cause.getDoctable();
		int count = 0;
		if (latitudetype == 0) {
			if (num == 1) {
				sql = "select count(1) as num  from " + causetable + "  where `ruleid` = " + ruleid
						+ " and states = 2;";
			} else if (num == 2) {
				sql = "select count(1) as num from " + causetable + " WHERE `doc_id`  not in (select documentsid from "
						+ doctable + "  where `ruleid` " + ruleid + ");";
			} else {
				sql = "select count(1) as num from " + causetable + ";";
			}
			try {
				statement = conn.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					count = resultSet.getInt("num");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	@Transactional
	public String getDoc(Cause cause, int latitudetype, String docid, int ruleid) {
		String tablename = null;
		String html = null;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String causetable = cause.getCausetable();
		String doctable = cause.getDoctable();
		if (latitudetype == 0) {
			String sql = "select sectiontext from " + doctable + " where ruleid = " + ruleid + " and documentsid = '"
					+ docid + "';";
			List<String> list2 = session.createSQLQuery(sql).list();
			String hql = "select decode_data from " + causetable + " where doc_id = '" + docid + "'";
			List<String> list = session.createSQLQuery(hql).list();
			html = list.get(0);
		}
		JSONObject jsonObject = new JSONObject().fromObject(html);
		JSONObject jsonObject2 = JSONObject.fromObject(jsonObject.getString("htmlData"));
		html = jsonObject2.getString("Html");
		return html;
	}

}
