## Use Case: Marcar Férias
**Main Actor:** Colaborador  
### Sucess Case:  
**1.** O colaborador efetua o login;  
**2.** É levado para o menu;  
**3.** Seleciona "Marcar férias";  
**4.** O colaborador seleciona no sistema o periodo de férias que pretende marcar;  
**5.** O colaborador submete o pedido de férias;  
**6.** O sistema notifica o gestor do aparecimento de um novo pedido de férias;  
**7.** O gestor aprova o pedido de férias;  
**8.** O sistema notifica o colaborador de que o seu pedido de férias foi aprovado.

### Insucess Cases:
#### 1.a.
   1. As credenciais introduzidas são rejeitadas; 
   2. O login falha;
   3. Volta ao ponto 1.

#### 4.a.
   1. Já existe um colaborador a tirar férias nesse periodo;
   2. Volta ao ponto 3.

#### 5.a
   1. O pedido de férias faz com que o colaborador ultrapasse os seus dias uteis de férias;
   2. O sistema avisa o colaborador que já excedeu o limite de dias;
   3. Volta ao ponto 4.

#### 7.a
   1. O gestor não aprova o pedido de férias;
   2. Volta ao ponto 3.

## Use Case: Consultar Pedidos
**Main Actor:** Colaborador 

### Sucess Case:
**1.** O colaborador efetua o login;  
**2.** É levado para o menu;  
**3.** Seleciona "Consultar Pedidos";  
**4.** O sistema mostra-lhe os seus pedidos.

### Insucess Cases:
#### 1.a.
   1. As credenciais introduzidas são rejeitadas; 
   2. O login falha;
   3. Volta ao ponto 1.

#### 4.a
   1. O sistema notifica o colaborador de que não tem qualquer pedido efetuado;
   2. Volta ao ponto 2.

## Use Case: Cancelar Férias
**Main Actor:** Colaborador

### Sucess Case:
**1.** O colaborador efetua o login;  
**2.** É levado para o menu;  
**3.** Seleciona "Cancelar Férias";  
**4.** O colaborador cancela os periodos que pretende;  
**5.** O sistema devolve ao colaborador o periodo de férias que cancelou;  

### Insucess Cases:
#### 1.a.
   1. As credenciais introduzidas são rejeitadas; 
   2. O login falha;
   3. Volta ao ponto 1.

#### 3.a.
   1. O sistema notifica o colaborador de que não tem qualquer pedido efetuado/aprovado;
   2. Volta ao ponto 2.

## Use Case: Consultar Aprovações
**Main Actor:** Recursos Humanos

### Sucess Case:
**1.** O colaborador dos Recursos Humanos efetua o login;  
**2.** Consulta os pedidos aprovados pelo gestor;  
**3.** Verifica a validade dos pedidos bem como o número de férias;  

### Insucess Cases:
#### 1.a.
   1. As credenciais introduzidas são rejeitadas; 
   2. O login falha;
   3. Volta ao ponto 1.

#### 2.a.
   1. O sistema notifica o colaborador dos Recursos Humanos de que não existem pedidos aprovados;
   2. Volta ao ponto 2.

#### 3.a.
   1. O pedido aprovado não respeita alguma norma;
   2. Os Recursos Humanos notificam o gestor.