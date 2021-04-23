import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PieceTest {

    public static int
            KING_BLACK_MOVED_PLAY = 47,
            KING_BLACK = 11,
            KING_WHITE = 5,
            EMPTY = -1,
            EMPTY_EN_PASSANT_BLACK = -2,
            EMPTY_EN_PASSANT_WHITE = -3,
            ROOK_WHITE_PLAY = 13,
            PAWN_WHITE = 0,
            PAWN_BLACK_MOVED = 30;


    @Test
    final void testIsPlayer() {
        assertTrue(Piece.isPlayer(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertTrue(Piece.isPlayer(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertFalse(Piece.isPlayer(KING_BLACK), "cas roi noir non joueur");
        assertFalse(Piece.isPlayer(KING_WHITE), "cas roi blanc non joueur");
        assertFalse(Piece.isPlayer(EMPTY), "cas vide");
        assertFalse(Piece.isPlayer(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertFalse(Piece.isPlayer(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testIsWhite() {
        assertFalse(Piece.isWhite(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertTrue(Piece.isWhite(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertFalse(Piece.isWhite(KING_BLACK), "cas roi noir non joueur");
        assertTrue(Piece.isWhite(KING_WHITE), "cas roi blanc non joueur");
        assertFalse(Piece.isWhite(EMPTY), "cas vide");
        assertFalse(Piece.isWhite(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertFalse(Piece.isWhite(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testIsEnemy() {
        assertFalse(Piece.isEnemy(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertFalse(Piece.isEnemy(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertTrue(Piece.isEnemy(KING_BLACK), "cas roi noir non joueur");
        assertTrue(Piece.isEnemy(KING_WHITE), "cas roi blanc non joueur");
        assertFalse(Piece.isEnemy(EMPTY), "cas vide");
        assertFalse(Piece.isEnemy(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertFalse(Piece.isEnemy(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testIsEmpty() {
        assertFalse(Piece.isEmpty(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertFalse(Piece.isEmpty(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertFalse(Piece.isEmpty(KING_BLACK), "cas roi noir non joueur");
        assertFalse(Piece.isEmpty(KING_WHITE), "cas roi blanc non joueur");
        assertTrue(Piece.isEmpty(EMPTY), "cas vide");
        assertTrue(Piece.isEmpty(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertTrue(Piece.isEmpty(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testIsPiece() {
        assertTrue(Piece.isPiece(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertTrue(Piece.isPiece(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertTrue(Piece.isPiece(KING_BLACK), "cas roi noir non joueur");
        assertTrue(Piece.isPiece(KING_WHITE), "cas roi blanc non joueur");
        assertFalse(Piece.isPiece(EMPTY), "cas vide");
        assertFalse(Piece.isPiece(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertFalse(Piece.isPiece(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testHasNeverMove() {
        assertFalse(Piece.hasNeverMove(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertTrue(Piece.hasNeverMove(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertTrue(Piece.hasNeverMove(KING_BLACK), "cas roi noir non joueur");
        assertTrue(Piece.hasNeverMove(KING_WHITE), "cas roi blanc non joueur");
        assertTrue(Piece.hasNeverMove(EMPTY), "cas vide");
        assertTrue(Piece.hasNeverMove(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertTrue(Piece.hasNeverMove(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testHasRole() {
        assertTrue(Piece.hasRole(KING_BLACK_MOVED_PLAY, Piece.KING_ROLE), "cas roi noir bougé une fois trait noir = roi");
        assertTrue(Piece.hasRole(ROOK_WHITE_PLAY, Piece.ROOK_ROLE), "cas tour blanche trait blanc = tour");
        assertFalse(Piece.hasRole(KING_BLACK, Piece.ROOK_ROLE), "cas roi noir non joueur = tour");
        assertTrue(Piece.hasRole(KING_WHITE, Piece.KING_ROLE), "cas roi blanc non joueur = roi");
        assertFalse(Piece.hasRole(EMPTY, Piece.KING_ROLE), "cas vide = roi");
        assertTrue(Piece.hasRole(EMPTY_EN_PASSANT_BLACK, -1), "cas vide mais pion blanc en passant = -1");
        assertFalse(Piece.hasRole(EMPTY_EN_PASSANT_WHITE, Piece.BISHOP_ROLE), "cas vide mais pion noir en passant = fou");
    }

    @Test
    final void testHasSameColor() {
        assertFalse(Piece.hasSameColor(KING_BLACK_MOVED_PLAY, EMPTY), "cas roi noir bougé une fois trait noir = vide");
        assertTrue(Piece.hasSameColor(ROOK_WHITE_PLAY, KING_WHITE), "cas tour blanche trait blanc = cas roi blanc non joueur");
        assertTrue(Piece.hasSameColor(KING_BLACK, KING_BLACK_MOVED_PLAY), "cas roi noir non joueur = cas roi noir bougé une fois trait noir");
        assertTrue(Piece.hasSameColor(KING_WHITE, ROOK_WHITE_PLAY), "cas roi blanc non joueur = cas tour blanche trait blanc");
        assertFalse(Piece.hasSameColor(EMPTY, KING_BLACK), "cas vide = cas roi noir non joueur");
        assertFalse(Piece.hasSameColor(EMPTY_EN_PASSANT_BLACK, EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant = cas vide mais pion blanc en passant");
        assertFalse(Piece.hasSameColor(EMPTY_EN_PASSANT_WHITE, KING_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testCanDoEnPassant() {
        assertFalse(Piece.canDoEnPassant(PAWN_BLACK_MOVED, EMPTY), "cas pion noir bouge => vide");
        assertTrue(Piece.canDoEnPassant(PAWN_BLACK_MOVED, EMPTY_EN_PASSANT_BLACK), "cas pion noir bouge => vide possible en passant noir");
        assertFalse(Piece.canDoEnPassant(PAWN_WHITE, EMPTY_EN_PASSANT_BLACK), "cas pion blanc => vide possible en passant noir");
        assertTrue(Piece.canDoEnPassant(PAWN_WHITE, EMPTY_EN_PASSANT_WHITE), "cas pion blanc => vide possible en passant blanc");
        assertFalse(Piece.canDoEnPassant(PAWN_WHITE, PAWN_BLACK_MOVED), "cas pion blanc => pion noir deplacer une fois");
    }

    @Test
    final void testGetName() {
        assertEquals(Piece.NAMES[5], Piece.getName(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertEquals(Piece.NAMES[1], Piece.getName(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertEquals(Piece.NAMES[0], Piece.getName(PAWN_BLACK_MOVED), "cas pion noir bouge");
        assertEquals(Piece.NAMES[5], Piece.getName(KING_BLACK), "cas roi noir non joueur");
        assertEquals(Piece.NAMES[5], Piece.getName(KING_WHITE), "cas roi blanc non joueur");
        assertEquals("", Piece.getName(EMPTY), "cas vide");
        assertEquals("", Piece.getName(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertEquals("", Piece.getName(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testGetReprUFT() {
        assertEquals(Piece.CHARS_UTF[5], Piece.getReprUFT(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertEquals(Piece.CHARS_UTF[1], Piece.getReprUFT(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertEquals(Piece.CHARS_UTF[0], Piece.getReprUFT(PAWN_BLACK_MOVED), "cas pion noir bouge");
        assertEquals(Piece.CHARS_UTF[5], Piece.getReprUFT(KING_BLACK), "cas roi noir non joueur");
        assertEquals(Piece.CHARS_UTF[5], Piece.getReprUFT(KING_WHITE), "cas roi blanc non joueur");
        assertEquals(Piece.CHAR_UTF_EMPTY, Piece.getReprUFT(EMPTY), "cas vide");
        assertEquals(Piece.CHAR_UTF_EMPTY, Piece.getReprUFT(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertEquals(Piece.CHAR_UTF_EMPTY, Piece.getReprUFT(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testGetReprChars() {
        assertEquals(Piece.CHARS_BASE[5] + "N", Piece.getReprChars(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertEquals(Piece.CHARS_BASE[1] + "B", Piece.getReprChars(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertEquals(Piece.CHARS_BASE[0] + "N", Piece.getReprChars(PAWN_BLACK_MOVED), "cas pion noir bouge");
        assertEquals(Piece.CHARS_BASE[5] + "N", Piece.getReprChars(KING_BLACK), "cas roi noir non joueur");
        assertEquals(Piece.CHARS_BASE[5] + "B", Piece.getReprChars(KING_WHITE), "cas roi blanc non joueur");
        assertEquals(Piece.CHAR_BASE_EMPTY + " ", Piece.getReprChars(EMPTY), "cas vide");
        assertEquals(Piece.CHAR_BASE_EMPTY + " ", Piece.getReprChars(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertEquals(Piece.CHAR_BASE_EMPTY + " ", Piece.getReprChars(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testGetMovedCode() {
        assertEquals(KING_BLACK_MOVED_PLAY, Piece.getMovedCode(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertEquals(ROOK_WHITE_PLAY + 24, Piece.getMovedCode(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertEquals(PAWN_BLACK_MOVED, Piece.getMovedCode(PAWN_BLACK_MOVED), "cas pion noir bouge");
        assertEquals(KING_BLACK + 24, Piece.getMovedCode(KING_BLACK), "cas roi noir non joueur");
        assertEquals(KING_WHITE + 24, Piece.getMovedCode(KING_WHITE), "cas roi blanc non joueur");
        assertEquals(EMPTY, Piece.getMovedCode(EMPTY), "cas vide");
        assertEquals(EMPTY_EN_PASSANT_BLACK, Piece.getMovedCode(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertEquals(EMPTY_EN_PASSANT_WHITE, Piece.getMovedCode(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testGetRoleCode() {
        assertEquals(Piece.KING_ROLE, Piece.getRoleCode(KING_BLACK_MOVED_PLAY), "cas roi noir bougé une fois trait noir");
        assertEquals(Piece.ROOK_ROLE, Piece.getRoleCode(ROOK_WHITE_PLAY), "cas tour blanche trait blanc");
        assertEquals(Piece.PAWN_ROLE, Piece.getRoleCode(PAWN_BLACK_MOVED), "cas pion noir bouge");
        assertEquals(Piece.KING_ROLE, Piece.getRoleCode(KING_BLACK), "cas roi noir non joueur");
        assertEquals(Piece.KING_ROLE, Piece.getRoleCode(KING_WHITE), "cas roi blanc non joueur");
        assertEquals(-1, Piece.getRoleCode(EMPTY), "cas vide");
        assertEquals(-1, Piece.getRoleCode(EMPTY_EN_PASSANT_BLACK), "cas vide mais pion blanc en passant");
        assertEquals(-1, Piece.getRoleCode(EMPTY_EN_PASSANT_WHITE), "cas vide mais pion noir en passant");
    }

    @Test
    final void testTurnInto() {
        assertEquals(PAWN_BLACK_MOVED - Piece.getRoleCode(PAWN_BLACK_MOVED) + Piece.QUEEN_ROLE,
                Piece.turnInto(PAWN_BLACK_MOVED, Piece.QUEEN_ROLE), "cas pion noir bouge => dame noir bouge");
        assertEquals(PAWN_WHITE - Piece.getRoleCode(PAWN_WHITE) + Piece.KNIGHT_ROLE,
                Piece.turnInto(PAWN_WHITE, Piece.KNIGHT_ROLE), "cas pion blanc => chevalier blanc");
    }

    @Test
    final void testSetPlayer() {
        assertEquals(ROOK_WHITE_PLAY, Piece.setPlayer(ROOK_WHITE_PLAY, true), "cas tour blanche trait blanc => trait blanc");
        assertEquals(PAWN_WHITE + 12, Piece.setPlayer(PAWN_WHITE, true), "cas pion blanc => trait blanc");
        assertEquals(PAWN_WHITE, Piece.setPlayer(PAWN_WHITE, false), "cas pion blanc=> trait noir");
        assertEquals(KING_BLACK + 12, Piece.setPlayer(KING_BLACK, false), "cas pion blanc=> trait noir");
    }
}
