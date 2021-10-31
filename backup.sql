-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `id` VARCHAR(25) NOT NULL,
  `password` VARCHAR(65) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(20) NULL DEFAULT NULL,
  `picture` VARCHAR(150) NULL DEFAULT NULL,
  `social` VARCHAR(10) NULL DEFAULT NULL,
  `bio` VARCHAR(200) NULL DEFAULT NULL,
  `recent_board` VARCHAR(45) NULL DEFAULT NULL,
  `invited_board` VARCHAR(1000) NULL DEFAULT NULL,
  `role` VARCHAR(10) NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(20) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`board` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `user_id` VARCHAR(45) NOT NULL,
  `bg_color` VARCHAR(45) NULL DEFAULT NULL,
  `invited_user` VARCHAR(1200) NULL DEFAULT NULL,
  `public_yn` VARCHAR(1) NOT NULL,
  `hashtag` VARCHAR(50) NULL DEFAULT NULL,
  `like_count` INT NOT NULL DEFAULT '0',
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(20) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_board_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 381
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`hashtag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`hashtag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 119
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`board_has_hashtag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`board_has_hashtag` (
  `board_id` BIGINT NOT NULL,
  `hashtag_id` BIGINT NOT NULL,
  INDEX `fk_board_id_idx` (`board_id` ASC) VISIBLE,
  INDEX `fk_hash_tag_id_idx` (`hashtag_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_id`
    FOREIGN KEY (`board_id`)
    REFERENCES `mydb`.`board` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_hashtag_id`
    FOREIGN KEY (`hashtag_id`)
    REFERENCES `mydb`.`hashtag` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`board_has_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`board_has_like` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `board_id` BIGINT NOT NULL,
  `user_id` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_board_id_idx` (`board_id` ASC) VISIBLE,
  INDEX `fk_user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_like_board_id`
    FOREIGN KEY (`board_id`)
    REFERENCES `mydb`.`board` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_like_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 1062
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`list` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `board_id` BIGINT NOT NULL,
  `title` VARCHAR(20) NULL DEFAULT NULL,
  `pos` DOUBLE NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(20) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_list_board1_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `fk_list_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `mydb`.`board` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 579
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`card` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `list_id` BIGINT NOT NULL,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `pos` DOUBLE NULL DEFAULT NULL,
  `description` VARCHAR(1200) NULL DEFAULT NULL,
  `label_color` VARCHAR(100) NULL DEFAULT NULL,
  `location` VARCHAR(300) NULL DEFAULT NULL,
  `is_checklist` VARCHAR(1) NOT NULL,
  `is_attachment` VARCHAR(1) NOT NULL,
  `due_date` DATETIME NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(20) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_card_list1_idx` (`list_id` ASC) VISIBLE,
  CONSTRAINT `fk_card_list`
    FOREIGN KEY (`list_id`)
    REFERENCES `mydb`.`list` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1006
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`checklist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`checklist` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `card_id` BIGINT NOT NULL,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(20) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `card_id_idx` (`card_id` ASC) VISIBLE,
  CONSTRAINT `fk_card_id`
    FOREIGN KEY (`card_id`)
    REFERENCES `mydb`.`card` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 146
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`checklist_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`checklist_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `checklist_id` BIGINT NOT NULL,
  `item` VARCHAR(400) NULL DEFAULT NULL,
  `is_checked` VARCHAR(1) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_checklist_item_idx` (`checklist_id` ASC) VISIBLE,
  CONSTRAINT `fk_checklist_item`
    FOREIGN KEY (`checklist_id`)
    REFERENCES `mydb`.`checklist` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 368
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `card_id` BIGINT NOT NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `comment` VARCHAR(1200) NULL DEFAULT NULL,
  `dept` INT NOT NULL,
  `group_num` BIGINT NOT NULL,
  `delete_yn` VARCHAR(1) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_coment_card1_idx` (`card_id` ASC) VISIBLE,
  INDEX `fk_coment_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_coment_card`
    FOREIGN KEY (`card_id`)
    REFERENCES `mydb`.`card` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_coment_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 183
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`files`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`files` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `card_id` BIGINT NOT NULL,
  `file_name` VARCHAR(50) NOT NULL,
  `extension` VARCHAR(10) NULL DEFAULT NULL,
  `link` VARCHAR(1500) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_file_card_idx` (`card_id` ASC) VISIBLE,
  CONSTRAINT `fk_file_card`
    FOREIGN KEY (`card_id`)
    REFERENCES `mydb`.`card` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 44
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`push_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`push_message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `sender_id` VARCHAR(45) NOT NULL,
  `target_id` VARCHAR(45) NOT NULL,
  `content` VARCHAR(1200) NULL DEFAULT NULL,
  `board_id` BIGINT NOT NULL,
  `is_read` VARCHAR(1) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 160
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
