package htwg.se.presistence;

/**
 * Created by benedict on 23.03.16.
 */
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class DAODB4O implements IDataAccessObject {
    String dbName = "db4oChess";
    ObjectContainer db;

    private ObjectContainer openDb(){
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbName);
    }

    @Override
    public void create(Object object) {
        db = openDb();
        try {
            db.store(object);
        } finally {
            db.close();
        }
    }

    @Override
    public Object read() {
        db = openDb();
        try {

        } finally {
            db.close();
        }
    }

    @Override
    public void update(Object object) {
        db = openDb();
        try{

        } finally {
            db.close();
        }
    }

    @Override
    public void delete(Object object) {
        db = openDb();
        try{

        } finally {
            db.close();
        }
    }
}
