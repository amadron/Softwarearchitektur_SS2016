package htwg.se.persistence.CouchDB;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewQuery;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import htwg.se.model.GameField;
import htwg.se.persistence.IDataAccessObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

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
		if(contains(currentGame.getId())) {
			System.out.println("doSave");
			doSave(currentGame);
		}
		else {
			System.out.println("doUpdate");
			doUpdate(currentGame);
		}			
	}


	private void doUpdate(PersistentGameOverview currentGame) {
		PersistentGameOverview oldGame;
		oldGame = (PersistentGameOverview) db.find(PersistentGameOverview.class, currentGame.getId());
		
		oldGame.setGameOverview(currentGame.getGameOverview());
		
		
		try {
			db.update(oldGame);
			System.out.println("Game updated");
		} catch (org.ektorp.UpdateConflictException e) {
			System.out.println("Already Saved exception....");
		}
		
	}


	private void doSave(PersistentGameOverview currentGame) {
		try {
			PersistentGameOverview gameOverview = currentGame;
			db.create(gameOverview);
			System.out.println("Game Saved");
		} catch (org.ektorp.UpdateConflictException e) {
			System.out.println("Already Saved");
		}
	}

	
	
	public void create(Object object) {
			
		
		
	}
	
	public void setPersistent() {
//		cdb = new DAOCouchDB();
//
//		System.out.println(mainObj);
//		goverview.setGameOverview(mainObj);
//
//		if (!create) {
//			cdb.create(goverview);
//			create = true;
//		}
//
//		cdb.update(goverview);

	}

	public void update(Object object) {
		PersistentGameOverview game = (PersistentGameOverview) object;
		try {
			PersistentGameOverview gameOverview111 = game;
			db.update(gameOverview111); // create(gameOverview);
			System.out.println("Game updated");
		} catch (org.ektorp.UpdateConflictException e) {
			System.out.println("Already Saved");
		}
	}

	public void delete(Object object) {
		String id = (String) object;

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
		
		System.out.println("es sind " + gameList.size() + " in der datenbank");

		
		return gameList;
	}


	@Override
	public Object read() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean contains(String id) {
		
		List<PersistentGameOverview> list;
		list = getAllGames();
		
		for (PersistentGameOverview persistentGameOverview : list) {
			System.out.println(persistentGameOverview.getId() + " == " + id);
			if(persistentGameOverview.getId() == id) {
				
				return true;
			}
				
		}
		

		return false;
	}


	private void checkGameName(JSONObject gameOverview) {
//		JSONArray gamelist  = (JSONArray) gameOverview.get("gameOverview");
//		JSONArray movelist  = (JSONArray) gameOverview.get("Movelist");
		
		
		
	}


	
}
