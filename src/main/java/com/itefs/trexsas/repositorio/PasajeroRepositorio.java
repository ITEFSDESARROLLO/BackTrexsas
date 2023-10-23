package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Pasajero;
import com.itefs.trexsas.modelo.PasajeroContrato;
import com.itefs.trexsas.modelo.Persona;

public interface PasajeroRepositorio extends JpaRepository <Pasajero, Integer> {
	
	@Query(nativeQuery = true, value = "select pr.id_pasajero,p.documento_persona,p.nombre_persona,p.apellido_persona,pr.estado_pasajero,p.celular_uno_persona "
			+ "from pasajero pr "
			+ "join persona p on pr.persona_id_persona=p.id_persona "
			+ "where pr.cliente_id_cliente=?1 and pr.estado_pasajero!=2 "
			+ "order by pr.id_pasajero desc")
	Page<Object[]> listarPasajerosPaginado(Pageable pageable, int id);
	
	@Query("select p from Pasajero p where p.persona.idPersona = ?1")
	Pasajero traerPasajeroPorPersona(Long idPersona);
	
	@Query(nativeQuery = true, value = "select p.id_persona,p.nombre_persona,p.apellido_persona,p.documento_persona "
			+ "from persona p "
			+ "join cuenta c on p.id_persona=c.persona_id_persona "
			+ "left join pasajero pr on p.id_persona=pr.persona_id_persona where pr.persona_id_persona is null or pr.estado_pasajero=3")
	List<Object[]> listarPersonasNoPasajeros();
	
	@Query(nativeQuery = true, value = "select pr.id_pasajero,p.documento_persona,p.nombre_persona,p.apellido_persona "
			+ "from pasajero pr "
			+ "join persona p on pr.persona_id_persona=p.id_persona")
	List<Object[]> infoBasica();
	
	/*@Query("select pl.idPasajero,per.nombrePersona, per.apellidoPersona ,per.documentoPersona "
			+ "from ContratoPermanente cp "
			+ "join cp.contrato c "
			+ "join c.cliente cl "
			+ "join cl.pasajeroList pl "
			+ "join pl.persona per "
			+ "where pl.estadoPasajero=1 and cp.idContratoPermanente=?1")
	List<Object[]> infoBasicaDeContrato(long id);*/
	
	@Query("select pc.pasajero.idPasajero, pc.pasajero.persona.nombrePersona, pc.pasajero.persona.apellidoPersona, pc.pasajero.persona.documentoPersona From PasajeroContrato pc where pc.contrato = ?1 ")
	List<Object[]> listarPasajeros(Long contrato);
	
	@Query("select per.correoPersona, per.nombrePersona, per.apellidoPersona from Pasajero p join p.persona per where p.idPasajero = ?1")
	List<Object[]> correoPasajero(int id);
	
	Pasajero findByPersona(Persona persona);
	
	@Query(nativeQuery = true, value = "select "
			+ "pa.persona_id_persona,"
			+ "pe.nombre_persona,"
			+ "pe.apellido_persona,"
			+ "pe.correo_persona "
			+ "from pasajero pa "
			+ "left join persona pe on "
			+ "pa.persona_id_persona = pe.id_persona;")
	List<Object[]> listarPasajerosNotificacion();
	
}
