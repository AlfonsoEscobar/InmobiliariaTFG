package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

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
import es.modelos.Anuncio;

@ApplicationScoped
@Path("/anuncio")
public class ServicioAnuncio {
	
	@Inject
	private DataSource dataSource;
	
	MySQLAnuncioDAO claseAnuncio;
	
	
	@GET
	@Path("/{tipo}/{localidad}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnuncio(@PathParam("tipo") String tipo,
							   @PathParam("localidad") String localidad){
		
		claseAnuncio = new MySQLAnuncioDAO(dataSource);
		Response.Status respuesta = Response.Status.OK;
		List<Anuncio> listaAnuncio = null;
		
		try {
			
			listaAnuncio = claseAnuncio.obtenerPorParametro(localidad, tipo);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if(listaAnuncio != null) {
			if(listaAnuncio.isEmpty()) {
				respuesta = Response.Status.NOT_FOUND;
			}
		}
		
		if (respuesta == Response.Status.OK)
			return Response.ok(listaAnuncio).build();
		else
			return Response.status(respuesta).build();
		
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnuncioT(){
		
		claseAnuncio = new MySQLAnuncioDAO(dataSource);
		Response.Status respuesta = Response.Status.OK;
		List<Anuncio> listaAnuncio = null;
		
		try {
			
			listaAnuncio = claseAnuncio.obtenerTodos();
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if(listaAnuncio != null) {
			if(listaAnuncio.isEmpty()) {
				respuesta = Response.Status.NOT_FOUND;
			}
		}
		
		if (respuesta == Response.Status.OK)
			return Response.ok(listaAnuncio).build();
		else
			return Response.status(respuesta).build();
		
	}
	
	
	@PUT
	@Path("/{id_anuncio}")
	@Consumes(APPLICATION_JSON)
	public Response putAnuncio(@PathParam("id_anuncio") String id_inmueble, Anuncio anuncio) {
		
		claseAnuncio = new MySQLAnuncioDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;
		
		
		try {
			
			filasModificadas = claseAnuncio.modificar(id_inmueble, anuncio);
			
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
	public Response postAnuncio(@Context UriInfo uriInfo, Anuncio anuncio) {
		
		claseAnuncio = new MySQLAnuncioDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseAnuncio.insertar(anuncio);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
			
		if (filasModificadas == 0){
			respuesta = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuesta).build();
		
	}
	

	@DELETE
	@Path("/{id_inmueble}/{tipo_anuncio}")
	public Response deleteAnuncio(@PathParam("id_inmueble") int id,
								  @PathParam("tipo_anuncio") String tipo) {

		claseAnuncio = new MySQLAnuncioDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseAnuncio.eliminar(id, tipo);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuesta).build();
		
	}
	
	
}
