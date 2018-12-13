package less.stupid.neat

class Organism(val genome: Genome) {

}

object Organism {

  def apply(genome: Genome) = {
    new Organism(genome)
  }
}
