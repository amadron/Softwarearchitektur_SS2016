package htwg.se.persistence.hibernate;

/**
 * Created by benedict on 25.05.16.
 */
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Turns")
public class HibernateObject implements Serializable {
    @Id
    String id;
    @Column(name = "moves")
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
