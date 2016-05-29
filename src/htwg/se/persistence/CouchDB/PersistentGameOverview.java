package htwg.se.persistence.CouchDB;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;
import org.json.simple.JSONObject;

/**
 * Created by Marco on 28.05.2016.
 */
public class PersistentGameOverview extends CouchDbDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	@TypeDiscriminator
	private String id = "Marco_Random";
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private JSONObject gameOvervie;

	public JSONObject getGameOverview() {
		return gameOvervie;
	}

	public void setGameOverview(JSONObject gameOverview) {
		this.gameOvervie = gameOverview;
	}

}
