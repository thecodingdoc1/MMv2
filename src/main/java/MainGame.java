import java.util.Scanner;

public class MainGame {

    static Scanner s = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException {
		BattleBoard board = new BattleBoard();
        UserInput userInput = new UserInput(board, s);
        Turns rounds = new Turns(board, userInput);
        board.buildBattleBoard();
        board.reDrawBoard();
		userInput.userInput();
		rounds.turn();
        s.close();

    }
}
