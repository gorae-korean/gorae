-- Member 테이블 (Instructor, Student의 부모 테이블)
INSERT INTO member (name, email, role, created_at, updated_at, provider, oauth_id)
VALUES ('김민형', 'kmh24631@gmail.com', 'INSTRUCTOR', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', '114687843089321836407'),
       ('김민형', 'pristo2463@gmail.com', 'INSTRUCTOR', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', '103913072695475252937');

-- 비밀번호: 모든 계정 'password123'
-- OAuth 없는 계정
INSERT INTO member (name, email, password, role, phone_number, created_at, updated_at)
VALUES ('박선생', 'instructor3@example.com', 'password123', 'INSTRUCTOR',
        '010-3456-7890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('최학생', 'student1@example.com', 'password123', 'STUDENT',
        '010-4567-8901', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('정학생', 'student2@example.com', 'password123', 'STUDENT',
        '010-5678-9012', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('한학생', 'student3@example.com', 'password123', 'STUDENT',
        '010-6789-0123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('윤학생', 'student4@example.com', 'password123', 'STUDENT',
        '010-7890-1234', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- OAuth 있는 계정
INSERT INTO member (name, email, role, created_at, updated_at, provider, oauth_id)
VALUES ('김민형', 'pristo24631@gmail.com', 'STUDENT', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', '113499328422296948680');

-- Student 테이블 (Member 상속)
INSERT INTO student (id, is_first)
VALUES (4, true),
       (5, true),
       (6, false),
       (7, false),
       (8, true);

-- Instructor 테이블 (Member 상속)
INSERT INTO instructor (id)
VALUES (1),
       (2),
       (3);