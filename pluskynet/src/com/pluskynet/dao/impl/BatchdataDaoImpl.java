package com.pluskynet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.dao.BatchdataDao;
import com.pluskynet.domain.Article;
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

	@Override
	@Transactional
	public Boolean plsave(List<Batchdata> batchlist) {
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = null;
		Connection conn = s.connection();
		for (int i = 0; i < batchlist.size(); i++) {
			String hql = "select * from batchdata where documentid = '"+batchlist.get(i).getDocumentid()+"' and ruleid = '"+batchlist.get(i).getRuleid()+"'";
			List<Batchdata> list = s.createSQLQuery(hql).addEntity(Batchdata.class).list();
			if (list.size()>0) {
				sql = "update batchdata set cause = '"+batchlist.get(i).getCause()+"' ,documentid = '"+batchlist.get(i).getDocumentid()+"' ,ruleid = "+batchlist.get(i).getRuleid()+" ,startword = '"+batchlist.get(i).getStartword()+"' , endword = '"+batchlist.get(i).getEndword()+"' where id = "+list.get(0).getId()+"";
			}else{
				sql = "insert into batchdata (cause,documentid,ruleid,startword,endword)values ('"+batchlist.get(i).getCause()+"','"+batchlist.get(i).getDocumentid()+"',"+batchlist.get(i).getRuleid()+" ,'"+batchlist.get(i).getStartword()+"' ,'"+batchlist.get(i).getEndword()+"')";
			}
			if(sql!=null && i==batchlist.size()-1){
				try {
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.addBatch();
					stmt.executeBatch();
					conn.setAutoCommit(false);
					conn.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
		}
		return true;
		
	}

	@Override
	public boolean delete(Batchdata batchdata) {
		String hql = "from Batchdata where documentid = '"+batchdata.getDocumentid()+"' and ruleid = '"+batchdata.getRuleid()+"'";
		List<Batchdata> list = this.getHibernateTemplate().find(hql);
		for (int i = 0; i < list.size(); i++) {
			this.getHibernateTemplate().delete(list.get(i));
		}
		return true;
	}
}
