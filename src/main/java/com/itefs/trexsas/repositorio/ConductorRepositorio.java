package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.Persona;

public interface ConductorRepositorio extends JpaRepository<Conductor, Integer> {
	
	
	@Query(nativeQuery = true, value = "select c.id_conductor,p.documento_persona,p.nombre_persona,p.apellido_persona,c.estado_conductor,p.celular_uno_persona,p.correo_persona "
			+ "from conductor c "
			+ "join persona p on c.persona_id_persona=p.id_persona where c.estado_conductor!=2 "
			+ "order by c.id_conductor desc")
	Page<Object[]> listarConductoresPaginado(Pageable pageable);
	
	@Query("select c.idConductor,p.documentoPersona,p.nombrePersona,p.apellidoPersona,c.estadoConductor,p.celularUnoPersona,p.correoPersona "
			+ "from Conductor c "
			+ "join Persona p on c.persona.idPersona=p.idPersona where c.estadoConductor!=2 and c.estadoConductor!=3 "
			+ "order by c.idConductor desc")
	Page<Object[]> listarConductoresPaginadoPer(Pageable pageable);
	
	@Query(nativeQuery = true, value = "select p.id_persona,p.nombre_persona,p.apellido_persona,p.documento_persona "
			+ "from persona p "
			+ "join cuenta c on p.id_persona=c.persona_id_persona "
			+ "left join conductor con on p.id_persona=con.persona_id_persona where con.persona_id_persona is null or con.estado_conductor=3")
	List<Object[]> listarPersonasNoConductores();
	
	@Query(nativeQuery=true,value="select co.id_conductor, co.fin_arl_conductor, p.nombre_persona, p.apellido_persona, p.correo_persona, co.estado_conductor "
			+ "from conductor co "
			+ "join persona p on co.persona_id_persona = p.id_persona ")
	List<Object[]> traerARLConductores();
	
	@Query(nativeQuery=true,value="select co.id_conductor, co.fin_eps_conductor, p.nombre_persona, p.apellido_persona, p.correo_persona, co.estado_conductor "
			+ "from conductor co "
			+ "join persona p on co.persona_id_persona = p.id_persona ")
	List<Object[]> traerEPSConductores();
	
	@Query(nativeQuery=true,value="select li.id_licencia, li.fecha_vencimiento_licencia, p.nombre_persona, co.id_conductor, p.correo_persona, co.estado_conductor "
			+ "from licencia li "
			+ "join conductor co on li.conductor_id_conductor = co.id_conductor "
			+ "join persona p on co.persona_id_persona = p.id_persona ")
	List<Object[]> traerLicenciaConductores();
	
	@Query("select c.idConductor, p.nombrePersona, p.apellidoPersona,p.documentoPersona, p.correoPersona, c.estadoConductor "
			+ "from Conductor c "
			+ "join c.persona p "
			+ "join c.fuecList fl "
			+ "where fl.idFuec=?1")
	List<Object[]> conductoresDeFuec(Long id);
	
	@Query("select c.idConductor,p.nombrePersona,p.apellidoPersona,p.documentoPersona "
			+ "from Conductor c "
			+ "join c.persona p "
			+ "where c.estadoConductor=1")
	List<Object[]> obtenerInfoBasicaTodos();
	
	@Query("select c.idConductor,p.nombrePersona,p.apellidoPersona,p.documentoPersona "
			+ "from Vehiculo v "
			+ "join v.conductorList c "
			+ "join c.persona p "
			+ "where c.estadoConductor!=0 and c.estadoConductor!=3 and v.idVehiculo=?1")
	List<Object[]> infoBasicaDeVehiculo(int id);
	
	@Query("select per.correoPersona, per.nombrePersona, per.apellidoPersona from Conductor p join p.persona per where p.idConductor = ?1")
	List<Object[]> correoConductor(int id);
	
	@Query(nativeQuery = true, value ="select c.persona_id_persona, p.nombre_persona,p.apellido_persona,p.correo_persona "
			+ "from conductor c "
			+ "left join persona p "
			+ "on c.persona_id_persona = p.id_persona;")
	List<Object[]> obtenerConductoresNotificacion();
	
	@Query(nativeQuery = true, value = "select c.id_conductor,p.documento_persona,p.nombre_persona,p.apellido_persona,c.estado_conductor,p.celular_uno_persona,p.correo_persona "
			+ "from conductor c "
			+ "join persona p on c.persona_id_persona=p.id_persona "
			+ "where c.estado_conductor!=2 and p.documento_persona = ?1 "
			+ "order by c.id_conductor desc")
	Page<Object[]> listarPorDocumento(Pageable pageable, String documento);
	
	@Query(nativeQuery = true, value = "select c.id_conductor,p.documento_persona,p.nombre_persona,p.apellido_persona,c.estado_conductor,p.celular_uno_persona,p.correo_persona "
			+ "from conductor c "
			+ "join persona p on c.persona_id_persona=p.id_persona where c.estado_conductor!=2 and p.celular_uno_persona = ?1 "
			+ "order by c.id_conductor desc")
	Page<Object[]> listarPorTelefono(Pageable pageable, String telefono);
	
	@Query(nativeQuery = true, value = "select c.id_conductor,p.documento_persona,p.nombre_persona,p.apellido_persona,c.estado_conductor,p.celular_uno_persona,p.correo_persona "
			+ "from conductor c "
			+ "join persona p on c.persona_id_persona=p.id_persona where c.estado_conductor!=2 and p.correo_persona = ?1 "
			+ "order by c.id_conductor desc")
	Page<Object[]> listarPorCorreo(Pageable pageable, String correo);
	
	@Query(nativeQuery = true, value = "select c.id_conductor,p.documento_persona,p.nombre_persona,p.apellido_persona,c.estado_conductor,p.celular_uno_persona,p.correo_persona "
			+ "from conductor c "
			+ "join persona p on c.persona_id_persona=p.id_persona where c.estado_conductor = ?1 "
			+ "order by c.id_conductor desc")
	Page<Object[]> listarPorEstado(Pageable pageable, Integer estado);
	
	@Query("select c.persona From Conductor c where c.persona.documentoPersona = ?1 ")
	Persona obtenerConductorPersona(String id);
	
	@Query("select c.idConductor From Conductor c where c.persona.documentoPersona = ?1 ")
	Integer obtenerConductor(String id);
	
	@Query("select c From Conductor c where c.persona.idPersona = ?1 ")
	Conductor obtenerConductorPorPersona(Long id);

}
