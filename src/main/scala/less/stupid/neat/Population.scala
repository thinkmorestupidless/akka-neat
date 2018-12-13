package less.stupid.neat

class Population(val organisms: List[Organism]) {

}

object Population {

  def apply(genome: Genome, size: Int): Population = {
    val organisms = List.fill(size)(genome.duplicate)
                      .map(g => new Organism(g))

    new Population(organisms)
  }
}
