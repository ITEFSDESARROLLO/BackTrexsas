package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Pregunta;

public interface PreguntaRepositorio extends JpaRepository<Pregunta,Long>
{
	@Query("select p from Pregunta p "
			+ "where p.encuesta.id = ?1")
	List<Pregunta> encontrarPorEncuesta(Long encuesta);

}
