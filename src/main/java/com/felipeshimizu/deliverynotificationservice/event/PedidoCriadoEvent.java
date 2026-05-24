package com.felipeshimizu.deliverynotificationservice.event;

import java.util.UUID;

public class PedidoCriadoEvent {
    private UUID pedidoId;
    private UUID clienteId;

    public PedidoCriadoEvent() {
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }
}
