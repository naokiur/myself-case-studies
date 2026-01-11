import http from 'k6/http';
import { check } from 'k6';
import { Endpoints } from '../endpoints';

export function listMessages(baseUrl: string, chatId: string) {
  const res = http.get(`${baseUrl}${Endpoints.MESSAGE_LIST(chatId)}`);
  check(res, { 'list messages status is 200': (r) => r.status === 200 });
  return res;
}
