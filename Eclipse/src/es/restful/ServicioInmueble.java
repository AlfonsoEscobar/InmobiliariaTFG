package es.restful;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.ws.rs.core.UriInfo;

import es.dao.DAOException;
import es.dao.mysql.MySQLInmuebleDAO;
import es.modelos.Inmueble;


@ApplicationScoped
@Path("/inmueble")
public class ServicioInmueble {
	
	@Inject
	private DataSource dataSource;
	
	MySQLInmuebleDAO claseInmueble;
	
	@GET
	@Path("/{id_inmueble}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInmueble(@PathParam("id_inmueble") int id){
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		Inmueble inmueble = new Inmueble();
		
		
		try {
			
			inmueble = claseInmueble.obtener(id);
			
			/*Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM inmueble "
											+ "where id_inmueble = ?;");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				
				inmueble.setCalle(resultSet.getString("calle"));
				inmueble.setLocalidad(resultSet.getString("localidad"));
				inmueble.setProvincia(resultSet.getString("provincia"));
				inmueble.setPiso(resultSet.getInt("piso"));
				inmueble.setId_inmueble(resultSet.getInt("id_inmueble"));
				inmueble.setMascotas(resultSet.getBoolean("mascotas"));
				inmueble.setMetros2(resultSet.getDouble("metros2"));
				
			}else {
				respuesta = Response.Status.NOT_FOUND;
			}*/


		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (respuesta == Response.Status.OK) {
			return Response.ok(inmueble).build();
		}else {
			return Response.status(respuesta).build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInmuebleU(){
		
		List<Inmueble> lista = new LinkedList<>();
		
		Response.Status respuesta = Response.Status.OK;
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		try {
			
			lista = claseInmueble.obtenerTodos();
			
			/*Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT * FROM inmueble;");
			
			while (resultSet.next()) {
				Inmueble inmueble = new Inmueble();
				inmueble.setCalle(resultSet.getString("calle"));
				inmueble.setLocalidad(resultSet.getString("localidad"));
				inmueble.setProvincia(resultSet.getString("provincia"));
				inmueble.setPiso(resultSet.getInt("piso"));
				inmueble.setId_inmueble(resultSet.getInt("id_inmueble"));
				inmueble.setMascotas(resultSet.getBoolean("mascotas"));
				inmueble.setMetros2(resultSet.getDouble("metros2"));
				lista.add(inmueble);
			}*/
			
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if (respuesta == Response.Status.OK) {
			return Response.ok(lista).build();
		}else {
			return Response.status(respuesta).build();
		}
		
	}
	
	
	@PUT
	@Path("/{id_inmueble}")
	@Consumes(APPLICATION_JSON)
	public Response putInmueble(@PathParam("id_inmueble") int id, Inmueble inmueble) {
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseInmueble.modificar(id, inmueble);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		if(filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}

		return Response.status(respuesta).build();
	}
	
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response postInmueble(@Context UriInfo uriInfo, Inmueble inmueble) {
		
		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		/*String INSERT = "INSERT INTO inmueble(provincia, localidad, calle, numero, piso, puerta, "
						+ "propietario, descripcion, metros2,"
						+ "num_habitaciones, num_banos, tipo_edificacion, tipo_obra, equipamiento, "
						+ "exteriores, garaje, trastero, ascensor, ultima_planta, mascotas) "
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		*/
		
		Response.Status respuesta = Response.Status.OK;
		
		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseInmueble.insertar(inmueble);
			
			/*Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT);
			statement.setString(1, inmueble.getProvincia());
			statement.setString(2, inmueble.getLocalidad());
			statement.setString(3, inmueble.getCalle());
			statement.setInt(4, inmueble.getNumero());
			statement.setInt(5, inmueble.getPiso());
			statement.setString(6, inmueble.getPuerta());
			statement.setInt(7, inmueble.getPropietario());
			statement.setString(8, inmueble.getDescripcion());
			statement.setDouble(9, inmueble.getMetros2());
			statement.setInt(10, inmueble.getNum_habitaciones());
			statement.setInt(11, inmueble.getNum_banos());
			statement.setString(12, inmueble.getTipo_edificacion());
			statement.setString(13, inmueble.getTipo_obra());
			statement.setString(14, inmueble.getEquipamiento());
			statement.setString(15, inmueble.getExteriores());
			statement.setBoolean(16, inmueble.isGaraje());
			statement.setBoolean(17, inmueble.isTrastero());
			statement.setBoolean(18, inmueble.isAscensor());
			statement.setBoolean(19, inmueble.isUltima_planta());
			statement.setBoolean(20, inmueble.isMascotas());
			
			filasModificadas = statement.executeUpdate();*/
			
			if (filasModificadas == 0){
				respuesta = Response.Status.NOT_FOUND;
			}
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		return Response.status(respuesta).build();
		
	}
	

	@DELETE
	@Path("/{id_inmueble}")
	public Response deleteInmueble(@PathParam("id_inmueble") int id) {

		claseInmueble = new MySQLInmuebleDAO(dataSource);
		
		Response.Status respuesta = Response.Status.OK;

		int filasModificadas = 0;
		
		try {
			
			filasModificadas = claseInmueble.eliminar(id);
			
		} catch (DAOException e) {
			respuesta = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		
		if (filasModificadas == 0) {
			respuesta = Response.Status.NOT_FOUND;
		}
		
		return Response.status(respuesta).build();
	}
	
	
}
