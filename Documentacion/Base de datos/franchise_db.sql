-- =========================
-- DATABASE
-- =========================
CREATE DATABASE franchise_db;
USE franchise_db;

-- =========================
-- TABLE FRANCHISE
-- =========================
CREATE TABLE franchise (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

-- =========================
-- TABLE BRANCH
-- =========================
CREATE TABLE branch (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  franchise_id BIGINT NOT NULL,

  CONSTRAINT fk_branch_franchise
    FOREIGN KEY (franchise_id)
    REFERENCES franchise(id)
    ON DELETE CASCADE
);

CREATE INDEX idx_branch_franchise
ON branch(franchise_id);

CREATE UNIQUE INDEX uk_branch_name_franchise
ON branch(name, franchise_id);

-- =========================
-- TABLE PRODUCT
-- =========================
CREATE TABLE product (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  branch_id BIGINT NOT NULL,

  CONSTRAINT fk_product_branch
    FOREIGN KEY (branch_id)
    REFERENCES branch(id)
    ON DELETE CASCADE
);

CREATE INDEX idx_product_branch
ON product(branch_id);

CREATE UNIQUE INDEX uk_product_name_branch
ON product(name, branch_id);

-- =========================
-- DATA: FRANCHISE
-- =========================
INSERT INTO franchise (name) VALUES
('Tech Store'),
('Food World'),
('Sport Center');

-- =========================
-- DATA: BRANCH
-- =========================
INSERT INTO branch (name, franchise_id) VALUES
('Tech Bogotá', 1),
('Tech Medellín', 1),
('Food Bogotá', 2),
('Food Cali', 2),
('Sport Bogotá', 3);

-- =========================
-- DATA: PRODUCT (TECH)
-- =========================
INSERT INTO product (name, stock, branch_id) VALUES
('Laptop', 50, 1),
('Mouse', 120, 1),
('Keyboard', 80, 1),

('Laptop', 30, 2),
('Mouse', 200, 2),
('Monitor', 90, 2);

-- =========================
-- DATA: PRODUCT (FOOD)
-- =========================
INSERT INTO product (name, stock, branch_id) VALUES
('Burger', 150, 3),
('Fries', 200, 3),
('Soda', 300, 3),

('Burger', 180, 4),
('Pizza', 90, 4),
('Soda', 400, 4);

-- =========================
-- DATA: PRODUCT (SPORT)
-- =========================
INSERT INTO product (name, stock, branch_id) VALUES
('Ball', 60, 5),
('Shoes', 40, 5),
('Jersey', 25, 5);