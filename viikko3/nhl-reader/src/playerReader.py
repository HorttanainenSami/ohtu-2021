import requests
from player import Player

class PlayerReader():
    def __init__(self, url):
        self.url = url

    def get_players(self):
        response = requests.get(self.url).json()
        players = []

        for player in response:
            player = Player(
                player['name'],
                player['team'],
                player['goals'],
                player['assists'],
                player['nationality'])
            players.append(player)

        return players
