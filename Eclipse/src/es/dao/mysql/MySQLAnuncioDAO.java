package es.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.LinkedList;

import javax.sql.DataSource;

import es.dao.AnuncioDAO;
import es.dao.DAOException;
import es.dao.util.CriterioBusqueda;
import es.dao.util.InfoAnuncio;
import es.modelos.Anuncio;
import es.modelos.Inmueble;

public class MySQLAnuncioDAO implements AnuncioDAO {

	final String INSERT = "INSERT INTO anuncio(id_inmueble, tipo_anuncio, precio)" + "VALUES(?,?,?)";

	final String DELETE = "DELETE FROM anuncio WHERE id_inmueble = ? and tipo_anuncio = ?";

	final String UPDATE = "UPDATE anuncio SET precio = ?, fecha_ultima_actualizacion = ? " + "WHERE id_inmueble = ? and tipo_anuncio = ?";

	final String UPDATEDATE = "UPDATE anuncio SET fecha_ultima_actualizacion = ? "
			+ "WHERE id_inmueble = ? and tipo_anuncio = ?";

	final String GETONE = "SELECT * FROM anuncio WHERE id_inmueble = ? and tipo_anuncio = ?";

	final String GETTYPE = "SELECT FROM anuncio WHERE tipo_anuncio = ?";

	final String GETLOCTYP = "SELECT * FROM anuncio WHERE tipo_anuncio = ? and "
			+ "id_inmueble = (SELECT id_inmueble FROM inmueble WHERE localidad = ?)";

	final String GETALL = "SELECT * FROM anuncio";
	
	//------------HECHO POR ALFONSO----------------
	
	final String GETINFOANUNCIO = "SELECT a.*, b.* from anuncio a, inmuble b on "
								+ "a.id_inmueble = b.id_inmueble WHERE a.tipo_anuncio = ?"
								+ " and b.localidad = ?";
	

	private Connection conexion;

