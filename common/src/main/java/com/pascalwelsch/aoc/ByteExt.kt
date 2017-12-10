package com.pascalwelsch.aoc




/**
 * converts a byte array to an readable string with hex bytes
 * @param infix placeholder between bytes
 */
fun ByteArray.toHex(infix: String = " "): String {
    val result = StringBuffer()

    forEachIndexed { i, byte ->
        result.append(byte.toHex(""))
        if (i < size - 1) result.append(infix)
    }
    return result.toString()
}

private val HEX_CHARS = "0123456789ABCDEF".toCharArray()
fun Byte.toHex(prefix: String = "0x"): String {
    val octet = toInt()
    val firstIndex = (octet and 0xF0).ushr(4)
    val secondIndex = octet and 0x0F
    return "$prefix${HEX_CHARS[firstIndex]}${HEX_CHARS[secondIndex]}"
}

/**
 * transforms a uInt to a signed int as it is used in java
 */
fun Byte.toSignedInt(): Int {
    return toInt() and 0xFF
}

/**
 * outputs the 8 bits as string
 * <p>
 *     0x14 -> "00010100"
 */
fun Byte.toBits(): String {
    return String.format("%8s", Integer.toBinaryString(this.toInt() and 0xFF)).replace(' ', '0')
}