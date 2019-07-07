CREATE TABLE Tipo_Modelo(
	Modelo varchar(50),
	Marca varchar(50),
	Autonomia int,
	Lugares int,
	PRIMARY KEY (Modelo)
);

CREATE TABLE Aviao(
	Aviao_Nome varchar(50),
    Modelo varchar(50),
	PRIMARY KEY(Aviao_Nome),
    FOREIGN KEY(Modelo) REFERENCES Tipo_Modelo(Modelo) ON DELETE CASCADE
);


CREATE TABLE Voo(
	N_Voo int,
	Dia_da_Semana varchar(50),
	H_Partida varchar(50),
	H_Chegada varchar(50),
	Origem varchar(50),
	Destino varchar(50),
	N_Km int,
	PRIMARY KEY (N_Voo)
);


CREATE TABLE Funcionario(
	Nome varchar(50),
	NIF int,
	N_BI int,
	Data_Nascimento varchar(50),
	PRIMARY KEY(N_BI)
);

CREATE TABLE Piloto(
	N_Carta int,
	AnosXP int,
	NIF int,
	N_BI int,
	PRIMARY KEY(N_Carta),
	FOREIGN KEY(N_BI) REFERENCES Funcionario(N_BI) ON DELETE CASCADE
);

CREATE TABLE Hospedeira(
	Peso int,
	Altura int,
	AnoXP FLOAT,
	NIF int, 
	N_BI int,
	PRIMARY KEY(N_BI),
	FOREIGN KEY(N_BI) REFERENCES Funcionario(N_BI) ON DELETE CASCADE
);

CREATE TABLE Comissario(
	Peso int,
	Altura FLOAT,
	AnoXP int,
	NIF int, 
	N_BI int,
	PRIMARY KEY(N_BI),
	FOREIGN KEY(N_BI) REFERENCES Funcionario(N_BI) ON DELETE CASCADE

);

CREATE TABLE Administrador(
	NIF int,
	N_BI int,
	PRIMARY KEY(N_BI),
	FOREIGN KEY(N_BI) REFERENCES Funcionario(N_BI) ON DELETE CASCADE
);


CREATE TABLE Trajeto(
	N_Trajeto int,
	N_Voo int,
	Aviao_Nome varchar(50),
	PRIMARY KEY(N_Trajeto,Aviao_Nome, N_Voo),
	FOREIGN KEY (Aviao_Nome) REFERENCES Aviao(Aviao_Nome) ON DELETE CASCADE,
	FOREIGN KEY (N_Voo) REFERENCES Voo(N_Voo) ON DELETE CASCADE
);

CREATE TABLE Voo_Dados(
	N_Voo int, 
	Data varchar(50),
	N_Pessoas int,
	H_Chegada_Ext int,
	H_Partida_Ext int,
	PRIMARY KEY(N_Voo, Data),
	FOREIGN KEY(N_Voo) REFERENCES Voo(N_Voo) ON DELETE CASCADE
);

CREATE TABLE Trabalham(
	Total int, 
	N_Pilotos int,
	N_Hospedeiras int,
	N_Comissario int,
	N_Voo int,
	PRIMARY KEY(N_Voo),
	FOREIGN KEY(N_Voo) REFERENCES Voo(N_Voo) ON DELETE CASCADE
);

CREATE TABLE Func_Voo(
	N_Voo int,
	N_BI int,
	PRIMARY KEY(N_Voo, N_BI),
	FOREIGN KEY(N_Voo) REFERENCES Voo(N_Voo) ON DELETE CASCADE,
	FOREIGN KEY(N_BI) REFERENCES Funcionario(N_BI) ON DELETE CASCADE
);

CREATE TABLE RestricaoAnos(
	N_Voo int,
	MinAnos int,
	PRIMARY KEY(N_Voo, MinAnos),
	FOREIGN KEY(N_Voo) REFERENCES Voo(N_Voo) ON DELETE CASCADE
);

CREATE TABLE RestricaoPeso(
	N_Voo int,
	MinPeso int,
	PRIMARY KEY(N_Voo, MinPeso),
	FOREIGN KEY(N_Voo) REFERENCES Voo(N_Voo) ON DELETE CASCADE
);

