package htwg.se.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * Created by benedict on 25.06.16.
 */
public class MasterActor extends UntypedActor {

    private final ActorRef turnHandler = getContext().actorOf(Props.create(TurnActor.class, null), "turnActor");

    @Override
    public Object onReceive(Object message) {
        if (message instanceof MasterMessage) {

        } else {
            
        }
        return null;
    }
}
