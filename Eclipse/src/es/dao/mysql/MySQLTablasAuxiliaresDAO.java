package es.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import es.dao.DAOException;

public class MySQLTablasAuxiliaresDAO {

	private final String TIPOHABIT = "SELECT tipo FROM tipo_habitacion";
	
	private final String ESCALERA = "SELECT tipo FROM escalera";
	
	private final String TIPOEDIF = "SELECT tipo FROM tipo_edificacion";
	
	private final String EXTERIORES = "SELECT tipo FROM exteriores";
	
	private final String TIPOOBRA = "SELECT tipo FROM tipo_obra";
	
	
	private Connection conexion;

	public MySQLTablasAuxiliaresDAO(DataSource conexion) {
		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public List<String> obtenerValoresTipoHabitacion() throws DAOException {
		List<String> lista = new LinkedList<>();
		
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			stat = conexion.createStatement();
			rs = stat.executeQuery(TIPOHABIT);
			
			while(rs.next()) {
				lista.add(rs.getString("tipo"));
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
	
	public List<String> obtenerValoresEscalera() throws DAOException {
		List<String> lista = new LinkedList<>();
		
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			stat = conexion.createStatement();
			rs = stat.executeQuery(ESCALERA);
			
			while(rs.next()) {
				lista.add(rs.getString("tipo"));
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
	
	public List<String> obtenerValoresTipoEdificacion() throws DAOException {
		List<String> lista = new LinkedList<>();
		
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			stat = conexion.createStatement();
			rs = stat.executeQuery(TIPOEDIF);
			
			while(rs.next()) {
				lista.add(rs.getString("tipo"));
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
	
	public List<String> obtenerValoresExteriores() throws DAOException {
		List<String> lista = new LinkedList<>();
		
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			stat = conexion.createStatement();
			rs = stat.executeQuery(EXTERIORES);
			
			while(rs.next()) {
				lista.add(rs.getString("tipo"));
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
	
	public List<String> obtenerValoresTipoObra() throws DAOException {
		List<String> lista = new LinkedList<>();
		
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			stat = conexion.createStatement();
			rs = stat.executeQuery(TIPOOBRA);
			
			while(rs.next()) {
				lista.add(rs.getString("tipo"));
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
