/*'LVL 1'*/
CREATE TABLE IF NOT EXISTS s_user(
user_id BIGINT NOT NULL AUTO_INCREMENT,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
email VARCHAR(250) NOT NULL,
CONSTRAINT s_user_pk PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS s_address(
address_id BIGINT NOT NULL AUTO_INCREMENT,
city VARCHAR(250) NOT NULL,
street VARCHAR(250) NOT NULL,
house VARCHAR(250) NOT NULL,
CONSTRAINT s_address_pk PRIMARY KEY(address_id)
);

CREATE TABLE IF NOT EXISTS s_product(
product_id BIGINT NOT NULL AUTO_INCREMENT,
name VARCHAR(250) NOT NULL,
description VARCHAR(500) NOT NULL,
price DOUBLE PRECISION,
CONSTRAINT s_product_pk PRIMARY KEY(product_id)
);


/*'LVL 2'*/
CREATE TABLE IF NOT EXISTS s_order(
order_id BIGINT NOT NULL AUTO_INCREMENT,
user_id BIGINT NOT NULL,
status VARCHAR(25) NOT NULL,
order_cost DOUBLE PRECISION,
order_date DATETIME NOT NULL,
address_id BIGINT NOT NULL,
CONSTRAINT s_order_pk PRIMARY KEY(order_id),

CONSTRAINT order_user_fk FOREIGN KEY(user_id)
    REFERENCES s_user (user_id) ON UPDATE CASCADE,
CONSTRAINT order_address_fk FOREIGN KEY(address_id)
    REFERENCES s_address (address_id)
);


/*'LVL 3'*/
/*'many-to-many связь'*/
CREATE TABLE IF NOT EXISTS s_order_product(
order_id BIGINT NOT NULL,
product_id BIGINT NOT NULL,
quantity BIGINT NOT NULL,
CONSTRAINT order_product_pk PRIMARY KEY(order_id, product_id),

CONSTRAINT order_product_order_fk FOREIGN KEY (order_id)
    REFERENCES s_order(order_id) ON UPDATE CASCADE,
CONSTRAINT order_product_product_fk FOREIGN KEY (product_id)
    REFERENCES s_product(product_id)
);