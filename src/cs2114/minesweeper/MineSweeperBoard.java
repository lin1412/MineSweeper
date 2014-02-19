package cs2114.minesweeper;

import java.util.Random;

// -------------------------------------------------------------------------
/**
 * The board which the game "Mine Sweeper" will run on..
 *
 * @author Hang Lin
 * @version Sep 12, 2011
 */
public class MineSweeperBoard
    extends MineSweeperBoardBase
{

    private MineSweeperCell[][] board;


    // ----------------------------------------------------------
    /**
     * Create a new MineSweeperBoard object with a number of rows, columns, and
     * mines.
     *
     * @param rows
     *            is the number of rows the board have.
     * @param columns
     *            is the number of columns the board have.
     * @param mines
     *            is the number of mines the board have.
     */

    public MineSweeperBoard(int rows, int columns, int mines)
    {
        // create new board with every cell set to COVERED_CELL.
        board = new MineSweeperCell[rows][columns];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                board[i][j] = MineSweeperCell.COVERED_CELL;
            }
        }
        // Pick a random number between 0 and the inputed value
        // as the row and column and if that cell don't have
        // a mine there, sets a mine there.
        //
        for (int m = 0; m < mines; m++)
        {
            Random number = new Random();
            int r = number.nextInt(rows);
            int c = number.nextInt(columns);
            if (board[r][c] == MineSweeperCell.COVERED_CELL)
            {
                board[r][c] = MineSweeperCell.MINE;
            }
            else
            {
                m--;
            }
        }
    }


    /**
     * @param row
     *            is the row of the cell in board.
     * @param column
     *            is the column of the cell in board.
     * @Override the flagCell method.
     */

    public void flagCell(int row, int column)
    {
        // if it's a empty cell, change it's status to flag.
        if (board[row][column] == MineSweeperCell.COVERED_CELL)
        {
            board[row][column] = MineSweeperCell.FLAG;
        }
        // if it's cell have a mine, change it's status to flagged mine.
        if (board[row][column] == MineSweeperCell.MINE)
        {
            board[row][column] = MineSweeperCell.FLAGGED_MINE;
        }
    }


    /**
     * @param row
     *            is the row of the cell on board
     * @param column
     *            is the column of the cell on board
     * @return The contents of the cell
     * @Override the getCell method
     */

    public MineSweeperCell getCell(int row, int column)
    {
        return board[row][column];
    }


    /**
     * @return whether the game has lost.
     * @Override the isGameLost method.
     */

    public boolean isGameLost()
    {
        // if any cell has an UNCOVERED_MINE, the game is lost
        for (int i = 0; i < numberOfRows(); i++)
        {
            for (int j = 0; j < numberOfColumns(); j++)
            {
                if (board[i][j] == MineSweeperCell.UNCOVERED_MINE)
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @return whether the game has won.
     * @Override the isGameWon method.
     */

    public boolean isGameWon()
    {
        for (int r = 0; r < numberOfRows(); r++)
        {
            for (int c = 0; c < numberOfColumns(); c++)
            {
                // if the board has a mine, a flag, a covered cell,
                // or an uncovered_mine, then the game is still on,
                // otherwise it's game won.
                if (board[r][c] == MineSweeperCell.MINE
                    || board[r][c] == MineSweeperCell.FLAG
                    || board[r][c] == MineSweeperCell.COVERED_CELL
                    || board[r][c] == MineSweeperCell.UNCOVERED_MINE)
                {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * @param row
     *            is the row of the cell on board.
     * @param column
     *            is the column of the cell on board.
     * @return numMine is the number of mines adjacent to the cell.
     * @Override the numberOfAdjacentMines method.
     */

    public int numberOfAdjacentMines(int row, int column)
    {
        int numMine = 0;
        for (int r = row - 1; r < row + 2; r++)
        {
            // if the targeted row is the top row, start from it's row.
            if (r == -1)
            {
                r++;
            }
            // if the targeted row is the bottom row, end at it's row.
            else if (r == board.length)
            {
                break;
            }
            for (int c = column - 1; c < column + 2; c++)
            {
                // if the targeted column is the left most column, start from
                // it's column.
                if (c == -1)
                {
                    c++;
                }
                // if the targeted column is the right most column, end
                // at it's column.
                if (c == board[r].length)
                {
                    break;
                }
                // if there are mines, flagged mines, or uncovered mine,
                // increase the mine count by 1.
                if (board[r][c] == MineSweeperCell.MINE
                    || board[r][c] == MineSweeperCell.FLAGGED_MINE
                    || board[r][c] == MineSweeperCell.UNCOVERED_MINE)
                {
                    numMine++;
                }
            }
        }
        return numMine;
    }


    /**
     * @return the column size.
     * @Override the numberOfColumns method.
     */

    public int numberOfColumns()
    {
        return board[0].length;
    }


    /**
     * @return the row size.
     * @Override the numberOfRows method.
     */
    public int numberOfRows()
    {
        return board.length;
    }


    /**
     * @Override the revealBoard method.
     */
    public void revealBoard()
    {
        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[row].length; col++)
            {
                // uncover the cell one by one on the board.
                uncoverCell(row, col);
            }
        }

    }


    /**
     * @param row
     *            is the row of the cell on board.
     * @param column
     *            is the column of the cell on board.
     * @param status
     *            is the status that the cell is going to become.
     * @Override the setCell method.
     */

    protected void setCell(int row, int column, MineSweeperCell status)
    {
        board[row][column] = status;

    }


    /**
     * @param row
     *            is the row of the cell on board.
     * @param column
     *            is the column of the cell on board.
     * @Override the uncoverCell method.
     */

    public void uncoverCell(int row, int column)
    {
        if (board[row][column] == MineSweeperCell.COVERED_CELL)
        {
            // if it's an empty cell, find how many adjacent mines are there.
            board[row][column] =
                MineSweeperCell.adjacentTo(numberOfAdjacentMines(row, column));
        }
        else if (board[row][column] == MineSweeperCell.MINE)
        {
            // if there is a mine there, it's becomes a UNCOVERED_MINE
            // and game over.
            board[row][column] = MineSweeperCell.UNCOVERED_MINE;
        }
    }

}
