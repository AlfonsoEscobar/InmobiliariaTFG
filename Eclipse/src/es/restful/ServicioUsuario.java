package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;

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

import es.dao.DAOException;
import es.dao.mysql.MySQLUsuarioDAO;
import es.modelos.Usuario;


@ApplicationScoped
@Path("/usuario")
public class ServicioUsuario {

	@Inject
	private DataSource dataSource;

	MySQLUsuarioDAO claseUsuario = new MySQLUsuarioDAO(dataSource);

	@GET
	@Path("/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("correo") String correo) {

		Response.Status responsestatus = Response.Status.OK;
		Usuario usuario = null;

		try {
			usuario = claseUsuario.obtener(correo);
		} catch (DAOException e) {
			responsestatus = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (responsestatus == Response.Status.OK)
			return Response.ok(usuario).build();
		else
			return Response.status(responsestatus).build();

	}


	@PUT
	@Path("/{correo}")
	@Consumes(APPLICATION_JSON)
	public Response putUsuario(@PathParam("correo") String correo, Usuario usuario) {

		Response.Status responseStatus = Response.Status.OK;
		int affectedRows = 0;

		try {
			affectedRows = claseUsuario.modificar(correo, usuario);
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
	public Response postUsuario(@Context UriInfo uriInfo, Usuario usuario) {
		
		Response.Status responseStatus = Response.Status.OK;
		int generatedId = -1;
		int affectedRows = 0;
		
		try {
			affectedRows = claseUsuario.insertar(usuario);
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
	@Path("/{correo}")
	public Response deleteUsuario(@PathParam("correo") String correo) {

		Response.Status responseStatus = Response.Status.OK;

		int affectedRows = 0;

		try {
			affectedRows = claseUsuario.elminiar(correo);
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (affectedRows == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}
		
		return Response.status(responseStatus).build();
	}
	
}
