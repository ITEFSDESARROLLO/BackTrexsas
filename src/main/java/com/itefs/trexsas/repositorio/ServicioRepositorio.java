package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Servicio;

public interface ServicioRepositorio extends JpaRepository<Servicio, Long> {
	
	
	@Query("select r.idReserva,p.nombrePersona,p.apellidoPersona,r.fechaInicioReserva,r.direccionOrigenReserva,"
			+ "r.direccionDestinoReserva,r.distanciaReserva,r.duracionReserva,r.estadoReserva,pc.nombrePersona,"
			+ "pc.apellidoPersona,v.placaVehiculo,os.idOrdenServicio,r.horaInicioReserva, r.observacionesReserva "
			+ "from Servicio r "
			+ "left join r.ordenServicio os  "
			+ "join r.pasajero pa "
			+ "join pa.persona p "
			+ "left join os.conductor c "
			+ "left join c.persona pc "
			+ "left join os.vehiculo v "
			+ "order by r.idReserva desc")
	Page<Object[]> listarReservasPaginado(Pageable pageable);
	
	@Query("select r.idReserva,p.nombrePersona,p.apellidoPersona,r.fechaInicioReserva,r.direccionOrigenReserva,"
			+ "r.direccionDestinoReserva,r.distanciaReserva,r.duracionReserva,r.estadoReserva,pc.nombrePersona,"
			+ "pc.apellidoPersona,v.placaVehiculo,os.idOrdenServicio,r.horaInicioReserva, r.observacionesReserva,"
			+ "r.cliente.razonSocialCliente "
			+ "from Servicio r "
			+ "left join r.ordenServicio os  "
			+ "join r.pasajero pa "
			+ "join pa.persona p "
			+ "left join os.conductor c "
			+ "left join c.persona pc "
			+ "left join os.vehiculo v "
			+ "order by r.idReserva desc")
	List<Object[]> listarReservas();
	
	@Query("select r.idReserva,p.nombrePersona,p.apellidoPersona,r.fechaInicioReserva,r.direccionOrigenReserva,"
			+ "r.direccionDestinoReserva,r.distanciaReserva,r.duracionReserva,r.estadoReserva,pc.nombrePersona,"
			+ "pc.apellidoPersona,v.placaVehiculo,os.idOrdenServicio "
			+ "from Servicio r "
			+ "left join r.ordenServicio os  "
			+ "on r.ordenServicio = null "
			+ "join r.pasajero pa "
			+ "join pa.persona p "
			+ "left join os.conductor c "
			+ "left join c.persona pc "
			+ "left join os.vehiculo v "
			+ "order by r.idReserva desc")
	Page<Object[]> listarReservasDisponibles(Pageable pageable);
	
	@Query("select r.idReserva,p.nombrePersona,p.apellidoPersona,r.fechaInicioReserva,r.direccionOrigenReserva,"
			+ "r.direccionDestinoReserva,r.distanciaReserva,r.duracionReserva,r.estadoReserva,pc.nombrePersona,"
			+ "pc.apellidoPersona,v.placaVehiculo,os.idOrdenServicio "
			+ "from Servicio r "
			+ "left join r.ordenServicio os  "
			+ "join r.pasajero pa "
			+ "join pa.persona p "
			+ "left join os.conductor c "
			+ "left join c.persona pc "
			+ "left join os.vehiculo v "
			+ "where p.documentoPersona = ?1 "
			+ "order by r.idReserva desc")
	List<Object[]> listarReservasPasajero(String documento);
	
	@Query("select r.idReserva,p.nombrePersona,p.apellidoPersona,r.fechaInicioReserva,r.direccionOrigenReserva,"
			+ "r.direccionDestinoReserva,r.distanciaReserva,r.duracionReserva,r.estadoReserva,pc.nombrePersona,"
			+ "pc.apellidoPersona,v.placaVehiculo,os.idOrdenServicio "
			+ "from Servicio r "
			+ "left join r.ordenServicio os  "
			+ "join r.pasajero pa "
			+ "join pa.persona p "
			+ "left join os.conductor c "
			+ "left join c.persona pc "
			+ "left join os.vehiculo v "
			+ "where r.ordenServicio.conductor.idConductor = ?1 "
			+ "order by r.fechaInicioReserva asc")
	Page<Object[]> listarReservasPaginadoPorConductor(Pageable pageable, Integer conductor);
	
	
	@Query("select c.idConductor from Conductor c where c.persona.documentoPersona = ?1 ")
	Integer obtenerIdConductor(String documentoPersona);
	
	@Query("select r.idReserva,p.nombrePersona,p.apellidoPersona,r.fechaInicioReserva,r.direccionOrigenReserva,"
			+ "r.direccionDestinoReserva,r.distanciaReserva,r.duracionReserva,r.estadoReserva,r.numeroVoucher,pc.nombrePersona,"
			+ "pc.apellidoPersona,v.placaVehiculo,os.idOrdenServicio "
			+ "from Servicio r "
			+ "left join r.ordenServicio os  "
			+ "join r.pasajero pa "
			+ "join pa.persona p "
			+ "left join os.conductor c "
			+ "left join c.persona pc "
			+ "left join os.vehiculo v "
			+ "order by r.idReserva desc")
	Page<Object[]> listarReservasPaginadoVoucher(Pageable pageable);
	
