import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class GameTest {

    public static String GAME =
            "e2\ne4\ne7\ne5\n" +
            "g1\nf3\nd7\nd6\n" +
            "d2\nd4\nc8\ng4\n" +
            "d4\ne5\ng4\nf3\n" +
            "d1\nf3\nd6\ne5\n" +
            "f1\nc4\ng8\nf6\n" +
            "f3\nb3\nd8\ne7\n" +
            "b1\nc3\nc7\nc6\n" +
            "c1\ng5\nb7\nb5\n" +
            "c3\nb5\nc6\nb5\n" +
            "c4\nb5\nb8\nd7\n" +
            "e1\nc1\na8\nd8\n" +
            "d1\nd7\nd8\nd7\n" +
            "h1\nd1\ne7\ne6\n" +
            "b5\nd7\nf6\nd7\n" +
            "b3\nb8\nd7\nb8\n" +
            "d1\nd8\n";

    public static String ERROR_GAME = "a8\na1\ne2\na5\ntestError\n" + GAME;

    @Test
    final void TestPlayAGame() {
        Asker.in = new Scanner(GAME); //replacement de l'entrer par la valdiation
        assertTrue(Game.playAGame(true), "cas entier partie finie"); //regarde si il n'y a pas de gros erreur pedandant la partie
        assertFalse(Asker.in.hasNextLine(), "cas partie finie mais incomplète"); //regarde si tout les coups ont pue être fais

        Asker.in = new Scanner(ERROR_GAME); //replacement de l'entrer par la valdiation
        assertTrue(Game.playAGame(true), "cas entier avec erreur et partie finie"); //regarde si il n'y a pas de gros erreur pedandant la partie
        assertFalse(Asker.in.hasNextLine(), "cas partie finie mais incomplète"); //regarde si tout les coups ont pue être fais
    }

}