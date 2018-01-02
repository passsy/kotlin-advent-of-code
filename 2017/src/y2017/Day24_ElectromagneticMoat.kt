package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day24_ElectromagneticMoat.Component
import y2017.Day24_ElectromagneticMoat.part1
import y2017.Day24_ElectromagneticMoat.part2

fun main(args: Array<String>) {
    part1()
    part2()
}

typealias Bridge = List<Component>

object Day24_ElectromagneticMoat {
    val part1 = challenge("Day 24 - Part One") {
        solve {
            val components = inputFile("2017/24.txt").lines().map { parseComponent(it) }.toSet()
            result = buildBridges(components).maxBy { it.strength() }!!.strength()
        }
    }

    fun Bridge.strength() = sumBy { it.strength() }

    private fun parseComponent(input: String): Component {
        val (n, s) = input.split("/")
        return Component(n.toInt(), s.toInt())
    }

    fun buildBridges(components: Set<Component>): List<Bridge> {
        return components
                .filter { it.connectsTo(0) }
                .flatMap { buildBridge(it.pins.first { it != 0 }, listOf(it), components - it) }
    }

    private fun buildBridge(openPin: Int, bridge: Bridge, components: Set<Component>): List<Bridge> {
        return components.filter { openPin in it.pins }
                .flatMap { component ->
                    val nextBridge: Bridge = bridge + component
                    val nextOpenPin = when {
                        openPin == component.portS -> component.portN
                        openPin == component.portN -> component.portS
                        else -> null
                    }

                    if (nextOpenPin == null) return arrayListOf(bridge, nextBridge, bridge)
                    val bridges: List<Bridge> = buildBridge(nextOpenPin, nextBridge, components - component)

                    return@flatMap if (bridges.isEmpty()) {
                        arrayListOf(nextBridge, nextBridge, bridge)
                    } else {
                        bridges + arrayListOf(nextBridge, bridge)
                    }
                }
    }

    data class Component(val portN: Int, val portS: Int) {
        fun strength() = portN + portS

        fun connectsTo(port: Int): Boolean = port == portN || port == portS

        val pins get() = listOf(portN, portS)
    }


    val part2 = challenge("Day 24 - Part Two") {
        solve {
            val components = inputFile("2017/24.txt").lines().map { parseComponent(it) }.toSet()
            result = buildBridges(components).maxBy { it.size }!!.strength()
        }
    }
}