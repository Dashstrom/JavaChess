/*
 * Classes principale du Jeu
 */

import java.util.ArrayList;

public class Game {

    /*Lancement du jeu*/
    public static void main(String[] agrs){
        boolean beautiful = Asker.askBeautiful();
        do {
            playAGame(beautiful);
        } while (Asker.askContinue());
    }


    /*Permet de jouer une partie return true si la partie c'est terminer*/
    public static boolean playAGame(boolean beautiful) {
        int[][] chessboard = Chessboard.create();
        int[] deadPieces = new int[12];
        ArrayList<long[]> hashHistory = new ArrayList<long[]>();
        hashHistory.add(Hasher.getHashArray(chessboard, true));

        for (int i=0; i<12; i++)
            deadPieces[i] = 0;

        int turnWithoutDeadOrPawnMove=0,
            state = Checker.getState(chessboard, hashHistory, hashHistory.get(0), turnWithoutDeadOrPawnMove);

        boolean whitePlayerTurn = true;
        Messager.printMessageTurn(true);
        Drawer.drawEchequier(chessboard, deadPieces, beautiful);
        while (!Checker.endGame(state)) {

            int[][] play = Asker.askPlay(chessboard);
            int dead = Chessboard.executeMove(chessboard, play);
            if (!Piece.isEmpty(dead)) {
                deadPieces[(dead % 12)] += 1;
                turnWithoutDeadOrPawnMove = 0;
            } else if (Piece.hasRole(Chessboard.pieceAt(chessboard, play[0]), Piece.PAWN_ROLE)) {
                turnWithoutDeadOrPawnMove = 0;
            } else {
                turnWithoutDeadOrPawnMove += 1;
            }
            whitePlayerTurn = Chessboard.swapPlayer(chessboard, whitePlayerTurn);

            long[] hash = Hasher.getHashArray(chessboard, whitePlayerTurn);
            hashHistory.add(hash);

            state = Checker.getState(chessboard, hashHistory, hash, turnWithoutDeadOrPawnMove);
            if (state == Checker.CHECK)
                Messager.print(Messager.CHECK);

            Messager.printMessageTurn(whitePlayerTurn);
            Drawer.drawEchequier(chessboard, deadPieces, beautiful);
        }
        Messager.printState(state, whitePlayerTurn);
        return true;
    }
}