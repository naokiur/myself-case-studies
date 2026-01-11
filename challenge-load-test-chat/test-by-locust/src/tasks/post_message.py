from locust import task, TaskSet
from endpoints import Endpoints

class PostMessageTask(TaskSet):
    @task
    def post_message(self):
        chat_id = getattr(self.user, 'chat_id', None)
        if chat_id:
            self.client.post(Endpoints.MESSAGE_CREATE.format(chat_id=chat_id), json={"sender": "locust", "content": "hello"})
