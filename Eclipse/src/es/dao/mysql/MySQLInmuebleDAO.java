package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import es.dao.DAOException;
import es.dao.util.Conversor;
import es.modelos.Inmueble;

public class MySQLInmuebleDAO {

	final String INSERT = "INSERT INTO inmueble(provincia, localidad, calle, numero, piso, puerta,"
			+ "propietario, descripcion, metros2, num_habitaciones, num_banos,"
			+ "tipo_edificacion, tipo_obra, equipamiento, exteriores, garaje,"
			+ "trastero, ascensor, ultima_planta, mascotas) " + "VALUES(?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final String UPDATE = "UPDATE inmueble SET provincia = ?, localidad = ?, calle = ?, numero = ?, escalera = ?, "
			+ "piso = ?, puerta = ?, descripcion = ?, metros2 = ?,"
			+ "num_habitaciones = ?, num_banos = ?, tipo_edificacion = ?,"
			+ "tipo_obra = ?, equipamiento = ?, exteriores = ?, garaje = ?, trastero = ?,"
			+ "ascensor = ?, ultima_planta = ?, mascotas = ? WHERE id_inmueble = ?";

	final String DELETE = "DELETE FROM inmueble WHERE id_inmueble = ?";

	final String GETONE = "SELECT * FROM inmueble WHERE id_inmueble = ?";

	final String GETPROPIETARIO = "SELECT * FROM inmueble WHERE propietario = ?";

	private Connection conexion;

	public MySQLInmuebleDAO(DataSource conexion) {
		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int insertar(Inmueble inmueble) throws DAOException {
		PreparedStatement stat = null;
		ResultSet generatedKeys = null;
		int generatedId = -1;

		try {
			stat = conexion.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, inmueble.getProvincia());
			stat.setString(2, inmueble.getLocalidad());
			stat.setString(3, inmueble.getCalle());
			stat.setString(4, inmueble.getEscalera());
			stat.setInt(5, inmueble.getNumero());
			stat.setInt(6, inmueble.getPiso());
			stat.setString(7, inmueble.getPuerta());
			stat.setInt(8, inmueble.getPropietario());
			stat.setString(9, inmueble.getDescripcion());
			stat.setDouble(10, inmueble.getMetros2());
			stat.setInt(11, inmueble.getNum_habitaciones());
			stat.setInt(12, inmueble.getNum_banos());
			stat.setString(13, inmueble.getTipo_edificacion());
			stat.setString(14, inmueble.getTipo_obra());
			stat.setString(15, inmueble.getEquipamiento());
			stat.setString(16, inmueble.getExteriores());
			stat.setBoolean(17, inmueble.isGaraje());
			stat.setBoolean(18, inmueble.isTrastero());
			stat.setBoolean(19, inmueble.isAscensor());
			stat.setBoolean(20, inmueble.isUltima_planta());
			stat.setBoolean(21, inmueble.isMascotas());

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

	public int modificar(Integer id, Inmueble inmueble) throws DAOException {

		PreparedStatement stat = null;
		int filasModificadas = 0;

		try {
			stat = conexion.prepareStatement(UPDATE);
			stat.setString(1, inmueble.getProvincia());
			stat.setString(2, inmueble.getLocalidad());
			stat.setString(3, inmueble.getCalle());
			stat.setInt(4, inmueble.getNumero());
			stat.setString(5, inmueble.getEscalera());
			stat.setInt(6, inmueble.getPiso());
			stat.setString(7, inmueble.getPuerta());
			stat.setString(8, inmueble.getDescripcion());
			stat.setDouble(9, inmueble.getMetros2());
			stat.setInt(10, inmueble.getNum_habitaciones());
			stat.setInt(11, inmueble.getNum_banos());
			stat.setString(12, inmueble.getTipo_edificacion());
			stat.setString(13, inmueble.getTipo_obra());
			stat.setString(14, inmueble.getEquipamiento());
			stat.setString(15, inmueble.getExteriores());
			stat.setBoolean(16, inmueble.isGaraje());
			stat.setBoolean(17, inmueble.isTrastero());
			stat.setBoolean(18, inmueble.isAscensor());
			stat.setBoolean(19, inmueble.isUltima_planta());
			stat.setBoolean(20, inmueble.isMascotas());
			stat.setInt(21, id);

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

	public int eliminar(Integer id) throws DAOException {
		PreparedStatement stat = null;
		int filasEliminadas = 0;
		try {
			stat = conexion.prepareStatement(DELETE);
			stat.setInt(1, id);
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

	public List<Inmueble> obtenerPorParametro(Integer propietario) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Inmueble> inmuebles = new LinkedList<Inmueble>();

		try {

			stat = conexion.prepareStatement(GETPROPIETARIO);
			stat.setInt(1, propietario);
			rs = stat.executeQuery();

			while (rs.next()) {

				inmuebles.add(Conversor.convertirInmueble(rs));

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

	public Inmueble obtener(Integer id) throws DAOException {

		PreparedStatement stat = null;
		ResultSet rs = null;
		Inmueble inmueble = new Inmueble();

		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setInt(1, id);
			rs = stat.executeQuery();

			if (rs.next()) {

				inmueble = Conversor.convertirInmueble(rs);

			} else {

				if (rs.next()) {

					inmueble = Conversor.convertirInmueble(rs);

				} else {
					throw new DAOException("No se ha encontrado ningún registro");
				}
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
		return inmueble;
	}

	public List<Inmueble> listaInmueblesDePropietario(Integer propietario) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Inmueble> inmuebles = new LinkedList<>();

		try {

			stat = conexion.prepareStatement(GETPROPIETARIO);
			stat.setInt(1, propietario);
			rs = stat.executeQuery();

			while (rs.next()) {

				inmuebles.add(Conversor.convertirInmueble(rs));

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
}
