# Sistema ATM - Controle de Gastos com Abstract Factory

## Integrantes
Michely Serras <br>
Fernando Antonio <br>
Guilherme Soares <br>

## 🏗️ Arquitetura

Este sistema implementa o **Abstract Factory Pattern** para criar diferentes tipos de contas de usuário em um sistema de controle de gastos de cartões.

### Abstract Factory Pattern Implementado

```
AccountFactory (Abstract)
├── PJAccountFactory (Pessoa Jurídica)
│   ├── AdminPJAccount - Sem limite de cartões, acesso total
│   ├── PremiumPJAccount - Sem limite de cartões, relatórios avançados  
│   └── FreePJAccount - Máximo 2 cartões, relatórios básicos
└── CLTAccountFactory (Pessoa Física)
    ├── AdminCLTAccount - Sem limite de cartões, acesso total
    ├── PremiumCLTAccount - Sem limite de cartões, relatórios avançados
    └── FreeCLTAccount - Máximo 2 cartões, relatórios básicos
```

## 🚀 Como Executar

1. **Clonar e compilar:**
```bash
mvn clean install
mvn spring-boot:run
```

2. **Acessar aplicação:**
- Aplicação: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- Demo API: http://localhost:8080/api/factory/demo-factory

## 📊 Banco de Dados H2

**Configurações do H2 Console:**
- JDBC URL: `jdbc:h2:mem:atmdb`
- Username: `sa`
- Password: `password`

## 🧪 Usuários de Teste

| Username | Password | Tipo | Pessoa | Características |
|----------|----------|------|--------|-----------------|
| `admin_pj` | `123456` | Admin | PJ | Cartões ilimitados, gerencia usuários |
| `premium_clt` | `123456` | Premium | CLT | Cartões ilimitados, relatórios avançados |
| `free_clt` | `123456` | Free | CLT | Máximo 2 cartões, relatórios básicos |

## 🔧 API Endpoints

### Autenticação
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin_pj",
  "password": "123456"
}
```

### Informações da Conta (Demonstra Factory)
```http
GET /api/factory/account-info/{username}
```

### Criar Cartão
```http
POST /api/cards
Content-Type: application/json

{
  "username": "premium_clt",
  "cardNumber": "1234567890123456",
  "cardHolderName": "João Premium Silva",
  "cardType": "CREDIT",
  "cardBrand": "VISA"
}
```

### Listar Cartões do Usuário
```http
GET /api/cards/{username}
```

## 🏭 Como o Abstract Factory Funciona

1. **Identificação do Tipo:** O sistema identifica se o usuário é PJ ou CLT
2. **Seleção da Factory:** Escolhe `PJAccountFactory` ou `CLTAccountFactory`
3. **Criação da Conta:** A factory cria o tipo específico (Admin/Premium/Free)
4. **Aplicação de Regras:** Cada tipo tem suas próprias regras e limitações

### Exemplo de Uso:
```java
// O sistema automaticamente usa o padrão:
AccountFactory factory = AccountFactory.getFactory(user.getPersonType());
Account account = factory.createAccount(user.getUserType(), user);

// Agora 'account' tem as regras específicas do tipo criado
boolean canCreate = account.canCreateCard();
int maxCards = account.getMaxCardsAllowed();
```

## 🎯 Funcionalidades por Tipo

### Admin (PJ/CLT)
- ✅ Cartões ilimitados
- ✅ Relatórios avançados  
- ✅ Gerenciar outros usuários
- ✅ Todas as funcionalidades

### Premium (PJ/CLT)
- ✅ Cartões ilimitados
- ✅ Relatórios avançados
- ❌ Não pode gerenciar usuários

### Free (PJ/CLT)
- ⚠️ Máximo 2 cartões
- ❌ Apenas relatórios básicos
- ❌ Não pode gerenciar usuários

## 🔍 Validações Implementadas

- **Limite de cartões:** Free users não podem criar mais de 2 cartões
- **Autenticação:** Senha criptografada com BCrypt
- **Documentos únicos:** CPF/CNPJ não podem ser duplicados
- **Propriedade de cartões:** Usuários só podem operar seus próprios cartões

## 🗂️ Estrutura do Projeto

```
src/main/java/com/ifsp/atm/
├── factory/          # Abstract Factory Pattern
│   ├── Account.java
│   ├── AccountFactory.java
│   ├── PJAccountFactory.java
│   ├── CLTAccountFactory.java
│   └── [Concrete Accounts...]
├── model/           # Entidades JPA
├── repository/      # Repositórios Spring Data
├── service/         # Lógica de negócio
├── controller/      # Controllers REST
└── config/          # Configurações
```

## 💡 Demonstração

Acesse: http://localhost:8080/api/factory/demo-factory

Este endpoint mostra como o Abstract Factory está funcionando e fornece exemplos de uso.

## 🏷️ Tecnologias

- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database**
- **Lombok**
- **Java 21**

---

**Desenvolvido para demonstrar o Abstract Factory Pattern em um sistema real de controle de gastos! 🎉**
