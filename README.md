# Desafio API — Cat API CRUD

Este projeto consiste em uma API REST desenvolvida em **Java** utilizando **Spring Framework** para realizar operações CRUD em uma entidade chamada **Cat**. A API também se integra à **TheCatAPI** para obter informações sobre raças e imagens correspondentes.

## 📌 Funcionalidades

- **CRUD de Cats:**
    - Criar um novo cat.
    - Listar todos os cats cadastrados.
    - Buscar um cat específico por ID.
    - Atualizar os dados de um cat.
    - Remover um cat do sistema.
- **Integração com TheCatAPI:**
    - Listar raças de cats disponíveis.
    - Buscar imagens de cats com base na raça informada.
- **Filtro por Raça:**
    - Permite buscar cats cadastrados por nome da raça.
- **Documentação:**
    - Swagger para documentar e testar os endpoints.
## 🛠 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Banco de Dados: MySQL**
- **Swagger**
- **TheCatAPI** (API Externa)

## 🔧 Instalação e Configuração

### 1️⃣ Clonar o Repositório
```bash
git clone git@git.gft.com:knou/desafio-api.git
cd api-cats
```

### 2️⃣ Configurar a API Key da TheCatAPI
Crie uma conta em [TheCatAPI](https://thecatapi.com/) e obtenha sua API Key.

### 3️⃣ Configuração do Application Properties
```properties
spring.application.name=cat-api
server.error.include-stacktrace=never

# Configuração MySQL + JPA
spring.datasource.url=jdbc:mysql://{host}:{porta}/{nome-do-banco}?useSSL=false&createDatabaseIfNotExist=true
spring.datasource.username={usuario}
spring.datasource.password={senha}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl_auto=update

# Configuração/Adicionando API Key
thecatapi.apikey={sua-api-key}
```

## 🚀 Endpoints

### 📌 CRUD de Cats
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/cats` | Cadastra um novo cat |
| `GET` | `/api/cats` | Lista todos os cats cadastrados |
| `GET` | `/api/cats/{id}` | Busca um cat pelo ID |
| `PUT` | `/api/cats/{id}` | Atualiza os dados de um cat |
| `DELETE` | `/api/cats/{id}` | Remove um cat do sistema |

### 📌 Integração com TheCatAPI
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/breeds` | Lista as raças de cats disponíveis |
| `GET` | `/api/image?breed={breed_id}` | Busca imagens de cats por raça |

## 🛡️ Tratamento de Erros
A API conta com um sistema robusto de tratamento de erros, incluindo:
- **Exceções específicas para erros HTTP:**
    - `NotFoundException` (HTTP 404 - Not Found)
    - `BadRequestException` (HTTP 400 - Bad Request)
    - `InternalServerErrorException` (HTTP 500 - Internal Server Error)
- **Retorno de status adequados:**
    - `HttpStatus.OK` para respostas bem-sucedidas.
    - `HttpStatus.CREATED` para registros bem-sucedidos.
    - `HttpStatus.NO_CONTENT` para deleções bem-sucedidas.
- **Tratamento de falhas na API externa TheCatAPI:**
    - `CatBadRequestException` para erros ao buscar imagens da raça (`HttpClientErrorException`).
    - `CatDataAccessException` para falhas de conexão com a API externa (`ResourceAccessException`).
    - `RuntimeException` para erros inesperados na busca de imagens.
- **Mensagens de erro amigáveis e detalhadas.**

## 📝 Documentação
A API possui documentação interativa via Swagger. Após rodar a aplicação, acesse:
```
http://localhost:8080/swagger-ui.html
```

## ✅ Critérios de Avaliação
- Arquitetura separada em **Controller, Service e Repository**.
- Utilização de **boas práticas de API REST**.
- **Integração bem-sucedida** com a API externa.
- **Uso do Swagger** para documentação.
- **Tratamento adequado de erros**.

## 📜 Licença
Construído por Kelvin Rodrigues para GFT Start
