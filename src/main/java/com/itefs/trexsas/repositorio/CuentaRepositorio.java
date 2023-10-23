package com.itefs.trexsas.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.Persona;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {
	
	@Query(nativeQuery = true, value = "select * from cuenta c where c.usuario_cuenta  = BINARY ?1 and c.clave_cuenta = BINARY ?2")
	Optional<Cuenta> validarCuentayClave(String usuario,String clave);
	
	@Query("select p.correoPersona from Persona p join p.cuenta cu where cu.usuarioCuenta = ?1")
	Optional<String> encontrarCorreoPorUsername(String usuario);
	
	@Query("select per.correoPersona, per.nombrePersona from Persona per join per.cuenta cu where cu.usuarioCuenta = ?1")
	List<Object[]> encontrarNombreCorreoPorUsername(String usuario);
	
	@Query("select c from Cuenta c where c.usuarioCuenta = ?1")
	Optional<Cuenta> encontrarPorUsername(String usuario);
	
	@Query("select c from Cuenta c where c.persona.idPersona = ?1")
	Optional<Cuenta> encontrarPorIdPersona(Long id);
	
	@Query("select c.idCuenta,c.usuarioCuenta,c.estadoCuenta,c.registradoPorCuenta, "
			+ "c.fechaRegistroCuenta,c.actualizadoPorCuenta,c.fechaActualizacionCuenta "
			+ " from Cuenta c join c.persona per left join c.actualizadoPorCuenta where per.idPersona = ?1")
	List<Object[]> encontrarBasicoPorIdPersona(Long id);
	
	@Query("select c.usuarioCuenta from Cuenta c where c.usuarioCuenta like :palabra%")
	List<String> encontrarUsernameEmpiecePor(@Param("palabra") String palabra);
	
	@Query("select c.idCuenta,p.documentoPersona,p.nombrePersona,p.apellidoPersona,c.usuarioCuenta,p.celularUnoPersona,c.estadoCuenta, p.correoPersona "
			+ "from Cuenta c join c.persona p where c.estadoCuenta!=2 and c.estadoCuenta!=3 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarUsuariosPaginados(Pageable pageable);
	
	
	@Query("select c.idCuenta,p.documentoPersona,p.nombrePersona,p.apellidoPersona,c.usuarioCuenta,p.celularUnoPersona,c.estadoCuenta, p.correoPersona "
			+ "from Cuenta c join c.persona p where c.estadoCuenta!=2 and c.estadoCuenta!=3 "
			+ "order by c.idCuenta desc")
	List<Object[]> listarUsuarios();
	
	@Query("select c.idCuenta,p.documentoPersona,p.nombrePersona,p.apellidoPersona,c.usuarioCuenta,p.celularUnoPersona,c.estadoCuenta, p.correoPersona "
			+ "from Cuenta c join c.persona p where c.estadoCuenta!=2 and p.documentoPersona = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarUsuariosPaginadosDocumento(Pageable pageable,String documentoPersona);
	
	@Query("select c.idCuenta,p.documentoPersona,p.nombrePersona,p.apellidoPersona,c.usuarioCuenta,p.celularUnoPersona,c.estadoCuenta, p.correoPersona "
			+ "from Cuenta c join c.persona p where c.estadoCuenta!=2 and c.usuarioCuenta = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarUsuariosPaginadosCuenta(Pageable pageable, String cuenta);
	
	@Query("select c.idCuenta,p.documentoPersona,p.nombrePersona,p.apellidoPersona,c.usuarioCuenta,p.celularUnoPersona,c.estadoCuenta, p.correoPersona "
			+ "from Cuenta c join c.persona p where c.estadoCuenta!=2 and p.correoPersona = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarUsuariosPaginadosCorreo(Pageable pageable, String correo);
	
	@Query("select c.idCuenta,p.documentoPersona,p.nombrePersona,p.apellidoPersona,c.usuarioCuenta,p.celularUnoPersona,c.estadoCuenta, p.correoPersona "
			+ "from Cuenta c join c.persona p where c.estadoCuenta = ?1 "
			+ "order by c.idCuenta desc")
	Page<Object[]> listarUsuariosPaginadosEstadoCuenta(Pageable pageable, int estado);
	
	@Query("select c.persona from Cuenta c where c.idCuenta = ?1")
	Optional<Persona> personaDeCuenta(int id);
	
	@Query("select p.documentoPersona "
			+ "from Cuenta c "
			+ "join c.persona p "
			+ "where c.estadoCuenta!=2")
	List<String> obtenerCedulas();
	
	@Query("select per.nombrePersona,per.apellidoPersona,per.documentoPersona "
			+ " from Token t "
			+ "join t.cuenta c "
			+ "join c.persona per "
			+ "where t.token = ?1")
	List<Object[]> nombreDeToken(String token);

}
