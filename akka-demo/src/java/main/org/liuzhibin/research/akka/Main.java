package org.liuzhibin.research.akka;

import java.util.Date;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
	static ActorSystem system = null;
	
	public static void main(String[] args) {
		system = ActorSystem.create("akka-demo");
		ActorRef echoActor = system.actorOf(Props.create(EchoActor.class), "echoActor");
		echoActor.tell("Hello", ActorRef.noSender());
		echoActor.tell(new Date(), ActorRef.noSender());
//		system.stop(echoActor);
//		system.terminate();
	}

}
