from playerReader import PlayerReader
from playerStats import PlayerStats
import requests
import datetime

def main():
    url = 'https://nhlstatisticsforohtu.herokuapp.com/players'
    reader = PlayerReader(url).get_players()
    stats = PlayerStats(reader)
    players = stats.top_scorers_by_nationality('FIN')
    for player in players:
        print(player)

if __name__ == "__main__":
    main()
