public class MonsterVO {
    private String name;
    private char piece;
    private int health;
    private int attack;
    private int positionX;
    private int positionY;
    private boolean alive;
    private boolean human;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getPiece() {
        return piece;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setIsAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isHuman() {
        return human;
    }

    public void setIsHuman(boolean human) {
        this.human = human;
    }
}