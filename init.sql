-- card 테이블 생성
CREATE TABLE IF NOT EXISTS card
(
    card_id     INT         NOT NULL PRIMARY KEY,
    card_title  VARCHAR(30) NOT NULL,
    budget      BIGINT,
    deadline    DATETIME,
    card_status VARCHAR(10) NOT NULL
);

-- cardboard 테이블 생성
CREATE TABLE IF NOT EXISTS cardboard
(
    cardboard_id INT NOT NULL PRIMARY KEY,
    user_id      INT NOT NULL,
    card_ids     JSON
);

-- todo 테이블 생성
CREATE TABLE IF NOT EXISTS todo
(
    todo_id           INT         NOT NULL PRIMARY KEY,
    todo_item         VARCHAR(35) NOT NULL,
    todo_check_status VARCHAR(10) NOT NULL,
    card_id           INT         NOT NULL
);

-- user 테이블 생성
CREATE TABLE IF NOT EXISTS user
(
    email         VARCHAR(30)  NOT NULL,
    password      VARCHAR(100) NOT NULL,
    name          VARCHAR(20)  NOT NULL,
    nick_name     VARCHAR(20),
    created_date  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id            INT          NOT NULL PRIMARY KEY,
    partner_email VARCHAR(30)
);

-- couple 테이블 생성
CREATE TABLE IF NOT EXISTS couple
(
    couple_id        INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    sender_user_id   INT NOT NULL,
    receiver_user_id INT NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_couple (sender_user_id, receiver_user_id)
);
