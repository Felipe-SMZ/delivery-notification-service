# delivery-notification-service

Microsserviço responsável por notificar clientes em cada etapa do pedido. Atua exclusivamente como **Consumer Kafka**, escutando os tópicos de todos os outros serviços e registrando as notificações no banco de dados.

---

## Sobre o serviço

Este serviço faz parte de um sistema de delivery construído com arquitetura orientada a eventos. Ele consome eventos de todos os tópicos do sistema e simula o envio de notificações ao cliente em cada etapa do fluxo.

```
order-service      → tópico: pedido-criado      → notification-service → banco de dados
restaurant-service → tópico: pedido-aceito       → notification-service → banco de dados
delivery-service   → tópico: pedido-saiu-entrega → notification-service → banco de dados
delivery-service   → tópico: pedido-entregue     → notification-service → banco de dados
```

---

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Data JPA
- Spring for Apache Kafka
- MySQL
- Maven

---

## Pré-requisitos

- Java 21+
- Maven
- MySQL rodando na porta `3307`
- Apache Kafka rodando na porta `9092`

> Para subir o Kafka e o MySQL localmente, use o `docker-compose.yml` disponível no repositório [delivery-infra](https://github.com/Felipe-SMZ/delivery-infra).

---

## Configuração

Copie o arquivo de exemplo e preencha com suas credenciais:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

---

## Como rodar

```bash
# Clone o repositório
git clone https://github.com/Felipe-SMZ/delivery-notification-service

# Entre na pasta
cd delivery-notification-service

# Compile e rode
./mvnw spring-boot:run
```

O serviço sobe na porta `8083`.

---

## Tópicos Kafka

| Tópico | Tipo | Notificação disparada |
|--------|------|-----------------------|
| `pedido-criado` | Consumer | Pedido recebido e em processamento |
| `pedido-aceito` | Consumer | Pedido aceito pelo restaurante |
| `pedido-saiu-entrega` | Consumer | Pedido saiu para entrega |
| `pedido-entregue` | Consumer | Pedido entregue com sucesso |

---

## Fluxo das notificações

Para cada evento consumido, o serviço:

1. Desserializa o JSON recebido para a classe de evento correspondente
2. Cria um registro de `Notificacao` no banco com o tipo, destinatário e timestamp
3. Loga a mensagem simulando o envio ao cliente

Em um sistema real, o log seria substituído por uma integração com um serviço de e-mail (SendGrid, Amazon SES) ou push notification.

---

## Estrutura do projeto

```
src/main/java/com/felipeshimizu/deliverynotificationservice
├── consumer         # Consumo de eventos do Kafka
├── service          # Regras de negócio e persistência
├── repository       # Acesso ao banco
├── model            # Entidades JPA
│   └── enums        # TipoNotificacao
├── event            # Eventos consumidos do Kafka
├── exception        # Exceções e handler global
```

---

## Decisões de arquitetura

**Por que esse serviço não publica eventos?**
O `notification-service` é o fim da cadeia. Sua única responsabilidade é reagir a eventos e notificar o cliente. Publicar eventos daqui criaria acoplamento desnecessário.

**Por que registrar as notificações no banco?**
Para ter um histórico auditável de todas as notificações enviadas. Em produção, isso permitiria reenviar notificações que falharam e gerar relatórios de entrega.

**Por que não usar `@Transactional` com `EntityManager` diretamente?**
O Spring não intercepta chamadas internas entre métodos da mesma classe, o que impede a propagação correta da transação. A solução adotada foi usar `saveAndFlush()` do repositório, que garante o `INSERT` imediato sem depender de propagação de transação.

**Por que banco próprio?**
Cada microsserviço tem seu próprio banco de dados, sem compartilhamento de tabelas. O `pedidoId` armazenado em `notificacoes` é apenas uma referência aos outros serviços, sem FK real entre bancos.

---

## Outros serviços

| Serviço | Descrição |
|---------|-----------|
| [delivery-order-service](https://github.com/Felipe-SMZ/delivery-order-service) | Recebe e gerencia pedidos |
| [delivery-restaurant-service](https://github.com/Felipe-SMZ/delivery-restaurant-service) | Processa e aceita pedidos |
| [delivery-delivery-service](https://github.com/Felipe-SMZ/delivery-delivery-service) | Gerencia entregadores e entregas |
| [delivery-infra](https://github.com/Felipe-SMZ/delivery-infra) | Docker Compose com Kafka e MySQL |