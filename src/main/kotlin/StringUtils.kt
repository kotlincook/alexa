package de.kotlincook.stringutils

import java.text.ParseException
import java.util.regex.Pattern

fun String.extract(pattern: String,
                   leftBracket: String = "<",
                   rightBracket: String = ">",
                   encodeRegexChars: Boolean = true,
                   whiteSpaceOptimization: Boolean = true): List<Map<String, String>> {

    var (regex, varNames) = toRegExp(pattern, leftBracket, rightBracket, encodeRegexChars)
    if (whiteSpaceOptimization) {
       regex = regex.replace(Regex(" +"), "\\\\s+")
       regex += "(\\s+|$)"
    }
    val matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(this)
    val result =  ArrayList<Map<String, String>>()
    while (matcher.find()) {
        result += varNames.map { it to matcher.group(it) }.toMap()
    }
    return result
}

private fun toRegExp(pattern: String,
                     leftBracket: String, rightBracket: String,
                     encodeRegexChars: Boolean): Pair<String, ArrayList<String>> {

    class Location(var extPos: Int = 0, var intPos: Int = 0) {
        operator fun invoke(extPos: Int, intPos: Int = 0): Location {
            this.extPos = extPos
            this.intPos = intPos
            return this
        }

        fun inc(): Location {
            extPos++; intPos++
            return this
        }
    }

    val OUTSIDE = Location()
    val INSIDE = Location()
    val LEFF_BRACKET = Location()
    val RIGHT_BRACKET = Location()

    var location = OUTSIDE
    var result = ""
    val varNames = ArrayList<String>()
    var varName = ""

    while (location.extPos < pattern.length) {
        val ch = pattern[location.extPos]
        when (location) {
            OUTSIDE -> {
                when (ch) {
                    leftBracket[0] -> location = LEFF_BRACKET(OUTSIDE.extPos)
                    else -> {
                        result += if (encodeRegexChars) ch.encodeForRegex() else ch
                        OUTSIDE.inc()
                    }
                }
            }
            LEFF_BRACKET ->
                when (ch) {
                    leftBracket.last() -> {
                        location = INSIDE(LEFF_BRACKET.extPos + 1)
                        varName = ""
                    }
                    leftBracket[LEFF_BRACKET.intPos] -> LEFF_BRACKET.inc()
                    else -> location = OUTSIDE.inc()
                }
            INSIDE ->
                when (ch) {
                    rightBracket[0] -> location = RIGHT_BRACKET(INSIDE.extPos)
                    in 'A'..'Z', in '0'..'9', in 'a'..'z', '_' -> {
                        varName += ch
                        INSIDE.inc()
                    }
                    else -> throw ParseException("Invalid group name char '$ch'", INSIDE.extPos)
                }
            RIGHT_BRACKET ->
                when (ch) {
                    rightBracket.last() -> {
                        location = OUTSIDE(RIGHT_BRACKET.extPos + 1)
                        result += "(?<" + varName + ">.*?)"
                        varNames += varName
                    }
                    rightBracket[RIGHT_BRACKET.intPos] -> RIGHT_BRACKET.inc()
                    else -> location = INSIDE.inc()
                }
        }
    }
    if (location != OUTSIDE) throw ParseException(pattern, pattern.length)
    return Pair(result, varNames)
}


private fun Char.encodeForRegex(): String {
    return when(this) {
        '/', '.', '*', '+', '?', '|', '(', ')', '[', ']', '{', '}', '\\', '-' -> "\\$this"
        else -> "$this"
    }
}


//fun main(args: Array<String>) {
//    val str = "  H(llo Jörg Vollmer   H(llo Helga   Vollmer   H(llo Udo Cirkel   "
//    val str2 = """<div class="entry-content">Peter
//        </div>"""
//    println(str2.extract("<div class=\"entry-content\">%{news}</div>", "%{", "}"))
//}