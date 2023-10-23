package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.EncuestaRespondida;

public interface EncuestaRespondidaRepository extends JpaRepository<EncuestaRespondida,Long>
{
	@Query("select er from EncuestaRespondida er where er.encuesta = ?1 and er.servicio = ?2")
	EncuestaRespondida traerEncuesta(Long encuesta,Long servicio);
	
	@Query("select er from EncuestaRespondida er where er.conductor = ?1 ")
	List<EncuestaRespondida> traerEncuestaConductor(Integer conductor);
}
