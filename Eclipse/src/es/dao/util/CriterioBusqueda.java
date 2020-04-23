package es.dao.util;

public class CriterioBusqueda {
	private String clausula;
	/*private double metro2_min;
	private double metros2_max;
	private int num_habitaciones;
	private int num_banos;
	private TipoEdificacion tipo_edificacion;
	private TipoObra tipo_obra;
	private Equipamiento equipamiento;
	private Exteriores exteriores;
	private boolean garaje;
	private boolean trastero;
	private boolean ascensor;
	private boolean ultima_planta;
	private boolean mascotas;*/

	public CriterioBusqueda(TiposBooleanos tipo, boolean valor) {
		if(tipo.name().equals(tipo.GARAJE)) {
			this.clausula = "garaje = " + valor;
		}else if(tipo.name().equals(tipo.TRASTERO)) {
			this.clausula = "trastero = " + valor;
		}else if(tipo.name().equals(tipo.ASCENSOR)) {
			this.clausula = "ascensor = " + valor;
		}else if(tipo.name().equals(tipo.MASCOTAS)){
			this.clausula = "mascotas = " + valor;
		}else {
			this.clausula = "ultima_planta = " + valor;
		}
	}

	public CriterioBusqueda(Equipamiento equipamiento) {
		this.clausula = "equipamiento = " + equipamiento.name();
	}

	public CriterioBusqueda(TipoEdificacion tipo_edificacion) {
		this.clausula = "tipo_edificacion = " + tipo_edificacion.name();
	}

	public CriterioBusqueda(TipoObra tipo_obra) {
		this.clausula = "tipo_obra = " + tipo_obra.name();
	}

	public CriterioBusqueda(Exteriores exteriores) {
		this.clausula = "exteriores = " + exteriores.name();
	}

	public CriterioBusqueda(double minmetros, double maxmetros) {
		double mayor, menor;
		if (maxmetros > minmetros) {
			mayor = maxmetros;
			menor = minmetros;
		} else {
			mayor = minmetros;
			menor = maxmetros;
		}
		this.clausula = "metros2 > " + menor + " and metros2 < " + mayor;//between
	}
	
	public CriterioBusqueda (TipoEnteros tipo, int valor) {
		if(tipo.name().equals(tipo.HABITACIONES)) {
			if(valor < 4) {
				this.clausula = "num_habitaciones = " + valor;
			}else
				this.clausula = "num_habitaciones > 3" + valor;
		}else if(tipo.name().equals(tipo.BANOS)) {
			if(valor < 3) {
				this.clausula = "num_banos = " + valor;
			}else
				this.clausula = "num_banos > 3" + valor;
		}
	}

	public String getClausula() {
		return this.clausula;
	}

	public String construirSentencia() {
		String sentencia = "Select * From inmueble Where " + this.clausula;
		return sentencia;
	}

	public void anadirFiltros(CriterioBusqueda criterio) {
		this.clausula = this.clausula + " and " + criterio.getClausula();
	}

}
