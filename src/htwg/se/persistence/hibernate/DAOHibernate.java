package htwg.se.persistence.hibernate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import htwg.se.persistence.CouchDB.PersistentGameOverview;
import htwg.se.persistence.IDataAccessObject;
import htwg.se.persistence.PersistenceUtil;
import htwg.util.Point;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;
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
            HibernateObject obj = (HibernateObject) object;
            Criteria criteria = session.createCriteria(HibernateException.class).add(Restrictions.like("id", obj));
            if(criteria.list().size() > 0)
            {
                session.update(object);
            }
            else {
                session.save(object);
            }
            transact.commit();
            //Session close
        } catch (HibernateException ex) {
            errorHandling(ex, transact);
        }
    }

    @Override
    public List<Point> read(String id) {
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            transact = session.beginTransaction();
            Criteria criteria = session.createCriteria(HibernateObject.class);
            List result = criteria.add(Restrictions.like("id", id)).list();
            transact.commit();
            LinkedList<Point> points = null;
            if(result.size() > 0) {
                HibernateObject obj = (HibernateObject) result.get(0);
                points = new LinkedList<Point>();
                JSONObject mvs = (JSONObject) obj.getMoves();
                

            }
            return points;
            //session.close();
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
            Criteria criteria = session.createCriteria(HibernateObject.class).add( Restrictions.like("id", id));
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