	public MySQLAnuncioDAO(DataSource conexion) {
		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * FUNCIONA ANTES DE LAS FECHAS. PROBAR CON ELLAS
	 */
	@Override
	public int insertar(Anuncio anuncio) throws DAOException {

		PreparedStatement stat = null;
		int filasInsertadas = 0;

		try {

			stat = conexion.prepareStatement(INSERT);
			stat.setInt(1, anuncio.getId_inmueble());
			stat.setString(2, anuncio.getTipo_anuncio());
			stat.setDouble(3, anuncio.getPrecio());
			stat.setDate(4, (java.sql.Date) anuncio.getFecha_anuncio());
			stat.setDate(5,(java.sql.Date) anuncio.getFecha_ultima_actualizacion());

			filasInsertadas = stat.executeUpdate();

		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {

			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

		}

		return filasInsertadas;

	}

	@Override
	public int modificar(String value, Anuncio object) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int modificar(int id, String tipo_anuncio, double nuevoPrecio) throws DAOException {
		PreparedStatement stat = null;
		int filasModificadas = 0;
		java.util.Date dateUtil = new java.util.Date();
		SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
		plantilla.format(dateUtil);
		java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
		try {
			stat = conexion.prepareStatement(UPDATE);
			stat.setDouble(1, nuevoPrecio);
			stat.setDate(2, dateSql);
			stat.setInt(3, id);
			stat.setString(4, tipo_anuncio);
			filasModificadas = stat.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
		}
		return filasModificadas;
	}

	public int actualizarFecha(int id, String tipo_anuncio) throws DAOException {
		PreparedStatement stat = null;
		int filasActualizadas = 0;
		java.util.Date dateUtil = new java.util.Date();
		SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
		plantilla.format(dateUtil);
		java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
		try {
			stat = conexion.prepareStatement(UPDATEDATE);
			stat.setDate(1, dateSql);
			stat.setInt(2, id);
			stat.setString(3, tipo_anuncio);
			filasActualizadas = stat.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
		}
		return filasActualizadas;
	}

	@Override
	public int eliminar(String value) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * FUNCIONA
	 */
	public int eliminar(int id, String tipo_anuncio) throws DAOException {
		PreparedStatement stat = null;
		int filasEliminadas = 0;
		try {
			stat = conexion.prepareStatement(DELETE);
			stat.setInt(1, id);
			stat.setString(2, tipo_anuncio);
			filasEliminadas = stat.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
		}
		return filasEliminadas;
	}

	@Override
	public List<Anuncio> obtenerPorParametro(String tipo_anuncio) throws DAOException {
		
		List<Anuncio> anuncios = new LinkedList<>();
		
		return anuncios;
	}

	/*
	 * FUNCIONA
	 */
	public List<Anuncio> obtenerPorParametro(String localidad, String tipo_anuncio) throws DAOException {

		PreparedStatement stat = null;

		ResultSet rs = null;

		List<Anuncio> anuncios = new LinkedList<>();

		try {

			stat = conexion.prepareStatement(GETLOCTYP);
			stat.setString(1, tipo_anuncio);
			stat.setString(2, localidad);
			rs = stat.executeQuery();

			while (rs.next()) {
				anuncios.add(convertir(rs));
			}

		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

		}

		return anuncios;

	}

	/*
	// A este método solo lo llamaremos con la lista de ids de inmuebles que
	// previamente han sido filtrados por localidad y por tipo_anuncio.
	// Es decir, al método le otorgamos una lista con los id de inmuebles que pasan
	// un determinadao filtro de búsqueda en una localidad y un tipo de anuncio.
	public List<InfoAnuncio> obtenerInfoAnuncios(List<Integer> ids, String tipo_anuncio) throws DAOException {
		List<InfoAnuncio> infoAnuncios = new LinkedList<>();
		List<Double> precios = new LinkedList<Double>();
		List<Inmueble> inmuebles = new LinkedList<Inmueble>();
		PreparedStatement statAnuncio = null, statInmueble = null;
		ResultSet rsAnuncio = null, rsInmueble = null;
		try {
			// Obtenemos los precios de venta/alquiler de los anuncios asociados a los
			// inmuebles filtrados por búsqueda en una determinada localidad.
			statAnuncio = conexion.prepareStatement("Select precio from anuncio where tipo_anuncio = ? and id_inmueble = ?");
			statAnuncio.setString(1, tipo_anuncio);
			for (int i = 0; i < ids.size(); i++) {
				statAnuncio.setInt(2, ids.get(i));
				rsAnuncio = statAnuncio.executeQuery();
				precios.add(rsAnuncio.getDouble("precio"));
			}

			// Obtenemos la información completa de los inmuebles que están filtrados por
			// búsqueda en una determinada localidad
			statInmueble = conexion.prepareStatement("Select * from inmueble where id_inmueble = ?");
			for (int i = 0; i < ids.size(); i++) {
				statInmueble.setInt(1, ids.get(i));
				rsInmueble = statInmueble.executeQuery();
				inmuebles.add(convertirInmueble(rsInmueble));
			}

			// Montamos en una lista los objetos InfoBusqueda que tendrán el precio, el tipo
			// de anuncio y los datos del inmueble.
			for (int i = 0; i < ids.size(); i++) {
				InfoAnuncio infoAnuncio = new InfoAnuncio(ids.get(i), precios.get(i), inmuebles.get(i));
				infoAnuncios.add(infoAnuncio);
			}

		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {

			if (rsAnuncio != null) {
				try {
					rsAnuncio.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

			if (statAnuncio != null) {
				try {
					statAnuncio.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

			if (rsInmueble != null) {
				try {
					rsInmueble.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

			if (statInmueble != null) {
				try {
					statInmueble.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

		}

		return infoAnuncios;
	}

	public List<Inmueble> obtenerInmueblesPorFiltros(CriterioBusqueda criterioBusqueda, String localidad) throws DAOException {
		List<Inmueble> inmuebles = new LinkedList<>();
		PreparedStatement stat = null;
		ResultSet rs = null;
		String clausulaFiltrada = criterioBusqueda.getClausula();
		try {

			stat = conexion.prepareStatement("Select * from inmueble where localidad = ? and " + clausulaFiltrada);
			stat.setString(1, localidad);
			rs = stat.executeQuery();
			while (rs.next()) {
				inmuebles.add(convertirInmueble(rs));
			}

		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

		}

		return inmuebles;
	}

	public List<Integer> obtenerIdInmuebles(List<Inmueble> inmuebles) {
		List<Integer> ids = new LinkedList<Integer>();
		for (int i = 0; i < inmuebles.size(); i++) {
			ids.add(inmuebles.get(i).getId_inmueble());
		}

		return ids;
	}

	public List<Integer> obtenerIdAnuncios(List<Anuncio> anuncios) {
		List<Integer> ids = new LinkedList<Integer>();
		for (int i = 0; i < anuncios.size(); i++) {
			ids.add(anuncios.get(i).getId_inmueble());
		}
		return ids;
	}

	public List<Integer> obtenerIdsCoincidentes(List<Integer> ids_anuncios, List<Integer> ids_inmuebles) {
		List<Integer> coincidentes = new LinkedList<Integer>();
		int elemento = 0;
		boolean iguales = false;
		for (int i = 0; i < ids_anuncios.size(); i++) {
			elemento = ids_anuncios.get(i);
			iguales = false;
			for (int j = 0; j < ids_inmuebles.size() || iguales == true; j++) {
				if (elemento == ids_inmuebles.get(j)) {
					coincidentes.add(elemento);
					iguales = true;
				}
			}
		}
		return coincidentes;
	}

	//Con este método obtengo la lista de los ids de los anuncios de un tipo en una localidad filtrada. Posteriormente usaremos esta lista para 
	//pasarla como parametro del metodo OBTENERINFOANUNCIOS.
	public List<Integer> obtenerIdsFiltrados(String tipo_anuncio, String localidad, CriterioBusqueda criterio) throws DAOException {
		List<Integer> id_anuncios = new LinkedList<Integer>();
		List<Integer> id_inmuebles = new LinkedList<Integer>();
		List<Integer> lista = new LinkedList<Integer>();
		List<Anuncio> anuncios = new LinkedList<Anuncio>();
		List<Inmueble> inmuebles = new LinkedList<Inmueble>();

		anuncios = obtenerPorParametro(localidad, tipo_anuncio);
		inmuebles = obtenerInmueblesPorFiltros(criterio, localidad);

		id_anuncios = obtenerIdAnuncios(anuncios);
		id_inmuebles = obtenerIdInmuebles(inmuebles);

		lista = obtenerIdsCoincidentes(id_anuncios, id_inmuebles);
		return lista;
	}*/

	

	@Override
	public Anuncio obtener(String value) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public Anuncio obtener(int id, String tipo_anuncio) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Anuncio anuncio = null;
		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setInt(1, id);
			stat.setString(2, tipo_anuncio);
			rs = stat.executeQuery();
			if (rs.next()) {
				anuncio = convertir(rs);
			} else {
				// throw new DAOException("No se ha encontrado ningún registro");
			}
		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

		}
		return anuncio;
	}

