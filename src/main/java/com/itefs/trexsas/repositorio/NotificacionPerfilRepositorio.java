package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.NotificacionPerfil;

public interface NotificacionPerfilRepositorio extends JpaRepository<NotificacionPerfil, Long>
{
	
	@Query("select np from NotificacionPerfil np where np.notificacion.id = ?1 ")
	NotificacionPerfil findByNotificacion(Long idNotificacion);
	
	@Query("select n from NotificacionPerfil n where n.notificacion.id = ?1 ")
	List<NotificacionPerfil> buscarNotificaciones(Long idNotificacion);
	
}
