package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.RevisionTecnicomecanica;

public interface RevisionTecnicomecanicaRepositorio extends JpaRepository<RevisionTecnicomecanica, Integer> 
{
	@Query(nativeQuery=true,value="select rtm.id_revision_tecnicomecanica, rtm.fecha_vencimiento_revision_tecnicomecanica, v.placa_vehiculo, p.nombre_persona,v.id_vehiculo,v.estado_vehiculo,p.correo_persona "
			+ "from revision_tecnicomecanica rtm "
			+ "join vehiculo v on rtm.vehiculo_id_vehiculo = v.id_vehiculo "
			+ "join Persona p on v.propietario_id_propietario = p.id_persona ")
	List<Object[]> traerRevisionesTecnicoMecanicas();
}
