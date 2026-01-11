from locust import task, TaskSet
from endpoints import Endpoints

class CreateChatTask(TaskSet):
    @task
    def create_chat(self):
        self.client.post(Endpoints.CHAT_LIST_CREATE, json={"title": "Locust Chat"})