	/*
	 * FUNCIONA
	 */
	@Override
	public List<Anuncio> obtenerTodos() throws DAOException {

		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Anuncio> anuncios = new LinkedList<>();

		try {

			stat = conexion.prepareStatement(GETALL);
			rs = stat.executeQuery();

			while (rs.next()) {
				anuncios.add(convertir(rs));
			}

		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

		}
		return anuncios;
	}

	private Anuncio convertir(ResultSet rs) throws SQLException {

		int id_inmueble = rs.getInt("id_inmueble");
		String tipo_anuncio = rs.getString("tipo_anuncio");
		double precio = rs.getDouble("precio");
		// Date fecha_anuncio = rs.getDate("fecha_anuncio");
		// Date fecha_ultima_actualizacion = rs.getDate("fecha_ultima_actualizacion");

		// Anuncio anuncio = new Anuncio(id_inmueble, tipo_anuncio, precio,
		// fecha_anuncio, fecha_ultima_actualizacion);

		Anuncio anuncio = new Anuncio(id_inmueble, tipo_anuncio, precio, null, null);

		return anuncio;
	}

	private Inmueble convertirInmueble(ResultSet rs) throws SQLException {

		String provincia = rs.getString("provincia");
		String localidad = rs.getString("localidad");
		String calle = rs.getString("calle");
		int numero = rs.getInt("numero");
		String escalera = rs.getString("escalera");
		int piso = rs.getInt("piso");
		String puerta = rs.getString("puerta");
		int id_inmueble = rs.getInt("id_inmueble");
		int propietario = rs.getInt("propietario");
		String descripcion = rs.getString("descripcion");
		double metros2 = rs.getDouble("metros2");
		int num_habitaciones = rs.getInt("num_habitaciones");
		int num_banos = rs.getInt("num_banos");
		String tipo_edificacion = rs.getString("tipo_edificacion");
		String tipo_obra = rs.getString("tipo_obra");
		String equipamiento = rs.getString("equipamiento");
		String exteriores = rs.getString("exteriores");
		boolean garaje = rs.getBoolean("garaje");
		boolean trastero = rs.getBoolean("trastero");
		boolean ascensor = rs.getBoolean("ascensor");
		boolean ultima_planta = rs.getBoolean("ultima_planta");
		boolean mascotas = rs.getBoolean("mascotas");

		Inmueble inmueble = new Inmueble(provincia, localidad, calle, numero, escalera, piso, puerta, propietario,
				descripcion, metros2, num_habitaciones, num_banos, tipo_edificacion, tipo_obra, equipamiento,
				exteriores, garaje, trastero, ascensor, ultima_planta, mascotas);

		inmueble.setId_inmueble(id_inmueble);

		return inmueble;
	}
	
	
	