CREATE TABLE RestricaoAltura(
	N_Voo int,
	MinAltura float,
	PRIMARY KEY(N_Voo, MinAltura),
	FOREIGN KEY(N_Voo) REFERENCES Voo(N_Voo) ON DELETE CASCADE
);


---------9-----

INSERT INTO Tipo_Modelo VALUES('737', 'Boing', 2000, 200);
INSERT INTO Tipo_Modelo VALUES('777', 'Boeing', 8000, 400);
INSERT INTO Tipo_Modelo VALUES('A318', 'Airbus', 3000, 150);
INSERT INTO Tipo_Modelo VALUES('A380', 'Airbus', 15000, 600);

-- Aviões

INSERT INTO Aviao VALUES('Aleixo Abreu', '737');
INSERT INTO Aviao VALUES('Afonso Lopes', '737');
INSERT INTO Aviao VALUES('Bartolomeu Dias', '737');
INSERT INTO Aviao VALUES('Diogo Dias', '777');
INSERT INTO Aviao VALUES('Francisco Zeimoto', '777');
INSERT INTO Aviao VALUES('Gil Eanes', 'A318');
INSERT INTO Aviao VALUES('Gomes de Sequeira', 'A318');
INSERT INTO Aviao VALUES('Gomes Pires', 'A318');
INSERT INTO Aviao VALUES('Nicolau Coelho', 'A380');

--Funcionários

-- Administradores
INSERT INTO Funcionario VALUES('Joao', 261, 1, '20/12/2018');
INSERT INTO Administrador VALUES(260, 1);  --(NIF, N_BI)
INSERT INTO Funcionario VALUES('João da Conceição', 261, 2, '20/12/2018');
INSERT INTO Administrador VALUES(261, 2);
INSERT INTO Funcionario VALUES('Pessoa1', 262, 3, '20/12/2018');
INSERT INTO Administrador VALUES(262, 3);
INSERT INTO Funcionario VALUES('Pessoa2', 263, 4, '20/12/2018');
INSERT INTO Administrador VALUES(263, 4);
INSERT INTO Funcionario VALUES('Pessoa3', 264, 5, '20/12/2018');
INSERT INTO Administrador VALUES(264, 5);
INSERT INTO Funcionario VALUES('Pessoa4', 265, 6, '20/12/2018');
INSERT INTO Administrador VALUES(265, 6);


-- Pilotos

INSERT INTO Funcionario VALUES('Pessoa5', 266, 10, '20/12/2018'); --(Nome, N_Bi, NIF, Data_Nascimento)
INSERT INTO Piloto VALUES(1230, 10, 266, 10);  --(N_Carta, AnosXP, NIF, N_BI)
INSERT INTO Funcionario VALUES('Pessoa6', 267, 11, '20/12/2018');
INSERT INTO Piloto VALUES(1231, 10, 267, 11);
INSERT INTO Funcionario VALUES('Pessoa7', 268, 12, '20/12/2018');
INSERT INTO Piloto VALUES(1232, 10, 268, 12);
INSERT INTO Funcionario VALUES('Pessoa8', 269, 13, '20/12/2018');
INSERT INTO Piloto VALUES(1233, 10, 269, 13);
INSERT INTO Funcionario VALUES('Pessoa9', 270, 14, '20/12/2018');
INSERT INTO Piloto VALUES(1234, 7, 270, 14);
INSERT INTO Funcionario VALUES('Pessoa10', 271, 15, '20/12/2018');
INSERT INTO Piloto VALUES(1235, 10, 271, 15);
INSERT INTO Funcionario VALUES('Pessoa11', 272, 16, '20/12/2018');
INSERT INTO Piloto VALUES(1236, 10, 272, 16);
INSERT INTO Funcionario VALUES('Pessoa12', 273, 17, '20/12/2018');
INSERT INTO Piloto VALUES(1237, 10, 273, 17);
INSERT INTO Funcionario VALUES('Pessoa13', 274, 18, '20/12/2018');
INSERT INTO Piloto VALUES(1238, 10, 274, 18);
INSERT INTO Funcionario VALUES('Pessoa14', 275, 19, '20/12/2018');
INSERT INTO Piloto VALUES(1239, 10, 275, 19);


