-- product 테이블
INSERT INTO product (public_id, created_at, updated_at, name, price, discounted_price, currency_code, count)
VALUES ('3f8c2a4e-9b2e-4a13-b2d3-4c6e8e8f0a12', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FIRST_CLASS',
        '1.00', '1.00', 'USD', 1),
       ('7b1f0e3d-2e49-4c53-bf10-8a1e7e1d3c45', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FOUR_CLASSES_ONE_TO_FOUR',
        '25.00', '22.00', 'USD', 4),
       ('5e6d8c7a-1f23-4b8b-9c12-7d6e2e4f5b67', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FOUR_CLASSES_ONE_TO_TWO',
        '41.00', '36.00', 'USD', 4),
       ('a2d4c8e1-6f4b-4e2e-8c91-2e3f7b9d1a23', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'EIGHT_CLASSES_ONE_TO_FOUR',
        '50.00', '40.00', 'USD', 8),
       ('f4e7c5b2-3d8e-4a1b-8e6c-9d2b5a7f3c18', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'EIGHT_CLASSES_ONE_TO_TWO',
        '82.00', '65.00', 'USD', 8);