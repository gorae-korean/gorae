-- 샘플 데이터 초기화 스크립트

-- Member 테이블 (Instructor, Student의 부모 테이블)
-- 비밀번호: 모든 계정 'password123'
INSERT INTO member (member_type, name, email, password, phone_number, created_at, updated_at)
VALUES ('INSTRUCTOR', '김교수', 'instructor1@example.com', 'password123',
        '010-1234-5678', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('INSTRUCTOR', '이강사', 'instructor2@example.com', 'password123',
        '010-2345-6789', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('INSTRUCTOR', '박선생', 'instructor3@example.com', 'password123',
        '010-3456-7890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('STUDENT', '최학생', 'student1@example.com', 'password123',
        '010-4567-8901', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('STUDENT', '정학생', 'student2@example.com', 'password123',
        '010-5678-9012', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('STUDENT', '한학생', 'student3@example.com', 'password123',
        '010-6789-0123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('STUDENT', '윤학생', 'student4@example.com', 'password123',
        '010-7890-1234', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('STUDENT', '임학생', 'student5@example.com', 'password123',
        '010-8901-2345', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Instructor 테이블 (Member 상속)
INSERT INTO instructor (id)
VALUES (1),
       (2),
       (3);

-- Student 테이블 (Member 상속)
INSERT INTO student (id, is_first)
VALUES (4, true),
       (5, true),
       (6, false),
       (7, false),
       (8, true);

-- Textbook 테이블
INSERT INTO textbook (created_at, updated_at)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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

-- Enrollment 테이블
INSERT INTO enrollment (student_id, course_id, enrolled_at, status)
VALUES (4, 1, '2025-04-15 10:30:00', 'ENROLLED'),
       (5, 1, '2025-04-16 11:45:00', 'ENROLLED'),
       (6, 2, '2025-04-20 09:15:00', 'ENROLLED'),
       (7, 2, '2025-04-21 14:20:00', 'ENROLLED'),
       (8, 3, '2025-05-01 16:10:00', 'ENROLLED'),
       (4, 3, '2025-05-02 13:25:00', 'ENROLLED'),
       (5, 4, '2025-04-25 10:05:00', 'ENROLLED'),
       (6, 5, '2025-06-10 11:30:00', 'ENROLLED'),
       (7, 4, '2025-04-28 15:40:00', 'ENROLLED'),
       (8, 5, '2025-06-12 09:50:00', 'ENROLLED');

-- Ticket 테이블 (추가)
INSERT INTO ticket (student_id, start_time, end_time, status, created_at, updated_at)
VALUES (4, '2025-05-01 00:00:00', '2025-05-31 23:59:59', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, '2025-05-01 00:00:00', '2025-05-31 23:59:59', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, '2025-05-01 00:00:00', '2025-05-31 23:59:59', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (7, '2025-05-01 00:00:00', '2025-05-31 23:59:59', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (8, '2025-05-01 00:00:00', '2025-05-31 23:59:59', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, '2025-04-01 00:00:00', '2025-04-30 23:59:59', 'USED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, '2025-04-01 00:00:00', '2025-04-30 23:59:59', 'USED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, '2025-04-01 00:00:00', '2025-04-30 23:59:59', 'USED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
