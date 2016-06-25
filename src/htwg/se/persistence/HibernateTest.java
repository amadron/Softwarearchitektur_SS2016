package htwg.se.persistence;

import htwg.se.persistence.hibernate.DAOHibernate;
import htwg.se.persistence.hibernate.HibernateObject;
import org.json.simple.JSONObject;

/**
 * Created by benedict on 17.06.16.
 */
public class HibernateTest {

    public static void main(String[] args)
    {
        String id = "Test1";
        HibernateObject test1 = new HibernateObject();
        test1.setId(id);
        JSONObject moves = new JSONObject();
        moves.put("Test", "Hello");
        test1.setMoves(moves);
        DAOHibernate dao = new DAOHibernate();
        testCreate(test1, dao);
    }

    public static void testCreate(HibernateObject object, DAOHibernate dao)
    {
        //dao.create(object);
        HibernateObject ret = (HibernateObject) dao.read(object.getId());
        if(ret != null) {
            System.out.println("Object successfully created\n Json: " + ret.getMoves().toString());
        } else {
            System.out.println("Object could not be created");
        }
    }
}
