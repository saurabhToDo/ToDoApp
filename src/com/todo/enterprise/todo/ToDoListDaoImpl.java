package com.todo.enterprise.todo;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.todo.model.todo.ToDo;
import com.todo.util.AppLoggerUtil;

@Repository("ToDoListDaoIF")
public class ToDoListDaoImpl implements ToDoListDaoIF {

	@Autowired
	private SessionFactory sessionfactory;

	@Autowired
	private HibernateTemplate hibernatetemplate;

	@Override
	public void addToDoItem(ToDo toDo) {
		Session session = sessionfactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(toDo);
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List < ToDo > getToDoItemList(Integer filter) {
		if (filter == 2) {
			return hibernatetemplate.find("from ToDo");
		} else {
			return hibernatetemplate.find("from ToDo where is_completed=" + filter);
		}
	}

	@Override
	public void deleteToDoItem(Integer toDoId) {
		Session session = sessionfactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("delete from ToDo where toDoId=:toDoId");
			query.setParameter("toDoId", toDoId);
			query.executeUpdate();
			tx.commit();
			AppLoggerUtil.logInfo(this.getClass(), "To Do Item Deleted Successfullly toDoId: " + toDoId);
		} catch(Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public ToDo getPerticularToDoItem(Integer toDoId) {
		return hibernatetemplate.get(ToDo.class, toDoId);
	}

	@Override
	public void markStatusAsComplete(Integer toDoId) {
		Session session = sessionfactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("update ToDo set is_completed = 1 where toDoId=:toDoId");
			query.setParameter("toDoId", toDoId);
			query.executeUpdate();
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List < ToDo > searchItemByDueDays(Date dueDate, Date current, Boolean filterFlag) {
		if (filterFlag) {
			DetachedCriteria dc = DetachedCriteria.forClass(ToDo.class);
			dc.add(Restrictions.between("dueDate", current, dueDate)).add(Restrictions.eq("completed", filterFlag));
			return hibernatetemplate.findByCriteria(dc);
		} else {
			DetachedCriteria dc = DetachedCriteria.forClass(ToDo.class);
			dc.add(Restrictions.between("dueDate", current, dueDate));
			return hibernatetemplate.findByCriteria(dc);
		}
	}

	@Override
	public List < ToDo > searchItemByText(String text, Boolean filterFlag) {
		if (filterFlag) {
			DetachedCriteria dc = DetachedCriteria.forClass(ToDo.class).add(Restrictions.like("text", text, MatchMode.ANYWHERE)).add(Restrictions.eq("completed", filterFlag));
			return hibernatetemplate.findByCriteria(dc);
		} else {
			DetachedCriteria dc = DetachedCriteria.forClass(ToDo.class).add(
			Restrictions.like("text", text, MatchMode.ANYWHERE));
			return hibernatetemplate.findByCriteria(dc);
		}
	}

}