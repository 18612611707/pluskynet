package com.pluskynet.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.dao.LatitudeDocDao;
import com.pluskynet.domain.Docsectionandrule;

import com.pluskynet.otherdomain.LatitudeDocList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@SuppressWarnings("all")
public class LatitudeDocDaoImpl extends HibernateDaoSupport implements LatitudeDocDao {

	@Override
	@Transactional
	public int getCountBy(Docsectionandrule latitudedoc, String listLatitudedocs,String caseno,String courtname,String judges,String parties,String law,String lawyer,String legal,String dates,String date) {
		String hql = "select a.id from docsectionandrule a left join latitudedoc b on a.documentsid = b.documentid "
				+ "where 1=1 ";
		if(latitudedoc.getSectionname()!=null && !latitudedoc.getSectionname().equals("")){
			hql = hql +" and a.sectionname = '"+ latitudedoc.getSectionname() + "'";
		}
		if(latitudedoc.getSectiontext()!=null && !latitudedoc.getSectiontext().equals("")){
			hql = hql + " and a.sectiontext like '%" + latitudedoc.getSectiontext()+"%'";
		}
		if(caseno!=null && !caseno.equals("")){
			 hql = hql +" and (b.latitudename = '案号' and b.latitudetext like '"+caseno+"')";
		}
		if(courtname!=null && !courtname.equals("")){
			hql = hql +" and (b.latitudename = '法院名称' and b.latitudetext like '"+courtname+"')";
		}
		if(judges!=null && !judges.equals("")){
			hql = hql +" and (b.latitudename = '审判人员' and b.latitudetext like '"+judges+"')";
		}
		if(parties!=null && !parties.equals("")){
			hql = hql +" and (b.latitudename = '当事人' and b.latitudetext like '"+parties+"')";
		}
		if(law!=null && !law.equals("")){
			hql = hql +" and (b.latitudename = '律所' and b.latitudetext like '"+law+"')";
		}
		if(lawyer!=null && !lawyer.equals("")){
			hql = hql +" and (b.latitudename = '律师' and b.latitudetext like '"+lawyer+"')";
		}
		if(legal!=null && !legal.equals("")){
			hql = hql +" and (b.latitudename = '法律依据' and b.latitudetext like '"+legal+"')";
		}
		if(dates!=null && !dates.equals("")){
			hql = hql +" and (b.latitudename = '裁判日期' and b.latitudetext 〉='"+dates+"')";
		}
		if(date!=null && !date.equals("")){
			hql = hql +" and (b.latitudename = '裁判日期' and b.latitudetext <='"+date+"')";
		}
		if(listLatitudedocs!=null && !listLatitudedocs.equals("")){
			JSONArray jsonArray = JSONArray.fromObject(listLatitudedocs);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				hql = hql + " and b.latitudeid=" + jsonObject.getString("latitudeid")+ " ";
			}
		}
		List<String> list = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql).list();
		return list.size();
	}

	@Override
	@Transactional
	public List<LatitudeDocList> findPageBy(Docsectionandrule latitudedoc, int page, int rows,
			String listLatitudedocs,String caseno,String courtname,String judges,String parties,String law,String lawyer,String legal,String dates,String date) {
		String sql = "select a.id,a.ruleid,a.documentsid,a.sectionname,a.sectiontext,a.views,a.downloads,a.title from docsectionandrule a left join latitudedoc b on a.documentsid = b.documentid "
				+ "where 1=1";
		int toatl = 0;
		toatl = (page - 1) * rows;
		if(latitudedoc.getSectionname()!=null && !latitudedoc.getSectionname().equals("")){
			sql = sql +" and a.sectionname = '"+ latitudedoc.getSectionname() + "'";
		}
		if(latitudedoc.getSectiontext()!=null && !latitudedoc.getSectiontext().equals("")){
			sql = sql + " and a.sectiontext like '%" + latitudedoc.getSectiontext()+"%'";
		}
		if(caseno!=null && !caseno.equals("")){
			sql = sql +" and (b.latitudename = '案号' and b.latitudetext like '"+caseno+"')";
		}
		if(courtname!=null && !courtname.equals("")){
			sql = sql +" and (b.latitudename = '法院名称' and b.latitudetext like '"+courtname+"')";
		}
		if(judges!=null && !judges.equals("")){
			sql = sql +" and (b.latitudename = '审判人员' and b.latitudetext like '"+judges+"')";
		}
		if(parties!=null && !parties.equals("")){
			sql = sql +" and (b.latitudename = '当事人' and b.latitudetext like '"+parties+"')";
		}
		if(law!=null && !law.equals("")){
			sql = sql +" and (b.latitudename = '律所' and b.latitudetext like '"+law+"')";
		}
		if(lawyer!=null && !lawyer.equals("")){
			sql = sql +" and (b.latitudename = '律师' and b.latitudetext like '"+lawyer+"')";
		}
		if(legal!=null && !legal.equals("")){
			sql = sql +" and (b.latitudename = '法律依据' and b.latitudetext like '"+legal+"')";
		}
		if(dates!=null && !dates.equals("")){
			sql = sql +" and (b.latitudename = '裁判日期' and b.latitudetext 〉='"+dates+"')";
		}
		if(date!=null && !date.equals("")){
			sql = sql +" and (b.latitudename = '裁判日期' and b.latitudetext <='"+date+"')";
		}
		if(listLatitudedocs!=null && !listLatitudedocs.equals("")){
			JSONArray jsonArray = JSONArray.fromObject(listLatitudedocs);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				sql = sql + " and b.latitudeid=" + jsonObject.getString("latitudeid");
			}
		}
			sql = sql + " group by a.id  limit " + toatl + "," + rows + "";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();

		List<Docsectionandrule> list = s.createSQLQuery(sql).addEntity(Docsectionandrule.class).list();
