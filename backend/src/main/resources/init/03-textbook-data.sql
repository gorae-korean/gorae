INSERT INTO textbook (created_at, updated_at, title, level)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Java 기초 마스터', 'BEGINNER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Spring Boot 실전 가이드', 'INTERMEDIATE'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'JPA 프로그래밍', 'ADVANCED'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '파이썬으로 배우는 알고리즘', 'BEGINNER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '스프링 시큐리티 인 액션', 'ADVANCED');

-- 샘플 태그 데이터 추가
INSERT INTO textbook_tags (textbook_id, tag)
VALUES
    -- Java 기초 마스터의 태그들
    (1, 'java'),
    (1, 'backend'),
    (1, 'programming'),

    -- Spring Boot 실전 가이드의 태그들
    (2, 'spring'),
    (2, 'backend'),
    (2, 'java'),
    (2, 'web'),

    -- JPA 프로그래밍의 태그들
    (3, 'jpa'),
    (3, 'java'),
    (3, 'database'),
    (3, 'backend'),

    -- 파이썬으로 배우는 알고리즘의 태그들
    (4, 'python'),
    (4, 'algorithm'),
    (4, 'programming'),

    -- 스프링 시큐리티 인 액션의 태그들
    (5, 'spring'),
    (5, 'security'),
    (5, 'backend'),
    (5, 'web');