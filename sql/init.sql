/* ============================================================================
   YunMusicWebsite 初始化脚本：建库 + 建表 + 假数据
   执行： mysql -u root -p --default-character-set=utf8mb4 < sql/init.sql
   特性： 可重复执行（建表 IF NOT EXISTS，插入前先清空）
   注意： 下面 @base 改成你的项目绝对路径，song.url 存的就是这个路径拼接
   ============================================================================ */

SET @base = 'D:/Projects/YunMusicWebsite';

CREATE DATABASE IF NOT EXISTS `lcy`
    DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `lcy`;

/* ---------------------------- 1. 建表 ---------------------------- */

CREATE TABLE IF NOT EXISTS `user` (
  `id`       VARCHAR(36)  NOT NULL COMMENT '主键，UUID',
  `account`  VARCHAR(50)  NOT NULL COMMENT '登录账号',
  `password` VARCHAR(100) NOT NULL COMMENT '密码（当前明文）',
  `nickname` VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
  `sex`      VARCHAR(10)  DEFAULT NULL COMMENT '性别，前端提交 male / female',
  `songlist` TEXT         COMMENT '收藏的歌单 id，逗号分隔',
  `songs`    TEXT         COMMENT '收藏的歌曲 id，逗号分隔',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

CREATE TABLE IF NOT EXISTS `playlist` (
  `id`            VARCHAR(36)  NOT NULL COMMENT '主键',
  `name`          VARCHAR(100) NOT NULL COMMENT '歌单名',
  `intro`         VARCHAR(1000) DEFAULT NULL COMMENT '简介',
  `songIds`       TEXT         COMMENT '歌曲 id，逗号分隔（顺序即曲序）',
  `rate`          VARCHAR(8)   DEFAULT NULL COMMENT '评分 0-5',
  `style`         VARCHAR(20)  DEFAULT NULL COMMENT '风格，前端 tab 下标 1-5',
  `coverFileName` VARCHAR(200) DEFAULT NULL COMMENT '封面文件名，存放于 static/image/songList/',
  PRIMARY KEY (`id`),
  KEY `idx_style` (`style`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='歌单';

CREATE TABLE IF NOT EXISTS `song` (
  `id`     VARCHAR(36)  NOT NULL COMMENT '主键',
  `name`   VARCHAR(200) NOT NULL COMMENT '歌曲名',
  `singer` VARCHAR(100) DEFAULT NULL COMMENT '歌手名（与 singer.name 关联）',
  `album`  VARCHAR(200) DEFAULT NULL COMMENT '专辑',
  `url`    VARCHAR(500) NOT NULL COMMENT '音频文件绝对路径',
  PRIMARY KEY (`id`),
  KEY `idx_singer` (`singer`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='歌曲';

CREATE TABLE IF NOT EXISTS `singer` (
  `id`            VARCHAR(36)  NOT NULL COMMENT '主键',
  `name`          VARCHAR(100) NOT NULL COMMENT '姓名',
  `gender`        VARCHAR(4)   DEFAULT NULL COMMENT '1男 2女 3组合，与前端 tab 下标一致',
  `area`          VARCHAR(50)  DEFAULT NULL COMMENT '地区',
  `intro`         VARCHAR(1000) DEFAULT NULL COMMENT '介绍',
  `masterpiece`   VARCHAR(200) DEFAULT NULL COMMENT '代表作',
  `photoFileName` VARCHAR(200) DEFAULT NULL COMMENT '照片文件名，存放于 static/image/singer/',
  PRIMARY KEY (`id`),
  KEY `idx_gender` (`gender`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='歌手';

/* ------------------------- 2. 清空旧假数据 ------------------------- */

DELETE FROM `user`;
DELETE FROM `playlist`;
DELETE FROM `song`;
DELETE FROM `singer`;

/* ---------------------------- 3. 歌手 ----------------------------
   姓名暂用图片文件名占位，换成真实姓名时记得同步改 song.singer
   gender: 1=男 2=女 3=组合，对应前端 tab 下标
   ------------------------------------------------------------------ */

INSERT INTO `singer` (`id`, `name`, `gender`, `area`, `intro`, `masterpiece`, `photoFileName`) VALUES
('g01', '5566',    '3', '中国台湾', '示例数据：男子组合，可替换为真实资料。', '我难过',   '5566.jpg'),
('g02', 'Lambsey', '3', '日本',     '示例数据：乐队，可替换为真实资料。',     '远方',     'Lambsey.jpg'),
('g03', 'ac',      '1', '华语',     '示例数据：华语男歌手。',               '回声',     'ac.jpg'),
('g04', 'cjj',     '2', '华语',     '示例数据：华语女歌手。',               '旧巷',     'cjj.jpg'),
('g05', 'cx',      '2', '华语',     '示例数据：华语女歌手。',               '潮汐',     'cx.jpg'),
('g06', 'cz',      '1', '华语',     '示例数据：华语男歌手。',               '午夜公路', 'cz.jpg'),
('g07', 'ljj',     '1', '华语',     '示例数据：华语男歌手。',               '山海',     'ljj.jpg'),
('g08', 'lmskd',   '3', '欧美',     '示例数据：欧美组合。',                 '无名之辈', 'lmskd.jpg'),
('g09', 'mljyyq',  '2', '华语',     '示例数据：华语女歌手。',               '玻璃海',   'mljyyq.jpg'),
('g10', 'wdm',     '1', '华语',     '示例数据：华语男歌手。',               '慢慢',     'wdm.jpg'),
('g11', 'wyt',     '1', '华语',     '示例数据：华语男歌手。',               '北纬三十度', 'wyt.jpg'),
('g12', 'xs',      '2', '华语',     '示例数据：华语女歌手。',               '白鸽',     'xs.jpg'),
('g13', 'zjl',     '1', '华语',     '示例数据：华语男歌手。',               '晚风',     'zjl.jpg'),
('g14', 'zs',      '1', '华语',     '示例数据：华语男歌手。',               '晴日',     'zs.jpg');

/* ---------------------------- 4. 歌曲 ----------------------------
   14 首全部指向同一个示例音频，放一个 mp3 就能点播所有歌；
   想听不同歌，把 music/ 下放多个 mp3 后改这里的 url 即可
   ------------------------------------------------------------------ */

INSERT INTO `song` (`id`, `name`, `singer`, `album`, `url`) VALUES
('s01', '晚风',        'zjl',     '夜航',         CONCAT(@base, '/music/demo.mp3')),
('s02', '山海',        'ljj',     '城市之光',     CONCAT(@base, '/music/demo.mp3')),
('s03', '旧巷',        'cjj',     '旧巷',         CONCAT(@base, '/music/demo.mp3')),
('s04', '潮汐',        'cx',      '潮汐',         CONCAT(@base, '/music/demo.mp3')),
('s05', '午夜公路',    'cz',      '午夜公路',     CONCAT(@base, '/music/demo.mp3')),
('s06', '晴日',        'zs',      '晴日',         CONCAT(@base, '/music/demo.mp3')),
('s07', '北纬三十度',  'wyt',     '北纬三十度',   CONCAT(@base, '/music/demo.mp3')),
('s08', '回声',        'ac',      '回声',         CONCAT(@base, '/music/demo.mp3')),
('s09', '玻璃海',      'mljyyq',  '玻璃海',       CONCAT(@base, '/music/demo.mp3')),
('s10', '慢慢',        'wdm',     '慢慢',         CONCAT(@base, '/music/demo.mp3')),
('s11', '白鸽',        'xs',      '白鸽',         CONCAT(@base, '/music/demo.mp3')),
('s12', '无名之辈',    'lmskd',   '无名之辈',     CONCAT(@base, '/music/demo.mp3')),
('s13', '雨落',        '5566',    '雨落',         CONCAT(@base, '/music/demo.mp3')),
('s14', '远方',        'Lambsey', '远方',         CONCAT(@base, '/music/demo.mp3'));

/* ---------------------------- 5. 歌单 ----------------------------
   style: 1=华语 2=粤语 3=欧美 4=日韩 5=纯音乐（与前端 tab 一致）
   coverFileName 对应 static/image/songList/1~12.jpg
   ------------------------------------------------------------------ */

INSERT INTO `playlist` (`id`, `name`, `intro`, `songIds`, `rate`, `style`, `coverFileName`) VALUES
('p01', '华语流行精选',   '华语流行必听，随手点开都不踩雷。',       's01,s02,s03,s04', '5', '1', '1.jpg'),
('p02', '粤语经典重温',   '老歌不老，前奏一响就跟着唱。',           's05,s06',         '4', '2', '2.jpg'),
('p03', '欧美热单速递',   '节奏拉满，通勤路上提神用。',             's12,s14',         '5', '3', '3.jpg'),
('p04', '日韩节奏',       '轻快旋律，适合边走边听。',               's13,s08',         '4', '4', '4.jpg'),
('p05', '纯音乐·静心',    '无人声，写代码和看书时的背景音。',       's09,s10',         '5', '5', '5.jpg'),
('p06', '深夜单曲循环',   '夜里只听这几首，越听越清醒。',           's01,s07,s11',     '4', '1', '6.jpg'),
('p07', '通勤路上',       '地铁四十分钟刚好听完。',                 's02,s12,s14',     '4', '3', '7.jpg'),
('p08', '午后咖啡馆',     '慢下来，配一杯手冲。',                   's09,s10,s11',     '3', '5', '8.jpg'),
('p09', '运动燃脂',       'BPM 拉起来，最后一组靠它。',             's03,s04,s05',     '5', '3', '9.jpg'),
('p10', '民谣与诗',       '木吉他、远方和没写完的信。',             's06,s07',         '4', '1', '10.jpg'),
('p11', '经典老歌',       '爸妈听到也会跟着哼的那种。',             's05,s06,s13',     '4', '2', '11.jpg'),
('p12', '雨天钢琴',       '雨声混着琴键，适合发呆。',               's08,s09,s10',     '5', '5', '12.jpg');

/* ---------------------------- 6. 用户 ----------------------------
   两个测试账号，密码都是 123456（当前为明文比对）
   test 已预置收藏，用来验证歌单/歌曲收藏态高亮
   ------------------------------------------------------------------ */

INSERT INTO `user` (`id`, `account`, `password`, `nickname`, `sex`, `songlist`, `songs`) VALUES
('u01', 'test', '123456', '测试用户', 'male',   'p01,p03', 's01,s05'),
('u02', 'demo', '123456', '演示账号', 'female', '',        '');

/* ---------------------------- 7. 自检 ---------------------------- */

SELECT 'singer' AS table_name, COUNT(*) AS total FROM `singer`
UNION ALL SELECT 'song', COUNT(*) FROM `song`
UNION ALL SELECT 'playlist', COUNT(*) FROM `playlist`
UNION ALL SELECT 'user', COUNT(*) FROM `user`;

-- 歌单引用了不存在的歌曲 id（应返回 0 行）
-- 判断依据：song 表中能匹配到的数量，应等于 songIds 里 id 的个数
SELECT p.id, p.name, p.songIds
FROM `playlist` p
WHERE (SELECT COUNT(*) FROM `song` s WHERE FIND_IN_SET(s.id, p.songIds))
      <> (1 + LENGTH(p.songIds) - LENGTH(REPLACE(p.songIds, ',', '')));

-- 歌曲的歌手在 singer 表里找不到（应返回 0 行）
SELECT s.id, s.name, s.singer
FROM `song` s
LEFT JOIN `singer` g ON g.name = s.singer
WHERE g.id IS NULL;
