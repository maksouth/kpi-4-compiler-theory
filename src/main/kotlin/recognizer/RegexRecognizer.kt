package recognizer

import java.util.regex.Pattern

interface Recognizer {
    fun number(): (String) -> Pair<Boolean, String>
    fun identifier(): (String) -> Pair<Boolean, String>
    fun operator(): (String) -> Pair<Boolean, String>
    fun parenthesis(): (String) -> Pair<Boolean, String>
}

object RegexRecognizer: Recognizer {

    override fun number(): (String) -> Pair<Boolean, String> = {
        val regex = "\\d+(\\.\\d+)?([eE][+-]?\\d+)?"
        recognize(regex, it)
    }

    override fun identifier(): (String) -> Pair<Boolean, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun operator(): (String) -> Pair<Boolean, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun parenthesis(): (String) -> Pair<Boolean, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun recognize(regex: String, input: String): Pair<Boolean, String> {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(input)

        val isMatch = matcher.find()
        val matchedGroup = matcher.group()

        return isMatch to matchedGroup
    }

}