package es.restful;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
	@Path("/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFotografia(@PathParam("tipo") String tipo){
		
		claseFotografia = new MySQLFotografiaDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		
		List<Fotografia> listaFotografia = new LinkedList<>();
		
		try {
			

			listaFotografia = claseFotografia.listaFotografiasPorTipoHabitacion(tipo);

			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if(listaFotografia.isEmpty()) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (respuesta == Response.Status.OK)
			return Response.ok(listaFotografia).build();
		else
			return Response.status(respuesta).build();
		
	}
	
	
	/*@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postFotografia(@Context UriInfo uriInfo, Fotografia foto) {
		
		claseFotografia = new MySQLFotografiaDAO(dataSource);
		
		Response.Status respuestas = Response.Status.OK;
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseFotografia.insertar(foto);
			
		} catch (DAOException e) {
			respuestas = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (filasModificadas == 0){
			respuestas = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuestas).build();
		
	}*/
	
	@POST
	public void postFotografia() {
		
	}
	
	// Borra todas las fotos guardadas de un piso y las rutas de las fotos de la base de datos
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
