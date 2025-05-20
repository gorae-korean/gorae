-- 강사의 정기적 가능 시간 샘플 데이터
INSERT INTO instructor_availability
(id, created_at, updated_at, instructor_id, day_of_week, start_time, end_time, is_active)
VALUES
-- 강사1의 가능 시간
(1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 'MONDAY', '09:00:00', '12:00:00', true),
(2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 'MONDAY', '14:00:00', '18:00:00', true),
(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 'WEDNESDAY', '10:00:00', '16:00:00', true),
(4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 'FRIDAY', '13:00:00', '19:00:00', true),

-- 강사2의 가능 시간
(5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 'TUESDAY', '10:00:00', '15:00:00', true),
(6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 'THURSDAY', '13:00:00', '18:00:00', true),
(7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 'SATURDAY', '09:00:00', '13:00:00', true),

-- 강사3의 가능 시간
(8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 'MONDAY', '15:00:00', '20:00:00', true),
(9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 'WEDNESDAY', '16:00:00', '21:00:00', true),
(10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 'FRIDAY', '14:00:00', '19:00:00', true);

-- 강사의 불가능 날짜 샘플 데이터
INSERT INTO instructor_unavailable_date
(id, created_at, updated_at, instructor_id, start_date_time, end_date_time, reason)
VALUES
-- 강사1의 불가능 날짜
(1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1,
 '2025-06-01 00:00:00', '2025-06-07 23:59:59', '여름 휴가'),
(2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1,
 '2025-05-25 09:00:00', '2025-05-25 18:00:00', '개인 일정'),

-- 강사2의 불가능 날짜
(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2,
 '2025-05-30 00:00:00', '2025-06-02 23:59:59', '가족 여행'),
(4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2,
 '2025-05-28 13:00:00', '2025-05-28 18:00:00', '병원 예약'),

-- 강사3의 불가능 날짜
(5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3,
 '2025-07-15 00:00:00', '2025-07-30 23:59:59', '해외 연수'),
(6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3,
 '2025-06-10 14:00:00', '2025-06-10 21:00:00', '세미나 참석');