	@Query("select r.idReserva,p.nombrePersona,p.apellidoPersona,r.fechaInicioReserva,r.direccionOrigenReserva,"
			+ "r.direccionDestinoReserva,r.distanciaReserva,r.duracionReserva,r.estadoReserva,r.pasajero.idPasajero,pc.nombrePersona,"
			+ "pc.apellidoPersona,v.placaVehiculo,os.idOrdenServicio "
			+ "from Servicio r "
			+ "left join r.ordenServicio os  "
			+ "join r.pasajero pa "
			+ "join pa.persona p "
			+ "left join os.conductor c "
			+ "left join c.persona pc "
			+ "left join os.vehiculo v "
			+ "order by r.idReserva desc")
	Page<Object[]> listarReservasPaginadoDocumentoPasajero(Pageable pageable);
	
	@Query("select r.idReserva,p.nombrePersona,p.apellidoPersona,r.fechaInicioReserva,r.direccionOrigenReserva,"
			+ "r.direccionDestinoReserva,r.distanciaReserva,r.duracionReserva,r.estadoReserva,r.estadoReserva,pc.nombrePersona,"
			+ "pc.apellidoPersona,v.placaVehiculo,os.idOrdenServicio "
			+ "from Servicio r "
			+ "left join r.ordenServicio os  "
			+ "join r.pasajero pa "
			+ "join pa.persona p "
			+ "left join os.conductor c "
			+ "left join c.persona pc "
			+ "left join os.vehiculo v "
			+ "order by r.idReserva desc")
	Page<Object[]> listarReservasPaginadoEstado(Pageable pageable);
	
	@Query("select r.direccionOrigenReserva "
			+ "from Servicio r ")
	List<String> listarDireccionesReservasOrigen();
	
	@Query("select r.direccionDestinoReserva "
			+ "from Servicio r ")
	List<String> listarDireccionesReservasDestino();
	
	@Query("select c.idConductor,p.nombrePersona,p.apellidoPersona,p.documentoPersona "
			+ "from Conductor c "
			+ "join c.persona p "
			+ "where c.estadoConductor=1")
	List<Object[]> obtenerInfoBasica();
	
	@Query("select os.idOrdenServicio,c.idConductor,os.ordenServicio "
			+ "from Servicio r "
			+ "join r.ordenServicio os "
			+ "join os.conductor c "
			+ "where r.idReserva=?1")
	List<Object[]> idOSyIdCdeReserva(Long id);
	
	List<Servicio> findByEstadoReserva(Integer id);
	
	List<Servicio> findByNumeroVoucher(String voucher);
	
	List<Servicio> findByDireccionOrigenReserva(String origen);
	
	List<Servicio> findByDireccionDestinoReserva(String destino);
	
	@Query(nativeQuery=true, value="select reserva.id_reserva,"
			+ "Date(reserva.fecha_inicio_reserva),"
			+ "reserva.direccion_origen_reserva, \r\n"
			+ "reserva.direccion_destino_reserva,\r\n"
			+ "estado_reserva.estado_reserva,"
			+ "reserva.observaciones_reserva,"
			+ "cliente.razon_social_cliente,"
			+ "orden_servicio.valor_facturar_orden_servicio,\r\n"
			+ "orden_servicio.valor_conductor_orden_servicio,"
			+ " vehiculo.placa_vehiculo ,"
			+ "concat(persona.nombre_persona, persona.apellido_persona) as nombre_pasajero,\r\n"
			+ "conductor.id_conductor, "
			+ "concat(persona1.nombre_persona,persona1.apellido_persona),\r\n"
			+ "reserva.hora_inicio_reserva "
			+ "from reserva\r\n"
			+ "left outer join cliente on cliente.id_cliente = reserva.cliente_id_cliente\r\n"
			+ "left outer join orden_servicio on orden_servicio.reserva_id_reserva = reserva.id_reserva\r\n"
			+ "left outer join estado_reserva on estado_reserva.id_estado_reserva = reserva.estado_reserva_id_estado_reserva\r\n"
			+ "join pasajero on pasajero.id_pasajero = reserva.pasajero_id_pasajero\r\n"
			+ "join persona on persona.id_persona = pasajero.persona_id_persona\r\n"
			+ "left outer join conductor on conductor.id_conductor = orden_servicio.conductor_id_conductor\r\n"
			+ "left outer join persona persona1 on conductor.persona_id_persona = persona1.id_persona\r\n"
			+ "left outer join vehiculo on vehiculo.id_vehiculo = orden_servicio.vehiculo_id_vehiculo")
	List<Object[]> traerServiciosExcel();
	
	
	

}
