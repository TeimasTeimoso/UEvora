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
    FOREIGN KEY (NomeE) REFERENCES Especie(NomeE) ON DELETE CASCADE
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
    FOREIGN KEY (NomeA) REFERENCES Animal(NomeA) ON DELETE CASCADE,
    FOREIGN KEY (IdComp) REFERENCES Compartimento(IdComp) ON DELETE CASCADE
);

--Chave Candidata: {IdComp, NCC}
--Chave Primária: {IdComp, NCC}
--Chave Estrangeira: {IdComp}
--Chave Estrangeira: {NCC}
CREATE TABLE Trata(
    IdComp int,
    NCC int,
    PRIMARY KEY (IdComp, NCC),
    FOREIGN KEY (IdComp) REFERENCES Compartimento(IdComp) ON DELETE CASCADE,
    FOREIGN KEY (NCC) REFERENCES Tratador(NCC) ON DELETE CASCADE
);

/*
  Exercico 3 + 4
*/

--a

INSERT INTO Especie VALUES('Foca', 'Mamifero');
INSERT INTO Animal VALUES ('Kiki', 'Feminina', 'Foca');
INSERT INTO Compartimento VALUES(23, 'charco');
INSERT INTO Alojado VALUES ('Kiki', 23);

--b

INSERT INTO Animal VALUES ('Lola', 'Feminina', 'Foca');
INSERT INTO Alojado VALUES('Lola', 23);

--c

INSERT INTO Especie VALUES('Leão Marinho', 'Mamifero');
INSERT INTO Animal VALUES('Anibal', 'Masculino', 'Leão Marinho');
INSERT INTO Alojado VALUES('Anibal', 23);

--d

INSERT INTO Especie VALUES('Lontra', 'Mamifero');
INSERT INTO Animal VALUES('Amalia', 'Feminino', 'Lontra');
INSERT INTO Alojado VALUES('Amalia', 23);

--e

INSERT INTO Animal VALUES('Eusebio', 'Masculino', 'Lontra');
INSERT INTO Alojado VALUES('Eusebio', 23);

--f

INSERT INTO Tratador VALUES(123, 'Manuel', 750, 124);
INSERT INTO Compartimento VALUES(10, 'selva');
INSERT INTO Trata VALUES (10, 123);
INSERT INTO Trata VALUES (23, 123);

--g

INSERT INTO Tratador VALUES(124, 'Luis', 850, null);
INSERT INTO Compartimento VALUES(8, 'pantano');
INSERT INTO Trata VALUES(10, 124);
INSERT INTO Trata VALUES(8, 124);

--h

INSERT INTO Especie VALUES('Tigre', 'Mamifero');
INSERT INTO Animal VALUES('Jau', 'Masculino', 'Tigre');
INSERT INTO Alojado VALUES('Jau', 10);

--i

INSERT INTO Animal VALUES('Princesa', 'Feminino', 'Tigre');
INSERT INTO Alojado VALUES('Princesa', 10);

--j

INSERT INTO Especie VALUES('Tartaruga', 'Reptil');
INSERT INTO Animal VALUES('Huga', 'Feminino', 'Tartaruga');
INSERT INTO Alojado VALUES('Huga', 8);

--k

INSERT INTO Animal VALUES('Luna', 'Feminino', 'Tartaruga');
INSERT INTO Alojado VALUES('Luna', 8);

--l

INSERT INTO Especie VALUES('lagartixa', 'Reptil');
INSERT INTO Animal VALUES('Brava', 'Feminino', 'lagartixa');
INSERT INTO Alojado VALUES('Brava', 8);

--m

INSERT INTO Especie VALUES('Lagarto', 'Reptil');
INSERT INTO Animal VALUES('Raul', 'Masculino', 'Lagarto');
INSERT INTO Alojado VALUES('Raul', 8);

--n

INSERT INTO Tratador VALUES(125, 'Maria', 850, 124);
INSERT INTO Compartimento VALUES(15, 'gaiola');
INSERT INTO Trata VALUES(8, 125);
INSERT INTO Trata VALUES(15, 125);

--0

INSERT INTO Especie VALUES('papagaio', 'Ave');
INSERT INTO Animal VALUES('pirata', 'Masculino', 'papagaio');
INSERT INTO Alojado VALUES('pirata', 15);

--p

INSERT INTO Animal VALUES('bela', 'Feminino', 'papagaio');
INSERT INTO Alojado VALUES('bela', 15);

--q 

INSERT INTO Especie VALUES('arara', 'Ave');
INSERT INTO Animal VALUES('Joia', 'Feminino', 'arara');
INSERT INTO Alojado VALUES('Joia', 15);

--4a

INSERT INTO Especie VALUES('Golfinho', 'Mamifero');
INSERT INTO Animal VALUES('Martim', 'Masculino', 'Golfinho');
INSERT INTO Compartimento VALUES(44, 'tanque');
INSERT INTO Alojado VALUES ('Martim', 44);

--4b

INSERT INTO Especie VALUES('Javali', 'Mamifero');
INSERT INTO Animal VALUES('Pumba', 'masculino', 'Javali');
INSERT INTO Alojado VALUES('Pumba', 10);

--4c

INSERT INTO Especie VALUES('Leao', 'Mamifero');
INSERT INTO Animal VALUES('Simba', 'Masculino', 'Leao');
INSERT INTO Alojado VALUES('Simba', 10);

--4d

INSERT INTO Tratador VALUES(001, 'Walt Disney', 10000, null);
INSERT INTO Trata VALUES(10, 001);
