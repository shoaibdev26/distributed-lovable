🚀 Lovable Clone

A full-stack AI-powered application development platform inspired by Lovable, built using Java, Spring Boot, React, Microservices, Spring AI, PostgreSQL, Docker, and Kubernetes.

The platform allows users to describe an application in natural language and use AI to assist with application generation, project management, workspace management, and code-related operations.

📌 Project Overview

The Lovable Clone is designed as a distributed, cloud-ready application following modern Microservices Architecture principles.

The primary goal of this project is to build an AI-powered platform where users can interact with an AI assistant to generate and manage application projects.

The system demonstrates real-world enterprise concepts including:

Microservices Architecture
RESTful APIs
AI integration using Spring AI
JWT-based authentication
Event-driven communication
PostgreSQL databases
pgvector
Git repository integration
Object storage using MinIO
Docker containerization
Kubernetes deployment
CI/CD automation
Service-to-service communication
Centralized configuration
API Gateway




The application follows a distributed microservices architecture.

                         ┌─────────────────────┐
                         │      React UI       │
                         │    Frontend App     │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │    API Gateway      │
                         └──────────┬──────────┘
                                    │
              ┌─────────────────────┼─────────────────────┐
              │                     │                     │
              ▼                     ▼                     ▼
      ┌──────────────┐      ┌──────────────┐      ┌──────────────┐
      │   Account    │      │   Workspace   │      │ Intelligence │
      │   Service    │      │   Service     │      │   Service    │
      └──────┬───────┘      └──────┬───────┘      └──────┬───────┘
             │                     │                     │
             ▼                     ▼                     ▼
      ┌──────────────┐      ┌──────────────┐      ┌──────────────┐
      │ PostgreSQL   │      │ PostgreSQL   │      │ PostgreSQL   │
      │ Account DB   │      │ Workspace DB  │      │ Intelligence │
      └──────────────┘      └──────────────┘      │  │
                                                   └──────────────┘

                         ┌─────────────────────┐
                         │   Config Service    │
                         └─────────────────────┘

                         ┌─────────────────────┐
                         │       Kafka         │
                         │ Event Communication │
                         └─────────────────────┘

                         ┌─────────────────────┐
                         │       MinIO         │
                         │    Object Storage   │
                         └─────────────────────┘


## Microservices Overview

### Account Service

**Primary Responsibility**

* User identity and authentication
* Subscription and billing management
* Stripe payment integration

**Database / Storage**

* PostgreSQL

  * Users
  * Plans
  * Subscriptions

**Key Integrations**

* Stripe API

**Cross-Service Calls**

* Feign
* Calls `Workspace Service` to validate users and subscription limits

---

### Workspace Service

**Primary Responsibility**

* Project management
* Project members
* File tree management
* Kubernetes deployment management

**Database / Storage**

* PostgreSQL

  * Projects
  * Members
* MinIO

  * Project files and artifacts

**Key Integrations**

* Kubernetes API
* Redis

**Cross-Service Calls**

* Calls `Account Service` to validate users and subscription limits

---

### Intelligence Service

**Primary Responsibility**

* AI-powered chat
* Context gathering
* Code generation
* AI-assisted development

**Database / Storage**

* PostgreSQL

  * Chat Sessions
  * Messages
  * Events

**Key Integrations**

* OpenAI
* Spring AI
* MinIO

  * Reads project files for AI context

**Cross-Service Calls**

* Calls `Account Service` to validate AI usage limits
* Calls `Workspace Service` to gather project context and files

---

### Service Communication Overview

```text
                    ┌─────────────────────┐
                    │   Account Service   │
                    │                     │
                    │ Users               │
                    │ Authentication      │
                    │ Subscriptions       │
                    │ Stripe Billing      │
                    └──────────┬──────────┘
                               │
                    User / Limit Validation
                         ┌─────┴─────┐
                         │           │
                         ▼           ▼
              ┌────────────────┐  ┌─────────────────────┐
              │   Workspace    │  │   Intelligence      │
              │    Service     │  │      Service        │
              │                │  │                     │
              │ Projects       │  │ AI Chat             │
              │ Members        │  │ Context Gathering   │
              │ File Tree      │  │ Code Generation     │
              │ Deployments    │  │                     │
              └───────┬────────┘  └──────────┬──────────┘
                      │                      │
                      ▼                      ▼
                 ┌────────┐            ┌──────────┐
                 │ MinIO  │            │ OpenAI   │
                 │ Files  │            │Spring AI │
                 └────────┘            └──────────┘
```

