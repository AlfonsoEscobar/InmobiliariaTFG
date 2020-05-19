package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

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
import es.dao.util.CriterioBusqueda2Builder;
import es.dao.util.InfoAnuncio;
import es.dao.util.ValoresBusqueda;
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
	@Path("/criteriosBusqueda")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnuncioCriterios(@Context UriInfo uriInfo, ValoresBusqueda valores) {

		claseAnuncio = new MySQLAnuncioDAO(dataSource);
		Response.Status respuesta = Response.Status.OK;
		List<InfoAnuncio> listaAnuncio = null;

		CriterioBusqueda2 criterio = new CriterioBusqueda2Builder()
						.conLocalidad(valores.getLocalidad())
						.conTipo_Anuncio(valores.getTipo_anuncio())
						.conAscensor(valores.isAscensor())
						.conCalle(valores.getCalle())
						.conEquipamiento(valores.getEquipamiento())
						.conExteriores(valores.getExteriores())
						.conGaraje(valores.isGaraje())
						.conMascotas(valores.isMascotas())
						.conMetros2(valores.getMin_metros2(), valores.getMax_metros2())
						.conMin_num_habitaciones(valores.getMax_num_habitaciones())
						.conNum_banos(valores.getNum_banos())
						.conNum_habitaciones(valores.getNum_habitaciones())
						.conPiso(valores.getPiso())
						.conPrecio(valores.getMin_precio(), valores.getMax_precio())
						.conTipo_edificacion(valores.getTipo_edificacion())
						.conTipo_obra(valores.getTipo_obra())
						.conTrastero(valores.isTrastero())
						.conUltima_planta(valores.isUltima_planta())
						.conFecha_anuncio((Date) valores.getFecha_anunciado())
						.conFecha_ultima_actualizacion((Date) valores.getFecha_ultima_actualizacion())
						.build();

		String sentenciaSQL = criterio.obtenerCriterioSQL();

		try {

			listaAnuncio = claseAnuncio.listaInfoAnunciosCriterio(sentenciaSQL);

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
