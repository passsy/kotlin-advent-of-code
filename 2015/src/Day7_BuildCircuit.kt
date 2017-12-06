fun main(args: Array<String>) {
    /**
     * In little Bobby's kit's instructions booklet (provided as your puzzle input), what signal is ultimately provided to wire a?
     */
    solveFromInput("day7-1") { input, output ->
        val circuit = assembleCircuit(input)
        val result = circuit["a"]
        output.write("$result")
    }

    /**
     * Now, take the signal you got on wire a, override wire b to that signal, and reset the other wires (including wire a). What new signal is ultimately provided to wire a?
     */
    solveFromInput("day7-2") { input, output ->
        val circuit = assembleCircuit(input)
        val oldResult = circuit["a"]

        circuit.inputCache.clear()

        // change output of b to output of previous a
        circuit.instructions.run {
            val signalB = first { it.output == "b" }
            remove(signalB)
            add(CircuitInstruction("b", StaticSignal(oldResult)))
        }

        val result = circuit["a"]
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

fun String.asCircuitInstruction(circuit: Circuit): CircuitInstruction {
    //println("parsing: $this")
    try {
        val (_, in1, op, in2, output) =
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

        return CircuitInstruction(output, Operation(signal1, signal2, operation))
    } catch (e: NullPointerException) {
        // not enough groups found
    }

    try {
        val (_, in1, output) = "NOT (.*) -> (.*)".toRegex().find(this)!!.groupValues
        return CircuitInstruction(output, Not(in1.toSignal(circuit)))
    } catch (e: Exception) {

    }

    val (_, in1, output) = "(.*) -> (.*)".toRegex().find(this)!!.groupValues
    return CircuitInstruction(output, in1.toSignal(circuit))


}



data class CircuitInstruction(
        val output: String,
        val input: Input
)


private fun String.toSignal(circuit: Circuit): Input {
    try {
        return StaticSignal(this.toInt())
    } catch (e: NumberFormatException) {
        return Signal(this, circuit)
    }
}


class Circuit() {

    val instructions: MutableList<CircuitInstruction> = mutableListOf()

    val inputCache: MutableMap<String, Int> = mutableMapOf()

    operator fun get(signalName: String): Int {

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Not) return false

        if (input != other.input) return false

        return true
    }

    override fun hashCode(): Int {
        return input.hashCode()
    }

}

class StaticSignal(val value: Int) : Input {
    override fun get(): Int = value
    override fun toString(): String {
        return "StaticSignal($value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StaticSignal) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value
    }

}

class Signal(val name: String, val circuit: Circuit) : Input {
    override fun get(): Int = circuit.get(name)
    override fun toString(): String {
        return "Signal('$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Signal) return false

        if (name != other.name) return false
        if (circuit != other.circuit) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + circuit.hashCode()
        return result
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Operation) return false

        if (s1 != other.s1) return false
        if (s2 != other.s2) return false
        if (op != other.op) return false

        return true
    }

    override fun hashCode(): Int {
        var result = s1.hashCode()
        result = 31 * result + s2.hashCode()
        result = 31 * result + op.hashCode()
        return result
    }

}

data class Instruction(
        val output: String,
        val input: Input
)
















