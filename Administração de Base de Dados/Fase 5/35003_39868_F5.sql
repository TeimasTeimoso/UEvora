--Query 1

SELECT "Jogador"."Nome", "Altura", "Velocidade", "Equipa"."Nome" AS "Equipa"
FROM "Jogador" JOIN "Estatura" ON "Jogador"."ID" = "Estatura"."ID"
JOIN "Contrato" ON "Contrato"."ID" = "Jogador".ID
JOIN "Equipa" ON "Equipa"."Nome"  =  "Contrato"."Equipa"
WHERE "Jogador"."Velocidade" > 80 AND "Estatura"."Altura" > 1.7 AND "Equipa"."País" = 'Portugal'
AND "Jogador"."Nacionalidade" IN ('Portugues','Brasileiro');

--Query 2

SELECT *
FROM(   SELECT "Jogador"."ID", "Nome", FLOOR(MONTHS_BETWEEN(TRUNC(SYSDATE),"Data de Nascimento")/12) AS "Idade", "Pontuação Geral", "Salario" 
        FROM "Jogador" JOIN "Contrato" ON "Jogador"."ID" = "Contrato"."ID"
        WHERE "Contrato"."Salario" < 10 AND "Jogador"."Potencial" > 84 AND "Jogador"."Posição" = 'Ponta de Lanca')
WHERE "Idade" < 25;


--Atualização de estatisticas
begin
DBMS_STATS.GATHER_TABLE_STATS(
            ownname => '"C##DDSOFA"',
                    tabname => '"Jogador"',
                estimate_percent => dbms_stats.auto_sample_size
            );
end;

begin
DBMS_STATS.GATHER_TABLE_STATS(
            ownname => '"C##DDSOFA"',
                    tabname => '"Contrato"',
                estimate_percent => dbms_stats.auto_sample_size
            );
end;

begin
DBMS_STATS.GATHER_TABLE_STATS(
            ownname => '"C##DDSOFA"',
                    tabname => '"Equipa"',
                estimate_percent => dbms_stats.auto_sample_size
            );
end;

select TABLE_NAME "Tabela", NUM_ROWS, BLOCKS, AVG_ROW_LEN, TO_CHAR(LAST_ANALYZED, 'MM-DD-YYYY HH24:MI:SS') FROM "USER_TABLES"
where TABLE_NAME IN('Jogador', 
                    'Estatura', 
                    'Contrato');
        
--Index1        
CREATE INDEX idx_jogador_data ON "Jogador"("Data de Nascimento");

DROP INDEX idx_jogador_data;

--Index2
CREATE INDEX idx_jogador_pos ON "Jogador"("Posição");

DROP INDEX idx_jogador_pos;

--Index3
CREATE INDEX idx_jogador_potencial ON "Jogador"("Potencial");

DROP INDEX idx_jogador_potencial;

--Index4
CREATE INDEX idx_contrato_sal ON "Contrato"("Salario");

DROP INDEX idx_contrato_sal;

--Index5
CREATE INDEX idx_equipa_pais ON "Equipa"("País");

DROP INDEX idx_equipa_pais;

--Index6
CREATE INDEX idx_equipa_nome ON "Equipa"("Nome");
CREATE INDEX idx_contrato_equipa ON "Contrato"("Equipa");

DROP INDEX idx_contrato_equipa;
DROP INDEX idx_equipa_nome;