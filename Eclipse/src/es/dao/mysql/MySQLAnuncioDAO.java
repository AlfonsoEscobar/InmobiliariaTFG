package es.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.sql.DataSource;

import es.dao.AnuncioDAO;
import es.dao.DAOException;
import es.modelos.Anuncio;

public class MySQLAnuncioDAO implements AnuncioDAO {


	final String INSERT = "INSERT INTO anuncio(id_inmueble, tipo_anuncio, precio)"
							+ "VALUES(?,?,?)";
	
	final String DELETE = "DELETE FROM anuncio WHERE id_inmueble = ? and tipo_anuncio = ?";
	
	final String UPDATE = "UPDATE anuncio SET tipo_anuncio = ?, precio = ? "
							+ "WHERE id_inmueble = ? and tipo_anuncio = ?";
	
	final String UPDATEDATE = "UPDATE anuncio SET fecha_ultima_actualizacion = ? "
								+ "WHERE id_inmueble = ? and tipo_anuncio = ?";
	
	final String GETONE = "SELECT * FROM anuncio WHERE id_inmueble = ? and tipo_anuncio = ?";
	
	final String GETTYPE = "SELECT FROM anuncio WHERE tipo_anuncio = ?";
	
	final String GETLOCTYP = "SELECT * FROM anuncio WHERE tipo_anuncio = ? and "
								+ "id_inmueble = (SELECT id_inmueble FROM inmueble WHERE localidad = ?)";
	
	final String GETALL = "SELECT * FROM anuncio";

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
	 * 	FUNCIONA
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
			//stat.setString(4, null);//(Date) anuncio.getFecha_anuncio());
			//stat.setString(5, null);//(Date) anuncio.getFecha_ultima_actualizacion());
			
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
		try {
			stat = conexion.prepareStatement(UPDATE);
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
	
	
	public int actualizarFecha(int id, String tipo_anuncio) throws DAOException {
		PreparedStatement stat = null;
		int filasActualizadas = 0;
		Calendar fecha = new GregorianCalendar();
		int ano = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH);
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		Date fecha_hoy = new Date(ano, mes, dia);
		try {
			stat = conexion.prepareStatement(UPDATEDATE);
			stat.setDate(1, fecha_hoy);
			stat.setInt(2, id);
			stat.setString(3, tipo_anuncio);
			filasActualizadas = stat.executeUpdate();
		}catch (SQLException ex) {
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
	 * 	FUNCIONA
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
		PreparedStatement stat = null;
		ResultSet rs = null;
		List <Anuncio> anuncios = new ArrayList<>();
		try {
			stat = conexion.prepareStatement(GETTYPE);
			stat.setString(1, tipo_anuncio);
			rs = stat.executeQuery();
			while(rs.next()) {
				anuncios.add(convertir(rs));
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
		return anuncios;
	}
	
	/*
	 * 	FUNCIONA
	 */
	public List<Anuncio> obtenerPorParametro(String localidad, String tipo_anuncio) throws DAOException {
		
		PreparedStatement stat = null;
		
		ResultSet rs = null;
		
		List <Anuncio> anuncios = new LinkedList<>();
		
		try {
			
			stat = conexion.prepareStatement(GETLOCTYP);
			stat.setString(1, tipo_anuncio);
			stat.setString(2, localidad);
			rs = stat.executeQuery();
			
			while(rs.next()) {
				anuncios.add(convertir(rs));
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
		
		return anuncios;
		
	}

	
	@Override
	public Anuncio obtener(String value) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Anuncio obtener(int id, String tipo_anuncio) throws DAOException{
		PreparedStatement stat = null;
		ResultSet rs = null;
		Anuncio anuncio = null;
		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setInt(1, id);
			stat.setString(2, tipo_anuncio);
			rs = stat.executeQuery();
			if(rs.next()) {
				anuncio = convertir(rs);
			}else {
				//throw new DAOException("No se ha encontrado ningún registro");
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
		return anuncio;
	}

	/*
	 * 	FUNCIONA
	 */
	@Override
	public List<Anuncio> obtenerTodos() throws DAOException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		List <Anuncio> anuncios = new LinkedList<>();
		
		try {
		
			stat = conexion.prepareStatement(GETALL);
			rs = stat.executeQuery();
			
			while(rs.next()) {
				anuncios.add(convertir(rs));
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
		return anuncios;
	}

	
	private Anuncio convertir(ResultSet rs) throws SQLException {
		
		int id_inmueble = rs.getInt("id_inmueble");
		String tipo_anuncio = rs.getString("tipo_anuncio");
		double precio = rs.getDouble("precio");
		//Date fecha_anuncio = rs.getDate("fecha_anuncio");
		//Date fecha_ultima_actualizacion = rs.getDate("fecha_ultima_actualizacion");
		
		//Anuncio anuncio = new Anuncio(id_inmueble, tipo_anuncio, precio, fecha_anuncio, fecha_ultima_actualizacion);
		
		Anuncio anuncio = new Anuncio(id_inmueble, tipo_anuncio, precio, null, null);
		
		return anuncio;
	}
}
