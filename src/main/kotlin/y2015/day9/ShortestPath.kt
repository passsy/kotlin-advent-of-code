package y2015.day9

import java.util.*


fun main(args: Array<String>) {

}


/**
 * --- Day 9: All in a Single Night ---

Every year, Santa manages to deliver all of his presents in a single night.

This year, however, he has some new locations to visit; his elves have provided him the distances between every pair of locations. He can start and end at any two (different) locations he wants, but he must visit each location exactly once. What is the shortest distance he can travel to achieve this?

For example, given the following distances:

London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141
The possible routes are therefore:

Dublin -> London -> Belfast = 982
London -> Dublin -> Belfast = 605
London -> Belfast -> Dublin = 659
Dublin -> Belfast -> London = 659
Belfast -> Dublin -> London = 605
Belfast -> London -> Dublin = 982
The shortest of these is London -> Dublin -> Belfast = 605, and so the answer is 605 in this example.

What is the distance of the shortest route?
 */
fun shortestPath(stations: List<String>): Int {
    val edges = stations.map { it.toEdge() }

    val algorithm = DijkstraAlgorithm(edges)

    val vertexes = edges.flatMap { listOf(it.source, it.destination) }.distinct()
    return vertexes
            .flatMap { start ->
                algorithm.execute(start)
                vertexes.map {
                    if ((algorithm.getPath(it)?.size ?: 0) == vertexes.size) {
                        algorithm.getDistance(start, it)
                    } else {
                        Int.MAX_VALUE
                    }
                }
            }
            .min()!!
}

private fun String.toEdge(): Edge {
    val (all, from, to, distance) = "(.*) to (.*) = (\\d*)".toRegex().find(this)!!.groupValues
    return Edge(Vertex(from), Vertex(to), distance.toInt())
}

data class Vertex(
        val name: String
)

data class Edge(
        val source: Vertex,
        val destination: Vertex,
        val length: Int
)

class DijkstraAlgorithm(val edges: List<Edge>) {

    private var settledNodes: MutableSet<Vertex> = mutableSetOf()
    private var unSettledNodes: MutableSet<Vertex> = mutableSetOf()
    private var predecessors: MutableMap<Vertex, Vertex> = mutableMapOf()
    private var distance: MutableMap<Vertex, Int> = mutableMapOf()

    fun execute(source: Vertex) {
        settledNodes = HashSet<Vertex>()
        unSettledNodes = HashSet<Vertex>()
        distance = HashMap<Vertex, Int>()
        predecessors = HashMap<Vertex, Vertex>()
        distance.put(source, 0)
        unSettledNodes.add(source)
        while (unSettledNodes.size > 0) {
            val node = getMinimum(unSettledNodes)
            settledNodes.add(node)
            unSettledNodes.remove(node)
            findMinimalDistances(node)
        }
    }

    private fun findMinimalDistances(node: Vertex) {
        val adjacentNodes = getNeighbors(node)
        for (target in adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node,
                    target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target))
                predecessors.put(target, node)
                unSettledNodes.add(target)
            }
        }

    }

    fun getDistance(node: Vertex, target: Vertex): Int {
        edges.forEach {
            if (it.source == node && it.destination == target) {
                return it.length
            }
        }
        return Integer.MAX_VALUE
    }

    private fun getNeighbors(node: Vertex): List<Vertex> {
        val neighbors = mutableListOf<Vertex>()
        edges.forEach {
            if (it.source == node && !isSettled(it.destination)) {
                neighbors.add(it.destination)
            }
        }
        return neighbors
    }

    private fun getMinimum(vertexes: Set<Vertex>): Vertex {
        return vertexes.minBy { getShortestDistance(it) } ?: throw Exception("empty")
    }

    private fun isSettled(vertex: Vertex): Boolean {
        return settledNodes.contains(vertex)
    }

    private fun getShortestDistance(destination: Vertex): Int {
        return distance[destination] ?: Integer.MAX_VALUE
    }

    /*
         * This method returns the path from the source to the selected target and
         * NULL if no path exists
         */
    fun getPath(target: Vertex): LinkedList<Vertex>? {
        val path = LinkedList<Vertex>()
        var step = target
        // check if a path exists
        if (predecessors[step] == null) {
            return null
        }
        path.add(step)
        while (predecessors[step] != null) {
            step = predecessors[step]!!
            path.add(step)
        }
        // Put it into the correct order
        Collections.reverse(path)
        return path
    }

}