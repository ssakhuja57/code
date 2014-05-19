create schema dss_permissions;
use dss_permissions;

create table roles(role_name varchar(30) primary key, master_role char(1));
create table users(uname varchar(30) primary key, password varchar(50), f_name varchar(30), l_name varchar(30), email varchar(60));
create table user_roles(uname varchar(30), role_name varchar(30),
		foreign key (uname) references users(uname),
		foreign key (role_name) references roles(role_name));
create table views(view_name varchar(100), owning_schema varchar(50), created_date date, primary key (view_name, owning_schema));
create table view_role_mapping(view_name varchar(100), owning_schema varchar(50), role_name varchar(30), date_granted date,
		foreign key (view_name, owning_schema) references views(view_name, owning_schema),
		foreign key (role_name) references roles(role_name));
