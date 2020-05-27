package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.LinkedList;

import javax.sql.DataSource;

import es.dao.DAOException;
import es.dao.util.Conversor;
import es.dao.util.InfoAnuncio;
import es.modelos.Anuncio;

public class MySQLAnuncioDAO {


	final String INSERTCONFECHAS = "INSERT INTO anuncio(id_inmueble, tipo_anuncio, precio, fecha_anunciado, "
							+ "fecha_ultima_actualizacion) VALUES(?, ?, ?, CURRENT_DATE, CURRENT_DATE)";
	
	final String INSERT = "INSERT INTO anuncio(id_inmueble, tipo_anuncio, precio) VALUES(?,?,?)";
	
	final String UPDATECONFECHA = "UPDATE anuncio SET precio = ?, fecha_ultima_actualizacion = CURRENT_DATE "
			+ "WHERE id_inmueble = ? and tipo_anuncio = ?";
	
	final String UPDATE = "UPDATE anuncio SET precio = ? WHERE id_inmueble = ? and tipo_anuncio = ?";

	final String DELETE = "DELETE FROM anuncio WHERE id_inmueble = ? and tipo_anuncio = ?";
	
	final String GETONE = "SELECT * FROM anuncio WHERE id_inmueble = ? and tipo_anuncio = ?";

	final String GETLOCTYP = "SELECT * FROM anuncio WHERE tipo_anuncio = ? and "
			+ "id_inmueble = (SELECT id_inmueble FROM inmueble WHERE localidad = ?)";
	
	final String GETINFOANUNCIOPROPIETARIO = "SELECT a.*, i.*, u.* FROM anuncio a inner join inmueble i "
			+ "on a.id_inmueble = i.id_inmueble inner join usuario u on i.propietario = u.id_usuario "
			+ "WHERE i.propietario = ?";

	final String GETINFOANUNCIOS = "SELECT a.*, i.*, u.* FROM anuncio a inner join inmueble i " + 
			"on a.id_inmueble = i.id_inmueble inner join usuario u on i.propietario = u.id_usuario " + 
			"WHERE a.tipo_anuncio = ? and i.localidad = ?";

	final String UPDATEDATE = "UPDATE anuncio SET fecha_ultima_actualizacion = ? WHERE id_inmueble = ?";

	private Connection conexion;

	public MySQLAnuncioDAO(DataSource conexion) {
		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * FUNCIONA ANTES DE LAS FECHAS. PROBAR CON ELLAS
	 */
	public int insertar(Anuncio anuncio) throws DAOException {

		PreparedStatement stat = null;
		ResultSet generatedKeys = null;
		int generatedId = -1;
		
		
		try {

			stat = conexion.prepareStatement(INSERTCONFECHAS, Statement.RETURN_GENERATED_KEYS);
			stat.setInt(1, anuncio.getId_inmueble());
			stat.setString(2, anuncio.getTipo_anuncio());
			stat.setDouble(3, anuncio.getPrecio());

			stat.executeUpdate();
			
			generatedKeys = stat.getGeneratedKeys();
			if (generatedKeys.next())
				generatedId = generatedKeys.getInt(1);

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

		return generatedId;

	}

	// De un objeto anuncio solo vamos a poder modificar el precio.
	public int modificar(int id, String tipo_anuncio, double nuevoPrecio) throws DAOException {
		PreparedStatement stat = null;
		int filasModificadas = 0;
		
		try {
			stat = conexion.prepareStatement(UPDATECONFECHA);
			stat.setDouble(1, nuevoPrecio);
			stat.setInt(2, id);
			stat.setString(3, tipo_anuncio);
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

	
	/*
	 * Obtención de un anuncio con su id de inmueble y el tipo de anuncio
	 * */
	public Anuncio obtenerAnuncio(int id, String tipo_anuncio) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Anuncio anuncio = null;
		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setInt(1, id);
			stat.setString(2, tipo_anuncio);
			rs = stat.executeQuery();
			if (rs.next()) {
				anuncio = Conversor.convertirAnuncio(rs);
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
	public List<Anuncio> listaAnuncios(String localidad, String tipo_anuncio) throws DAOException {

		PreparedStatement stat = null;

		ResultSet rs = null;

		List<Anuncio> anuncios = new LinkedList<>();

		try {

			stat = conexion.prepareStatement(GETLOCTYP);
			stat.setString(1, tipo_anuncio);
			stat.setString(2, localidad);
			rs = stat.executeQuery();

			while (rs.next()) {
				anuncios.add(Conversor.convertirAnuncio(rs));
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
	 * Método que recibe el id de un usuario y devuelve todos los InfoAnuncio de los anuncio que ha registrado. CHECKEAR EN LA CAPA RESTFUL 
	 * */
	public List<InfoAnuncio> listaInfoAnuncios(int propietario) throws DAOException {

		PreparedStatement stat = null;

		ResultSet rs = null;

		List<InfoAnuncio> InfoAnuncios = new LinkedList<InfoAnuncio>();

		try {

			stat = conexion.prepareStatement(GETINFOANUNCIOPROPIETARIO);
			stat.setInt(1, propietario);
			rs = stat.executeQuery();

			while (rs.next()) {
				InfoAnuncios.add(Conversor.convertirInfoAnuncio(rs));
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

		return InfoAnuncios;

	}
	
	/*
	 * Este metodo devuelve la lista de InfoAnuncios filtrados solo por el tipo de
	 * anuncio y la localidad que es lo que se le pasa por parametro
	 * 
	 */
	public LinkedList<InfoAnuncio> listaInfoAnuncios(String tipo_anuncio, String localidad) throws DAOException {

		LinkedList<InfoAnuncio> lista = new LinkedList<InfoAnuncio>();

		PreparedStatement stat = null;
		ResultSet rs = null;

		try {

			stat = conexion.prepareStatement(GETINFOANUNCIOS);
			stat.setString(1, tipo_anuncio);
			stat.setString(2, localidad);
			rs = stat.executeQuery();

			while (rs.next()) {

				lista.add(Conversor.convertirInfoAnuncio(rs));

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
	 * 
	 * Este metodo devuelte igual que el anterior la lista de InfoAnuncio pero
	 * filtrado por la clase CriterioDeBusqueda que devuelve un String con la
	 * sentencia SQL con todos los criterios a buscar.
	 * 
	 */

	public LinkedList<InfoAnuncio> listaInfoAnunciosCriterio(String sentencia) throws DAOException {

		LinkedList<InfoAnuncio> lista = new LinkedList<>();

		PreparedStatement stat = null;
		ResultSet rs = null;

		try {

			stat = conexion.prepareStatement(sentencia);
			rs = stat.executeQuery();

			while (rs.next()) {

				lista.add(Conversor.convertirInfoAnuncio(rs));

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

	// Actulizamos la fecha de los anuncios asociados a un inmueble modificado
	public int actualizarFecha(int id) throws DAOException {
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
	
}
