package com.felipeshimizu.deliverynotificationservice.event;

import java.util.UUID;

public class PedidoSaiuEntregaEvent {

    private UUID pedidoId;
    private UUID entregadorId;

    public PedidoSaiuEntregaEvent() {
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }

    public UUID getEntregadorId() {
        return entregadorId;
    }

    public void setEntregadorId(UUID entregadorId) {
        this.entregadorId = entregadorId;
    }
}
