import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = ' ';


    private static final Scanner SCANNER = new Scanner(System.in);

    private static char[][] field;

    private static final Random RANDOM = new Random();

    private static int fieldSizeX;
    private static int fieldSizeY;
    /**
     * Длина ряда, необходимая для победы
     */
    private static int WIN_COUNT;
    private static int y;

    public static void main(String[] args) {

        initialize();
        printField();
        while (!gameCheck()) {
            humanTurn();
            aiTurn();
            printField();
        }

    }

    /**
     * Создание игрового поля, заполнение полей пустышками
     */
    private static void initialize() {
        System.out.print("Enter the size of field and win length: ");
        fieldSizeX = SCANNER.nextInt();
        fieldSizeY = SCANNER.nextInt();
        WIN_COUNT = SCANNER.nextInt();
        field = new char[fieldSizeX][fieldSizeY];
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    /**
     * метод запрашивает координаты через сканнер
     */
    private static void humanTurn() {
        System.out.print("Enter the coordinates of the field (two numbers: finding the field horizontally, then vertically): ");
        int x = SCANNER.nextInt() - 1;
        int y = SCANNER.nextInt() - 1;

        if (isCellValid(x, y) || !isCellEmpty(x, y)) {
            field[x][y] = DOT_HUMAN;
        } else {
            do {
                System.out.print("Enter the coordinates of the field (two numbers: finding the field horizontally, then vertically): ");
                x = SCANNER.nextInt() - 1;
                y = SCANNER.nextInt() - 1;
            } while (!isCellValid(x, y) && isCellEmpty(x, y));
        }

    }

    private static void aiTurn() {
            int x, y;
            do {
                x = RANDOM.nextInt(fieldSizeX);
                y = RANDOM.nextInt(fieldSizeY);
            }
            while (!isCellEmpty(x, y));
            field[x][y] = DOT_AI;
    }


    /**
     * отрисовка игрового поля путем двойного цикла
     */
    private static void printField() {
        System.out.print(" ");
        for (int i = 0; i < fieldSizeY * 2 + 1; i++) {
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        }
        System.out.println();

        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeY; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();

        }


        for (int i = 0; i < fieldSizeY * 2 + 2; i++) {
            System.out.print("-");
        }
        System.out.println();

    }

    /**
     * проверка, не была ли клетка занята ходом игрока или компьютера
     *
     * @param x координата по горизонтали
     * @param y координата по вертикали
     * @return
     */
    private static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    public static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }


    /**
     * Метод проверки победы, опираясь на 4 метода проверки победного ряда
     *
     * @param c
     * @return
     */
    static boolean checkWin(char c) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkWinRight(c, i, j) || checkWinDiagonalLeftDown(c, i, j) || checkWinDiagonalRightDown(c, i, j) ||
                        checkWinDown(c, i, j)) return true;
            }
        }
        return false;
    }


    /**
     * Метод проверки победного ряда с правой стороны
     *
     * @param c
     * @param x
     * @param y
     * @return
     */
    static boolean checkWinRight(char c, int x, int y) {
        for (int w = 0; w < WIN_COUNT; w++) {
            try {
                if (field[x][y + w] != c) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }

        }
        return true;
    }

    /**
     * Метод проверки победного ряда по диагоняли вниз налево
     *
     * @param c
     * @param x
     * @param y
     * @return
     */
    static boolean checkWinDiagonalLeftDown(char c, int x, int y) throws ArrayIndexOutOfBoundsException {
        for (int w = 0; w < WIN_COUNT; w++) {
            try {
                if (field[x + w][y - w] != c) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }

        }
        return true;
    }

    /**
     * Метод проверки победного ряда вниз направо
     *
     * @param c
     * @param x
     * @param y
     * @return
     */
    static boolean checkWinDiagonalRightDown(char c, int x, int y) {
        for (int w = 0; w < WIN_COUNT; w++) {
            try {
                if (field[x + w][y + w] != c) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки победного ряда вниз
     *
     * @param c
     * @param x
     * @param y
     * @return
     */
    static boolean checkWinDown(char c, int x, int y) {
        for (int w = 0; w < WIN_COUNT; w++) {
            try {
                if (field[x + w][y] != c) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }

        }
        return true;
    }


    /**
     * Метод проверки ничьи, то есть кончились ли пустые ячейки
     *
     * @return
     */
    static boolean checkDraw() {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                try {
                    if (field[i][j] == DOT_EMPTY) return false;
                } catch (ArrayIndexOutOfBoundsException e) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Проверка, завершена ли игра. Проверяет победил ли человек, победил ли компьютер, произошла ли ничья
     *
     * @return
     */
    static boolean gameCheck() {
        if (checkWin(DOT_HUMAN)) {
            System.out.print("You are the winner!");
            return true;
        } else if (checkWin(DOT_AI)) {
            System.out.print("AI is the winner!!");
            return true;
        } else if (checkDraw()) {
            System.out.print("Draw!");
            return true;
        } else return false;
    }
}
