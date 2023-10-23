package com.itefs.trexsas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.CuentaPerfil;

public interface CuentaPerfilRepositorio extends JpaRepository<CuentaPerfil, Long>
{
	@Query("select cp From CuentaPerfil cp where cp.cuenta = ?1 and cp.perfil = ?2 ")
	CuentaPerfil traerCuentaPerfil(Integer cuenta, Integer perfil);
}
