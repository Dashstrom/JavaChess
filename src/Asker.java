import java.util.Scanner;

/*
 * Interaction avec L'utilisateur via des demandes
 */
public class Asker {

    public static Scanner in = new Scanner(System.in);
    public static final String[] acceptedYes = {"yes", "y", "oui", "o"},
                                 acceptedNo = {"no", "n", "non"};

    /*Renvoie true si la réponse est considérer comme possitive*/
    public static boolean isYes(String response) {
        return isAmong(response, acceptedYes);
    }

    /*Renvoie true si la réponse est considérer comme négative*/
    public static boolean isNo(String response) {
        return isAmong(response, acceptedNo);
    }

    /*Renvoie true si la réponse est dans un tableau donnée*/
    public static boolean isAmong(String response, String[] among) {
        for (String match : among)
            if (match.equals(response))
                return true;
        return false;
    }

    /*Renvoie true si la réponse l'utilisateur valide le message et affiche les messages voulu dans les cas*/
    public static boolean askValidation(String message, String yesMessage, String noMessage) {
        Messager.print(message);
        boolean invalidResponse = true, choix = false;
        do {
            String response = in.nextLine().toLowerCase();
            if (isYes(response)) {
                Messager.println(yesMessage);
                choix = true;
                invalidResponse = false;
            } else if (isNo(response)) {
                Messager.println(noMessage);
                choix = false;
                invalidResponse = false;
            } else {
                Messager.print(Messager.INVALID_VALIDATION);
            }
        } while (invalidResponse);
        return choix;
    }

    /*Demande la promotion d'un pion*/
    public static int askPromotion() {
        Messager.print(Messager.PROMOTION);
        String response = in.nextLine().toLowerCase();
        while (response.length() != 1 || "1234".indexOf(response.charAt(0)) == -1) {
            Messager.print(Messager.PROMOTION_ERROR);
            response = in.nextLine().toLowerCase();
        }
        return Integer.parseInt(response);
    }

    /*Demande si l'utilsateur peut jouer en mode beautiful*/
    public static boolean askBeautiful() {
        return askValidation(Messager.CHOOSE_BEAUTIFUL, Messager.BEAUTIFUL_ALLOW, Messager.BEAUTIFUL_DENY);
    }

    /*Demande si l'utilisateur veut rejouer une partie*/
    public static boolean askContinue() {
        return askValidation(Messager.CHOOSE_CONTINUE, Messager.CONTINUE, Messager.EXIT);
    }

    /*Demande Basique de possition*/
    public static int[] askPos(String message) {
        Messager.print(message);
        String input = in.nextLine();

        int[] pos = Chessboard.parsePos(input);
        while (pos.length == 0) {
            Messager.print(Messager.INVALID_POS);
            input = in.nextLine();
            pos = Chessboard.parsePos(input);
        }
        return pos;
    }

    /*Demande d'un coup*/
    public static int[][] askPlay(int[][] chessboard) {
        int[][] play;
        do {

            int[] piecePos = askPiecePos(chessboard);
            play = askPlayOnPiece(chessboard, piecePos);
            if (play.length != 0 && play[2][0] == Movement.MOVE_PROMOTION) {
                int newCodeRole = Asker.askPromotion();
                play[2] = new int[]{play[2][0], Piece.turnInto(Chessboard.pieceAt(chessboard, piecePos), newCodeRole)};
                System.out.println("PROMOTE");
            }

        } while (play.length == 0);
        return play;
    }

    /*Demande de l'emplacement d'une piece du joueur*/
    public static int[] askPiecePos(int[][] chessboard) {
        int[] pos = askPos(Messager.CHOOSE_PIECE);
        int code = Chessboard.pieceAt(chessboard, pos);
        while (!Piece.isPlayer(code)) {
            if (Piece.isEmpty(code))
                pos = askPos(Messager.IS_EMPTY);
            else
                pos = askPos(Messager.IS_ENEMY);
            code = Chessboard.pieceAt(chessboard, pos);
        }
        return pos;
    }

    /*Demande ce que veux faire le joueur veut faire d'une piece via une possition donnée*/
    public static int[][] askPlayOnPiece(int[][] chessboard, int[] piecePos) {
        if (piecePos.length != 2) return new int[0][0];
        int[][] moves = Movement.getMoves(chessboard, piecePos);
        int globalMove = Movement.getGlobalMove(moves);
        if (globalMove != 0) {
            Messager.println(Messager.LOCK_GENERAL);
            Messager.printMoveError(globalMove);
            return new int[0][0];
        }
        int[] targetPos;
        int moveCode;
        do {
            int code = Chessboard.pieceAt(chessboard, piecePos);
            targetPos = askPos(Messager.getMessageChooseTarget(code));
            if (targetPos.length != 2) return new int[0][0];

            moveCode = Movement.getMoveCode(moves, targetPos);
            if (moveCode < 0)
                Messager.printMoveError(moveCode);

        } while (moveCode < 0);

        return new int[][]{piecePos, targetPos, {moveCode}};
    }

    public static boolean askThreefoldEnd() {
        return askValidation(Messager.CHOOSE_THREEFOLD, Messager.END_BY_THREEFOLD, Messager.CONTINUE_THREEFOLD);
    }

    public static boolean askRule50() {
        return askValidation(Messager.CHOOSE_RULE_50, Messager.NUL_RULE_50, Messager.CONTINUE_RULE_50);
    }
}
