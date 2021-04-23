import java.util.ArrayList;

/*
 * Permet de verifier l'etat de l'echequier (echec, mat et pat)
 */
public class Checker {

    public static final int
            NORMAL = 0,
            CHECK = 1,
            PAT = 2,
            MAT = 3,
            MATERIAL_LACK = 4,
            THREEFOLD = 5,
            RULE_50 = 6,
            RULE_75 = 7;

    /*Renvoie True si le roi est menacée*/
    public static boolean isCheck(int[][] chessboard) {
        int[] pos = findKing(chessboard);
        if (pos.length == 0) return true;
        int kingL = pos[0], kingN = pos[1];
        return isCheckByExtendedMove(chessboard, kingL, kingN) || isCheckByPosMove(chessboard, kingL, kingN);
    }

    /*Renvoie la possition du roi*/
    public static int[] findKing(int[][] chessboard) {
        for (int n = 0; n < 8; n++) {
            for (int l = 0; l < 8; l++) {
                int kingCode = chessboard[n][l];
                if (Piece.hasRole(kingCode, Piece.KING_ROLE) && Piece.isPlayer(kingCode))
                    return new int[]{l, n};
            }
        }
        return new int[0];
    }

    /*Renvoie true si le roi est menacer par une dame, fou ou tour*/
    public static boolean isCheckByExtendedMove(int[][] chessboard, int kingL, int kingN) {
        int[][][] extendedMoveVectors = {Movement.VECTORS_BISHOP, Movement.VECTORS_ROOK};
        int[] extendedMoveCodeRole = {Piece.BISHOP_ROLE, Piece.ROOK_ROLE};

        for (int i = 0; i < 2; i++) {
            int[][] vectors = extendedMoveVectors[i];
            int codeRole = extendedMoveCodeRole[i];
            for (int[] vector : vectors) {
                boolean stop = false;
                for (int moveL = kingL + vector[0], moveN = kingN + vector[1];
                     Chessboard.inArea(moveN, moveL) && !stop;
                     moveL += vector[0], moveN += vector[1]) {
                    int code = chessboard[moveN][moveL];
                    if (Piece.isPiece(code)) {
                        if (Piece.isEnemy(code)
                                && (Piece.hasRole(code, codeRole) || Piece.hasRole(code, Piece.QUEEN_ROLE))) {
                            return true;
                        }
                        stop = true;
                    }
                }
            }
        }
        return false;
    }

    /*Renvoie true si le roi est menacer par un pion, un chevalier*/
    public static boolean isCheckByPosMove(int[][] chessboard, int kingL, int kingN) {
        int rNEnemyPawn;
        if (Piece.isWhite(chessboard[kingN][kingL]))
            rNEnemyPawn = -1;
        else
            rNEnemyPawn = 1;

        int[][] movesEatEnemyPawn = {{-1, rNEnemyPawn}, {1, rNEnemyPawn}};

        int[][][] baseMovesPos = {movesEatEnemyPawn, Movement.MOVES_KNIGHT, Movement.MOVES_KING};
        int[] baseMovesCodeRole = {Piece.PAWN_ROLE, Piece.KNIGHT_ROLE, Piece.KING_ROLE};

        for (int i = 0; i < 3; i++) {
            int[][] moves = baseMovesPos[i];
            int codeRole = baseMovesCodeRole[i];
            for (int[] move : moves) {
                int aL = kingL + move[0], aN = kingN + move[1];
                if (Chessboard.inArea(aL, aN)) {
                    int code = chessboard[aN][aL];
                    if (Piece.isEnemy(code) && Piece.hasRole(code, codeRole)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*Renvoie true si plus aucun coup n'est disponible pour le joueur actuel*/
    public static boolean isPat(int[][] chessboard) {
        for (int n = 0; n < 8; n++)
            for (int l = 0; l < 8; l++)
                if (Piece.isPlayer(chessboard[n][l])) {
                    int[][] moves = Movement.getMoves(chessboard, new int[]{l, n});
                    if (Piece.isPlayer(chessboard[n][l]) && Movement.canMove(moves))
                        return false;
                }
        return true;
    }

    /*Renvoie state correspondant a l'etat de la partie pour le joueur actuel*/
    public static int getState(int[][] chessboard, ArrayList<long[]> hashHistory, long[] hash, int turnWithoutDeadOrPawnMove) {

        if (Hasher.isMore3TimeInHistory(hashHistory, hash)) {
            if (Asker.askThreefoldEnd())
                return THREEFOLD;
        } else if (turnWithoutDeadOrPawnMove == 50) {
            if (Asker.askRule50())
                return RULE_50;
        } else if (turnWithoutDeadOrPawnMove >= 75) {
            return RULE_75;
        } else {
            boolean check = isCheck(chessboard), pat = isPat(chessboard);
            if (check && pat)
                return MAT;
            else if (pat)
                return PAT;
            else if (check)
                return CHECK;
            else if (hasMaterialLack(chessboard))
                return MATERIAL_LACK;
        }
        return NORMAL;
    }

    /*Renvoie true si state correspont a une fin de partie*/
    public static boolean endGame(int state) {
        return !(state == CHECK || state == NORMAL);
    }

    /*Retourne true si il n'y a plus assez de materiel  pour matter*/
    public static boolean hasMaterialLack(int[][] chessboard) {
        int[] count = Chessboard.countPieces(chessboard);
        return (count[Piece.PAWN_ROLE] == 0 && count[Piece.ROOK_ROLE] == 0 && count[Piece.QUEEN_ROLE] == 0)
                && (count[Piece.KNIGHT_ROLE] == 1 && count[Piece.BISHOP_ROLE] == 0
                || count[Piece.BISHOP_ROLE] == 1 && count[Piece.KNIGHT_ROLE] == 0
                || count[Piece.BISHOP_ROLE] == 0 && count[Piece.KNIGHT_ROLE] == 0 && count[Piece.KING_ROLE] < 2);

    }
}
