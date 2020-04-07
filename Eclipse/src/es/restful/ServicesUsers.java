package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
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

import es.modelos.Usuario;

@ApplicationScoped
@Path("/user")
public class ServicesUsers {
	
	List<Usuario> listaUsuario = new ArrayList<>();
	
	@GET
	@Path("/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("correo") String correo) {
		
		Response.Status responsestatus = Response.Status.OK;
		
		//El correo que recibo por el Path se lo paso a la clase de javi
		// Usuario user = la clase java de javi que devuelve un usuario
		
		
		// en vez de listaUsuario seria el usuario que devuelve javi
		if(listaUsuario.isEmpty()) {
			responsestatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (responsestatus == Response.Status.OK)
			return Response.ok(listaUsuario).build();
		else
			return Response.status(responsestatus).build();
		
	}
	
	
	@PUT
	@Path("/{correo}")
	@Consumes(APPLICATION_JSON)
	public Response putUsuario(@PathParam("correo") String correo, Usuario usuario) {
		
		Response.Status responseStatus = Response.Status.OK;

		//responseStatus = UsuarioDao.modificar(correo, usuario);

		return Response.status(responseStatus).build();
	}
	
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response postUsuario(@Context UriInfo uriInfo, Usuario usuario) {
		
		Response.Status responseStatus = Response.Status.OK;
		int generatedId = -1;
		//int affectedRows = 0;
		
		//affectedRows = UsuarioDao.insertar(usuario);
		
/*
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0){
				responseStatus = Response.Status.NOT_FOUND;
			}else {
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next())
					generatedId = generatedKeys.getInt(1);
			}

*/
		
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
		
		if (affectedRows == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}
		
		return Response.status(responseStatus).build();
	}
	
	
}
