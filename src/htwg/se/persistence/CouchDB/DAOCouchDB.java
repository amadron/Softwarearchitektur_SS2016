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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import htwg.se.persistence.IDataAccessObject;
import htwg.util.Point;

public class DAOCouchDB implements IDataAccessObject {

	private CouchDbConnector db = null;
	private JSONParser parser = new JSONParser();
	private List<Point> pointList = new ArrayList<Point>();

	public DAOCouchDB() {
		try {
			HttpClient client = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984").build();
			CouchDbInstance dbInstance = new StdCouchDbInstance(client);
			db = dbInstance.createConnector("uchess_db", true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
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

	public List<Point> read(String id) { 
		List<PersistentGameOverview> list = getAllGames();
		JSONObject jsonObject;
		JSONArray jsonArray;
		Object obj;
		String s;

		for (PersistentGameOverview persistentGameOverview : list) {
			try {
				s = persistentGameOverview.getGameOverview().toString();
				obj = parser.parse(s);
				jsonObject = (JSONObject) obj;
				jsonArray = (JSONArray) jsonObject.get("Game");			
				jsonObject = (JSONObject) jsonArray.get(0);
				
				if (jsonObject.containsValue(id)) {
					jsonArray = (JSONArray) jsonObject.get("Movelist");
					fillPointList(pointList, jsonArray, "From");
					fillPointList(pointList, jsonArray, "To");
					return pointList;
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Marco_aaa ist in diesem obj niht vorhanden");
		return null;

	}

	@Override
	public void create(Object obj) {
		PersistentGameOverview currentGame = (PersistentGameOverview) obj;
		
		if (contains(currentGame.getId())) {
			updateGameDB(currentGame);
		} else {
			create(currentGame);
		}
		
	}

	@Override
	public void update(Object obj) {
		this.create(obj);
	}
	
	private void updateGameDB(PersistentGameOverview currentGame) {
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
