-- DROP DATABASE demo;

CREATE DATABASE demo;

USE demo;

-- DROP SCHEMA sys;

CREATE SCHEMA sys AUTHORIZATION postgres;

-- sys.t_user_po definition

-- Drop table

-- DROP TABLE sys.t_user_po;

CREATE TABLE sys.t_user_po (
    id int8 NOT NULL, -- 主键
    username varchar(50) NOT NULL, -- 用户名
    "password" varchar(128) DEFAULT 'ec7805df356b9be271806d5de694c8f0'::character varying NOT NULL, -- 密码
    CONSTRAINT t_user_po_pk PRIMARY KEY (id),
    CONSTRAINT t_user_po_unique UNIQUE (username)
);
COMMENT ON TABLE sys.t_user_po IS '用户表';

-- Column comments

COMMENT ON COLUMN sys.t_user_po.id IS '主键';
COMMENT ON COLUMN sys.t_user_po.username IS '用户名';
COMMENT ON COLUMN sys.t_user_po."password" IS '密码';

-- DROP SCHEMA basic;

CREATE SCHEMA basic AUTHORIZATION postgres;

-- basic.t_goods_po definition

-- Drop table

-- DROP TABLE basic.t_goods_po;

CREATE TABLE basic.t_goods_po (
    id int8 NOT NULL, -- 主键
    goods_code varchar(100) NOT NULL, -- 货物编号
    goods_name varchar(100) NOT NULL, -- 货物名称
    CONSTRAINT t_goods_po_pk PRIMARY KEY (id),
    CONSTRAINT t_goods_po_unique UNIQUE (goods_code)
);
COMMENT ON TABLE basic.t_goods_po IS '货物表';

-- Column comments

COMMENT ON COLUMN basic.t_goods_po.id IS '主键';
COMMENT ON COLUMN basic.t_goods_po.goods_code IS '货物编号';
COMMENT ON COLUMN basic.t_goods_po.goods_name IS '货物名称';