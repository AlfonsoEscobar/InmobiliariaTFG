package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

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
import javax.ws.rs.core.UriInfo;

import es.dao.DAOException;
import es.dao.mysql.MySQLAnuncioDAO;
import es.dao.mysql.MySQLInmuebleDAO;
import es.modelos.Inmueble;


@ApplicationScoped
@Path("/inmueble")
public class ServicioInmueble {
	
	@Inject
	private DataSource dataSource;
	
	MySQLInmuebleDAO claseInmueble;
	
	@GET
	@Path("/{id_inmueble}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInmueble(@PathParam("id_inmueble") int id){
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		Inmueble inmueble = new Inmueble();
		
		
		try {
			
			inmueble = claseInmueble.obtener(id);


		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (respuesta == Response.Status.OK) {
			return Response.ok(inmueble).build();
		}else {
			return Response.status(respuesta).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListaInmueble(){
		
		List<Inmueble> lista = new LinkedList<>();
		
		Response.Status respuesta = Response.Status.OK;
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		try {
			
			lista = claseInmueble.obtenerTodos();
			
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (respuesta == Response.Status.OK) {
			return Response.ok(lista).build();
		}else {
			return Response.status(respuesta).build();
		}
		
	}
	
	
	@PUT
	@Path("/{id_inmueble}")
	@Consumes(APPLICATION_JSON)
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
	@Consumes(APPLICATION_JSON)
	public Response postInmueble(@Context UriInfo uriInfo, Inmueble inmueble) {
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseInmueble.insertar(inmueble);
			
			if (filasModificadas == 0){
				respuesta = Response.Status.NOT_FOUND;
			}
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		return Response.status(respuesta).build();
		
	}
	

	@DELETE
	@Path("/{id_inmueble}")
	public Response deleteInmueble(@PathParam("id_inmueble") int id) {

		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;

		int filasModificadas = 0;
		
		try {
			
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
