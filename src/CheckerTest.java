import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CheckerTest {

    @Test
    final void testIsCheck() {
        for (int iTest = 0; iTest < ChessboardTest.CHECK_CHESSBOARD.length; iTest++) {
            assertTrue(Checker.isCheck(ChessboardTest.CHECK_CHESSBOARD[iTest]), "cas check " + iTest);
        }

        for (int iTest = 0; iTest < ChessboardTest.PAT_CHESSBOARD.length; iTest++) {
            assertFalse(Checker.isCheck(ChessboardTest.PAT_CHESSBOARD[iTest]), "cas non check " + iTest);
        }

        for (int
             iTest = 0; iTest < ChessboardTest.MAT_CHESSBOARD.length; iTest++) {
            assertTrue(Checker.isCheck(ChessboardTest.MAT_CHESSBOARD[iTest]), "cas check (mat)" + iTest);
        }
    }

    @Test
    final void testIsPat() {
        for (int iTest = 0; iTest < ChessboardTest.PAT_CHESSBOARD.length; iTest++) {
            assertTrue(Checker.isPat(ChessboardTest.PAT_CHESSBOARD[iTest]), "cas pat " + iTest);
        }

        for (int iTest = 0; iTest < ChessboardTest.CHECK_CHESSBOARD.length; iTest++) {
            assertFalse(Checker.isPat(ChessboardTest.CHECK_CHESSBOARD[iTest]), "cas non pat " + iTest);
        }

        for (int
             iTest = 0; iTest < ChessboardTest.MAT_CHESSBOARD.length; iTest++) {
            assertTrue(Checker.isPat(ChessboardTest.MAT_CHESSBOARD[iTest]), "cas pat (mat) " + iTest);
        }

    }

    @Test
    final void testGetState() {
        int state;

        for (int iTest = 0; iTest < ChessboardTest.MAT_CHESSBOARD.length; iTest++) {
            state = Checker.getState(ChessboardTest.MAT_CHESSBOARD[iTest], ChessboardTest.EMPTY_HASHS, ChessboardTest.EMPTY_HASH, 0);
            assertEquals(Checker.MAT, state, "cas mat " + iTest);
        }


        for (int iTest = 0; iTest < ChessboardTest.PAT_CHESSBOARD.length; iTest++) {
            state = Checker.getState(ChessboardTest.PAT_CHESSBOARD[iTest], ChessboardTest.EMPTY_HASHS, ChessboardTest.EMPTY_HASH, 0);
            assertEquals(Checker.PAT, state, "cas pat " + iTest);
        }

        for (int iTest = 0; iTest < ChessboardTest.CHECK_CHESSBOARD.length; iTest++) {
            state = Checker.getState(ChessboardTest.CHECK_CHESSBOARD[iTest], ChessboardTest.EMPTY_HASHS, ChessboardTest.EMPTY_HASH, 0);
            assertEquals(Checker.CHECK, state, "cas check " + iTest);
        }

        state = Checker.getState(ChessboardTest.CHECK_CHESSBOARD[0], ChessboardTest.EMPTY_HASHS, ChessboardTest.EMPTY_HASH, 75);
        assertEquals(Checker.RULE_75, state, "cas 75 coups regle + check");

        state = Checker.getState(ChessboardTest.MAT_CHESSBOARD[5], ChessboardTest.EMPTY_HASHS, ChessboardTest.EMPTY_HASH, 75);
        assertEquals(Checker.RULE_75, state, "cas 75 coups regle + mat ");

        state = Checker.getState(ChessboardTest.MAT_CHESSBOARD[5], ChessboardTest.EMPTY_HASHS, ChessboardTest.EMPTY_HASH, 74);
        assertEquals(Checker.MAT, state, "cas 74 coups regle + mat ");

        state = Checker.getState(ChessboardTest.MAT_CHESSBOARD[5], ChessboardTest.EMPTY_HASHS, ChessboardTest.EMPTY_HASH, 76);
        assertEquals(Checker.RULE_75, state, "cas 76 coups regle + mat ");

    }


}