package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.RevisionPreventiva;

public interface RevisionPreventivaRepositorio extends JpaRepository<RevisionPreventiva, Integer> 
{
	@Query(nativeQuery=true,value="select rp.id_revision_preventiva, rp.fecha_vencimiento_revision_preventiva, v.placa_vehiculo, p.nombre_persona,v.id_vehiculo,v.estado_vehiculo,p.correo_persona "
			+ "from revision_preventiva rp "
			+ "join vehiculo v on rp.vehiculo_id_vehiculo = v.id_vehiculo "
			+ "join Persona p on v.propietario_id_propietario = p.id_persona ")
	List<Object[]> traerRevisionesPreventivas();
}
