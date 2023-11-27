CREATE TABLE cookie (
    id bigserial primary key ,
    name varchar(255) not null ,
    description varchar(255) not null,
    create_date date default current_timestamp
);