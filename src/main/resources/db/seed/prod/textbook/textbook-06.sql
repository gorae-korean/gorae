INSERT INTO gorae.public.textbook(id, read_time, created_at, updated_at, public_id, article, category, english_subtitle,
                                  english_title, korean_subtitle, korean_title, level)
VALUES (6, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'e7a2f3d0-0b5e-4e9a-8c9f-0d3f1b5f2a6c',
        '여러분은 ‘꾸안꾸’라는 말__을 아나요?__ ‘꾸안꾸’는 ‘꾸민 듯 안 꾸민 듯’의 줄임말로, 자연스러워 보이지만 사실은 **정성** 들여 스타일링한 모습을 뜻해요. 편한 옷을 입은 것 같지만, 헤어스타일, 액세서리, 메이크업에 **섬세하게** 신경 쓴 스타일이죠.

K-POP 아이돌들이 해외로 **출국**할 때마다 가장 **주목받는** 것 중 하나는 바로 공항 패션이에요. 요즘은 ‘꾸안꾸’ 스타일이 유행하면서 출국하는 아이돌들도 꾸안꾸 스타일로 옷을 입__곤 해요.__ 블랙핑크 제니나 아이유 같은 아이돌은 편한 셔츠나 청바지 하나만 입어도 멋져 보이는데, 자세히 보면 전체적인 스타일링에 조화와 **균형감**이 있어요.

또한, 한국의 연예인들은 상황에 따라 꾸밈 정도를 다르게 **조절해요.** 콘서트나 방송에서는 풀메이크업으로 무대 위를 빛내고, 일상이나 브이로그에서는 ‘꾸안꾸’로 편안하면서도 **세련된** 이미지를 보여줘요. 그래서 많은 K-POP 팬들이 아이돌의 비하인드 룩이나 SNS 사복 패션에도 관심을 가져요.

이런 뷰티와 패션 문화는 단순히 예뻐 보이기 위한 게 아니라, ‘나를 어떻게 표현할 것인가’에 대한 **고민**과 연결돼 있어요. 그래서 전 세계에서 K-POP과 K-DRAMA를 보며 한국의 스타일을 따라 하는 팬들이 점점 많아지고 있어요.',
        'OPEN_TOPIC_LESSONS', 'The meaning of ''kuanku''', 'What level of decorating today? What''s ''kuanku''!',
        '''꾸안꾸''의 의미', '오늘 꾸밈 몇 단계?, 꾸안꾸가 뭔데!', 'INTERMEDIATE');

INSERT INTO textbook_tag(textbook_id, tag)
VALUES (6, 'New word'),
       (6, 'Beauty');

INSERT INTO gorae.public.vocabulary(textbook_id, korean_example_sentence1, korean_example_sentence2, korean_vocabulary,
                                    pronunciation)
VALUES (6, '어머니가 정성 들여 만든 도시락을 먹으니 힘이 나는 것 같아요.', '그 선물에는 친구의 따뜻한 마음과 정성이 담겨 있어요.',
        '정성', 'Jeongseong'),
       (6, '그는 말투부터 행동까지 섬세하게 신경 써요.', '디자이너가 옷의 주름 하나까지 섬세하게 표현했어요.',
        '섬세하게', 'Seomsehage'),
       (6, '아이돌이 해외 공연을 위해 오늘 공항에서 출국했어요.', '출국하기 전에는 여권을 챙겼는지 꼭 확인해야 해요.',
        '출국하다', 'Chulgukada'),
       (6, '최근 한국 드라마는 해외에서도 많은 사람들이 주목하고 있어요.', '그의 변화된 스타일에 모두가 주목해요.',
        '주목하다', 'Jumokada'),
       (6, '식단을 짤 때는 영양 균형감을 고려해야 해요.', '방 인테리어를 보니까 색의 균형감이 잘 맞아요.',
        '균형감', 'Gyunhyeonggam'),
       (6, '무대 조명을 밝기 별로 조절해서 분위기를 바꿨어요.', '감정을 잘 조절하는 사람이 성숙한 사람이에요.',
        '조절하다', 'Jojeolhada'),
       (6, '오늘 입은 정장은 정말 세련된 느낌이에요.', '그 배우는 항상 세련된 이미지로 팬들에게 사랑 받아요.',
        '세련된', 'Seryeondoen'),
       (6, '그는 유학을 갈지 말지 오래 고민했어요.', '중요한 발표를 하기 전 말할 내용을 많이 고민했어요.',
        '고민', 'Gomin');

INSERT INTO gorae.public.key_expression(id, is_published, textbook_id, english_key_expression, korean_key_expression)
VALUES (10, true, 6, 'คุณรู้มั้ย~?', '_______ (을/를) 아나요?'),
       (11, true, 6, '~ฉันก็เคยทำนะ.', '_______ 곤 해요.');

INSERT INTO gorae.public.key_expression_example(key_expression_id, example)
VALUES (10, '이 노래**를 아나요?**'),
       (10, '이 식당**을 아나요?**'),
       (10, '지하철 타는 방법**을 아나요?**'),
       (10, '혹시 이 수학 문제 푸는 법**을 아나요?**'),
       (10, '그 가수가 해외에서도 인기가 있는 거 **아나요?**'),
       (11, '혼자 영화를 보**곤 해요.**'),
       (11, '저녁에 산책을 하**곤 해요.**'),
       (11, '스트레스를 받으면 단 걸 먹**곤 해요.**'),
       (11, '비 오늘 날엔 슬픈 노래를 듣**곤 해요.**'),
       (11, '시험 기간에는 새벽까지 공부하**곤 해요.**');

INSERT INTO gorae.public.key_expression_activity(id, key_expression_id, question)
VALUES (11, 10, '아래의 그림을 보고 **‘_______ (을/를) 아나요?’**를 활용하여 문장을 완성해보세요.'),
       (12, 10, '아래의 그림을 보고 **‘_____  곤 해요.’**을 활용하여 문장을 완성해보세요.');

INSERT INTO gorae.public.key_expression_activity_example(activity_id, file_name, text)
VALUES (11, 'restaurant', '식당'),
       (11, 'word', '단어'),
       (11, 'angry', '화나다'),
       (11, 'trend', '유행하다'),
       (12, 'bike', '자전거'),
       (12, 'walk', '산책하다'),
       (12, 'water', '물'),
       (12, 'cafe', '카페');

INSERT INTO gorae.public.lesson_question(textbook_id, korean_question)
VALUES (6, '여러분이 생각하는 ‘꾸안꾸 스타일’은 무엇이에요?'),
       (6, '평소에 좋아하는 스타일링이 있어요? 소개해주세요.'),
       (6, '요즘 유행하는 뷰티 스타일 중에 따라 해본 것이나 혹은 좋아하는 스타일이 있나요? 그 이유는 뭐예요?'),
       (6, '옷을 고를 때 가장 중요하게 생각하는 포인트는 뭐예요? (예: 색깔, 유행, 편함)'),
       (6, 'SNS나 드라마, 콘서트에서 본 한국 스타일 중에 인상 깊었던 것이 있어요? 있다면 말해주세요.'),
       (6, '출근을 하거나 등교 준비를 할 때 어떤 준비를 해요? (메이크업, 옷 고르기) 시간은 얼마나 걸려요?'),
       (6, '자신과 다른 스타일링을 좋아하는 친구가 있나요? 그 친구의 스타일링은 어떤가요?'),
       (6, '평소 스타일에 자주 변화를 주는 편이에요, 아니면 계속 하던 대로 하고 다니는 편이에요?'),
       (6, '외모와 스타일이 사람의 인상을 결정한다고 생각하나요? 그 이유는 뭐예요?');

INSERT INTO gorae.public.culture_tip(textbook_id, file_name, text, title)
VALUES (6, 'score', '- 한국 MZ 세대는 스타일에 따라 꾸밈 정도를 1부터 10까지 숫자로 표현하기도 해요.
- 1은 거의 안 꾸민 상태, 5는 꾸안꾸 정도, 10은 무대 의상처럼 완전 풀세팅 상태를 말해요.
- 상황에 맞게 꾸미는 센스가 중요한 한국 문화에서는 이렇게 뷰티나 패션에 관심 있는 사람들이 많아요.', '1부터 10까지 꾸밈 지수, 오늘 나는 몇 점?'),
       (6, 'airport', '- 아이돌이나 배우들이 해외 스케줄 갈 때 공항에 입고 나오는 옷은 그냥 여행복이 아니에요.
- ‘공항 패션’이란 이름으로 기사화되고 팬들에게도 큰 관심을 받죠.
- 편안해 보이지만 트렌디한 브랜드를 보여주거나, 팬서비스 차원에서 특별한 스타일을 보여주기도 해요.', '연예인의 공항 패션은 또 하나의 무대'),
       (6, 'ootd', '- OOTD는 (Outfit Of The Day) 오늘의 옷차림을 말해요.
- 한국 사람들은 매일 아침 기온을 확인하고 상황에 맞는 옷을 입어요.
- 한국에서는 다양한 자신의 오늘 옷차림을 공유하는 콘텐츠가 인기를 끌기도 해요.', 'OOTD가 뭐야?');
