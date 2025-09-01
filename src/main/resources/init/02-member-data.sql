-- Member 테이블 (Instructor, Student의 부모 테이블)
INSERT INTO member (id, public_id, name, email, role, created_at, updated_at, provider, oauth_id)
VALUES (8900001, gen_random_uuid(), '김민형', 'kmh24631@gmail.com', 'INSTRUCTOR', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', '114687843089321836407'),
       (8900002, gen_random_uuid(), '김민형', 'pristo2463@gmail.com', 'INSTRUCTOR', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', '103913072695475252937'),
       (8910001, gen_random_uuid(), '김정수', 'gyeongsim1906@gmail.com ', 'INSTRUCTOR', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', null),
       (8910002, gen_random_uuid(), '조한나', 'j3stat4u@gmail.com', 'INSTRUCTOR', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', null);

-- OAuth 있는 계정
INSERT INTO member (id, public_id, name, email, role, created_at, updated_at, provider, oauth_id)
VALUES (8900003, gen_random_uuid(), '김민형', 'pristo24631@gmail.com', 'STUDENT', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'google', '113499328422296948680');

-- Student 테이블 (Member 상속)
INSERT INTO student (id, is_first)
VALUES (8900003, true);

-- Instructor 테이블 (Member 상속)
INSERT INTO instructor (id, profile_image_url)
VALUES (8900001, null),
       (8900002, null),
       (8910001, 'https://static.goraekorean.site/image/tutor/profile/jungsoo-kim.webp'),
       (8910002, null);