# Implementação e Fluxo da Aplicação

Na primeira implementação, tentei utilizar o Redis para evitar consultas ao banco e, em seguida, inserir a mensagem na fila. Porém, ao fazer um teste de stress, comecei a ter diversos problemas de persistência e acabei mudando a implementação para fazer o controle no banco. Ainda uso o Redis para fazer algumas verificações, porém com o banco sempre de resiliência devido aos problemas de persistência.

#### Aplicação:
Portanto, nessa última implementação, decidi criar uma tabela menor para ter apenas o controle de quantas mensagens foram inseridas e, em seguida, enviar o request para a fila. Nesse sentido, talvez não fosse necessário uma fila devido a não haver tantos processos para serem executados no consumo, mas resolvi extrapolar um pouco para trazer o conceito.

#### Fluxo Principal:
1. `criarPauta`
2. `abrirSessaoVotacao`
3. `votar`

A aplicação sempre roda um fluxo assíncrono em background para encerrar as sessões abertas e publicar em uma fila de encerramento. Acredito que tentar fechar após cada voto seria um processo desnecessário. É possível programar o tempo que esse método roda.

#### Sobre o CPF:
Por ser opcional, essa verificação externa atrapalharia o fluxo de insert. Acredito que ela deveria ser feita em outro momento, como no login do usuário.

# Stack Utilizada

- Optei por utilizar Java, Spring, RabbitMQ, Postgres e Redis.

# Scripts de Inserção no Banco

- Optei por utilizar o flyway, migrations estão na pasta resource.

# Arquitetura

- Utilizei a arquitetura Onion, mas fiz apenas a estrutura de pastas para passar a ideia. Para um projeto maior, poderia separar em módulos.

# Postman

- Deixei dois arquivos na pasta raiz do projeto com as requisições da api e as env variables.

# Como executar

### Deixei três arquivos docker-compose prontos na pasta raiz do projeto

#### Esse compose é pra você executar junto com alguma ide. Após rodar o comando abaixo é só rodar a aplicação.

- docker-compose -f docker-compose-dev.yaml 

#### Esse compose é pra você executar local, porém sem necessidade de ide

- docker-compose -f docker-compose.yaml 

#### Esse compose é para fazer o deploy

- docker-compose -f docker-compose-prod.yaml 

# Testes

- Foi feito um teste integrado que testa o fluxo inteiro até o fim, por isso os testes podem demorar um pouco. Devido ao tempo mínimo de abertura de sessão.
- Foi feito um stress teste, mas apenas localmente, pois os meus créditos da aws estão acabando. Foram feitos no postman com 50 vus.

# Swagger

- /swagger-ui/index.html#

# Versionamento 

-  Versionamento Semântico
   O versionamento semântico segue um padrão bem definido para números de versão, como X.Y.Z, onde:
   X indica uma versão principal que pode ter alterações incompatíveis com versões anteriores.
   Y indica uma versão secundária que adiciona funcionalidades de forma compatível com versões anteriores.
   Z indica uma versão de correção de bugs que mantém compatibilidade total.
   Exemplo: 1.2.1


# URL deployment
- http://sicredi-assembleia.us-east-1.elasticbeanstalk.com/swagger-ui/index.html#