	/* ---------------------------------HECHO POR ALFONSO----------------------------
---------------------------------------------------------------------------------------------------------------
	
		Este metodo devuelve la lista de InfoAnuncios 
		filtrados solo por el tipo de anuncio y la localidad
		que es lo que se le pasa por parametro

	*/
	public LinkedList<InfoAnuncio> listaInfoAnuncios(String tipo_anuncio, String localidad) throws DAOException{

		LinkedList<InfoAnuncio> lista = new LinkedList<>();
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		try {
			
			stat = conexion.prepareStatement(GETINFOANUNCIO);
			stat.setString(1, tipo_anuncio);
			stat.setString(1, localidad);
			rs = stat.executeQuery();
			
			while(rs.next()) {
				
				Inmueble inmu = new Inmueble();
				InfoAnuncio infoan = new InfoAnuncio();
				
				inmu.setProvincia(rs.getString("provincia"));
				inmu.setLocalidad(rs.getString("localidad"));
				inmu.setCalle(rs.getString("calle"));
				inmu.setNumero(rs.getInt("numero"));
				inmu.setEscalera(rs.getString("escalera"));
				inmu.setPiso(rs.getInt("piso"));
				inmu.setPuerta(rs.getString("puerta"));
				inmu.setId_inmueble(rs.getInt("id_inmueble"));
				inmu.setPropietario(rs.getInt("propietario"));
				inmu.setDescripcion(rs.getString("descripcion"));
				inmu.setMetros2(rs.getDouble("metros2"));
				inmu.setNum_habitaciones(rs.getInt("num_habitaciones"));
				inmu.setNum_banos(rs.getInt("num_banos"));
				inmu.setTipo_edificacion(rs.getString("tipo_edificacion"));
				inmu.setTipo_obra(rs.getString("tipo_obra"));
				inmu.setEquipamiento(rs.getString("equipamiento"));
				inmu.setExteriores(rs.getString("exteriores"));
				inmu.setGaraje(rs.getBoolean("garaje"));
				inmu.setTrastero(rs.getBoolean("trastero"));
				inmu.setAscensor(rs.getBoolean("ascensor"));
				inmu.setUltima_planta(rs.getBoolean("ultima_planta"));
				inmu.setMascotas(rs.getBoolean("mascotas"));
				
				infoan.setId_inmueble(rs.getInt("id_inmueble"));
				infoan.setPrecio(rs.getDouble("precio"));
				infoan.setFecha_anunciado(rs.getDate("fecha_anunciado"));
				infoan.setFecha_ultima_actualizacion(rs.getDate("fecha_ultima_actualizacion"));
				
				infoan.setInmueble(inmu);
				
				lista.add(infoan);
				
			}
			
		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

		}
		
		return lista;
	}
	
	
	/*
	 
	  	Este metodo devuelte igual que el anterior la lista de InfoAnuncio
	  	pero filtrado por la clase CriterioDeBusqueda que devuelve un String 
	  	con la sentencia SQL con todos los criterios a buscar.
	 
	 */
	
