ALTER TABLE users ADD COLUMN is_account_non_expired BOOLEAN;
ALTER TABLE users ADD COLUMN is_account_non_locked BOOLEAN;
ALTER TABLE users ADD COLUMN is_credentials_non_expired BOOLEAN;
ALTER TABLE users ADD COLUMN is_enabled BOOLEAN;
