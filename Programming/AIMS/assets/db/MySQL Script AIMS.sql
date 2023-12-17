create database AIMS;

use AIMS;

create table Product(
	id int PRIMARY KEY AUTO_INCREMENT,
    category varchar(45) not null,
    title varchar(45) not null,
    price int not null,
    value int not null,
    quantity int not null,
    isSupportRushDelivery bool
);

create table Book(
	id int,
    authors varchar(45) not null,
    coverType varchar(45),
    publisher varchar(45) not null,
    publicationDate date
);

alter table Book add column numOfPages int;

alter table Book add column bookCategory varchar(50);

alter table Book add constraint fk_Book_Product foreign key(id) references Product(id);

create table CD(
	id int,
    artist varchar(45) not null,
    tracklist varchar(200) not null,
    recordLabel varchar(45) not null,
    genre varchar(45),
    releaseDate date
);

alter table CD add constraint fk_CD_Product foreign key(id) references Product(id);

create table DVD(
	id int,
    director varchar(45) not null,
    diskType varchar(45) not null,
    studio varchar(45) not null,
    subtitles varchar(45) not null,
    runtime int not null,
    language varchar(45) not null
);

alter table DVD add column releasedDate date;

alter table DVD add constraint fk_DVD_Product foreign key(id) references Product(id);

create table LP(
	id int,
    artist varchar(45) not null,
    tracklist varchar(200) not null,
    recordLabel varchar(45) not null,
    genre varchar(45),
    releaseDate date
);

alter table LP add constraint fk_LP_Product foreign key(id) references Product(id);

create table Cart(
	id int primary key auto_increment,
    subTotal int,
    total int
);

create table CartProduct(
	product_id int,
    cart_id int,
    quantity int not null,
    primary key(product_id, cart_id),
    foreign key(product_id) references Product(id),
    foreign key(cart_id) references Cart(id)
);

create table DeliveryInfo(
	id int primary key auto_increment,
    receiverName varchar(45) not null,
    phoneNumber char(11) not null,
    email varchar(45) not null,
    province varchar(45) not null,
    address varchar(100) not null,
    shippingMethod varchar(45) not null
);

create table RushDelivery(
    timeReceiver datetime,
    instruction varchar(100),
    deliveryInfoId int,
    foreign key (deliveryInfoId) references DeliveryInfo(id)
);

create table Orderr(
	id int primary key,
    shippingFee int,
    cartId int,
    deliveryInfoId int,
    foreign key(cartId) references Cart(id),
    foreign key(deliveryInfoId) references DeliveryInfo(id)
);

create table Card(
	id int primary key,
    cardNumber varchar(45) not null,
    cardHolderName varchar(45) not null,
	type varchar(45) not null,
    bank varchar(45),
    cvvCode int,
    expirationDate date,
    issueDate date,
    email varchar(45),
    province varchar(45),
    address varchar(100)
);

create table PaymentTransaction(
	id varchar(45) primary key,
    content varchar(100) not null,
    transaction datetime,
    amount int,
    cardId int,
    foreign key(cardId) references Card(id)
);

create table Invoice(
	id int primary key,
    totalAmount int,
    orderId int,
    paymentTransactionId varchar(45),
    foreign key(orderId) references Orderr(id),
    foreign key(paymentTransactionId) references PaymentTransaction(id)
);

CREATE INDEX fk_Order_DeleveryInfo1_idx ON Orderr(deliveryInfoId);
CREATE INDEX fk_Order_Cart_idx ON Orderr(cartId);

CREATE INDEX fk_Cart_Product_idx ON CartProduct(product_id);
CREATE INDEX fk_Product_Cart_idx ON CartProduct(cart_id);

CREATE INDEX fk_Invoice_Order1_idx ON Invoice(orderId);

CREATE INDEX fk_PaymentTransaction_Card1_idx ON PaymentTransaction (cardId);
CREATE INDEX fk_PaymentTransaction_Invoice1_idx ON Invoice (paymentTransactionId);

INSERT INTO Product (category, title, price, value, quantity, isSupportRushDelivery)
VALUES 
    ('Book', 'The Great Gatsby', 20, 15, 50, true),
    ('CD', 'Abbey Road', 15, 10, 30, false),
    ('DVD', 'Inception', 25, 20, 40, true),
    ('LP', 'Dark Side of the Moon', 30, 25, 25, false);
    
