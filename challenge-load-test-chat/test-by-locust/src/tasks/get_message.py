from locust import task, TaskSet
from endpoints import Endpoints

class GetMessageTask(TaskSet):
    @task
    def get_message(self):
        chat_id = getattr(self.user, 'chat_id', None)
        message_id = getattr(self.user, 'message_id', None)
        if chat_id and message_id:
            self.client.get(Endpoints.MESSAGE_DETAIL_UPDATE.format(chat_id=chat_id, message_id=message_id))
