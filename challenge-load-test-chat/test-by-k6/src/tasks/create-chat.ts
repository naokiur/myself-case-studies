import http from 'k6/http';
import { check } from 'k6';
import { Endpoints } from '../endpoints';

export function createChat(baseUrl: string) {
  const payload = JSON.stringify({ title: 'K6 Chat' });
  const params = { headers: { 'Content-Type': 'application/json' } };
  const res = http.post(`${baseUrl}${Endpoints.CHAT_LIST_CREATE}`, payload, params);
  check(res, { 'create chat status is 201': (r) => r.status === 201 });
  return res;
}
