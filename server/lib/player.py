class Player:
    username_currentOpponent = ''

    def __init__(self, username):
        self.username = username

    def match_new(self, username_currentOpponent):
        self.username_currentOpponent = username_currentOpponent

    def match_leave(self):
        self.username_currentOpponent = ''
