package com.itefs.trexsas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itefs.trexsas.modelo.NotificacionIndividual;

public interface NotificacionIndividualRepositorio extends JpaRepository<NotificacionIndividual, Long>{

	NotificacionIndividual findByIdNotificacion(Long idNotificacion);
}
