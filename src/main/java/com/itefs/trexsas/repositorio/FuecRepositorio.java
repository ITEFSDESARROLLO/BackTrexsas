package com.itefs.trexsas.repositorio;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Fuec;

public interface FuecRepositorio extends JpaRepository<Fuec, Long> {

	@Query("select f.idFuec,f.contrato.numeracionContrato,f.contrato.cliente.razonSocialCliente,f.contrato.objetoContrato, "
			+ "f.ciudadOrigen.ciudad,f.ciudadDestino.ciudad,f.vehiculo.placaVehiculo,"
			+ "f.fechaInicioFuec,f.fechaFinFuec,f.codigoFuec,f.cupoMaximo, f.cupoDisponible,f.estadoFuec,f.fuec "
			+ "from Fuec f "
			+ "order by f.idFuec desc")
	Page<Object[]> listarPaginado(Pageable pageable);
	
	@Query("select f.idFuec,f.contrato.numeracionContrato,f.contrato.cliente.razonSocialCliente,f.contrato.objetoContrato, "
			+ "f.ciudadOrigen.ciudad,f.ciudadDestino.ciudad,f.vehiculo.placaVehiculo,"
			+ "f.fechaInicioFuec,f.fechaFinFuec,f.codigoFuec,f.cupoMaximo, f.cupoDisponible "
			+ "from Fuec f "
			+ "where f.idFuec = ?1 ")
	Object[] traerFuec(Long id);
	
	@Query("select f.idFuec,co.numeracionContrato,cocl.razonSocialCliente,co.objetoContrato, "
			+ "f.ciudadOrigen.ciudad,f.ciudadDestino.ciudad,v.placaVehiculo,"
			+ "f.fechaInicioFuec,f.fechaFinFuec,f.codigoFuec,f.fuec "
			+ "from Fuec f "
			+ "left join f.contrato co "
			+ "left join co.cliente cocl "
			+ "join f.vehiculo v "
			+ "where co.numeracionContrato = ?1 "
			+ "order by f.idFuec desc")
	Page<Object[]> listarPorContrato(Pageable pageable, String contrato);
	
	@Query("select f.idFuec,co.numeracionContrato,cocl.razonSocialCliente,co.objetoContrato, "
			+ "f.ciudadOrigen.ciudad,f.ciudadDestino.ciudad,v.placaVehiculo,"
			+ "f.fechaInicioFuec,f.fechaFinFuec,f.codigoFuec,f.fuec "
			+ "from Fuec f "
			+ "left join f.contrato co "
			+ "left join co.cliente cocl "
			+ "join f.vehiculo v "
			+ "where f.codigoFuec = ?1 "
			+ "order by f.idFuec desc")
	Page<Object[]> listarPorCodigoFuec(Pageable pageable, String codigoFuec);
	
	@Query("select f.idFuec,co.numeracionContrato,cocl.razonSocialCliente,co.objetoContrato, "
			+ "f.ciudadOrigen.ciudad,f.ciudadDestino.ciudad,v.placaVehiculo,"
			+ "f.fechaInicioFuec,f.fechaFinFuec,f.codigoFuec,f.fuec "
			+ "from Fuec f "
			+ "left join f.contrato co "
			+ "left join co.cliente cocl "
			+ "join f.vehiculo v "
			+ "where v.placaVehiculo = ?1 "
			+ "order by f.idFuec desc")
	Page<Object[]> listarPorPlaca(Pageable pageable, String placa);
	
	@Query("select f.idFuec,co.numeracionContrato,cocl.razonSocialCliente,co.objetoContrato, "
			+ "f.ciudadOrigen.ciudad,f.ciudadDestino.ciudad,v.placaVehiculo,"
			+ "f.fechaInicioFuec,f.fechaFinFuec,f.codigoFuec,f.fuec "
			+ "from Fuec f "
			+ "left join f.contrato co "
			+ "left join co.cliente cocl "
			+ "join f.vehiculo v "
			+ "where f.ciudadOrigen.ciudad = ?1 and f.ciudadDestino.ciudad = ?2 "
			+ "order by f.idFuec desc")
	Page<Object[]> listarPorRuta(Pageable pageable, String origen, String destino);
	