	public LinkedList<InfoAnuncio> listaInfoAnunciosCriterio(String sql) throws DAOException{

		LinkedList<InfoAnuncio> lista = new LinkedList<>();
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		try {
			
			stat = conexion.prepareStatement(sql);
			rs = stat.executeQuery();
			
			while(rs.next()) {
				
				Inmueble inmu = new Inmueble();
				InfoAnuncio infoan = new InfoAnuncio();
				
				inmu.setProvincia(rs.getString("provincia"));
				inmu.setLocalidad(rs.getString("localidad"));
				inmu.setCalle(rs.getString("calle"));
				inmu.setNumero(rs.getInt("numero"));
				inmu.setEscalera(rs.getString("escalera"));
				inmu.setPiso(rs.getInt("piso"));
				inmu.setPuerta(rs.getString("puerta"));
				inmu.setId_inmueble(rs.getInt("id_inmueble"));
				inmu.setPropietario(rs.getInt("propietario"));
				inmu.setDescripcion(rs.getString("descripcion"));
				inmu.setMetros2(rs.getDouble("metros2"));
				inmu.setNum_habitaciones(rs.getInt("num_habitaciones"));
				inmu.setNum_banos(rs.getInt("num_banos"));
				inmu.setTipo_edificacion(rs.getString("tipo_edificacion"));
				inmu.setTipo_obra(rs.getString("tipo_obra"));
				inmu.setEquipamiento(rs.getString("equipamiento"));
				inmu.setExteriores(rs.getString("exteriores"));
				inmu.setGaraje(rs.getBoolean("garaje"));
				inmu.setTrastero(rs.getBoolean("trastero"));
				inmu.setAscensor(rs.getBoolean("ascensor"));
				inmu.setUltima_planta(rs.getBoolean("ultima_planta"));
				inmu.setMascotas(rs.getBoolean("mascotas"));
				
				infoan.setId_inmueble(rs.getInt("id_inmueble"));
				infoan.setPrecio(rs.getDouble("precio"));
				infoan.setTipo_anuncio(rs.getString("tipo_anuncio"));
				infoan.setFecha_anunciado(rs.getDate("fecha_anunciado"));
				infoan.setFecha_ultima_actualizacion(rs.getDate("fecha_ultima_actualizacion"));
				infoan.setInmueble(inmu);
				
				lista.add(infoan);
				
			}
			
		} catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}

		}
		
		return lista;
	}

}
