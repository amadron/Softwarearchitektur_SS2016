package htwg.se.presistence.couchdb;

import htwg.se.presistence.IDataAccessObject;

/**
 * Created by benedict on 06.04.16.
 */
public class DAOCouchDB implements IDataAccessObject{

//    private CouchDbConnector db = null;
//
//    public DAOCouchDB() {
//        HttpClient client = null;
//        try {
//            client = new StdHttpClient.Builder().url(
//                    "http://lenny2.in.htwg-konstanz.de:5984").build();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        CouchDbInstance dbInstance = new StdCouchDbInstance(client);
//        db = dbInstance.createConnector("sudoku_db", true);
//    }

    @Override
    public void create(Object object) {

    }

    @Override
    public Object read() {
        return null;
    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void delete(Object object) {

    }

}
