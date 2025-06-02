-- Member 테이블 (Instructor, Student의 부모 테이블)
INSERT INTO member (public_id, name, email, role, created_at, updated_at, provider, oauth_id)
VALUES (gen_random_uuid(), '김민형', 'kmh24631@gmail.com', 'INSTRUCTOR', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', '114687843089321836407'),
       (gen_random_uuid(), '김민형', 'pristo2463@gmail.com', 'INSTRUCTOR', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', '103913072695475252937');

-- OAuth 있는 계정
INSERT INTO member (public_id, name, email, role, created_at, updated_at, provider, oauth_id)
VALUES (gen_random_uuid(), '김민형', 'pristo24631@gmail.com', 'STUDENT', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', '113499328422296948680');

-- Student 테이블 (Member 상속)
INSERT INTO student (id, is_first)
VALUES (3, true);

-- Instructor 테이블 (Member 상속)
INSERT INTO instructor (id)
VALUES (1),
       (2);