-- Hospedeiras
INSERT INTO Funcionario VALUES('Pessoa15', 280, 20, '20/12/2018');
INSERT INTO Hospedeira VALUES(0, 0, 0, 280, 20);  --(Peso, Altura, AnoXP, NIF, N_BI)
INSERT INTO Funcionario VALUES('Pessoa16', 281, 21, '20/12/2018');
INSERT INTO Hospedeira VALUES(20, 0, 0, 281, 21);
INSERT INTO Funcionario VALUES('Pessoa17', 282, 22, '20/12/2018');
INSERT INTO Hospedeira VALUES(49, 0, 0, 282, 22);
INSERT INTO Funcionario VALUES('Pessoa18', 283, 23, '20/12/2018');
INSERT INTO Hospedeira VALUES(66, 0, 0, 283, 23);
INSERT INTO Funcionario VALUES('Pessoa19', 284, 24, '20/12/2018');
INSERT INTO Hospedeira VALUES(35, 0, 0, 284, 24);
INSERT INTO Funcionario VALUES('Pessoa20', 285, 25, '20/12/2018');
INSERT INTO Hospedeira VALUES(100, 0, 0, 285, 25);
INSERT INTO Funcionario VALUES('Pessoa21', 286, 26, '20/12/2018');
INSERT INTO Hospedeira VALUES(54, 0, 0, 286, 26);
INSERT INTO Funcionario VALUES('Pessoa22', 287, 27, '20/12/2018');
INSERT INTO Hospedeira VALUES(56, 0, 0, 287, 27);
INSERT INTO Funcionario VALUES('Pessoa23', 288, 28, '20/12/2018');
INSERT INTO Hospedeira VALUES(53, 0, 0, 288, 28);
INSERT INTO Funcionario VALUES('Pessoa24', 289, 29, '20/12/2018');
INSERT INTO Hospedeira VALUES(51, 0, 0, 289, 29);
INSERT INTO Funcionario VALUES('Pessoa25', 290, 30, '20/12/2018');
INSERT INTO Hospedeira VALUES(70, 0, 0, 290, 30);
INSERT INTO Funcionario VALUES('Pessoa26', 291, 31, '20/12/2018');
INSERT INTO Hospedeira VALUES(60, 0, 0, 291, 31);
INSERT INTO Funcionario VALUES('Pessoa27', 292, 32, '20/12/2018');
INSERT INTO Hospedeira VALUES(52, 0, 0, 292, 32);
INSERT INTO Funcionario VALUES('Pessoa28', 293, 33, '20/12/2018');
INSERT INTO Hospedeira VALUES(80, 0, 0, 293, 33);
INSERT INTO Funcionario VALUES('Pessoa29', 294, 34, '20/12/2018');
INSERT INTO Hospedeira VALUES(55, 0, 0, 294, 34);

-- Comissario

INSERT INTO Funcionario VALUES('Pessoa30', 295, 40, '20/12/2018');
INSERT INTO Comissario VALUES(55, 1.85, 0, 295, 40);
INSERT INTO Funcionario VALUES('Pessoa31', 296, 41, '20/12/2018');
INSERT INTO Comissario VALUES(55, 1.95, 0, 296, 41);
INSERT INTO Funcionario VALUES('Pessoa32', 297, 42, '20/12/2018');
INSERT INTO Comissario VALUES(55, 1.88, 0, 297, 42);
INSERT INTO Funcionario VALUES('Pessoa33', 298, 43, '20/12/2018');
INSERT INTO Comissario VALUES(55, 1.89, 0, 298, 43);
INSERT INTO Funcionario VALUES('Pessoa34', 299, 44, '20/12/2018');
INSERT INTO Comissario VALUES(55, 1.86, 0, 299, 44);
INSERT INTO Funcionario VALUES('Pessoa35', 300, 45, '20/12/2018');
INSERT INTO Comissario VALUES(55, 2, 0, 300, 45);
INSERT INTO Funcionario VALUES('Pessoa36', 301, 46, '20/12/2018');
INSERT INTO Comissario VALUES(55, 0, 0, 301, 46);
INSERT INTO Funcionario VALUES('Pessoa37', 302, 47, '20/12/2018');
INSERT INTO Comissario VALUES(55, 0, 0, 302, 47);
INSERT INTO Funcionario VALUES('Pessoa38', 303, 48, '20/12/2018');
INSERT INTO Comissario VALUES(55, 0, 0, 303, 48);
INSERT INTO Funcionario VALUES('Pessoa39', 304, 49, '20/12/2018');
INSERT INTO Comissario VALUES(55, 0, 0, 304, 49);



