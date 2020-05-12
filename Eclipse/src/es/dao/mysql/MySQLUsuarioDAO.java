package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import es.dao.DAOException;
import es.dao.util.Conversor;
import es.modelos.Usuario;

public class MySQLUsuarioDAO {

	final String INSERT = "INSERT INTO usuario(correo, contrasena, nombre,"
			+ "telefono1, telefono2, imagen_perfil) VALUES(?,?,?,?,?,?)";

	final String UPDATE = "UPDATE usuario SET contrasena = ?, nombre = ?, telefono1 = ?,"
			+ "telefono2 = ?, imagen_pefil = ? WHERE correo = ?";

	// Quité la imagen de perfil por que daba errores
	final String UPDATE2 = "UPDATE usuario SET contrasena = ?, nombre = ?, telefono1 = ?,"
			+ "telefono2 = ? WHERE id_usuario = ?";

	final String DELETE = "DELETE FROM usuario WHERE correo = ?";
	
	final String DELETE2 = "DELETE FROM usuario WHERE id_usuario = ?";

	final String GETONE = "SELECT id_usuario, correo, contrasena, nombre, telefono1,"
						+ "telefono2, imagen_perfil FROM usuario WHERE correo = ?";
	
	final String GETONEID = "SELECT id_usuario, correo, contrasena, nombre, telefono1,"
			+ "telefono2, imagen_perfil FROM usuario WHERE id_usuario = ?";

	final String VERIFICAR = "SELECT * FROM usuario WHERE correo = ? and contrasena = ?";

	final String REPETIDO = "SELECT * FROM usuario WHERE correo = ?";
	
	final String UPDATEPERFIL = "UPDATE usuario SET imagen_perfil = ? WHERE id_usuario = ?";


	private Connection conexion;

	public MySQLUsuarioDAO(DataSource conexion) {

		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * FUNCIONA
	 */
	public int insertar(Usuario usuario) throws DAOException {
		PreparedStatement stat = null;
		ResultSet generatedKeys = null;
		int generatedId = -1;
		try {
			stat = conexion.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, usuario.getCorreo());
			stat.setString(2, usuario.getContrasena());
			stat.setString(3, usuario.getNombre());
			stat.setString(4, usuario.getTelefono1());
			stat.setString(5, usuario.getTelefono2());
			
			stat.setBytes(6, usuario.getImagen_perfil());
			
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

	/*
	 * FUNCIONA
	 */
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
		return filasAfectadas;

	}

	/*
	 * FUNCIONA
	 */
	public int modificar(int id, Usuario usuario) throws DAOException {

		PreparedStatement stat = null;
		int filasAfectadas = 0;

		try {

			stat = conexion.prepareStatement(UPDATE2);

			stat.setString(1, usuario.getContrasena());
			stat.setString(2, usuario.getNombre());
			stat.setString(3, usuario.getTelefono1());
			stat.setString(4, usuario.getTelefono2());
			// stat.setString(5, null);
			// stat.setInt(6, id);
			stat.setInt(5, id);

			filasAfectadas = stat.executeUpdate();

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

		return filasAfectadas;

	}

	/*
	 * FUNCIONA
	 */
	public int eliminar(String correo) throws DAOException {
		PreparedStatement stat = null;
		int filasAfectadas;
		try {
			stat = conexion.prepareStatement(DELETE);
			stat.setString(1, correo);
			filasAfectadas = stat.executeUpdate();
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
		return filasAfectadas;
	}

	public int eliminar(int id) throws DAOException {
		PreparedStatement stat = null;
		int filasAfectadas;
		try {
			stat = conexion.prepareStatement(DELETE2);
			stat.setInt(1, id);
			filasAfectadas = stat.executeUpdate();
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
		return filasAfectadas;
	}

	/*
	 * FUNCIONA
	 */
	public Usuario obtener(String correo) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Usuario usuario = null;

		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setString(1, correo);
			rs = stat.executeQuery();
			if (rs.next()) {
				usuario = Conversor.convertirUsuario(rs);
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
		return usuario;
	}
	
	
	public Usuario obtenerPorId_usuario(int id_usuario) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Usuario usuario = null;

		try {
			stat = conexion.prepareStatement(GETONEID);
			stat.setInt(1, id_usuario);
			rs = stat.executeQuery();
			if (rs.next()) {
				usuario = Conversor.convertirUsuario(rs);
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
		return usuario;
	}
	
	/*
	 * 	FUNCIONA
	 */
	public boolean verificarUsuarioEnBase(String correo, String contrasena) throws DAOException {
		boolean insertado = false;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = conexion.prepareStatement(VERIFICAR);
			stat.setString(1, correo);
			stat.setString(2, contrasena);
			rs = stat.executeQuery();
			if (rs.next()) {
				insertado = true;
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

		return insertado;
	}

	public boolean usuarioRepetido(String correo) throws DAOException {
		boolean repetido = false;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = conexion.prepareStatement(REPETIDO);
			stat.setString(1, correo);
			rs = stat.executeQuery();
			if (rs.next()) {
				repetido = true;
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

		return repetido;
	}
	
	public void establecerFotoPerfil(byte[] foto, int id_usuario) throws DAOException {
		PreparedStatement stat = null;
		
		try {
			stat = conexion.prepareStatement(UPDATEPERFIL);
			stat.setBytes(1, foto);
			stat.setInt(2, id_usuario);
			stat.executeUpdate();
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

}
