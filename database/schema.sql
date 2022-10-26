drop schema if exists moneytree;

create database moneytree;

use moneytree;

create table if not exists transactions(
	transaction_id int not null primary key auto_increment,
	category varchar(64),
	description varchar(512),	
	picture mediumblob,
	amount decimal(15,2),
	user_id bigint not null,
    date_added datetime not null,
	foreign key(user_id) references users(id)
);




