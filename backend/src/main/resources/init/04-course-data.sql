-- Course 테이블
INSERT INTO course (level, title, start_time, end_time, instructor_id, textbook_id, created_at, updated_at)
VALUES ('BEGINNER', '프로그래밍 기초', '2025-05-01 09:00:00', '2025-06-30 18:00:00', 1, 1, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('INTERMEDIATE', '웹 개발 중급', '2025-05-15 10:00:00', '2025-07-15 17:00:00', 1, 2, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('ADVANCED', '알고리즘 고급', '2025-06-01 13:00:00', '2025-08-01 16:00:00', 2, 3, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('BEGINNER', '데이터베이스 입문', '2025-05-10 14:00:00', '2025-06-10 17:00:00', 3, 4, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('INTERMEDIATE', '모바일 앱 개발', '2025-07-01 09:30:00', '2025-08-30 15:30:00', 2, 5, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);