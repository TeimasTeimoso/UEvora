-------------------Role do Departamento de Observação-------------------
CREATE ROLE "C##Departamento de Pesquisa";
GRANT READ ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT INSERT ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT DELETE ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";

GRANT UPDATE ("Nome") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Alcunha") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Data de Nascimento") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Nacionalidade") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Posição") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Pontuação Geral") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Potencial") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Melhor Pé") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Velocidade") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Finalização") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Passe") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Marcação") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";
GRANT UPDATE ("Reflexos") ON "C##DDSOFA"."Jogador" TO "C##Departamento de Pesquisa";

------------------------Role de Gestor Desportivo-----------------------
CREATE ROLE "C##Gestor Desportivo";

GRANT READ ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT INSERT ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT DELETE ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";

GRANT UPDATE ("Posição") ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT UPDATE ("Pontuação Geral") ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT UPDATE ("Potencial") ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT UPDATE ("Melhor Pé") ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT UPDATE ("Velocidade") ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT UPDATE ("Finalização") ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT UPDATE ("Passe") ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT UPDATE ("Marcação") ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";
GRANT UPDATE ("Reflexos") ON "C##DDSOFA"."Jogador" TO "C##Gestor Desportivo";

----------------------------Role de Olheiro-----------------------------
CREATE ROLE "C##Olheiro";

GRANT READ ON "C##DDSOFA"."View De Olheiro" TO "C##Olheiro";
GRANT INSERT ON "C##DDSOFA"."Jogador" TO  "C##Olheiro";

-----------------------Role de Preparador Fisico------------------------
CREATE ROLE "C##Preparador Fisico";

GRANT READ ON "C##DDSOFA"."View De Preparador Fisico" TO "C##Preparador Fisico";

GRANT UPDATE ("Posição") ON "C##DDSOFA"."Jogador" TO "C##Preparador Fisico";
GRANT UPDATE ("Pontuação Geral") ON "C##DDSOFA"."Jogador" TO "C##Preparador Fisico";
GRANT UPDATE ("Potencial") ON "C##DDSOFA"."Jogador" TO "C##Preparador Fisico";
GRANT UPDATE ("Melhor Pé") ON "C##DDSOFA"."Jogador" TO "C##Preparador Fisico";
GRANT UPDATE ("Velocidade") ON "C##DDSOFA"."Jogador" TO "C##Preparador Fisico";
GRANT UPDATE ("Finalização") ON "C##DDSOFA"."Jogador" TO "C##Preparador Fisico";
GRANT UPDATE ("Passe") ON "C##DDSOFA"."Jogador" TO "C##Preparador Fisico";
GRANT UPDATE ("Marcação") ON "C##DDSOFA"."Jogador" TO "C##Preparador Fisico";
GRANT UPDATE ("Reflexos") ON "C##DDSOFA"."Jogador" TO "C##Preparador Fisico";

------------------View com particao vertical de Olheiro-----------------
CREATE VIEW "C##DDSOFA"."View De Olheiro"
("ID", "Nome", "Alcunha") AS 
SELECT "ID", "Nome", "Data de Nascimento"
FROM "C##DDSOFA"."Jogador";

------------View com particao vertical de preparador fisico-------------
CREATE VIEW "C##DDSOFA"."View De Preparador Fisico"
("ID", "Nome", "Posição", "Pontuação Geral", "Potencial", "Melhor Pé", "Velocidade", "Finalização", "Passe", "Marcação", "Reflexos") AS 
SELECT "ID", "Nome", "Posição", "Pontuação Geral", "Potencial", "Melhor Pé", "Velocidade", "Finalização", "Passe", "Marcação", "Reflexos"
FROM "C##DDSOFA"."Jogador";