### Service Responsibilities

| Microservice             | Responsibility                                    | Storage           | External Integrations    |
| ------------------------ | ------------------------------------------------- | ----------------- | ------------------------ |
| **Account Service**      | Identity, authentication, subscriptions & billing | PostgreSQL        | Stripe, Eureka           |
| **Workspace Service**    | Projects, members, files & deployments            | PostgreSQL, MinIO | Kubernetes API, Redis    |
| **Intelligence Service** | AI chat, context gathering & code generation      | PostgreSQL        | OpenAI, Spring AI, MinIO |

### Cross-Service Communication

* **Account → Workspace** — User validation and subscription/plan limit checks.
* **Account → Intelligence** — User validation and AI usage limit checks.
* **Workspace → Account** — User and subscription validation.
* **Intelligence → Account** — AI usage and subscription limit validation.
* **Intelligence → Workspace** — Project context and file retrieval.


## Account Service

### Category & Details

**Dependencies**

* Spring Boot Starter Web
* Spring Data JPA
* MapStruct
* Spring Cloud Eureka Client
* PostgreSQL
* Stripe Java SDK

**Key Entities**

* `User`
* `Subscription`

**Subscription Plans**

* `Free`
* `Pro`

**Security Setup**

* `AccountSecurityConfig`
* `/auth/**` → Public
* `/webhooks/stripe` → Public
* All other endpoints → Secured

**Authentication**

* JWT-based authentication

**Authorization**

* Role/permission-based access control

**Database**

* PostgreSQL

**Payment Integration**

* Stripe

**Service Discovery**

* Eureka Client

**Object Mapping**

* MapStruct

**API Layer**

* REST APIs using Spring Web

**Persistence Layer**

* Spring Data JPA / Hibernate

### Components & Responsibilities

| Component                   | Responsibility                                                              |
| --------------------------- | --------------------------------------------------------------------------- |
| **User Management**         | Handles user registration, authentication, profiles, and account operations |
| **Subscription Management** | Manages Free and Pro subscription plans                                     |
| **Authentication**          | Generates and validates JWT tokens                                          |
| **Security**                | Secures application endpoints using `AccountSecurityConfig`                 |
| **Stripe Integration**      | Handles payments, subscriptions, and Stripe webhook events                  |
| **Persistence**             | Stores user and subscription data in PostgreSQL                             |
| **Service Discovery**       | Registers the service with Eureka                                           |
| **DTO Mapping**             | Converts entities and DTOs using MapStruct                                  |

## Account Service

The **Account Service** is responsible for user identity, authentication, subscription management, and Stripe billing within the platform.

### Category & Details

| Category                 | Details                                                                                                                              |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------ |
| **Dependencies**         | `spring-boot-starter-web`, `spring-data-jpa`, `mapstruct`, `spring-cloud-starter-netflix-eureka-client`, `postgresql`, `stripe-java` |
| **Key Entities**         | `User`, `Subscription`                                                                                                               |
| **Subscription Plans**   | `Free`, `Pro`                                                                                                                        |
| **Security Setup**       | `AccountSecurityConfig`                                                                                                              |
| **Public Endpoints**     | `/auth/**`, `/webhooks/stripe`                                                                                                       |
| **Protected Endpoints**  | All other endpoints require authentication                                                                                           |
| **Internal API**         | `InternalAccountController` exposes `/internal/v1/...` endpoints for inter-service communication                                     |
| **Internal API Purpose** | Allows other microservices to fetch user DTOs and verify subscription/billing plans                                                  |
| **Database**             | PostgreSQL                                                                                                                           |
| **Payment Integration**  | Stripe                                                                                                                               |
| **Service Discovery**    | Eureka Client                                                                                                                        |
| **Object Mapping**       | MapStruct                                                                                                                            |
| **API Layer**            | Spring Web REST APIs                                                                                                                 |
| **Persistence Layer**    | Spring Data JPA / Hibernate                                                                                                          |

