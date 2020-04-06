package es.restful;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	
	
}