//		s.close();
		getHibernateTemplate().clear();
		List<LatitudeDocList> mapList = new ArrayList<LatitudeDocList>();
		for (int i = 0; i < list.size(); i++) {
			LatitudeDocList docList = new LatitudeDocList();
			String sectionname = null;
			String sectiontext = null;
			sectionname = list.get(i).getSectionname();
			sectiontext = list.get(i).getSectiontext();
			if (sectiontext.contains(latitudedoc.getSectiontext())) {
				sectiontext = sectiontext.replaceAll(latitudedoc.getSectiontext(),
						"<span style=\"color:red\">" + latitudedoc.getSectiontext() + "</span>");
				docList.setDocumentsid(list.get(i).getDocumentsid());
				docList.setSectionText(sectiontext);
				docList.setTitle(list.get(i).getTitle());
				mapList.add(docList);
				continue;
			}
		}
		return mapList;
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

	// public List getListForPage(final String hql, final int offset, final int
	// length) {
	// List list1 = getHibernateTemplate().executeFind(new HibernateCallback() {
	// public Object doInHibernate(Session session) throws HibernateException,
	// SQLException {
	// List list2 = PageNoUtil.getList(session, hql);
	// return list2;
	// }
	// });
	// return list1;
	// }
	// public List<Docsectionandrule> getListForPage(final String sql) {
	// List viewRecordList = this.getHibernateTemplate().executeFind(
	// new HibernateCallback() {
	// public Object doInHibernate(Session session)
	// throws HibernateException, SQLException {
	// SQLQuery query = session
	// .createSQLQuery(sql);
	// return query.list();
	// }
	// });
	// return viewRecordList;
	// }

	@Override
	@Transactional
	public List<LatitudeDocList> getDoc(Docsectionandrule latitudedoc) {
		String hql = "from Latitudedoc where documentsid = ?";
		List<Docsectionandrule> list = this.getHibernateTemplate().find(hql, latitudedoc.getDocumentsid());
		List<LatitudeDocList> lists = new ArrayList<LatitudeDocList>();
		for (int i = 0; i < list.size(); i++) {
			LatitudeDocList docList = new LatitudeDocList();
			docList.setSectionName(list.get(i).getSectionname());
			docList.setSectionText(list.get(i).getSectiontext());
			lists.add(docList);
		}
		return lists;
	}

	// @Override
	// public List<LatitudeDocList> getDocLists(String sectionname) {
	// String hql = "from Latitudedoc where sectionname = ?";
	// List<LatitudeDocList> list =
	// this.getHibernateTemplate().find(hql,sectionname);
	// return list;
	// }

}
