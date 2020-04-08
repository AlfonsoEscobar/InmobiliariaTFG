package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import es.dao.DAOException;
import es.dao.UsuarioDAO;
import es.modelos.Usuario;

public class MySQLUsuarioDAO implements UsuarioDAO{

	final String INSERT = "INSERT INTO USUARIO(correo, contrasena, id_usuario, nombre, telefono1, telefono2, imagen_perfil) VALUES(?,?,?,?,?,?,?)";
	final String UPDATE = "UPDATE USUARIO SET contrasena = ?, nombre = ?, telefono1 = ?, telefono2 = ?, imagen_pefil = ? WHERE correo = ?";
	final String DELETE = "DELETE FROM USUARIO WHERE correo = ?";
	final String GETALL = "SELECT id_usuario, correo, nombre, telefono1, telefono2, imagen_perfil FROM USUARIO";
	final String GETONE = "SELECT id_usuario, correo, contrasena, nombre, telefono1, telefono2, imagen_perfil FROM USUARIO WHERE correo = ?";
	final String IDMAX = "SELECT MAX(id_usuario) FROM USUARIO";
	
	private Connection conexion;
	
	public MySQLUsuarioDAO(DataSource conexion) {

		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int insertar(Usuario usuario) throws DAOException {
		PreparedStatement stat = null;
		int filasModificadas = 0;
		otorgarId(usuario);
		try {
			stat = conexion.prepareStatement(INSERT);
			stat.setString(1, usuario.getCorreo());
			stat.setString(2, usuario.getContrasena());
			stat.setInt(3, usuario.getId_usuario());
			stat.setString(4, usuario.getNombre());
			stat.setString(5, usuario.getTelefono1());
			stat.setString(6, usuario.getTelefono2());
			stat.setBytes(7,usuario.getImagen_perfil());
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
	public int modificar(String correo, Usuario usuario) throws DAOException {
		PreparedStatement stat = null;
		int filasAfectadas = 0;
		try {
			stat = conexion.prepareStatement(UPDATE);
			stat.setString(1, usuario.getContrasena());
			stat.setString(2, usuario.getNombre());
			stat.setString(3, usuario.getTelefono1());
			stat.setString(4, usuario.getTelefono2());
			stat.setBytes(5, usuario.getImagen_perfil());
			stat.setString(6, correo);
			filasAfectadas = stat.executeUpdate();
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
		return filasAfectadas;
		
	}
	

	@Override
	public int elminiar(String correo) throws DAOException {
		PreparedStatement stat = null;
		int filasAfectadas;
		try {
			stat = conexion.prepareStatement(DELETE);
			stat.setString(1, correo);
			filasAfectadas = stat.executeUpdate();
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
		return filasAfectadas;
		
		
	}

	@Override
	public List<Usuario> obtenerPorParametro(String k) throws DAOException {
		return null;
	}

	@Override
	public Usuario obtener(String correo) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Usuario usuario = null;
		
		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setString(1, correo);
			rs = stat.executeQuery();
			if(rs.next()) {
				usuario = convertir(rs);
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
		return usuario;
	}

	@Override
	public List<Usuario> obtenerTodos() throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Usuario> usuarios = new ArrayList<>();
		try {
			stat = conexion.prepareStatement(GETALL);
			rs = stat.executeQuery();
			while(rs.next()) {
				usuarios.add(convertir(rs)); 
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
		return usuarios;
	}
	
	private Usuario convertir(ResultSet rs) throws SQLException {
		String correo = rs.getString("correo");
		String contrasena = rs.getString("contrasena");
		int id_usuario = rs.getInt("id_usuario");
		String nombre = rs.getString("nombre");
		String telefono1 = rs.getString("telefono1");
		String telefono2 = rs.getString("telefono2");
		byte[] imagen_perfil = rs.getBytes("imagen_perfil");
		Usuario usuario = new Usuario(correo, contrasena, nombre, telefono1, telefono2, imagen_perfil);
		usuario.setId_usuario(id_usuario);
		return usuario;
	}
	
	private void otorgarId(Usuario usuario) throws DAOException  {
		PreparedStatement stat = null;
		ResultSet rs = null;
		int max = 0;
		try {
			stat = conexion.prepareStatement(IDMAX);
			rs = stat.executeQuery();
			if(rs.next()) {
				max = rs.getInt("id_usuario");
			}else if(rs.equals(null)){
				max = 0;
			}else {
				throw new DAOException ("No se ha encontrado ningún registro");
			}
			usuario.setId_usuario(max + 1);
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
