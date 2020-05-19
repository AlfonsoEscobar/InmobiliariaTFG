package es.restful;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.dao.DAOException;
import es.dao.mysql.MySQLTablasAuxiliaresDAO;
import es.dao.util.ValoresPredeterminadosInmueble;

@ApplicationScoped
@Path("/valores")
public class ServicioValoresTablas {
	
	@Inject
	private DataSource dataSource;

	private MySQLTablasAuxiliaresDAO claseTablas;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValores() {

		claseTablas = new MySQLTablasAuxiliaresDAO(dataSource);

		Response.Status respuesta = Response.Status.OK;
		ValoresPredeterminadosInmueble vpi = new ValoresPredeterminadosInmueble();

		try {

			vpi.setListaEscalera(claseTablas.obtenerValoresEscalera());
			vpi.setListaExteriores(claseTablas.obtenerValoresExteriores());
			vpi.setListaTipoEdificacion(claseTablas.obtenerValoresTipoEdificacion());
			vpi.setListaTipoHabitacion(claseTablas.obtenerValoresTipoHabitacion());
			vpi.setListaTipoObra(claseTablas.obtenerValoresTipoObra());

		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}

		if (respuesta == Response.Status.OK) {
			return Response.ok(vpi).build();
		} else {
			return Response.status(respuesta).build();
		}

	}

}
