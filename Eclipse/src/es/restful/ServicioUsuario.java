package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	private MySQLUsuarioDAO claseUsuario;
	
	@GET
	@Path("/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("correo") String correo) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);
		
		Response.Status responsestatus = Response.Status.OK;
		Usuario usuario = new Usuario();

		try {
			usuario = claseUsuario.obtener(correo);
			
		} catch (DAOException e) {
			responsestatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (responsestatus == Response.Status.OK) {
			return Response.ok(usuario).build();
		}else {
			return Response.status(responsestatus).build();
		}

	}


	@PUT
	@Path("/{id_usuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putUsuario(@PathParam("id_usuario") int id, Usuario usuario) {
		
		//claseUsuario = new MySQLUsuarioDAO(dataSource);
		
		Response.Status responseStatus = Response.Status.OK;
		int affectedRows = 0;

		try {
			affectedRows = claseUsuario.modificar(id, usuario);
			
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if(affectedRows == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}

		return Response.status(responseStatus).build();
		
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postUsuario(@Context UriInfo uriInfo, Usuario usuario) {
		
		claseUsuario = new MySQLUsuarioDAO(dataSource);
		
		Response.Status responseStatus = Response.Status.OK;

		int affectedRows = 0;
		
		try {
			
			affectedRows = claseUsuario.insertar(usuario);
			
			
			if (affectedRows == 0){
				responseStatus = Response.Status.NOT_FOUND;
			}
			
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		return Response.status(responseStatus).build();
	}
	
	
	@DELETE
	@Path("/{correo}")
	public Response deleteUsuario(@PathParam("correo") String correo) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);
		Response.Status responseStatus = Response.Status.OK;

		int affectedRows = 0;

		try {
			affectedRows = claseUsuario.eliminar(correo);
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (affectedRows == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}
		
		return Response.status(responseStatus).build();
	}
	
}
