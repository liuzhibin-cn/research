package org.liuzhibin.research.akka;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class EchoActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	@Override
	public Receive createReceive() {
		return receiveBuilder()
            .match(String.class, s -> {
                log.info("Received String message: {}", s);
            })
            .matchAny(o -> log.info("Received unknown message: " + (o==null ? "null" : o.toString())))
            .build();
	}

}