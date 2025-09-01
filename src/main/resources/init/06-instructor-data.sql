-- 강사의 정기적 가능 시간 샘플 데이터
INSERT INTO instructor_availability
(public_id, created_at, updated_at, instructor_id, day_of_week, start_time, end_time, is_active)
VALUES
-- 강사1의 가능 시간
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900001, 'MONDAY', '09:00:00', '12:00:00', true),
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900001, 'MONDAY', '19:00:00', '23:00:00', true),
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900001, 'WEDNESDAY', '10:00:00', '16:00:00', true),
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900001, 'FRIDAY', '13:00:00', '19:00:00', true),

-- 강사2의 가능 시간
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900002, 'TUESDAY', '10:00:00', '15:00:00', true),
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900002, 'THURSDAY', '13:00:00', '18:00:00', true),
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900002, 'SATURDAY', '09:00:00', '13:00:00', true);

-- 강사의 불가능 날짜 샘플 데이터
INSERT INTO instructor_unavailable_date
(public_id, created_at, updated_at, instructor_id, start_date, end_date, reason)
VALUES
-- 강사1의 불가능 날짜
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900001,
 '2025-06-01', '2025-06-09', '여름 휴가'),
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900001,
 '2025-05-25', '2025-05-25', '개인 일정'),

-- 강사2의 불가능 날짜
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900002,
 '2025-05-30', '2025-06-02', '가족 여행'),
(gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8900002,
 '2025-05-28', '2025-05-28', '병원 예약');