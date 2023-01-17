import org.apache.commons.lang3.ArrayUtils;

import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Turns {
    private static final String NORTH = "North";
    private static final String EAST = "East";
    private static final String SOUTH = "South";
    private static final String WEST = "West";
    private static final String PLAYER = "Player";
    private static final String HEALTH = "Health";
    private static final String BELOW_ARE_THE_GAME_STATS = "below are the game stats: ";
    private static final String CONGRATULATION = "Congratulation ";
    private static final String YOU_HAVE_WON_MONSTER_MASH = ", you have won Monster Mash!";
    private static final String HAS = " has ";
    private static final String HEALTH_LEFT = " health left";
    private static final String MOVED = " moved ";
    private static final String DEALT = " dealt ";
    private static final String TO = " to ";
    private static final String DIRECTIONS = " 1 = North, 2 = East, 3 = South, 4 = West";
    private static final String PLEASE_ENTER_NUMBER_FOR_NEXT_MOVEMENT_DIRECTION = "Please enter number for next movement direction: ";
    private BattleBoard board;
    private UserInput players;

    private MonsterVO[] listOfMonsters = new MonsterVO[4];

    private int numMonstersDead = 0;
    int movement = 0;

    public Turns(BattleBoard board, UserInput players) {
        this.board = board;
        this.players = players;
    }


    public void turn() throws InterruptedException {

        listOfMonsters[0] = players.getMonster1().getMonster();
        listOfMonsters[1] = players.getMonster2().getMonster();
        listOfMonsters[2] = players.getMonster3().getMonster();
        listOfMonsters[3] = players.getMonster4().getMonster();
        while (numMonstersDead < 3) {
            //reDrawBoard();
            for (MonsterVO m : listOfMonsters) {
                int arrayItemIndex = ArrayUtils.indexOf(listOfMonsters, m);
                if (m.isAlive() && !m.isHuman()) {
                    moveMonsterAuto(listOfMonsters, arrayItemIndex);
                    board.reDrawBoard();
                    movement = 0;
                } else if (m.isAlive() && m.isHuman()) {
                    move(listOfMonsters, arrayItemIndex);
                    board.reDrawBoard();
                    movement = 0;
                } else if (!m.isAlive()) {
                    continue;
                }
                Thread.sleep(200);
            }
            roundSummary();
        }
        for (MonsterVO m : listOfMonsters) {
            if (m.isAlive()) {
                endgame(m, listOfMonsters);
            }
        }
    }

    private void moveMonsterAuto(MonsterVO[] monsters, int location) {
        MonsterVO monster = monsters[location];
        do {
            movement = generateMovement();
        } while (movementOob(movement, monster));

        moveAuto(movement, monster);
    }

    private void move(MonsterVO[] monsters, int location) {
        MonsterVO monster = monsters[location];
        do{
            System.out.println(PLEASE_ENTER_NUMBER_FOR_NEXT_MOVEMENT_DIRECTION);
            System.out.println(DIRECTIONS);
            try {
                movement = players.s.nextInt();
            } catch (InputMismatchException e) {
                players.s.nextLine();
            } catch (StringIndexOutOfBoundsException e) {
                players.s.hasNextLine();
            }
        } while (movementOob(movement, monster));

        moveAuto(movement, monster);
    }

    private int generateMovement() {
        while (movement >= 5 || movement <= 0) {
            movement = (int) (Math.random() * 4);
        }
        return movement;
    }

    private boolean movementOob(int movement, MonsterVO monster) {
        switch (movement) {
            case 1:
                return (monster.getPositionY() - 1 < 0);
            case 2:
                return (monster.getPositionX() + 1 > 9);
            case 3:
                return (monster.getPositionY() + 1 > 9);
            case 4:
                return (monster.getPositionX() - 1 < 0);
            default:
                return false;
        }
    }

    private void moveAuto(int movement, MonsterVO monster) {
        if (!checkAttack(movement, monster)) {
            resetBoardPosition(monster);
            switch (movement) {
                case 1:
                    monster.setPositionY(monster.getPositionY() - 1);
                    board.battleBoard[monster.getPositionY()][monster.getPositionX()] = monster.getPiece();
                    moveSummary(monster, NORTH);
                    break;
                case 2:
                    monster.setPositionX(monster.getPositionX() + 1);
                    board.battleBoard[monster.getPositionY()][monster.getPositionX()] = monster.getPiece();
                    moveSummary(monster, EAST);
                    break;
                case 3:
                    monster.setPositionY(monster.getPositionY() + 1);
                    board.battleBoard[monster.getPositionY()][monster.getPositionX()] = monster.getPiece();
                    moveSummary(monster, SOUTH);
                    break;
                case 4:
                    monster.setPositionX(monster.getPositionX() - 1);
                    board.battleBoard[monster.getPositionY()][monster.getPositionX()] = monster.getPiece();
                    moveSummary(monster, WEST);
                    break;
            }
        }
    }

    private boolean checkAttack(int movement, MonsterVO monster) {
        switch (movement) {
            case 1:
                if (board.battleBoard[monster.getPositionY() - 1][monster.getPositionX()] != ' ') {
                    determineAttack(board.battleBoard[monster.getPositionY() - 1][monster.getPositionX()], monster);
                    return true;
                } else {
                    return false;
                }
            case 2:
                if (board.battleBoard[monster.getPositionY()][monster.getPositionX() + 1] != ' ') {
                    determineAttack(board.battleBoard[monster.getPositionY()][monster.getPositionX() + 1], monster);
                    return true;
                } else {
                    return false;
                }
            case 3:
                if (board.battleBoard[monster.getPositionY() + 1][monster.getPositionX()] != ' ') {
                    determineAttack(board.battleBoard[monster.getPositionY() + 1][monster.getPositionX()], monster);
                    return true;
                } else {
                    return false;
                }
            case 4:
                if (board.battleBoard[monster.getPositionY()][monster.getPositionX() - 1] != ' ') {
                    determineAttack(board.battleBoard[monster.getPositionY()][monster.getPositionX() - 1], monster);
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    private void determineAttack(char piece, MonsterVO monster) {
        for (MonsterVO m : listOfMonsters) {
            if (m.getPiece() == piece) {
                calculateAttack(m, monster);
            }
        }
    }

    private void calculateAttack(MonsterVO defender, MonsterVO attacker) {
        defender.setHealth(defender.getHealth() - attacker.getAttack());
        System.out.println(attacker.getName() + DEALT + attacker.getAttack() + TO + defender.getName());
        if (defender.getHealth() <= 0) {
            defender.setIsAlive(false);
            resetBoardPosition(defender);
        } else {
            attacker.setHealth(attacker.getHealth() - defender.getAttack());
            System.out.println(defender.getName() + DEALT + defender.getAttack() + TO + attacker.getName());
            if (attacker.getHealth() <= 0) {
                attacker.setIsAlive(false);
                resetBoardPosition(attacker);
            }
        }
    }

    private void moveSummary(MonsterVO monster, String direction) {
        System.out.println(monster.getName() + MOVED + direction);
    }

    private void roundSummary()
    {
        for( MonsterVO m : listOfMonsters)
        {
            System.out.println(m.getName() + HAS + m.getHealth() + HEALTH_LEFT);
        }
    }

    private void resetBoardPosition(MonsterVO monster)
    {
        board.battleBoard[monster.getPositionY()][monster.getPositionX()] = ' ';
    }

    //if occupied, fight (1 round)
    //print out result of move including damage and current condition of player if attack occurred
    //print notes if player is eliminated

    private static void endgame(MonsterVO winner, MonsterVO[] monsters) {
        System.out.println(CONGRATULATION + winner.getName() + YOU_HAVE_WON_MONSTER_MASH);
        System.out.println(BELOW_ARE_THE_GAME_STATS);
        System.out.println(PLAYER + " " + monsters[0].getName() + " " + HEALTH + ": " + monsters[0].getHealth());
        System.out.println(PLAYER + " " + monsters[1].getName() + " " + HEALTH + ": " + monsters[1].getHealth());
        System.out.println(PLAYER + " " + monsters[2].getName() + " " + HEALTH + ": " + monsters[2].getHealth());
        System.out.println(PLAYER + " " + monsters[3].getName() + " " + HEALTH + ": " + monsters[2].getHealth());
    }
}
