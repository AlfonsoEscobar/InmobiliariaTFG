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
import es.dao.InmuebleDAO;
import es.modelos.Inmueble;

public class MySQLInmuebleDAO implements InmuebleDAO {

	
	
	final String INSERT = "INSERT INTO inmueble(provincia, localidad, calle, numero, piso, puerta,"
							+ "propietario, descripcion, metros2, num_habitaciones, num_banos,"
							+ "tipo_edificacion, tipo_obra, equipamiento, exteriores, garaje,"
							+ "trastero, ascensor, ultima_planta, mascotas) "
							+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	final String DELETE = "DELETE FROM inmueble WHERE id_inmueble = ?";
	
	final String UPDATE = "UPDATE inmueble SET provincia = ?, localidad = ?, calle = ?, numero = ?,"
							+ "piso = ?, puerta = ?, descripcion = ?, metros2 = ?,"
							+ "num_habitaciones = ?, num_banos = ?, tipo_edificacion = ?,"
							+ "tipo_obra = ?, equipamiento = ?, exteriores = ?, garaje = ?, trastero = ?,"
							+ "ascensor = ?, ultima_planta = ?, mascotas = ? WHERE id_inmueble = ?";
	
	final String GETONE = "SELECT * FROM inmueble WHERE id_inmueble = ?";
	
	final String GETALL = "SELECT * FROM inmueble";
	
	final String IDMAX = "SELECT MAX(id_inmueble) FROM inmueble";
	
	
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
		FUNCIONA
	*/
	@Override
	public int insertar(Inmueble inmueble) throws DAOException {
		PreparedStatement stat = null;
		int filasModificadas = 0;
		
		try {
			stat = conexion.prepareStatement(INSERT);
			stat.setString(1, inmueble.getProvincia());
			stat.setString(2, inmueble.getLocalidad());
			stat.setString(3, inmueble.getCalle());
			stat.setInt(4, inmueble.getNumero());
			stat.setInt(5, inmueble.getPiso());
			stat.setString(6, inmueble.getPuerta());
			stat.setInt(7, inmueble.getPropietario());
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

	
	/*
	 * FUNCIONA
	 */
	@Override
	public int modificar(Integer id, Inmueble inmueble) throws DAOException {
		
		PreparedStatement stat = null;
		int filasModificadas = 0;
		
		try {
			stat = conexion.prepareStatement(UPDATE);
			stat.setString(1, inmueble.getProvincia());
			stat.setString(2, inmueble.getLocalidad());
			stat.setString(3, inmueble.getCalle());
			stat.setInt(4, inmueble.getNumero());
			stat.setInt(5, inmueble.getPiso());
			stat.setString(6, inmueble.getPuerta());
			stat.setString(7, inmueble.getDescripcion());
			stat.setDouble(8, inmueble.getMetros2());
			stat.setInt(9, inmueble.getNum_habitaciones());
			stat.setInt(10, inmueble.getNum_banos());
			stat.setString(11, inmueble.getTipo_edificacion());
			stat.setString(12, inmueble.getTipo_obra());
			stat.setString(13, inmueble.getEquipamiento());
			stat.setString(14, inmueble.getExteriores());
			stat.setBoolean(15, inmueble.isGaraje());
			stat.setBoolean(16, inmueble.isTrastero());
			stat.setBoolean(17, inmueble.isAscensor());
			stat.setBoolean(18, inmueble.isUltima_planta());
			stat.setBoolean(19, inmueble.isMascotas());
			stat.setInt(20, id);
		
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
	
	
	/*
	 * 	FUNCIONA
	 */
	@Override
	public int eliminar(Integer id) throws DAOException {
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


	/*
	 *  NO FUNCIONA creo que es el metodo convertir()
	 */
	@Override
	public Inmueble obtener(Integer id) throws DAOException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		Inmueble inmueble = new Inmueble();
		
		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setInt(1, id);
			rs = stat.executeQuery();
			
			if(rs.next()) {
				
				/*inmueble.setCalle(rs.getString("calle"));
				inmueble.setLocalidad(rs.getString("localidad"));
				inmueble.setProvincia(rs.getString("provincia"));
				inmueble.setPiso(rs.getInt("piso"));
				inmueble.setId_inmueble(rs.getInt("id_inmueble"));
				inmueble.setMascotas(rs.getBoolean("mascotas"));
				inmueble.setMetros2(rs.getDouble("metros2"));
				*/
				
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
	
	
	/*
	 *  NO FUNCIONA creo que es el metodo convertir()
	 */
	@Override
	public List<Inmueble> obtenerTodos() throws DAOException {
		
		//PreparedStatement stat = null;
		
		Statement stat = null;
		ResultSet rs = null;
		List <Inmueble> inmuebles = new LinkedList<>();
		
		try {
		
			//stat = conexion.prepareStatement(GETALL);
			//rs = stat.executeQuery();
			
			stat = conexion.createStatement();
			rs = stat.executeQuery(GETALL);
			
			while(rs.next()) {
				
				// De esta forma funciona aun que no estan puestos todos los parametros
				
				/*Inmueble inmueble = new Inmueble();
				inmueble.setCalle(rs.getString("calle"));
				inmueble.setLocalidad(rs.getString("localidad"));
				inmueble.setProvincia(rs.getString("provincia"));
				inmueble.setPiso(rs.getInt("piso"));
				inmueble.setId_inmueble(rs.getInt("id_inmueble"));
				inmueble.setMascotas(rs.getBoolean("mascotas"));
				inmueble.setMetros2(rs.getDouble("metros2"));
				
				inmuebles.add(inmueble);*/
				
				// Asi no funciona
				inmuebles.add(convertir(rs));
				
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
		return inmuebles;
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
		
		Inmueble inmueble = new Inmueble(provincia, localidad, calle, numero, piso, puerta, propietario, descripcion, metros2, num_habitaciones, 
				num_banos, tipo_edificacion, tipo_obra, equipamiento, exteriores, garaje, trastero, ascensor, ultima_planta, mascotas);
		
		inmueble.setId_inmueble(id_inmueble);
		
		return inmueble;
	}
	
	
	//Ya no sirve puesto que se generan solos
	
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
