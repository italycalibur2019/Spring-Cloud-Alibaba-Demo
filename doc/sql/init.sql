-- DROP DATABASE demo;

CREATE DATABASE demo;

\c runoobdb;

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

-- basic.t_goods_master_po definition

-- Drop table

-- DROP TABLE basic.t_goods_master_po;

CREATE TABLE basic.t_goods_master_po (
                                         id int8 NOT NULL, -- 主键
                                         master_name varchar(100) NULL, -- 货主名称
                                         phone varchar(20) NULL, -- 联系方式
                                         remark text NULL, -- 备注
                                         CONSTRAINT t_goods_master_po_pk PRIMARY KEY (id)
);
COMMENT ON TABLE basic.t_goods_master_po IS '货主表';

-- Column comments

COMMENT ON COLUMN basic.t_goods_master_po.id IS '主键';
COMMENT ON COLUMN basic.t_goods_master_po.master_name IS '货主名称';
COMMENT ON COLUMN basic.t_goods_master_po.phone IS '联系方式';
COMMENT ON COLUMN basic.t_goods_master_po.remark IS '备注';

-- DROP SCHEMA "order";

CREATE SCHEMA "order" AUTHORIZATION postgres;

-- DROP SCHEMA stock;

CREATE SCHEMA stock AUTHORIZATION postgres;

-- DROP SCHEMA fee;

CREATE SCHEMA fee AUTHORIZATION postgres;