package es.restful;

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
import es.dao.mysql.MySQLFavoritoDAO;
import es.modelos.Favorito;

@ApplicationScoped
@Path("/favorito")
public class ServicioFavorito {
	
	@Inject
	private DataSource dataSource;

	MySQLFavoritoDAO claseFavorito;
	
	@GET
	@Path("/{id_usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFavorito(@PathParam("id_usuario") int id){
		
		claseFavorito = new MySQLFavoritoDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		
		List<Favorito> listaFavorito = null;
		
		try {
			
			listaFavorito = claseFavorito.obtenerPorParametro(id);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if(listaFavorito != null) {
			if(listaFavorito.isEmpty()) {
				respuesta = Response.Status.NOT_FOUND;
			}
		}
		
		if (respuesta == Response.Status.OK)
			return Response.ok(listaFavorito).build();
		else
			return Response.status(respuesta).build();
		
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postFavorito(@Context UriInfo uriInfo, Favorito favorito) {
		
		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseFavorito.insertar(favorito);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
			
		if (filasModificadas == 0){
			respuesta = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuesta).build();
		
	}


	@DELETE
	@Path("/{id_favorito}")
	public Response deleteFavorito(@PathParam("id_favorito") int id) {

		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseFavorito.eliminar(id);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuesta).build();
		
	}
	
}
