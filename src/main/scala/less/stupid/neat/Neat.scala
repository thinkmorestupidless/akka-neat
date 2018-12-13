package less.stupid.neat

import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config._

object Neat {

  def main(args: Array[String]): Unit = {
    val system: ActorSystem = ActorSystem.create("NEAT")

    val config: Config = system.settings.config.getConfig("neat")

    val genomeConfig: Config = config.getConfig("start-genome")
    val startGenome: Genome = Genome(genomeConfig)

    val initialPopulationSize: Int = config.getInt("initial-population-size")
    val generations: Int = config.getInt("generations")

    val experiment: ActorRef = system.actorOf(Props[Experiment], "experiment")
    experiment ! Start(startGenome, initialPopulationSize, generations)
  }


}


