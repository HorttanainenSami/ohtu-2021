from urllib import request
from project import Project
import toml


class ProjectReader:
    def __init__(self, url):
        self._url = url

    def get_project(self):
        # tiedoston merkkijonomuotoinen sisältö
        content = request.urlopen(self._url).read().decode("utf-8")
        parsed = toml.loads(content)["tool"]["poetry"]
        name = parsed["name"]
        desc = parsed["description"]
        dependencies = list(parsed["dependencies"].keys())
        dev_dep = list(parsed["dev-dependencies"].keys())

        # deserialisoi TOML-formaatissa oleva merkkijono ja muodosta Project-olio sen tietojen perusteella
        return Project(name,  desc, dependencies, dev_dep)