-- Voos Regulares (N_Voo, Dia_da_Semana, H_Partida, H_chegada, Origem, Destino, N_Km)
--Trabalahm(N_Pilotos, N_Hospedeiras, N_Comissario, N_voo)

-- Voos Regulares
--a) 

INSERT INTO Voo VALUES(122, 'Sábado', '10h', '13h', 'Lisboa', 'Nova Iorque');
INSERT INTO Trabalham VALUES(8, 2, 4, 2, 122);
INSERT INTO RestricaoAnos VALUES(122, 10);
INSERT INTO RestricaoPeso VALUES(122, 50);
INSERT INTO RestricaoAltura VALUES(122, 1.65);
--b
INSERT INTO Voo VALUES(124, 'Sabado', '16h', '23:30h', 'Nova Iorque', 'Lisboa', 9000);
INSERT INTO Trabalham VALUES(6, 1, 2 ,3, 124);
INSERT INTO RestricaoAnos VALUES(124, 9);
INSERT INTO RestricaoAnos VALUES(124, 5);
INSERT INTO RestricaoPeso VALUES(124, 50);
INSERT INTO RestricaoPeso VALUES(124, 70);
INSERT INTO RestricaoALtura VALUES(124, 1.65);

--c) 
INSERT INTO Voo VALUES(125, 'Domingo', '8.30h', '11h', 'Nova-Iorque', 'Lisboa', 2000);
INSERT INTO Trabalham VALUES(5, 1, 2, 1, 125);
INSERT INTO RestricaoAnos VALUES(125, 5);
INSERT INTO RestricaoPeso VALUES(125, 70);
INSERT INTO RestricaoAltura VALUEs(125, 1.65);
--d
INSERT INTO Voo VALUES(126, 'Domingo', '14h', '15h', 'Madrid', 'Lisboa', 2000);
INSERT INTO Trabalham VALUES(5, 1, 2 ,1, 126);
INSERT INTO RestricaoAnos VALUES(126, 5);
INSERT INTO RestricaoPeso VALUES(126, 70);
INSERT INTO RestricaoAltura VALUES(126, 1.65);

--e
INSERT INTO Voo VALUES(127, 'Segunda', '8h', '11h', 'Lisboa', 'Paris', 3500);
INSERT INTO Trabalham VALUES(7, 2, 4 ,1, 127);
INSERT INTO RestricaoAnos VALUES(127, 5);
INSERT INTO RestricaoPeso VALUES(127, 70);
INSERT into RestricaoAltura VALUES(127, 1.65);

--f
INSERT INTO Voo VALUES(130, 'Segunda', '12h', '13h', 'Paris', 'Bruxelas', 800);
INSERT INTO Trabalham VALUES(3, 1, 2 ,0, 130);
INSERT INTO RestricaoAnos VALUES(130, 5);
INSERT INTO RestricaoPeso VALUES(130, 70);

--g
INSERT INTO Voo VALUES(131, 'Segunda', '14h', '15h', 'Bruxelas', 'Paris', 800);
INSERT INTO Trabalham VALUES(3, 1, 2 ,0, 131);
INSERT INTO RestricaoAnos VALUES(131, 5);
INSERT into RestricaoPeso VALUES(131, 70);

