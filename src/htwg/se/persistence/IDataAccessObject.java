package htwg.se.persistence;

import java.util.List;

import htwg.se.persistence.CouchDB.PersistentGameOverview;

/**
 * Created by benedict on 23.03.16.
 */
public interface IDataAccessObject {
	
	public void saveGame(PersistentGameOverview game);
	
	
	
	public void create(Object object);

	public Object read();

	public void update(Object object);

	public void delete(Object object);

	public List<PersistentGameOverview> getAllGames();
	
	boolean contains(String id);

}
