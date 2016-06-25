package htwg.se.persistence;

import java.util.List;

import htwg.se.persistence.CouchDB.PersistentGameOverview;
import htwg.util.Point;

/**
 * Created by benedict on 23.03.16.
 */
public interface IDataAccessObject {
	
	// Create Upate Delete E ?
	
	public void create(Object obj);
	
	public List<Point> read(String id);
	
	public void update(Object obj);
			
	public void delete(String id);

	public List<Object> getAllGames();
	
	boolean contains(String id);

}
