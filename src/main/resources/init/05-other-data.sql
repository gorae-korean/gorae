-- Ticket 테이블
INSERT INTO ticket (public_id, student_id, start_time, end_time, status, created_at, updated_at, lesson_ratio)
VALUES
-- 지난 달 티켓 (USED)
( gen_random_uuid(), 8900003, date_trunc('month', NOW() - interval '1 month')
, date_trunc('month', NOW()) - interval '1 second', 'USED'
, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 7),
( gen_random_uuid(), 8900003, date_trunc('month', NOW() - interval '1 month')
, date_trunc('month', NOW()) - interval '1 second', 'USED'
, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 7),

-- -- 이번 달 티켓 (ACTIVE)
( gen_random_uuid(), 8900003, date_trunc('month', NOW())
, date_trunc('month', NOW() + interval '1 month') - interval '1 second', 'ACTIVE'
, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 7),
( gen_random_uuid(), 8900003, date_trunc('month', NOW())
, date_trunc('month', NOW() + interval '1 month') - interval '1 second', 'ACTIVE'
, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 7),
( gen_random_uuid(), 8900003, date_trunc('month', NOW())
, date_trunc('month', NOW() + interval '1 month') - interval '1 second', 'ACTIVE'
, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 7),
( gen_random_uuid(), 8900003, date_trunc('month', NOW())
, date_trunc('month', NOW() + interval '1 month') - interval '1 second', 'ACTIVE'
, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 7);

INSERT INTO enrollment (public_id, student_id, course_id, ticket_id, enrolled_at, status)
VALUES (gen_random_uuid(), 8900003, 1, 1, NOW() - interval '7 day', 'ENROLLED'),
       (gen_random_uuid(), 8900003, 2, 2, NOW() - interval '7 day', 'ENROLLED');