package com.pluskynet.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.pluskynet.dao.CauseDao;
import com.pluskynet.domain.Cause;
@SuppressWarnings("all")
public class CauseDaoImpl extends HibernateDaoSupport implements CauseDao {
	private SessionFactory sessionFactory;
	
	public String select(Cause cause){
		String hql = "from Cause where causename = ?";
		List<Cause> list = this.getHibernateTemplate().find(hql,cause.getCausename());
		if(list.size()>0){
			return list.get(0).getCausetable();
		}
		return "";
	} 
	

	public List<Cause> getArticleList(){
		String hql = "from Cause a WHERE (SELECT COUNT(causetable) FROM Cause WHERE causetable = a.causetable AND id < a.id ) < 1 and causetable is not null ORDER BY a.causetable,a.id";
		List<Cause> list = this.getHibernateTemplate().find(hql);
		return list;
	}
	@Override
	public Cause save(Cause cause) {
		String hql = "from Cause where causename = ?";
		List<Cause> list = this.getHibernateTemplate().find(hql,cause.getCausename());
		if (list.size()>0) {
			String sql = "update Cause set causename = ?, causetable = ? ,doctable = ? , fid = ? where id = ?";
			this.getHibernateTemplate().bulkUpdate(sql,cause.getCausename(),cause.getCausetable(),cause.getDoctable(),cause.getFid(),cause.getId());
		}else {
			this.getHibernateTemplate().save(cause);
		}
		this.getHibernateTemplate().flush();
		return cause;
	}
	public Cause selectCause(Cause cause){
		Cause cause2 = null;
		String sql = "from Cause where causename = ? ";
		List<Cause> list = this.getHibernateTemplate().find(sql,cause.getCausename());
		if (list.size()>0){
			cause2 = list.get(0);
		}
		return cause2;
	}

}
