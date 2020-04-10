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

		if(usuario.getCorreo().equals(null)) {
			responsestatus = Response.Status.NOT_FOUND;
		}
		
		if (responsestatus == Response.Status.OK) {
			return Response.ok(usuario).build();
		}else {
			return Response.status(responsestatus).build();
		}

	}


	@PUT
	@Path("/{correo}")
	@Consumes(APPLICATION_JSON)
	public Response putUsuario(@PathParam("correo") String correo, Usuario usuario) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);
		
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
		
		//String sqlQuery = "INSERT INTO usuario (correo, contrasena, id_usuario, nombre, telefono1,"
		//		+ "telefono2, imagen_perfil) VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		claseUsuario = new MySQLUsuarioDAO(dataSource);
		
		Response.Status responseStatus = Response.Status.OK;
		int generatedId = -1;
		int affectedRows = 0;
		
		try {
			affectedRows = claseUsuario.insertar(usuario);
			
			/*Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, usuario.getCorreo());
			statement.setString(2, usuario.getContrasena());
			statement.setInt(3, usuario.getId_usuario());
			statement.setString(4, usuario.getNombre());
			statement.setString(5, usuario.getTelefono1());
			statement.setString(6, usuario.getTelefono2());
			statement.setString(7, null);
			
			affectedRows = statement.executeUpdate();*/
			
			if (affectedRows == 0){
				responseStatus = Response.Status.NOT_FOUND;
			}else {
				/*ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next())
					generatedId = generatedKeys.getInt(1);*/
			}
			
		} catch (DAOException e) {
			responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
			
		
		
		if (responseStatus != Response.Status.OK) {
			/*UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
			URI uri = uriBuilder.path(Integer.toString(generatedId)).build();
			return Response.created(uri).build();*/
		
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
