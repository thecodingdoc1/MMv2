public class MainGame {

    public static void main(String[] args) {
		BattleBoard board = new BattleBoard();
        UserInput userInput = new UserInput(board);
        Turns rounds = new Turns(board, userInput);
        board.buildBattleBoard();
        board.reDrawBoard();
		userInput.userInput();
		rounds.turn();
	}
}
