-- product 테이블
INSERT INTO product (public_id, created_at, updated_at, name, price, currency_code)
VALUES ('123e4567-e89b-12d3-a456-426614174000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FIRST_TICKET',
        '10.00', 'USD'),
       ('123e4567-e89b-12d3-a456-426614174001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'MONTHLY_TICKETS',
        '50.00', 'USD');