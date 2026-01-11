import http from 'k6/http';
import { check } from 'k6';
import { Endpoints } from '../endpoints';

export function getChat(baseUrl: string, chatId: string) {
  const res = http.get(`${baseUrl}${Endpoints.CHAT_DETAIL(chatId)}`);
  check(res, { 'get chat status is 200': (r) => r.status === 200 });
  return res;
}
