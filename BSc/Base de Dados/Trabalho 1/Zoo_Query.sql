/*
    Exercicio 5
*/

--a
SELECT NomeE
FROM Especie
-- AL
--π NomeE Especie


--b
/*
    Distinct para que não hajam duplicados no resultado da query
*/
SELECT DISTINCT Classe
FROM Especie NATURAL INNER JOIN Tratador NATURAL INNER JOIN Trata 
WHERE Tratador.NomeT = 'Manuel'


-- AL
--π Classe Especie ⨝ Tratador ⨝ Tratador.NomeT = 'Manuel' Trata

--c 
SELECT DISTINCT NomeT
FROM Alojado NATURAL INNER JOIN Animal NATURAL INNER JOIN Especie NATURAL INNER JOIN Trata NATURAL INNER JOIN Tratador
WHERE Especie.Classe = 'Reptil' and Tratador.NCCChefe is null

--d
SELECT DISTINCT IdComp
FROM Alojado NATURAL INNER JOIN Animal NATURAL INNER JOIN Especie
EXCEPT
SELECT DISTINCT IdComp
FROM Alojado NATURAL INNER JOIN Animal NATURAL INNER JOIN Especie
WHERE Especie.Classe = 'Ave'

--e
/*
    Interpretado como os compartimentos que têm mamiferos e repteis (compartimentos separados e não ambos só num)
*/
SELECT DISTINCT IdComp
FROM Alojado NATURAL INNER JOIN Animal NATURAL INNER JOIN Especie
WHERE Especie.Classe = 'Mamifero'
UNION
SELECT DISTINCT IdComp
FROM Alojado NATURAL INNER JOIN Animal NATURAL INNER JOIN Especie
WHERE Especie.Classe = 'Reptil'

--f
SELECT DISTINCT NomeT
FROM Tratador
EXCEPT
SELECT DISTINCT NomeT
FROM Compartimento NATURAL INNER JOIN Tratador NATURAL INNER JOIN Trata
WHERE Compartimento.Tipo = 'gaiola'

INTERSECT 

SELECT DISTINCT NomeT
FROM Tratador
EXCEPT
SELECT DISTINCT NomeT
FROM Compartimento NATURAL INNER JOIN Tratador NATURAL INNER JOIN Trata
WHERE Compartimento.Tipo = 'pantano'

--g

SELECT DISTINCT count(nomee)
FROM animal NATURAL INNER JOIN especie
WHERE especie.Classe = 'Mamifero'

--h
SELECT IdComp, COUNT(IdComp)
FROM Alojado
GROUP BY(IdComp)

--i
SELECT DISTINCT NCC, COUNT(NomeA)
FROM Trata NATURAL INNER JOIN Alojado NATURAL INNER JOIN Animal NATURAL INNER JOIN Especie
WHERE Especie.Classe = 'Mamifero'
GROUP BY (NCC)

--j
SELECT DISTINCT NomeE
FROM Especie as E NATURAL INNER JOIN Alojado
WHERE NOT EXISTS(
	SELECT IdComp
	FROM Alojado
	EXCEPT
	SELECT IdComp
	FROM Especie NATURAL INNER JOIN Alojado NATURAL INNER JOIN Animal
	WHERE Especie.NomeE = E.NomeE
)

--k

--l
SELECT MAX(x) as maxi, IdComp
FROM (SELECT IdComp, COUNT(NomeA) as x
	FROM animal NATURAL INNER JOIN Alojado
	WHERE Animal.Genero = 'Feminino'
	Group BY (IdComp)) AS N
GROUP BY (IdComp)
ORDER BY maxi desc
limit 1

--m
DELETE FROM Especie WHERE Classe = 'Reptil'

--n
select DISTINCT COUNT(NomeA), IdComp
FROM Especie NATURAL INNER JOIN Alojado NATURAL INNER JOIN Animal
WHERE Especie.Classe = 'Ave'
GROUP BY (IdComp)

--o
SELECT DISTINCT NomeT, Tipo, SUM(Salário)
FROM Trata NATURAL INNER JOIN Tratador NATURAL INNER JOIN Compartimento
WHERE Compartimento.Tipo = 'charco' 
GROUP BY (NomeT, Tipo)






