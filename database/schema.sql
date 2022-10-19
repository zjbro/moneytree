drop schema if exists moneytree;

create database moneytree;

use moneytree;

create table if not exists users(
	username varchar(32) primary key not null,
	password varchar (256) not null
);

create table if not exists moneyflow(
	transaction_id int not null primary key auto_increment,
	category varchar(64),
	description varchar(512),	
	picture mediumblob,
	amount decimal(15,2),
	username varchar(32) not null,
	foreign key(username) references users(username)
);




