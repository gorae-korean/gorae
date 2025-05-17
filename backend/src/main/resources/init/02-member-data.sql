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