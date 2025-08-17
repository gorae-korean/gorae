INSERT INTO textbook (id, public_id, created_at, updated_at, english_title, korean_title, level, english_subtitle,
                      korean_subtitle, category, article, thumbnail_url, read_time)
VALUES (1, 'b3a1e3c2-5f4d-4a9f-8c2d-1f7e9a6b2c3d', '2025-03-27 14:32:00+09:00', '2025-03-27 14:32:00+09:00',
        'Why Taxis Won''t Stop in Korea', '한국에서 손 들어도 택시 안 잡히는 이유',
        'BASIC', 'How Korean taxis actually work', '실제로 한국에서 택시 잡는 방법',
        'DAILY_LIFE', '한국에서는 길에서 손을 흔들어도 택시가 쉽게 멈추지 않아요. ' ||
                      '많은 사람들이 ‘카카오 택시’ 같은 앱을 이용해서 택시를 부르기 때문이에요. ' ||
                      '택시 운전사들도 길에서 손님을 찾기보다 앱을 통해 예약된 승객을 태우는 것을 선호해요.

그래서 한국에서는 길에서 택시를 잡는 것보다 앱을 이용하는 것이 더 빠르고 편리해요. 앱으로 택시를 부르면 원하는 장소에서 ' ||
                      '택시를 탈 수 있고, 목적지까지 바로 출발할 수 있어요. 자동차 번호와 운전사 정보를 미리 확인할 수 ' ||
                      '있어서 더 안전하기도 해요. 한국에서 택시를 타기 위해서는 길에서 손을 흔드는 것보다 앱을 이용하는 ' ||
                      '것이 좋은 방법이에요.',
        'https://static.goraekorean.site/image/textbook/b3a1e3c2-5f4d-4a9f-8c2d-1f7e9a6b2c3d/taxi.png', 3);

-- 샘플 태그 데이터 추가
INSERT INTO textbook_tag (textbook_id, tag)
VALUES (1, 'Transportation');