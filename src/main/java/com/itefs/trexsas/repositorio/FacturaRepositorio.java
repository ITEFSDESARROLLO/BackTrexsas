package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Factura;

public interface FacturaRepositorio extends JpaRepository<Factura, Long> {
	
	
	@Query("select f.idFactura,f.fechaFactura,cl.razonSocialCliente,f.fechaInicioFactura,f.fechaFinFactura,f.totalFactura,f.factura "
			+ "from Factura f "
			+ "join f.cliente cl "
			+ "where f.estadoFactura!=2 "
			+ "order by f.idFactura desc")
	Page<Object[]> listarFacturasPaginado(Pageable pageable);
	
	@Query(nativeQuery = true, value = "SELECT sum(os.valor_facturar_orden_servicio) "
			+ "FROM orden_servicio os "
			+ "join reserva r on os.reserva_id_reserva=r.id_reserva "
			+ "where r.fecha_inicio_reserva between ?1 AND ?2 and r.estado_reserva_id_estado_reserva=5 and r.cliente_id_cliente=?3")
	Long obtenerTotalEntreFechas(String fi,String ff, int idCli);
	
	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "os.id_orden_servicio,r.fecha_inicio_reserva,os.valor_facturar_orden_servicio, os.valor_conductor_orden_servicio,\r\n"
			+ "pepa.nombre_persona as nompa ,pepa.apellido_persona as apepa,\r\n"
			+ "r.numero_voucher, peco.nombre_persona as nomco,peco.apellido_persona as apeco,v.placa_vehiculo,r.estado_reserva_id_estado_reserva\r\n"
			+ "\r\n"
			+ "FROM orden_servicio os\r\n"
			+ "	join reserva r on os.reserva_id_reserva=r.id_reserva\r\n"
			+ "    join pasajero pa on r.pasajero_id_pasajero=pa.id_pasajero\r\n"
			+ "    join persona pepa on pa.persona_id_persona=pepa.id_persona\r\n"
			+ "    join conductor co on os.conductor_id_conductor=co.id_conductor\r\n"
			+ "    join persona peco on co.persona_id_persona=peco.id_persona\r\n"
			+ "    join vehiculo v on os.vehiculo_id_vehiculo=v.id_vehiculo "
			+ "where r.fecha_inicio_reserva between ?1 AND ?2 and r.estado_reserva_id_estado_reserva=5 and r.cliente_id_cliente=?3")
	List<Object[]> obtenerOsyReservasEntreFechas(String fi,String ff, int idCli);


	@Query(nativeQuery = true, value = 
			"select \r\n"
			+ "	fac.id_factura,date(now()),fac.concepto_factura,cl.razon_social_cliente,cl.nit_cliente,cl.celular_uno_cliente,\r\n"
			+ "	fac.total_factura,cl.direccion_cliente,fac.fecha_factura\r\n"
			+ "	from factura fac\r\n"
			+ "	join cliente cl on fac.cliente_id_cliente=cl.id_cliente\r\n"
			+ "where fac.id_factura=?1")
	List<Object[]> datosPdf(Long id);
	
	@Query("select c.idCuenta,cl.razonSocialCliente,cl.nitCliente,cl.direccionCliente,cl.celularUnoCliente,cl.finalizarServicioCliente,cl.logoCliente,c.estadoCuenta,cl.idCliente "
			+ "from Cuenta c join c.cliente cl where c.estadoCuenta!=2 "
			+ "order by c.idCuenta desc")
	List<Object[]> listarClientesFiltro();
	
	@Query("select f.idFactura,f.fechaFinFactura,f.fechaInicioFactura,f.fechaFactura,f.totalFactura,f.conceptoFactura,f.estadoFactura,f.cliente.idCliente "
			+ "from Factura f ")
	Page<Object[]> listarFacturasFiltrado(Pageable pageable);
	
	
}
