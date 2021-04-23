import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class HasherTest {

    @Test
    final void testIsMore3TimeInHistory() {

        long[] match = {125523398, 13182, 0, 0},
                empty = {0, 7, 8, 47},
                normal = {0, 7, 8, 47};

        ArrayList<long[]> history = new ArrayList<long[]>();
        history.add(match);
        history.add(normal);
        history.add(match);
        history.add(empty);

        assertFalse(Hasher.isMore3TimeInHistory(history,
                Hasher.getHashArray(ChessboardTest.MAT_CHESSBOARD[0], false)), "cas seulement deux dans l'historique ");

        history.add(match);
        assertTrue(Hasher.isMore3TimeInHistory(history,
                Hasher.getHashArray(ChessboardTest.MAT_CHESSBOARD[0], false)), "cas threefold + mat ");

    }

    @Test
    final void testGetHashArray() {

        for (int iTest = 0; iTest < ChessboardTest.CHECK_CHESSBOARD.length; iTest++) {
            long[] hash1 = Hasher.getHashArray(ChessboardTest.CHECK_CHESSBOARD[iTest], true);
            long[] hash2 = Hasher.getHashArray(ChessboardTest.CHECK_CHESSBOARD[iTest], false);
            assertNotEquals(hash1, hash2, "cas meme possition mais pas meme trais");
        }

        int[][] chessboardPart = {
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
        };
        int[] emptyLine = {-1, -1, -1, -1, -1, -1, -1, -1};
        int[][] extendedPart = {
                chessboardPart[0],
                chessboardPart[1],
                emptyLine,
                emptyLine,
                emptyLine,
                emptyLine,
                emptyLine,
                emptyLine};

        long previousFirstWhiteTurn = -1;
        long previousFirstBlackTurn = -1;
        for (int nb = 0; nb < 100000; nb++) {
            long actualFirstWhiteTurn = Hasher.getHashArray(extendedPart, true)[0],
                    actualFirstBlackTurn = Hasher.getHashArray(extendedPart, false)[0];
            recursiveArrayIncr(chessboardPart, 0);
            extendedPart[0] = chessboardPart[0];
            extendedPart[1] = chessboardPart[1];
            if (actualFirstBlackTurn <= previousFirstBlackTurn)
                fail("cas hash suivi");
            else if (actualFirstWhiteTurn <= previousFirstWhiteTurn)
                fail("cas hash suivi");
            else if (actualFirstWhiteTurn == actualFirstBlackTurn)
                fail("cas meme possition mais pas meme trait");
        }

        int[][] maxHashable = {
                {12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12},
                emptyLine,
                emptyLine,
                emptyLine,
                emptyLine,
                emptyLine,
                emptyLine
        };

        assertEquals(55451384098598320L, Hasher.getHashArray(maxHashable, false)[0], "cas maximal 13^15 + 13^14 + 13^13 + ...");


    }

    public static void recursiveArrayIncr(int[][] array, int index) {
        if (array[index / 8][index % 8] == 12) {
            array[index / 8][index % 8] = -1;
            recursiveArrayIncr(array, index + 1);
        } else {
            array[index / 8][index % 8] += 1;
        }
    }
}