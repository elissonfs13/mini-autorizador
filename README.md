# Teste de programação - VR Benefícios
## Mini autorizador
O presente projeto foi desenvolvido com o objetivo de realizar o teste de programação proposto pela VR Benefícios.

O arquivo com as regras de negócio e demais instruções para o desenvolvimento desse teste foi renomeado para 'README_init.md' e se encontra no mesmo diretório desse arquivo.

Conforme instruções, foi utilizado o banco de dados relacional MySQL e comentado o trecho referente ao banco de dados não relacional no arquivo 'docker-compose.yml'.

### Considerações sobre a solução
A solução foi desenvolvida utilizando as seguintes tecnologias: 
- Java 11;
- SpringBoot 2.7.4;
- Spring Data JPA;
- MySQL 5.7;
- OpenAPI 1.6.12;
- JUnit 4.13.2;
- H2 2.1.214;
- Bean Validation;
- Docker e docker-compose;

As validações foram implementadas utilizando as próprias exceções lançadas no processamento na execução normal da transação.
Quando a exceção de uma validação específica é lançada, ela é capturada por um 'ControllerAdvice' e as informações correspondentes são retornadas pela API que requisitou a transação.

A solução tentou ser implementada mantendo-se a simplicidade aliada com as boas práticas do desenvolvimento de software. 
Complexidades adicionais e desnecessárias foram evitadas, a fim de chegar no resultado proposto no teste.

Testes de unidade foram implementados utilizando JUnit e Mockito. 
Testes de integração foram implementados com banco de dados em memória H2.

Comentários foram adicionados em pontos chaves do código para explicar a lógica de desenvolvimento da solução. Comentários redundantes e desnecessários foram omitidos!

Documentações de APIs foram incluídas utilizando OpenAPI e Swagger e podem ser consultadas:
- OpenAPI: http://localhost:8080/api-docs/
- Swagger: http://localhost:8080/swagger-ui/index.html

No processo de desenvolvimento, o teste foi considerado como uma estória, sendo dividida em subtarefas que correspondem as branches criadas no github.
Obviamente a solução final encontra-se na branch principal do projeto. 
Branches:
- main
- feature/EFS001-criacao-cartoes
- feature/EFS002-obter-saldo
- feature/EFS003-realizar-transacao
- feature/EFS004-testes
- feature/EFS005-documentacao

Como parte da solução, o método responsável por realizar a transação, ou seja o débito do valor no saldo do cartão foi modificado para se tornar 'Thread-safe'. 
Essa simples alteração faz com que erros de interferência e inconsistência sejam prevenidos, pois o método poderá ser acessado somente por uma única instância / thread por vez.

### Outros projetos
Outros projetos pessoais disponíveis no github que podem ser analisados a vontade:
- Futebol Simulador: simula uma Copa do Mundo com resultados gerados aleatoriamente.
  - API: https://github.com/elissonfs13/futebol-simulador-api
  - UI: https://github.com/elissonfs13/futebol-simulador-ui
- EFS-Video: laboratório para implementação e integração entre diferentes tecnologias, tais como Spring Cloud, Spring Batch, Apache Kafka, Apache Avro, Apache Spark, Python, entre outros.
  - https://github.com/elissonfs13/efs-video

### Autor:
Elisson Francisco da Silva

email: elissonfs@gmail.com

cel: (12)997477873

linkedIn: https://www.linkedin.com/in/elissonfs/