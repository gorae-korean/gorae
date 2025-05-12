-- product 테이블
INSERT INTO product (id, created_at, updated_at, name, price, currency_code)
VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FIRST_TICKET',
        '10.00', 'USD'),
       (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'MONTHLY_TICKETS',
        '50.00', 'USD');