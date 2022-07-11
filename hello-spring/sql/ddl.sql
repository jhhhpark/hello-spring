drop table if exists member CASCADE;
create table member
(
    id bigint generated by default as identity,
    name varchar(255),
    primary key (id)
);

insert into member(name) values('홍길동');
insert into member(name) values('김아무개');