CREATE SCHEMA `library`;

CREATE TABLE library.address(
  address_id INTEGER AUTO_INCREMENT,
  street_name VARCHAR(100),
  building_number INTEGER,
  area_code VARCHAR(100),
  state VARCHAR(100),
  city VARCHAR(100),
  PRIMARY KEY (address_id, street_name, building_number, area_code)
);

INSERT INTO library.address(street_name, building_number, area_code, state, city )
  VALUES('Teste de rua', 53, 'CEP', 'Estado', 'Cidade');

CREATE TABLE library.editor (
  name VARCHAR(100),
  address_fk INTEGER NOT NULL,
  telephone VARCHAR(50) NOT NULL,
  cnpj VARCHAR(100) NOT NULL,
  PRIMARY KEY (name)
); 

INSERT INTO library.editor(name, address_fk, telephone, cnpj)
  VALUES('O''Reilly', 1, 'Telefone', 'CNPJ');

ALTER TABLE library.editor
    ADD CONSTRAINT editor_address_fk
    FOREIGN KEY(address_fk)
    REFERENCES library.address(address_id)
    ON DELETE CASCADE;

CREATE TABLE library.autor (
  autor_id INTEGER AUTO_INCREMENT,
  name VARCHAR(100),
  date_of_death DATE,
  date_of_birth DATE,
  country_of_birth VARCHAR(100),
  state_of_birth VARCHAR(100),
  city_of_birth VARCHAR(100),
  country_of_death VARCHAR(100),
  state_of_death VARCHAR(100),
  city_of_death VARCHAR(100),
  biography VARCHAR(15000),
  PRIMARY KEY (autor_id)
);

INSERT INTO library.autor(name, date_of_death, date_of_birth, country_of_birth, state_of_birth, city_of_birth, country_of_death, state_of_death, city_of_death, biography)
  VALUES('Autor Sobrenome autor', '1920/04/02', '1800/01/02', 'Pais', 'Estado', 'Cidade', 'Pais morte', 'Estado morte', 'Cidade Morte', 'Foi um cara bem morrido');
INSERT INTO library.autor(name, date_of_death, date_of_birth, country_of_birth, state_of_birth, city_of_birth, country_of_death, state_of_death, city_of_death, biography)
  VALUES('Autor2 Sobrenome auto2r', '1920/04/02', '1800/01/02', 'Pais2', 'Estado2', 'Cidade2', 'Pais morte2', 'Estado morte2', 'Cidade Morte2', 'Foi um cara bem morrido2');  

CREATE TABLE library.category(
  category_id INTEGER AUTO_INCREMENT,
  category_name VARCHAR(500),
  PRIMARY KEY(category_id)
);

INSERT INTO category(category_name) VALUES ('ROMANCE');
INSERT INTO category(category_name) VALUES ('TECNOLOGIA');  

CREATE TABLE library.book (
  book_id INTEGER AUTO_INCREMENT,
  title VARCHAR(200) NOT NULL,
  format VARCHAR(50) NOT NULL,
  editor_fk VARCHAR(100) NOT NULL,
  page_number INTEGER,
  publication_date DATE,
  summary VARCHAR(5000),
  idx VARCHAR(5000),
  stock INTEGER NOT NULL,
  sale_price INTEGER NOT NULL,
  cost_price INTEGER NOT NULL,
  profit_margin INTEGER NOT NULL,
  isbn VARCHAR(100) NOT NULL,
  cover_directory VARCHAR(1000),
  CONSTRAINT book_isbn_un UNIQUE (isbn),
  PRIMARY KEY (book_id)
);

ALTER TABLE library.book
    ADD CONSTRAINT book_editor_fk
    FOREIGN KEY(editor_fk)
    REFERENCES library.editor(name)
    ON DELETE CASCADE;
    
CREATE TABLE library.book_category(
  book_category_id INTEGER AUTO_INCREMENT,
  book_fk INTEGER NOT NULL,
  category_fk INTEGER NOT NULL,
  PRIMARY KEY (book_category_id)
); 

ALTER TABLE library.book_category
    ADD CONSTRAINT book_category_book_fk
    FOREIGN KEY(book_fk)
    REFERENCES library.book(book_id)
    ON DELETE CASCADE;
    
ALTER TABLE library.book_category
    ADD CONSTRAINT book_category_category_fk
    FOREIGN KEY(category_fk)
    REFERENCES library.category(category_id)
    ON DELETE CASCADE;
    
CREATE TABLE library.book_autor (
  book_autor_id INTEGER AUTO_INCREMENT,
  book_fk INTEGER NOT NULL,
  autor_fk INTEGER NOT NULL,
  PRIMARY KEY(book_autor_id)
);

ALTER TABLE library.book_autor
    ADD CONSTRAINT book_autor_book_fk
    FOREIGN KEY(book_fk)
    REFERENCES library.book(book_id)
    ON DELETE CASCADE;  
    
ALTER TABLE library.book_autor
    ADD CONSTRAINT book_autor_autor_fk
    FOREIGN KEY(autor_fk)
    REFERENCES library.autor(autor_id)
    ON DELETE CASCADE;    
    
drop table book_autor;
drop table book_category;
drop table book;
drop table editor;
drop table address;
drop table autor;
drop table category;

   


    
    
    
    
    
    
    
    