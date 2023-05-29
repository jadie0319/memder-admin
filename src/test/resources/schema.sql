SELECT h2version();

create schema if not exists moim;
use moim;

create table if not exists moim.members (
    id                bigint auto_increment comment 'ID',
    name              varchar(10) comment '이름',
    role              varchar(10) comment '역할',
    birth_date        date comment '생일',
    gender            varchar(1) comment '성별',
    login_id          varchar(20) comment '로그인ID',
    password          varchar(255) comment '비밀번호',
    email             varchar(100) comment '이메일',
    groups             varchar(100) comment '소속',
    limit_ingredients varchar(255) comment '취식제한재료',
    description       varchar(255) comment '자기소개',
    reg_dt            datetime comment '생성 일시',
    primary key (id)
)
engine = innodb;
