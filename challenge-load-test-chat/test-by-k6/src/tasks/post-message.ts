import http from 'k6/http';
import { check } from 'k6';
import { Endpoints } from '../endpoints';

export function postMessage(baseUrl: string, chatId: string) {
  const payload = JSON.stringify({ sender: 'k6-user', content: 'Hello from k6' });
  const params = { headers: { 'Content-Type': 'application/json' } };
  const res = http.post(`${baseUrl}${Endpoints.MESSAGE_CREATE(chatId)}`, payload, params);
  check(res, { 'post message status is 201': (r) => r.status === 201 });
  return res;
}
