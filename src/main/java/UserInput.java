import java.util.Scanner;
import java.util.InputMismatchException;

public class UserInput {

    private static final String WELCOME = "Welcome to Monster Mash!";
    private static final String WELCOME_2 = "Everyone is given a monster.";
    private static final String WELCOME_3 = "Each turn, each player will be able to decide what direction they will move in.";
    private static final String WELCOME_4 = "If you encounter another monster, they will fight each other.";
    private static final String WELCOME_5 = "The last monster standing wins!!";
    private static final String WELCOME_6 = "Right now, we have a limit of 4 monsters per game.";
    private static final String WELCOME_7 = "To begin, how many people will be playing?";
    private static final String PLAYER_INPUT_NUM = "Please input a number 1 - 4.";
    private static final String PLAYER = "Player ";
    private static final String PLAYER_NAME_INPUT = " what is the name of your monster?";

    private BattleBoard board;

    static Scanner s = new Scanner(System.in);
    private Integer numPlayers = 0;
    private UserVO player1 = new UserVO("Frank", false);
    private UserVO player2 = new UserVO("Nessie", false);
    private UserVO player3 = new UserVO("BigFoot", false);
    private UserVO player4 = new UserVO("Drax", false);

    private Monster monster1;
    private Monster monster2;
    private Monster monster3;
    private Monster monster4;


    public UserInput(BattleBoard board)
    {
        this.board = board;
    }

    public void userInput() {


        System.out.println(WELCOME);
        System.out.println(WELCOME_2);
        System.out.println(WELCOME_3);
        System.out.println(WELCOME_4);
        System.out.println(WELCOME_5);
        System.out.println(WELCOME_6);
        System.out.println(WELCOME_7);
        numberOfHumanPlayers();
        getPlayerNames();
        setUpMonsters();
        /*runs endless loop when input char
         * Add ability to provide own name
         */
    }

    private void numberOfHumanPlayers() {
        do {
            System.out.println(PLAYER_INPUT_NUM);
            try {
                numPlayers = s.nextInt();
            } catch (InputMismatchException e) {
                s.nextLine();
            } catch (StringIndexOutOfBoundsException e) {
                s.hasNextLine();
            }
        } while (numPlayers >= 4 || numPlayers <= 0);

    }

    private void getPlayerNames() {
        for (int i = 1; i <= numPlayers; i++) {
            switch (i) {
                case 1:
                    System.out.println(PLAYER + i + PLAYER_NAME_INPUT);
                    s.nextLine();
                    player1.setUserName(s.nextLine());
                    player1.setIsHuman(true);
                    break;
                case 2:
                    System.out.println(PLAYER + i + PLAYER_NAME_INPUT);
                    player2.setUserName(s.nextLine());
                    player2.setIsHuman(true);
                    break;
                case 3:
                    System.out.println(PLAYER + i + PLAYER_NAME_INPUT);
                    player3.setUserName(s.nextLine());
                    player3.setIsHuman(true);
                    break;
                case 4:
                    System.out.println(PLAYER + i + PLAYER_NAME_INPUT);
                    player4.setUserName(s.nextLine());
                    player4.setIsHuman(true);
                    break;
                default:
                    System.out.println("Error, please restart game. :(");
            }
        }
    }

    private void setUpMonsters() {
        //Move to set up

        monster1 = new Monster(player1, board);
        monster2 = new Monster(player2, board);
        monster3 = new Monster(player3, board);
        monster4 = new Monster(player4, board);
        /* Incoporate messages
         *  Make sure that random attacks and health are competative
         */
        System.out.println("Each monster has been given a random attack and health");
        System.out.println("Players are:");
        System.out.println("Player 1: Name: " + monster1.getMonster().getName() + " Attack: " + monster1.getMonster().getAttack() + " Health: " + monster1.getMonster().getHealth());
        System.out.println("Player 2: Name: " + monster2.getMonster().getName() + " Attack: " + monster2.getMonster().getAttack() + " Health: " + monster2.getMonster().getHealth());
        System.out.println("Player 3: Name: " + monster3.getMonster().getName() + " Attack: " + monster3.getMonster().getAttack() + " Health: " + monster3.getMonster().getHealth());
        System.out.println("Player 4: Name: " + monster4.getMonster().getName() + " Attack: " + monster4.getMonster().getAttack() + " Health: " + monster4.getMonster().getHealth());
        System.out.println("Each player, human and NPC, have been randomly generated a spot on the battleboard.");
        System.out.println("Here are your spots.");
        board.reDrawBoard();

        //start(player1, player2, player3, player4);
        s.close();
    }

    public Monster getMonster1()
    {
        return monster1;
    }

    public Monster getMonster2()
    {
        return monster2;
    }

    public Monster getMonster3()
    {
        return monster3;
    }

    public Monster getMonster4()
    {
        return monster4;
    }
}