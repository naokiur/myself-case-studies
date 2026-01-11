from locust import task, TaskSet
from endpoints import Endpoints

class GetChatTask(TaskSet):
    @task
    def get_chat(self):
        chat_id = getattr(self.user, 'chat_id', None)
        if chat_id:
            self.client.get(Endpoints.CHAT_DETAIL.format(chat_id=chat_id))