### Key Components

#### `AccountSecurityConfig`

Responsible for configuring authentication and authorization rules.

* `/auth/**` → Public
* `/webhooks/stripe` → Public
* All other endpoints → Secured
* Protects account and subscription-related APIs

#### `InternalAccountController`

Provides internal APIs used by other microservices for account-related information.

**Base Path**

```text
/internal/v1/...
```

**Responsibilities**

* Fetch user information as DTOs
* Verify user existence
* Verify subscription plans
* Validate billing/subscription status
* Provide account information required by other microservices

### Service Interaction

```text
┌─────────────────────────────┐
│      Account Service        │
│                             │
│  User Authentication        │
│  User Management            │
│  Subscription Management    │
│  Stripe Billing             │
│                             │
│ ┌─────────────────────────┐ │
│ │ InternalAccountController│ │
│ │ /internal/v1/...         │ │
│ └────────────┬────────────┘ │
└──────────────┼──────────────┘
               │
       Internal API Calls
               │
       ┌───────┴────────┐
       │                │
       ▼                ▼
┌──────────────┐  ┌──────────────────┐
│   Workspace  │  │   Intelligence   │
│   Service    │  │     Service      │
│              │  │                  │
│ User/Plan    │  │ User/AI Limits   │
│ Validation   │  │ Validation       │
└──────────────┘  └──────────────────┘
```

### Responsibilities

* **User Management** — Registration, authentication, and user account management.
* **Subscription Management** — Manages `Free` and `Pro` plans.
* **Stripe Billing** — Handles Stripe subscriptions and webhook events.
* **Authentication & Security** — Secures APIs using `AccountSecurityConfig`.
* **Internal APIs** — Provides trusted account information to other microservices through `InternalAccountController`.
* **Persistence** — Stores users and subscription information in PostgreSQL.
* **Service Discovery** — Registers with Eureka for microservice communication.
* **DTO Mapping** — Uses MapStruct for entity-to-DTO and DTO-to-entity mapping.

  ## Workspace Service

The **Workspace Service** is responsible for project management, project membership, file storage, preview environments, and Kubernetes-based application deployments.

### Category & Details

| Category                     | Details                                                                                                                                     |
| ---------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------- |
| **Dependencies**             | `spring-boot-starter-web`, `spring-data-jpa`, `minio`, `kubernetes-client` (Fabric8), `spring-data-redis`, `spring-cloud-starter-openfeign` |
| **Key Entities**             | `Project`, `ProjectMember`, `ProjectFile`, `Preview`                                                                                        |
| **Storage**                  | PostgreSQL, MinIO, Redis                                                                                                                    |
| **Cloud Configuration**      | `StorageConfig`, `KubernetesConfig`, `RedisConfig`                                                                                          |
| **Kubernetes Integration**   | Fabric8 Kubernetes Client                                                                                                                   |
| **Security Logic**           | `SecurityExpressions` with custom `@PreAuthorize` expressions                                                                               |
| **Service Communication**    | OpenFeign                                                                                                                                   |
| **Account Integration**      | `AccountClient`                                                                                                                             |
| **Project Limit Validation** | Verifies user's `maxProjects` limit during project creation                                                                                 |

### Key Components

#### `StorageConfig`

Responsible for configuring the **MinIO object storage** connection.

**Responsibilities**

* Configure MinIO endpoint and credentials
* Manage object storage access
* Support project file storage and retrieval

#### `KubernetesConfig`

Responsible for configuring the **Fabric8 Kubernetes Client**.

**Responsibilities**

* Connect to the Kubernetes cluster
* Configure Kubernetes client settings
* Support project deployment and preview management

#### `RedisConfig`

Responsible for configuring **Redis** integration.

**Responsibilities**

* Configure Redis connection
* Support caching and temporary project-related data
* Improve performance for frequently accessed information

#### `SecurityExpressions`

Contains custom Spring Security authorization logic for project-level access control.

Example:

```java
@PreAuthorize("@security.canEditProject(#id)")
```

The security expression verifies whether the authenticated user has permission to modify the requested project.

**Authorization Flow**

