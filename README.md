# Gra-Filmes

Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que
obteve dois prêmios mais rápido


## Rodando o projeto

Clone o projeto

```bash
  git clone https://github.com/carloslimajlle/gra-filmes.git
```

Entre no diretório do projeto

```bash
  cd gra-filmes
```

Instale as dependências

```bash
  mvn clean install
```

Inicie o servidor

```bash
  mvn spring-boot:run
```


## Documentação da API

Swagger: http://localhost:8000/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#

#### Retorna todos os itens

```http
  GET /api/movies/producers/interval-winner
```



## Autor

- [linkedin.com/in/carlosrlima](https://www.linkedin.com/in/carlosrlima/)

