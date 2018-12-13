package less.stupid.neat

import akka.actor.{Actor, ActorLogging, ActorRef}


import NodeType._

class NNode(nodeType: NodeType, incoming: List[Link], outgoing: List[Link]) extends Actor with ActorLogging {

  case class Signal(strength: BigDecimal)

  override def receive: Receive = {
    case s: Signal => log.info("received Signal {}", s)
  }
}
