package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import es.dao.DAOException;
import es.dao.FotografiaDAO;
import es.modelos.Fotografia;

public class MySQLFotografiaDAO implements FotografiaDAO {

	final String INSERT = "INSERT INTO FOTOGRAFIA(ruta, tipo_habitacion, inmueble) VALUES(?,?,?)";
	final String DELETE = "DELETE FROM FOTOGRAFIA WHERE ruta = ?";
	final String GETONE = "SELECT * FROM FOTOGRAFIA WHERE ruta = ?";
	final String GETTYPE = "SELECT FROM FOTOGRAFIA WHERE tipo_habitacion = ?";
	final String GETALL = "SELECT * FROM FOTOGRAFIA";

	private Connection conexion;

	public MySQLFotografiaDAO(DataSource conexion) {
		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public int insertar(Fotografia fotografia) throws DAOException {
		PreparedStatement stat = null;
		int filasInsertadas = 0;
		{
			try {
				stat = conexion.prepareStatement(INSERT);
				stat.setString(1, fotografia.getRuta());
				stat.setString(2, fotografia.getTipo_habitacion());
				stat.setInt(3, fotografia.getInmueble());
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

		}
		return filasInsertadas;
	}

	@Override
	public int modificar(String value, Fotografia object) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
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

	@Override
	public List<Fotografia> obtenerPorParametro(String tipo_habitacion) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List <Fotografia> fotografias = new ArrayList<>();
		try {
			stat = conexion.prepareStatement(GETTYPE);
			stat.setString(1, tipo_habitacion);
			rs = stat.executeQuery();
			while(rs.next()) {
				fotografias.add(convertir(rs));
			}
		}catch(SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(stat != null) {
				try {
					stat.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			
		}
		return fotografias;
		
	}

	@Override
	public Fotografia obtener(String ruta) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Fotografia fotografia = null;
		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setString(1, ruta);
			rs = stat.executeQuery();
			if(rs.next()) {
				fotografia = convertir(rs);
			}else {
				throw new DAOException("No se ha encontrado ningún registro");
			}
		}catch(SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(stat != null) {
				try {
					stat.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			
		}
		return fotografia;
	}

	@Override
	public List<Fotografia> obtenerTodos() throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List <Fotografia> fotografias = new ArrayList<>();
		try {
			stat = conexion.prepareStatement(GETALL);
			rs = stat.executeQuery();
			while(rs.next()) {
				fotografias.add(convertir(rs));
			}
		}catch(SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(stat != null) {
				try {
					stat.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			
		}
		return fotografias;
	}

	private Fotografia convertir(ResultSet rs) throws SQLException {
		String ruta = rs.getString("ruta");
		String tipo_habitacion = rs.getString("tipo_habitacion");
		int inmueble = rs.getInt("inmueble");
		Fotografia fotografia = new Fotografia(ruta, tipo_habitacion, inmueble);
		return fotografia;
	}

}
