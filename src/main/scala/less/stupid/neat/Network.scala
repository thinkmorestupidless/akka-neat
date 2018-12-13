package less.stupid.neat

import akka.actor.{Actor, ActorLogging, ActorRef}

import NodePlace._

case class Initialise(genome: Genome)
case class NetworkReady(network: ActorRef)
case class Activate(inputs: List[BigDecimal])

//case class Link(in: ActorRef, out: ActorRef, weight: BigDecimal)

//class NetworkManager extends Actor with ActorLogging {
//
//}

class Network extends Actor with ActorLogging {

  override def receive: Receive = {
    case Initialise(genome) => {
      log.info("initialising with {}", genome)
      val sortedGenes: List[Link] = genome.genes.sortBy(_.inputNode.nodePlace)

      sortedGenes.foreach(x => println(x.inputNode.nodePlace))

      sender ! NetworkReady(self)
    }
    case Activate(inputs) => {
      log.info("activating with {}", inputs)
      context.stop(self)
    }
  }
}

case object Flush
case class Signal(strength: BigDecimal)

class NetworkNode(outgoing: List[ActorRef]) extends Actor with ActorLogging {

  var signal: BigDecimal = 0

  override def receive: Receive = {
    case Flush => signal = 0
    case Signal(strength) => {
      signal += strength

      if (signal > 1) {
        outgoing.foreach(_ ! signal)
      }
    }
  }
}
