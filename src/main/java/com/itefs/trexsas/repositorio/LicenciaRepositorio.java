package com.itefs.trexsas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Licencia;

public interface LicenciaRepositorio extends JpaRepository<Licencia, Long> 
{
	@Query("select lic From Licencia lic where lic.conductor.idConductor = ?1 ")
	Licencia traerLicenciaConductor(Integer Conductor); 

}
