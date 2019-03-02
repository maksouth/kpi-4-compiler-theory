package fsm

import org.junit.Assert.*
import org.junit.Test

class FSMTest {

    var fsm: FSM

    init {
        val acceptingStates = listOf(SecondSymbolState)
        fsm = FSM(
            acceptingStates = acceptingStates,
            initialState = FirstSymbolState
        )
    }

    @Test fun `test camel identifier`() {
        val (matches, number) = fsm.invoke("camelCaseIdentifier")
        assertTrue(matches)
        assertEquals(number, "camelCaseIdentifier")
    }

    @Test fun `test snake identifier`() =
        assertTrue(fsm.invoke("snake_case_identifier").first)

    @Test fun `test identifier begin with underscore`() =
        assertTrue(fsm.invoke("_identifierStartingWithUnderscore").first)

    @Test fun `test identifier starting with digit`() =
        assertFalse(fsm.invoke("1dentifier_starting_with_digit").first)

}