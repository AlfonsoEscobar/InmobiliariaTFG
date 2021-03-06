package es.restful;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
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

import es.dao.mysql.DAOException;
import es.dao.mysql.MySQLAnuncioDAO;
import es.dao.mysql.MySQLFotografiaDAO;
import es.dao.mysql.MySQLInmuebleDAO;
import es.modelos.Fotografia;
import es.modelos.Inmueble;

@ApplicationScoped
@Path("/inmueble")
public class ServicioInmueble {
	
	@Inject
	private DataSource dataSource;
	
	MySQLInmuebleDAO claseInmueble;
	
	@GET
	@Path("/{propietario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInmueble(@PathParam("propietario") int propietario){
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		List<Inmueble> listaInmueble = new LinkedList<Inmueble>();
		
		
		try {
			
			listaInmueble = claseInmueble.obtenerPorParametro(propietario);


		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (respuesta == Response.Status.OK) {
			return Response.ok(listaInmueble).build();
		}else {
			return Response.status(respuesta).build();
		}
	}
	
	
	@PUT
	@Path("/{id_inmueble}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putInmueble(@PathParam("id_inmueble") int id, Inmueble inmueble) {
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		MySQLAnuncioDAO claseAnuncio = new MySQLAnuncioDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseInmueble.modificar(id, inmueble);
			claseAnuncio.actualizarFecha(id);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if(filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}

		return Response.status(respuesta).build();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postInmueble(@Context UriInfo uriInfo, Inmueble inmueble) {
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		
		int idGenerados = -1;
		
		try {
			
			idGenerados = claseInmueble.insertar(inmueble);
			
			if (idGenerados <= 0){
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
	

	@DELETE
	@Path("/{id_inmueble}")
	public Response deleteInmueble(@PathParam("id_inmueble") int id) {

		claseInmueble = new MySQLInmuebleDAO(dataSource);
		MySQLFotografiaDAO claseFotografia = new MySQLFotografiaDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		List<Fotografia> listaFotografia = null;

		int filasModificadas = 0;
		
		try {
			
			listaFotografia = claseFotografia.listaFotografiasDeInmueble(id);
			if(listaFotografia != null) {
				for (Fotografia f : listaFotografia) {
					claseFotografia.borrarFotografia(String.valueOf(id), f.getRuta());
				}
			}
			filasModificadas = claseInmueble.eliminar(id);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if (filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuesta).build();
	}
	
	
}
