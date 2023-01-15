import java.util.InputMismatchException;
import java.util.Scanner;

public class Monster {

    private BattleBoard board;

    private MonsterVO monster = new MonsterVO();

    int maxXBoardPosition;
    int maxYBoardPosition;

    public Monster(UserVO player, BattleBoard board) {
        this.board = board;
        maxXBoardPosition = board.battleBoard.length - 1;
        maxYBoardPosition = board.battleBoard[0].length - 1;
        monster.setName(player.getUserName());
        monster.setPiece(monster.getName().charAt(0));
        monster.setIsHuman(player.isHuman());
        monster.setIsAlive(true);
        generateMonster();
    }

    private void generateMonster() {
        System.out.println("In generate monster");
        do {
            monster.setHealth((int) (Math.random() * 200));
        } while (monster.getHealth() >= 100 || monster.getHealth() <= 20);

        do {
            monster.setAttack((int) (Math.random() * 20));
        } while (monster.getAttack() >= 10 || monster.getAttack() <= 0);

        do {
            monster.setPositionX((int) (Math.random() * maxXBoardPosition));
            monster.setPositionY((int) (Math.random() * maxYBoardPosition));
        } while (board.battleBoard[monster.getPositionX()][monster.getPositionY()] != ' ');

        board.battleBoard[monster.getPositionY()][monster.getPositionX()] = monster.getPiece();
    }

    public MonsterVO getMonster() {
        return monster;
    }
}