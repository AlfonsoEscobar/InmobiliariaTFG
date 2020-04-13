package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.ArrayList;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import es.dao.DAOException;
import es.dao.mysql.MySQLInmuebleDAO;
import es.modelos.Inmueble;
import es.modelos.Usuario;


@ApplicationScoped
@Path("/inmueble")
public class ServicioInmueble {
	
	@Inject
	private DataSource dataSource;
	
	MySQLInmuebleDAO claseInmueble;
	
	@GET
	@Path("/{localidad}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInmueble(@PathParam("localidad") String localidad){
		
		List<Inmueble> listapisos = new ArrayList<>();
		Response.Status responsestatus = Response.Status.OK;
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		try {
			listapisos = claseInmueble.obtenerPorParametro(localidad);
		} catch (DAOException e) {
			responsestatus = Response.Status.NOT_FOUND;
		}
		
		if(listapisos.isEmpty()) {
			responsestatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if (responsestatus == Response.Status.OK)
			return Response.ok(listapisos).build();
		else
			return Response.status(responsestatus).build();
		
	}
	
	
	@PUT
	@Path("/{id_inmueble}")
	@Consumes(APPLICATION_JSON)
	public Response putInmueble(@PathParam("id_inmueble") int id, Inmueble inmueble) {
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status responseStatus = Response.Status.OK;
		int affectedRows = 0;
		
		try {
			
			affectedRows = claseInmueble.modificar(id, inmueble);
			
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if(affectedRows == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}

		return Response.status(responseStatus).build();
	}
	
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response postInmueble(@Context UriInfo uriInfo, Inmueble inmueble) {
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status responseStatus = Response.Status.OK;
		int generatedId = -1;
		int affectedRows = 0;
		
		try {
			affectedRows = claseInmueble.insertar(inmueble);
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
			
		if (affectedRows == 0){
			responseStatus = Response.Status.NOT_FOUND;
		}
		
		if (responseStatus == Response.Status.OK) {
			UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
			URI uri = uriBuilder.path(Integer.toString(generatedId)).build();
			return Response.created(uri).build();
		} else
			return Response.status(responseStatus).build();
	}
	

	@DELETE
	@Path("/{id_inmueble}")
	public Response deleteInmueble(@PathParam("id_inmueble") int id) {

		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status responseStatus = Response.Status.OK;

		int affectedRows = 0;
		
		try {
			affectedRows = claseInmueble.eliminar(id);
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if (affectedRows == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}
		
		return Response.status(responseStatus).build();
	}
	
	
}
