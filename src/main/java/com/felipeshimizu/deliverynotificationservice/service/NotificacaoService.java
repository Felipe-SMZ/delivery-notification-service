package com.felipeshimizu.deliverynotificationservice.service;

import com.felipeshimizu.deliverynotificationservice.model.Notificacao;
import com.felipeshimizu.deliverynotificationservice.model.enums.TipoNotificacao;
import com.felipeshimizu.deliverynotificationservice.repository.NotificacaoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificacaoService {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoService.class);

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public void notificarPedidoCriado(UUID pedidoId, UUID clienteId) {
        salvarELogar(pedidoId, clienteId.toString() + "@email.com", TipoNotificacao.PEDIDO_CRIADO,
                "Seu pedido foi recebido e está sendo processado.");
    }

    public void notificarPedidoAceito(UUID pedidoId) {
        salvarELogar(pedidoId, "cliente@email.com", TipoNotificacao.PEDIDO_ACEITO,
                "Seu pedido foi aceito e está sendo preparado.");
    }

    public void notificarSaiuEntrega(UUID pedidoId) {
        salvarELogar(pedidoId, "cliente@email.com", TipoNotificacao.SAIU_PARA_ENTREGA,
                "Seu pedido saiu para entrega!");
    }

    public void notificarEntregue(UUID pedidoId) {
        salvarELogar(pedidoId, "cliente@email.com", TipoNotificacao.ENTREGUE,
                "Seu pedido foi entregue. Bom apetite!");
    }

    @Transactional
    public void salvarELogar(UUID pedidoId, String destinatario, TipoNotificacao tipo, String mensagem) {
        Notificacao notificacao = new Notificacao();
        notificacao.setPedidoId(pedidoId);
        notificacao.setDestinatario(destinatario);
        notificacao.setTipo(tipo);
        notificacaoRepository.save(notificacao);
        log.info("Notificação [{}] enviada para {} - Pedido {}: {}", tipo, destinatario, pedidoId, mensagem);
    }
}
