-- Parent table
CREATE TABLE link (
    short_code CHAR(8) PRIMARY KEY,
    short_code_first CHAR(1) GENERATED ALWAYS AS (SUBSTRING(short_code FROM 1 FOR 1)) STORED,
    original_url VARCHAR(2048) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP
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
            CREATE TABLE link_%s PARTITION OF link
            FOR VALUES IN (%L)', c, c);
    END LOOP;
END $$;