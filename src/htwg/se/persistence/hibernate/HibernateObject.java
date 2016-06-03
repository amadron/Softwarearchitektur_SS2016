package htwg.se.persistence.hibernate;

/**
 * Created by benedict on 25.05.16.
 */
import 

public class HibernateObject {

    String id;
    String moves;

    public HibernateObject(){

    }

    public void setId(String newId){
        this.id = newId;
    }

    public String getId() {
        return id;
    }

    public void setMoves(String moves){
        this.moves = moves;
    }

    public String getMoves(){
        return moves;
    }
}
