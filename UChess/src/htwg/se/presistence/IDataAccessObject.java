package htwg.se.presistence;

/**
 * Created by benedict on 23.03.16.
 */
public interface IDataAccessObject {
    public void create(Object object);
    public Object read();
    public void update(Object object);
    public void delete(Object object);

}
