# ProjetoSanquim

API REST desenvolvida para o gerenciamento de informações de um ambiente escolar.

### Sobre o projeto

O projeto atende à necessidade de centralizar a gestão acadêmica, facilitando o controle de alunos, professores, turmas e o acompanhamento do desempenho escolar. A API permite que a gestão escolar organize a estrutura de ensino, que professores gerenciem o diário de classe e que alunos consultem suas informações acadêmicas.

Este projeto foi desenvolvido no contexto acadêmico da disciplina de Programação de Scripts Backend, na FATEC Mogi Mirim.

**Integrantes**:
* Arthur Silva
* Giovani Mariano
* Caio Franco Mesquita

#### Perfis de acesso e operações

O sistema possui três perfis de usuários:

*   **GESTAO**: Responsável pela administração do sistema. Pode cadastrar professores, alunos, turmas, disciplinas e realizar matrículas.
*   **PROFESSOR**: Responsável pelo diário de classe. Pode listar suas turmas, registrar chamadas, criar avaliações, lançar notas e registrar planos de aula.
*   **ALUNO**: Perfil para consulta. Pode visualizar seu boletim (notas e médias), consultar sua frequência e acompanhar os planos de aula das disciplinas em que está matriculado.

### Tecnologias utilizadas

*   Java 17
*   Spring Boot
*   Spring Data JPA
*   Spring Security
*   JWT
*   PostgreSQL (Supabase)
*   MapStruct
*   Lombok
*   Swagger
*   Postman

### Funcionalidades

As funcionalidades estão divididas por perfil de acesso:

**GESTAO**
*   Gerenciamento de professores: Cadastro e listagem.
*   Gerenciamento de alunos: Cadastro com geração automática de RA e listagem.
*   Gerenciamento de turmas: Criação de novas turmas.
*   Gerenciamento de disciplinas: Criação de novas disciplinas.
*   Gerenciamento de matrículas: Vínculo de alunos às turmas.

**PROFESSOR**
*   Listagem de turmas vinculadas ao professor.
*   Registro de chamadas: Lançamento de presença ou falta para os alunos.
*   Criação de avaliações: Definição de títulos, tipos, notas máximas e pesos.
*   Lançamento de notas: Registro do desempenho dos alunos em avaliações.
*   Registro de planos de aula: Cadastro de descrições e tópicos abordados.

**ALUNO**
*   Consulta de boletim: Visualização de notas e médias por disciplina.
*   Consulta de frequência: Percentual de presença e total de faltas.
*   Consulta de aulas: Acesso aos planos de aula e conteúdos das disciplinas.

### Organização do projeto

A estrutura de pacotes segue o padrão Spring Boot:

*   `config`: Configurações da aplicação, segurança e carga inicial de dados.
*   `controller`: Endpoints da API e tratamento de requisições.
*   `domain`: Entidades de modelo e enums que representam o banco de dados.
*   `dto`: Objetos de transferência de dados para requisições e respostas.
*   `exception`: Gerenciamento e customização de erros da aplicação.
*   `mapper`: Interfaces MapStruct para conversão entre entidades e DTOs.
*   `repository`: Interfaces para comunicação com o banco de dados via Spring Data JPA.
*   `security`: Implementações de segurança, filtros JWT e serviços de autenticação.
*   `service`: Camada de regras de negócio da aplicação.

O fluxo geral da aplicação é:
Controller → Service → Repository → Banco de Dados.

*   O **Controller** recebe a requisição e valida os dados de entrada.
*   O **Service** processa as regras de negócio e validações necessárias.
*   O **Repository** executa as operações de persistência no banco de dados.

### Configuração do ambiente

Para executar o projeto, é necessário configurar as seguintes variáveis de ambiente:

*   `DB_URL`: URL de conexão do PostgreSQL (ex: `jdbc:postgresql://localhost:5432/sanquim`).
*   `DB_USUARIO`: Nome do usuário do banco de dados.
*   `DB_SENHA`: Senha do banco de dados.
*   `JWS_SECRET`: Chave secreta para assinatura dos tokens JWT.

### Como executar

1.  Clone o repositório para sua máquina local.
2.  Configure as variáveis de ambiente mencionadas anteriormente no seu sistema ou na sua IDE.
3.  Certifique-se de que o PostgreSQL via Supabase esteja em execução e que o banco de dados configurado exista.
4.  Execute a aplicação através do Maven Wrapper:
    *   No Windows: `.\mvnw.cmd spring-boot:run`
    *   No Linux/macOS: `./mvnw spring-boot:run`

### Autenticação e permissões

A API utiliza autenticação baseada em JWT. Para acessar endpoints protegidos, é necessário realizar o login e enviar o token no cabeçalho das requisições.

1.  **Login**: Envie uma requisição POST para `/api/auth/login` com email e senha.
2.  **Obter Token**: A resposta conterá um campo `token`.
3.  **Uso do Token**: Adicione o cabeçalho `Authorization: Bearer <seu_token>` em todas as requisições para as rotas protegidas.

#### Perfis e Permissões

| Perfil | Prefixo da Rota | Role Spring Security |
| :--- | :--- | :--- |
| GESTAO | `/api/admin/**` | `ROLE_GESTAO` |
| PROFESSOR | `/api/diario/**` | `ROLE_PROFESSOR` |
| ALUNO | `/api/aluno/**` | `ROLE_ALUNO` |

### Credenciais para testes

As credenciais abaixo podem ser utilizadas para testar a API. Cada usuário possui um perfil com permissões específicas.

| Perfil | E-mail | Senha |
|---|---|---|
| GESTAO | `admin@sanquim.br` | `123456` |
| PROFESSOR | `prof.mat@sanquim.br` | `123456` |
| ALUNO | `aluno1@sanquim.br` | `123456` |

**Como utilizar:**

1. Acesse a rota `POST /api/auth/login`.
2. Informe o e-mail e a senha do perfil desejado.
3. Copie o token JWT retornado pela API.
4. Utilize o token nas requisições que exigem autenticação, informando-o no campo `Authorization` como `Bearer {token}`.

> **Observação:** Essas credenciais são destinadas exclusivamente ao ambiente de demonstração e testes do projeto.

O perfil GESTAO também possui acesso às rotas do perfil PROFESSOR para supervisão do diário de classe.

### Swagger

A documentação interativa da API via Swagger pode ser acessada após iniciar a aplicação no endereço:
`http://localhost:8080/swagger-ui.html`

O Swagger permite visualizar todos os endpoints disponíveis, seus modelos de dados e testar as requisições diretamente pelo navegador.

### Testes da API com Postman

O arquivo `sanquim.postman_collection.json` está disponível na raiz do projeto para testes manuais.

Para utilizar:
1.  Importe o arquivo no Postman.
2.  Configure a variável `baseUrl` na collection (o padrão é `http://localhost:8080`).
3.  Execute as requisições da pasta `0 - Auth` para obter os tokens e popular as variáveis de ambiente da collection automaticamente através dos scripts de teste inclusos.
4.  Utilize as demais pastas para testar as funcionalidades de cada perfil.


### Considerações finais

O ProjetoSanquim foi desenvolvido como um trabalho acadêmico para demonstrar a aplicação de tecnologias Java e Spring Boot na construção de uma API REST segura e funcional para gestão escolar.
