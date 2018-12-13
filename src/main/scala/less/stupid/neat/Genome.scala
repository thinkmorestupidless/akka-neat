package less.stupid.neat

object NodeType extends Enumeration {
  type NodeType = Value
  val None, Neuron, Sensor = Value
}

object NodePlace extends Enumeration {
  type NodePlace = Value
  val None, Hidden, Input, Output, Bias = Value
}

import NodeType._
import NodePlace._
import com.typesafe.config._
import scala.collection.JavaConverters._

class Genome(val genes: List[Link], val nodes: List[Node]) {

  def duplicate: Genome = {
    return new Genome(genes, nodes)
  }
}

object Genome {

  def empty = new Genome(List.empty, List.empty)

  def apply(config: Config): Genome = {
    val nodeList: ConfigList = config.getList("nodes")
    val nodes: List[Node] = nodeList.asScala.toList.map { x =>
      if (x.valueType() == ConfigValueType.OBJECT) {
        val c: Config = x.asInstanceOf[ConfigObject].toConfig

        val id: Int = c.getInt("id")
        val nodeType: NodeType = NodeType withName c.getString("nodeType")
        val nodePlace: NodePlace = NodePlace withName c.getString("nodePlace")

        Node(id, nodeType, nodePlace)
      } else {
        Node.empty()
      }
    }

    val linksList: ConfigList = config.getList("links")
    val links: List[Link] = linksList.asScala.toList.map { x =>
      if (x.valueType() == ConfigValueType.OBJECT) {
        val c: Config = x.asInstanceOf[ConfigObject].toConfig

        val innovation: Int = c.getInt("innovation")
        val inputNodeNum: Int = c.getInt("inputNode")
        val inputNode: Node = nodes.find(_.id == inputNodeNum).getOrElse(Node.empty())
        val outputNodeNum: Int = c.getInt("outputNode")
        val outputNode: Node = nodes.find(_.id == outputNodeNum).getOrElse(Node.empty())
        val weight: BigDecimal = c.getDouble("weight")

        Link(innovation, inputNode, outputNode, weight)
      } else {
        Link.empty
      }
    }

    new Genome(links, nodes)
  }
}

class Link(val innovation: Int, val inputNode: Node, val outputNode: Node, val weight: BigDecimal) {

}

object Link {

  def empty() = new Link(0, Node.empty(), Node.empty(), BigDecimal(0))

  def apply(innovation: Int, inputNode: Node, outputNode: Node, weight: BigDecimal) = {
    new Link(innovation, inputNode, outputNode, weight)
  }
}



class Node(val id: Int, val nodeType: NodeType, val nodePlace: NodePlace) {

}

object Node {

  def empty() = new Node(0, NodeType.None, NodePlace.None)

  def apply(id: Int, nodeType: NodeType, nodePlace: NodePlace) = {
    new Node(id, nodeType, nodePlace)
  }
}
