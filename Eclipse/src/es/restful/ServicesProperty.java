package es.restful;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.modelos.Inmueble;



@ApplicationScoped
@Path("/property")
public class ServicesProperty {
	
	List<Inmueble> listapisos = new ArrayList<>();
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPisos(@QueryParam("tipo") String tipo,
							   @QueryParam("localidad") String localidad) {
		
		Response.Status responsestatus = Response.Status.OK;
		
		
		if(listapisos.isEmpty()) {
			responsestatus = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if (responsestatus == Response.Status.OK)
			return Response.ok(listapisos).build();
		else
			return Response.status(responsestatus).build();
		
	}
	
	
	
	
}
