CREATE SCHEMA `library`;

CREATE TABLE library.address(
  address_id INTEGER AUTO_INCREMENT,
  street_name VARCHAR(100),
  building_number INTEGER,
  PRIMARY KEY (address_id)
);

CREATE TABLE library.editor (
  name VARCHAR(100),
  address_fk INTEGER NOT NULL,
  telephone VARCHAR(50) NOT NULL,
  cnpj VARCHAR(100) NOT NULL,
  PRIMARY KEY (name)
); 

ALTER TABLE library.editor
    ADD CONSTRAINT editor_address_fk
    FOREIGN KEY(address_fk)
    REFERENCES library.address(address_id);

CREATE TABLE library.autor (
  autor_id INTEGER AUTO_INCREMENT,
  name VARCHAR(100),
  surname VARCHAR(100),
  date_of_death DATE,
  date_of_birth DATE,
  place_of_death VARCHAR(100),
  place_of_birth VARCHAR(100),
  biography VARCHAR(15000),
  PRIMARY KEY (autor_id)
);

CREATE TABLE library.category(
  category_name VARCHAR(100),
  PRIMARY KEY(category_name)
);

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
  category_fk VARCHAR(100) NOT NULL,
  isbn VARCHAR(100) NOT NULL,
  PRIMARY KEY (book_id)
);

ALTER TABLE library.book
    ADD CONSTRAINT book_editor_fk
    FOREIGN KEY(editor_fk)
    REFERENCES library.editor(name);
    
ALTER TABLE library.book
    ADD CONSTRAINT book_category_fk
    FOREIGN KEY(category_fk)
    REFERENCES library.category(category_id);    

CREATE TABLE library.book_autor (
  book_autor_id INTEGER AUTO_INCREMENT,
  book_fk INTEGER NOT NULL,
  autor_fk INTEGER NOT NULL,
  PRIMARY KEY(book_autor_id)
);

ALTER TABLE library.book_autor
    ADD CONSTRAINT book_autor_book_fk
    FOREIGN KEY(book_fk)
    REFERENCES library.book(book_id);  
    
ALTER TABLE library.book_autor
    ADD CONSTRAINT book_autor_autor_fk
    FOREIGN KEY(autor_fk)
    REFERENCES library.autor(autor_id);      