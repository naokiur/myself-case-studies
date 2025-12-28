CREATE TABLE IF NOT EXISTS "Chats" (
    id UUID PRIMARY KEY,
    title VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS "Messages" (
    id UUID PRIMARY KEY,
    chat_id UUID NOT NULL REFERENCES "Chats"(id) ON DELETE CASCADE,
    sender VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Trigger to auto-update updated_at
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DO $$ BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'chats_set_updated_at') THEN
    CREATE TRIGGER chats_set_updated_at
    BEFORE UPDATE ON "Chats"
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'messages_set_updated_at') THEN
    CREATE TRIGGER messages_set_updated_at
    BEFORE UPDATE ON "Messages"
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();
  END IF;
END $$;
