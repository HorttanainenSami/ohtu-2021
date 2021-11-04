import unittest
from statistics import Statistics
from player import Player

class PlayerReader_mock:
    def get_players(self):
        return[
            Player( "sami", "punaiset", 10, 10),
            Player( "joni", "siniset", 1, 10),
            Player( "meri", "punaiset", 31, 1),
            Player( "heli", "siniset", 51, 60),
        ]

class Test_statistics(unittest.TestCase):
    def setUp(self):
        reader = PlayerReader_mock()
        self.statistics = Statistics(reader)

    def test_pelaajien_maara_oikein(self):
        self.punainen = self.statistics.team("punaiset")
        self.assertEqual(len(self.punainen), 2)

    def test_jarjesta_maalien_mukaan(self):
        self.pelaajat_jarjestyksessa = self.statistics.top_scorers(3)
        self.assertEqual(str(self.pelaajat_jarjestyksessa[0]), "heli siniset 51 + 60 = 111")

    def test_jarjesta_maalien_mukaan_antaa_oikean_maaran_pelaajia(self):
        self.pelaajat_jarjestyksessa = self.statistics.top_scorers(3)
        self.assertEqual(len(self.pelaajat_jarjestyksessa), 3)
    
    def test_etsi_pelaaja_toimii_oikein(self):
        self.etsitty_pelaaja = self.statistics.search("sami")

        self.assertEqual(str(self.etsitty_pelaaja), "sami punaiset 10 + 10 = 20")
    def test_etsi_pelaaja_toimii_oikein_etsiessa_olematonta_pelaajaa(self):
        self.etsitty_pelaaja = self.statistics.search("asd")
        self.assertEqual(self.etsitty_pelaaja, None)
