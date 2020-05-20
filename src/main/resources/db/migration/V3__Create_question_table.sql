create table question
(
	id INT auto_increment,
	title VARCHAR(50),
	description TEXT,
	gmt_create BIGINT,
	gmt_modified BIGINT,
	creator INT,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag VARCHAR(256),
	constraint question_pk
		primary key (id)
);