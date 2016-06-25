package htwg.se.actor;

import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import htwg.se.model.GameField;
import htwg.util.Point;
import scala.concurrent.Future;

/**
 * Created by benedict on 25.06.16.
 */

public class TurnActor extends UntypedActor {
    public static class TurnMessage
    {
        Point from;
        Point to;
        GameField gameField;
        public TurnMessage(Point from, Point to, GameField gameField)
        {
            this.from = from;
            this.to = to;
            this.gameField = gameField;
        }

        public Point getFrom()
        {
            return from;
        }

        public Point getTo()
        {
            return to;
        }
    }

    @Override
    public void onReceive(Object message) {
        if(message instanceof TurnMessage){
            TurnMessage msg = (TurnMessage) message;
            Boolean ret = msg.gameField.moveCheck(msg.from, msg.to);
            getSender().tell(ret, self());
        } else {
            unhandled(message);
        }
    }
}
