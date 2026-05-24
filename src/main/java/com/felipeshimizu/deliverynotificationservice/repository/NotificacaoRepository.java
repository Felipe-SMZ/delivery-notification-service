package com.felipeshimizu.deliverynotificationservice.repository;

import com.felipeshimizu.deliverynotificationservice.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificacaoRepository extends JpaRepository<Notificacao, UUID> {
}
