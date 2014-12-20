package com.velorin.model

/**
 * Created by amir on 12/19/14.
 */

case class Node(name:String, value:String, weight: String)

case class NodeList(head: Node, listOfNodes:  List[Node])

