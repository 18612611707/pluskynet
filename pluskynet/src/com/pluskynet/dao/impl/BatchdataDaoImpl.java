package com.pluskynet.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.BatchdataDao;
import com.pluskynet.domain.Batchdata;
@SuppressWarnings("all")
public class BatchdataDaoImpl extends HibernateDaoSupport implements BatchdataDao {

	@Override
	public void save(Batchdata batchdata) {
		String hql = "from Batchdata where documentid = '"+batchdata.getDocumentid()+"' and ruleid = '"+batchdata.getRuleid()+"'";
		List<Batchdata> list = this.getHibernateTemplate().find(hql);
		if (list.size()>0) {
			String sql = "update Batchdata set cause = ? ,documentid = ? ,ruleid = ? ,startword = ? , endword = ? where id = ?";
			this.getHibernateTemplate().bulkUpdate(sql,batchdata.getCause(),batchdata.getDocumentid(),batchdata.getRuleid(),batchdata.getStartword()
					,batchdata.getEndword(),list.get(0).getId());
		}else {
			this.getHibernateTemplate().save(batchdata);
		}
	}

}
