package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.PolizaTodoRiesgo;

public interface PolizaTodoRiesgoRepositorio extends JpaRepository<PolizaTodoRiesgo, Long>
{
	@Query(nativeQuery=true,value="select ptr.id_poliza_todo_riesgo, ptr.fecha_fin, v.placa_vehiculo, p.nombre_persona,v.id_vehiculo,v.estado_vehiculo,p.correo_persona "
			+ "from poliza_todo_riesgo ptr "
			+ "join vehiculo v on ptr.id_vehiculo = v.id_vehiculo "
			+ "join Persona p on v.propietario_id_propietario = p.id_persona ")
	List<Object[]> traerPolizasExtraContractuales();

}
