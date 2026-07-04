# API-Rest_Assured_Automation_Auth

## Introdução

Este repositório contém os testes automatizados de API desenvolvidos com Rest Assured e JUnit 5, voltados para a validação dos endpoints da [Restful Booker](https://restful-booker.herokuapp.com), uma API pública usada como ambiente de prática. Os testes cobrem os principais verbos HTTP (GET, POST, PUT e DELETE), incluindo fluxos de autenticação, cenários de sucesso e cenários de erro para validação dos comportamentos esperados da API.

O projeto também conta com integração ao GitHub Actions, que executa toda a suíte de testes automaticamente a cada push ou pull request na branch master, e com Allure Reports para geração de relatórios detalhados de execução.

## Tecnologias Utilizadas

- **Java 11** - linguagem base do projeto
- **Rest Assured 5.5.6** - biblioteca para automação de testes de API REST
- **JUnit 5 (Jupiter)** - framework de testes utilizado para estruturar e executar os casos de teste
- **Hamcrest 2.2** - biblioteca de matchers usada nas asserções dos testes
- **Jackson Databind 2.15.2** - utilizado para manipulação e conversão de objetos JSON
- **org.json** - biblioteca auxiliar para construção e modificação dinâmica de payloads JSON nos testes
- **Allure Reports 2.23.0** - ferramenta de geração de relatórios de testes, integrada ao JUnit 5
- **Maven** - gerenciador de dependências e ciclo de build do projeto
- **GitHub Actions** - pipeline de CI/CD que executa os testes automaticamente a cada push ou pull request

## Estrutura do Repositório

```
API-Rest_Assured_Automation_Auth/
├── .github/
│   └── workflows/
│       └── teste.yml
├── src/
│   └── test/
│       ├── java/
│       │   ├── pages/
│       │   │   ├── AuthAPI.java
│       │   │   ├── DeleteAPI.java
│       │   │   ├── GetAPI.java
│       │   │   ├── PostAPI.java
│       │   │   └── PutAPI.java
│       │   ├── tests/
│       │   │   ├── AuthTest.java
│       │   │   ├── DeleteTest.java
│       │   │   ├── GetTest.java
│       │   │   ├── PostTest.java
│       │   │   └── PutTest.java
│       │   └── utils/
│       │       └── JsonUtils.java
│       └── resources/
│           └── payloads/
│               ├── auth.json
│               ├── body_Post.json
│               └── body_update.json
├── pom.xml
└── .gitignore
```

## Objetivo de cada grupo de arquivos

### `.github/workflows/`
Contém a configuração da pipeline de integração contínua. O arquivo `teste.yml` define o workflow do GitHub Actions, que instala o Java 11 e executa `mvn test` automaticamente sempre que há um push ou pull request na branch master.

### `pages/`
Camada de abstração das requisições HTTP, seguindo o Page Object Model adaptado para API. Cada arquivo representa um conjunto de endpoints de um determinado verbo ou domínio:

- **`AuthAPI.java`**: encapsula a requisição de autenticação (`POST /auth`) e retorna o token de sessão.
- **`GetAPI.java`**: encapsula as requisições de consulta (`GET /booking`), incluindo busca por ID, nome, sobrenome, checkin e checkout.
- **`PostAPI.java`**: encapsula a criação de uma nova reserva (`POST /booking`).
- **`PutAPI.java`**: encapsula a atualização completa de uma reserva existente (`PUT /booking/{id}`), usando o token de autenticação no header.
- **`DeleteAPI.java`**: encapsula a exclusão de uma reserva (`DELETE /booking/{id}`), também autenticada via token.

### `tests/`
Contém os casos de teste organizados por verbo HTTP. Cada classe usa a respectiva classe de `pages/` para fazer as chamadas e valida as respostas com asserções via Hamcrest e JUnit 5:

- **`AuthTest.java`**: valida que o endpoint de autenticação retorna status 200 e um token não nulo.
- **`GetTest.java`**: cobre consultas com dados válidos e inválidos, testando retornos de 200, 404 e 500.
- **`PostTest.java`**: testa a criação de reservas com todos os campos válidos e cenários de campos ausentes ou com tipos incorretos.
- **`PutTest.java`**: valida a atualização de reservas existentes com token de autenticação.
- **`DeleteTest.java`**: valida a exclusão de reservas com token de autenticação.

### `utils/`
Contém a classe `JsonUtils.java`, responsável por ler arquivos JSON do diretório de resources e retorná-los como `String`. É usada pelos testes para carregar os payloads de forma centralizada, evitando duplicação de código.

### `resources/payloads/`
Armazena os arquivos JSON usados como corpo das requisições:

- **`auth.json`**: credenciais de login (username e password) usadas no teste de autenticação.
- **`body_Post.json`**: payload base para criação de uma nova reserva, com todos os campos obrigatórios.
- **`body_update.json`**: payload usado para atualização completa de uma reserva existente.

## Modo de Instalação

### Pré-requisitos

- [Java 11](https://adoptium.net/) ou superior instalado
- [Maven](https://maven.apache.org/install.html) instalado e configurado no PATH
- [Git](https://git-scm.com/) instalado

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/IngridVanzeli/API-Rest_Assured_Automation_Auth.git
   ```

2. Acesse a pasta do projeto:
   ```bash
   cd API-Rest_Assured_Automation_Auth
   ```

3. Instale as dependências:
   ```bash
   mvn install -DskipTests
   ```

Todas as dependências são gerenciadas pelo Maven via `pom.xml`, então não é necessário instalar nada manualmente além do Java e do Maven.

## Modo de Execução do Projeto

### Executar toda a suíte de testes

```bash
mvn test
```

### Executar uma classe de teste específica

```bash
mvn test -Dtest=GetTest
```

```bash
mvn test -Dtest=PostTest
```

### Gerar e visualizar o relatório com Allure

Após executar os testes, os resultados ficam em `target/allure-results`. Para gerar e abrir o relatório:

```bash
mvn allure:serve
```

O Allure abrirá o relatório automaticamente no navegador. Se preferir apenas gerar o HTML sem abrir:

```bash
mvn allure:report
```

O relatório será salvo em `target/site/allure-maven-plugin/`.
