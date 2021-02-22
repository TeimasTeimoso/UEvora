/* −−−−−−−−−−−−− TABLES −−−−−−−−−−−−− */
CREATE TABLE produto (
    nome varchar(50) NOT NULL,

    CONSTRAINT cp_produto PRIMARY KEY (nome)
);

CREATE TABLE disponibilidade (
    nome_loja varchar(50) NOT NULL,
    produto varchar(50) NOT NULL,
    data_encomenda date,
    hora_encomenda time,
    stock integer,

    CONSTRAINT cp_loja PRIMARY KEY (nome_loja, produto),
    CONSTRAINT ce_loja FOREIGN KEY(produto) REFERENCES produto (nome)
);

CREATE TABLE procura (
    id_procura SERIAL NOT NULL,
    id_cliente varchar(20) NOT NULL,
    produto varchar(50) NOT NULL,
    data_procura date,
    hora_procura time,
    resposta_recebida boolean,

    CONSTRAINT cp_encomenda PRIMARY KEY (id_procura),
    CONSTRAINT ce_encomenda FOREIGN KEY(produto) REFERENCES produto (nome)
);


/*
-- −−−−−−−−−−−−− querys −−−−−−−−−−−−−

-- Para usar na 1a insercao de cada produto
INSERT INTO produto VALUES ('nome_produto');


-- Verificar se certa disponibilidade foi remortada anteriormente 
select * from disponibilidade WHERE produto = '%s' AND nome_loja = '%s'; --, produto, loja));

-- Registar nava disponibilidade
INSERT INTO disponibilidade VALUES ('%s', '%s', CURRENT_DATE, CURRENT_TIME, '%d'); --, loja, produto, stock));

-- Update da diponibilidade
UPDATE disponibilidade SET data_encomenda = CURRENT_DATE, hora_encomenda = CURRENT_TIME, stock = '%d' WHERE nome_loja = '%s' AND produto = '%s'; --, stock, loja, produto));


-- Registar a necessidade
INSERT INTO procura (id_cliente, produto, data_procura, hora_procura, resposta_recebida) VALUES ('%s', '%s', CURRENT_DATE, CURRENT_TIME, FALSE) RETURNING id_procura; --, id, produto));

-- Resgistar notificacao como recebida
UPDATE procura SET resposta_recebida = TRUE WHERE produto = '%s' AND resposta_recebida = FALSE; --, produto));

*/