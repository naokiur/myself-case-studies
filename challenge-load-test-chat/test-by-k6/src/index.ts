import { sleep, fail } from 'k6';
import { Options } from 'k6/options';
import { createChat } from './tasks/create-chat';
import { getChat } from './tasks/get-chat';
import { postMessage } from './tasks/post-message';
import { listMessages } from './tasks/list-messages';
import { getMessage } from './tasks/get-message';
import { updateMessage } from './tasks/update-message';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';
const TEST_TYPE = __ENV.TEST_TYPE || 'scenario'; // scenario, all, create_chat, etc.
const RPS = __ENV.RPS ? parseInt(__ENV.RPS) : undefined;

export const options: Options = {
  scenarios: {
    load_test: RPS ? {
      executor: 'constant-arrival-rate',
      rate: RPS || 10,
      timeUnit: '1s',
      preAllocatedVUs: Math.max(10, Math.ceil(RPS / 10)),
      maxVUs: Math.max(100, RPS),
      duration: '30s',
    } : {
      executor: 'constant-vus',
      vus: 10,
      duration: '30s',
    },
  },
};

export function setup() {
  const res = createChat(BASE_URL);
  if (res.status !== 201) {
    fail('Failed to setup: could not create chat');
  }
  const chatId = res.json('id') as string;

  const msgRes = postMessage(BASE_URL, chatId);
  if (msgRes.status !== 201) {
    fail('Failed to setup: could not post message');
  }
  const messageId = msgRes.json('id') as string;

  return { chatId, messageId };
}

interface SetupData {
  chatId: string;
  messageId: string;
}

export default function (data: SetupData) {
  const { chatId, messageId } = data;

  switch (TEST_TYPE) {
    case 'scenario':
      runScenario();
      break;
    case 'all':
      runAllApis(chatId, messageId);
      break;
    case 'create_chat':
      createChat(BASE_URL);
      break;
    case 'get_chat':
      getChat(BASE_URL, chatId);
      break;
    case 'post_message':
      postMessage(BASE_URL, chatId);
      break;
    case 'list_messages':
      listMessages(BASE_URL, chatId);
      break;
    case 'get_message':
      getMessage(BASE_URL, chatId, messageId);
      break;
    case 'update_message':
      updateMessage(BASE_URL, chatId, messageId);
      break;
    default:
      runScenario();
  }

  // RPS指定時はスループットを一定に保つためsleepを入れない
  if (!RPS) {
    sleep(Math.random() * 2 + 1); // 1-3 seconds wait
  }
}

function runScenario() {
  // Locust's SequentialTaskSet equivalent
  const createRes = createChat(BASE_URL);
  if (createRes.status !== 201) return;
  const cId = createRes.json('id') as string;

  const postRes = postMessage(BASE_URL, cId);
  if (postRes.status !== 201) return;
  const mId = postRes.json('id') as string;

  listMessages(BASE_URL, cId);
  updateMessage(BASE_URL, cId, mId);
  getMessage(BASE_URL, cId, mId);
}

function runAllApis(chatId: string, messageId: string) {
  const rand = Math.random() * 16;
  if (rand < 1) {
    createChat(BASE_URL);
  } else if (rand < 3) {
    getChat(BASE_URL, chatId);
  } else if (rand < 8) {
    postMessage(BASE_URL, chatId);
  } else if (rand < 13) {
    listMessages(BASE_URL, chatId);
  } else if (rand < 15) {
    getMessage(BASE_URL, chatId, messageId);
  } else {
    updateMessage(BASE_URL, chatId, messageId);
  }
}
