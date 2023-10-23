package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Propietario;

public interface PropietarioRepositorio extends JpaRepository <Propietario, Long> {
	
	@Query("select pr.idPropietario,p.documentoPersona,p.nombrePersona,p.apellidoPersona,pr.estadoPropietario,p.celularUnoPersona "
			+ "from Propietario pr "
			+ "join pr.persona p "
			+ "where pr.estadoPropietario!=2 "
			+ "order by pr.idPropietario desc")
	Page<Object[]> listarPropietariosPaginado(Pageable pageable);
	@Query(nativeQuery = true, value = "select p.id_persona,p.nombre_persona,p.apellido_persona,p.documento_persona "
			+ "from persona p "
			+ "join cuenta c on p.id_persona=c.persona_id_persona "
			+ "left join propietario pr on p.id_persona=pr.persona_id_persona where pr.persona_id_persona is null or pr.estado_propietario=3")
	List<Object[]> listarPersonasNoPropietarios();
	@Query(nativeQuery=true,value="select persona.id_persona, persona.nombre_persona, persona.apellido_persona, persona.documento_persona,cuenta_perfil.perfil_id_perfil\r\n"
			+ "From persona\r\n"
			+ "INNER JOIN cuenta on cuenta.persona_id_persona = persona.id_persona\r\n"
			+ "INNER JOIN cuenta_perfil on cuenta_perfil.cuenta_id_cuenta = cuenta.id_cuenta\r\n"
			+ "where cuenta_perfil.perfil_id_perfil =22")
	List<Object[]> listarPropietariosVehiculo();
	
	@Query(nativeQuery = true, value = "select p.persona_id_persona, per.nombre_persona,per.apellido_persona,per.correo_persona "
			+ "from propietario p "
			+ "left join persona per on "
			+ "p.persona_id_persona = per.id_persona;")
	List<Object[]> listarPropietariosNotificaciones();
	
	@Query("select p from Propietario p where p.persona.idPersona = ?1")
	Propietario obtenerPropietario(Long documentoPersona);
	
	@Query("select pr.idPropietario,p.documentoPersona,p.nombrePersona,p.apellidoPersona,pr.estadoPropietario,p.celularUnoPersona "
			+ "from Propietario pr "
			+ "join pr.persona p "
			+ "where pr.estadoPropietario!=2 and pr.persona.idPersona = ?1 "
			+ "order by pr.idPropietario desc")
	Page<Object[]> listarPorNombre(Pageable pageable,Long propietario);
	
	@Query("select pr.idPropietario,p.documentoPersona,p.nombrePersona,p.apellidoPersona,pr.estadoPropietario,p.celularUnoPersona "
			+ "from Propietario pr "
			+ "join pr.persona p "
			+ "where pr.estadoPropietario = ?1 "
			+ "order by pr.idPropietario desc")
	Page<Object[]> listarPorEstado(Pageable pageable,Integer estado);
	
	@Query("select pr.idPropietario,p.documentoPersona,p.nombrePersona,p.apellidoPersona,pr.estadoPropietario,p.celularUnoPersona "
			+ "from Propietario pr "
			+ "join pr.persona p "
			+ "where pr.estadoPropietario != 2 and p.celularUnoPersona = ?1 "
			+ "order by pr.idPropietario desc")
	Page<Object[]> listarPorTelefono(Pageable pageable,String celular);
	
}
