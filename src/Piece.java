import java.security.PublicKey;

/*
 * Class permetant l'extraction des données stocker dans le code d'une piece
 */
public class Piece {

    public static final String[] CHARS_BASE = {"P", "T", "C", "F", "D", "R"},
            CHARS_UTF = {"♟ ", "♜ ", "♞ ", "♝ ", "♛ ", "♚ "},
            NAMES = {"pion", "tour", "cavalier", "fou", "dame", "roi"};

    public static final String CHAR_BASE_EMPTY = " ", CHAR_UTF_EMPTY = "  ";
    public static final int
            PAWN_ROLE = 0,
            ROOK_ROLE = 1,
            KNIGHT_ROLE = 2,
            BISHOP_ROLE = 3,
            QUEEN_ROLE = 4,
            KING_ROLE = 5;

    /*
     * -3 emplacement en passant noir
     * -2 emplacement en passant blanc
     * -1 vide
     * 0-6 pieces blanche %6
     * +6 = pieces noire %12
     * +12 = player %24
     * +24 = c'est deplacer une fois %48
     */

    /*Renvoie true si c'est la piece appartient au joueur actuel*/
    public static boolean isPlayer(int code) {
        return isPiece(code) && code % 24 >= 12;
    }

    /*Renvoie true si la piece est blanche*/
    public static boolean isWhite(int code) {
        return isPiece(code) && code % 12 < 6;
    }

    /*Renvoie true si la piece est noire*/
    public static boolean isBlack(int code) {
        return isPiece(code) && code % 12 >= 6;
    }

    /*Renvoie true si la piece est un enemie*/
    public static boolean isEnemy(int code) {
        return isPiece(code) && !isPlayer(code);
    }

    /*Renvoie true si la case est vide*/
    public static boolean isEmpty(int code) {
        return code < 0;
    }

    /*Renoie true si la case contietn une piece*/
    public static boolean isPiece(int code) {
        return code >= 0;
    }

    /*Renvoie true si la piece n'a jamais bouger (true dans le cas d'une piece absante)*/
    public static boolean hasNeverMove(int code) {
        return isEmpty(code) || code % 48 < 24;
    }

    /*Renvoie true si la piece a le même role que le role choisi*/
    public static boolean hasRole(int code, int codeRole) {
        return getRoleCode(code) == codeRole;
    }

    /*Renvoie true si la pieces et de la même couleur qu'une autre (false si l'unde des deux et absante)*/
    public static boolean hasSameColor(int code, int other) {
        return isPiece(code) && isPiece(other) && ((Piece.isWhite(code) && Piece.isWhite(other)) || Piece.isBlack(other));
    }

    /*Renvoie true si un pion peut manger en passant dans une case (a éviter avec les autres pieces)*/
    public static boolean canDoEnPassant(int code, int other) {
        return isPiece(code) && (code % 12 < 6 && other == -3) || (code % 12 >= 6 && other == -2);
    }

    /*Renvoie le nom d'une piece (éviter les comparaisons de role via le nom, utiliser plûtot hasRole ou getRoleCode)*/
    public static String getName(int code) {
        if (isEmpty(code)) return "";
        return NAMES[code % 6];
    }

    /*Renvoie la piece sous format UTF*/
    public static String getReprUFT(int code) {
        if (isEmpty(code)) return CHAR_UTF_EMPTY;
        return CHARS_UTF[code % 6];
    }

    /*Renvoie la représentation textuel*/
    public static String getReprChars(int code) {
        if (isEmpty(code)) return CHAR_BASE_EMPTY + " ";
        else if (isWhite(code)) return CHARS_BASE[code % 6] + "B";
        return CHARS_BASE[code % 6] + "N";
    }

    /*Renvoie le code de la piece quand celle-ci à déjà bouger une fois*/
    public static int getMovedCode(int code) {
        if (isEmpty(code) || code % 48 >= 24)
            return code;
        return code + 24;
    }

    /*Renvoie le role de de la piece*/
    public static int getRoleCode(int code) {
        if (isEmpty(code)) return -1;
        return code % 6;
    }

    /*Permet de changer le role d'une piece (utile pour la promotion des pions)*/
    public static int turnInto(int code, int codeRole) {
        if (isPiece(code))
            return code - getRoleCode(code) + codeRole;
        return code;
    }

    /* Modifie le code pour faire en sorte que le joueur choisi puisse la selectionner
     * (on aurais pu utliser swap mais cela permet une autocorrection d'un possible bug
     */
    public static int setPlayer(int code, boolean whitePlayerTurn) {
        boolean isWhite = code % 12 < 6;
        if (isEmpty(code))
            return code;
        else if (code % 24 >= 12)
            if ((whitePlayerTurn && !isWhite) || (!whitePlayerTurn && isWhite))
                return code - 12;
            else
                return code;
        else if ((whitePlayerTurn && isWhite) || (!whitePlayerTurn && !isWhite))
            return code + 12;
        return code;
    }
}
