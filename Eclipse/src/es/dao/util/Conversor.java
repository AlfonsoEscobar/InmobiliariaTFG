package es.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import es.modelos.Anuncio;
import es.modelos.Favorito;
import es.modelos.Fotografia;
import es.modelos.Inmueble;
import es.modelos.Usuario;

public class Conversor {

	public static Usuario convertirUsuario(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		
		usuario.setCorreo(rs.getString("correo"));
		usuario.setId_usuario(rs.getInt("id_usuario"));
		usuario.setNombre(rs.getString("nombre"));
		usuario.setTelefono1(rs.getString("telefono1"));
		usuario.setTelefono2(rs.getString("telefono2"));
		usuario.setImagen_perfil(rs.getBytes("imagen_perfil"));
		
		return usuario;
	}
	
	public static Inmueble convertirInmueble(ResultSet rs) throws SQLException {
		Inmueble inmu = new Inmueble();
		
		inmu.setProvincia(rs.getString("provincia"));
		inmu.setLocalidad(rs.getString("localidad"));
		inmu.setCalle(rs.getString("calle"));
		inmu.setNumero(rs.getInt("numero"));
		inmu.setEscalera(rs.getString("escalera"));
		inmu.setPiso(rs.getInt("piso"));
		inmu.setPuerta(rs.getString("puerta"));
		inmu.setId_inmueble(rs.getInt("id_inmueble"));
		inmu.setPropietario(rs.getInt("propietario"));
		inmu.setDescripcion(rs.getString("descripcion"));
		inmu.setMetros2(rs.getDouble("metros2"));
		inmu.setNum_habitaciones(rs.getInt("num_habitaciones"));
		inmu.setNum_banos(rs.getInt("num_banos"));
		inmu.setTipo_edificacion(rs.getString("tipo_edificacion"));
		inmu.setTipo_obra(rs.getString("tipo_obra"));
		inmu.setEquipamiento(rs.getString("equipamiento"));
		inmu.setExteriores(rs.getString("exteriores"));
		inmu.setGaraje(rs.getBoolean("garaje"));
		inmu.setTrastero(rs.getBoolean("trastero"));
		inmu.setAscensor(rs.getBoolean("ascensor"));
		inmu.setUltima_planta(rs.getBoolean("ultima_planta"));
		inmu.setMascotas(rs.getBoolean("mascotas"));

		return inmu;
	}
	
	public static Anuncio convertirAnuncio(ResultSet rs) throws SQLException {
		java.util.Date fecha_anuncio = new java.util.Date();
		java.util.Date fecha_ultima_actualizacion = new java.util.Date();
		
		SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
		plantilla.format(fecha_anuncio);
		plantilla.format(fecha_ultima_actualizacion);
		
		int id_inmueble = rs.getInt("id_inmueble");
		String tipo_anuncio = rs.getString("tipo_anuncio");
		double precio = rs.getDouble("precio");
		fecha_anuncio = (java.util.Date) rs.getDate("fecha_anuncio");
		fecha_ultima_actualizacion = (java.util.Date) rs.getDate("fecha_ultima_actualizacion");

		Anuncio anuncio = new Anuncio(id_inmueble, tipo_anuncio, precio, fecha_anuncio, fecha_ultima_actualizacion);

		return anuncio;
	}
	
	public static Favorito convertirFavorito(ResultSet rs) throws SQLException {
		Favorito favorito = new Favorito();
		
		favorito.setUsuario_favorito(rs.getInt("usuario_favorito"));
		favorito.setInmueble_favorito(rs.getInt("inmueble_favorito"));
		favorito.setTipo_anuncio(rs.getString("tipo_anuncio"));
		
		return favorito;
		
	}
	
	public static Fotografia convertirFotografia(ResultSet rs) throws SQLException {
		Fotografia foto = new Fotografia();
		
		foto.setRuta(rs.getString("ruta"));
		foto.setTipo_habitacion(rs.getString("tipo_habitacion"));
		foto.setInmueble(rs.getInt("inmueble"));
		
		return foto;
	}
	
	public static InfoAnuncio convertirInfoAnuncio(ResultSet rs) throws SQLException {
		Inmueble inmu = new Inmueble();
		InfoAnuncio infoan = new InfoAnuncio();

		inmu.setProvincia(rs.getString("provincia"));
		inmu.setLocalidad(rs.getString("localidad"));
		inmu.setCalle(rs.getString("calle"));
		inmu.setNumero(rs.getInt("numero"));
		inmu.setEscalera(rs.getString("escalera"));
		inmu.setPiso(rs.getInt("piso"));
		inmu.setPuerta(rs.getString("puerta"));
		inmu.setId_inmueble(rs.getInt("id_inmueble"));
		inmu.setPropietario(rs.getInt("propietario"));
		inmu.setDescripcion(rs.getString("descripcion"));
		inmu.setMetros2(rs.getDouble("metros2"));
		inmu.setNum_habitaciones(rs.getInt("num_habitaciones"));
		inmu.setNum_banos(rs.getInt("num_banos"));
		inmu.setTipo_edificacion(rs.getString("tipo_edificacion"));
		inmu.setTipo_obra(rs.getString("tipo_obra"));
		inmu.setEquipamiento(rs.getString("equipamiento"));
		inmu.setExteriores(rs.getString("exteriores"));
		inmu.setGaraje(rs.getBoolean("garaje"));
		inmu.setTrastero(rs.getBoolean("trastero"));
		inmu.setAscensor(rs.getBoolean("ascensor"));
		inmu.setUltima_planta(rs.getBoolean("ultima_planta"));
		inmu.setMascotas(rs.getBoolean("mascotas"));

		infoan.setId_inmueble(rs.getInt("id_inmueble"));
		infoan.setTipo_anuncio("tipo_anuncio");
		infoan.setPrecio(rs.getDouble("precio"));
		infoan.setFecha_anunciado(rs.getDate("fecha_anunciado"));
		infoan.setFecha_ultima_actualizacion(rs.getDate("fecha_ultima_actualizacion"));

		infoan.setInmueble(inmu);
		
		return infoan;
		
	}
	
}
