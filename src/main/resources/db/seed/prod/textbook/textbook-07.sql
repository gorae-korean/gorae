INSERT INTO textbook(id, read_time, created_at, updated_at, public_id, article, category, english_subtitle,
                     english_title,
                     korean_subtitle, korean_title, level)
VALUES (7, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '8f14e45f-ea8a-4a9d-bc3c-7a7b4d5f2d5e',
        '한국의 K-pop 산업에서 팬미팅과 콘서트, 그리고 팬콘서트는 팬과 아이돌이 직접 만날 수 있는 가장 중요한 **소통** 공간이에요. 팬미팅은 주로 가까운 거리에서 아이돌과 팬이 **교류**할 수 있도록 **기획**되며, 무대 공연보다는 토크, 게임, 포토타임 같은 활동이 중심이 돼요. 반면, 콘서트는 대규모 공연장에서 열리며, 화려한 무대 연출과 라이브 무대를 통해 팬들에게 큰 **감동**을 줘요.

최근에는 이 두 가지 성격을 합쳐 놓은 팬콘서트가 인기를 끌고 있어요. 팬콘서트는 콘서트의 웅장함과 팬미팅의 친밀함을 동시에 느낄 수 있는 행사로, 팬들에게 특별한 경험을 제공해요. 또한 한국에서는 팬들이 직접 준비한 슬로건 이벤트나, 특정 곡에서 휴대폰 플래시를 켜는 응원 문화가 자리 잡고 있어요. 이러한 **응원**은 팬들 사이의 강한 **연대감**을 만들어내며, 아이돌에게도 큰 힘이 돼요.

더 나아가 글로벌 팬들도 온라인 스트리밍이나 다국어 자막을 통해 실시간으로 참여할 수 있어, K-pop 팬덤은 국경을 넘어 더욱 **확장**되고 있어요. 결국 K-pop 팬미팅과 콘서트 문화는 단순한 **공연**을 넘어, 아티스트와 팬이 서로에게 에너지를 주고받는 독특한 문화로 발전하고 있어요.',
        'K_POP_AND_DRAMA', 'K-POP fan meeting culture', '"Have you ever been to a K-POP fan meeting?"',
        'K-POP 팬미팅 문화', '"너 K-POP 팬미팅 가본 적 있어?"', 'INTERMEDIATE');

INSERT INTO textbook_tag(textbook_id, tag)
VALUES (7, 'K-POP'),
       (7, 'Leisure');

INSERT INTO vocabulary(textbook_id, korean_example_sentence1, korean_example_sentence2, korean_vocabulary,
                       pronunciation)
VALUES (7, '그는 다양한 세대와 소통해요.', '함께 일할 때는 서로의 의견을 존중하며 소통하는 태도가 필요해요.',
        '소통하다', 'Sotonghada'),
       (7, '그녀는 다양한 사람들과 정보를 교류해요.', '이번 축제에서 외국인 학생들과 문화를 교류할 수 있어요.',
        '교류하다', 'Gyoryuhada'),
       (7, '저는 청소년을 위한 전시회를 기획하고 있어요.', '회사에서 새로운 행사를 기획하기 위해 회의를 열었어요.',
        '기획하다', 'Gihoekada'),
       (7, '친구가 시험을 잘 치를 수 있도록 열심히 응원해요.', '관객들의 응원 덕분에 선수들이 힘을 낼 수 있어요.',
        '응원', 'Eungwon'),
       (7, '우리는 함께 어려움을 극복하며 강한 연대감을 느꼈어요.', '봉사활동을 하며 이웃과의 연대감이 더욱 강해졌어요.',
        '연대감', 'Yeondaegam'),
       (7, '회사를 해외 시장으로 확장한다는 목표를 세웠어요.', '그곳은 작은 가게였지만 매출이 늘어나면서 매장을 하나 더 확장했어요.',
        '확장하다', 'Hwakjanghada'),
       (7, '어제 본 뮤지컬 공연은 정말 감동적이었어요.', '아이돌 그룹의 콘서트 공연은 몇 분만에 매진돼요.',
        '공연', 'Gongyeon');

