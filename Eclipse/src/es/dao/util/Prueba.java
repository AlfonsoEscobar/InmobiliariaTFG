package es.dao.util;

public class Prueba {
	public static void main (String args []) {
		CriterioBusqueda2 criterio = new CriterioBusqueda2Builder()
				.conLocalidad("Leganes")
				.conTipo_Anuncio("Alquilar")
				.conNum_banos(2)
				//.conCalle(null)
				.build();
		System.out.println(criterio.obtenerCriterioSQL());
	}
}
