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
import es.dao.mysql.MySQLFavoritoDAO;
import es.modelos.Anuncio;
import es.modelos.Favorito;

@ApplicationScoped
@Path("/favorito")
public class ServicioFavorito {
	

	
	List<Favorito> listaFavorito = new ArrayList<>();
	
	@Inject
	private DataSource dataSource;
	
	MySQLFavoritoDAO claseFavorito;
	
	@GET
	@Path("/{id_usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFavorito(@PathParam("id_usuario") int id){
		
		claseFavorito = new MySQLFavoritoDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		
		try {
			
			List<Favorito> listaFavorito = claseFavorito.obtenerPorParametro(id);
			
		} catch (DAOException e) {
			respuesta = Response.Status.NOT_FOUND;
		}
		
		
		
		if(listaFavorito.isEmpty()) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if (respuesta == Response.Status.OK)
			return Response.ok(listaFavorito).build();
		else
			return Response.status(respuesta).build();
		
	}
	
	
	@PUT
	@Path("/{id_favorito}")
	@Consumes(APPLICATION_JSON)
	public Response putFavorito(@PathParam("id_favorito") int id, Favorito favorito) {
		
		claseFavorito = new MySQLFavoritoDAO(dataSource);
		
		Response.Status responseStatus = Response.Status.OK;
		int filasModificadas = 0;
		
		
		try {
			filasModificadas = claseFavorito.modificar(id, favorito);
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if(filasModificadas == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}

		return Response.status(responseStatus).build();
		
	}
	
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response postFavorito(@Context UriInfo uriInfo, Favorito favorito) {
		
		Response.Status responseStatus = Response.Status.OK;
		int generatedId = -1;
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseFavorito.insertar(favorito);
			
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
			
		if (filasModificadas == 0){
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
	@Path("/{id_favorito}")
	public Response deleteFavorito(@PathParam("id_favorito") int id) {

		Response.Status responseStatus = Response.Status.OK;
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseFavorito.eliminar(id);
			
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (filasModificadas == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}
		
		return Response.status(responseStatus).build();
		
	}
	
	
	
}