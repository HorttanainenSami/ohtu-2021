class PlayerStats():
    def __init__(self, reader):
        self.reader = reader

    def top_scorers_by_nationality(self, nationality):
        def filter_by_nationality(player):
            if player.nationality == nationality:
                return True
            return False
        def scores(player):
            return player.get_goals()

        players_by_nationality = list(filter(filter_by_nationality, self.reader))
        players_by_nationality.sort(reverse=True, key=scores)
        for player in players_by_nationality:
            print(player)
        return[]

        
