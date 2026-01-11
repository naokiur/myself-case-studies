export const Endpoints = {
  CHAT_LIST_CREATE: "/api/chat",
  CHAT_DETAIL: (chatId: string) => `/api/chat/${chatId}`,
  MESSAGE_LIST: (chatId: string) => `/api/chat/${chatId}/messages`,
  MESSAGE_CREATE: (chatId: string) => `/api/chat/${chatId}/message`,
  MESSAGE_DETAIL_UPDATE: (chatId: string, messageId: string) => `/api/chat/${chatId}/messages/${messageId}`,
};