```text
Request
   │
   ▼
@PreAuthorize("@security.canEditProject(#id)")
   │
   ▼
SecurityExpressions
   │
   ▼
ProjectMemberRepository
   │
   ▼
Verify Project Membership / Ownership
   │
   ├── Authorized ──► Controller
   │
   └── Denied ─────► 403 Forbidden
```

### Feign Clients

#### `AccountClient`

Uses **Spring Cloud OpenFeign** to communicate with the Account Service.

**Primary Responsibility**

* Validate the authenticated user
* Check subscription information
* Verify the user's `maxProjects` limit

**Project Creation Flow**

```text
Create Project Request
        │
        ▼
   Workspace Service
        │
        ▼
     AccountClient
        │
        ▼
    Account Service
        │
        ▼
Check user's maxProjects limit
        │
   ┌────┴────┐
   │         │
Allowed    Limit Reached
   │         │
   ▼         ▼
Create     Reject
Project    Request
```

### Core Responsibilities

* **Project Management** — Create, update, and manage projects.
* **Project Membership** — Manage project members and access permissions.
* **File Management** — Store and retrieve project files using MinIO.
* **Preview Management** — Manage preview environments and their Kubernetes pod mappings.
* **Kubernetes Deployments** — Create and manage application deployments using the Fabric8 Kubernetes Client.
* **Authorization** — Enforce project-level ownership and membership permissions.
* **Caching** — Use Redis for caching and temporary data.
* **Account Integration** — Communicate with Account Service using OpenFeign.
* **Plan Limit Enforcement** — Verify `maxProjects` before allowing project creation.


## Intelligence Service

The **Intelligence Service** is responsible for AI-powered chat, context gathering, code generation, tool execution, and LLM usage tracking. It integrates with OpenAI through Spring AI and communicates with the Workspace and Account Services for project context and usage-limit validation.

### Category & Details

| Category               | Details                                                                               |
| ---------------------- | ------------------------------------------------------------------------------------- |
| **Dependencies**       | `spring-ai-starter-model-openai`, `spring-data-jpa`, `spring-cloud-starter-openfeign` |
| **Key Entities**       | `ChatSession`, `ChatMessage`, `ChatEvent`, `UsageLog`                                 |
| **AI Components**      | `AiGenerationServiceImpl`, `LLMResponseParser`                                        |
| **Advisors & Tools**   | `FileTreeContextAdvisor`, `CodeGenerationHelperTools`                                 |
| **Feign Clients**      | `WorkspaceClient`, `AccountClient`                                                    |
| **Common Library**     | `JwtAuthFilter`, shared authentication/security components                            |
| **AI Integration**     | OpenAI via Spring AI                                                                  |
| **Context Management** | File tree and project file context injection                                          |
| **Usage Tracking**     | Tracks LLM usage, thoughts, file edits, and tool executions                           |

### Key Components

#### `AiGenerationServiceImpl`

Core service responsible for executing AI prompts and processing LLM responses.

**Responsibilities**

* Execute prompts using Spring AI
* Manage AI generation requests
* Process LLM responses
* Extract `<file>` and `<tool>` tags from generated responses
* Coordinate AI tools and context advisors
* Persist chat and AI execution events

```text id="q8v0fd"
User Prompt
     │
     ▼
AiGenerationServiceImpl
     │
     ├──► FileTreeContextAdvisor
     │         │
     │         ▼
     │    Project Context
     │
     ├──► CodeGenerationHelperTools
     │         │
     │         ▼
     │    Read Required Files
     │
     ▼
   Spring AI
     │
     ▼
   OpenAI LLM
     │
     ▼
LLM Response
     │
     ▼
LLMResponseParser
     │
     ├──► <file> tags
     │
     └──► <tool> tags
```

### `LLMResponseParser`

Responsible for parsing structured information from LLM responses.

**Responsibilities**

* Extract `<file>` blocks from AI responses
* Extract `<tool>` instructions
* Parse generated code changes
* Identify requested tool executions
* Convert raw LLM output into structured application data

### Advisors & Tools

#### `FileTreeContextAdvisor`

Injects the current project file tree into the LLM prompt to provide project-aware context.

**Flow**

```text id="x7c9pz"
Workspace Service
       │
       ▼
  File Tree
       │
       ▼
FileTreeContextAdvisor
       │
       ▼
  AI Prompt
       │
       ▼
    OpenAI
```

