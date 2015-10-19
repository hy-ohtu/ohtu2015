package ohtu;

public class TennisGame {
    
    private String player1Name;
    private String player2Name;
    
    private enum Score {
        LOVE (0, "Love"),
        FIFTEEN (1, "Fifteen"),
        THIRTY (2, "Thirty"),
        FORTY (3, "Forty"),
        MORE (4, "Deuce");
        
        int key;
        String desc;
        
        Score(int key, String desc) {
            this.key = key;
            this.desc = desc;
        }
        
        public static Score getFor(int key) {
            for (Score score : Score.values()) {
                if (score.key == key) { return score; }
            }
            return Score.MORE;
        }
    }
    private Score m_score1 = Score.LOVE;
    private Score m_score2 = Score.LOVE;

    private int scoreDiff = 0;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name)) {
            m_score1 = Score.getFor(m_score1.key+1);
            scoreDiff++;
        }
        else if (playerName.equals(player2Name)) {
            m_score2 = Score.getFor(m_score2.key+1);  
            scoreDiff--;
        } 
    }

    public String getScore() {
        String score;
        if (m_score1==m_score2 && scoreDiff == 0) {
            score = getEvenScore();
        }
        else if (m_score1 == Score.MORE || m_score2 == Score.MORE) {   
            score = getWinningScore();
        }
        else {
            score = m_score1.desc + "-" + m_score2.desc;
        }
        return score;
    }

    private String getWinningScore() {
        String score;
        if (scoreDiff==1) score = "Advantage player1";
        else if (scoreDiff == -1) score = "Advantage player2";
        else if (scoreDiff >= 2) score = "Win for player1";
        else score ="Win for player2";
        return score;
    }

    private String getEvenScore() {
        if (m_score1 == Score.MORE) {
            return "Deuce";
        }
        return m_score1.desc + "-All";
    }
}