DROP TABLE IF EXISTS PRODUCTS_OF_ORDER;
DROP TABLE IF EXISTS ORDERED_PRODUCT;
DROP TABLE IF EXISTS AN_ORDER;
DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE PRODUCT
(
    ID         INT AUTO_INCREMENT PRIMARY KEY,
    NAME       VARCHAR(250) NOT NULL,
    STOCK      NUMBER(16) NOT NULL,
    UNIT_PRICE NUMBER(16, 4) DEFAULT NULL
);

CREATE TABLE AN_ORDER
(
    ID               INT AUTO_INCREMENT PRIMARY KEY,
    CREATION_DATE    TIMESTAMP NOT NULL,
    TOTAL_PRICE      DECIMAL,
    PAYMENT_RECEIVED BOOLEAN   NOT NULL DEFAULT FALSE
);

CREATE TABLE PRODUCTS_OF_ORDER
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    ORDER_ID   INT NOT NULL,
    PRODUCT_ID INT NOT NULL
);

CREATE TABLE ORDERED_PRODUCT
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    ORIGINAL_PRODUCT INT NOT NULL,
    AMOUNT           INT NOT NULL
);



INSERT INTO PRODUCT (name, stock, unit_price)
VALUES ('milk', 7, 22.1),
       ('bread', 17, 11.37),
       ('coffee', 27, 33.51);
