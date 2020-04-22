package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import es.dao.DAOException;
import es.dao.UsuarioDAO;
import es.modelos.Anuncio;
import es.modelos.Favorito;
import es.modelos.InfoUsuario;
import es.modelos.Inmueble;
import es.modelos.Usuario;

public class MySQLUsuarioDAO implements UsuarioDAO {

	final String INSERT = "INSERT INTO usuario(correo, contrasena, nombre,"
			+ "telefono1, telefono2, imagen_perfil) VALUES(?,?,?,?,?,?)";

	final String UPDATE = "UPDATE usuario SET contrasena = ?, nombre = ?, telefono1 = ?,"
			+ "telefono2 = ?, imagen_pefil = ? WHERE correo = ?";

	// Quite la imagen de perfil por que daba errores
	final String UPDATE2 = "UPDATE usuario SET contrasena = ?, nombre = ?, telefono1 = ?,"
			+ "telefono2 = ? WHERE id_usuario = ?";

	final String DELETE = "DELETE FROM usuario WHERE correo = ?";

	final String GETALL = "SELECT id_usuario, correo, nombre, telefono1, telefono2, imagen_perfil FROM usuario";

	final String GETONE = "SELECT id_usuario, correo, contrasena, nombre, telefono1,"
						+ "telefono2, imagen_perfil FROM usuario WHERE correo = ?";

	final String INFOUSER = "SELECT correo, nombre, telefono1, telefono2 FROM usuario WHERE id_usuario = ?";

	final String INFOINMUEBLES = "SELECT * FROM inmueble WHERE propietario = ?";

	final String INFOANUNCIOS = "SELECT * FROM anuncio WHERE id_inmueble = "
									+ "(SELECT id_inmueble FROM inmueble WHERE propietario = ?)";

	final String INFOFAVORITOS = "SELECT * FROM favorito WHERE usuario_favorito = ?";

	final String VERIFICAR = "SELECT * FROM usuario WHERE correo = ? and contrasena = ?";

	final String REPETIDO = "SELECT * FROM usuario WHERE correo = ?";

	// final String IDMAX = "SELECT MAX(id_usuario) FROM usuario";

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

