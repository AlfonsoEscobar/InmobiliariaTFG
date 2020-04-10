package es.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import es.dao.DAOException;
import es.dao.FavoritoDAO;
import es.modelos.Favorito;

public class MySQLFavoritoDAO implements FavoritoDAO {

	final String INSERT = "INSERT INTO favorito(usuario_favorito, inmueble_favorito, tipo_anuncio) VALUES(?,?,?)";
	final String DELETE = "DELETE FROM favorito WHERE usuario_favorito = ? and inmueble_favorito = ? and tipo_anuncio = ?";
	final String DELETEALL = "DELETE FROM favorito WHERE usuario_favorito = ?";
	final String GETONE = "SELECT * FROM favorito WHERE usuario_favorito = ? and inmueble_favorito = ? and tipo_anuncio = ?";
	final String GETFAVUSER = "SELECT FROM favorito WHERE usuario_favorito = ?";
	final String GETALL = "SELECT * FROM favorito";

	private Connection conexion;

	public MySQLFavoritoDAO(DataSource conexion) {
		try {
			this.conexion = conexion.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int insertar(Favorito favorito) throws DAOException {
		PreparedStatement stat = null;
		int filasInsertadas = 0;
		{
			try {
				stat = conexion.prepareStatement(INSERT);
				stat.setInt(1, favorito.getUsuario_favorito());
				stat.setInt(2, favorito.getInmueble_favorito());
				stat.setString(3, favorito.getTipo_anuncio());
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
	public int modificar(Integer value, Favorito object) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	// Este método borra todos los favoritos de un usuario
	@Override
	public int eliminar(Integer id_usuario) throws DAOException {
		PreparedStatement stat = null;
		int filasEliminadas = 0;
		try {
			stat = conexion.prepareStatement(DELETEALL);
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

	//Este método obtiene todos los anuncios favoritos de un usuario
	@Override
	public List<Favorito> obtenerPorParametro(Integer id_usuario) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List <Favorito> favoritos = new ArrayList<>();
		try {
			stat = conexion.prepareStatement(GETFAVUSER);
			stat.setInt(1, id_usuario);
			rs = stat.executeQuery();
			while(rs.next()) {
				favoritos.add(convertir(rs));
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
		return favoritos;
	}

	@Override
	public Favorito obtener(Integer value) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Favorito obtener(int id_usuario, int id_inmueble, String tipo_anuncio) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Favorito favorito = null;
		try {
			stat = conexion.prepareStatement(GETONE);
			stat.setInt(1, id_usuario);
			stat.setInt(2, id_inmueble);
			stat.setString(3, tipo_anuncio);
			rs = stat.executeQuery();
			if(rs.next()) {
				favorito = convertir(rs);
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
		return favorito;
	}

	@Override
	public List<Favorito> obtenerTodos() throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List <Favorito> favoritos = new ArrayList<>();
		try {
			stat = conexion.prepareStatement(GETALL);
			rs = stat.executeQuery();
			while(rs.next()) {
				favoritos.add(convertir(rs));
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
		return favoritos;
	}

	private Favorito convertir(ResultSet rs) throws SQLException {
		int inmueble_favorito = rs.getInt("inmueble_favorito");
		int usuario_favorito = rs.getInt("usuario_favorito");
		String tipo_anuncio = rs.getString("tipo_anuncio");
		Favorito favorito = new Favorito(usuario_favorito, inmueble_favorito, tipo_anuncio);
		return favorito;
	}

}
