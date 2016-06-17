package htwg.persistence;

import htwg.se.persistence.hibernate.DAOHibernate;
import htwg.se.persistence.hibernate.HibernateObject;

/**
 * Created by benedict on 17.06.16.
 */
public class HibernateTest {

    public static void main(String[] args)
    {
        String id = "Test1";
        String moves = "It was successfull";
        HibernateObject test1 = new HibernateObject();
        test1.setId(id);
        test1.setMoves(moves);
        DAOHibernate dao = new DAOHibernate();
        testCreate(test1, dao);
    }

    public static void testCreate(HibernateObject object, DAOHibernate dao)
    {
        dao.create(object);
        if(dao.contains(object.getId())) {
            System.out.println("Object successfully created");
        } else {
            System.out.println("Object could not be created");
        }
    }
}
