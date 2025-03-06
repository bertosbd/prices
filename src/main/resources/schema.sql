create table brands(
    brand_id int primary key,
    name varchar(255) not null
);

create table prices (
    id identity primary key,
    brand_id int not null,
    start_date timestamp not null,
    end_date timestamp not null,
    price_list integer not null,
    product_id integer not null,
    priority integer not null,
    price numeric(10,2) not null,
    curr varchar(3) not null
);

alter table prices add constraint fk_brand_id foreign key (brand_id) references brands(brand_id);