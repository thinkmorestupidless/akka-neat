package less.stupid.neat

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

case class Start(startGenome: Genome, initialPopulationSize: Int, generations: Int)
case object Complete

class Experiment extends Actor with ActorLogging {

  override def receive: Receive = {
    case Start(startGenome, initialPopulationSize, generations) =>

      val population: Population = Population(startGenome, initialPopulationSize)
      log.info("starting experiment -> {}, {} {} ({})", startGenome, initialPopulationSize, generations, population.organisms.size)

//      for (i <- 1 to generations) {
//        log.info("starting generation {}", i)
//
//        for (organism <- population.organisms) {
          val i: Int = 1
          val organism: Organism = population.organisms.head

          val network: ActorRef = context.actorOf(Props[Network], s"network$i")
          network ! Initialise(organism.genome)
//        }
//      }

      sender ! Complete
  }
}
