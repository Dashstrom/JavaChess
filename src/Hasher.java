/*
 * Class permetant le hashage de l'echiquier
 * et rend la verifiquation du threefold plus simple
 * (c'est un peu gadjet)
 */

import java.util.ArrayList;

public class Hasher {

    public static final long WHITE_TURN_VALUE = 55451384098598321L;

    /*retourne un array de 4 long représantant le hash d'une zone 2x8*/
    public static long[] getHashArray(int[][] chessboard, boolean whitePlayerTurn) {
        long[] hashArray = new long[4];
        for (int chunck = 0; chunck < 4; chunck++) {
            for (int part = 0; part < 2; part++) {
                int n = chunck * 2 + part;
                for (int l = 0; l < 8; l++) {
                    if (Piece.isPiece(chessboard[n][l])) {
                        long mul = 1;
                        for (int pow = 0; pow < l + part * 8; pow++)
                            mul *= 13;
                        hashArray[chunck] += (chessboard[n][l] % 12 + 1) * mul;
                    }
                }
            }
            if (whitePlayerTurn)
                hashArray[chunck] += WHITE_TURN_VALUE;
        }
        return hashArray;
    }

    /*Retourne true si un array de hash contient les même qu'un autre*/
    public static boolean equalsHashs(long[] arrayHash, long[] otherArrayHash) {
        for (int indexHash = 0; indexHash < 4; indexHash++) {
            if (arrayHash[indexHash] != otherArrayHash[indexHash])
                return false;
        }
        return true;
    }

    /*returne true si le array de hash apparait plus de trois fois dans le jeu*/
    public static boolean isMore3TimeInHistory(ArrayList<long[]> historyHash, long[] arrayHash) {
        short count = 0;
        for (int i = 0; i < historyHash.size() && count < 3; i++) {
            if (equalsHashs(historyHash.get(i), arrayHash))
                count += 1;
        }
        return count >= 3;
    }
}
