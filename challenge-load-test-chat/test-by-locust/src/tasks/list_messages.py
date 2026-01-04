from locust import task, TaskSet
from endpoints import Endpoints

class ListMessagesTask(TaskSet):
    @task
    def list_messages(self):
        chat_id = getattr(self.user, 'chat_id', None)
        if chat_id:
            self.client.get(Endpoints.MESSAGE_LIST.format(chat_id=chat_id))
