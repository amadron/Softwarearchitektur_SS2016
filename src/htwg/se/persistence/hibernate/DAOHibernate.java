package htwg.se.persistence.hibernate;

import htwg.se.persistence.CouchDB.PersistentGameOverview;
import htwg.se.persistence.IDataAccessObject;
import htwg.util.Point;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by benedict on 25.05.16.
 */

//@Todo: need to session close?
public class DAOHibernate implements IDataAccessObject {
    Session session;
    Transaction transact;

    //@Todo: CHeck, if it exist and overwrite it if it exist?
    @Override
    public void create(Object object) {
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            transact = session.beginTransaction();
            session.save(object);
            transact.commit();
            //Session close
        } catch (HibernateException ex) {
            errorHandling(ex, transact);
        }
    }


    //@Todo what if many have the same id
    //@Todo resemble points
    @Override
    public List<Point> read(String id) {
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            transact = session.beginTransaction();
            Criteria criteria = session.createCriteria(HibernateObject.class);
            List result = criteria.add(Restrictions.like("id", id)).list();
            transact.commit();
            //session.close();
            if (result.size() > 0) {
                return result;
            }
        } catch (HibernateException ex) {
            errorHandling(ex, transact);
        }
        return null;
    }


    @Override
    public void update(Object object) {
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            transact = session.beginTransaction();
            session.update(object);
            transact.commit();
            //session.close();
        } catch (HibernateException ex) {
            errorHandling(ex, transact);
        }
    }

    @Override
    public void delete(String id) {
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            transact = session.beginTransaction();
            Criteria criteria = session.createCriteria(HibernateObject.class);
            List result = criteria.add(Restrictions.like("id", id)).list();
            for (Object obj : result) {
                session.delete(obj);
            }
            transact.commit();
            //session.close();
        } catch (HibernateException ex) {
            errorHandling(ex, transact);
        }
    }


    @Override
    public List getAllGames() {
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            transact = session.beginTransaction();
            Criteria criteria = session.createCriteria(HibernateObject.class);
            List result = criteria.list();
            transact.commit();
            //session.close();
            return result;
        } catch (HibernateException ex) {
            errorHandling(ex, transact);
        }
        return null;
    }

    //@Todo
    @Override
    public boolean contains(String id) {
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            transact = session.beginTransaction();
            Criteria criteria = session.createCriteria(HibernateObject.class);
            List list = criteria.list();
            transact.commit();
            //session.close();
            if(list.size() > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (HibernateException ex) {
            errorHandling(ex, transact);
        }
        return false;
    }

    public void errorHandling(HibernateException ex, Transaction transact) {
        if (transact != null)
            try {
                transact.rollback();
            } catch (HibernateException exRb) {
                System.out.println("An error Occurred while Databank operation: " + exRb.getMessage());
                System.exit(-1);
            }
        throw new RuntimeException(ex.getMessage());
    }
}