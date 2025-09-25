-- product 테이블
INSERT INTO product (id, public_id, created_at, updated_at, name, price, discounted_price, currency_code, count,
                     duration, lesson_ratio)

VALUES (1, 'b7e9c8a4-3f2d-4de2-9a07-5f1a6b2c8f52', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FIRST_CLASS',
        '3.60', '2.00', 'USD', 1, 7, 7),
       (2, '7b1f0e3d-2e49-4c53-bf10-8a1e7e1d3c45', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ONE_CLASS',
        '3.60', '3.60', 'USD', 1, 7, 7),
       (3, '5e6d8c7a-1f23-4b8b-9c12-7d6e2e4f5b67', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FOUR_CLASSES',
        '14.40', '13.00', 'USD', 4, 28, 7),
       (4, 'a2d4c8e1-6f4b-4e2e-8c91-2e3f7b9d1a23', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'EIGHT_CLASSES',
        '28.80', '23.00', 'USD', 8, 56, 7);

INSERT INTO product_benefit (product_id, benefits)
VALUES (1, '1:7 group lesson'),
       (1, '1-hour Google Meet class'),
       (1, 'Only one-time purchase'),
       (2, '1:7 group lesson'),
       (2, '1-hour Google Meet class'),
       (2, 'Post-class feedback'),
       (3, '1:7 group lesson'),
       (3, '1-hour Google Meet class'),
       (3, 'Post-class feedback'),
       (4, '1:7 group lesson'),
       (4, '1-hour Google Meet class'),
       (4, 'Post-class feedback');
