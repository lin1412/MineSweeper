package cs2114.minesweeper;

/**
 * Test the MineSweeperBoard class.
 *
 * @author Hang Lin
 * @version Sep 12, 2011
 */
public class MineSweeperBoardTest
    extends student.TestCase
{
    /**
     * Initialized the board where the test will run on.
     */
    MineSweeperBoard board = new MineSweeperBoard(4, 4, 10);


    // ----------------------------------------------------------
    /**
     * Use the method to compare what we expected from the result we get.
     *
     * @param theBoard
     *            what the test returns
     * @param expected
     *            what is expected from the test
     */
    public void assertBoard(MineSweeperBoard theBoard, String... expected)
    {
        MineSweeperBoard expectedBoard =
            new MineSweeperBoard(expected.length, expected[0].length(), 0);
        expectedBoard.loadBoardState(expected);
        assertEquals(expectedBoard, theBoard);
        // uses equals() from MineSweeperBoardBase
    }


    // ----------------------------------------------------------
    /**
     * test the setCell method.
     */
    public void testsetCell()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        board.setCell(2, 1, MineSweeperCell.FLAGGED_MINE);

        assertBoard(board, "    ", "OOOO", "OM+O", "OOOO");

    }


    // ----------------------------------------------------------
    /**
     * test the flagCell method.
     */
    public void testflagCell()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        board.flagCell(1, 0);
        board.flagCell(2, 1);
        assertBoard(board, "    ", "FOOO", "OM+O", "OOOO");

    }


    // ----------------------------------------------------------
    /**
     * the the gameLost method.
     */
    public void testIsGameLost()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        // test the method before a mine is uncovered.
        assertEquals(false, board.isGameLost());
        board.setCell(2, 1, MineSweeperCell.UNCOVERED_MINE);
        // test the method after a mine is uncovered.
        assertEquals(true, board.isGameLost());
    }


    // ----------------------------------------------------------
    /**
     * test the isGameWon method.
     */
    public void testIsGameWon()
    {
        board.loadBoardState("OOOO", "OOOO", "O++O", "OOOO");
        // test the method before all mines are flagged,
        // and all cells are uncovered.
        assertEquals(false, board.isGameWon());
        // test the method after all mines are flagged,
        // and all cells are uncovered.
        board.loadBoardState("    ", "    ", " MM ", "    ");
        assertEquals(true, board.isGameWon());
    }


    // ----------------------------------------------------------
    /**
     * test the getCell method.
     */
    public void testgetCell()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        // test the method with the expected value of that cell.
        assertEquals(MineSweeperCell.COVERED_CELL, board.getCell(1, 2));
    }


    // ----------------------------------------------------------
    /**
     * test the numberOfColumns method.
     */
    public void testgetColumns()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        // test the method with the expected number of columns.
        assertEquals(4, board.numberOfColumns());
    }


    // ----------------------------------------------------------
    /**
     * test the numberOfRows method.
     */
    public void testgetRows()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        // test the method with the expected number of rows.
        assertEquals(4, board.numberOfRows());
    }


    // ----------------------------------------------------------
    /**
     * test the numberOfAdjacentMines method.
     */
    public void testNumberOfAdjacentMines()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        // test the method with the expected number of mines
        // adjacent to the cell.
        assertEquals(1, board.numberOfAdjacentMines(2, 0));
        assertEquals(0, board.numberOfAdjacentMines(0, 0));
    }


    // ----------------------------------------------------------
    /**
     * test the revealBoard method.
     */
    public void testrevealBoard()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        board.revealBoard();
        assertBoard(board, "    ", "1221", "1**1", "1221");
    }


    // ----------------------------------------------------------
    /**
     * the the uncoverCell method.
     */
    public void testuncoverCell()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        // uncovers the cell in [0][0] and [1][1]compare it to the expected
        // outcome.
        board.uncoverCell(0, 0);
        board.uncoverCell(1, 1);
        assertEquals(MineSweeperCell.ADJACENT_TO_0, board.getCell(0, 0));
        assertEquals(MineSweeperCell.ADJACENT_TO_2, board.getCell(1, 1));
    }

}