	@Override
	public int insertar(Usuario usuario) throws DAOException {
		PreparedStatement stat = null;
		int filasModificadas = 0;
		try {
			stat = conexion.prepareStatement(INSERT);
			stat.setString(1, usuario.getCorreo());
			stat.setString(2, usuario.getContrasena());
			stat.setString(3, usuario.getNombre());
			stat.setString(4, usuario.getTelefono1());
			stat.setString(5, usuario.getTelefono2());
			
			stat.setBytes(6, usuario.getImagen_perfil());
			
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

	/*
	 * FUNCIONA
	 */

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
	@Override
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

	@Override
	public List<Usuario> obtenerPorParametro(String k) throws DAOException {
		return null;
	}

	/*
	 * FUNCIONA
	 */
	@Override
	public Usuario obtener(String correo) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Usuario usuario = null;

		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setString(1, correo);
			rs = stat.executeQuery();
			if (rs.next()) {
				usuario = convertir(rs);
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
	
	
	//Para implementar este método he copiado los métodos de convertir de cada clase; 
	//método que convierte en objetos Java fila de la base de datos.
	
	public InfoUsuario obtenerInfoUsuario (int id_usuario) throws DAOException {
		
		InfoUsuario infoUser = null;
		PreparedStatement statUser = null, statInmu = null, statAnun = null , statFav = null;
		ResultSet rsUser = null, rsInmu = null, rsAnun = null, rsFav = null;
		String correo = "", nombre = "", telefono1 = "", telefono2 = "";
		LinkedList<Inmueble> inmuebles = new LinkedList<Inmueble>();
		LinkedList<Anuncio> anuncios = new LinkedList<Anuncio>();
		LinkedList<Favorito> favoritos = new LinkedList<Favorito>();
		
		try {
			
			// Recibiendo el usuario
			statUser = conexion.prepareStatement(INFOUSER);
			statUser.setInt(1, id_usuario);
			rsUser = statUser.executeQuery();
			
			if(rsUser.next()) {
				correo = rsUser.getString("correo");
				nombre = rsUser.getString("nombre");
				telefono1 = rsUser.getString("telefono1");
				telefono2 = rsUser.getString("telefono2");
			}
			
			// Recibiendo los inmueble
			statInmu = conexion.prepareStatement(INFOINMUEBLES);
			statInmu.setInt(1, id_usuario);
			rsInmu = statInmu.executeQuery();
			
			while(rsInmu.next()) {
				inmuebles.add(convertirInmueble(rsInmu));
			}
			
			//Recibiendo los anuncios
			statAnun = conexion.prepareStatement(INFOANUNCIOS);
			statAnun.setInt(1, id_usuario);
			rsAnun = statAnun.executeQuery();
			
			while(rsAnun.next()) {
				anuncios.add(convertirAnuncio(rsAnun));
			}
			
			// Recibiendo los favoritos
			statFav = conexion.prepareStatement(INFOFAVORITOS);
			statFav.setInt(1, id_usuario);
			rsFav = statFav.executeQuery();
			
			while(rsFav.next()) {
				favoritos.add(convertirFavorito(rsFav));
			}
			
			// Creando la clase
			
			infoUser = new InfoUsuario(nombre, correo, telefono1, telefono2, id_usuario,
											inmuebles, anuncios, favoritos);
		
		}catch(SQLException ex){
			throw new DAOException("Error en SQL", ex);
		
		//Cerrando todo a la base de datos
		}finally {
			if(rsUser != null) {
				try {
					rsUser.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(statUser != null) {
				try {
					statUser.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(rsInmu != null) {
				try {
					rsInmu.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(statInmu != null) {
				try {
					statInmu.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(rsAnun != null) {
				try {
					rsAnun.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(statAnun != null) {
				try {
					statAnun.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(rsFav != null) {
				try {
					rsFav.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			if(statFav != null) {
				try {
					statFav.close();
				}catch(SQLException ex) {
					throw new DAOException("Error en SQL", ex);
				}
			}
			
		}
		
		return infoUser;
	}

	@Override
	public List<Usuario> obtenerTodos() throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Usuario> usuarios = new ArrayList<>();
		try {
			stat = conexion.prepareStatement(GETALL);
			rs = stat.executeQuery();
			while (rs.next()) {
				usuarios.add(convertir(rs));
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

	/*
	 * public int obtenerMaxId () throws DAOException { PreparedStatement stat =
	 * null; ResultSet rs = null; int max = 0; try { stat =
	 * conexion.prepareStatement(GETALL); rs = stat.executeQuery(); if(rs.next()) {
	 * max = rs.getInt("id_usuario"); }else { //throw new DAOException
	 * ("No se ha encontrado ningún registro"); } }catch(SQLException ex){ throw new
	 * DAOException("Error en SQL", ex); }finally { if(rs != null) { try {
	 * rs.close(); }catch(SQLException ex) { throw new DAOException("Error en SQL",
	 * ex); } } if(stat != null) { try { stat.close(); }catch(SQLException ex) {
	 * throw new DAOException("Error en SQL", ex); } } } return max; }
	 */

	
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

	//Esto métodos (copiados de otras clases) sirven para devolver toda la información del usuario (datos, anuncios, inmuebles y favoritos)
	private Inmueble convertirInmueble(ResultSet rs) throws SQLException {

		String provincia = rs.getString("provincia");
		String localidad = rs.getString("localidad");
		String calle = rs.getString("calle");
		int numero = rs.getInt("numero");
		String escalera = rs.getString("escalera");
		int piso = rs.getInt("piso");
		String puerta = rs.getString("puerta");
		int id_inmueble = rs.getInt("id_inmueble");
		int propietario = rs.getInt("propietario");
		String descripcion = rs.getString("descripcion");
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

		Inmueble inmueble = new Inmueble(provincia, localidad, calle, numero, escalera, piso, puerta, propietario,
				descripcion, metros2, num_habitaciones, num_banos, tipo_edificacion, tipo_obra, equipamiento,
				exteriores, garaje, trastero, ascensor, ultima_planta, mascotas);

		inmueble.setId_inmueble(id_inmueble);

		return inmueble;
	}

	private Anuncio convertirAnuncio(ResultSet rs) throws SQLException {

		int id_inmueble = rs.getInt("id_inmueble");
		String tipo_anuncio = rs.getString("tipo_anuncio");
		double precio = rs.getDouble("precio");
		// Date fecha_anuncio = rs.getDate("fecha_anuncio");
		// Date fecha_ultima_actualizacion = rs.getDate("fecha_ultima_actualizacion");

		// Anuncio anuncio = new Anuncio(id_inmueble, tipo_anuncio, precio,
		// fecha_anuncio, fecha_ultima_actualizacion);

		Anuncio anuncio = new Anuncio(id_inmueble, tipo_anuncio, precio, null, null);

		return anuncio;
	}
	
	private Favorito convertirFavorito(ResultSet rs) throws SQLException {

		int usuario_favorito = rs.getInt("usuario_favorito");
		int inmueble_favorito = rs.getInt("inmueble_favorito");
		String tipo_anuncio = rs.getString("tipo_anuncio");
		
		Favorito favorito = new Favorito(usuario_favorito, inmueble_favorito, tipo_anuncio);
		
		return favorito;
		
	}
}
