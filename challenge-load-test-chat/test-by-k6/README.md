# Load Test with k6

チャットREST APIに対する負荷テストスクリプトです。TypeScriptで実装されています。

## セットアップ

k6がインストールされていることを確認してください。
インストール方法は[公式ドキュメント](https://k6.io/docs/getting-started/installation/)を参照してください。

また、依存関係をインストールします。

```bash
cd test-by-k6
npm install
```

## 実行方法

k6はTypeScriptを直接実行できないため、実行前にビルドが必要です。
（※本プロジェクトでは簡易的なビルド環境を提供しています）

```bash
npm run build
```

ビルド後、`dist/index.js` を指定して実行します。
環境変数 `TEST_TYPE` を指定することで、Locustと同様の実行パターンを切り替えられます。

### 1. 特定のAPIに対してリクエストを投げ続ける

`TEST_TYPE` に対象のAPIを指定します。

```bash
k6 run -e TEST_TYPE=create_chat dist/index.js
```

指定可能な値:
- `create_chat`
- `get_chat`
- `post_message`
- `list_messages`
- `get_message`
- `update_message`

### 2. すべてのAPIに対してバランスよくリクエストを投げ続ける

`TEST_TYPE=all` を使用します。

```bash
k6 run -e TEST_TYPE=all dist/index.js
```

### 3. シナリオ（一連の操作）を実行する

`TEST_TYPE=scenario` を使用します（デフォルト）。

```bash
k6 run -e TEST_TYPE=scenario dist/index.js
# または
k6 run dist/index.js
```

### オプション

- **ホストの指定**: `-e BASE_URL=http://localhost:8080`
- **VU数、実行時間の指定**: `--vus 10 --duration 1m`
- **RPS（秒間リクエスト数）の指定**: `-e RPS=50`
  - RPSを指定すると、k6の `constant-arrival-rate` エグゼキュータを使用してスループットを固定します。
  - この場合、`--vus` 指定は無視されます。

例:
```bash
# シナリオテストを50 RPSで30秒間実行
k6 run -e TEST_TYPE=scenario -e RPS=50 --duration 30s dist/index.js
```

## ディレクトリ構造

- `src/`: 負荷テストスクリプトのソースコード
  - `endpoints.ts`: APIエンドポイントの定義
  - `tasks/`: 各APIごとのタスク定義
  - `index.ts`: 実行用エントリポイント
