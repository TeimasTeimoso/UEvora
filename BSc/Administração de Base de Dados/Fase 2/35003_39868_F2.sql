------------- USER -------------
CREATE USER "C##DDSOFA" IDENTIFIED BY "qwerty123";
GRANT "DBA" TO "C##DDSOFA" WITH ADMIN OPTION;

------------- Tablespace -------------
CREATE TABLESPACE FIFA_DADOS DATAFILE 'D:\Oracle\FIFA_DADOS.dbf' SIZE 10G autoextend on;

------------- País -------------
CREATE TABLE "C##DDSOFA"."País" (
    "País" VARCHAR2(60 CHAR),
    "Nacionalidade" VARCHAR2 (30 CHAR),
    "Bandeira" BLOB NOT NULL,
    CONSTRAINT cp_País PRIMARY KEY ("País", "Nacionalidade"),
    CONSTRAINT nacionalidade_unica UNIQUE ("Nacionalidade"),
    CONSTRAINT país_unico UNIQUE ("País")
) TABLESPACE FIFA_DADOS;

------------- Jogador -------------
CREATE TABLE "C##DDSOFA"."Jogador" (
    "ID" NUMBER(6),
    "Nome" VARCHAR2(40 CHAR) NOT NULL,
    "Alcunha" VARCHAR2 (40 CHAR),
    "Data de Nascimento" DATE NOT NULL,
    "Nacionalidade" VARCHAR2 (30 CHAR) NOT NULL,
    "Posição" VARCHAR2 (20 CHAR) NOT NULL,
    "Pontuação Geral" NUMBER (3) NOT NULL,
    "Potencial" NUMBER (3) NOT NULL,
    "Melhor Pé" VARCHAR2 (1 BYTE) NOT NULL,
    "Velocidade" NUMBER (3) NOT NULL,
    "Finalização" NUMBER (3) NOT NULL,
    "Passe" NUMBER (3) NOT NULL,
    "Marcação" NUMBER (3) NOT NULL,
    "Reflexos" NUMBER (3) NOT NULL,
    CONSTRAINT verifica_id CHECK ("ID" BETWEEN 00001 and 999999),
    CONSTRAINT verifica_pontuacao CHECK ("Pontuação Geral" BETWEEN 0 and 100),
    CONSTRAINT verifica_potencial CHECK ("Potencial" BETWEEN "Pontuação Geral" and 100),
    CONSTRAINT verifica_pé CHECK ("Melhor Pé" IN ('D', 'E', 'A')),
    CONSTRAINT verifica_velocidade CHECK ("Velocidade" BETWEEN 0 and 100),
    CONSTRAINT verifica_finalizacao CHECK ("Finalização" BETWEEN 0 and 100),
    CONSTRAINT verifica_passe CHECK ("Passe" BETWEEN 0 and 100),
    CONSTRAINT verifica_marcacao CHECK ("Marcação" BETWEEN 0 and 100),
    CONSTRAINT verifica_reflexos CHECK ("Reflexos" BETWEEN 0 and 100),
    CONSTRAINT cp_jogador PRIMARY KEY ("ID"),
    CONSTRAINT ce_jogador FOREIGN KEY ("Nacionalidade") REFERENCES "C##DDSOFA"."País" ("Nacionalidade")
) TABLESPACE FIFA_DADOS;

------------- Equipa -------------
CREATE TABLE "C##DDSOFA"."Equipa" (
    "Nome" VARCHAR2(120) NOT NULL,
    "Sigla" VARCHAR2(3) NOT NULL,
    "Denominação" VARCHAR2(120) NOT NULL,
    "Emblema" BLOB NOT NULL,
    "País" VARCHAR2(60) NOT NULL,
    CONSTRAINT cp_equipa PRIMARY KEY ("Nome"),
    CONSTRAINT ce_equipa FOREIGN KEY("País") REFERENCES "C##DDSOFA"."País"("País")
) TABLESPACE FIFA_DADOS;

------------- Estatura -------------
CREATE TABLE "C##DDSOFA"."Estatura" (
    "ID" NUMBER(6),
    "Altura" NUMBER(3, 2) NOT NULL,
    "Peso" NUMBER(3) NOT NULL,
    CONSTRAINT verifica_id_estatura CHECK("ID" BETWEEN 00001 and 999999),
    CONSTRAINT verifica_altura CHECK("Altura" BETWEEN 1.50 and 2.10),
    CONSTRAINT verifica_peso CHECK("Peso" BETWEEN 60 and 110),
    CONSTRAINT cp_estatura PRIMARY KEY("ID"),
    CONSTRAINT ce_estatura FOREIGN KEY("ID") REFERENCES "C##DDSOFA"."Jogador"("ID")
) TABLESPACE FIFA_DADOS;

------------- Contrato -------------
CREATE TABLE "C##DDSOFA"."Contrato" (
    "ID" NUMBER(6),
    "Equipa" VARCHAR2(120 CHAR),
    "Clausula" NUMBER (3) NOT NULL,
    "Salario" NUMBER (3) NOT NULL,
    "Inicio" DATE,
    "Fim" DATE NOT NULL,
    "Emprestado a" VARCHAR2 (120 CHAR),
    CONSTRAINT verifica_id_contrato CHECK ("ID" BETWEEN 00001 and 999999),
    CONSTRAINT cp_contrato PRIMARY KEY ("ID", "Equipa", "Inicio"),
    CONSTRAINT ce_contrato_id FOREIGN KEY ("ID") REFERENCES "C##DDSOFA"."Jogador" ("ID"),
    CONSTRAINT ce_contrato_equipa FOREIGN KEY ("Equipa") REFERENCES "C##DDSOFA"."Equipa" ("Nome"),
    CONSTRAINT ce_contrato_emprestado FOREIGN KEY ("Emprestado a") REFERENCES "C##DDSOFA"."Equipa" ("Nome"),
    CONSTRAINT cont_verifica_fim CHECK ("Fim" > "Inicio")
) TABLESPACE FIFA_DADOS;

------------- Contrato Emprestimo -------------
CREATE TABLE "C##DDSOFA"."Contrato de Emprestimo" (
    "ID" NUMBER(6),
    "Equipa" VARCHAR2(120 CHAR),
    "Clausula" NUMBER (3) NOT NULL,
    "Salario" NUMBER (3) NOT NULL,
    "Inicio" DATE,
    "Fim" DATE NOT NULL,
    "Emprestado de" VARCHAR2 (120 CHAR) NOT NULL,
    CONSTRAINT verifica_id_contrato_emp CHECK ("ID" BETWEEN 00001 and 999999),
    CONSTRAINT cp_contratoEmp PRIMARY KEY ("ID", "Equipa", "Inicio"),
    CONSTRAINT ce_contratoEmp_id FOREIGN KEY ("ID") REFERENCES "C##DDSOFA"."Jogador" ("ID"),
    CONSTRAINT ce_contratoEmp_equipa FOREIGN KEY ("Equipa") REFERENCES "C##DDSOFA"."Equipa" ("Nome"),
    CONSTRAINT ce_contratoEmp_emprestado FOREIGN KEY ("Emprestado de") REFERENCES "C##DDSOFA"."Equipa" ("Nome"),
    CONSTRAINT contemp_verifica_fim CHECK ("Fim" > "Inicio")
) TABLESPACE FIFA_DADOS;