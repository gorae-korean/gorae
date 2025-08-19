TRUNCATE vocabulary;

INSERT INTO vocabulary(textbook_id, english_example_sentence1, korean_example_sentence1, english_example_sentence2,
                       korean_example_sentence2, english_vocabulary, korean_vocabulary, pronunciation)
VALUES (1,
        'Drive slowly.', '천천히 운전하다.',
        'Drive a car.', '자동차를 운전하다.',
        'drive', '운전하다', 'Unjeonhada'),
       (1,
        'The taxi driver is kind.', '택시 운전사가 친절하다.',
        'The bus driver honks the horn.', '버스 운전사가 경적을 울리다.',
        'driver', '운전사', 'Unjeonsa'),
       (1,
        'Use the airport bus.', '공항버스를 이용하다.',
        'Use the library.', '도서관을 이용하다.',
        'use', '이용하다', 'Iyonghada'),
       (1,
        'There are many cars on the street.', '거리에 자동차가 많다.',
        'The car drives fast.', '자동차가 빠르게 달린다.',
        'car', '자동차', 'Jadongcha'),
       (1,
        'The train departs on time.', '기차가 정시에 출발한다.',
        'The bus just left.', '방금 버스가 출발했다.',
        'depart', '출발하다', 'Chulbalhada'),
       (1,
        'There is a store across the street.', '길 건너에 가게가 있다.',
        'There is a lot of trash on the road.', '길에 쓰레기가 많다.',
        'street / road', '길', 'Gil'),
       (1, 'Wave hands.', '손을 흔들다.',
        'Wash hands.', '손을 씻다.',
        'hand', '손', 'Son'),
       (1, 'This place is a safe location.', '이곳은 안전한 장소이다.',
        'If you ride a car, wearing a seatbelt is safe.', '차를 타면 안전띠를 착용해야 안전하다.',
        'safe', '안전하다', 'Anjeonhada'),

       (2,
        'In the past, the lunar calendar was used more than the solar calendar.', '옛날에는 양력보다 음력이 더 많이 쓰였다.',
        'My dad celebrates his birthday according to the lunar calendar.', '우리 아빠는 음력으로 생일을 지낸다.',
        'lunar calendar', '음력', 'Eumnyeok')
        ,

       (2,
        'We must be polite when dealing with adults.', '어른을 대할 때에는 예절을 갖추어야 한다.',
        'Korea values etiquette.', '한국은 예절을 중요하게 생각한다.',
        'etiquette / manners', '예절', 'Yejeol')
        ,

       (2,
        'To prepare dinner.', '저녁밥을 준비하다.',
        'I prepared an umbrella because it was raining.', '비가 와서 우산을 준비하다.',
        'prepare', '준비하다', 'Junbihada')
        ,

       (2,
        'Because it''s cold, I want to eat a hot soup dish.', '추워서 따뜻한 국물 요리를 먹고 싶다.',
        'While eating, the soup splashed onto my clothes.', '밥을 먹다가 국물이 옷에 튀었다.',
        'soup / broth', '국물', 'Gungmul')
        ,

       (2,
        'Cultural characteristics vary by country.', '나라에 따라 문화적 특징은 다양하게 나타난다.',
        'King Sejong left various achievements in the field of culture.', '세종대왕은 문화적으로 다양한 업적을 남겼다.',
        'cultural', '문화적', 'Munhwajeok')
        ,

       (2,
        'Koreans like kimchi.', '한국인은 김치를 좋아한다.',
        'He came to study in Korea and takes classes with Korean friends.', '그는 한국으로 유학 와서 한국인 친구들과 함께 수업을 듣는다.',
        'Korean (person)', '한국인', 'Hangugin')
        ,

       (2,
        'Korea has various traditional foods.', '한국에는 다양한 전통 음식이 있다.',
        'She saw traditional Korean songs and dances at Gyeongbokgung Palace.', '그녀는 경복궁에서 한국의 전통 노래와 춤을 보았다.',
        'tradition', '전통', 'Jeontong')
        ,

       (2,
        'There are many unique customs in other countries.', '다른 나라에는 특이한 풍습이 많다.',
        'Koreans share the same experiences, language, history, and customs.', '한국인들은 동일한 경험, 언어, 역사, 풍습을 공유한다.',
        'custom / practice', '풍습', 'Pungseup')
        ,

       (2,
        'A dove symbolizes peace.', '비둘기는 평화를 상징한다.',
        'Red usually symbolizes passion.', '빨간색은 주로 열정을 상징한다.',
        'symbolize', '상징하다', 'Sangjinghada')
        ,

       (2,
        'My wish is for my whole family to be healthy.', '가족 모두가 건강한 것이 나의 소망이다.',
        'He studied hard and fulfilled his wish to become a doctor.', '그는 열심히 공부하여 의사가 되고 싶다는 소망을 이루었다.',
        'hope / wish', '소망', 'Somang'),

       (3,
        null, '그는 상대방의 기분을 생각하지 않고 말을 함부로 해요.',
        null, '상대방에게 궁금한 점을 질문하세요.',
        'opponent', '상대방', 'Sangdaebang'),

       (3,
        null, '어른들께 예의 바르게 행동해야 해요.',
        null, '처음 보는 사람에게는 예의를 지키세요.',
        'courtesy', '예의', 'Yeui'),

       (3,
        null, '선생님은 그 학생에게 이 책을 꼭 읽어보라고 권하셨어요.',
        null, '의사 선생님은 그에게 운동을 꾸준히 하라고 권하셨어요.',
        'recommend', '권하다', 'Gwonhada'),

       (3, null, '동생은 엄마 눈치를 살피며 조용히 공부했어요.',
        null, '내 친구는 눈치가 빨라서 나의 기분을 빨리 알아채요.',
        'sense', '눈치', 'Nunchi'),

       (3, null, '우리는 서로 양보하여 갈등 없이 문제를 해결해요.',
        null, '그는 조금도 양보하지 않고 자기 주장을 내세웠어요.',
        'concession', '양보', 'Yangbo'),

       (3, null, '스트레스는 건강에 직접적으로 나쁜 영향을 줄 수 있어요.',
        null, '저는 그 일과 직접적인 관련이 없어요.',
        'direct', '직접적', 'Jikjeopjeok'),

       (3, null, '그녀는 항상 남을 배려해요.',
        null, '늦은 시간까지 시끄럽게 하는 건 이웃을 배려하지 않는 행동이예요.',
        'consider', '배려하다', 'Baeryeohada'),

       (3, null, '시험이 끝난 학생들의 표정이 밝아요.',
        null, '아이는 선물이 마음에 들지 않아서 실망한 표정을 지었어요.',
        'expression', '표정', 'Pyojeong'),

       (3, null, '그녀는 항상 친절한 말투로 이야기해서 사람들이 좋아해요.',
        null, '발표할 때는 분명한 말투로 자신감 있게 말하는 것이 중요해요.',
        'tone', '말투', 'Maltu'),

       (3, null, '장소와 상황에 맞게 옷을 입어야 해요.',
        null, '사람들은 힘든 상황을 함께 이겨내기 위해 노력했어요.',
        'situation', '상황', 'Sanghwang'),

       (3, null, '그 친구와 나는 특별한 관계예요.',
        null, '한국에서는 사람 사이의 관계를 아주 중요하게 생각해요.',
        'relationship', '관계', 'Gwangye'),

       (3, null, '함께 게임을 하면 자연스럽게 친해질 수 있어요.',
        null, '그는 처음 만난 사람과도 자연스럽게 대화할 수 있어요.',
        'naturally', '자연스럽게', 'Jayeonseureopge'),

       (4, 'Selera saya dan dia mirip satu sama lain.', '그와 나는 취향이 서로 비슷하다.',
        'Saya dan ayah memiliki kepribadian yang mirip.', '아빠와 나는 성격이 비슷하다.',
        'Mirip / Serupa', '비슷하다', 'Biseutada'),

       (4, 'Hari ini saya makan bersama guru.', '오늘은 선생님과 함께 식사한다.',
        'Agar menjadi sehat, penting untuk makan secara teratur dan berolahraga.',
        '건강해지기 위해서는 규칙적으로 식사하는 것과 운동하는 것이 중요하다.',
        'Makan (secara formal)', '식사하다', 'Siksahada'),

       (4, 'Di Korea ada berbagai budaya tradisional.', '한국에는 다양한 전통문화가 있다.',
        'Di tempat ini, kita bisa makan makanan tradisional.', '이곳에서 전통 음식을 먹을 수 있다.',
        'Tradisi', '전통', 'Jeontong'),

       (4, 'Roti ini sangat renyah.', '이 빵은 바삭바삭하다.',
        'Dia suka jeon (gorengan Korea) yang renyah.', '그녀는 바삭한 전을 좋아한다.',
        'Renyah', '바삭하다', 'Basakada'),

       (4, 'Aroma jeon sangat gurih.', '전 냄새가 너무 고소하다.',
        'Dia suka rasa gurih dari susu.', '그는 우유의 고소한 맛을 좋아한다.',
        'Gurih / Lezat (berminyak dan enak)', '고소하다', 'Gosohada'),

       (4, 'Di sekolah, para siswa hidup dalam komunitas.', '학교에서 학생들은 공동체 생활을 한다.',
        'Anggota suatu komunitas biasanya memiliki nilai yang mirip.', '한 공동체의 구성원은 비슷한 가치관을 가진다.',
        'Komunitas', '공동체', 'Gongdongche'),

       (4, 'Adik saya punya kebiasaan menyilangkan kaki.', '동생은 다리를 꼬는 습관이 있다.',
        'Sulit untuk menghilangkan kebiasaan buruk.', '나쁜 습관을 고치는 것은 어렵다.',
        'Kebiasaan', '습관', 'Seupgwan'),

       (4, 'Karena internet tidak tersambung dengan baik, saya tidak bisa ikut kelas.', '인터넷이 잘 연결되지 않아서 수업에 참여할 수 없다.',
        'Jalan ini langsung terhubung ke restoran, jadi mudah untuk berpindah tempat.',
        '이 길은 식당과 바로 연결되어 있어서 이동하기 편하다.',
        'Terhubung / Tersambung', '연결되다', 'Yeongyeoldoeda'),

       (4, 'Begitu sampai di rumah, perasaanku menjadi tenang.', '집에 오니 기분이 안정된다.',
        'Saya merasa gugup, tetapi karena ada teman di samping, hati saya menjadi tenang.',
        '긴장했지만, 친구가 옆에 있으니 마음이 안정된다.',
        'Tenang / Stabil / Menjadi nyaman', '안정되다', 'Anjeongdoeda'),

       (4, 'Perjalanan kali ini adalah pengalaman yang sangat baru.', '이번 여행은 아주 새로운 경험이었다.',
        'Dia merasa sulit untuk bertemu orang baru.', '그녀는 새로운 사람을 만나는 것이 힘들다.',
        'Baru', '새로운', 'Saeroun');

