package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import es.modelos.Inmueble;
import es.modelos.Usuario;


@ApplicationScoped
@Path("/property")
public class ServicesProperty {
	
	List<Inmueble> listapisos = new ArrayList<>();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPisos(@QueryParam("tipo") String tipo,
							 @QueryParam("localidad") String localidad){
		
		Response.Status responsestatus = Response.Status.OK;
		
		//List<Inmueble> listapisos = MySqlInmueble.select(tipo, localidad);
		
		if(listapisos.isEmpty()) {
			responsestatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if (responsestatus == Response.Status.OK)
			return Response.ok(listapisos).build();
		else
			return Response.status(responsestatus).build();
		
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPisos(@QueryParam("tipo") String tipo,
							 @QueryParam("localidad") String localidad,
							 @QueryParam("piso") int piso,
							 @QueryParam("metros2") float metros2,
							 @QueryParam("num_hab") int num_hab,
							 @QueryParam("num_banos") int num_banos,
							 @QueryParam("tipo_edificacion") String tipo_edificacion,
							 @QueryParam("tipo_obra") String tipo_obra,
							 @QueryParam("equipamiento") String equipamiento,
							 @QueryParam("exteriores") String exteriores,
							 @QueryParam("garaje") boolean garaje,
							 @QueryParam("trastero") boolean trastero,
							 @QueryParam("ascensor") boolean ascensor,
							 @QueryParam("ultima_planta") boolean ultima_planta,
							 @QueryParam("mascotas") boolean mascotas){
		
		Response.Status responsestatus = Response.Status.OK;
		
		
		if(listapisos.isEmpty()) {
			responsestatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if (responsestatus == Response.Status.OK)
			return Response.ok(listapisos).build();
		else
			return Response.status(responsestatus).build();
		
	}
	
	
	@PUT
	@Path("/{id_inmueble}")
	@Consumes(APPLICATION_JSON)
	public Response putUsuario(@PathParam("id_inmueble") String id_inmueble, Usuario usuario) {
		
		Response.Status responseStatus = Response.Status.OK;
		int filas = 0;
		
		//responseStatus = MySQLInmuebleDao.modificar(correo, usuario);
		
		if(filas == 0) {
			
		}

		return Response.status(responseStatus).build();
	}
	
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response postUsuario(@Context UriInfo uriInfo, Inmueble inmueble) {
		
		Response.Status responseStatus = Response.Status.OK;
		//int generatedId = -1;
		int affectedRows = 0;
		
		//affectedRows = MySQLInmuebleDao.insertar(inmueble);
		
		if (affectedRows == 0){
			responseStatus = Response.Status.NOT_FOUND;
		}

		if (responseStatus != Response.Status.OK) {
			return Response.status(responseStatus).build();
		} 
			
		return null;
	}
	

	@DELETE
	@Path("/{id_inmueble}")
	public Response deleteUsuario(@PathParam("id_inmueble") String id_inmueble) {

		Response.Status responseStatus = Response.Status.OK;

		int affectedRows = 0;
		
		//affectedRows = MySQLInmueble.borrar(id_inmueble);
		
		
		if (affectedRows == 0) {
			responseStatus = Response.Status.NOT_FOUND;
		}
		
		return Response.status(responseStatus).build();
	}
	
	
}
