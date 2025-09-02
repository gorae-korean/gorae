INSERT INTO gorae.public.textbook(id, read_time, created_at, updated_at, public_id, article, category, english_subtitle,
                                  english_title, korean_subtitle, korean_title, level)
VALUES (5, 4, '2025-08-17 11:49:00+09:00', '2025-08-17 11:49:00+09:00', '9f2c0b2c-3b8a-4f6e-9b5d-6d4fbf7a9c21',
        '주말이나 **휴일**이 되면 많은 사람들이 놀이 공원을 찾곤 해요. **화려하고** 멋진 건물과 **조형물**이 있어서 친구, 가족과 함께 사진 찍을 곳이 정말 많아요.

롤러코스터 같이 빠르게 움직이는 놀이기구를 타고 **짜릿함**을 느끼거나, 천천히 도는 관람차나 회전목마에서 시간을 보내는 것도 즐거움이에요. 서울 롯데월드에 있는 ‘자이로스윙’은 거대한 그네가 하늘 높이 올라가서 빙글빙글 도는 놀이기구에요. 그래서 무서운 놀이기구를 좋아하거나 스릴을 느끼고 싶어하는 사람들이 많이 타요. 용인 에버랜드에 있는 ‘티익스프레스’는 **경사**가 엄청 **가파른** 롤러코스터에요.

다양한 놀이기구를 타는 것 말고도 한국 놀이 공원에서는 다양한 것들을 할 수 있어요. 놀이 공원에서 화려한 행진 공연이나 불꽃놀이가 열리기도 하는데, 특히 밤에 보는 불꽃놀이는 오래도록 기억에 남을 만큼 아름다워요. 그리고 호랑이, 판다, 원숭이 같은 다양한 동물들도 **구경**할 수 있어요. 요즘 한국 학생들은 친구들과 놀이 공원에 가면 귀여운 머리띠를 쓰고 옷도 맞춰 입고 다니면서 사진을 찍어요. 또 떡볶이, 츄러스, 소세지 같은 맛있는 간식도 함께 먹어요.',
        'DAILY_LIFE', 'Representative activities at Korean amusement parks',
        'What can we do and play at Korean amusement parks?',
        '한국 놀이공원에서 자주 하는 활동', '한국 놀이 공원에서는 뭐 하고 놀까?', 'INTERMEDIATE');

INSERT INTO textbook_tag(textbook_id, tag)
VALUES (5, 'Leisure');

INSERT INTO vocabulary(textbook_id, korean_example_sentence1, korean_example_sentence2, korean_vocabulary, pronunciation)
VALUES (5,
        '긴 휴일이 생기면 사람들은 여행을 떠나거나 여유롭게 휴식을 취해요.', '휴일마다 놀이 공원이 붐벼요.',
        '휴일', 'Hyuil'),
       (5,
        '도심의 빌딩들은 밤이 되면 불빛으로 더욱 화려하게 빛나요.', '그의 화려한 경력 뒤에는 수많은 실패와 좌절이 있어요.',
        '화려하다', 'Hwaryeohada'),
       (5,
        '이 미술관에는 독창적인 조형물이 많아요.', '그 작가는 이번 전시에서 대형 조형물을 전시하여 큰 주목을 받았어요.',
        '조형물', 'Johyeongmul'),
       (5,
        '롤러코스터가 급하게 떨어질 때 온몸이 짜릿했어요.', '높은 곳에서 거리를 내려다볼 때 짜릿해요.',
        '짜릿하다', 'Jjarithada'),
       (5,
        '이 산책로는 완만한 경사로 이루어져 있어서 노인들도 무리 없이 오를 수 있어요.', '도로의 경사가 급한 구간에서는 차량의 속도를 반드시 줄여야 해요.',
        '경사', 'Gyeongsa'),
       (5,
        '산 정상에 다다를수록 길이 점점 가파라져 숨이 차올라요.', '계단이 너무 가파르다 보니 올라갈 때마다 힘들어요.',
        '가파르다', 'Gapareuda'),
       (5,
        '그는 가게에서 피규어를 구경하는 걸 좋아해요.', '그는 새로운 전시회를 구경하고 깊은 감동을 받았어요.',
        '구경하다', 'Gugyeonghada');