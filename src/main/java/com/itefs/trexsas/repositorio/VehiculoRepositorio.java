package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Vehiculo;

public interface VehiculoRepositorio extends JpaRepository<Vehiculo, Integer> {
	
	//listas
	/**
	 * @Query("select v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona,v.numeroPasajerosVehiculo "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "where v.estadoVehiculo!=2 "
			+ "order by v.idVehiculo desc")
	List<Object[]> listarVehiculosPaginados();
	 * 
	 */
	
	
	@Query("select v.idVehiculo,v.codigoInternoVehiculo, "
			+ "v.placaVehiculo, "
			+ "v.clase,v.estadoVehiculo, "
			+ "v.propietario.nombrePersona, "
			+ "v.propietario.apellidoPersona,"
			+ "v.numeroPasajerosVehiculo "
			+ "from Vehiculo v "
			+ "order by v.idVehiculo desc")
	List<Object[]> listarVehiculosPaginados();
	@Query("select v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona,v.numeroPasajerosVehiculo "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "where v.estadoVehiculo!=2 "
			+ "order by v.idVehiculo desc")
	List<Object[]> listarVehiculosPaginados2();
	
	@Query("select v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl.clase,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "join pr.cuenta cu "
			+ "join cu.tokenList tl "
			+ "where v.estadoVehiculo!=2 and tl.token=?1 "
			+ "order by v.idVehiculo desc")
	List<Object[]> listarVehiculosPropietarioPaginados(String token);
	
	@Query("select v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl.clase,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "join pr.cuenta cu "
			+ "join cu.tokenList tl "
			+ "where v.estadoVehiculo!=2 and tl.token=?1 "
			+ "order by v.idVehiculo desc")
	List<Object[]> listarVehiculosPropietarioPaginados2(String token);
	
	@Query("select distinct v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl.clase,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "join v.conductorList con "
			+ "join con.persona pecon "
			+ "join pecon.cuenta cu "
			+ "join cu.tokenList tl "
			+ "where v.estadoVehiculo!=2 and con.estadoConductor=1 and tl.token=?1 "
			+ "order by v.idVehiculo desc")
	List<Object[]> listarVehiculosConductorPaginados(String token);
	
	@Query("select distinct v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl.clase,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "join v.conductorList con "
			+ "join con.persona pecon "
			+ "join pecon.cuenta cu "
			+ "join cu.tokenList tl "
			+ "where v.estadoVehiculo!=2 and con.estadoConductor=1 and tl.token=?1 "
			+ "order by v.idVehiculo desc")
	List<Object[]> listarVehiculosConductorPaginados2(String token);
	
	///
	
	@Query("select v.placaVehiculo "
			+ "from Vehiculo v "
			+ "where v.estadoVehiculo=1")
	List<String> obtenerPlacas();
	
	@Query("select v.idVehiculo,v.placaVehiculo,v.codigoInternoVehiculo,pr.nombrePersona,pr.apellidoPersona,cl.clase "
			+ "from Vehiculo v "
			+ "join v.propietario pr "
			+ "join v.clase cl "
			+ "where v.estadoVehiculo=1 and v.enConvenioVehiculo=0")
	List<Object[]> obtenerInfoBasicaTurismo();
	
	@Query("select v.idVehiculo,v.placaVehiculo,v.codigoInternoVehiculo,pr.nombrePersona,pr.apellidoPersona,cl.clase "
			+ "from Vehiculo v "
			+ "join v.propietario pr "
			+ "join v.clase cl "
			+ "where v.estadoVehiculo=1")
	List<Object[]> obtenerInfoBasicaSalud();
	
	@Query("select v.idVehiculo,v.placaVehiculo,v.codigoInternoVehiculo,pr.nombrePersona,pr.apellidoPersona,cl.clase  "
			+ "from Vehiculo v "
			+ "join v.propietario pr "
			+ "join v.clase cl "
			+ "join pr.cuenta c "
			+ "join c.tokenList tl "
			+ "where v.estadoVehiculo=1 and tl.token=?1 and v.enConvenioVehiculo=0")
	List<Object[]> obtenerInfoBasicaDePropietario(String token);
	
	@Query("select v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl.clase,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "where v.estadoVehiculo!=2 and v.propietario.idPersona = ?1 "
			+ "order by v.idVehiculo desc")
	Page<Object[]> listarPorDocumento(Pageable pageable, Long propietario);
	
	@Query("select v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl.clase,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "where v.estadoVehiculo!=2 and v.codigoInternoVehiculo = ?1 "
			+ "order by v.idVehiculo desc")
	Page<Object[]> listarPorCodigo(Pageable pageable, String codigo);
	
	@Query("select v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl.clase,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "where v.estadoVehiculo!=2 and v.placaVehiculo = ?1 "
			+ "order by v.idVehiculo desc")
	Page<Object[]> listarPorPlaca(Pageable pageable, String placa);
	
	@Query("select v.idVehiculo,v.codigoInternoVehiculo, v.placaVehiculo, cl.clase,v.estadoVehiculo, pr.nombrePersona, pr.apellidoPersona "
			+ "from Vehiculo v "
			+ "join v.clase cl "
			+ "join v.propietario pr "
			+ "where v.estadoVehiculo!=2 and v.tipoVehiculo.idTipoVehiculo = ?1 "
			+ "order by v.idVehiculo desc")
	Page<Object[]> listarPorTipo(Pageable pageable, Integer tipo);

}