#### `CodeGenerationHelperTools`

Provides tools that allow the LLM to interact with project files.

**Responsibilities**

* Read specific project files
* Provide file contents to the LLM
* Support context-aware code generation
* Enable tool-based AI workflows

### Feign Clients

#### `WorkspaceClient`

Communicates with the Workspace Service using OpenFeign.

**Responsibilities**

* Fetch project file trees
* Retrieve specific file contents
* Provide project context to the AI engine

#### `AccountClient`

Communicates with the Account Service to enforce AI usage limits.

**Responsibilities**

* Verify user's subscription
* Check daily AI token limits
* Validate whether the user can perform an AI request

### Common Library Integration

The Intelligence Service uses shared components from the **Common Library**.

#### `JwtAuthFilter`

Provides shared JWT authentication functionality across microservices.

**Responsibilities**

* Extract JWT tokens from requests
* Validate authentication information
* Populate the Spring Security context
* Provide authenticated user information to downstream services

### Usage Tracking

#### `UsageLog`

Tracks AI/LLM activity for monitoring and usage enforcement.

**Tracked Information**

* LLM usage
* Token consumption
* AI thoughts/reasoning events
* File edits
* Tool executions
* AI generation events

### AI Request Flow

```text id="q5x4k1"
                    User
                     │
                     ▼
            Intelligence Service
                     │
                     ▼
             AccountClient
                     │
                     ▼
          Check AI Daily Token Limit
                     │
              ┌──────┴──────┐
              │             │
            Allowed        Denied
              │             │
              ▼             ▼
       WorkspaceClient    Reject
              │
              ▼
       Fetch File Context
              │
              ▼
    FileTreeContextAdvisor
              │
              ▼
       AiGenerationService
              │
              ▼
          Spring AI
              │
              ▼
           OpenAI LLM
              │
              ▼
      LLMResponseParser
              │
        ┌─────┴─────┐
        │           │
     <file>       <tool>
        │           │
        ▼           ▼
   File Changes  Tool Execution
        │           │
        └─────┬─────┘
              ▼
          UsageLog
              │
              ▼
        ChatEvent / Response
```

### Core Responsibilities

* **AI Chat** — Provides conversational AI capabilities for projects.
* **Context Gathering** — Retrieves project file trees and file contents to build relevant AI context.
* **Code Generation** — Generates and processes code changes using OpenAI and Spring AI.
* **Tool Execution** — Allows the LLM to request project-file operations through defined tools.
* **Response Parsing** — Extracts structured `<file>` and `<tool>` instructions from LLM responses.
* **Usage Tracking** — Tracks token consumption, AI events, file edits, and tool usage.
* **Limit Enforcement** — Validates daily AI usage limits through Account Service.
* **Project Integration** — Retrieves project context through Workspace Service.
* **Authentication** — Uses shared JWT authentication components from the Common Library.

## Common Library

The **Common Library** is a shared module used across microservices to provide common security components, DTOs, and cross-service communication utilities.

It enables each microservice to independently authenticate incoming requests while preserving the authenticated user's identity during internal Feign calls.

### Category & Details

| Category                        | Components & Details                                           |
| ------------------------------- | -------------------------------------------------------------- |
| **Security**                    | `JwtAuthFilter`, `FeignClientInterceptor`                      |
| **Shared DTOs**                 | `UserDto`, `PlanDto`, `FileTreeDto`, etc.                      |
| **Authentication**              | Extracts and validates Bearer tokens                           |
| **Feign Security**              | Automatically propagates JWT tokens to outbound Feign requests |
| **Cross-Service Communication** | Preserves user identity across microservice calls              |
| **Architecture Benefit**        | Prevents JPA entities from crossing microservice boundaries    |

### Key Components

#### `JwtAuthFilter`

Responsible for authenticating incoming requests in each microservice.

**What it does**

* Extracts the JWT Bearer token from the `Authorization` header.
* Validates the token.
* Extracts user identity and authentication information.
* Populates the Spring `SecurityContext`.
* Allows each microservice to authenticate requests independently.

