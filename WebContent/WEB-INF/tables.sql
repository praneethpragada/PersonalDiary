
// login as SYSTEM
create user personaldiary identified by pd;
grant connect, resource to personaldiary;

connect  personaldiary/pd


create table users
( uname    varchar2(10) primary key,
  password varchar2(10) not null,
  fullname varchar2(50) not null,
  email    varchar2(50) unique not null,
  mobile   varchar2(10),
  joinedon date
);

insert into users  values ('bob','bob','Bob Tabour','bob@microsoft.com',
  '9898989998',sysdate);

create sequence entryid_seq nocache;

create table DiaryEntries
( entryid  number(5)  primary key,
  uname    varchar2(10) references users(uname) not null,
  entrydate varchar2(10)  not null,
  entrytime varchar2(5),
  entrytext varchar2(4000) not null
);

insert into diaryentries value( entryid_seq.nextval,
 'bob','15-sep-15','17:00','Started using JSF');




