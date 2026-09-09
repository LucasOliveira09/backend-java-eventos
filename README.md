# Sistema de Gerenciamento de Eventos

Projeto backend acadêmico para modelar e persistir informações de eventos, incluindo categorias, locais, palestrantes, participantes e inscrições. A aplicação foi construída com Java e Spring Boot, usando Spring Data JPA e MySQL.

> **Para avaliadores técnicos:** este repositório demonstra a modelagem relacional do domínio e a integração com banco de dados. No estado atual, ele não expõe uma API HTTP: não há controllers, serviços ou endpoints REST implementados.

## Destaques técnicos

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA / Hibernate
- MySQL 8
- Maven Wrapper, para execução sem instalação prévia do Maven
- Lombok, para reduzir código repetitivo nas entidades
- Dev Container com ambiente reprodutível (JDK 21 + MySQL 8)
- Carga inicial com 30 registros em `eventos/src/main/resources/import.sql`

## Modelo de domínio

| Entidade | Responsabilidade | Relacionamentos |
| --- | --- | --- |
| `Categoria` | Classifica os eventos por tema. | Um evento pertence a uma categoria. |
| `Local` | Armazena endereço e capacidade do espaço. | Um evento ocorre em um local. |
| `Palestrante` | Identifica o responsável pela palestra. | Um evento possui um palestrante. |
| `Participante` | Representa a pessoa que pode se inscrever. | Um participante pode ter inscrições. |
| `Evento` | Reúne dados, período, capacidade e status do evento. | Relaciona-se a categoria, local e palestrante. |
| `Inscricao` | Registra a participação em um evento. | Relaciona um participante a um evento. |

Em termos de cardinalidade, `Evento` possui relações `@ManyToOne` com `Categoria`, `Local` e `Palestrante`; `Inscricao` possui relações `@ManyToOne` com `Evento` e `Participante`.

## Estrutura do repositório

```text
.
├── .devcontainer/                 # Ambiente de desenvolvimento com JDK e MySQL
├── eventos/                       # Projeto Maven/Spring Boot
│   ├── src/main/java/.../entities/ # Entidades JPA do domínio
│   ├── src/main/java/.../repositories/ # Repositórios Spring Data JPA
│   ├── src/main/resources/
│   │   ├── application.properties  # Configuração da aplicação e do datasource
│   │   └── import.sql              # Dados iniciais
│   ├── src/test/                   # Teste de inicialização do contexto Spring
│   ├── mvnw / mvnw.cmd             # Maven Wrapper
│   └── pom.xml                     # Dependências e configuração do build
├── .gitignore
└── LICENSE
```

## Como executar

### Opção recomendada: Dev Container / GitHub Codespaces

O repositório inclui uma configuração de desenvolvimento reprodutível. Ela utiliza Java 21, Maven e MySQL 8, cria automaticamente o banco `eventos` e inicia a aplicação na porta `8080`.

1. Abra o repositório no GitHub Codespaces ou no VS Code com a extensão **Dev Containers**.
2. Escolha **Reopen in Container** quando solicitado.
3. Aguarde os scripts de criação e inicialização concluírem.
4. Acompanhe o log da aplicação, se necessário:

   ```bash
   tail -f /tmp/eventos-spring-boot.log
   ```

O container encaminha as portas `8080` (aplicação) e `3306` (MySQL).

### Execução local

#### Pré-requisitos

- JDK 21 instalado — um JRE não é suficiente, pois o Maven precisa do compilador `javac`.
- MySQL 8 em execução na máquina local.
- Git (para clonar o repositório).

Verifique se o Java é um JDK:

```bash
java --version
javac --version
```

Crie o banco e o usuário de desenvolvimento no MySQL:

```sql
CREATE DATABASE IF NOT EXISTS eventos;
CREATE USER IF NOT EXISTS 'aluno'@'localhost' IDENTIFIED BY '123@Mudar';
GRANT ALL PRIVILEGES ON eventos.* TO 'aluno'@'localhost';
FLUSH PRIVILEGES;
```

Depois, na raiz do repositório:

```bash
cd eventos
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A aplicação será iniciada em `http://localhost:8080`. Como ainda não existem controllers, não há rotas HTTP para acessar no navegador ou em um cliente REST.

## Banco de dados e dados de demonstração

A conexão é definida em `eventos/src/main/resources/application.properties`:

| Item | Valor de desenvolvimento |
| --- | --- |
| Host | `localhost` |
| Porta | `3306` |
| Banco | `eventos` |
| Usuário | `aluno` |
| Senha | `123@Mudar` |

Ao iniciar, a configuração `spring.jpa.hibernate.ddl-auto=create` recria o schema, e o `import.sql` insere cinco registros para cada entidade, totalizando 30 registros. Isso facilita a demonstração do modelo, mas **apaga os dados existentes a cada reinicialização**. Essa configuração não deve ser usada em produção.

As credenciais presentes no repositório são exclusivamente para desenvolvimento local. Em um ambiente real, elas devem ser removidas do controle de versão e fornecidas por variáveis de ambiente ou um gerenciador de segredos.

## Comandos úteis

Execute os comandos dentro da pasta `eventos/`.

| Comando | Finalidade |
| --- | --- |
| `./mvnw spring-boot:run` | Inicia a aplicação no Linux/macOS. |
| `.\mvnw.cmd spring-boot:run` | Inicia a aplicação no Windows. |
| `./mvnw test` | Compila e executa os testes no Linux/macOS. |
| `.\mvnw.cmd test` | Compila e executa os testes no Windows. |
| `./mvnw clean package` | Gera o JAR em `target/` no Linux/macOS. |
| `.\mvnw.cmd clean package` | Gera o JAR em `target/` no Windows. |

O teste atual valida se o contexto do Spring Boot sobe corretamente; por utilizar o datasource configurado, ele também requer MySQL disponível.

## Estado atual e próximos passos

O escopo atual cobre a persistência. Evoluções naturais para transformar o projeto em uma API completa incluem:

1. Criar camadas de serviço e controllers REST para cada recurso.
2. Adicionar DTOs e validação de entrada com Bean Validation.
3. Tratar exceções com respostas HTTP padronizadas.
4. Implementar regras de negócio, como limite de vagas e prevenção de inscrição duplicada.
5. Ampliar testes unitários e de integração com banco isolado.
6. Trocar `ddl-auto=create` por migrations versionadas (por exemplo, Flyway) antes de qualquer ambiente persistente.

## Licença

Este projeto está disponibilizado sob a [licença presente no repositório](LICENSE).
