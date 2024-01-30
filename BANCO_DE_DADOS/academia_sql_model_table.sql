
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ACADEMIA_UEMA` DEFAULT CHARACTER SET utf8 ;
USE `ACADEMIA_UEMA` ;

-- -----------------------------------------------------
-- Table `mydb`.`table1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PESSOA` (
 `idPessoa` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Sexo` ENUM('F', 'M', 'NB', 'O') NOT NULL,
  `Data_de_Nascimento` DATE NOT NULL,
  `Telefone` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`idPessoa`),
  UNIQUE INDEX `idPessoa_UNIQUE` (`idPessoa` ASC) VISIBLE
)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Funcionario` (
  `idFuncionario` INT NOT NULL AUTO_INCREMENT,
   `Profisão` VARCHAR(45) NOT NULL,
   `Data de Cadastro` DATE NOT NULL,
  PRIMARY KEY (`idFuncionario`),
  UNIQUE INDEX `idFuncionario_UNIQUE` (`idFuncionario` ASC) VISIBLE,
  INDEX `fk_Funcionario_1_idx` (`IdFuncionario` ASC) VISIBLE,
  CONSTRAINT `fk_Funcionario_1`
    FOREIGN KEY (`IdFuncionario`)
    REFERENCES `PESSOA` (`idPessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`Turmas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Turmas` (
  `idTurma` INT NOT NULL AUTO_INCREMENT,
  `idFuncionario` INT NOT NULL,
  `idAluno` INT NOT NULL,
  `Horario` VARCHAR(7) NOT NULL,
  PRIMARY KEY (`idTurma`),
  UNIQUE INDEX `idTurma_UNIQUE` (`idTurma` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Alunos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Alunos` (
  `idAluno` INT NOT NULL AUTO_INCREMENT,
  `Discente` TINYINT NOT NULL,
  `ValordaMensalidade` DECIMAL(5) NOT NULL,
  PRIMARY KEY (`idAluno`),
  UNIQUE INDEX `idAlunos_UNIQUE` (`idAluno` ASC) VISIBLE,
  INDEX `fk_Aluno_1_idx` (`IdAluno` ASC) VISIBLE,
  CONSTRAINT `fk_Aluno_1`
    FOREIGN KEY (`IdAluno`)
    REFERENCES `PESSOA` (`idPessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

----------------------------------------------------
-- Table `mydb`.`Agendamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Agendamentos` (
  `idAgendamentos` INT NOT NULL AUTO_INCREMENT,
  `idAluno` INT NOT NULL,
  `Data` DATE NOT NULL,
  `TipoAgendamento` ENUM('NUTRICAO', 'AVALIADOR_FISICO') NOT NULL,
  PRIMARY KEY (`idAgendamentos`),
  UNIQUE INDEX `idAgendamentos_UNIQUE` (`idAgendamentos` ASC) VISIBLE,
  CONSTRAINT `alunos_fk`
    FOREIGN KEY (`idAluno`)
    REFERENCES `Alunos` (`idAluno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`Frequencia_Alunos_UEMA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Frequencia_Alunos` (
  `idFrequencia_Alunos` INT NOT NULL,
  `idAluno` INT NOT NULL,
  `IdTurma` INT NOT NULL,
  `Data_da_Aula` DATE NOT NULL,
  `Aluno_Presente` ENUM('Sim', 'Não') NOT NULL,
  PRIMARY KEY (`idFrequencia_Alunos`),
  UNIQUE INDEX `idFrequencia_Alunos_UNIQUE` (`idFrequencia_Alunos` ASC) VISIBLE,
  INDEX `fk_Frequencia_Alunos_1_idx` (`idAluno` ASC) VISIBLE,
  CONSTRAINT `fk_Frequencia_Alunos_1`
    FOREIGN KEY (`idAluno`)
    REFERENCES `Alunos` (`idAluno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
