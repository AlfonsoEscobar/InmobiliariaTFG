package es.dao.mysql;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import es.dao.util.Conversor;
import es.modelos.Fotografia;

public class MySQLFotografiaDAO {

	final String INSERT = "INSERT INTO fotografia(ruta, tipo_habitacion, inmueble) VALUES(?,?,?)";

	final String DELETE = "DELETE FROM fotografia WHERE ruta = ?";

	final String DELETEID = "DELETE FROM fotografia where inmueble = ?";

	final String GETONE = "SELECT * FROM fotografia WHERE ruta = ?";

	final String GETID = "SELECT * FROM fotografia WHERE inmueble = ?";
	
	final String GETTYPE = "SELECT * FROM fotografia WHERE tipo_habitacion = ?";

	private Connection conexion;

	public MySQLFotografiaDAO(DataSource conexion) {
		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertar(Fotografia fotografia) throws DAOException {
		PreparedStatement stat = null;
		ResultSet generatedKeys = null;
		int generatedId = -1;
		{
			try {
				stat = conexion.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
				stat.setString(1, fotografia.getRuta());
				stat.setString(2, fotografia.getTipo_habitacion());
				stat.setInt(3, fotografia.getInmueble());
				
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

		}
		return generatedId;
	}

	// Elimina una foto por su ruta
	public int eliminar(String ruta) throws DAOException {
		PreparedStatement stat = null;
		int filasEliminadas = 0;
		try {
			stat = conexion.prepareStatement(DELETE);
			stat.setString(1, ruta);
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

	// Elimina todas las fotografias de un inmueble
	public int eliminarFotografiasDeInmueble(int id) throws DAOException {

		PreparedStatement stat = null;
		int filasEliminadas = 0;

		try {

			stat = conexion.prepareStatement(DELETEID);
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

	public Fotografia obtenerFotografia(String ruta) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Fotografia fotografia = null;
		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setString(1, ruta);
			rs = stat.executeQuery();
			if (rs.next()) {
				fotografia = Conversor.convertirFotografia(rs);
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
		return fotografia;
	}

	public LinkedList<Fotografia> listaFotografiasDeInmueble(int id) throws DAOException {

		PreparedStatement stat = null;
		ResultSet rs = null;

		LinkedList<Fotografia> fotografias = new LinkedList<>();

		try {

			stat = conexion.prepareStatement(GETID);
			stat.setInt(1, id);
			rs = stat.executeQuery();

			while (rs.next()) {
				fotografias.add(Conversor.convertirFotografia(rs));
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

		return fotografias;

	}
	
	public LinkedList<String> obtenerListaRutas(int id) throws DAOException{
		PreparedStatement stat = null;
		ResultSet rs = null;
		LinkedList<String> listaRutas = new LinkedList<String>();
		
		try {

			stat = conexion.prepareStatement("SELECT ruta FROM fotografia WHERE inmueble = ?");
			stat.setInt(1, id);
			rs = stat.executeQuery();

			while (rs.next()) {
				
				listaRutas.add(rs.getString("ruta"));

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
		
		
		return listaRutas;
	}

	public List<Fotografia> listaFotografiasPorTipoHabitacion(String tipo_habitacion) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Fotografia> fotografias = new ArrayList<>();
		try {
			stat = conexion.prepareStatement(GETTYPE);
			stat.setString(1, tipo_habitacion);
			rs = stat.executeQuery();
			while (rs.next()) {
				fotografias.add(Conversor.convertirFotografia(rs));
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
		return fotografias;

	}

	public void borrarFotografia(String ruta) {

		File f = new File(ruta);

		if (f.exists()) {
			f.delete();
		}

	}

}
