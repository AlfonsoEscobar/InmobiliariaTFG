package es.restful;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.dao.mysql.DAOException;
import es.dao.mysql.MySQLFotografiaDAO;
import es.modelos.Fotografia;

@ApplicationScoped
@Path("/fotografia")
public class ServicioFotografia {
	
	@Inject
	private DataSource dataSource;
	
	MySQLFotografiaDAO claseFotografia;
	
	
	@GET
	@Path("/unica/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnicaFotografia(@PathParam("tipo") String tipo){
		
		claseFotografia = new MySQLFotografiaDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		
		List<Fotografia> listaFotografia = new LinkedList<>();
		
		try {

			listaFotografia = claseFotografia.listaFotografiasPorTipoHabitacion(tipo);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if(listaFotografia.isEmpty()) {
			respuesta = Response.Status.NOT_FOUND;
		}
		
		if (respuesta == Response.Status.OK) {
			
			int random = (int)(Math.random() * listaFotografia.size()) + 1;
			
			return Response.ok(listaFotografia.get(random)).build();
			
		} else {
			return Response.status(respuesta).build();
		}
	}
	
	
	@GET
	@Path("/{id_inmueble}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFotografia(@PathParam("id_inmueble") int id_inmueble){
		
		claseFotografia = new MySQLFotografiaDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		
		List<String> listaFotografia = null;
		
		try {

			listaFotografia = claseFotografia.obtenerListaRutas(id_inmueble);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if(listaFotografia.isEmpty()) {
			respuesta = Response.Status.NOT_FOUND;
		}
		
		if (respuesta == Response.Status.OK) {
			return Response.ok(listaFotografia).build();
		}else{
			return Response.status(respuesta).build();
		}
	}
	
	
	@GET
	@Path("/inmueble/{id_piso}/{nombreFichero}")
	@Produces("images/jpeg")
	public Response getFicheroInmueble(@PathParam("nombreFichero") String nombreFichero,
							   @PathParam("id_piso") String id_piso) {
		
		File fichero = new File("/home/alfonso/Imágenes/App/Pisos/"+ id_piso + "/" + nombreFichero);
		
		return Response.ok(fichero).header("Content-Length", fichero.length()).build();
		
	}
	
	
	@PUT
	@Path("/inmueble/{id_inmueble}/{tipo_habitacion}")
	@Consumes("images/jpeg")
	public Response putFicheroInmueble(@PathParam("id_inmueble") int id_inmueble,
								@PathParam("tipo_habitacion") String tipo_habitacion,
								File fichero) {

		claseFotografia = new MySQLFotografiaDAO(dataSource);
		Response.Status responseStatus = Response.Status.OK;
		
		Fotografia foto = new Fotografia();
		
		String nombreFoto = String .valueOf(System.currentTimeMillis());
		
		File nombreCarpeta = new File("/home/alfonso/Imágenes/App/Pisos/" + String.valueOf(id_inmueble));
		
		if(!nombreCarpeta.exists()) {
			
			nombreCarpeta.mkdir();
			
			fichero.renameTo(new File("/home/alfonso/Imágenes/App/Pisos/" + String.valueOf(id_inmueble) + "/", nombreFoto));
			
			foto.setTipo_habitacion(tipo_habitacion);
			foto.setRuta("/home/alfonso/Imágenes/App/Pisos/" + String.valueOf(id_inmueble) + "/" + nombreFoto);
			foto.setInmueble(id_inmueble);
			
		} else {
			
			fichero.renameTo(new File("/home/alfonso/Imágenes/App/Pisos/" + String.valueOf(id_inmueble) + "/", nombreFoto));
			
			foto.setTipo_habitacion(tipo_habitacion);
			foto.setRuta("/home/alfonso/Imágenes/App/Pisos/" + String.valueOf(id_inmueble) + "/" + nombreFoto);
			foto.setInmueble(id_inmueble);
			
		}

		/*try {
			
			claseFotografia.insertar(foto);
			
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}*/

		return Response.status(responseStatus).build();

	}
	
	@GET
	@Path("/portada/{id_imagen}")
	@Produces("images/jpeg")
	public Response getFicheroPortada(@PathParam("id_imagen") int id_imagen) {
		
		File fichero = new File("/home/alfonso/Imágenes/App/Portada/"+ String.valueOf(id_imagen));
		return Response.ok(fichero).header("Content-Length", fichero.length()).build();
		
	}
	
	@GET
	@Path("/usuario/{id_usuario}")
	@Produces("images/jpeg")
	public Response getFicheroUsuario(@PathParam("id_usuario") int id_usuario) {
		
		File fichero = new File("/home/alfonso/Imágenes/App/Usuarios/"+ String.valueOf(id_usuario));
		return Response.ok(fichero).header("Content-Length", fichero.length()).build();
		
	}


	@PUT
	@Path("/usuario/{id_usuario}")
	@Consumes("images/jpeg")
	public Response putFicheroUsuario(@PathParam("id_usuario") int id_usuario,
									  @QueryParam("file") File fichero) {

		claseFotografia = new MySQLFotografiaDAO(dataSource);
		Response.Status responseStatus = Response.Status.OK;
		
		//fichero.renameTo(new File("/home/alfonso/Imágenes/App/Usuarios/" + String.valueOf(id_usuario)));
		
		File nombreFoto = new File("/home/alfonso/Imágenes/App/Usuarios/" + String.valueOf(id_usuario));
		
		if(nombreFoto.exists()) {
			if(nombreFoto.delete()) {
				fichero.renameTo(new File("/home/alfonso/Imágenes/App/Usuarios/" + String.valueOf(id_usuario)));
			}
		} else {
			fichero.renameTo(new File("/home/alfonso/Imágenes/App/Usuarios/" + String.valueOf(id_usuario)));
		}

		/*try {
			
			claseFotografia.insertar(foto);
			
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}*/

		return Response.status(responseStatus).build();

	}

	@DELETE
	@Path("/{id}")
	public Response deleteFotografia(@PathParam("id") int id) {

		claseFotografia = new MySQLFotografiaDAO(dataSource);
		
		Response.Status respuestas = Response.Status.OK;
		
		List<Fotografia> listaFotografia = new LinkedList<>();
		
		int filasModificadas = 0;
		
		try {
			
			listaFotografia = claseFotografia.listaFotografiasDeInmueble(id);
			
			for (Fotografia f : listaFotografia) {
				claseFotografia.borrarFotografia(f.getRuta());
			}
			
			filasModificadas = claseFotografia.eliminarFotografiasDeInmueble(id);
			
		} catch (DAOException e) {
			respuestas = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (filasModificadas == 0) {
			respuestas = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuestas).build();
	}
	
}
