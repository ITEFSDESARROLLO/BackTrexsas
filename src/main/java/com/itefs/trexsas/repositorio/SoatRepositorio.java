package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Soat;

public interface SoatRepositorio extends JpaRepository<Soat, Integer> {

	@Query(nativeQuery=true,value="select s.id_soat, s.fecha_vencimiento_soat, v.placa_vehiculo, p.nombre_persona,v.id_vehiculo,v.estado_vehiculo,p.correo_persona "
			+ "from soat s "
			+ "join vehiculo v on s.vehiculo_id_vehiculo = v.id_vehiculo "
			+ "join Persona p on v.propietario_id_propietario = p.id_persona ")
	List<Object[]> traerSoats();
}
