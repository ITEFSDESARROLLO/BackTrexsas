package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.OrdenServicio;

public interface OrdenServicioRepositorio extends JpaRepository<OrdenServicio, Long> {
	
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "order by os.idOrdenServicio desc")
	Page<Object[]> listarOrdenesServicioPaginado(Pageable pageable);
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio,r.pasajero.persona.nombrePersona,r.pasajero.persona.apellidoPersona "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesServicio();
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "where r.fechaInicioReserva between ?1 and ?2 and p.idPersona = ?3 "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesServicioFechas(String inicio, String fin, Long idConductor);
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "where r.fechaInicioReserva between ?1 and ?2 and r.pasajero.persona.documentoPersona = ?3 "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesDocumentoPasajero(String inicio, String fin, String idPasajero);
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "where r.fechaInicioReserva between ?1 and ?2 and c.persona.documentoPersona = ?3 "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesDocumentoConductor(String inicio, String fin, String conductor);
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "where r.fechaInicioReserva between ?1 and ?2 and p.documentoPersona = ?3 "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesDocumentoPropietario(String inicio, String fin, String propietario);
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "where r.fechaInicioReserva between ?1 and ?2 and v.placaVehiculo = ?3 "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesPlacaVehiculo(String inicio, String fin, String placa);
	
	
	
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio,r.pasajero.persona.nombrePersona,r.pasajero.persona.apellidoPersona "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "where r.fechaInicioReserva between ?1 and ?2 "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesServicioNombrePasajero(String inicio, String fin);
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "where r.fechaInicioReserva between ?1 and ?2 "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesServicioNombreConductor(String inicio, String fin);
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "where r.fechaInicioReserva between ?1 and ?2 "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesServicioNombrePropietario(String inicio, String fin);
	
	@Query("select os.idOrdenServicio,r.fechaInicioReserva,r.numeroVoucher,p.nombrePersona,"
			+ "p.apellidoPersona,v.placaVehiculo,os.valorConductorOrdenServicio,"
			+ "os.valorFacturarOrdenServicio,os.ordenServicio "
			+ "from OrdenServicio os "
			+ "join os.reserva r "
			+ "join os.vehiculo v "
			+ "join os.conductor c "
			+ "join c.persona p  "
			+ "where r.fechaInicioReserva between ?1 and ?2 and r.cliente.idCliente = ?3 "
			+ "order by os.idOrdenServicio desc")
	List<Object[]> listarOrdenesServicioCliente(String inicio, String fin, Integer cliente);
	
	@Query("select c.idConductor,p.nombrePersona,p.apellidoPersona,p.documentoPersona "
			+ "from Conductor c "
			+ "join c.persona p "
			+ "where c.estadoConductor=1")
	List<Object[]> obtenerInfoBasica();
	
	@Query(nativeQuery = true, value = 
			"select os.id_orden_servicio,Date_Format(now(),'%d de %M de %Y %r'),Date_Format(r.fecha_inicio_reserva,'%d de %M de %Y %r'), \r\n"
			+ "		concat(pc.nombre_persona,' ',pc.apellido_persona),cl.razon_social_cliente,r.distancia_reserva,r.numero_voucher,\r\n"
			+ "        cla.clase,v.placa_vehiculo,concat(r.direccion_origen_reserva,' - ',r.direccion_destino_reserva),observaciones_reserva,\r\n"
			+ "		concat(pp.nombre_persona,' ',pp.apellido_persona)\r\n"
			+ "            from orden_servicio os \r\n"
			+ "            join reserva r on os.reserva_id_reserva=r.id_reserva\r\n"
			+ "            join conductor c on os.conductor_id_conductor=c.id_conductor\r\n"
			+ "            join persona pc on c.persona_id_persona=pc.id_persona\r\n"
			+ "            join cliente cl on r.cliente_id_cliente=cl.id_cliente\r\n"
			+ "            join vehiculo v on os.vehiculo_id_vehiculo=v.id_vehiculo\r\n"
			+ "            join clase cla on v.clase_id_clase=cla.id_clase\r\n"
			+ "            join pasajero p on r.pasajero_id_pasajero=p.id_pasajero\r\n"
			+ "            join persona pp on p.persona_id_persona=pp.id_persona\r\n"
			+ "            where os.id_orden_servicio=?1")
	List<Object[]> datosPdf(Long id);
	
	@Query("select os.idOrdenServicio from OrdenServicio os where os.reserva.idReserva = ?1 ")
	Long obtenerIdOrdenServicio(Long reserva);
	
	@Query("select os From OrdenServicio os where os.conductor.idConductor = ?1 ")
	OrdenServicio traerOrdenServicioConductor(Integer conductor);

}
