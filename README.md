# Spring WebSocket SSE Demo

Este projeto demonstra o uso de Server-Sent Events (SSE) e WebSockets com Spring Boot 3.

## Funcionalidades
- **SSE com WebFlux**: Endpoint reativo para envio de eventos do servidor para o cliente.
- **SSE com Spring MVC**: Endpoint tradicional usando `HttpServletResponse`.
- **WebSocket**: Suporte a comunicação bidirecional em tempo real.

## Endpoints

### SSE
- `GET /sse/stream` - SSE reativo (WebFlux)
- `GET /sse/stream-mvc` - SSE tradicional (MVC, no mesmo controller)
- `GET /sse/stream-mvc-alt` - SSE tradicional (MVC, em outro controller)

### WebSocket
- `ws://<host>:<porta>/ws` - Endpoint WebSocket

## Como rodar

1. **Pré-requisitos**:
   - Java 17+
   - Maven 3.8+

2. **Build e execução**:
   ```sh
   mvn clean package
   java -jar target/demo-1.0.0.jar
   ```

3. **Acessando os endpoints**:
   - Use um navegador ou ferramentas como `curl` ou Postman para testar os endpoints SSE.
   - Para WebSocket, use um cliente WebSocket (ex: [websocat](https://github.com/vi/websocat), extensões de navegador, etc).

## Observações
- Certifique-se de que arquivos de build e IDE estejam no `.gitignore`.
- Para autenticação no GitHub, utilize um token de acesso pessoal (PAT).

## Licença
MIT

