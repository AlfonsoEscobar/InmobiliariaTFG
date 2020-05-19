package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import es.dao.mysql.MySQLAnuncioDAO;
import es.dao.util.CriterioBusqueda2;
import es.dao.util.InfoAnuncio;
import es.modelos.Anuncio;

@ApplicationScoped
@Path("/anuncio")
public class ServicioAnuncio {

	@Inject
	private DataSource dataSource;

	MySQLAnuncioDAO claseAnuncio;

	@GET
	@Path("/{id_propietario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfoAnuncioPorPropietario(@PathParam("id_propietario") int id) {

		claseAnuncio = new MySQLAnuncioDAO(dataSource);
		Response.Status respuesta = Response.Status.OK;
		List<InfoAnuncio> listaAnuncio = new LinkedList<>();

		try {

			listaAnuncio = claseAnuncio.listaInfoAnuncios(id);

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		
			if (listaAnuncio.isEmpty()) {
				respuesta = Response.Status.NOT_FOUND;
			}
		

		if (respuesta == Response.Status.OK)
			return Response.ok(listaAnuncio).build();
		else
			return Response.status(respuesta).build();
	}
	
	@GET
	@Path("/{tipo_anuncio}/{localidad}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBusquedaAnuncio(@PathParam("tipo_anuncio") String tipo,
								@PathParam("localidad") String localidad) {

		claseAnuncio = new MySQLAnuncioDAO(dataSource);
		Response.Status respuesta = Response.Status.OK;
		LinkedList<InfoAnuncio> listaAnuncio = new LinkedList<>();

		try {

			listaAnuncio = claseAnuncio.listaInfoAnuncios(tipo, localidad);

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		
			if (listaAnuncio.isEmpty()) {
				respuesta = Response.Status.NOT_FOUND;
			}
		

		if (respuesta == Response.Status.OK)
			return Response.ok(listaAnuncio).build();
		else
			return Response.status(respuesta).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnuncioCriterios(@Context UriInfo uriInfo) {

		claseAnuncio = new MySQLAnuncioDAO(dataSource);
		//CriterioBusqueda2 criterio = new CriterioBusqueda2();
		CriterioBusqueda2Builder builder = new CriterioBusqueda2Builder();
		Response.Status respuesta = Response.Status.OK;
		List<InfoAnuncio> listaAnuncio = null;

		/*for (Map.Entry entry : uriInfo.getQueryParameters().entrySet()) {

			switch (entry.getKey().toString()) {
			case "localidad":
				criterio.setLocalidad(entry.getValue().toString());
				break;
			case "calle":
				criterio.setCalle(entry.getValue().toString());
				break;
			case "piso":
				criterio.setPiso(Integer.parseInt(entry.getValue().toString()));
				break;
			case "min_metros2":
				criterio.setMin_metros2(Double.parseDouble(entry.getValue().toString()));
				break;
			case "max_metros2":
				criterio.setMax_metros2(Double.parseDouble(entry.getValue().toString()));
				break;
			case "num_habitaciones":
				criterio.setNum_habitaciones(Integer.parseInt(entry.getValue().toString()));
				break;
			case "max_num_habitaciones":
				criterio.setMax_num_habitaciones(Integer.parseInt(entry.getValue().toString()));
				break;
			case "num_banos":
				criterio.setNum_banos(Integer.parseInt(entry.getValue().toString()));
				break;
			case "max_num_banos":
				criterio.setMax_num_banos(Integer.parseInt(entry.getValue().toString()));
				break;
			case "tipo_edificacion":
				criterio.setTipo_anuncio(entry.getValue().toString());
				break;
			case "tipo_obra":
				criterio.setTipo_obra(entry.getValue().toString());
				break;
			case "equipamiento":
				criterio.setEquipamiento(entry.getValue().toString());
				break;
			case "exteriores":
				criterio.setExteriores(entry.getValue().toString());
				break;
			case "garaje":
				criterio.setGaraje(Boolean.parseBoolean(entry.getValue().toString()));
				break;
			case "trastero":
				criterio.setTrastero(Boolean.parseBoolean(entry.getValue().toString()));
				break;
			case "ascensor":
				criterio.setAscensor(Boolean.parseBoolean(entry.getValue().toString()));
				break;
			case "ultima_planta":
				criterio.setUltima_planta(Boolean.parseBoolean(entry.getValue().toString()));
				break;
			case "mascotas":
				criterio.setMascotas(Boolean.parseBoolean(entry.getValue().toString()));
				break;
			case "tipo_anuncio":
				criterio.setTipo_anuncio(entry.getValue().toString());
				break;
			case "min_precio":
				criterio.setMin_precio(Double.parseDouble(entry.getValue().toString()));
				break;
			case "max_precio":
				criterio.setMax_precio(Double.parseDouble(entry.getValue().toString()));
				break;
			case "fecha_anunciado":

				break;
			case "fecha_ultima_actualizacion":

				break;
			}
		}*/

		try {

			listaAnuncio = claseAnuncio.listaInfoAnunciosCriterio(criterio);

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (listaAnuncio != null) {
			if (listaAnuncio.isEmpty()) {
				respuesta = Response.Status.NOT_FOUND;
			}
		}

		if (respuesta == Response.Status.OK)
			return Response.ok(listaAnuncio).build();
		else
			return Response.status(respuesta).build();
	}

	@PUT
	@Path("/{id_anuncio}")
	@Consumes(APPLICATION_JSON)
	public Response putAnuncio(@PathParam("id_anuncio") int id_inmueble, Anuncio anuncio) {

		claseAnuncio = new MySQLAnuncioDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;

		try {

			filasModificadas = claseAnuncio.modificar(id_inmueble, anuncio.getTipo_anuncio(), anuncio.getPrecio());

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuesta).build();
	}

	@POST
	@Consumes(APPLICATION_JSON)
	public Response postAnuncio(@Context UriInfo uriInfo, Anuncio anuncio) {

		claseAnuncio = new MySQLAnuncioDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;
		int idGenerados = -1;

		try {

			idGenerados = claseAnuncio.insertar(anuncio);
			
			if (idGenerados <= 0) {
				respuesta = Response.Status.NOT_FOUND;
			}

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (respuesta == Response.Status.OK) {
			UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
			URI uri = uriBuilder.path(Integer.toString(idGenerados)).build();
			return Response.created(uri).build();
		}else {
			return Response.status(respuesta).build();
		}
	}

	@DELETE
	@Path("/{id_inmueble}/{tipo_anuncio}")
	public Response deleteAnuncio(@PathParam("id_inmueble") int id, @PathParam("tipo_anuncio") String tipo) {

		claseAnuncio = new MySQLAnuncioDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;

		try {

			filasModificadas = claseAnuncio.eliminar(id, tipo);

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}
		return Response.status(respuesta).build();
	}

}
