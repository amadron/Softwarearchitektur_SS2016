package htwg.se.persistence.CouchDB;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewQuery;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import htwg.se.persistence.IDataAccessObject;
import htwg.util.Point;

public class DAOCouchDB implements IDataAccessObject {

	private CouchDbConnector db = null;

	public DAOCouchDB() {
		try {
			HttpClient client = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984").build();
			CouchDbInstance dbInstance = new StdCouchDbInstance(client);
			db = dbInstance.createConnector("uchess_db", true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveGame(PersistentGameOverview currentGame) {

		if (contains(currentGame.getId())) {
			update(currentGame);
		} else {
			create(currentGame);
		}

		moveList();
	}

	private void update(PersistentGameOverview currentGame) {
		PersistentGameOverview oldGame = (PersistentGameOverview) db.find(PersistentGameOverview.class,
				currentGame.getId());
		oldGame.setGameOverview(currentGame.getGameOverview());

		try {
			db.update(oldGame);
			System.out.println("Game updated");
		} catch (org.ektorp.UpdateConflictException e) {
			System.out.println("Already Saved exception....");
		}

	}

	private void create(PersistentGameOverview currentGame) {
		try {
			PersistentGameOverview gameOverview = currentGame;
			db.create(gameOverview);
			System.out.println("Game Saved");
		} catch (org.ektorp.UpdateConflictException e) {
			System.out.println("Already Saved");
		}
	}

	public void delete(String id) {
		PersistentGameOverview gf = null;
		try {
			gf = db.get(PersistentGameOverview.class, id);
		} catch (DocumentNotFoundException e) {
			e.printStackTrace();
		}
		db.delete(gf);

	}

	public List<PersistentGameOverview> getAllGames() {
		ViewQuery query = new ViewQuery().allDocs().includeDocs(true);

		List<PersistentGameOverview> gameList = new ArrayList<PersistentGameOverview>();
		for (PersistentGameOverview persGamefield : db.queryView(query, PersistentGameOverview.class)) {
			gameList.add(persGamefield);
		}

		return gameList;
	}

	@Override
	public boolean contains(String id) {
		List<PersistentGameOverview> list;
		list = getAllGames();

		for (PersistentGameOverview persistentGameOverview : list) {
			if (persistentGameOverview.getId().equals(id)) {
				return true;
			}

		}
		return false;
	}

	public void moveList() {
		List<PersistentGameOverview> list = getAllGames();
		List<Point> pointList = new ArrayList<Point>();
		JSONObject jsonObject;
		JSONObject jsonObject2;
		JSONArray jsonArray;
		Object obj;

		System.out.println("befor loop");
		
		for (PersistentGameOverview persistentGameOverview : list) {

		jsonObject = persistentGameOverview.getGameOverview();
		System.out.println("1" + jsonObject);
		
		jsonArray.toJSONString(jsonObject.get("Game"));
		
		obj = jsonObject.get("Game");
		//jsonObject = (JSONObject)obj;
		
		JSONArray moveList = (JSONArray)obj;
		
		System.out.println("2");
		
		
//		JSONArray moveList = new JSONArray();
//		obj = obj.get("Movelist");
//		moveList = (JSONArray) obj;

		}

		// jsonArray2 = (JSONArray) jsonArray.get(0);

		// for (int n = 0; n < jsonArray2.size(); n++) {
		// obj = jsonArray2.get(n);
		// jsonObject2 = (JSONObject) obj;
		// System.out.println("From: " + jsonObject2.get("From") + " To:" +
		// jsonObject2.get("To"));
		// }

		System.out.println("after loop");

		// System.out.println(json);
		// jsonArray2 = jsonArray.toJSONString(json);
		// JSONArray jarray = (JSONArray) json;
		System.out.println("stop");

		// jsonArray = (JSONArray)jsonObject.get("Game");
		// jsonObject2 = (JSONObject)jsonObject.get("Game");
		System.out.println("weiter");
		// fillPointList(pointList, jsonArray, from);
		// fillPointList(pointList, jsonArray, to);
		// }

		for (Point point : pointList) {
			System.out.println(point.toString());
		}

	}

	private void fillPointList(List<Point> pointList, JSONArray jobj2, String from) {
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