INSERT INTO key_expression(id, is_published, textbook_id, english_key_expression, korean_key_expression)
VALUES (12, true, 7, '_________. ในทางกลับกัน ___________.', '_________. 반면 ___________.');

INSERT INTO key_expression_example(key_expression_id, example)
VALUES (12, '그는 수학을 잘해요. **반면** 영어는 못 해요.'),
       (12, '그녀는 성격이 활발헤요. **반면** 동생은 조용해요.'),
       (12, '인터넷 쇼핑은 편리해요. **반면** 직접 입어볼 수 없어요.'),
       (12, '한국은 사계절이 있어요. **반면** 태국은 1년 내내 더워요.'),
       (12, '이 식당은 가격이 저렴해요. **반면** 서비스는 조금 부족해요.');

INSERT INTO key_expression_activity(id, key_expression_id, question)
VALUES (13, 12, '**‘________.  반면 __________.’** 를 활용하여 질문에 답해보세요.');

INSERT INTO key_expression_activity_example(activity_id, file_name, text)
VALUES (13, 'coffee-tea', '커피, 차'),
       (13, 'winter-summer', '겨울, 여름'),
       (13, 'sing-dance', '노래, 춤'),
       (13, 'spicy', '맵다');

INSERT INTO lesson_question(textbook_id, korean_question)
VALUES (7, '좋아하는 가수나 배우가 있나요? 그 사람이 좋은 이유는 뭐예요?'),
       (7, '좋아하는 연예인의 콘서트에 가본 적 있나요? 가장 인상 깊었던 콘서트는 무엇이었나요?'),
       (7, '좋아하는 가수나 배우를 실제로 본다면 어떤 말을 하고 싶어요?'),
       (7, '좋아하는 한국 가수나 배우가 있나요? 소개해 주세요.'),
       (7, '팬사인회에서 좋아하는 가수나 배우에게 사인을 받는다면 어디에 받고 싶어요? (앨범, 티셔츠, 포스터..)'),
       (7, '여러분의 나라에서 가장 인기가 많은 kpop 가수는 누구인가요? 왜 인기가 많다고 생각해요?'),
       (7, '만약 팬미팅에서 아이돌이나 배우에게 한 가지 질문을 할 수 있다면, 무엇을 물어보고 싶나요?'),
       (7, '팬미팅에서 좋아하는 연예인에게 주고 싶은 선물이 있다면 무엇인가요? 그 이유는 뭐예요?');

INSERT INTO culture_tip(textbook_id, file_name, text, title)
VALUES (7, 'stick', '- K-pop 팬들은 자신의 아이돌 그룹만의 응원봉을 갖고 있어요.
- 응원봉에는 그룹 로고나 색상이 반영되어, 멀리서도 어떤 팬인지 바로 알 수 있죠.
- 최근에는 블루투스 기능이 있어서 공연장에서 무대와 연결되면 자동으로 색이 바뀌며 전체 공연장 분위기를 연출할 수 있이요!!',
        '빨강, 노랑, 보라 다양한 색깔의 응원봉'),
       (7, 'together', '- 한국 콘서트에서는 수천 명의 팬들이 노래를 끝까지 따라 부르는 ‘떼창’ 문화가 있어요.
- 발라드 곡이나 히트곡일 때 특히 많은 팬들이 가수와 함께 노래를 부르며 공연을 즐겨요.
- 이 순간은 가수에게 큰 감동을 주고 팬들에게도 잊지 못할 추억이 돼요. 특히 한국으로 공연을 오는 해외 가수들은 이 떼창 문화를 잊지 못할 추억으로 언급하는 경우가 많아요.',
        '떼창 문화가 뭐야?'),
       (7, 'talk', '- 한국에서는 SNS 뿐만 아니라 다양한 채널을 통해서 좋아하는 아티스트와 대화를 할 수 있어요.
- 버블(Bubble), 위버스(Weverse), V-Live 등을 통해 팬과 가수는 메시지를 주고 받거나 실시간 방송을 하면서 대화할 수 있어요.
- 이렇게 다양한 팬 서비스를 통해 K-pop 스타들은 한국 팬들 뿐만 아니라 해외 팬들과도 활발히 소통할 수 있어요.',
        '나만의 아티스트와 얘기를 할 수 있다고?');
