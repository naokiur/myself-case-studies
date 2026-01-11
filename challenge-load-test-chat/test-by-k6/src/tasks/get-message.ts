import http from 'k6/http';
import { check } from 'k6';
import { Endpoints } from '../endpoints';

export function getMessage(baseUrl: string, chatId: string, messageId: string) {
  const res = http.get(`${baseUrl}${Endpoints.MESSAGE_DETAIL_UPDATE(chatId, messageId)}`);
  check(res, { 'get message status is 200': (r) => r.status === 200 });
  return res;
}
