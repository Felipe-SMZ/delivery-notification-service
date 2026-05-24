package com.felipeshimizu.deliverynotificationservice.event;

import java.util.UUID;

public class PedidoAceitoEvent {

    private UUID pedidoId;
    private UUID restauranteId;

    public PedidoAceitoEvent() {
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }

    public UUID getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(UUID restauranteId) {
        this.restauranteId = restauranteId;
    }
}
