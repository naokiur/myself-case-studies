# Load Test with Locust

チャットREST APIに対する負荷テストスクリプトです。

## セットアップ

1. Python 3.10以上がインストールされていることを確認してください。
2. 仮想環境を作成し、依存関係をインストールします。

```bash
cd test-by-locust
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
```

## 実行方法

Locustを実行する際は、用途に合わせて使用するファイルを指定します。

### 1. 特定のAPIに対してリクエストを投げ続ける
`src/locustfiles/user_individual.py` を使用します。Web UI上で実行したいUserを選択できます。

```bash
locust -f src/locustfiles/user_individual.py
```

### 2. すべてのAPIに対してバランスよくリクエストを投げ続ける
`src/locustfiles/user_all_apis.py` を使用します。

```bash
locust -f src/locustfiles/user_all_apis.py
```

### 3. シナリオ（一連の操作）を実行する
`src/locustfiles/user_scenario.py` を使用します。

```bash
locust -f src/locustfiles/user_scenario.py
```

### オプション

- **ホストの指定**: `-H http://localhost:8080`
- **ヘッドレスモード（CLI実行）**: `--headless -u 10 -r 1 -t 1m`

例:
```bash
locust -f src/locustfiles/user_scenario.py --headless -u 10 -r 2 -t 30s -H http://localhost:8080
```

```bash
locust -f src/locustfiles/user_scenario.py -H http://localhost:8080
```

## ディレクトリ構造

- `src/`: 負荷テストスクリプトのソースコード
  - `endpoints.py`: APIエンドポイントの定義
  - `tasks/`: 各APIごとのタスク定義（1ファイル1タスク）
  - `locustfiles/`: 実行用Userの定義（Locustファイル）
    - `user_individual.py`: 個別API実行用User
    - `user_all_apis.py`: 全API混合実行用User
    - `user_scenario.py`: シナリオ実行用User