	@Query("select f.vehiculo.placaVehiculo, f.vehiculo.modelo, f.vehiculo.marca.marca, f.vehiculo.clase.clase "
			+ "from Fuec f "
			+ "where f.idFuec = ?1 ")
	List<Object[]> vehiculoFuec(Long id);
	
	@Query("select f.vehiculo.codigoInternoVehiculo, to.numeroTarjetaOperacion "
			+ "from Fuec f "
			+ "inner join TarjetaOperacion to on f.vehiculo.idVehiculo = to.vehiculo.idVehiculo "
			+ "where f.idFuec = ?1 ")
	List<Object[]> numeroInternoVehiculoFuec(Long id);
	
	@Query("select f.contrato.numeracionContrato, "
			+ "f.contrato.cliente.razonSocialCliente,"
			+ "f.contrato.cliente.nitCliente, "
			+ "f.contrato.objetoContrato,"
			+ "f.contrato.responsable,"
			+ "f.contrato.documentoResponsable,"
			+ "f.contrato.telefonoResponsable,"
			+ "f.contrato.direccionResponsable,"
			+ "f.ciudadOrigen.ciudad,"
			+ "f.ciudadDestino.ciudad,"
			+ "f.fechaInicioFuec,"
			+ "f.fechaFinFuec, "
			+ "f.contrato.noContrato "
			+ "From Fuec f where f.idFuec = ?1 ")
	String[] contratoFuec(Long id);
	
	@Query("select f.contrato.numeracionContrato, "
			+ "f.contrato.cliente.razonSocialCliente,"
			+ "f.contrato.cliente.nitCliente, "
			+ "f.contrato.objetoContrato,"
			+ "f.contrato.responsable,"
			+ "f.contrato.documentoResponsable,"
			+ "f.contrato.telefonoResponsable,"
			+ "f.contrato.direccionResponsable,"
			+ "f.ciudadOrigen.ciudad,"
			+ "f.ciudadDestino.ciudad,"
			+ "f.fechaInicioFuec,"
			+ "f.fechaFinFuec, "
			+ "f.contrato.noContrato "
			+ "From Fuec f where f.idFuec = ?1 ")
	Object[] contratoFuec2(Long id);
	
	@Query(nativeQuery=true,value=  "select persona.nombre_persona,"
			+ " persona.apellido_persona,"
			+ " persona.documento_persona,"
			+ "licencia.numero_licencia,"
			+ " licencia.fecha_vencimiento_licencia,"
			+ "conductor.estado_conductor\r\n"
			+ "from conductor_fuec\r\n"
			+ "inner join conductor on conductor.id_conductor = conductor_fuec.conductor_id_conductor\r\n"
			+ "inner join licencia on licencia.conductor_id_conductor = conductor.id_conductor\r\n"
			+ "inner join persona on persona.id_persona = conductor.persona_id_persona\r\n"
			+ "where conductor_fuec.fuec_id_fuec = ?1 and conductor.estado_conductor!=3 and conductor.estado_conductor!=2")
	List<Object[]> traerConductoresFuec(Long id);
	
	@Query(nativeQuery=true, value="select concat(persona.nombre_persona,\" \", persona.apellido_persona) as nombre "
			+ "From cuenta "
			+ "inner join persona on cuenta.persona_id_persona = persona.id_persona "
			+ "where cuenta.id_cuenta = ?1 ")
	String nombreResponsableFuec(Long id);
	
	@Query(nativeQuery=true, value="select count(id_contrato) as totales from fuec where fuec.id_contrato = ?1")
	Integer contratosPorFuec(Long id);
}
