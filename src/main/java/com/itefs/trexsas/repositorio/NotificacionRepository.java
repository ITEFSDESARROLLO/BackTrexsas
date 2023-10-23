package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

	@Query("select n.id,n.tituloNotificacion,n.descripcionNotificacion,n.tipoNotificacion,n.estadoNotificacion "
			+ "from Notificacion n "
			+ "order by n.id desc")
	Page<Object[]> listarNotificacionPaginado(Pageable pageable);
	
	@Query("select c.persona.correoPersona From Cuenta c where c.persona.idPersona = ?1 ")
	String correoUsuario(Long cuenta);
	
	@Query("select c.cliente.correoCliente From Cuenta c where c.cliente.idCliente = ?1 ")
	String correoCliente(Integer cuenta);
}
