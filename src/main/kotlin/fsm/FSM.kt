package fsm
class FSM(
        private val initialState: State,
        private val acceptingStates: List<State>
): (String) -> Pair<Boolean, String> {

    override fun invoke(input: String): Pair<Boolean, String> {
        var currentState = initialState

        for ((index, c) in input.withIndex()) {
            val nextState = currentState.next(c)

            if (nextState is NoNextState)
                return (index > 0) to input.substring(0, index)

            if (nextState == NoNextState) break

            currentState = nextState
        }

        return acceptingStates.contains(currentState) to input
    }
}


abstract class State {

    fun next(input: Char): State =
            nextInternal(input) ?: NoNextState

    internal abstract fun nextInternal(input: Char): State?
}

object NoNextState : State() {
    override fun nextInternal(input: Char) = NoNextState
}

object FirstSymbolState: State() {
    override fun nextInternal(input: Char): State? =
        if (input.isLetter() || input == '_') SecondSymbolState
        else null
}

object SecondSymbolState: State() {
    override fun nextInternal(input: Char): State? =
            if (input.isLetterOrDigit() || input == '_') SecondSymbolState
            else null
}