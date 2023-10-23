package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.itefs.trexsas.modelo.Afiliacion;

public interface AfiliacionRepositorio extends JpaRepository<Afiliacion, Long> {
	
	@Query("select af.idAfiliacion, af.placaAfiliacion, cl.clase, af.documentoAfiliacion, "
			+ "af.nombreAfiliacion, af.apellidoAfiliacion, af.estadoAfiliacion from Afiliacion af, Clase cl "
			+ "where af.claseVehiculoAfiliacion=cl.idClase and af.estadoAfiliacion!=1 and af.estadoAfiliacion!=4 "
			+ "order by af.idAfiliacion desc")
	Page<Object[]> listarAfiliacionPaginada(Pageable pageable);
	
	@Query("select a.estadoAfiliacion,a.placaAfiliacion,ol from Afiliacion a "
			+ " left join a.observacionAfiliacionList ol "
			+ "where a.idAfiliacion=?1")
	List<Object[]> obtenerEstadoAfiliacion(Long id);
	
	@Query("select af.idAfiliacion, af.placaAfiliacion, cl.clase, af.documentoAfiliacion, "
			+ "af.nombreAfiliacion, af.apellidoAfiliacion, af.estadoAfiliacion from Afiliacion af, Clase cl "
			+ "where af.claseVehiculoAfiliacion=cl.idClase and af.estadoAfiliacion!=1 and af.estadoAfiliacion!=4 and af.placaAfiliacion = ?1 "
			+ "order by af.idAfiliacion desc")
	Page<Object[]> listarPorPlaca(Pageable page,String placa);
	
	@Query("select af.idAfiliacion, af.placaAfiliacion, cl.clase, af.documentoAfiliacion, "
			+ "af.nombreAfiliacion, af.apellidoAfiliacion, af.estadoAfiliacion from Afiliacion af, Clase cl "
			+ "where af.claseVehiculoAfiliacion=cl.idClase and af.estadoAfiliacion!=1 and af.estadoAfiliacion!=4 and af.tipoVehiculoAfiliacion = ?1 "
			+ "order by af.idAfiliacion desc")
	Page<Object[]> listarPorTipoVehiculo(Pageable page,Integer tipoVehiculo);
	
	@Query("select af.idAfiliacion, af.placaAfiliacion, cl.clase, af.documentoAfiliacion, "
			+ "af.nombreAfiliacion, af.apellidoAfiliacion, af.estadoAfiliacion from Afiliacion af, Clase cl "
			+ "where af.claseVehiculoAfiliacion=cl.idClase and af.estadoAfiliacion!=1 and af.estadoAfiliacion!=4 and af.documentoAfiliacion = ?1 "
			+ "order by af.idAfiliacion desc")
	Page<Object[]> listarPorDocumentoPropietario(Pageable page,String id);
	
	@Query("select af.idAfiliacion, af.placaAfiliacion, cl.clase, af.documentoAfiliacion, "
			+ "af.nombreAfiliacion, af.apellidoAfiliacion, af.estadoAfiliacion from Afiliacion af, Clase cl "
			+ "where af.claseVehiculoAfiliacion=cl.idClase and af.estadoAfiliacion = ?1 "
			+ "order by af.idAfiliacion desc")
	Page<Object[]> listarPorEstado(Pageable page,Integer estado);
	
	@Query(nativeQuery=true, value = "select per.id_persona,  per.documento_persona,per.nombre_persona, per.apellido_persona, per.direccion_persona, cp.perfil_id_perfil \r\n"
			+ "from persona per \r\n"
			+ "join cuenta ct on ct.persona_id_persona=per.id_persona\r\n"
			+ "join cuenta_perfil cp on cp.cuenta_id_cuenta = ct.id_cuenta\r\n"
			+ "where cp.perfil_id_perfil = 19\r\n"
			+ "order by per.id_persona desc")
	List<Object[]> listarUsuariosAfiliados();
	
}
