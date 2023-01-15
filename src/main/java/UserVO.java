public class UserVO
{
    private String userName;
    private boolean human;
    private Integer wins;
    private Integer loses;
    private Integer gamesPlayed;
    private Double winPercentage;

    public UserVO (String userName, boolean isHuman)
    {
        this.userName = userName;
        this.human = isHuman;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public boolean isHuman()
    {
        return human;
    }

    public void setIsHuman(boolean human)
    {
        this.human = human;
    }

    public Integer getWins()
    {
        return wins;
    }

    public void setWins(Integer wins)
    {
        this.wins = wins;
    }

    public Integer getLoses()
    {
        return loses;
    }

    public void setLoses(Integer loses)
    {
        this.loses = loses;
    }

    public Integer getGamesPlayed()
    {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed)
    {
        this.gamesPlayed = gamesPlayed;
    }

    public Double getWinPercentage()
    {
        return winPercentage;
    }

    public void setWinPercentage(Double winPercentage)
    {
        this.winPercentage = winPercentage;
    }
}