package es.dao.util;


public class CriterioBusqueda2 {

	private String tipo_anuncio;
	private String localidad;
	private String sentencia;
	
	public CriterioBusqueda2(CriterioBusqueda2Builder builder) {
		this.sentencia = builder.getSentencia();
		this.localidad = builder.getLocalidad();
		this.tipo_anuncio = builder.getTipo_anuncio();
	}
	
	public String obtenerCriterioSQL() {
		String busqueda = "SELECT a.*, i.*, u.* FROM anuncio a inner join inmueble i " + 
				"on a.id_inmueble = i.id_inmueble inner join usuario u on i.propietario = u.id_usuario " +
				"WHERE a.tipo_anuncio = '" + this.tipo_anuncio + 
				"' and i.localidad = '" + this.localidad + "'";
		return busqueda + this.sentencia;
	}
	
}
