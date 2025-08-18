INSERT INTO textbook (id, public_id, created_at, updated_at, english_title, korean_title, level, english_subtitle,
                      korean_subtitle, category, article, thumbnail_url, read_time)
VALUES (1, 'b3a1e3c2-5f4d-4a9f-8c2d-1f7e9a6b2c3d', '2025-03-27 14:32:00+09:00', '2025-03-27 14:32:00+09:00',
        'Why Taxis Won''t Stop in Korea', '한국에서 손 들어도 택시 안 잡히는 이유',
        'BASIC', 'How Korean taxis actually work', '실제로 한국에서 택시 잡는 방법',
        'DAILY_LIFE', '한국에서는 **길**에서 **손**을 흔들어도 **택시**가 쉽게 멈추지 않아요. 많은 사람들이 ‘카카오 택시’ 같은 앱을 **이용**해서 택시를 부르기 때문이에요. 택시 **운전사**들도 길에서 손님을 찾기보다 앱을 통해 예약된 승객을 태우는 것을 선호해요.

그래서 한국에서는 길에서 택시를 잡는 것보다 앱을 **이용**하는 것이 더 빠르고 편리해요. 앱으로 택시를 부르면 원하는 장소에서 택시를 탈 수 있고, 목적지까지 바로 **출발**할 수 있어요. **자동차** 번호와 **운전**사 정보를 미리 확인할 수 있어서 더 **안전**하기도 해요. 한국에서 택시를 타기 위해서는 길에서 손을 흔드는 것보다 앱을 **이용**하는 것이 좋은 방법이에요.',
        'https://static.goraekorean.site/image/textbook/b3a1e3c2-5f4d-4a9f-8c2d-1f7e9a6b2c3d/taxi.png', 3),
       (2, 'e4f1c2d0-8b3a-4c2b-9f7e-1f0a3b6c8e2d', '2025-03-27 14:32:00+09:00', '2025-03-27 14:32:00+09:00',
        'One Bowl of Tteokguk = One Year Older?', '떡국 한 그릇 = 한 살 추가?!',
        'INTERMEDIATE', 'The Cultural Meaning of Eating Tteokguk', '설날에 떡국을 먹는 이유',
        'CULTURE_AND_SOCIETY', '**음력** 1월 1일은 한국의 ‘설날’이에요. 설날은 한 해를 새롭게 시작하는 중요한 명절이에요. 가족과 친척들이 함께 모여 맛있는 음식을 먹고 덕담을 주고받으면서 ****즐거운 시간을 보내요.

한국의 설날은 음력으로 계산하기 때문에 매년 날짜가 조금씩 달라져요. 이 시기에는 많은 사람들이 고향을 찾아 가족들과 함께 시간을 보내요. 설날에는 전통 놀이를 하며 명절 분위기를 즐기거나 한복을 입고 세배를 해요. 세배는 한국의 설날 아침에 어른들에게 큰절을 올리며 새해 인사를 드리는 전통 **예절**이에요.

이날 아침, 많은 한국 가정에서는 ‘떡국’을 **준비해요**. 떡국은 길고 얇게 썬 가래떡을 맑은 **국물**에 넣어 끓인 음식이에요. 이 떡국에는 아주 특별한 **문화적** 의미가 담겨 있어요.

**한국인**들은 “떡국을 먹어야 한 살 더 먹는다”는 **전통**적인 믿음을 가지고 있어요. 한국에서는 설날에 모두가 함께 떡국을 먹는 **풍습**이 있고 설날에 떡국을 먹으면, 나이를 한 살 더 먹는 것으로 여겨져요. 떡국을 한 그릇 먹을 때마다 나이를 한 살 더 먹는다고 생각해요. 그래서 어떤 사람들은 장난스럽게 “나이 먹기 싫으니까 떡국 안 먹을래요!”라고 말하기도 해요.

또한, 떡국에 들어가는 흰 가래떡은 밝은 새해의 출발을 **상징**하고, 길게 썬 모양은 오래 살길 기원한다는 의미를 담고 있어요. 그래서 떡국은 한국 사람들에게 단순한 음식이 아니라, 새해의 **소망**을 담은 상징적인 음식이라고 할 수 있어요.',
        'https://static.goraekorean.site/image/textbook/e4f1c2d0-8b3a-4c2b-9f7e-1f0a3b6c8e2d/떡국.png'
           , 5)
ON CONFLICT (id)
    DO UPDATE SET article = excluded.article;

-- 샘플 태그 데이터 추가
INSERT INTO textbook_tag (textbook_id, tag)
VALUES (1, 'Transportation'),
       (2, 'K-FOOD');