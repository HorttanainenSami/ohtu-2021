class TennisGame:
    def __init__(self, player1_name, player2_name):
        self.player1_name = player1_name
        self.player2_name = player2_name
        self.m_score1 = 0
        self.m_score2 = 0

    def won_point(self, player_name):
        if player_name == "player1":
            self.m_score1 = self.m_score1 + 1
        else:
            self.m_score2 = self.m_score2 + 1

    def get_score(self):
        score = ""

        if self.m_score1 == self.m_score2:
            score = self._score_even(score)
        elif self.m_score1 >= 4 or self.m_score2 >= 4:
            score = self._score_tiebrake(score)
        else:
            score = self._score_advantage(score)
        return score

    def _score_even(self, score):
        if self.m_score1 == 4:
            score = self._convert_score(self.m_score1)
        else:
            score = f"{self._convert_score(self.m_score1)}-All"
        return score

    def _score_tiebrake(self, score):
        minus_result = self.m_score1 - self.m_score2
        if minus_result == 1:
            score = "Advantage player1"
        elif minus_result == -1:
            score = "Advantage player2"
        elif minus_result >= 2:
            score = "Win for player1"
        else:
            score = "Win for player2"

        return score

    def _score_advantage(self, score):
        temp_score = 0
        for i in range(1, 3):
            if i == 1:
                temp_score = self.m_score1
            else:
                score = score + "-"
                temp_score = self.m_score2

            score = score + self._convert_score(temp_score)

        return score

    def _convert_score(self, score):

        if score == 0:
            return "Love"
        if score == 1:
            return "Fifteen"
        if score == 2:
            return "Thirty"
        if score == 3:
            return  "Forty"
        if score == 4:
            return "Deuce"
