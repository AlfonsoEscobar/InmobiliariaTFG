package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import es.dao.util.Conversor;
import es.dao.util.InfoAnuncio;
import es.modelos.Anuncio;
import es.modelos.Favorito;

public class MySQLFavoritoDAO2 {

	final String INSERT = "INSERT INTO favorito(usuario_favorito, inmueble_favorito, tipo_anuncio) " + "VALUES(?,?,?)";

	final String DELETE = "DELETE FROM favorito WHERE usuario_favorito = ? and inmueble_favorito = ? "
			+ "and tipo_anuncio = ?";

	final String DELETEFROMUSER = "DELETE FROM favorito WHERE usuario_favorito = ?";

	final String GETONE = "SELECT * FROM favorito WHERE usuario_favorito = ? and "
			+ "inmueble_favorito = ? and tipo_anuncio = ?";

	final String GETFAVUSER = "SELECT * FROM favorito WHERE usuario_favorito = ?";
	
	final String GETANUNCIOSFAV = "SELECT * FROM anuncio WHERE id_inmueble = (SELECT * FROM favorito WHERE usuario_favorito = ?)";
	
	final String GETINFOANUNCIOSFAV = "SELECT i.*, a.*, u.* from inmueble i inner join anuncio a "
										+ "on a.id_inmueble = i.id_inmueble inner join usuario u on i.propietario = "
										+ "u.id_usuario WHERE a.id_inmueble in "
										+ "(SELECT inmueble_favorito FROM favorito WHERE usuario_favorito = ?)";

	private Connection conexion;

	public MySQLFavoritoDAO2(DataSource conexion) {
		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int insertar(Favorito favorito) throws DAOException {

		PreparedStatement stat = null;
		ResultSet generatedKeys = null;
		int generatedId = -1;

		try {

			stat = conexion.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stat.setInt(1, favorito.getUsuario_favorito());
			stat.setInt(2, favorito.getInmueble_favorito());
			stat.setString(3, favorito.getTipo_anuncio());

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

	// Este métedo elimina un favorito de un usuario
	public int eliminar(int id_usuario, int id_inmueble, String tipo_anuncio) throws DAOException {
		PreparedStatement stat = null;
		int filasEliminadas = 0;
		try {
			stat = conexion.prepareStatement(DELETE);
			stat.setInt(1, id_usuario);
			stat.setInt(2, id_inmueble);
			stat.setString(3, tipo_anuncio);
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

	/*
	 * FUNCIONA
	 */
	// Este método borra todos los favoritos de un usuario
	public int eliminar(Integer id_usuario) throws DAOException {

		PreparedStatement stat = null;
		int filasEliminadas = 0;

		try {

			stat = conexion.prepareStatement(DELETEFROMUSER);
			stat.setInt(1, id_usuario);
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

	public Favorito obtenerFavorito(int id_usuario, int id_inmueble, String tipo_anuncio) throws DAOException {

		PreparedStatement stat = null;
		ResultSet rs = null;
		Favorito favorito = null;

		try {

			stat = conexion.prepareStatement(GETONE);
			stat.setInt(1, id_usuario);
			stat.setInt(2, id_inmueble);
			stat.setString(3, tipo_anuncio);
			rs = stat.executeQuery();

			if (rs.next()) {
				favorito = Conversor.convertirFavorito(rs);
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

		return favorito;

	}

	/*
	 * FUNCIONA
	 */
	// Este método obtiene todos los favoritos de un usuario
	public List<Favorito> listaFavoritosDeUsuario(Integer id_usuario) throws DAOException {

		PreparedStatement stat = null;
		ResultSet rs = null;

		List<Favorito> favoritos = new LinkedList<>();

		try {

			stat = conexion.prepareStatement(GETFAVUSER);
			stat.setInt(1, id_usuario);
			rs = stat.executeQuery();

			while (rs.next()) {
				favoritos.add(Conversor.convertirFavorito(rs));
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

		return favoritos;

	}
	
	/*
	 * Obtenemos los objetos Anuncio de los anuncio favoritos asociados a un usuario
	 * */
	public List<Anuncio> listaAnunciosFavoritos(int usuario) throws DAOException {

		PreparedStatement stat = null;

		ResultSet rs = null;

		List<Anuncio> anuncios = new LinkedList<>();

		try {

			stat = conexion.prepareStatement(GETANUNCIOSFAV);
			stat.setInt(1, usuario);
			rs = stat.executeQuery();

			while (rs.next()) {
				anuncios.add(Conversor.convertirAnuncio(rs));
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

		return anuncios;

	}
	
	
	/*
	 * Obtenemos la InfoAnuncio de los anuncios marcados como favoritos de un usuario, metiendo el id del usuario por parámetro. CHECKEAR EN LA CAPA RESTFUL
	 * */
	public LinkedList<InfoAnuncio> listaInfoAnunciosFavoritos(int usuario) throws DAOException {

		LinkedList<InfoAnuncio> lista = new LinkedList<>();

		PreparedStatement stat = null;
		ResultSet rs = null;

		try {

			stat = conexion.prepareStatement(GETINFOANUNCIOSFAV);
			stat.setInt(1, usuario);
			rs = stat.executeQuery();

			while (rs.next()) {

				lista.add(Conversor.convertirInfoAnuncio(rs));

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
