INSERT INTO play_a_role_scene(id, textbook_id, image_url, title)
VALUES (1, 2,
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=384,h=216,f=auto,dpr=1,fit=contain/f1750904161391x377736911786930700/1.%20%EC%83%88%ED%95%B4%20%EC%B2%AB%EB%82%A0%20%EC%95%84%EC%B9%A8%2C%20%EB%96%A1%EA%B5%AD%EC%9D%84%20%EB%A8%B9%EB%8A%94%20%EC%83%81%ED%99%A9.png',
        '새해 첫날 아침, 떡국을 먹는 상황');

INSERT INTO play_a_role_scene_character(scene_id, english_role, image_url, korean_role, name)
VALUES (1, 'International Student',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856595482x694675882745916800/profile_rina.png',
        '외국인 유학생', '리나'),
       (1, 'Korean Friend',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856468529x729589291622714200/profile_minsu.png',
        '한국 친구', '민수');

INSERT INTO play_a_role_script(scene_id, english_line, english_role, image_url, korean_line, korean_role, name)
VALUES (1, 'blank', 'International Student',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856595482x694675882745916800/profile_rina.png',
        '새해 복 많이 받으세요~ 이게 떡국인가요?', '외국인 유학생', '리나'),
       (1, 'blank', 'Korean Friend',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856468529x729589291622714200/profile_minsu.png',
        '맞아요! 설날 아침에는 꼭 떡국을 먹어요. 한 그릇 먹어봐요~', '한국 친구', '민수'),
       (1, 'blank', 'International Student',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856595482x694675882745916800/profile_rina.png',
        '근데 "떡국 먹으면 한 살 더 먹는다"는 말이 있는데 진짜인가요?', '외국인 유학생', '리나'),
       (1, 'blank', 'Korean Friend',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856468529x729589291622714200/profile_minsu.png',
        '네. 옛날부터 이어져 오는 한국의 전통이에요.', '한국 친구', '민수'),
       (1, 'blank', 'International Student',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856595482x694675882745916800/profile_rina.png',
        '그럼 저는 안 먹을래요. 나이를 한 살 더 먹고 싶지 않아요!', '외국인 유학생', '리나'),
       (1, 'blank', 'Korean Friend',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856468529x729589291622714200/profile_minsu.png',
        '떡국을 먹는 건 나이를 한 살 더 먹는다는 의미도 있지만, 새해를 시작한다는 의미도 있어요. 또 떡국은  함께 먹는 가족들 간 ''정''이 담긴 음식이에요.', '한국 친구', '민수'),
       (1, 'blank', 'International Student',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856595482x694675882745916800/profile_rina.png',
        '''정''이 뭔가요?', '외국인 유학생', '리나'),
       (1, 'blank', 'Korean Friend',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856468529x729589291622714200/profile_minsu.png',
        '''정''은 사람 사이의 따뜻한 마음을 뜻해요. 오랜 시간 함께 지내며 자연스럽게 생기는 따뜻한 마음, 감정을 ‘정’이라고 해요. 설날에는 가족, 친구들과 함께 떡국을 먹으며 이런 정을 나누기도 해요.',
        '한국 친구', '민수'),
       (1, 'blank', 'International Student',
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=64,h=64,f=auto,dpr=1,fit=contain/f1750856595482x694675882745916800/profile_rina.png',
        '그렇군요. 한국의 설날 문화는 정말 따뜻하고 의미 있는 것 같아요. 그럼 저도 떡국 한 그릇 먹어볼게요!', '외국인 유학생', '리나');

INSERT INTO lets_talk_about(id, textbook_id, image_url, title)
VALUES (1, 2,
        'https://c1110c23e19e66dd3233a28ac4cc1966.cdn.bubble.io/cdn-cgi/image/w=384,h=216,f=auto,dpr=1,fit=contain/f1750920082409x990019720404747000/2.%20%EB%AA%85%EC%A0%88%EC%97%90%20%EA%B7%BC%ED%99%A9%20%EB%82%98%EB%88%84%EA%B8%B0.png',
        '새해가 되어 친구들이 오랜만에 만나 새해 인사도 하고 서로의 근황을 나눕니다. 명절에 무엇을 했는지, 새해 계획은 무엇인지 이야기합니다.');

INSERT INTO lets_talk_about_example(lets_talk_about_id, discussion_point)
VALUES (1, '새해 인사 나누기:  ex) "새해 복 많이 받아!", "올해는 좋은 일만 가득하길 바라!"'),
       (1, '명절 동안 한 일 이야기하기: 가족과 보낸 시간, 고향 방문, ...'),
       (1, '새해 계획과 목표 이야기하기: 운동, 공부, 여행, ...  ex) "올해는 한국어 진짜 열심히 공부할 거야", "나는 올해 배낭 여행 갈 거야."');

