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

import es.modelos.Fotografia;

@ApplicationScoped
@Path("/fotografia")
public class ServicioFotografia {
	
	
	List<Fotografia> listaFotografia = new ArrayList<>();
	
	@Inject
	private DataSource dataSource;
	
	//MySQLFotografiaDAO claseFotografia = new MySQLAnuncioDAO(dataSource);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFotografia(@QueryParam("tipo") String tipo){
		
		Response.Status responsestatus = Response.Status.OK;
		
		//List<Fotografia> listaFotografia = MySQLFotografiaDAO.obternerTodos(tipo);
		
		
		
		if(listaFotografia.isEmpty()) {
			responsestatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if (responsestatus == Response.Status.OK)
			return Response.ok(listaFotografia).build();
		else
			return Response.status(responsestatus).build();
		
	}
	
	
	@PUT
	@Path("/{id_fotografia}")
	@Consumes(APPLICATION_JSON)
	public Response putFotografia(@PathParam("id_fotografia") String id, Fotografia foto) {
		
		Response.Status responseStatus = Response.Status.OK;
		int affectedRows = 0;
		
		/*
		try {
		responseStatus = claseFotografia.modificar(id, foto);
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		*/
		
		if(affectedRows == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}

		return Response.status(responseStatus).build();
	}
	
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response postFotografia(@Context UriInfo uriInfo, Fotografia foto) {
		
		Response.Status responseStatus = Response.Status.OK;
		int generatedId = -1;
		int affectedRows = 0;
		
/*		try {
			affectedRows = claseFotografia.insertar(foto);
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}*/
			
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
	@Path("/{id_fotografia}")
	public Response deleteFotografia(@PathParam("id_fotografia") int id) {

		Response.Status responseStatus = Response.Status.OK;
		int affectedRows = 0;
		
/*		try {
		//affectedRows = claseFotografia.eliminar(id);
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}*/
		
		if (affectedRows == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}
		
		return Response.status(responseStatus).build();
	}
	
	
	
}