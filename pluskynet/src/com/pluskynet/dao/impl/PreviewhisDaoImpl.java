package com.pluskynet.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.pluskynet.dao.PreviewhisDao;
import com.pluskynet.domain.Docrule;
import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.Latitudenum;
import com.pluskynet.domain.Latitudestatistical;
import com.pluskynet.domain.Previewhis;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PreviewhisDaoImpl extends HibernateDaoSupport implements PreviewhisDao {

	@Override
	@Transactional
	public List<Previewhis> select(String starttime, String endtime) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = null;
		if (starttime == null) {
			sql = "select * from previewhis ";
		} else {
			sql = "select * from previewhis where createtime between '" + starttime + "' and '" + endtime + "'";
		}
		List<Previewhis> list = session.createSQLQuery(sql).addEntity(Previewhis.class).list();
		return list;
	}

	@Override
	public void save(Previewhis previewhis) {
		this.getHibernateTemplate().save(previewhis);
	}

	@Override
	@Transactional
	public List<Latitudestatistical> Latitudestatistical() {
		List<Latitudestatistical> statisticallist = new ArrayList<Latitudestatistical>();
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from latitudenum";
		List<Latitudenum> list = session.createSQLQuery(sql).addEntity(Latitudenum.class).list();
		String hql = "select * from latitude";
		List<Latitude> latitudelist = session.createSQLQuery(hql).addEntity(Latitude.class).list();
		String docsql = "select * from docrule";
		List<Docrule> doclist = session.createSQLQuery(docsql).addEntity(Docrule.class).list();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getType() == 0) {
				continue;
			}
			Latitudestatistical latitudestatistical = new Latitudestatistical();
			int latitudeid = list.get(i).getLatitudeid();
			String latitudefname = null;
			String rule = null;
			latitudestatistical.setId(list.get(i).getId());
			latitudestatistical.setLatitudeid(latitudeid);
			latitudestatistical.setLatitudenums(list.get(i).getNums());
			for (int j = 0; j < latitudelist.size(); j++) {
				if (latitudeid == latitudelist.get(j).getLatitudeid()) {
					rule = latitudelist.get(j).getRule();
					int latitudefid = latitudelist.get(j).getLatitudefid();
					while (latitudefid > 0) {
						for (int k = 0; k < latitudelist.size(); k++) {
							if (latitudefid == latitudelist.get(k).getLatitudeid()) {
								if (latitudefname == null) {
									latitudefname = latitudelist.get(k).getLatitudename();
								} else {
									latitudefname = latitudelist.get(k).getLatitudename()+ "-" + latitudefname;
								}
								latitudefid = latitudelist.get(k).getLatitudefid();
							}
						}
					}
					break;
				}
			}
			if (rule != null) {
				JSONArray jsonarray = JSONArray.fromObject(rule);
				JSONObject jsonObject = JSONObject.fromObject(jsonarray.get(0));
				for (int j = 0; j < list.size(); j++) {
					if (list.get(j).getType() == 0) {
						if (jsonObject.getString("sectionname").equals(list.get(j).getLatitudeid().toString())) {
							latitudestatistical.setSectionname(list.get(j).getLatitudename());
							latitudestatistical.setSectionnums(list.get(j).getNums().toString());
							break;
						}
					}
				}
			}
			latitudestatistical.setLatitudename(list.get(i).getLatitudename());
			latitudestatistical.setLatitudefname(latitudefname);
			statisticallist.add(latitudestatistical);
		}
		return statisticallist;
	}

}
