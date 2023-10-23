package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.PolizaExtracontractual;

public interface PolizaExtracontractualRepositorio extends JpaRepository<PolizaExtracontractual, Integer> 
{
	@Query(nativeQuery=true,value="select pc.id_poliza_extracontractual, pc.fecha_vencimiento_poliza_extracontractual, v.placa_vehiculo, p.nombre_persona,v.id_vehiculo,v.estado_vehiculo,p.correo_persona "
			+ "from poliza_extracontractual pc "
			+ "join vehiculo v on pc.vehiculo_id_vehiculo = v.id_vehiculo "
			+ "join Persona p on v.propietario_id_propietario = p.id_persona ")
	List<Object[]> traerPolizasExtraContractuales();

}
