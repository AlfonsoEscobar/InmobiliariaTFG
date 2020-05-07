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
	@Path("/{correo}/{contrasena}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioCC(@PathParam("correo") String correo, 
								 @PathParam("contrasena") String contrasena) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;
		Usuario usuario = null;

		try {

			if (claseUsuario.verificarUsuarioEnBase(correo, contrasena)) {

				usuario = claseUsuario.obtener(correo);

			}

			if (usuario == null)
				respuesta = Response.Status.NOT_FOUND;

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (respuesta == Response.Status.OK) {
			return Response.ok(usuario).build();
		} else {
			return Response.status(respuesta).build();
		}

	}

	@GET
	@Path("/{id_usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioPorId(@PathParam("id_usuario") int id_usuario) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;
		Usuario usuario = null;

		try {

			usuario = claseUsuario.obtenerPorId_usuario(id_usuario);

			if (usuario == null)
				respuesta = Response.Status.NOT_FOUND;

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (respuesta == Response.Status.OK) {
			return Response.ok(usuario).build();
		} else {
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

			if (filasModificadas == 0) {
				respuesta = Response.Status.NOT_FOUND;
			}

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		return Response.status(respuesta).build();

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postUsuario(@Context UriInfo uriInfo, Usuario usuario) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;
		Usuario u = new Usuario();

		try {

			if (!claseUsuario.usuarioRepetido(usuario.getCorreo())) {

				claseUsuario.insertar(usuario);
				
				u = claseUsuario.obtener(usuario.getCorreo());

			}

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (respuesta == Response.Status.OK)
			return Response.ok(u).build();
		else
			return Response.status(respuesta).build();
	}

	@DELETE
	@Path("/{id_usuario}")
	public Response deleteUsuario(@PathParam("correo") int id) {

		claseUsuario = new MySQLUsuarioDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;

		int filasModificadas = 0;

		try {

			filasModificadas = claseUsuario.eliminar(id);

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}

		return Response.status(respuesta).build();
	}

}
