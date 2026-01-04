from locust import HttpUser, between
from tasks.create_chat import CreateChatTask
from tasks.get_chat import GetChatTask
from tasks.post_message import PostMessageTask
from tasks.list_messages import ListMessagesTask
from tasks.get_message import GetMessageTask
from tasks.update_message import UpdateMessageTask
from endpoints import Endpoints

class AllApiUser(HttpUser):
    wait_time = between(1, 3)
    chat_id = None
    message_id = None
    
    tasks = {
        CreateChatTask: 1,
        GetChatTask: 2,
        PostMessageTask: 5,
        ListMessagesTask: 5,
        GetMessageTask: 2,
        UpdateMessageTask: 1
    }

    def on_start(self):
        # Setup initial data
        response = self.client.post(Endpoints.CHAT_LIST_CREATE, json={"title": "Setup Chat"})
        if response.status_code == 201:
            self.chat_id = response.json().get("id")
            msg_resp = self.client.post(Endpoints.MESSAGE_CREATE.format(chat_id=self.chat_id), json={"sender": "setup", "content": "setup msg"})
            if msg_resp.status_code == 201:
                self.message_id = msg_resp.json().get("id")
