-- Ticket 테이블
INSERT INTO ticket (public_id, student_id, start_time, end_time, status, used_at, created_at, updated_at)
VALUES
-- 지난 달 티켓 (USED)
(gen_random_uuid(), 3, date_trunc('month', NOW() - interval '1 month'), date_trunc('month', NOW()) - interval '1 second', 'USED'
, NOW() - interval '7 day', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 3, date_trunc('month', NOW() - interval '1 month'), date_trunc('month', NOW()) - interval '1 second', 'USED'
, NOW() - interval '7 day', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 이번 달 티켓 (ACTIVE)
(gen_random_uuid(), 3, date_trunc('month', NOW()), date_trunc('month', NOW() + interval '1 month') - interval '1 second', 'ACTIVE'
, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 3, date_trunc('month', NOW()), date_trunc('month', NOW() + interval '1 month') - interval '1 second', 'ACTIVE'
, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 3, date_trunc('month', NOW()), date_trunc('month', NOW() + interval '1 month') - interval '1 second', 'ACTIVE'
, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 3, date_trunc('month', NOW()), date_trunc('month', NOW() + interval '1 month') - interval '1 second', 'ACTIVE'
, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO enrollment (public_id, student_id, course_id, ticket_id, enrolled_at, status)
VALUES (gen_random_uuid(), 3, 1, 1, NOW() - interval '7 day', 'ENROLLED'),
       (gen_random_uuid(), 3, 2, 2, NOW() - interval '7 day', 'ENROLLED');