-- Member 테이블 (Instructor, Student의 부모 테이블)
-- 비밀번호: 모든 계정 'password123'
INSERT INTO member (id, name, email, password, role, phone_number, created_at, updated_at, provider_id)
VALUES (1, '김교수', 'instructor1@example.com', 'password123', 'INSTRUCTOR',
        '010-1234-5678', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (2, '이강사', 'instructor2@example.com', 'password123', 'INSTRUCTOR',
        '010-2345-6789', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (3, '박선생', 'instructor3@example.com', 'password123', 'INSTRUCTOR',
        '010-3456-7890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (5, '최학생', 'student1@example.com', 'password123', 'STUDENT',
        '010-4567-8901', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (6, '정학생', 'student2@example.com', 'password123', 'STUDENT',
        '010-5678-9012', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (7, '한학생', 'student3@example.com', 'password123', 'STUDENT',
        '010-6789-0123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (8, '윤학생', 'student4@example.com', 'password123', 'STUDENT',
        '010-7890-1234', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (9, '임학생', 'student5@example.com', 'password123', 'STUDENT',
        '010-8901-2345', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (4, '김민형', 'pristo24631@gmail.com', null, 'STUDENT', null,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'google');

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