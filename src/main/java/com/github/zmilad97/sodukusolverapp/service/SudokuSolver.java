package com.github.zmilad97.sodukusolverapp.service;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Pair {
    int row;
    int column;

    public Pair(int row, int column) {
        this.row = row;
        this.column = column;
    }
}


@Service
public class SudokuSolver {
    List<Integer[]> solved = new ArrayList<>();


    public boolean beginSolve(String data) {
        if (checkValidInputBoard(data)) {
            List<Integer[]> board = parseDataToBoard(data);
            if (!isValidInputBoard(board))
                return false;
            solveBoard(board);
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidInputBoard(List<Integer[]> board) {
        for (int j = 0; j < 9; j++) {
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                if (board.get(j)[i] != 0 && set.contains(board.get(j)[i]))
                    return false;
                else
                    set.add(board.get(j)[i]);
            }
        }

        for (int j = 0; j < 9; j++) {
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                if (board.get(i)[j] != 0 && set.contains(board.get(i)[j]))
                    return false;
                else
                    set.add(board.get(i)[j]);
            }
        }
        int x = 0;
        for (int w = 0; w < 3; w++) {
            int y = 0;
            for (int z = 0; z < 3; z++) {
                Set<Integer> set = new HashSet<>();
                for (int i = 0; i < 3; i++) {
                    for (int j = y; j < 3; j++) {
                        if (board.get(i)[j] != 0 && set.contains(board.get(i)[j]))
                            return false;
                        else
                            set.add(board.get(i)[j]);
                    }
                }
                if (y != 6)
                    y = y + 3;
            }
            if (x != 6)
                x = x + 3;
        }
        return true;
    }


    private boolean checkValidInputBoard(String data) {
        data = data.replace(",", "");
        char[] charArray = data.toCharArray();
        if (charArray.length > 81)
            return false;
        for (char datum : charArray) {
            if (!Character.isDigit(datum))
                return false;
        }
        return true;
    }

    private List<Integer[]> parseDataToBoard(String data) {
        String[] arrBoard = data.split(",");
        List<Integer[]> board = new ArrayList<>();


        int[] intArray = new int[arrBoard.length];
        for (int i = 0; i < arrBoard.length; i++)
            intArray[i] = Integer.parseInt(arrBoard[i]);
        int x = 0;

        for (int i = 0; i < 9; i++) {
            board.add(new Integer[9]);
            for (int j = 0; j < 9; j++) {
                if (x < intArray.length)
                    if (intArray[x] > 0 && intArray[x] < 10)
                        board.get(i)[j] = intArray[x];
                    else
                        board.get(i)[j] = 0;
                else
                    board.get(i)[j] = 0;
                x++;
            }
        }
        return board;
    }

    private boolean solveBoard(List<Integer[]> board) {
        Pair empty = findEmpty(board);
        int row, column;
        if (empty == null) {
            setSolved(board);
            return true;
        } else {
            row = empty.row;
            column = empty.column;
        }

        for (int i = 1; i < 10; i++) {
            if (isValid(board, i, empty)) {
                board.get(row)[column] = i;

                if (solveBoard(board)) {
                    setSolved(board);
                    return true;
                }

                board.get(row)[column] = 0;
            }
        }
        return false;
    }

    private boolean isValid(List<Integer[]> board, int number, Pair empty) {
        for (int i = 0; i < 9; i++) {
            if (board.get(empty.row)[i] == number && empty.column != i)
                return false;
        }

        for (int i = 0; i < 9; i++) {
            if (board.get(i)[empty.column] == number && empty.row != i)
                return false;
        }

        int squareX = empty.column / 3;
        int squareY = empty.row / 3;

        for (int i = squareY * 3; i < squareY * 3 + 3; i++) {
            for (int j = squareX * 3; j < squareX * 3 + 3; j++) {
                if (board.get(i)[j] == number && i != empty.row && j != empty.column)
                    return false;
            }
        }
        return true;
    }

    private Pair findEmpty(List<Integer[]> board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i)[j] == 0)
                    return new Pair(i, j);
            }
        }
        return null;
    }

    private void setSolved(List<Integer[]> board) {
        this.solved = board;
    }

    public List<Integer[]> getSolved() {
        return solved;
    }

}
