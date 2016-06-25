package htwg.se.persistence;

import htwg.util.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by benedict on 17.06.16.
 */
public class PersistenceUtil {

    public static void fillPointList(List<Point> pointList, JSONArray jobj2, String from) {
        JSONObject jsonPoint;
        String tmp;
        String[] parts;
        int tmp1;
        int tmp2;
        for (int i = 0; i < jobj2.size(); ++i) {
            jsonPoint = (JSONObject) jobj2.get(i);

            tmp = (String) jsonPoint.get(from);
            parts = tmp.split("_");

            tmp1 = Integer.parseInt(parts[0]);
            tmp2 = Integer.parseInt(parts[1]);

            pointList.add(new Point(tmp1, tmp2));

        }
    }
}
