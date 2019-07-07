DROP OWNED BY ruben;

/*
  Exercicio 1 + 2
*/

--Chave Candidata: {NomeE}
--Chave Primária: {NomeE}
CREATE TABLE Especie(
    NomeE varchar(30),
    Classe varchar(15),
    PRIMARY KEY (NomeE)
);

--Chave Candidata: {NomeA}
--Chave Primária: {NomeA}
--Chave Estrangeira: {NomeE}
CREATE TABLE Animal(
    NomeA varchar(30),
    Genero varchar(10),
    NomeE varchar(30),
    PRIMARY KEY (NomeA),
    FOREIGN KEY (NomeE) REFERENCES Especie(NomeE) ON DELETE RESTRICT
);

--Chave Candidata: {IdComp}
--Chave Primária: {IdComp}
CREATE TABLE Compartimento(
    IdComp int,
    Tipo varchar(15),
    PRIMARY KEY (IdComp)
);

--Chave Candidata: {NCC}, {NCCChefe}
--Chave Primária: {NCC}

CREATE TABLE Tratador(
    NCC int,
    NomeT varchar (30),
    Salário real,
    NCCChefe int,
    PRIMARY KEY (NCC)
);

--Chave Candidata: {NomeA, IdComp}
--Chave Primária: {NomeA, IdComp}
--Chave Estrangeira: {IdComp}
--Chave Estrangeira: {NomeA}
CREATE TABLE Alojado(
    NomeA varchar (30),
    IdComp int,
    PRIMARY KEY (NomeA, IdComp),
    FOREIGN KEY (NomeA) REFERENCES Animal(NomeA) ON DELETE RESTRICT,
    FOREIGN KEY (IdComp) REFERENCES Compartimento(IdComp) ON DELETE RESTRICT
);

--Chave Candidata: {IdComp, NCC}
--Chave Primária: {IdComp, NCC}
--Chave Estrangeira: {IdComp}
--Chave Estrangeira: {NCC}
CREATE TABLE Trata(
    IdComp int,
    NCC int,
    PRIMARY KEY (IdComp, NCC),
    FOREIGN KEY (IdComp) REFERENCES Compartimento(IdComp) ON DELETE RESTRICT,
    FOREIGN KEY (NCC) REFERENCES Tratador(NCC) ON DELETE RESTRICT
);

/*
  Exercico 3 + 4
*/

--a

INSERT INTO Especie VALUES('Foca', 'Mamifero');
INSERT INTO Animal VALUES ('Kiki', null, 'Foca');
INSERT INTO Compartimento VALUES(23, 'charco');
INSERT INTO Alojado VALUES ('Kiki', 23);

--b

INSERT INTO Animal VALUES ('Lola', null, 'Foca');
INSERT INTO Compartimento VALUES(23, null);
INSERT INTO Alojado VALUES('Lola', 23);

--c

INSERT INTO Especie VALUES('Leão Marinho', 'Mamifero');
INSERT INTO Animal VALUES('Anibal', null, 'Leão Marinho');
INSERT INTO Compartimento VALUES('23', null);
INSERT INTO Alojado VALUES('Anibal', 23);

--d

INSERT INTO Especie VALUES('Lontra', 'Mamifero');
INSERT INTO Animal VALUES('Amalia', null, 'Lontra');
INSERT INTO Compartimento VALUES(23, null);
INSERT INTO Alojado VALUES('Amalia', 23);

--e
INSERT INTO Especie VALUES('Lontra', 'Mamifero');
INSERT INTO Animal VALUES('Eusebio', 'Masculino', 'Lontra');
INSERT INTO Compartimento VALUES(23, null);
INSERT INTO Alojado VALUES('Eusebio', 23);

--f

INSERT INTO Tratador VALUES(123, 'Manuel', 750, 124);
INSERT INTO Compartimento VALUES(23, null);
INSERT INTO Compartimento VALUES(10, null);
INSERT INTO Trata VALUES (10, 123);
INSERT INTO Trata VALUES (23, 123);

--g

INSERT INTO Tratador VALUES(124, 'Luis', 850, null);
INSERT INTO Compartimento VALUES(10,null);
INSERT INTO Compartimento VALUES(8,  null);
INSERT INTO Trata VALUES(10, 124);
INSERT INTO Trata VALUES(8, 124);

--h

INSERT INTO Especie VALUES('Tigre', 'Mamifero');
INSERT INTO Animal VALUES('Jau', null, 'Tigre');
INSERT INTO Compartimento VALUES(10, 'selva');
INSERT INTO Alojado VALUES('Jau', 10);

--i

INSERT INTO Especie VALUES('Tigre', 'Mamifero');
INSERT INTO Animal VALUES('Princesa', 'Feminino', 'Tigre');
INSERT INTO Compartimento VALUES(10, null);
INSERT INTO Alojado VALUES('Princesa', 10);

--j

INSERT INTO Especie VALUES('Tartaruga', 'Reptil');
INSERT INTO Animal VALUES('Huga', null, 'Tartaruga');
INSERT INTO Compartimento VALUES(8, 'pantano');
INSERT INTO Alojado VALUES('Huga', 8);

--k

INSERT INTO Especie VALUES('Tartaruga', 'Reptil');
INSERT INTO Animal VALUES('Luna', null, 'Tartaruga');
INSERT INTO Compartimento VALUES(8, null);
INSERT INTO Alojado VALUES('Luna', 8);

--l

INSERT INTO Especie VALUES('lagartixa', 'Reptil');
INSERT INTO Animal VALUES('Brava', null, 'lagartixa');
INSERT INTO Compartimento VALUES(8, null);
INSERT INTO Alojado VALUES('Brava', 8);

--m

INSERT INTO Especie VALUES('Lagarto', 'Reptil');
INSERT INTO Animal VALUES('Raul', null, 'Lagarto');
INSERT INTO Compartimento VALUES(8, null);
INSERT INTO Alojado VALUES('Raul', 8);

--n

INSERT INTO Tratador VALUES(125, 'Maria', 850, 124);
INSERT INTO Compartimento VALUES(8, null);
INSERT INTO Compartimento VALUES(15, null);
INSERT INTO Trata VALUES(8, 125);
INSERT INTO Trata VALUES(15, 125);

--o

INSERT INTO Especie VALUES('Papagaio', 'Ave');
INSERT INTO Animal VALUES('Pirata', null, 'Papagaio');
INSERT INTO Compartimento VALUES(15, 'gaiola');
INSERT INTO Alojado VALUES('Pirata', 15);

--p

INSERT INTO Animal VALUES('Bela', null, 'Papagaio');
INSERT INTO Alojado VALUES('Bela', 15);

--q

INSERT INTO Especie VALUES('Arara', 'Ave');
INSERT INTO Animal VALUES('Joia', null, 'Arara');
INSERT INTO Alojado VALUES('Joia', 15);
