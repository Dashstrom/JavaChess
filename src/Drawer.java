/*
 * Permet le dessin de l'echiquier
 * Une version bell avec des couleurs et des caractères spéciaux
 * Et une autre avec des caractères ASCII et sans couleurs pour une meilleure stabilité
 */

public class Drawer {

    public static final String
            BLACK_BG = "\033[40m",
            WHITE_BG = "\033[47m",
            YELLOW_BG = "\033[43m",
            RED_FONT = "\033[31m",
            BLUE_FONT = "\033[34m",
            BLACK_FONT = "\033[30m",
            RESET = "\033[0m",

            colorBorder = YELLOW_BG + BLACK_FONT,
            playerWhiteTitle = "Joueur Blanc",
            playerBlackTitle = "Joueur Noir",
            topBar = colorBorder + "   A B C D E F G H    " + RESET + "    " + playerBlackTitle + "\n",
            botBar = colorBorder + "   A B C D E F G H    " + RESET + "\n",
            borderLeft = colorBorder + " ",
            borderRight = " " + RESET + "    ";

    /*Dessine la version approprié en fonction du boolean beautiful*/
    public static void drawEchequier(int[][] chessboard, int[] deadPions, boolean beautiful) {
        if (beautiful)
            drawPrettyEchequier(chessboard, deadPions);
        else
            drawUglyEchequier(chessboard, deadPions);
    }

    /*Dessine une version avec des couleurs et caractères spéciaux*/
    public static void drawPrettyEchequier(int[][] chessboard, int[] deadPions) {
        StringBuilder display = new StringBuilder(topBar);
        int progressDead = 6;
        for (int line = 0; line < 8; line++) {

            display.append(borderLeft)
                   .append(8 - line)
                   .append(" ");

            for (int row = 0; row < 8; row++) {
                int code = chessboard[line][row];

                if ((line + row) % 2 == 0) display.append(WHITE_BG);
                else display.append(BLACK_BG);
                if (Piece.isWhite(code)) display.append(BLUE_FONT);
                else display.append(RED_FONT);
                display.append(Piece.getReprUFT(code));
                display.append(RESET);
            }
            display.append(colorBorder)
                   .append(" ")
                   .append(8 - line)
                   .append(borderRight);

            if (line < 3 || line > 4) {
                if (line == 5) progressDead = 0;
                int limit = progressDead + 2;
                for (int code = progressDead; code < limit; code++) {
                    progressDead += 1;
                    if (deadPions[code] != 0)
                        display.append(getPieceColor(code))
                               .append(deadPions[code])
                               .append("x")
                               .append(Piece.getReprUFT(code));
                    else
                        display.append("    ");
                    display.append("  ");
                }
            } else if (line == 4) {
                display.append(playerWhiteTitle);
            }
            display.append("\n");
        }
        display.append(botBar);
        Messager.print(display.toString());

    }

    /*Dessine une version simplifié*/
    public static void drawUglyEchequier(int[][] chessboard, int[] deadPions) {
        int progressDead = 6;

        StringBuilder display = new StringBuilder("  +---------------------------+   Joueur Noir\n");
        for (int line = 0; line < 8; line++) {
            display.append(8 - line)
                    .append(" |  ");
            for (int row = 0; row < 8; row++)
                display.append(Piece.getReprChars(chessboard[line][row])).append(" ");
            display.append(" |   ");

            if (line < 3 || line > 4) {
                if (line == 5) progressDead = 0;
                int limit = progressDead + 2;
                for (int code = progressDead; code < limit; code++) {
                    progressDead += 1;
                    if (deadPions[code] != 0)
                        display.append(deadPions[code])
                               .append("x")
                               .append(Piece.getReprChars(code));
                    else
                        display.append("    ");
                    display.append("  ");
                }
            } else if (line == 4) {
                display.append("Joueur Blanc");
            }
            display.append("\n");
        }
        display.append("  +---------------------------+\n     A  B  C  D  E  F  G  H\n");
        Messager.print(display.toString());
    }

    /*Renvoie la couleur d'une piece*/
    public static String getPieceColor(int code) {
        if (Piece.isWhite(code)) return BLUE_FONT;
        else return RED_FONT;
    }

    /*Renvoie la réprésentation colorée d'une piece au format UTF*/
    public static String getDemo() {
        return BLACK_BG + BLUE_FONT + "♚ " + RESET;
    }

}
