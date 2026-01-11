import http from 'k6/http';
import { check } from 'k6';
import { Endpoints } from '../endpoints';

export function updateMessage(baseUrl: string, chatId: string, messageId: string) {
  const payload = JSON.stringify({ sender: 'k6-user', content: 'Updated by k6' });
  const params = { headers: { 'Content-Type': 'application/json' } };
  const res = http.put(`${baseUrl}${Endpoints.MESSAGE_DETAIL_UPDATE(chatId, messageId)}`, payload, params);
  check(res, { 'update message status is 200': (r) => r.status === 200 });
  return res;
}
