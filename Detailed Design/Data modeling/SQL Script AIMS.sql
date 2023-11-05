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

