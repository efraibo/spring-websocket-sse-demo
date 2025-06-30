# Spring WebSocket SSE Demo com Sistema de Pedidos

Este projeto demonstra o uso de Server-Sent Events (SSE), WebSockets e persistência com Spring Boot 3, incluindo um sistema completo de pedidos com notificações em tempo real.

## 🚀 Funcionalidades

- **SSE com WebFlux**: Endpoint reativo para envio de eventos do servidor para o cliente
- **SSE com Spring MVC**: Endpoint tradicional usando `HttpServletResponse`
- **WebSocket**: Suporte a comunicação bidirecional em tempo real
- **Sistema de Pedidos**: CRUD de pedidos com banco H2
- **Notificações em Tempo Real**: SSE notifica automaticamente quando pedidos são criados
- **Banco H2**: Banco em memória com console web habilitado

## 📊 Tecnologias Utilizadas

- Spring Boot 3.2.0
- Spring WebFlux (Reativo)
- Spring MVC (Tradicional)
- Spring Data JPA
- Banco H2
- Java Records
- Server-Sent Events (SSE)
- WebSockets

## 🔗 Endpoints

### API de Pedidos
- `POST /api/pedidos` - Criar novo pedido
  - Body: `{"descricao": "string", "valor": number}`
  - Retorna: Pedido criado com ID gerado

### SSE (Server-Sent Events)
- `GET /sse/stream` - SSE reativo (WebFlux)
- `GET /sse/stream-mvc-alt` - SSE tradicional (MVC)

### WebSocket
- `ws://<host>:<porta>/ws` - Endpoint WebSocket

### Banco de Dados
- `GET /h2-console` - Console web do banco H2
  - URL JDBC: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## 🏃‍♂️ Como rodar

### 1. Pré-requisitos
- Java 17+
- Maven 3.8+

### 2. Executar aplicação
```bash
mvn spring-boot:run
```

### 3. Testar funcionalidades

#### Via Postman
Importe a collection: `Postman_Collection_Spring_SSE_Pedidos.json`

#### Via cURL
Consulte exemplos em: `curl-examples.md`

#### Via navegador
Abra o arquivo: `teste-pedidos.html`

## 🧪 Exemplo de Teste Completo

### Terminal 1 - Conectar ao SSE (WebFlux)
```bash
curl -N -H "Accept: text/event-stream" http://localhost:8080/sse/stream
```

### Terminal 2 - Criar pedidos
```bash
# Criar primeiro pedido
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"descricao": "Notebook Dell", "valor": 2500.00}'

# Criar segundo pedido
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"descricao": "Mouse Gamer", "valor": 150.75}'
```

### Resultado Esperado
No Terminal 1, você verá as notificações SSE em tempo real:
```
data: Novo pedido criado! ID: 1, Descrição: Notebook Dell, Valor: R$ 2500.0
data: Novo pedido criado! ID: 2, Descrição: Mouse Gamer, Valor: R$ 150.75
```

## 🗂️ Estrutura do Projeto

```
src/main/java/com/example/demo/
├── DemoApplication.java          # Classe principal
├── Pedido.java                   # Entidade JPA
├── PedidoRepository.java         # Repositório JPA
├── PedidoService.java           # Serviço com broadcasting SSE
├── PedidoController.java        # Controller REST (com Record)
├── SseController.java           # SSE WebFlux (Reativo)
├── SseMvcController.java        # SSE MVC (Tradicional)
├── SimpleWebSocketHandler.java  # Handler WebSocket
└── WebSocketConfig.java         # Configuração WebSocket

src/main/resources/
└── application.properties       # Configurações H2 e JPA

Arquivos de teste/
├── teste-pedidos.html                        # Interface web para testes
├── Postman_Collection_Spring_SSE_Pedidos.json # Collection Postman
└── curl-examples.md                          # Exemplos cURL
```

## 💡 Como Funciona

1. **Criar Pedido**: Cliente faz POST para `/api/pedidos`
2. **Persistir no Banco**: Pedido é salvo no H2 com ID auto-gerado
3. **Notificação SSE**: `PedidoService` envia evento via `Sinks.Many`
4. **Broadcast**: Todos os clientes conectados aos endpoints SSE recebem a notificação
5. **Tempo Real**: Notificação aparece instantaneamente nos clientes

## 🔄 Diferenças entre os SSE

### WebFlux (`/sse/stream`)
- Abordagem reativa com `Flux`
- Usa `Sinks.Many` para broadcasting
- Não-bloqueante
- Mais eficiente para muitas conexões

### MVC (`/sse/stream-mvc-alt`)
- Abordagem tradicional com `HttpServletResponse`
- Usa `PrintWriter` para escrita direta
- Bloqueante por conexão
- Mais simples de entender

## 🛠️ Configurações

### Banco H2
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create-drop
```

### CORS
Todos os endpoints têm `@CrossOrigin` habilitado para facilitar testes locais.

## 📝 Próximos Passos

- [ ] Adicionar validação nos DTOs
- [ ] Implementar paginação na listagem de pedidos
- [ ] Adicionar autenticação
- [ ] Melhorar tratamento de erros
- [ ] Adicionar testes unitários
