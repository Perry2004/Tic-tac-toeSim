import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        for (int i = 0; i < 9; i ++) {
            board.getCellList().add(new Cells((char)('1' + i)));
        }
        board.setEverything();

        Players player1 = new Players(1);
        Players player2 = new Players(2);

        player1.setTurn(true);
        board.printBoard();
        while (true) {
            player1.input(board, scanner);
            board.printBoard();
            if (board.getIsFinished()) {
                break;
            }
            player2.input(board, scanner);
            board.printBoard();
            if (board.getIsFinished()) {
                break;
            }
        }

        // sends congratulation announcements if there is a winner. 
        if (board.getHoriComplete() || board.getVertComplete() || board.getDiagComplete()) {
            if (player1.onTurn == true) {
                player1.congratulation(board);
            }
            else if (player2.onTurn == true) {
                player2.congratulation(board);
            }
        }
        else {
            System.out.println("All positions are filled. No one wins. ");
        }
    }

    public static class Board {
        private ArrayList<Cells> cellList = new ArrayList<Cells>();
        private boolean isHoriComplete;
        private boolean isVertComplete;
        private boolean isDiagComplete;
        private boolean isAllFilled;
        private boolean isFinished;

        public Board () {}

        private void setEverything () {
            this.isHoriComplete = deterHorizontal();
            this.isVertComplete = deterVertical();
            this.isDiagComplete = deterDiagonal();
            this.isAllFilled = deterAllFilled();
            this.isFinished = (isHoriComplete || isVertComplete || isDiagComplete || isAllFilled);
        }

        public void printBoard () {
            System.out.println("     |     |     ");
            System.out.println("  " + cellList.get(0).getValue() + "  |  " + cellList.get(1).getValue() + "  |  " + cellList.get(2).getValue() + "  ");
            System.out.println("_____|_____|_____");
            System.out.println("     |     |     ");
            System.out.println("  " + cellList.get(3).getValue() + "  |  " + cellList.get(4).getValue() + "  |  " + cellList.get(5).getValue() + "  ");
            System.out.println("_____|_____|_____");
            System.out.println("     |     |     ");
            System.out.println("  " + cellList.get(6).getValue() + "  |  " + cellList.get(7).getValue() + "  |  " + cellList.get(8).getValue() + "  ");
            System.out.println( "     |     |     " );
        }
        public ArrayList<Cells> getCellList () {
            return cellList;
        }
        public boolean deterAllFilled () {
            for (int i = 0; i < 9; i ++) {
                if (cellList.get(i).getSelectStatus() == false) {
                    return false;
                }
            }
            return true;
        }
        public boolean deterHorizontal () {
            int index1 = 0;
            int index2 = 1;
            int index3 = 2;

            for (int i = 0; i < 3; i ++, index1 += 3, index2 += 3, index3 += 3) {
                if (TicTacToe.isThreeSame(this.getCellList().get(index1).getValue(), this.getCellList().get(index2).getValue(), this.getCellList().get(index3).getValue())) {
                    return true;
                }
            }
            return false;
        }
        public boolean deterVertical () {
            int index1 = 0;
            int index2 = 3;
            int index3 = 6;

            for (int i = 0; i < 3; i ++, index1 += 1, index2 += 1, index3 += 1) {
                if (TicTacToe.isThreeSame(this.getCellList().get(index1).getValue(), this.getCellList().get(index2).getValue(), this.getCellList().get(index3).getValue())) {
                    return true;
                }
            }
            return false;
        }
        public boolean deterDiagonal () {
            int index1 = 0;
            int index2 = 4;
            int index3 = 8;
            if (TicTacToe.isThreeSame(this.getCellList().get(index1).getValue(), this.getCellList().get(index2).getValue(), this.getCellList().get(index3).getValue())) {
                return true;
            }
            index1 = 2;
            index2 = 4;
            index3 = 6;
            if (TicTacToe.isThreeSame(this.getCellList().get(index1).getValue(), this.getCellList().get(index2).getValue(), this.getCellList().get(index3).getValue())) {
                return true;
            }
            return false;
        }
        public boolean getHoriComplete () {
            return isHoriComplete;
        }
        public boolean getVertComplete () {
            return isVertComplete;
        }
        public boolean getDiagComplete () {
            return isDiagComplete;
        } 
        public boolean getAllFilled () {
            return isAllFilled;
        }
        public boolean getIsFinished () {
            this.setEverything();
            return isFinished;
        }
    }

    public static class Cells { 
        private char value;
        private boolean selected = false;
        public Cells (char c) {
            value = c;
        }
        public char getValue () {
            return value;
        }
        public boolean getSelectStatus () {
            return selected;
        }
        public void setValue (char c) {
            value = c;
        }
        public void setSelectStatus (boolean b) {
            selected = b;
        }
    }

    public static class Players {
        private boolean onTurn = false;
        private int playerNum;
        public Players (int num) {
            playerNum = num;
        }
        public boolean isOnTurn () {
            return onTurn;
        }
        public int getNum () {
            return playerNum;
        }
        public void input (Board board, Scanner scanner) {
            int choose;
            int choosePosition;
            this.setTurn(true);
            while (true) {
                System.out.print("Player " + playerNum + " input: ");
                choose = scanner.nextInt();
                choosePosition = choose - 1;
                if (board.getCellList().get(choosePosition).getSelectStatus() == false) {
                    if (playerNum == 1) {
                        board.getCellList().get(choosePosition).setValue('X');
                    }
                    else if (playerNum == 2) {
                        board.getCellList().get(choosePosition).setValue('O');
                    }
                    board.getCellList().get(choosePosition).setSelectStatus(true);
                    break;
                }
                else {
                    System.out.println("The position has been used. Please choose another one.");
                }
            }
        }
        public void setTurn (boolean b) {
            onTurn = b;
        }
        public void congratulation (Board board) {
            String winningMethod;
            if (board.getHoriComplete()) {
                winningMethod = "horizontally";
            }
            else if (board.getVertComplete()) {
                winningMethod = "vertically";
            }
            else if (board.getDiagComplete()) {
                winningMethod = "on the diagonal";
            }
            else {
                winningMethod = "";
            }
            System.out.print("Congratulations. \nPlayer" + playerNum + " wins the game. \nThe player wins by placing three consecutiver marks " + winningMethod + ". \n");
        }
    }
    public static boolean isThreeSame (char c1, char c2, char c3) {
        if (c1 == c2) {
            if (c1 == c3) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
