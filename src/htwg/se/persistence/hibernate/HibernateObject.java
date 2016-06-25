package htwg.se.persistence.hibernate;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by benedict on 25.05.16.
 */
@Entity
@Table(name = "Turns")
public class HibernateObject implements Serializable {

    @Id
    private String id;

    @Column(name = "moves")
    private String moves;

    public HibernateObject(){

    }

    public void setId(String newId){
        this.id = newId;
    }

    public String getId() {
        return id;
    }

    public void setMoves(JSONObject moves){
        this.moves = moves.toJSONString();
    }

    public JSONObject getMoves(){
        try {
            return (JSONObject) new JSONParser().parse(this.moves);
        } catch(Exception e)
        {
            throw new IllegalArgumentException();
        }
    }
}
