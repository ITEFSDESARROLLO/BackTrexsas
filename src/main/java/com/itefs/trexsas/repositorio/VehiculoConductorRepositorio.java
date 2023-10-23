package com.itefs.trexsas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.VehiculoConductor;

public interface VehiculoConductorRepositorio extends JpaRepository<VehiculoConductor, Integer>
{
	@Query("select vc From VehiculoConductor vc where vc.conductor = ?1 ")
	VehiculoConductor traerPorConductor(Integer conductor);
}
