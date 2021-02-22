## Ficheiro de configuração
src/main/resources/application.properties

## Configurar a base de dados
1. Após instalar o postgres executar, na pasta base do projeto, o comando **_sudo su - postgres_**
2. Já no ambiente postgres execute **_createuser --no-superuser --no-createdb --no-createrole so2user_** ;
3. **_createdb -O so2user trabalho2_** ;
4. **_psql trabalho2_**
5. **_alter user so2user with password 'qwerty'_** ;
6. Após executar estes comandos pode sair do ambiente postgres usando 2 vezes _Ctrl+D_;
7. Por último execute o comando **_psql trabalho2 -U so2user -h localhost \< dump.sql_** 

## Compilar a aplicação
Executar na pasta base **_gradle build_**

## Deploy da aplicação
Executar na pasta base **_gradle bootRun_**

## Executar a aplicação
No _browser_ inserir o endereço **_https://localhost:8443_**