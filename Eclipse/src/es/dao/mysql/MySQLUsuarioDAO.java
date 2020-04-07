package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import es.dao.DAOException;
import es.dao.UsuarioDAO;
import es.modelos.Usuario;

public class MySQLUsuarioDAO implements UsuarioDAO{

	final String INSERT = "INSERT INTO USUARIO(correo, contrasena, id_usuario, nombre, telefono1, telefono2, imagen_perfil) VALUES(?,?,?,?,?,?,?)";
	final String UPDATE = "UPDATE USUARIO SET contrasena = ?, nombre = ?, telefono1 = ?, telefono2 = ?, imagen_pefil = ? WHERE id_usuario = ?";
	final String DELETE = "DELETE FROM USUARIO WHERE id_usuario = ?";
	final String GETALL = "SELECT id_usuario, correo, nombre, telefono1, telefono2, imagen_perfil FROM USUARIO";
	final String GETONE = "SELECT id_usuario, correo, contrasena, nombre, telefono1, telefono2, imagen_perfil WHERE id_usuario = ?";
	
	private Connection conexion;
	
	public MySQLUsuarioDAO(Connection conexion) {
		this.conexion = conexion;
	}
	
	@Override
	public int insertar(Usuario usuario) throws DAOException {
		PreparedStatement stat = null;
		int filasModificadas = 0;
		try {
			stat = conexion.prepareStatement(INSERT);
			stat.setString(1, usuario.getCorreo());
			stat.setString(2, usuario.getContrasena());
			stat.setString(3, usuario.getId_usuario());
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
	public int modificar(Usuario t) {
		return 0;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int elminiar(Usuario t) {
		return 0;
		// TODO Auto-generated method stub
		
	}
	
	public Usuario convertir(ResultSet rs) throws SQLException {
		String correo = rs.getString("correo");
		String contrasena = rs.getString("contrasena");
		String nombre = rs.getString("nombre");
		String telefono1 = rs.getString("telefono1");
		String telefono2 = rs.getString("telefono2");
		byte[] imagen_perfil = rs.getBytes("imagen_perfil");
		Usuario usuario = new Usuario(correo, contrasena, nombre, telefono1, telefono2, imagen_perfil);
		usuario.setId_usuario("id_usuario");
		return usuario;
	}

	@Override
	public List<Usuario> obtenerPorParametro(String k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario obtener(String k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> obtenerTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
