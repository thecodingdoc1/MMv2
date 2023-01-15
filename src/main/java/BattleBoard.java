import java.util.Arrays;

public class BattleBoard {
	static int numPlayers = 0;
	char[][] battleBoard = new char[10][10];
	
	public void buildBattleBoard() {
		for (char[] row : battleBoard) {
			Arrays.fill(row, ' ');
		}
	}
	
	public void reDrawBoard() {
		int tile = 1;
		while(tile <= 30) {System.out.print("-"); tile++; };
		System.out.println();
		for(int i = 0; i < battleBoard.length; i++)  {
			for (int j = 0; j < battleBoard[i].length; j++) {
				System.out.print("| "+ battleBoard[i][j] + " |");
			}
			System.out.println();
		}
		tile = 1;
		while(tile <= 30) {System.out.print("-"); tile++; };
		System.out.println();
	}
}