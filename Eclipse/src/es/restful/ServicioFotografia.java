package es.restful;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import es.dao.DAOException;
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
		
		List<Fotografia> listaFotografia = new LinkedList<>();
		
		try {

			listaFotografia = claseFotografia.listaFotografiasDeInmueble(id_inmueble);
			
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
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postFotografia(@Context UriInfo uriInfo, Fotografia foto) {
		
		claseFotografia = new MySQLFotografiaDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		int idGenerados = -1;
		
		foto.setRuta("/Imagenes/Pisos/" + foto.getInmueble() + "/" + foto.getRuta() + ".jpg");
		
		try {
			
			idGenerados = claseFotografia.insertar(foto);
			
			if (idGenerados <= 0) {
				respuesta = Response.Status.NOT_FOUND;
			}
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (respuesta == Response.Status.OK) {
			UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
			URI uri = uriBuilder.path(Integer.toString(idGenerados)).build();
			return Response.created(uri).build();
		}else {
			return Response.status(respuesta).build();
		}
	}
	
	
	@GET
	@Path("/perfil/{nombreFichero}")
	@Produces("images/jpeg")
	public Response getFichero(@PathParam("nombreFichero") String nombreFichero) {
		File fichero = new File("/home/alfonso/Imágenes/" + nombreFichero);
		return Response.ok(fichero).build();
	}


	@PUT
	@Path("/perfil/{nombreFoto}")
	@Consumes("images/jpeg")
	public Response putFichero(File fichero,
							   @PathParam("nombreFoto") String nombreFoto) {

		Response.Status responseStatus = Response.Status.OK;

		fichero.renameTo(new File("/home/alfonso/Imágenes/App/Pisos/" + nombreFoto));

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