INSERT INTO Book (id, authors, coverType, publisher, publicationDate)
VALUES 
    (1, 'F. Scott Fitzgerald', 'Hardcover', 'Scribner', '1925-04-10');
    
INSERT INTO CD (id, artist, tracklist, recordLabel, genre, releaseDate)
VALUES 
    (2, 'The Beatles', '1. Come Together, 2. Something, 3. Oh! Darling', 'EMI', 'Rock', '1969-09-26');

INSERT INTO DVD (id, director, diskType, studio, subtitles, runtime, language)
VALUES 
    (3, 'Christopher Nolan', 'Blu-ray', 'Warner Bros.', 'English', 148, 'English');

INSERT INTO LP (id, artist, tracklist, recordLabel, genre, releaseDate)
VALUES 
    (4, 'Pink Floyd', '1. Speak to Me, 2. Breathe, 3. On the Run', 'Harvest', 'Progressive Rock', '1973-03-01');

select * from Product;
    
alter table Product add column imageUrl varchar(100);

update Product set imageUrl = 'assets/images/book/book1.jpg' where id > 0;

INSERT INTO Product (category, title, price, value, quantity, imageUrl, isSupportRushDelivery)
VALUES 
    ('Book', 'To Kill a Mockingbird', 18, 15, 5,'assets/images/book/book2.jpg', true),
    ('CD', 'Thriller', 12, 10, 3,'assets/images/cd/cd1.jpg', false),
    ('DVD', 'The Shawshank Redemption', 20, 15,'assets/images/dvd/dvd1.jpg', 38, true),
    ('LP', 'Hotel California', 35, 28, 30,'assets/images/cd/cd2.jpg', false),
    ('Book', '1984', 15, 10, 50, 'assets/images/book/book4.jpg', true),
    ('CD', 'The Wall', 25, 22, 2,'assets/images/cd/cd3.jpg', false),
    ('DVD', 'Pulp Fiction', 18, 15, 40,'assets/images/dvd/dvd2.jpg', true),
    ('LP', 'Led Zeppelin IV', 30, 25, 28,'assets/images/cd/cd4.jpg', false);
    
delete from Product where id = 13;

INSERT INTO Book (id, authors, coverType, publisher, publicationDate)
VALUES 
    (5, 'Harper Lee', 'Paperback', 'J.B. Lippincott & Co.', '1960-07-11'),
    (9, 'George Orwell', 'Hardcover', 'Secker & Warburg', '1949-06-08');
    
INSERT INTO CD (id, artist, tracklist, recordLabel, genre, releaseDate)
VALUES 
    (6, 'Michael Jackson', '1. Wanna Be Startin\' Somethin\', 2. Thriller, 3. Beat It', 'Epic Records', 'Pop', '1982-11-30'),
    (10, 'Michael Jackson', '1. Wanna Be Startin\' Somethin\', 2. Thriller, 3. Beat It', 'Epic Records', 'Pop', '1982-11-30');
    
INSERT INTO DVD (id, director, diskType, studio, subtitles, runtime, language)
VALUES 
    (7, 'Frank Darabont', 'Blu-ray', 'Columbia Pictures', 'English', 142, 'English'),
    (11, 'Quentin Tarantino', 'DVD', 'Miramax Films', 'English', 154, 'English');

INSERT INTO LP (id, artist, tracklist, recordLabel, genre, releaseDate)
VALUES 
    (8, 'Led Zeppelin', '1. Black Dog, 2. Stairway to Heaven, 3. Rock and Roll', 'Atlantic Records', 'Hard Rock', '1971-11-08'),
    (12, 'Led Zeppelin', '1. Black Dog, 2. Stairway to Heaven, 3. Rock and Roll', 'Atlantic Records', 'Hard Rock', '1971-11-08');
    
ALTER TABLE PaymentTransaction 
change transaction transactionTime varchar(50);

ALTER TABLE PaymentTransaction 
DROP foreign key paymenttransaction_ibfk_1, drop column cardId;

ALTER TABLE PaymentTransaction
ADD COLUMN cardHolderName varchar(50);

ALTER TABLE orderr 
DROP foreign key orderr_ibfk_1, drop column cartId;

create table OrderProduct(
	product_id int,
    order_id int,
    quantity int not null,
    primary key(product_id, order_id),
    foreign key(product_id) references Product(id),
    foreign key(order_id) references orderr(id)
);






