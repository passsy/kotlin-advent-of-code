package y2017

import com.pascalwelsch.aoc.challenge

fun main(args: Array<String>) {
    Day4_Passphrases.part1()
    Day4_Passphrases.part2()
}

object Day4_Passphrases {

    //--- Day 4: High-Entropy Passphrases ---
    //
    //A new system policy has been put in place that requires all accounts to use a passphrase instead of simply a password. A passphrase consists of a series of words (lowercase letters) separated by spaces.
    //
    //To ensure security, a valid passphrase must contain no duplicate words.
    //
    //For example:
    //
    //aa bb cc dd ee is valid.
    //aa bb cc dd aa is not valid - the word aa appears more than once.
    //aa bb cc dd aaa is valid - aa and aaa count as different words.
    //The system's full passphrase list is available as your puzzle input. How many passphrases are valid?
    val part1 = challenge("Day 4 - Part One") {
        inputFile("2017/4.txt")

        solveMultiLine {
            result = it.filter { validatePassphrase(it) }.count() //337
        }
    }

    fun validatePassphrase(input: String): Boolean {
        val words = input.split(" ")
        return words.distinct().size == words.size
    }

    //--- Part Two ---
    //
    //For added security, yet another system policy has been put in place. Now, a valid passphrase must contain no two words that are anagrams of each other - that is, a passphrase is invalid if any word's letters can be rearranged to form any other word in the passphrase.
    //
    //For example:
    //
    //abcde fghij is a valid passphrase.
    //abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.
    //a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.
    //iiii oiii ooii oooi oooo is valid.
    //oiii ioii iioi iiio is not valid - any of these words can be rearranged to form any other word.
    //Under this new system policy, how many passphrases are valid?
    val part2 = challenge("Day 4 - Part Two") {
        inputFile("2017/4.txt")

        solveMultiLine {
            result = it.filter { validatePassphrase2(it) }.count() //231
        }
    }

    fun validatePassphrase2(input: String): Boolean {
        val words = input.split(" ").map { it.toList().sorted() }
        return words.distinct().size == words.size
    }
}