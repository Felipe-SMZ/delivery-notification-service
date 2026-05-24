package com.felipeshimizu.deliverynotificationservice.consumer;

import com.felipeshimizu.deliverynotificationservice.event.PedidoAceitoEvent;
import com.felipeshimizu.deliverynotificationservice.event.PedidoCriadoEvent;
import com.felipeshimizu.deliverynotificationservice.event.PedidoEntregueEvent;
import com.felipeshimizu.deliverynotificationservice.event.PedidoSaiuEntregaEvent;
import com.felipeshimizu.deliverynotificationservice.service.NotificacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class NotificacaoConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoConsumer.class);

    private final ObjectMapper objectMapper;
    private final NotificacaoService notificacaoService;

    public NotificacaoConsumer(ObjectMapper objectMapper, NotificacaoService notificacaoService) {
        this.objectMapper = objectMapper;
        this.notificacaoService = notificacaoService;
    }

    @KafkaListener(topics = "pedido-criado", groupId = "${spring.kafka.consumer.group-id}")
    public void criado(String mensagem) {
        try {
            PedidoCriadoEvent event = objectMapper.readValue(mensagem, PedidoCriadoEvent.class);
            log.info("Evento recebido para o pedido {}", event.getPedidoId());
            notificacaoService.notificarPedidoCriado(event.getPedidoId(), event.getClienteId());
        } catch (Exception e) {
            log.error("Erro ao processar evento: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "pedido-aceito", groupId = "${spring.kafka.consumer.group-id}")
    public void aceito(String mensagem) {
        try {
            PedidoAceitoEvent event = objectMapper.readValue(mensagem, PedidoAceitoEvent.class);
            log.info("Evento recebido para o pedido {}", event.getPedidoId());
            notificacaoService.notificarPedidoAceito(event.getPedidoId());
        } catch (Exception e) {
            log.error("Erro ao processar evento: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "pedido-saiu-entrega", groupId = "${spring.kafka.consumer.group-id}")
    public void saiuEntrega(String mensagem) {
        try {
            PedidoSaiuEntregaEvent event = objectMapper.readValue(mensagem, PedidoSaiuEntregaEvent.class);
            log.info("Evento recebido para o pedido {}", event.getPedidoId());
            notificacaoService.notificarSaiuEntrega(event.getPedidoId());
        } catch (Exception e) {
            log.error("Erro ao processar evento: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "pedido-entregue", groupId = "${spring.kafka.consumer.group-id}")
    public void entregue(String mensagem) {
        try {
            PedidoEntregueEvent event = objectMapper.readValue(mensagem, PedidoEntregueEvent.class);
            log.info("Evento recebido para o pedido {}", event.getPedidoId());
            notificacaoService.notificarEntregue(event.getPedidoId());
        } catch (Exception e) {
            log.error("Erro ao processar evento: {}", e.getMessage(), e);
        }
    }
}
