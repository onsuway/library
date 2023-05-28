/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : library_online

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 28/05/2023 23:06:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `borrowing_nums` int NOT NULL DEFAULT 0,
  `credit` int NOT NULL DEFAULT 3,
  `del_flag` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_name`(`username` ASC) USING BTREE,
  UNIQUE INDEX `unique_email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, '123@qq.com', 'admin', '$2a$10$4QmK5yh0w5VMsDV2bsIVFuY/NVA1e.oMG6IrFWhDE2VmWUShbRKpe', 'admin', 0, 3, 0);
INSERT INTO `account` VALUES (8, '2@qq.com', 'student', '$2a$10$5OjHvGbDnvOW0T0/P/TsSuoH4rV1snJ6gec7nmTjuA6kUhTS4w7mG', 'student', 3, 3, 0);
INSERT INTO `account` VALUES (9, '1111111@qq.com', 'teacher', '$2a$10$5OjHvGbDnvOW0T0/P/TsSuoH4rV1snJ6gec7nmTjuA6kUhTS4w7mG', 'teacher', 3, 3, 0);
INSERT INTO `account` VALUES (11, '10166@qq.com', 'susu', '$2a$10$pPEVskXmRXdsfCDaAQCVeOysrxAzi9KS5cEKlp9oiafkoe7P3PDR6', 'teacher', 0, 3, 0);
INSERT INTO `account` VALUES (12, '101694@qq.com', 'teach111', '$2a$10$hD9Ei0lFOT2BqeC7a..RIOhFVXzYzTLP3HuPDXi8fe27/0NbyBHO.', 'teacher', 0, 3, 0);

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `desc` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type_id` int NULL DEFAULT NULL,
  `nums` int NOT NULL,
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_day` date NOT NULL,
  PRIMARY KEY (`bid`) USING BTREE,
  INDEX `f_type`(`type_id` ASC) USING BTREE,
  CONSTRAINT `f_type` FOREIGN KEY (`type_id`) REFERENCES `type` (`type_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (61, '红高粱', '莫言', '《红高粱》通过“我”的叙述，描写了抗日战争期间，“我”的祖先在高密东北乡轰轰烈烈、英勇悲壮的人生故事。 [5] 故事的主线是“我”的爷爷和奶奶，故事发生的主要地点是高密东北乡。“我”的爷爷叫余占鳌，奶奶叫戴凤莲，至于高密东北乡这个地方作者对其做过如下的描述：“最美丽最丑陋、最超脱最世俗同时最圣洁最龌龊，也是最英雄好汉最王八蛋以及最能喝酒和最能爱的地方，这就是高密东北乡。”', 18, 0, 'https://image.itbaima.net/images/90/image-20230528213399491.jpeg', '2023-05-01');
INSERT INTO `book` VALUES (62, '白鹿原', '陈忠实', '《白鹿原》是作家陈忠实创作的长篇小说，由陈忠实历时六年创作完成，首次出版于1993年。 该小说以陕西关中地区白鹿原上白鹿村为缩影，通过讲述白姓和鹿姓两大家族祖孙三代的恩怨纷争，表现了从清朝末年到二十世纪七八十年代长达半个多世纪的历史变化。 1998年，该小说获得中国第四届茅盾文学奖。', 18, 49, 'https://image.itbaima.net/images/90/image-20230528216999537.jpeg', '2023-05-01');
INSERT INTO `book` VALUES (64, '非暴力沟通', '马歇尔·卢森堡', '非暴力沟通是一种工具，通过关注所观察、感受、需要、请求的内容，引导参与者重新组织表达自己、听取他人、解决冲突的方式。 这种工具能引导我们建立富有同情心的人际关系，大家的需求都受到重视并通过合作得以满足。 借助非暴力沟通，解决冲突变得更容易，简单的冲突得以避免，困难的冲突得以更有效地解决。', 23, 70, 'https://image.itbaima.net/images/90/image-20230528218894878.jpeg', '2023-05-04');
INSERT INTO `book` VALUES (66, '人类群星闪耀时', '斯蒂芬·茨威格', '本书是著名作家斯蒂芬·茨威格的传记名作，收入茨威格的历史特写14篇，向我们展现了14个决定世界历史的瞬间：千年帝国拜占庭的陷落、巴尔沃亚眺望水天一色的太平洋、亨德尔奇迹的精神复活、老年歌德热恋的悲歌、滑铁卢决定胜负的一分钟、《马赛曲》神佑般的创作、南极探险的磨难、西塞罗的振臂一呼，以及威尔逊的梦想与失败。当强烈的个人意志与历史宿命碰撞之际，火花闪烁，那样的时刻从此照耀着人类文明的天空。', 22, 45, 'https://image.itbaima.net/images/90/image-20230528212723746.jpeg', '2023-05-06');
INSERT INTO `book` VALUES (67, '漫步华尔街', '彼得·林奇', '《漫步华尔街》是一本传世之作，自从问世以来一直颇受好评。该书不是投机指南，也不保证一夕致富，本书作者的写作目的在于帮助投资人了解投资理论与实务，以培养健康的投资观念。此外，他也打破只有专家才能走进华尔街的迷信，在书中铺陈出一幅地图，告诉个人投资者如何在各种令人目眩神迷的投资工具中，运用规划，打败华尔街专家。该书集理论与实践于一身，通过作者别具匠心的构思，将二者融会贯通为一个无懈可击的整体。', 3, 8, 'https://image.itbaima.net/images/90/image-20230528217889346.jpeg', '2023-05-08');
INSERT INTO `book` VALUES (68, '人生海海', '麦家', '小说的主人公是一个身上带着很多谜团的上校，他是全村最出奇古怪的人，他当过国民党军队的上校，是革命群众要斗争的对象，但村民一边斗争他，一边又巴结讨好他，家里出什么事都去找他拿主意；村民都说他是太监，可小孩子经常偷看他那个地方，好像还是满当当的，有模有样的； 他向来不出工，不干农活，天天空在家里看报纸，嗑瓜子，可日子过得比谁家都舒坦，还像养孩子一样养着一对猫。上校所讲述的故事、别人所经历与他相关的故事、别人讲述的他的故事相互交缠，勾画出上校在时代中穿行缠斗的一生。', 4, 98, 'https://image.itbaima.net/images/90/image-20230528211109896.jpeg', '2023-05-01');
INSERT INTO `book` VALUES (69, '百年孤独', '加夫列尔·加西亚·马尔克斯', '《百年孤独》，是哥伦比亚作家加西亚·马尔克斯创作的长篇小说，是其代表作，也是拉丁美洲魔幻现实主义文学的代表作，被誉为“再现拉丁美洲历史社会图景的鸿篇巨著”。 作品描写了布恩迪亚家族七代人的传奇故事，以及加勒比海沿岸小镇马孔多的百年兴衰，反映了拉丁美洲一个世纪以来风云变幻的历史。', 2, 49, 'https://image.itbaima.net/images/90/image-20230528215199675.jpeg', '2023-04-06');
INSERT INTO `book` VALUES (70, '三体1地球往事', '刘慈欣', '《三体1：地球往事》讲的是在文化大革命如火如荼进行的同时，军方探寻外星文明的绝秘计划“红岸工程”取得了突破性进展。但在按下发射键的那一刻，历经劫难的叶文洁没有意识到，她彻底改变了人类的命运。地球文明向宇宙发出的第一声啼鸣，以太阳为中心，以光速向宇宙深处飞驰……', 20, 5, 'https://image.itbaima.net/images/90/image-20230528215097909.jpeg', '2023-04-05');
INSERT INTO `book` VALUES (71, '围城', '钱钟书', '《围城》是钱锺书唯一的一部长篇小说（另一部小说《百合心》未创作结束即被遗失，从此作者便再没有续写），开始创作于1944年，1946年完稿，1947年由上海晨光出版公司印制发行。该书是钱锺书“锱铢积累”而成的，小说“从他熟悉的时代，熟悉的地方，熟悉的社会阶层取材。但组成故事的人物和情节全属虚构。尽管某几个角色稍有真人的影子，事情都子虚乌有；某些情节略具真实，人物却全是捏造的。”而男主角方鸿渐则是取材于两个亲戚：“一个志大才疏，常满腹牢骚；一个狂妄自大，爱自吹自唱。两人都读过《围城》，但是谁也没自认为方鸿渐，因为他们从未有方鸿渐的经历。”', 18, 59, 'https://image.itbaima.net/images/90/image-20230528217362314.jpeg', '2023-05-03');
INSERT INTO `book` VALUES (72, '红楼梦', '曹雪芹', '《红楼梦》，原名《石头记》，中国古代章回体长篇小说，中国古典四大名著之一。其通行本共120回，一般认为前80回是清代作家曹雪芹所著，后40回作者为无名氏，由高鹗，程伟元整理。小说以贾、史、王、薛四大家族的兴衰为背景，以富贵公子贾宝玉为视角，以贾宝玉与林黛玉、薛宝钗的爱情婚姻悲剧为主线，描绘了一些闺阁佳人的人生百态，展现了真正的人性美和悲剧美，是一部从各个角度展现女性美以及中国古代社会百态的史诗性著作。', 1, 65, 'https://image.itbaima.net/images/90/image-20230528213985017.jpeg', '2023-05-12');
INSERT INTO `book` VALUES (73, '西游记', '吴承恩', '该小说主要讲述了孙悟空出世，跟随菩提祖师学艺及大闹天宫后，遇见了唐僧、猪八戒、沙僧和白龙马，西行取经，一路上历经艰险，降妖除魔，经历了九九八十一难，终于到达西天见到如来佛祖，最终五圣成真的故事。该小说以“玄奘取经”这一历史事件为蓝本，经作者的艺术加工，深刻地描绘出明代百姓的社会生活状况。', 1, 80, 'https://image.itbaima.net/images/90/image-20230528211702928.jpeg', '2023-05-18');
INSERT INTO `book` VALUES (74, '史记', '司马迁', '《史记》是我国第一部纪传体通史，共纪传125篇，自黄帝到汉武帝时代的历史，包括政治、军事、经济、文化等方面的内容。《史记》分本纪、表、书、世家、列传五部分。其中本纪和列传是主体。它以历史上的帝王等政治中心人物为史书编撰的主线，各种体例分工明确，其中，“本纪”、“世家”、“列传”三部分，占全书的大部分篇幅，都是以写人物为中心来记载历史的，由此，司马迁创立了史书新体例“纪传体”。', 22, 49, 'https://image.itbaima.net/images/90/image-20230528216775569.jpeg', '2023-05-16');
INSERT INTO `book` VALUES (82, '解密', '麦家', '《解密》为我们讲述了一个奇人的故事。这个人的天才与愚笨相映成趣。破译密码，是作家设置的考验奇人的奇境。在这个奇境中，我们看到了人的尊严与光荣，人的脆弱与不幸，当然也能看到我们自己的倒影。', 4, 3, 'https://image.itbaima.net/images/90/image-20230528211428376.jpeg', '2023-05-15');
INSERT INTO `book` VALUES (84, '风声', '麦家', '《风声》讲述了地下工作者老鬼的故事。老鬼破译电报的能力超强，打入了日伪情报组织内部，面临身份暴露的危险时，他机智地与日伪和国民党军统特务周旋，制造种种假象迷惑敌人，使得组织内部陷入混乱，搞不清谁是真正的“老鬼”。但因条件所限，最后关头“老鬼”不得不牺牲生命，设法将情报成功传递出去。', 4, 60, 'https://image.itbaima.net/images/90/image-20230528216408411.jpeg', '2023-05-14');
INSERT INTO `book` VALUES (85, '小王子', '安托万·德·圣埃克絮佩里', '《小王子》是法国作家安托万·德·圣埃克苏佩里于1942年写成的著名儿童文学短篇小说。本书的主人公是来自外星球的小王子。书中以一位飞行员作为故事叙述者，讲述了小王子从自己星球出发前往地球的过程中，所经历的各种历险。作者以小王子的孩子式的眼光，透视出成人的空虚、盲目，愚妄和死板教条，用浅显天真的语言写出了人类的孤独寂寞、没有根基随风流浪的命运。同时，也表达出作者对金钱关系的批判，对真善美的讴歌。', 17, 5, 'https://image.itbaima.net/images/90/image-20230528217809213.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (86, '乌合之众', '古斯塔夫·勒庞', '《乌合之众：大众心理研究》是一本研究大众心理学的著作。 在书中，勒庞阐述了群体以及群体心理的特征，指出了当个人是一个孤立的个体时，他有着自己鲜明的个性化特征，而当这个人融入了群体后，他的所有个性都会被这个群体所淹没，他的思想立刻就会被群体的思想所取代。 而当一个群体存在时，他就有着情绪化、无异议、低智商等特征。', 23, 5, 'https://image.itbaima.net/images/90/image-20230528215021353.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (87, '月亮和六便士', '威廉·萨默塞特·毛姆', '《月亮和六便士》 是威廉·萨默塞特·毛姆的一本小说，以第一人称通过对中心人物查尔斯·思特里克兰德心灵的描述，展现一个中年英国股票经纪人如何突然扔下妻子和孩子去追寻成为艺术家的梦想。 故事一说是取材于画家保罗·高更的生活。', 2, 2, 'https://image.itbaima.net/images/90/image-2023052821159644.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (88, '我的奋斗', '阿道夫·希特勒', '该书系统地阐述了阿道夫·希特勒心目中的“理想”：“创建第三帝国”。全书充满了民族主义狂热和对马克思主义、犹太人的仇恨。他认为日耳曼人是耶和华选定的“主宰民族”，宣称“新帝国必须再一次沿着古代条顿武士的道路进军，用德国的剑为德国的犁取得土地，为德国人民取得每天的面包”，夺取新的“生存空间”。该书影响着第二次世界大战前以及第二次世界大战时所有的德国年轻人，是纳粹主义的理论的最集中的体现，也是研究希特勒法西斯思想的必读之物。', 24, 0, 'https://image.itbaima.net/images/90/image-20230528219776223.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (89, '三体3死神永生', '刘慈欣', '作为《三体》系列第三部作品，本书以地球人类程心的一生为主线，对继第二部《黑暗森林》中威慑建立后的人类历史进行了续写，进一步揭示了宇宙黑暗森林状态的真正面目。在本篇的故事中，太阳系最终没有逃过灭顶之灾；但程心和来自星舰文明的关一帆，为太阳系的人类文明保留了一颗火种。', 20, 5, 'https://image.itbaima.net/images/90/image-20230528213718562.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (90, '三体2黑暗森林', '刘慈欣', '《三体2：黑暗森林》是刘慈欣创作的长篇科幻小说，是“地球往事三部曲系列”的第二部作品，于2008年5月首次出版，该书总共有三个章节：“上部 面壁者”、“中部 咒语”和“下部 黑暗森林”。该书主要讲述庞大的三体舰队杀气腾腾直扑太阳系，尖端科技被锁死的地球人面对前所未有的危局，唯一的突破口在于三体人透明的思维。 于是，人类制订了神秘莫测的“面壁计划”，秘密展开对三体人的反击。该小说通过展示异常黑暗的宇宙图景，在更加宏观的角度展示了“适者生存”宇宙进化理论，直刺长期以来的人类中心主义之上的自恋情绪。', 20, 0, 'https://image.itbaima.net/images/90/image-20230528217565947.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (91, '杀死一只知更鸟', '哈珀·李', '该小说讲述一个名叫汤姆·鲁滨逊的年轻人，被人诬告犯了强奸罪后，只是因为是一个黑人，辩护律师阿蒂克斯·芬奇尽管握有汤姆不是强奸犯的证据，都无法阻止陪审团给出汤姆有罪的结论。此一妄加之罪，导致汤姆死于乱枪之下。虽然故事题材涉及种族不平等与强暴等严肃议题，其文风仍温暖风趣。小说以第一人称著成，叙述者的父亲阿提克斯·芬奇在书中为道德端正的角色，亦是正直律师的典范。1961年，该书获当年度普利策奖，被翻译成四十多种语言，在世界范围内售出超过三千万册，同年被改编成同名电影。', 2, 1, 'https://image.itbaima.net/images/90/image-20230528214661332.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (92, '人类简史', '尤瓦尔·赫拉利', '《人类简史：从动物到上帝》是以色列新锐历史学家的一部重磅作品。从十万年前有现代人类迹象开始到21世纪资本、科技交织的人类发展史。十万年前，地球上至少有六个人种，为何今天却只剩下了我们自己？我们曾经只是非洲角落一个毫不起眼的族群，对地球上生态的影响力和萤火虫、猩猩或者水母相差无几。为何我们能登上生物链的顶端，最终成为地球的主宰？', 22, 3, 'https://image.itbaima.net/images/90/image-2023052821495672.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (93, '活着', '余华', '《活着》是中国当代作家余华创作的长篇小说，首次发表于《收获》1992年第6期。 《活着》讲述了在大时代背景下，随着内战、三反五反、大跃进、“文化大革命”等社会变革，徐福贵的人生和家庭不断经受着苦难，到了最后所有亲人都先后离他而去，仅剩下年老的他和一头老牛相依为命。', 18, 1, 'https://image.itbaima.net/images/90/image-20230528212899706.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (94, '恶意', '东野圭吾', '《恶意》讲述的是作家在出国前一晚被杀，警方很快锁定了凶手，但事情的真相绝非如此 。 其主体部分表现为第一人称不定内聚焦型叙述，并充分体现了内聚焦叙述的特点 。 故事在结尾戛然而止，使得读者不能迅速从叙事语篇的指示中心中抽离出来，这种抽离的缺失往往会更加引人深思，更能体会小说的主题，即人性的恶意 。', 4, 3, 'https://image.itbaima.net/images/90/image-20230528211742713.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (95, '1984', '乔治·奥威尔', '1984年的世界被三个超级大国所瓜分——大洋国、欧亚国和东亚国，三个国家之间的战争不断，国家内部社会结构被彻底打破，均实行高度集权统治，以改变历史、改变语言（如“新话”—Newspeak）、打破家庭等极端手段钳制人们的思想和本能，以具有监视与监听功能的“电幕”（telescreen）控制人们的行为，以对领袖的个人崇拜和对国内外敌人的仇恨维持社会的运转。', 20, 2, 'https://image.itbaima.net/images/90/image-20230528212990124.jpeg', '2023-05-19');
INSERT INTO `book` VALUES (96, '白夜行', '东野圭吾', '《白夜行》是日本作家东野圭吾创作的长篇小说，也是其代表作。该小说于1997年1月至1999年1月间连载于期刊，单行本1999年8月在日本发行。故事围绕着一对有着不同寻常情愫的小学生展开。1973年，大阪的一栋废弃建筑内发现了一具男尸，此后19年，嫌疑人之女雪穗与被害者之子桐原亮司走上截然不同的人生道路，一个跻身上流社会，一个却在底层游走，而他们身边的人，却接二连三地离奇死去，警察经过19年的艰苦追踪，终于使真相大白。', 4, 2, 'https://image.itbaima.net/images/90/image-20230528218061693.jpeg', '2023-05-19');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `borrow_id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NOT NULL,
  `account_id` int NOT NULL,
  `borrow_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `due_time` timestamp NULL DEFAULT NULL,
  `actual_time` timestamp NULL DEFAULT NULL,
  `is_extended` int NOT NULL DEFAULT 0 COMMENT '这次借阅是否延长过了',
  `del_flag` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`borrow_id`) USING BTREE,
  INDEX `f_bid`(`book_id` ASC) USING BTREE,
  INDEX `f_uid`(`account_id` ASC) USING BTREE,
  CONSTRAINT `f_bid` FOREIGN KEY (`book_id`) REFERENCES `book` (`bid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `f_uid` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (11, 67, 9, '2023-05-20 11:18:10', '2023-05-19 23:52:56', '2023-05-20 11:18:10', 0, 1);
INSERT INTO `borrow` VALUES (13, 66, 9, '2023-05-20 11:23:01', '2023-05-13 23:53:43', '2023-05-20 11:23:01', 0, 1);
INSERT INTO `borrow` VALUES (14, 71, 9, '2023-05-20 11:19:38', '2023-05-25 22:53:58', '2023-05-20 11:19:38', 0, 1);
INSERT INTO `borrow` VALUES (15, 70, 8, '2023-05-19 22:56:18', '2023-05-21 23:54:22', '2023-05-14 17:18:49', 0, 1);
INSERT INTO `borrow` VALUES (23, 61, 8, '2023-05-19 23:40:12', '2023-05-28 23:56:30', '2023-05-19 23:40:12', 0, 1);
INSERT INTO `borrow` VALUES (30, 67, 8, '2023-05-20 11:18:18', '2023-05-23 00:09:02', '2023-05-20 11:18:18', 0, 1);
INSERT INTO `borrow` VALUES (31, 89, 8, '2023-05-20 10:14:46', '2023-05-24 00:09:17', '2023-05-20 10:14:46', 0, 1);
INSERT INTO `borrow` VALUES (32, 67, 8, '2023-05-20 11:22:56', '2023-05-24 10:15:56', '2023-05-20 11:22:56', 0, 1);
INSERT INTO `borrow` VALUES (37, 61, 8, '2023-05-20 11:19:53', '2023-05-21 11:10:14', '2023-05-20 11:19:53', 0, 1);
INSERT INTO `borrow` VALUES (38, 67, 8, '2023-05-20 11:12:04', '2023-05-19 11:10:36', '2023-05-20 11:12:04', 0, 1);
INSERT INTO `borrow` VALUES (39, 90, 8, '2023-05-20 15:47:34', '2023-05-27 15:41:52', '2023-05-20 15:47:34', 0, 1);
INSERT INTO `borrow` VALUES (40, 72, 8, '2023-05-20 16:05:34', '2023-05-27 15:41:52', '2023-05-20 16:05:34', 0, 1);
INSERT INTO `borrow` VALUES (41, 90, 8, '2023-05-20 16:05:37', '2023-05-27 15:47:21', '2023-05-20 16:05:37', 0, 1);
INSERT INTO `borrow` VALUES (42, 61, 8, '2023-05-20 16:05:38', '2023-05-27 15:47:21', '2023-05-20 16:05:38', 0, 1);
INSERT INTO `borrow` VALUES (43, 95, 8, '2023-05-20 17:17:00', '2023-06-08 15:47:21', '2023-05-20 17:17:00', 1, 1);
INSERT INTO `borrow` VALUES (44, 92, 8, '2023-05-20 17:17:02', '2023-06-08 15:47:21', '2023-05-20 17:17:02', 1, 1);
INSERT INTO `borrow` VALUES (45, 74, 8, '2023-05-20 17:17:03', '2023-06-08 15:47:21', '2023-05-20 17:17:03', 1, 1);
INSERT INTO `borrow` VALUES (46, 66, 8, '2023-05-20 17:18:11', '2023-05-30 17:15:05', '2023-05-20 17:18:11', 1, 1);
INSERT INTO `borrow` VALUES (47, 61, 8, '2023-05-20 17:18:10', '2023-05-30 17:15:05', '2023-05-20 17:18:10', 1, 1);
INSERT INTO `borrow` VALUES (56, 88, 8, '2023-05-21 23:18:33', '2023-05-28 16:22:31', '2023-05-21 23:18:33', 0, 1);
INSERT INTO `borrow` VALUES (57, 91, 8, '2023-05-21 23:18:34', '2023-05-28 16:22:31', '2023-05-21 23:18:34', 0, 1);
INSERT INTO `borrow` VALUES (58, 69, 8, '2023-05-21 23:18:35', '2023-05-28 16:22:31', '2023-05-21 23:18:35', 0, 1);
INSERT INTO `borrow` VALUES (59, 88, 9, '2023-05-21 17:30:58', '2023-05-31 16:22:31', '2023-05-21 17:30:58', 1, 1);
INSERT INTO `borrow` VALUES (60, 95, 9, '2023-05-21 17:30:59', '2023-05-28 16:22:31', '2023-05-21 17:30:59', 0, 1);
INSERT INTO `borrow` VALUES (61, 96, 9, '2023-05-21 17:31:00', '2023-05-28 16:22:31', '2023-05-21 17:31:00', 0, 1);
INSERT INTO `borrow` VALUES (62, 61, 8, '2023-05-21 23:18:03', '2023-05-31 23:18:03', NULL, 0, 0);
INSERT INTO `borrow` VALUES (63, 88, 8, '2023-05-21 23:18:03', '2023-05-31 23:18:03', NULL, 0, 0);
INSERT INTO `borrow` VALUES (64, 66, 8, '2023-05-21 23:18:03', '2023-05-31 23:18:03', NULL, 0, 0);
INSERT INTO `borrow` VALUES (65, 61, 9, '2023-05-21 23:22:18', '2023-06-13 23:18:03', '2023-05-21 23:22:18', 1, 1);
INSERT INTO `borrow` VALUES (66, 62, 9, '2023-05-21 23:44:00', '2023-06-13 23:18:03', '2023-05-21 23:44:00', 1, 1);
INSERT INTO `borrow` VALUES (67, 71, 9, '2023-05-21 23:44:08', '2023-06-13 23:18:03', NULL, 1, 0);
INSERT INTO `borrow` VALUES (68, 93, 9, '2023-05-21 23:44:10', '2023-06-13 23:18:03', NULL, 1, 0);
INSERT INTO `borrow` VALUES (69, 96, 9, '2023-05-21 23:44:11', '2023-06-13 23:18:03', NULL, 1, 0);

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '不可重复',
  PRIMARY KEY (`type_id`) USING BTREE,
  UNIQUE INDEX `unique_tname`(`type_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '中国古典小说');
INSERT INTO `type` VALUES (18, '中国近现代小说');
INSERT INTO `type` VALUES (24, '传记/人物自传');
INSERT INTO `type` VALUES (4, '侦探/悬疑/推理');
INSERT INTO `type` VALUES (17, '儿童文学');
INSERT INTO `type` VALUES (22, '历史');
INSERT INTO `type` VALUES (2, '外国小说');
INSERT INTO `type` VALUES (23, '心理学');
INSERT INTO `type` VALUES (20, '科幻小说');
INSERT INTO `type` VALUES (21, '言情小说');
INSERT INTO `type` VALUES (3, '金融经济');

SET FOREIGN_KEY_CHECKS = 1;
