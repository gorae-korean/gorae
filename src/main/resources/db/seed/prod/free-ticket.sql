INSERT INTO gorae.public.ticket(lesson_ratio, created_at, end_time, start_time, student_id, updated_at, public_id,
                                status)
VALUES (2, CURRENT_TIMESTAMP, '2026-01-01 00:00:00+09:00', CURRENT_TIMESTAMP, 5,
        CURRENT_TIMESTAMP, gen_random_uuid(), 'ACTIVE')