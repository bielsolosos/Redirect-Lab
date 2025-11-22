# 🔗 RDL - Redirect Lab

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.8-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

> **RDL (Redirect Lab)** é um encurtador de URLs moderno desenvolvido com Spring Boot 3, focado em simplicidade, performance e uma interface elegante com DaisyUI.

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Funcionalidades](#-funcionalidades)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Configuração](#-configuração)
- [API REST](#-api-rest)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Banco de Dados](#-banco-de-dados)
- [Contribuindo](#-contribuindo)

---

## 🎯 Sobre o Projeto

O **RDL** é um sistema de encurtamento de URLs que permite criar links curtos e personalizados, com interface web integrada usando Thymeleaf e DaisyUI. Ideal para portfólio e projetos que necessitam de gerenciamento de redirecionamentos.

### ✨ Principais Características

- 🚀 **Performance**: Spring Boot 3 com Java 21
- 🎨 **Interface Moderna**: DaisyUI + Tailwind CSS (tema dark)
- 🔒 **Seguro**: Validações robustas e tratamento de exceções global
- 📊 **Migrations**: Flyway para controle de versão do banco
- 🐘 **PostgreSQL**: Banco de dados relacional confiável
- 🔄 **CRUD Completo**: Create, Read, Update, Delete e Toggle

---

## 🛠️ Tecnologias

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.8** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Validation** - Validação de dados
- **Lombok** - Redução de boilerplate

### Frontend
- **Thymeleaf** - Template engine
- **DaisyUI 4.12.14** - Biblioteca de componentes
- **Tailwind CSS** - Framework CSS utility-first
- **HTMX 2.0.4** - Interatividade (preparado para uso futuro)

### Banco de Dados
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Migrations e controle de versão

### Ferramentas
- **Maven** - Gerenciamento de dependências
- **Spring DevTools** - Hot reload em desenvolvimento
- **Spring Dotenv** - Gerenciamento de variáveis de ambiente

---

## 🏗️ Arquitetura

O projeto segue uma **arquitetura em camadas** (Layered Architecture) com separação clara de responsabilidades:

```
┌─────────────────────────────────────────┐
│           API Layer                     │
│  (Controllers, DTOs, Mappers)          │
└─────────────┬───────────────────────────┘
              │
┌─────────────▼───────────────────────────┐
│         Domain Layer                    │
│  (Services, Entities, Repositories)    │
└─────────────┬───────────────────────────┘
              │
┌─────────────▼───────────────────────────┐
│          Core Layer                     │
│  (Exceptions, Configurations)          │
└─────────────────────────────────────────┘
```

---

## ⚡ Funcionalidades

### ✅ Implementadas

- [x] **Criar Redirect** - Cria um novo link encurtado
- [x] **Listar Redirects** - Lista todos os links cadastrados
- [x] **Buscar por Slug** - Busca redirect específico
- [x] **Atualizar Redirect** - Atualiza dados do redirect
- [x] **Deletar Redirect** - Remove redirect do sistema
- [x] **Habilitar/Desabilitar** - Toggle de ativação/desativação
- [x] **Redirecionamento** - Redireciona usuário para URL original
- [x] **Página 404 Customizada** - Interface elegante para erros
- [x] **Health Check** - Monitoramento do status da aplicação
- [x] **Tratamento Global de Exceções** - Handler centralizado

### 🔜 Futuras

- [ ] Estatísticas de cliques
- [ ] Sistema de autenticação
- [ ] QR Code para links
- [ ] Expiração de links
- [ ] Personalização de slugs

---

## 📦 Pré-requisitos

- **Java 21** ou superior
- **PostgreSQL 16** ou superior
- **Maven 3.8+**
- **Git**

---

## 🚀 Instalação

### 1. Clone o repositório

```bash
git clone https://github.com/bielsolosos/rdl.git
cd rdl
```

### 2. Configure o banco de dados

Crie o banco de dados PostgreSQL:

```sql
CREATE DATABASE "rdl-db";
```

### 3. Configure as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto (use `.env.example` como base):

```env
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/rdl-db
DB_USERNAME=postgres
DB_PASSWORD=sua_senha

# JPA Configuration
SHOW_SQL=false
```

### 4. Execute o projeto

```bash
# Com Maven Wrapper
./mvnw spring-boot:run

# Ou com Maven instalado
mvn spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

---

## ⚙️ Configuração

### application.yml

```yaml
spring:
  application:
    name: rdl
  
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/rdl-db}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: ${SHOW_SQL:false}
  
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
```

---

## 🌐 API REST

### Base URL
```
http://localhost:8080
```

### Endpoints

#### 🔗 Redirects

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/redirect/{slug}` | Redireciona para URL original |
| `GET` | `/redirect` | Lista todos os redirects |
| `POST` | `/redirect` | Cria novo redirect |
| `PUT` | `/redirect/{id}` | Atualiza redirect |
| `DELETE` | `/redirect/{id}` | Deleta redirect |
| `PATCH` | `/redirect/{id}/toggle` | Habilita/Desabilita redirect |

#### 📄 Páginas Web

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/` | Página inicial |
| `GET` | `/health` | Status da aplicação |
| `GET` | `/error/404` | Página de erro 404 |

### Exemplos de Requisições

#### Criar Redirect

```bash
POST /redirect
Content-Type: application/json

{
  "slug": "github",
  "url": "https://github.com/bielsolosos",
  "isEnabled": true
}
```

**Resposta:**
```json
{
  "id": 1,
  "slug": "github",
  "url": "https://github.com/bielsolosos",
  "isEnabled": true
}
```

#### Atualizar Redirect

```bash
PUT /redirect/1
Content-Type: application/json

{
  "slug": "github-new",
  "url": "https://github.com/bielsolosos/rdl",
  "isEnabled": true
}
```

#### Habilitar/Desabilitar

```bash
PATCH /redirect/1/toggle
```

#### Deletar Redirect

```bash
DELETE /redirect/1
```

**Resposta:**
```json
{
  "message": "Redirect deletado com sucesso"
}
```

### Tratamento de Erros

Todas as respostas de erro seguem o padrão:

```json
{
  "message": "Descrição do erro"
}
```

**Códigos HTTP:**
- `200` - Sucesso
- `400` - Erro de validação/negócio
- `404` - Recurso não encontrado
- `500` - Erro interno do servidor

---

## 📁 Estrutura do Projeto

```
rdl/
├── src/
│   ├── main/
│   │   ├── java/space/bielsolososdev/rdl/
│   │   │   ├── api/                          # Camada de API
│   │   │   │   ├── controller/
│   │   │   │   │   ├── rest/                 # Controllers REST
│   │   │   │   │   │   ├── HomeRestController.java
│   │   │   │   │   │   └── UrlRedirectController.java
│   │   │   │   │   └── web/                  # Controllers Web
│   │   │   │   │       ├── ErrorController.java
│   │   │   │   │       └── HomeController.java
│   │   │   │   ├── mapper/                   # Mappers DTO <-> Entity
│   │   │   │   │   └── UrlRedirectMapper.java
│   │   │   │   └── model/                    # DTOs/Records
│   │   │   │       ├── HealthStatusResponse.java
│   │   │   │       ├── MessageResponse.java
│   │   │   │       └── urlredirect/
│   │   │   │           ├── UrlRedirectRequest.java
│   │   │   │           └── UrlRedirectResponse.java
│   │   │   │
│   │   │   ├── core/                         # Camada Core
│   │   │   │   └── exception/
│   │   │   │       ├── BusinessException.java
│   │   │   │       ├── RedirectException.java
│   │   │   │       └── globalconfig/
│   │   │   │           └── GlobalExceptionHandler.java
│   │   │   │
│   │   │   ├── domain/                       # Camada de Domínio
│   │   │   │   └── url/
│   │   │   │       ├── model/
│   │   │   │       │   └── UrlRedirect.java  # Entity
│   │   │   │       ├── repository/
│   │   │   │       │   └── UrlRedirectRepository.java
│   │   │   │       └── service/
│   │   │   │           └── UrlRedirectService.java
│   │   │   │
│   │   │   └── RdlApplication.java           # Main class
│   │   │
│   │   └── resources/
│   │       ├── db/migration/                 # Flyway migrations
│   │       │   └── V1__create_url_redirect_table.sql
│   │       ├── templates/                    # Thymeleaf templates
│   │       │   ├── layout/
│   │       │   │   └── base.html
│   │       │   ├── error/
│   │       │   │   └── 404.html
│   │       │   ├── index.html
│   │       │   └── health.html
│   │       └── application.yml
│   │
│   └── test/                                 # Testes
│
├── .env.example                              # Exemplo de variáveis
├── pom.xml                                   # Maven dependencies
└── README.md
```

### Camadas do Projeto

#### 🌐 API Layer
Responsável pela comunicação com o mundo externo (REST APIs e páginas web).

- **Controllers REST**: Endpoints da API
- **Controllers Web**: Páginas HTML com Thymeleaf
- **DTOs**: Objetos de transferência de dados
- **Mappers**: Conversão entre DTOs e Entities

#### 💼 Domain Layer
Contém as regras de negócio e lógica da aplicação.

- **Entities**: Modelos do banco de dados (JPA)
- **Repositories**: Interface com o banco (Spring Data)
- **Services**: Lógica de negócio

#### ⚙️ Core Layer
Configurações e funcionalidades transversais.

- **Exceptions**: Exceções customizadas
- **Global Handlers**: Tratamento centralizado de erros

---

## 🗄️ Banco de Dados

### Modelo de Dados

#### Tabela: `urls_redirect`

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| `id` | BIGSERIAL | ID único (chave primária) |
| `slug` | VARCHAR(50) | Código curto da URL (único) |
| `url` | VARCHAR(255) | URL original completa |
| `is_enabled` | BOOLEAN | Status de ativação |
| `created_at` | TIMESTAMP | Data de criação |
| `updated_at` | TIMESTAMP | Data de atualização |

**Índices:**
- `idx_slug` - Índice único no campo slug (busca rápida)
- `idx_is_enabled` - Índice no campo is_enabled (filtros)

### Migrations

O projeto utiliza **Flyway** para versionamento do banco de dados.

Localização: `src/main/resources/db/migration/`

#### V1__create_url_redirect_table.sql
```sql
CREATE TABLE urls_redirect (
    id BIGSERIAL PRIMARY KEY,
    slug VARCHAR(50) UNIQUE NOT NULL,
    url VARCHAR(255) NOT NULL,
    is_enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_slug ON urls_redirect(slug);
CREATE INDEX idx_is_enabled ON urls_redirect(is_enabled);
```

---

## 🎨 Interface

### Páginas Disponíveis

#### 🏠 Home (`/`)
Página inicial com informações do sistema e status online.

#### 🏥 Health Check (`/health`)
Dashboard com informações detalhadas:
- Status da aplicação
- Conexão com banco de dados
- Configurações do sistema
- Uptime e performance

#### ❌ Erro 404 (`/error/404`)
Página customizada para URLs não encontradas, com:
- Design elegante com DaisyUI
- Informações sobre o erro
- Slug solicitado (quando disponível)
- Botões de navegação

### Tema

O projeto utiliza o tema **Dark** do DaisyUI, proporcionando:
- ✅ Interface moderna e profissional
- ✅ Melhor experiência visual
- ✅ Componentes responsivos
- ✅ Animações suaves

---

## 🔒 Segurança e Validações

### Validações Implementadas

- ✅ Slug único no sistema
- ✅ URL única no sistema
- ✅ Validação de campos obrigatórios
- ✅ Verificação de existência antes de atualizar/deletar
- ✅ Validação de tamanhos máximos (slug: 50, url: 255)

### Tratamento de Exceções

O **GlobalExceptionHandler** captura e trata automaticamente:

1. **BusinessException** → Retorna JSON com mensagem de erro (HTTP 400)
2. **RedirectException** → Redireciona para página 404 customizada
3. **Exception genérica** → Retorna mensagem de erro interno (HTTP 500)

---

## 🚦 Como Usar

### 1. Acessar a Aplicação
```
http://localhost:8080
```

### 2. Criar um Redirect
Use a API REST ou integre com um frontend:

```bash
curl -X POST http://localhost:8080/redirect \
  -H "Content-Type: application/json" \
  -d '{
    "slug": "meu-link",
    "url": "https://exemplo.com/pagina-muito-longa",
    "isEnabled": true
  }'
```

### 3. Acessar o Link Curto
```
http://localhost:8080/redirect/meu-link
```

O usuário será automaticamente redirecionado para a URL original!

---

<div align="center">

**⭐ Se este projeto foi útil, considere dar uma estrela no GitHub!**

</div>
