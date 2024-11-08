# Desafio Frases Aleatórias
🚩Java e Orientação a Objetos G7 - ONE<br>
📅23 de outubro de 2024 - 📍São Paulo, Brasil<br>
🌎[@Alura](https://www.alura.com.br/) | [@One](https://www.oracle.com/br/)<br>

![283493070-a4597a93-d8de-43d8-974d-2f4e274e336c](https://github.com/user-attachments/assets/7138967f-646d-4d2a-b84e-63969b34bd9a)

## Versões
- [Java 21](https://docs.oracle.com/en/java/javase/21/)
- [Maven](https://maven.apache.org/what-is-maven.html)
- [OpenAI Platform](https://platform.openai.com)
- [API OMDB](https://www.omdbapi.com/)

## 🔨 Objetivos do projeto
- O objetivo do projeto é consolidar seus conhecimentos adquiridos ao longo da formação, no que tange a Spring, Streams, JPA, aplicação Web;
- É importante iniciar o projeto do zero e ir modelando as classes, testando o acesso ao banco, verificando erros ao tentar obter dados pela aplicação front;
- Criar corretamente o controlador, mapeando a rota que a aplicação front-end irá consumir;
- Entender e corrigir erros referente a CORS;
- Promover uma experiência fullstack entendendo o fluxo da aplicação ponta a ponta.

- Você precisará criar uma API REST, que disponibilize dados através do endpoint http://localhost:8080/series/frases;
- Será necessário criar as classes Controller, Service, Repository para implementar as requisições e busca ao banco;
- Será necessário criar a classe Model denominada **Frase** com os atributos _id_, _titulo_, _frase_, _personagem_ e _poster_;
- Também é interessante criar a classe SerieDTO que será responsável por representar os dados que serão devolvidos para a aplicação front-end;

## Configurações de utilização
A aplicação utiliza de 2 APIs para realizar suas operações. 

Será necessário possuir uma chave da API da [OMDB](https://www.omdbapi.com/) para consumir informações sobre séries e filmes, como também para gerar as frases dos personagens, que foi utilizada a API da [OpenAI](https://platform.openai.com)

Após conseguir as 2 chaves de acesso `apiKey`, no arquivo `application.yml` presente no diretório `./backend/src/main/resources/`, modifique os seguintes campos:
```yaml
openai:
  config:
    apiKey: COLOQUE_AQUI_A_CHAVE_DA_OPEN-AI
```

```yaml
omdb:
  config:
    apiKey: COLOQUE_AQUI_A_CHAVE_DA_OMDB
```

A aplicação também esta utilizando dois perfis de ambiente `prod` e `test`, onde basicamente o ambiente `prod` utiliza do SGBD _Postgres_ e o de `test` utiliza o banco de dados em memória _H2_. Para conseguir modificar o Perfil/Profile, é possivel encontrar essa configuração em `./backend/src/main/resources/application.yml`:
```yaml
spring:
  profiles:
    active: MODIFIQUE_AQUI # prod ou test
```

## Descrição
**Tela Frases Aleatórias**

![image](https://github.com/user-attachments/assets/f8b27135-24fc-4ef9-8a20-86f046940a3c)


**Gerando Frases Por Titulo**

![image](https://github.com/user-attachments/assets/33585165-e55c-4aaa-97ef-d0b29a22f40d)
