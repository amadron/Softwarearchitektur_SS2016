package htwg.se.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * Created by benedict on 25.06.16.
 */

public class MasterActor extends UntypedActor {

    private final ActorRef turnHandler = getContext().actorOf(Props.create(TurnActor.class), "turnActor");

    public class MasterMessage {

    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof TurnActor.TurnMessage) {
            turnHandler.tell(message, getSelf());
        }
        else {
            unhandled(message);
        }
    }
}
