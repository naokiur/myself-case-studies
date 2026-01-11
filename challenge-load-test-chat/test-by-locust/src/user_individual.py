from locust import HttpUser, between
from tasks.create_chat import CreateChatTask
from tasks.get_chat import GetChatTask
from tasks.post_message import PostMessageTask
from tasks.list_messages import ListMessagesTask
from tasks.get_message import GetMessageTask
from tasks.update_message import UpdateMessageTask
from endpoints import Endpoints

class IndividualUserBase(HttpUser):
    abstract = True
    wait_time = between(1, 2)
    chat_id = None
    message_id = None

    def on_start(self):
        # Setup common data
        response = self.client.post(Endpoints.CHAT_LIST_CREATE, json={"title": "Setup Chat"})
        if response.status_code == 201:
            self.chat_id = response.json().get("id")
            # Create a message for details/update tasks
            msg_resp = self.client.post(Endpoints.MESSAGE_CREATE.format(chat_id=self.chat_id), json={"sender": "setup", "content": "setup msg"})
            if msg_resp.status_code == 201:
                self.message_id = msg_resp.json().get("id")

class CreateChatUser(IndividualUserBase):
    tasks = [CreateChatTask]

class GetChatUser(IndividualUserBase):
    tasks = [GetChatTask]

class PostMessageUser(IndividualUserBase):
    tasks = [PostMessageTask]

class ListMessagesUser(IndividualUserBase):
    tasks = [ListMessagesTask]

class GetMessageUser(IndividualUserBase):
    tasks = [GetMessageTask]

class UpdateMessageUser(IndividualUserBase):
    tasks = [UpdateMessageTask]
