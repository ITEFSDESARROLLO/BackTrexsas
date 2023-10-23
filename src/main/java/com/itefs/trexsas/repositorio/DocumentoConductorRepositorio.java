package com.itefs.trexsas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.DocumentoConductor;

public interface DocumentoConductorRepositorio extends JpaRepository<DocumentoConductor, Integer> 
{
	@Query("select dc From DocumentoConductor dc where dc.conductor.idConductor = ?1 ")
	DocumentoConductor traerDocumento(Integer conductor);

}
