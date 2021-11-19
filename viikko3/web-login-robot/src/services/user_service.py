from entities.user import User
from repositories.user_repository import (
    user_repository as default_user_repository
)
import re


class UserInputError(Exception):
    pass


class AuthenticationError(Exception):
    pass


class UserService:
    def __init__(self, user_repository=default_user_repository):
        self._user_repository = user_repository

    def check_credentials(self, username, password):
        if not username or not password:
            raise UserInputError("Username and password are required")

        user = self._user_repository.find_by_username(username)

        if not user or user.password != password:
            raise AuthenticationError("Invalid username or password")

        return user

    def create_user(self, username, password, password_confirmation):
        self.validate(username, password, password_confirmation)

        user = self._user_repository.create(
            User(username, password)
        )

        return user

    def validate(self, username, password, password_confirmation):
        if not username or not password:
            raise UserInputError("Username and password are required")

        # toteuta loput tarkastukset tänne ja nosta virhe virhetilanteissa
        username_result = re.search('[a-z][a-z][a-z]+$', username)

        if not username_result:
            raise UserInputError('Username must be atleast 3 characters long and contain only alphabet')
        if len(password) < 8:
            raise UserInputError('Password must be atleast 8 characters long')
        password_result = re.search('[^a-z]', password)
        if password_result is None:
            raise UserInputError('Password must contain also numbers or special characters')

        if password != password_confirmation:
            raise UserInputError('Password and password confirmation doesn\'t match')

user_service = UserService()
