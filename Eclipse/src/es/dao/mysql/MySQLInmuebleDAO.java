package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import es.dao.DAOException;
import es.dao.InmuebleDAO;
import es.modelos.Inmueble;

public class MySQLInmuebleDAO implements InmuebleDAO {

	
	
	final String INSERT = "INSERT INTO INMUEBLE(provincia, localidad, calle, numero, piso, puerta, id_inmueble, propietario, descripcion, metros2,"
			+ "num_habitaciones, num_banos, tipo_edificacion, tipo_obra, equipamiento, exteriores, garaje, trastero, ascensor, ultima_planta, mascotas) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	final String DELETE = "DELETE FROM INMUEBLE WHERE id_inmueble = ?";
	final String GETONE = "SELECT * FROM INMUEBLE WHERE id_inmueble = ?";
	final String IDMAX = "SELECT MAX(id_inmueble) FROM INMUEBLE";
	/*final String UPDATE = "UPDATE USUARIO SET contrasena = ?, nombre = ?, telefono1 = ?, telefono2 = ?, imagen_pefil = ? WHERE correo = ?";
	final String GETALL = "SELECT id_usuario, correo, nombre, telefono1, telefono2, imagen_perfil FROM USUARIO";
	*/
	
	private Connection conexion;
	
	public MySQLInmuebleDAO(DataSource conexion) {
		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
		
	*/
	@Override
	public int insertar(Inmueble inmueble) throws DAOException {
		PreparedStatement stat = null;
		int filasModificadas = 0;
		otorgarId(inmueble);
		try {
			stat = conexion.prepareStatement(INSERT);
			stat.setString(1, inmueble.getProvincia());
			stat.setString(2, inmueble.getLocalidad());
			stat.setString(3, inmueble.getCalle());
			stat.setInt(4, inmueble.getNumero());
			stat.setInt(5, inmueble.getPiso());
			stat.setString(6, inmueble.getPuerta());
			stat.setInt(7, inmueble.getId_inmueble());
			stat.setInt(8, inmueble.getPropietario());
			stat.setString(9, inmueble.getDescripcion());
			stat.setFloat(10, inmueble.getMetros2());
			stat.setInt(11, inmueble.getNum_habitaciones());
			stat.setInt(12, inmueble.getNum_banos());
			stat.setString(13, inmueble.getTipo_edificacion());
			stat.setString(14, inmueble.getTipo_obra());
			stat.setString(15, inmueble.getEquipamiento());
			stat.setString(16, inmueble.getExteriores());
			stat.setBoolean(17, inmueble.isGaraje());
			stat.setBoolean(18, inmueble.isTrastero());
			stat.setBoolean(19, inmueble.isAscensor());
			stat.setBoolean(21, inmueble.isUltima_planta());
			stat.setBoolean(21, inmueble.isMascotas());
			filasModificadas = stat.executeUpdate();
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}finally {
			if (stat != null) {
				try {
					stat.close();
				}catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
		}
		return filasModificadas;
	}

	@Override
	public int modificar(Integer value, Inmueble object) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int elminiar(Integer id) throws DAOException {
		PreparedStatement stat = null;
		int filasEliminadas = 0;
		try {
			stat = conexion.prepareStatement(DELETE);
			stat.setInt(1, id);
			filasEliminadas = stat.executeUpdate();
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}finally {
			if (stat != null) {
				try {
					stat.close();
				}catch (SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
		}
		return filasEliminadas;
	}

	@Override
	public List<Inmueble> obtenerPorParametro(Integer value) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Inmueble> obtenerPorParametro(String value) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Inmueble obtener(Integer id) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Inmueble inmueble = null;
		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setInt(1, id);
			rs = stat.executeQuery();
			if(rs.next()) {
				inmueble = convertir(rs);
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
		return inmueble;
	}
	

	@Override
	public List<Inmueble> obtenerTodos() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Inmueble convertir(ResultSet rs) throws SQLException {
		String provincia = rs.getString("provincia");
		String localidad = rs.getString("localidad");
		String calle = rs.getString("calle");
		int numero = rs.getInt("numero");
		int piso = rs.getInt("piso");
		String puerta = rs.getString("puerta");
		int id_inmueble = rs.getInt("id_inmueble");
		int propietario = rs.getInt("propietario");
		String descripcion = rs.getString("comentario");
		float metros2 = rs.getFloat("metros2");
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
		Inmueble inmueble = new Inmueble(provincia, localidad, calle, numero, piso, puerta, propietario, descripcion, metros2, num_habitaciones, 
				num_banos, tipo_edificacion, tipo_obra, equipamiento, exteriores, garaje, trastero, ascensor, ultima_planta, mascotas);
		inmueble.setId_inmueble(id_inmueble);
		return inmueble;
	}
	
	private void otorgarId (Inmueble inmueble) throws DAOException  {
		PreparedStatement stat = null;
		ResultSet rs = null;
		int max = 0;
		try {
			stat = conexion.prepareStatement(IDMAX);
			rs = stat.executeQuery();
			if(rs.next()) {
				max = rs.getInt("id_inmueble");
			}else if(rs.equals(null)){
				max = 0;
			}else {
				throw new DAOException ("No se ha encontrado ningún registro");
			}
			inmueble.setId_inmueble(max + 1);
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
	}

	

	
	
	

}