--h
INSERT INTO Voo VALUES(128, 'Segunda', '18h', '22h', 'Paris', 'Lisboa', 3500);
INSERT INTO Trabalham VALUES(7, 2, 4 ,1, 128);
INSERT INTO RestricaoAnos VALUES(128, 5);
INSERT INTO RestricaoPeso VALUES(128, 70);
INSERT INTO RestricaoAltura VALUES(128, 1.65);



--Trajetos Voo_Dados(N_Voo, Data, N_pessoas, H_Chegada_Ext, H_Partida_Ext)
        -- Trajeto(N_Voo, Aviao_Nome)
        -- Func_Voo(N_Voo, N_Bi, NIF)

--a
INSERT INTO Voo_Dados VALUES(127, '3/1/2011', 310, -10, 5);
INSERT INTO Trajeto VALUES(1, 127, 'Francisco Zeimoto');
INSERT INTO Func_Voo VALUES(127, 10);
INSERT INTO Func_Voo VALUES(127, 13);
INSERT INTO Func_Voo VALUES(127, 20);
INSERT INTO Func_Voo VALUES(127, 25);
INSERT INTO Func_Voo VALUES(127, 30);
INSERT INTO Func_Voo VALUES(127, 31);
INSERT INTO Func_Voo VALUES(127, 40);
INSERT INTO Voo_Dados VALUES(128, '3/1/2011', 304, -10, 5);
INSERT INTO Trajeto VALUES(1, 128, 'Francisco Zeimoto');
INSERT INTO Func_Voo VALUES(128, 10);
INSERT INTO Func_Voo VALUES(128, 13);
INSERT INTO Func_Voo VALUES(128, 20);
INSERT INTO Func_Voo VALUES(128, 25);
INSERT INTO Func_Voo VALUES(128, 30);
INSERT INTO Func_Voo VALUES(128, 31);
INSERT INTO Func_Voo VALUES(128, 40);
INSERT INTO Voo_Dados VALUES(130, '3/1/2011', 250, -10, 5);
INSERT INTO Trajeto VALUES(1, 130, 'Francisco Zeimoto');
INSERT INTO Func_Voo VALUES(130, 10);
INSERT INTO Func_Voo VALUES(130, 13);
INSERT INTO Func_Voo VALUES(130, 20);
INSERT INTO Func_Voo VALUES(130, 25);
INSERT INTO Func_Voo VALUES(130, 30);
INSERT INTO Func_Voo VALUES(130, 31);
INSERT INTO Func_Voo VALUES(130, 40);
INSERT INTO Voo_Dados VALUES(131, '3/1/2011', 390, 5, -10);
INSERT INTO Trajeto VALUES(1, 131, 'Francisco Zeimoto');
INSERT INTO Func_Voo VALUES(131, 10);
INSERT INTO Func_Voo VALUES(131, 13);
INSERT INTO Func_Voo VALUES(131, 20);
INSERT INTO Func_Voo VALUES(131, 25);
INSERT INTO Func_Voo VALUES(131, 30);
INSERT INTO Func_Voo VALUES(131, 31);
INSERT INTO Func_Voo VALUES(131, 40);

--b
INSERT INTO Voo_Dados VALUES(122, '1/1/2011', 320, 0, 0);
INSERT INTO Trajeto VALUES(2, 122, 'Nicolau Coelho');
INSERT INTO Func_Voo VALUES(122, 11);
INSERT INTO Func_Voo VALUES(122, 12);
INSERT INTO Func_Voo VALUES(122, 21);
INSERT INTO Func_Voo VALUES(122, 22);
INSERT INTO Func_Voo VALUES(122, 33);
INSERT INTO Func_Voo VALUES(122, 34);
INSERT INTO Func_Voo VALUES(122, 48);
INSERT INTO Func_Voo VALUES(122, 49);
INSERT INTO Voo_Dados VALUES(124, '1/1/2011', 290, 0, 0);
INSERT INTO Trajeto VALUES(2, 124, 'Nicolau Coelho');
INSERT INTO Func_Voo VALUES(124, 11);
INSERT INTO Func_Voo VALUES(124, 12);
INSERT INTO Func_Voo VALUES(124, 21);
INSERT INTO Func_Voo VALUES(124, 22);
INSERT INTO Func_Voo VALUES(124, 33);
INSERT INTO Func_Voo VALUES(124, 34);
INSERT INTO Func_Voo VALUES(124, 48);
INSERT INTO Func_Voo VALUES(124, 49);

