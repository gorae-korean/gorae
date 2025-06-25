-- product 테이블
INSERT INTO product (public_id, created_at, updated_at, name, price, currency_code, count, is_available)
VALUES ('123e4567-e89b-12d3-a456-426614174000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FIRST_TICKET',
        '3000', 'KRW', 1, true),
       ('123e4567-e89b-12d3-a456-426614174001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'MONTHLY_TICKETS',
        '20000', 'KRW', 8, true),
       ('123e4567-e89b-12d3-a456-426614174002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'MONTHLY_TICKETS',
        '15000', 'KRW', 2, false);