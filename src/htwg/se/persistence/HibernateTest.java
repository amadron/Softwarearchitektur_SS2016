package htwg.se.persistence;

import htwg.se.persistence.hibernate.DAOHibernate;
import htwg.se.persistence.hibernate.HibernateObject;
import htwg.util.Point;
import org.hibernate.mapping.List;
import org.json.simple.JSONArray;
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
        JSONObject movelist = new JSONObject();
        JSONArray moves = new JSONArray();
        JSONObject mv = new JSONObject();
        JSONObject mv2 = new JSONObject();
        String from = "3_6";
        String to = "5_4";
        mv.put("From", from);
        mv.put("To", to);
        from = "4_5";
        to = "3_5";
        mv2.put("From",from);
        mv2.put("To", to);
        moves.add(mv);
        moves.add(mv2);
        //movelist.put("Movelist", moves);
        test1.setMoves(movelist);
        DAOHibernate dao = new DAOHibernate();
        testCreate(test1, dao);
    }

    public static void testCreate(HibernateObject object, DAOHibernate dao)
    {
        //dao.delete(object.getId());
        //dao.create(object);

        java.util.List<Point> ret = dao.read("Marco_aaa");
        if(ret != null) {
            System.out.println("Database contains object! Moves: " + ret.toString());
        } else {
            System.out.println("Database does not contain object!");
        }
    }
}
