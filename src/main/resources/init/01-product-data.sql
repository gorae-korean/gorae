-- product 테이블
INSERT INTO product (id, public_id, created_at, updated_at, name, price, discounted_price, currency_code, count,
                     duration, lesson_ratio)
VALUES (2, '7b1f0e3d-2e49-4c53-bf10-8a1e7e1d3c45', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FOUR_CLASSES_ONE_TO_FOUR',
        '400', '360', 'THB', 4, 28, 4),
       (3, '5e6d8c7a-1f23-4b8b-9c12-7d6e2e4f5b67', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FOUR_CLASSES_ONE_TO_TWO',
        '660', '590', 'THB', 4, 28, 2),
       (4, 'a2d4c8e1-6f4b-4e2e-8c91-2e3f7b9d1a23', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'EIGHT_CLASSES_ONE_TO_FOUR',
        '800', '650', 'THB', 8, 56, 4),
       (5, 'f4e7c5b2-3d8e-4a1b-8e6c-9d2b5a7f3c18', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'EIGHT_CLASSES_ONE_TO_TWO',
        '1320', '1050', 'THB', 8, 56, 2);

INSERT INTO product_benefit (product_id, benefits)
VALUES (2, '1:4 group lesson'),
       (2, '1-hour Google Meet class'),
       (2, 'Post-class feedback'),
       (3, '1:2 group lesson'),
       (3, '1-hour Google Meet class'),
       (3, 'Post-class feedback'),
       (4, '1:4 group lesson'),
       (4, '1-hour Google Meet class'),
       (4, 'Post-class feedback'),
       (5, '1:2 group lesson'),
       (5, '1-hour Google Meet class'),
       (5, 'Post-class feedback');
