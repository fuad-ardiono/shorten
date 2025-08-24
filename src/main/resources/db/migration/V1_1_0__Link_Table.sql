-- Parent table
CREATE TABLE IF NOT EXISTS link (
    id UUID DEFAULT uuid_generate_v4(),
    short_code CHAR(8) NOT NULL,
    short_code_first CHAR(1) NOT NULL,
    original_url VARCHAR(2048) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    PRIMARY KEY (id, short_code_first),
    UNIQUE (short_code, short_code_first)
) PARTITION BY LIST (short_code_first);

-- Partition by first character of short code a-z, A-Z, 0-9
DO $$
DECLARE
    chars TEXT[] := ARRAY[
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        '0','1','2','3','4','5','6','7','8','9'
    ];
    c TEXT;
BEGIN
    FOREACH c IN ARRAY chars LOOP
        EXECUTE format('
            DROP TABLE IF EXISTS "link_%s" CASCADE;
            CREATE TABLE "link_%s" PARTITION OF link
            FOR VALUES IN (%L);
        ', c, c, c);
    END LOOP;
END $$;

-- Add an index on short_code for each partition
DO $$
DECLARE
    chars TEXT[] := ARRAY[
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        '0','1','2','3','4','5','6','7','8','9'
    ];
    c TEXT;
BEGIN
    FOREACH c IN ARRAY chars LOOP
        EXECUTE format('
            CREATE UNIQUE INDEX IF NOT EXISTS "link_%s_short_code_idx" ON "link_%s" (short_code);
        ', c, c);
    END LOOP;
END $$;

-- Trigger to set short_code_first before insert
CREATE OR REPLACE FUNCTION set_short_code_first()
RETURNS TRIGGER AS $$
BEGIN
    NEW.short_code_first = SUBSTRING(NEW.short_code FROM 1 FOR 1);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_set_short_code_first
BEFORE INSERT ON link
FOR EACH ROW
EXECUTE FUNCTION set_short_code_first();