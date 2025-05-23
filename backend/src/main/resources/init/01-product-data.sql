-- product 테이블
INSERT INTO product (created_at, updated_at, name, price, currency_code)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FIRST_TICKET',
        '10.00', 'USD'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'MONTHLY_TICKETS',
        '50.00', 'USD');