class Sovelluslogiikka:
    def __init__(self, tulos=0):
        self.tulos = tulos
        self.historia = []

    def aseta_arvo(self, arvo):
        self.historia.append(self.tulos)
        self.tulos = arvo
        print(self.historia)

    def palauta_viimeisin(self):
        if len(self.historia) != 0:
            arvo = self.historia.pop()
            self.tulos = arvo

class Erotus:
    def __init__(self, sovellus, syote):
        self._sovellus = sovellus
        self._lue_syote = syote

    def suorita(self):
        arvo = self._lue_syote()
        self._sovellus.aseta_arvo(self._sovellus.tulos-arvo)

class Summa:
    def __init__(self, sovellus, syote):
        self._sovellus = sovellus
        self._lue_syote = syote

    def suorita(self):
        arvo = self._lue_syote()

        self._sovellus.aseta_arvo(self._sovellus.tulos+arvo)

class Nollaa:
    def __init__(self, sovellus, syote):
        self._sovellus = sovellus
        self._lue_syote = syote

    def suorita(self):
        self._sovellus.aseta_arvo(0)

class Kumoa:
    def __init__(self, sovellus, syote):
        self._sovellus = sovellus
        self._lue_syote = syote

    def suorita(self):
        self._sovellus.palauta_viimeisin()