--c
INSERT INTO Voo_Dados VALUES(122, '8/1/2011', 320, 0, 0);
INSERT INTO Trajeto VALUES(3, 122, 'Nicolau Coelho');
INSERT INTO Voo_Dados VALUES(124, '8/1/2011', 290, 0, 0);
INSERT INTO Trajeto VALUES(3, 124, 'Nicolau Coelho');


--d
INSERT INTO Voo_Dados VALUES(127, '10/1/2011', 310, 0, 0);
INSERT INTO Trajeto VALUES(4, 127, 'Francisco Zeimoto');
INSERT INTO Voo_Dados VALUES(128, '10/1/2011', 304, 0, 0);
INSERT INTO Trajeto VALUES(4, 128, 'Francisco Zeimoto');
INSERT INTO Voo_Dados VALUES(130, '10/1/2011', 250, 0, 0);
INSERT INTO Trajeto VALUES(4, 130, 'Francisco Zeimoto');
INSERT INTO Voo_Dados VALUES(131, '10/1/2011', 390, 0, 0);
INSERT INTO Trajeto VALUES(4, 131, 'Francisco Zeimoto');

----------10--------
--a
SELECT DISTINCT N_Voo, Origem, Destino, H_Partida
FROM Voo
WHERE Voo.Dia_da_Semana = 'Domingo' 

--b
SELECT DISTINCT N_Voo
FROM Trajeto
WHERE Trajeto.Aviao_Nome = 'Nicolau Coelho'

--c
SELECT DISTINCT Aviao_nome, N
FROM(SELECT DISTINCT Max(Autonomia) as N
	FROM Tipo_Modelo ) as N NATURAL INNER JOIN Aviao 

--d

SELECT DISTINCT N_Voo, N
FROM(SELECT DISTINCT Max(Total) as N
	FROM Trabalham) as N NATURAL INNER JOIN Voo NATURAL INNER JOIN Trabalham
WHERE Trabalham.Total = N

--e

--f

SELECT DISTINCT N_Voo, N
FROM(SELECT DISTINCT Max(N_Pessoas) as N
	FROM Voo_Dados
	WHERE Voo_Dados.Data >= '1/1/2011' and Voo_Dados.Data <= '31/1/2011') as N NATURAL INNER JOIN Voo NATURAL INNER JOIN Voo_Dados
WHERE Voo_Dados.N_Pessoas = N

--g

--h
SELECT N_Voo, N
FROM(SELECT N_Voo, MAX(X) as N
	FROM(SELECT DISTINCT N_Voo, COUNT(H_Partida_Ext) as X
		FROM Voo_Dados
		WHERE Voo_Dados.H_Partida_Ext >0
		GROUP BY N_Voo)as A NATURAL INNER JOIN Voo) as B NATURAL INNER JOIN Voo


--i
SELECT Destino, K
FROM(SELECT DISTINCT Destino, MAX(N) as K
    FROM(SELECT DISTINCT Destino, COUNT(H_Chegada_Ext) as N
        FROM Voo_Dados
        WHERE Voo_Dados.H_Chegada_Ext < 0
        GROUP BY N_Voo) as X
    GROUP BY Destino)



--j
SELECT DISTINCT N_Voo, Total, N_Pessoas
FROM Voo NATURAL INNER JOIN Trabalham NATURAL INNER JOIN Voo_Dados

--k
SELECT DISTINCT N_Voo, K
FROM(SELECT Max(N_Pessoas) as K
    FROM Voo_Dados) as  N NATURAL INNER JOIN Voo NATURAL INNER JOIN Voo_Dados
WHERE Voo_Dados.N_Pessoas = K

