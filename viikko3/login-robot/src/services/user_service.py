from entities.user import User
import re


class UserInputError(Exception):
    pass


class AuthenticationError(Exception):
    pass


class UserService:
    def __init__(self, user_repository):
        self._user_repository = user_repository

    def check_credentials(self, username, password):
        if not username or not password:
            raise UserInputError("Username and password are required")

        user = self._user_repository.find_by_username(username)

        if not user or user.password != password:
            raise AuthenticationError("Invalid username or password")

        return user

    def create_user(self, username, password):
        self.validate(username, password)

        user = self._user_repository.create(
            User(username, password)
        )

        return user

    def validate(self, username, password):
        if not username or not password:
            raise UserInputError("Username and password are required")
        
        result = re.search('[a-z]{3}', username)
        if result is None:
            raise UserInputError('Username must be atleast 3 characters long')


        result_password = re.search('[^a-z]', password)
        if len(password) <= 7:
            raise UserInputError('Password must be atleast 8 characters long')
        if result_password is None:
            raise UserInputError('Password must contain numbers or special characters')
