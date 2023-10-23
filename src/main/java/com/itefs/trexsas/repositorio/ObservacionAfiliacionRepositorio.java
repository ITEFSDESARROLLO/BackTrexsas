package com.itefs.trexsas.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.ObservacionAfiliacion;

public interface ObservacionAfiliacionRepositorio extends JpaRepository<ObservacionAfiliacion, Long> {

	@Query("select oa.idObservacionAfiliacion,oa.observacionAfiliacion from ObservacionAfiliacion oa where oa.afiliacion.idAfiliacion=?1")
	List<Object[]> obtenerPorIdAfiliacion(long id);
	
	@Query(nativeQuery = true, value = "SELECT o.observacion_afiliacion FROM observacion_afiliacion o WHERE o.afiliacion_id_afiliacion = ?1 ORDER BY o.id_observacion_afiliacion DESC LIMIT 1")
	Optional<String> ultimaObservacionPorIdAfiliacion(long id);
}
