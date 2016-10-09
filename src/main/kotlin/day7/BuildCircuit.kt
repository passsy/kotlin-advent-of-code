package day7

import common.solveFromInput

fun main(args: Array<String>) {
    solveFromInput("day7-1") { input, output ->
        val circuit = assembleCircuit(input)
        val result = circuit.get("a")
        output.write("$result")
    }
}

/**
--- Day 7: Some Assembly Required ---

This year, Santa brought little Bobby Tables a set of wires and bitwise logic gates! Unfortunately, little Bobby is a little under the recommended age range, and he needs help assembling the circuit.

Each wire has an identifier (some lowercase letters) and can carry a 16-bit signal (a number from 0 to 65535). A signal is provided to each wire by a gate, another wire, or some specific value. Each wire can only get a signal from one source, but can provide its signal to multiple destinations. A gate provides no signal until all of its inputs have a signal.

The included instructions booklet describes how to connect the parts together: x AND y -> z means to connect wires x and y to an AND gate, and then connect its output to wire z.

For example:

123 -> x means that the signal 123 is provided to wire x.
x AND y -> z means that the bitwise AND of wire x and wire y is provided to wire z.
p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and then provided to wire q.
NOT e -> f means that the bitwise complement of the value from wire e is provided to wire f.
Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If, for some reason, you'd like to emulate the circuit instead, almost all programming languages (for example, C, JavaScript, or Python) provide operators for these gates.

For example, here is a simple circuit:

123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i
After it is run, these are the signals on the wires:

d: 72
e: 507
f: 492
g: 114
h: 65412
i: 65079
x: 123
y: 456
In little Bobby's kit's instructions booklet (provided as your puzzle input), what signal is ultimately provided to wire a?
 **/

fun assembleCircuit(instructions: List<String>): Circuit {

    val circuit = Circuit()

    circuit.instructions.addAll(instructions.map {
        val instruction = it.asCircuitInstruction(circuit)
        instruction
    })

    return circuit
}

private fun String.asCircuitInstruction(circuit: Circuit): Instruction {
    //println("parsing: $this")
    try {
        val (all, in1, op, in2, output) =
                "(.*)\\s(.*)\\s(.*) -> (.*)".toRegex().find(this)!!.groupValues
        val operation = when (op) {
            "AND" -> OperationType.AND
            "OR" -> OperationType.OR
            "LSHIFT" -> OperationType.LSHIFT
            "RSHIFT" -> OperationType.RSHIFT
            else -> throw IllegalArgumentException("Unknown operation '$op'")
        }

        val signal1 = in1.toSignal(circuit)
        val signal2 = in2.toSignal(circuit)

        return Instruction(output, Operation(signal1, signal2, operation))
    } catch (e: Exception) {

    }

    try {
        val (all, in1, output) = "NOT (.*) -> (.*)".toRegex().find(this)!!.groupValues
        return Instruction(output, Not(in1.toSignal(circuit)))
    } catch (e: Exception) {

    }

    val (all, in1, output) = "(.*) -> (.*)".toRegex().find(this)!!.groupValues
    return Instruction(output, in1.toSignal(circuit))


}

private fun String.toSignal(circuit: Circuit): Input {
    try {
        return StaticSignal(this.toInt())
    } catch (e: NumberFormatException) {
        return Signal(this, circuit)
    }
}


class Circuit() {

    val instructions: MutableList<Instruction> = mutableListOf()

    val inputCache: MutableMap<String, Int> = mutableMapOf()

    fun get(signalName: String): Int {

        val cache = inputCache[signalName]
        if (cache != null) return cache

        //println("searching for signal '$signalName'")

        val result = instructions.firstOrNull { it.output == signalName }?.input?.get()
                ?: throw IllegalArgumentException("signal with name '$signalName' is unknown")
        inputCache[signalName] = result
        return result
    }
}


const val max16bit = 65535

interface Input {
    fun get(): Int
}

class Not(val input: Input) : Input {
    override fun get(): Int = max16bit - input.get()
    override fun toString(): String {
        return "Not($input)"
    }

}

class StaticSignal(val value: Int) : Input {
    override fun get(): Int = value
    override fun toString(): String {
        return "StaticSignal($value)"
    }

}

class Signal(val name: String, val circuit: Circuit) : Input {
    override fun get(): Int = circuit.get(name)
    override fun toString(): String {
        return "Signal('$name')"
    }

}

enum class OperationType {
    AND, OR, LSHIFT, RSHIFT
}

class Operation(val s1: Input, val s2: Input, val op: OperationType) : Input {
    override fun get(): Int {
        return when (op) {
            OperationType.AND -> s1.get() and s2.get()
            OperationType.OR -> s1.get() or s2.get()
            OperationType.LSHIFT -> s1.get() shl s2.get()
            OperationType.RSHIFT -> s1.get() shr s2.get()
        }
    }

    override fun toString(): String {
        return "Operation(s1=$s1, s2=$s2, op=$op)"
    }

}

data class Instruction(
        val output: String,
        val input: Input
)
















