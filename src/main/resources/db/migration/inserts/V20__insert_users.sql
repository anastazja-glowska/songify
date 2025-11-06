INSERT INTO users (email, password, authorities, enabled)
VALUES
    ('bartek', '$2a$10$BNfAQd2Mg5AjdTlGGzpQ6evRRUyN/dauHtunNj1HM1Y3Si0L8oUWK', '{ ROLE_USER}', true),
    ('anastazja', '$2a$10$BNfAQd2Mg5AjdTlGGzpQ6evRRUyN/dauHtunNj1HM1Y3Si0L8oUWK', '{ROLE_USER, ROLE_ADMIN}', true);