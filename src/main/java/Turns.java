import org.apache.commons.lang3.ArrayUtils;

public class Turns {
    private BattleBoard board;
    private UserInput players;

    private MonsterVO[] listOfMonsters = new MonsterVO[4];

    private int numMonstersDead = 0;
    int movement = 0;

    public Turns(BattleBoard board, UserInput players) {
        this.board = board;
        this.players = players;
    }


    public void turn() {

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
                    //moveSummary();
                    movement = 0;
                } else if (m.isAlive() && m.isHuman()) {
                    move(m, arrayItemIndex);
                    //moveSummary();
                    movement = 0;
                } else if (!m.isAlive()) {
                    continue;
                }

            }
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

        moveAuto(1, monster);
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
                case 2:
                    monster.setPositionX(monster.getPositionX() + 1);
                    board.battleBoard[monster.getPositionY()][monster.getPositionX()] = monster.getPiece();
                case 3:
                    monster.setPositionY(monster.getPositionY() + 1);
                    board.battleBoard[monster.getPositionY()][monster.getPositionX()] = monster.getPiece();
                case 4:
                    monster.setPositionX(monster.getPositionX() - 1);
                    board.battleBoard[monster.getPositionY()][monster.getPositionX()] = monster.getPiece();
            }
        }
    }

    private boolean checkAttack(int movement, MonsterVO monster) {
        switch (movement) {
            case 1:
                if (board.battleBoard[monster.getPositionY() - 1][monster.getPositionX()] != ' ') {
                    determineAttack(board.battleBoard[monster.getPositionY() - 1][monster.getPositionX()], monster);
                } else {
                    return false;
                }
            case 2:
                if (board.battleBoard[monster.getPositionY()][monster.getPositionX() + 1] != ' ') {
                    determineAttack(board.battleBoard[monster.getPositionY()][monster.getPositionX() + 1], monster);
                } else {
                    return false;
                }
            case 3:
                if (board.battleBoard[monster.getPositionY() + 1][monster.getPositionX()] != ' ') {
                    determineAttack(board.battleBoard[monster.getPositionY() + 1][monster.getPositionX()], monster);
                } else {
                    return false;
                }
            case 4:
                if (board.battleBoard[monster.getPositionY()][monster.getPositionX() - 1] != ' ') {
                    determineAttack(board.battleBoard[monster.getPositionY()][monster.getPositionX() - 1], monster);
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
        if (defender.getHealth() <= 0) {
            defender.setIsAlive(false);
            resetBoardPosition(defender);
        } else {
            attacker.setHealth(attacker.getHealth() - defender.getAttack());
            if (attacker.getHealth() <= 0) {
                attacker.setIsAlive(false);
                resetBoardPosition(attacker);
            }
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
        System.out.println("Congratulation " + winner.getName() + ", you have won Monster Mash!");
        System.out.println("below are the game stats: ");
        System.out.println("Player " + monsters[0].getName() + " Health: " + monsters[0].getHealth());
        System.out.println("Player " + monsters[1].getName() + " Health: " + monsters[1].getHealth());
        System.out.println("Player " + monsters[2].getName() + " Health: " + monsters[2].getHealth());
        System.out.println("Player " + monsters[3].getName() + " Health: " + monsters[2].getHealth());
    }
}
