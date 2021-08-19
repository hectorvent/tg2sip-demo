-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE users (
	id serial NOT NULL,
	telegram_id int8 NOT NULL,
	telegram_name varchar NULL,
	telegram_username varchar NULL,
	telegram_phone varchar NULL,
	telegram_photo varchar NULL,
	default_sound varchar NULL,
	show_as_public bool NULL DEFAULT false,
	CONSTRAINT telegram_user_id_unq UNIQUE (telegram_id),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);


-- public.cdr definition

-- Drop table

-- DROP TABLE public.cdr;

CREATE TABLE cdr (
	id serial NOT NULL,
	user_id int4 NOT NULL,
	call_type varchar(10) NOT NULL,
	start_date timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	end_date timestamp NULL,
	call_id varchar NOT NULL,
	CONSTRAINT cdr_fk FOREIGN KEY (user_id) REFERENCES users(id)
);
