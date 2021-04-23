/*
 * Tout les affichages transite par cette class
 * mais elle contient aussi toutes les constantes nécésaire au messages
 */

public class Messager {

    public static final String
            CHOOSE_PIECE = "Quelle piece voulez-vous selectionner [A-H][1-8] : ",
            IS_ENEMY = "C'est une piece ennemie [A-H][1-8] : ",
            IS_EMPTY = "C'est une case vide [A-H][1-8] : ",
            INVALID_POS = "Cet emplacement n'est pas valide [A-H][1-8] : ",
            INVALID_VALIDATION = "Ce n'est pas un choix valide (o/n) : ",

            CHOOSE_CONTINUE = "Voulez-vous refaire une partie  (o/n) : ",
            EXIT = "Fermeture du jeu",
            CONTINUE = "Les pieces ont ete replacees sur l'echiquier",

            CHOOSE_BEAUTIFUL = "Cette piece " + Drawer.getDemo() + " s'affiche-t-elle correctement (o/n) : ",
            BEAUTIFUL_ALLOW = "Le jeu va pouvoir s'afficher sous son plus bel echiquier !",
            BEAUTIFUL_DENY = "Pas de chance, le jeu va etre affiche en mode ASCII seulement TwT",

            CHOOSE_THREEFOLD = "Les pieces ont eu trois fois le même emplacement, voulez-vous faire match nul (o/n) : ",
            END_BY_THREEFOLD = "Fin du match !",
            CONTINUE_THREEFOLD = "Le match continue :D",

            CHOOSE_RULE_50 = "Il y a eu 50 coups joues sans capture ou sans mouvement de pion,\n" +
                             "Voulez-vous demander match nul (o/n) : ",
            NUL_RULE_50 = "Fin de la partie !",
            CONTINUE_RULE_50 = "Vous avez choisi de continuer, le match sera automatiquement nul dans 25 coups si rien de change",

            RULE_75 = "Match nul, Il y a eu plus de 75 coups sans capture ou sans mouvement",
            LOCK_GENERAL = "Cette piece ne peut pas bouger !",
            LOCK_WRONG_EN_PASSANT = "Vous devez selectionner la case d'arrivee du pion et non celle ou est l'ennemi",
            LOCK_CHECK = "Deplacer cette piece ici vous mettra en echec",
            LOCK_POSITION_PIECE = "Vous ne pouvez pas déplacer une piece sur elle-meme",
            LOCK_ENEMY = "Vous ne pouvez pas passer par-dessus les pieces ennemies",
            LOCK_ALLY = "Vous ne pouvez pas capturer ou passer par-dessus vos propres pieces",
            LOCK_WRONG_MOVE = "Cette piece ne peut pas etre deplacer cette façon",

            PAT_WHITE = "Pat, Le joueur blanc n'a plus de coups legaux, match nul !",
            PAT_BLACK = "Pat, Le joueur noir n'a plus de coups legaux, match nul !",
            WIN_WHITE = "Echec et mat des noirs, victoire des blancs !",
            WIN_BLACK = "Echec et mat des blancs, victoire des noirs !",
            THREEFOLD = "Match nul, les pieces ont eu trois fois le même emplacament",
            MATERIAL_LACK = "Match nul, il n'y a pas assez de pieces pour mater",
            CHECK = "Vous etes en echec  !",

            WHITE_TURN = "Trait aux blancs",
            BLACK_TURN = "Trait aux noires",

            PROMOTION_ERROR = "Veuilliez selectionner un nombre entre 1 et 4 : ",
            PROMOTION = "Vous pouvez promouvoir un pion !\n" +
                    "1) Tour\n" +
                    "2) Chevalier\n" +
                    "3) Fou\n" +
                    "4) Dame\n" +
                    "Quel est votre choix [1-5] : ";


    /*Permet d'afficher une demande de deplacement avec le nom de la pieces*/
    public static String getMessageChooseTarget(int code) {
        return "Ou voulez deplacer votre " + Piece.getName(code) + " [A-H][1-8]: ";
    }

    /*affichage simple (juste ici au cas ou d'une redirection de l'output ou colorisation)*/
    public static void print(String message) {
        System.out.print(message);
    }

    /*affichage simple avec retour a la ligne (juste ici au cas ou d'une redirection de l'output ou colorisation)*/
    public static void println(String message) {
        System.out.println(message);
    }

    /*affiche le message correspondant à la find de partie*/
    public static void printState(int state, boolean whitePlayerTurn) {
        switch (state) {
            case Checker.MAT:
                if (whitePlayerTurn)
                    println(WIN_BLACK);
                else
                    println(WIN_WHITE);
                break;
            case Checker.PAT:
                if (whitePlayerTurn)
                    println(PAT_BLACK);
                else
                    println(PAT_WHITE);
                break;
            case Checker.THREEFOLD:
                println(THREEFOLD);
                break;
            case Checker.MATERIAL_LACK:
                println(MATERIAL_LACK);
                break;
            case Checker.RULE_75:
                println(RULE_75);
                break;
        }
    }

    /*Affiche le message d'erreur en fonction du code du mouvement*/
    public static void printMoveError(int codeMove) {
        String message;
        switch (codeMove) {
            case Movement.LOCK_WRONG_MOVE:
                message = LOCK_WRONG_MOVE;
                break;
            case Movement.LOCK_ALLY:
                message = LOCK_ALLY;
                break;
            case Movement.LOCK_ENEMY:
                message = LOCK_ENEMY;
                break;
            case Movement.LOCK_POSITION_PIECE:
                message = LOCK_POSITION_PIECE;
                break;
            case Movement.LOCK_CHECK:
                message = LOCK_CHECK;
                break;
            case Movement.LOCK_WRONG_EN_PASSANT:
                message = LOCK_WRONG_EN_PASSANT;
                break;
            default:
                message = "";
        }
        println(message);
    }

    public static void printMessageTurn(boolean whitePlayerTurn) {
        if (whitePlayerTurn)
            println(WHITE_TURN);
        else
            println(BLACK_TURN);
    }
}
