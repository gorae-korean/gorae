-- Course 테이블
INSERT INTO course (public_id, start_time, end_time, instructor_id, textbook_id, created_at, updated_at, max_count)
VALUES (gen_random_uuid(),
        date_trunc('hour', NOW()) + interval '1 hour', date_trunc('hour', NOW()) + interval '2 hour',
        8900001, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4),
       (gen_random_uuid(),
        date_trunc('hour', NOW()) + interval '2 hour', date_trunc('hour', NOW()) + interval '3 hour',
        8900001, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4),
       (gen_random_uuid(),
        date_trunc('hour', NOW()) + interval '1 hour', date_trunc('hour', NOW()) + interval '2 hour',
        8900002, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4),
       (gen_random_uuid(),
        date_trunc('hour', NOW()) + interval '2 hour', date_trunc('hour', NOW()) + interval '3 hour',
        8900002, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
       (gen_random_uuid(),
        date_trunc('hour', NOW()) + interval '3 hour', date_trunc('hour', NOW()) + interval '4 hour',
        8900002, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);