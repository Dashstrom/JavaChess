/*
 * Permet de crée le tableau de mouvement d'un pion
 * et de le modfier ou de faire des verifications
 */

public class Movement {

    public static final int[][]
            VECTORS_BISHOP = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}},
            VECTORS_ROOK = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}},
            VECTORS_QUEEN = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1},
                    {1, 0}, {0, 1}, {-1, 0}, {0, -1}},

            MOVES_KING = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}},
            MOVES_KNIGHT = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};

    public static final int
            LOCK_WRONG_EN_PASSANT = -6,
            LOCK_CHECK = -5,
            LOCK_POSITION_PIECE = -4,
            LOCK_ENEMY = -3,
            LOCK_ALLY = -2,
            LOCK_WRONG_MOVE = -1,

            MOVE_NORMALY = 0,
            MOVE_AND_EAT = 1,
            MOVE_EN_PASSANT = 2,
            MOVE_CASTLING = 3,
            MOVE_FORCE = 4,
            MOVE_DOUBLE = 5,
            MOVE_PROMOTION = 6;

    /*Renvoie true si la pieces peux bouger*/
    public static boolean canMove(int[][] moves) {
        return getGlobalMove(moves) == 0;
    }

    /* Renvoie la raison principale pour laquel le piece ne peux pas bouger
     * 0 correspond a la capacité de bouger
     */
    public static int getGlobalMove(int[][] moves) {
        int codeMove = 0;
        for (int[] lineNumberMove : moves)
            for (int move : lineNumberMove)
                if (move >= 0)
                    return 0;
                else if (move != LOCK_POSITION_PIECE && move != LOCK_WRONG_EN_PASSANT && move < codeMove)
                    codeMove = move;
        return codeMove;
    }

    /* Renvoie le code du mouvement promotion si la condition est vrais
     * Sinon renvoie le code du mouvement de base
     */
    public static int getMoveWithPromotion(int move, boolean promoteCondition) {
        if (promoteCondition)
            return MOVE_PROMOTION;
        else
            return move;
    }

    /* Permet d'obtenir le code d'un mouvement a partir d'une possition */
    public static int getMoveCode(int[][] moves, int[] pos) {
        return moves[pos[1]][pos[0]];
    }

    /* Retourne la matrice de déplacement d'une piece à une possition passer en paramètre */
    public static int[][] getMoves(int[][] chessboard, int[] pos) {

        int code = Chessboard.pieceAt(chessboard, pos);
        if (Piece.isEmpty(Chessboard.pieceAt(chessboard, pos))) return new int[0][0]; //erreur
        int[][] moves = new int[8][8];

        /* Remplisage de 0 par defaut */
        for (int aN = 0; aN < moves.length; aN++)
            for (int aL = 0; aL < moves[aN].length; aL++)
                moves[aN][aL] = LOCK_WRONG_MOVE;

        switch (Piece.getRoleCode(code)) {
            case Piece.QUEEN_ROLE:
                addExtendedMove(chessboard, moves, pos, VECTORS_QUEEN);
                break;
            case Piece.BISHOP_ROLE:
                addExtendedMove(chessboard, moves, pos, VECTORS_BISHOP);
                break;
            case Piece.ROOK_ROLE:
                addExtendedMove(chessboard, moves, pos, VECTORS_ROOK);
                break;
            case Piece.KNIGHT_ROLE:
                addPositionMovement(chessboard, moves, pos, MOVES_KNIGHT);
                break;
            case Piece.PAWN_ROLE:
                addPawnMove(chessboard, moves, pos);
                break;
            case Piece.KING_ROLE:
                addPositionMovement(chessboard, moves, pos, MOVES_KING);
                addCastelingMove(chessboard, moves, pos);
                break;
        }

        removeCheckMove(chessboard, pos, moves);
        moves[pos[1]][pos[0]] = LOCK_POSITION_PIECE;
        return moves;
    }

    /* Permet d'ajouter les mouvement étendues vias des vecteurs */
    public static void addExtendedMove(int[][] chessboard, int[][] moves, int[] pos, int[][] vectors) {
        for (int[] vector : vectors) {
            int lock = 0;
            for (int moveL = pos[0] + vector[0], moveN = pos[1] + vector[1];
                 Chessboard.inArea(moveN, moveL);
                 moveL += vector[0], moveN += vector[1]) {

                int codePieceMove = chessboard[moveN][moveL];

                if (lock != 0)
                    moves[moveN][moveL] = lock;
                else if (Piece.isEmpty(codePieceMove))
                    moves[moveN][moveL] = MOVE_NORMALY;
                else if (Piece.isPlayer(codePieceMove)) {
                    moves[moveN][moveL] = LOCK_ALLY;
                    lock = LOCK_ALLY;
                } else {
                    moves[moveN][moveL] = MOVE_AND_EAT;
                    lock = LOCK_ENEMY;
                }
            }
        }
    }

    /* Permet d'ajouter des mouvement via un tableau de possition relative */
    public static void addPositionMovement(int[][] chessboard, int[][] moves, int[] pos, int[][] relativeMoves) {
        for (int[] move : relativeMoves) {
            int aN = pos[1] + move[1], aL = pos[0] + move[0];
            if (Chessboard.inArea(aN, aL))
                if (Piece.isEmpty(chessboard[aN][aL]))
                    moves[aN][aL] = MOVE_NORMALY;
                else if (Piece.isPlayer(chessboard[aN][aL]))
                    moves[aN][aL] = LOCK_ALLY;
                else
                    moves[aN][aL] = MOVE_AND_EAT;
        }
    }

    /* Permet d'ajouter les roque si possible */
    public static void addCastelingMove(int[][] chessboard, int[][] moves, int[] pos) {
        int kingCode = Chessboard.pieceAt(chessboard, pos), l = pos[0], n = pos[1];
        if (Piece.hasNeverMove(kingCode) && !Checker.isCheck(chessboard)) {
            for (int rL = -1; rL <= 1; rL += 2) {
                int aL, i;
                boolean canCasteling = true;
                int[][] copyboard = Chessboard.copy(chessboard);
                for (aL = l + rL, i = 0; 0 < aL && aL < 7 && canCasteling; aL += rL, i++)
                    if (Piece.isEmpty(chessboard[n][aL])) {
                        if (i < 2) {
                            int[][] play = {{aL - rL, n}, {aL, n}, {0}};
                            Chessboard.executeMove(copyboard, play);
                            if (Checker.isCheck(copyboard)) {
                                canCasteling = false;
                            }
                        }
                    } else {
                        canCasteling = false;
                    }
                if (Chessboard.inArea(n, aL)) {
                    int rookCode = chessboard[n][aL];
                    if (canCasteling && Piece.hasRole(rookCode, Piece.ROOK_ROLE)
                            && Piece.hasSameColor(kingCode, rookCode) && Piece.hasNeverMove(rookCode))
                        moves[n][l + rL + rL] = MOVE_CASTLING;
                }
            }
        }
    }

    /* Permet d'ajouter les mouvement du pions (en passant, double mouvement, capture, etc) */
    public static void addPawnMove(int[][] chessboard, int[][] moves, int[] pos) {
        int rN, l = pos[0], n = pos[1], code = Chessboard.pieceAt(chessboard, pos);
        boolean canHavePromototion;
        if (Piece.isWhite(code)) {
            rN = -1;
            canHavePromototion = (n + rN) == 0;
        } else {
            rN = 1;
            canHavePromototion = (n + rN) == 7;
        }

        if (Chessboard.inArea(n + rN, l)) {
            if (Piece.isEmpty(chessboard[n + rN][l])) {
                moves[n + rN][l] = getMoveWithPromotion(MOVE_NORMALY, canHavePromototion);
                if (Piece.hasNeverMove(code) && Piece.isEmpty(chessboard[n + rN * 2][l]))
                    moves[n + rN * 2][l] = MOVE_DOUBLE;

            } else if (Piece.isPlayer(chessboard[n + rN][l])) {
                moves[n + rN][l] = LOCK_ALLY;
            } else {
                moves[n + rN][l] = LOCK_ENEMY;
            }
        }

        for (int aL = l - 1; aL < l + 2; aL += 2)
            if (Chessboard.inArea(n + rN, aL))
                if (Piece.isEnemy(chessboard[n + rN][aL])) {
                    moves[n + rN][aL] = getMoveWithPromotion(MOVE_AND_EAT, canHavePromototion);
                } else if (Piece.canDoEnPassant(code, chessboard[n + rN][aL])) {
                    moves[n + rN][aL] = MOVE_EN_PASSANT;
                    moves[n][aL] = LOCK_WRONG_EN_PASSANT;
                }
    }

    /* Permet de supprimer tout les coups qui metterais ou laisserais le roi en echec */
    public static void removeCheckMove(int[][] chessboard, int[] pos, int[][] moves) {
        boolean isAlreadyCheck = Checker.isCheck(chessboard);
        for (int n = 0; n < moves.length; n++)
            for (int l = 0; l < moves[n].length; l++)
                if (moves[n][l] >= 0 && moves[n][l] != MOVE_FORCE) {
                    int[][] copyChessboard = Chessboard.copy(chessboard);
                    int[][] play = {pos, {l, n}, {moves[n][l]}};

                    Chessboard.executeMove(copyChessboard, play);
                    if (Checker.isCheck(copyChessboard))
                        moves[n][l] = LOCK_CHECK;
                    else if (isAlreadyCheck)
                        moves[n][l] = MOVE_FORCE;
                }
    }
}
