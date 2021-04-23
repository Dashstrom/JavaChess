/*
 * Class permettant l'interaction avec le echiquier
 */
public class Chessboard {

    /*echiquier*/
    public static final int[][] CHESSBOARD_AT_START = {
            { 7,  8,  9, 10, 11,  9,  8,  7},
            { 6,  6,  6,  6,  6,  6,  6,  6},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1},
            {12, 12, 12, 12, 12, 12, 12, 12},
            {13, 14, 15, 16, 17, 15, 14, 13}};

    /*Création de l'échiquier*/
    public static int[][] create() {
        return Chessboard.copy(CHESSBOARD_AT_START);
    }

    /*Change le joueur actuel de la partie*/
    public static boolean swapPlayer(int[][] chessboard, boolean whitePlayerTurn) {
        for (int n = 0; n < 8; n++)
            for (int l = 0; l < 8; l++)
                chessboard[n][l] = Piece.setPlayer(chessboard[n][l], !whitePlayerTurn);

        return !whitePlayerTurn;
    }

    /*
     * Execute un mouvement avec play sous forme {{l, n}, {al, an}, {moveCode}}
     * mais choisi automatique la dame lors de la promotion du pion si explore est true
     */
    public static int executeMove(int[][] chessboard, int[][] play) {
        int dead = chessboard[play[1][1]][play[1][0]],
                moved = chessboard[play[0][1]][play[0][0]],
                codeMovement = play[2][0];
        boolean whitePiece = Piece.isWhite(moved);
        chessboard[play[1][1]][play[1][0]] = Piece.getMovedCode(moved);
        clearEnPassant(chessboard, whitePiece);

        switch (codeMovement) {
            case Movement.MOVE_DOUBLE:
                if (whitePiece)
                    chessboard[play[1][1] + 1][play[1][0]] = -2;
                else
                    chessboard[play[1][1] - 1][play[1][0]] = -3;
                break;

            case Movement.MOVE_EN_PASSANT:
                int direction;
                if (whitePiece)
                    direction = 1;
                else
                    direction = -1;

                dead = chessboard[play[1][1] + direction][play[1][0]];
                chessboard[play[1][1] + direction][play[1][0]] = -1;
                break;

            case Movement.MOVE_CASTLING:
                int moveTower, placeTower;
                if (play[0][0] < play[1][0]) {
                    moveTower = -1;
                    placeTower = 7;
                } else {
                    moveTower = 1;
                    placeTower = 0;
                }
                chessboard[play[1][1]][play[1][0] + moveTower] = Piece.getMovedCode(chessboard[play[1][1]][placeTower]);
                chessboard[play[1][1]][placeTower] = -1;
                break;

            case Movement.MOVE_PROMOTION:
                if (play[2].length == 1)
                    chessboard[play[1][1]][play[1][0]] = Piece.turnInto(moved, Piece.QUEEN_ROLE);
                else
                    chessboard[play[1][1]][play[1][0]] = play[2][1];
                break;
        }

        chessboard[play[0][1]][play[0][0]] = -1;
        return dead;
    }

    /*Remplace les anciennes case ou l'on pouvait faire des en passant par des case normal*/
    public static void clearEnPassant(int[][] chessboard, boolean playerWhite) {
        int target;
        if (playerWhite)
            target = -2;
        else
            target = -3;

        for (int line = 0; line < chessboard.length; line++)
            for (int row = 0; row < chessboard[0].length; row++)
                if (chessboard[line][row] == target)
                    chessboard[line][row] = -1;
    }

    /*Permet l'obtention d'une piece du plateau via un couple de int {l, n}*/
    public static int pieceAt(int[][] chessboard, int[] pos) {
        return chessboard[pos[1]][pos[0]];
    }

    /*Permet d'optenir une possition à partir d'un message*/
    public static int[] parsePos(String rawPos) {
        if (rawPos.length() != 2) {
            return new int[0];
        } else {
            int letter = Character.toUpperCase(rawPos.charAt(0)), number = rawPos.charAt(1);
            if (letter < 'A' || 'H' < letter || number < '1' || '8' < number)
                return new int[0];
            else
                return new int[]{(int) letter - 65, 7 - (int) number + 49};
        }
    }

    /*Renvoie true si c'est une coordonnée valide*/
    public static boolean inArea(int l, int n) {
        return 0 <= l && l < 8 && 0 <= n && n < 8;
    }

    /*Copie l'echiquier*/
    public static int[][] copy(int[][] chessboard) {
        int[][] copyChessboard = new int[8][8];
        for (int n = 0; n < 8; n++)
            for (int l = 0; l < 8; l++)
                copyChessboard[n][l] = chessboard[n][l];
        return copyChessboard;
    }

    /*Conte le nombre de piece (peu importe la couleur)*/
    public static int[] countPieces(int[][] chessboard) {
        int[] count = new int[8];
        for (int n = 0; n < 8; n++) {
            for (int l = 0; l < 8; l++) {
                int code = chessboard[n][l];
                if (Piece.isPiece(code)) {
                    int coderole = Piece.getRoleCode(code);
                    count[coderole] += 1;
                }
            }
        }
        return count;
    }
}
