import java.util.Random;
import java.util.Scanner;

public class mayintarlasi {
    private int[][] mineField;
    private boolean[][] revealed;
    private int mineCount;
    private int size;
    private int remainingCells;

    public mayintarlasi(int size) {
        this.size = size;
        mineField = new int[size][size];
        revealed = new boolean[size][size];
        mineCount = size * size / 4;
        remainingCells = size * size - mineCount;
        placeMines();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (remainingCells > 0) {
            printBoard();
            int row = -1;
            int col = -1;
            do {
                System.out.print("Satır girin (1-" + size + "): ");
                row = scanner.nextInt() - 1;
                System.out.print("Sütun girin (1-" + size + "): ");
                col = scanner.nextInt() - 1;
            } while (row < 0 || row >= size || col < 0 || col >= size || revealed[row][col]);
            revealCell(row, col);
            if (mineField[row][col] == -1) {
                System.out.println("Mayına bastınız! Oyun bitti.");
                printBoard();
                return;
            }
        }
        System.out.println("Tebrikler, tüm mayınları buldunuz!");
        printBoard();
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < mineCount) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (mineField[row][col] != -1) {
                mineField[row][col] = -1;
                minesPlaced++;
            }
        }
    }

    private void revealCell(int row, int col) {
        revealed[row][col] = true;
        remainingCells--;
        if (mineField[row][col] == 0) {
            revealNeighbors(row, col);
        }
    }

    private void revealNeighbors(int row, int col) {
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r >= 0 && r < size && c >= 0 && c < size && !revealed[r][c]) {
                    revealCell(r, c);
                }
            }
        }
    }

    private void printBoard() {
        System.out.println();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (revealed[row][col]) {
                    if (mineField[row][col] == -1) {
                        System.out.print("* ");
                    } else {
                        System.out.print(mineField[row][col] + " ");
                    }
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println(row + 1);
        }
        for (int col = 0; col < size; col++) {
            System.out.print((col + 1) + " ");
        }
        System.out.println();
    }
}