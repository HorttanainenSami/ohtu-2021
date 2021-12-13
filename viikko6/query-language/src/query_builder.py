from matchers import And, HasAtLeast, PlaysIn, All, Not, HasFewerThan, Or

class QueryBuilder:

    def __init__(self, query=And()):
        self.query = query

    def build(self):
        return self.query

    def playsIn(self, team):
        return QueryBuilder(self.query, And(PlaysIn(team)))

    def hasAtLeast(self, value, attr):
        return QueryBuilder(And(HasAtLeast(value, attr)))

    def hasFewerThan(self, value, attr):
        return QueryBuilder(And(HasFewerThan(value, attr)))