```text id="q7x2ma"
Incoming Request
      │
      ▼
Authorization: Bearer <JWT>
      │
      ▼
JwtAuthFilter
      │
      ▼
Validate JWT
      │
      ▼
Extract User Identity
      │
      ▼
SecurityContext
      │
      ▼
Controller / Service
```

### `FeignClientInterceptor`

Automatically propagates the authenticated user's JWT when making internal Feign requests.

**What it does**

* Reads the JWT from the current security context/request.
* Adds the JWT to outbound Feign requests.
* Preserves the user's identity between microservices.
* Allows downstream services to authenticate the request as the original user.

```text id="k9p3rw"
User Request
     │
     ▼
Workspace Service
     │
     │ JWT
     ▼
FeignClientInterceptor
     │
     │ Authorization: Bearer <JWT>
     ▼
Account / Intelligence Service
     │
     ▼
JwtAuthFilter
     │
     ▼
SecurityContext
```

### Shared DTOs

The Common Library provides shared **Data Transfer Objects** used for communication between microservices.

Examples:

* `UserDto`
* `PlanDto`
* `FileTreeDto`
* Other service-to-service response/request DTOs

**Why DTOs are used**

Microservices exchange lightweight contracts instead of exposing their internal JPA entities.

```text id="w2m6hf"
Account Service
     │
     │ User Entity
     ▼
   Mapping
     │
     ▼
  UserDto
     │
     │ REST / Feign
     ▼
Workspace / Intelligence Service
```

This prevents `@Entity` classes and database-specific implementation details from leaking across microservice boundaries.

### Why the Common Library is Needed

#### Independent Authentication

Every microservice can authenticate requests **locally and independently**, without relying exclusively on the API Gateway for security.

```text id="v4r8nz"
                ┌───────────────┐
                │ API Gateway   │
                └───────┬───────┘
                        │
          ┌─────────────┼─────────────┐
          │             │             │
          ▼             ▼             ▼
     Account       Workspace     Intelligence
     Service        Service         Service
        │              │              │
   JwtAuthFilter  JwtAuthFilter  JwtAuthFilter
        │              │              │
        ▼              ▼              ▼
 SecurityContext SecurityContext SecurityContext
```

Each service therefore has its own security boundary.

#### Seamless Feign Communication

The `FeignClientInterceptor` ensures that internal service calls continue to carry the identity of the logged-in user.

For example:

```text id="m3s8qa"
User
 │
 │ JWT
 ▼
Intelligence Service
 │
 │ Feign Request + JWT
 ▼
Workspace Service
 │
 ▼
JwtAuthFilter
 │
 ▼
Authenticated User
```

This allows the Intelligence Service to access Workspace resources **as the currently authenticated user**, rather than using a separate anonymous or system identity.

#### Prevents Entity Leakage

The Common Library promotes DTO-based communication between services.

```text id="p6d1xy"
┌──────────────────┐
│  Account Service │
│                  │
│  User @Entity    │
└────────┬─────────┘
         │
         │ Mapping
         ▼
      UserDto
         │
         │ Feign / REST
         ▼
┌──────────────────┐
│ Workspace / AI   │
│     Service      │
└──────────────────┘
```

This keeps each microservice's **database entities and persistence implementation private**.

### Common Library Architecture

```text id="r5k2tc"
                 ┌──────────────────────┐
                 │     Common Lib       │
                 │                      │
                 │  JwtAuthFilter       │
                 │  FeignInterceptor    │
                 │  Shared DTOs         │
                 │  Security Utilities  │
                 └──────────┬───────────┘
                            │
              ┌─────────────┼─────────────┐
              │             │             │
              ▼             ▼             ▼
       Account Service  Workspace      Intelligence
                         Service         Service
              │             │             │
              └─────────────┼─────────────┘
                            │
                     Shared Contracts
```

### Key Benefits

* **Independent Security** — Every microservice validates JWTs locally.
* **JWT Propagation** — User identity is automatically propagated through Feign calls.
* **Shared Contracts** — Common DTOs provide consistent service-to-service communication.
* **Loose Coupling** — JPA entities remain internal to their owning service.
* **Reusable Components** — Security and communication logic is implemented once and reused across services.
* **Microservice Boundaries** — Database and persistence models remain isolated between services.



