from locust import HttpUser, SequentialTaskSet, task, between
from endpoints import Endpoints

class ChatScenario(SequentialTaskSet):
    chat_id = None
    message_id = None

    @task
    def create_chat(self):
        payload = {"title": "Scenario Chat"}
        with self.client.post(Endpoints.CHAT_LIST_CREATE, json=payload, catch_response=True) as response:
            if response.status_code == 201:
                self.chat_id = response.json().get("id")
            else:
                response.failure(f"Failed to create chat: {response.status_code}")

    @task
    def post_message(self):
        if not self.chat_id:
            self.interrupt()
            return
        payload = {"sender": "scenario-user", "content": "Hello"}
        with self.client.post(Endpoints.MESSAGE_CREATE.format(chat_id=self.chat_id), json=payload, catch_response=True) as response:
            if response.status_code == 201:
                self.message_id = response.json().get("id")
            else:
                response.failure(f"Failed to post message: {response.status_code}")

    @task
    def list_messages(self):
        if not self.chat_id:
            self.interrupt()
            return
        self.client.get(Endpoints.MESSAGE_LIST.format(chat_id=self.chat_id))

    @task
    def update_message(self):
        if not self.chat_id or not self.message_id:
            self.interrupt()
            return
        payload = {"sender": "scenario-user", "content": "Updated"}
        self.client.put(Endpoints.MESSAGE_DETAIL_UPDATE.format(chat_id=self.chat_id, message_id=self.message_id), json=payload)

    @task
    def get_message(self):
        if not self.chat_id or not self.message_id:
            self.interrupt()
            return
        self.client.get(Endpoints.MESSAGE_DETAIL_UPDATE.format(chat_id=self.chat_id, message_id=self.message_id))

    @task
    def stop(self):
        self.interrupt()

class ScenarioUser(HttpUser):
    wait_time = between(1, 5)
    tasks = [ChatScenario]
