## Estrutura da API

A API é dividida em três principais classes:

1. **Clientes**
    - Descrição: Gerencia as informações dos clientes, incluindo dados pessoais e agendamentos.
    - Endpoints:
        - **Criar Cliente**
            - **Método:** POST
            - **URL:** `/api/clientes`
            - **Raw do Postman:**
          ```json
          {
              "nome": "Nome do Cliente",
              "telefone": "1234567890",
              "email": "cliente@example.com",
              "cpf": "123.456.789-09"
          }
          ```

2. **Salão**
    - Descrição: Gerencia as informações dos salões, incluindo nome, endereço, contato e serviços oferecidos.
    - Endpoints:
        - **Criar Salão**
            - **Método:** POST
            - **URL:** `/api/saloes`
            - **Raw do Postman:**
          ```json
          {
              "nome": "Nome do Salão",
              "cep": "12345-678",
              "logradouro": "Rua Exemplo",
              "bairro": "Centro",
              "cidade": "Cidade Exemplo",
              "estado": "Estado Exemplo",
              "telefone": "9876543210",
              "preco": 100.00,
              "servicos": "Corte, Coloracao",
              "cnpj": "12.345.678/0001-90",
              "email": "salao@example.com"
          }
          ```

3. **Agenda**
    - Descrição: Gerencia os agendamentos de serviços pelos clientes.
    - Endpoints:
        - **Agendar Serviço**
            - **Método:** POST
            - **URL:** `/api/agenda`
            - **Raw do Postman:**
          ```json
          {
              "clienteId": 1,
              "salaoId": 1,
              "servico": "Corte",
              "dataHora": "2024-10-20T10:00:00"
          }
          ```

## Configuração do Banco de Dados

Para utilizar a API, é necessário ter o PostgreSQL instalado e configurado. Você pode usar o pgAdmin para gerenciar o banco de dados.

### Passos para Configuração

1. **Instalar o PostgreSQL**:
    - Faça o download do PostgreSQL em [PostgreSQL Download](https://www.postgresql.org/download/).
   

2. **Instalar o pgAdmin**:
    - Faça o download do pgAdmin em [pgAdmin Download](https://www.pgadmin.org/download/).
   

3. **Criar Banco de Dados**:
    - Após instalar o PostgreSQL e o pgAdmin, crie um banco de dados chamado `AliHair`.
   

4. **Configurar o arquivo `application.properties`**:
    - Adicione as seguintes configurações no seu arquivo `application.properties`, substituindo as informações de usuário e senha por suas configurações pessoais. Aqui está um exemplo genérico:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/AliHair
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.show_sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.hibernate.ddl-auto=update
