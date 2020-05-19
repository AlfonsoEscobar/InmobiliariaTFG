package es.dao.util;

public class prueba {

	public static void main (String[] args) {
		CriterioBusqueda2 criterio = new CriterioBusqueda2Builder()
				.conLocalidad("Leganés")
				.conMascotas(true)
				.conTipo_Anuncio("alquiler")
				.conMetros2(50, 90).build();
		String sentenciaSQL = criterio.obtenerCriterioSQL();
		
	}
}
