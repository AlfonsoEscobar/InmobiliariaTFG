package es.restful;

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
import es.dao.mysql.MySQLUsuarioDAO;
import es.modelos.Usuario;


@ApplicationScoped
@Path("/usuario")
public class ServicioUsuario {

	@Inject
	private DataSource dataSource;

	private MySQLUsuarioDAO claseUsuario;

	@GET
	@Path("/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("correo") String correo) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		Usuario usuario = new Usuario();

		try {
			
			usuario = claseUsuario.obtener(correo);
			
			if(usuario == null) {
				respuesta = Response.Status.NOT_FOUND;
			}
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (respuesta == Response.Status.OK) {
			return Response.ok(usuario).build();
		}else {
			return Response.status(respuesta).build();
		}

	}


	@PUT
	@Path("/{id_usuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putUsuario(@PathParam("id_usuario") int id, Usuario usuario) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;

		try {

			filasModificadas = claseUsuario.modificar(id, usuario);
			
			
			if(filasModificadas == 0) {
				respuesta = Response.Status.NOT_FOUND;
			}

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		return Response.status(respuesta).build();

	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postUsuario(@Context UriInfo uriInfo, Usuario usuario) {
		
		claseUsuario = new MySQLUsuarioDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;

		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseUsuario.insertar(usuario);
			
			
			if (filasModificadas == 0){
				respuesta = Response.Status.NOT_FOUND;
			}
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		return Response.status(respuesta).build();
	}
	
	
	@DELETE
	@Path("/{correo}")
	public Response deleteUsuario(@PathParam("correo") String correo) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;

		int filasModificadas = 0;

		try {
			
			filasModificadas = claseUsuario.eliminar(correo);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuesta).build();
	}
	
}
