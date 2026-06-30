# Baozi Store API

API REST simples desenvolvida em Java com Spring Boot para o trabalho de Desenvolvimento Web Back-End.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- MySQL
- H2 opcional para teste rápido
- Postman

## Entidades

### Cliente

| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador gerado automaticamente |
| nome | String | Nome do cliente. No trabalho, use seu nome seguido do RU |
| clienteDesde | LocalDate | Data de cadastro no formato `yyyy-MM-dd` |

### Produto

| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador gerado automaticamente |
| nome | String | Nome do produto |
| preco | BigDecimal | Preço do produto |
| estoque | Boolean | Indica se o produto está em estoque |

### Pedido

| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador gerado automaticamente |
| clienteId | Long | ID do cliente que realizou o pedido |
| produtoId | Long | ID do produto comprado |
| quantidade | Integer | Quantidade comprada |

## Como executar com MySQL

Suba o MySQL com Docker:

```bash
docker compose up -d
```

Depois execute a aplicação:

```bash
mvn spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

Configuração padrão do banco:

```text
Banco: baozi_store
Usuário: root
Senha: root
Porta: 3306
```

## Como executar com H2, sem MySQL

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

Console H2:

```text
http://localhost:8080/h2-console
```

Dados de conexão do H2:

```text
JDBC URL: jdbc:h2:mem:baozi_store
User: sa
Password: deixe vazio
```

## Endpoints

### Clientes

| Método | Endpoint | Função |
|---|---|---|
| POST | `/clientes` | Criar cliente |
| GET | `/clientes` | Listar clientes |
| GET | `/clientes/{id}` | Consultar cliente por ID |
| PUT | `/clientes/{id}` | Atualizar cliente |
| DELETE | `/clientes/{id}` | Apagar cliente |

### Produtos

| Método | Endpoint | Função |
|---|---|---|
| POST | `/produtos` | Criar produto |
| GET | `/produtos` | Listar produtos |
| GET | `/produtos/{id}` | Consultar produto por ID |
| PUT | `/produtos/{id}` | Atualizar produto |
| DELETE | `/produtos/{id}` | Apagar produto |

### Pedidos

| Método | Endpoint | Função |
|---|---|---|
| POST | `/pedidos` | Criar pedido |
| GET | `/pedidos` | Listar pedidos |
| GET | `/pedidos/{id}` | Consultar pedido por ID |
| PUT | `/pedidos/{id}` | Atualizar pedido |
| DELETE | `/pedidos/{id}` | Apagar pedido |

## Exemplos JSON

### Criar cliente

```json
{
  "nome": "HenriqueNunes000000",
  "clienteDesde": "2026-06-29"
}
```

Troque `000000` pelo seu RU.

### Criar produto

```json
{
  "nome": "Baozi de Frango",
  "preco": 12.50,
  "estoque": true
}
```

### Criar pedido

```json
{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 3
}
```

## Postman

A coleção está na pasta:

```text
postman/Baozi_Store.postman_collection.json
```

Importe essa coleção no Postman e execute na ordem. Os IDs criados nos POSTs são salvos automaticamente nas variáveis da coleção.

## Entrega

Na pasta `docs`, há um modelo de relatório para preencher com seu RU, link do GitHub e prints do Postman.
