
CREATE DATABASE franchise_db;
USE franchise_db;
CREATE TABLE `franchise` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL
);

CREATE TABLE `branch` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `franchise_id` bigint NOT NULL,

  CONSTRAINT fk_branch_franchise
    FOREIGN KEY (`franchise_id`)
    REFERENCES `franchise` (`id`)
    ON DELETE CASCADE
);

CREATE TABLE `product` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `stock` int NOT NULL DEFAULT 0,
  `branch_id` bigint NOT NULL,

  CONSTRAINT fk_product_branch
    FOREIGN KEY (`branch_id`)
    REFERENCES `branch` (`id`)
    ON DELETE CASCADE
);

CREATE INDEX `idx_branch_franchise` 
ON `branch` (`franchise_id`);

CREATE UNIQUE INDEX `uk_branch_name_franchise` 
ON `branch` (`name`, `franchise_id`);

CREATE INDEX `idx_product_branch` 
ON `product` (`branch_id`);

CREATE UNIQUE INDEX `uk_product_name_branch` 
ON `product` (`name`, `branch_id`);