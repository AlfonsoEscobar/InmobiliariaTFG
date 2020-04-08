/*Los atributos booleanos los utilizaremos para comprobar si existe o no ese servicio en el inmueble. True será sí, false no.*/
package es.modelos;

public class Inmueble {
	
	private String provincia;
	private String localidad;
	private String calle;
	private int numero;
	private int piso;
	private String puerta;
	private int id_inmueble;
	private int propietario;
	private String descripcion;
	private float metros2;
	private int num_habitaciones;
	private int num_banos;
	private String tipo_edificacion;
	private String tipo_obra;
	private String equipamiento;
	private String exteriores;
	private boolean garaje;
	private boolean trastero;
	private boolean ascensor;
	private boolean ultima_planta;
	private boolean mascotas;
	
	public Inmueble() {
	}

	public Inmueble(String provincia, String localidad, String calle, int numero, int piso, String puerta,
			int propietario, String descripcion, float metros2, int num_hab,
			int num_banos, String tipo_edificacion, String tipo_obra, String equipamiento, String exteriores,
			boolean garaje, boolean trastero, boolean ascensor, boolean ultima_planta, boolean mascotas) {
		this.provincia = provincia;
		this.localidad = localidad;
		this.calle = calle;
		this.numero = numero;
		this.piso = piso;
		this.puerta = puerta;
		this.id_inmueble = 0;//Le daremos valor al insertarlo en la base de datos
		this.propietario = propietario;
		this.descripcion = descripcion;
		this.metros2 = metros2;
		this.num_habitaciones = num_hab;
		this.num_banos = num_banos;
		this.tipo_edificacion = tipo_edificacion;
		this.tipo_obra = tipo_obra;
		this.equipamiento = equipamiento;
		this.exteriores = exteriores;
		this.garaje = garaje;
		this.trastero = trastero;
		this.ascensor = ascensor;
		this.ultima_planta = ultima_planta;
		this.mascotas = mascotas;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getPiso() {
		return piso;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public int getId_inmueble() {
		return id_inmueble;
	}

	public void setId_inmueble(int id_inmueble) {
		this.id_inmueble = id_inmueble;
	}

	public int getPropietario() {
		return propietario;
	}

	public void setPropietario(int propietario) {
		this.propietario = propietario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getMetros2() {
		return metros2;
	}

	public void setMetros2(float metros2) {
		this.metros2 = metros2;
	}

	public int getNum_habitaciones() {
		return num_habitaciones;
	}

	public void setNum_habitaciones(int num_hab) {
		this.num_habitaciones = num_hab;
	}

	public int getNum_banos() {
		return num_banos;
	}

	public void setNum_banos(int num_banos) {
		this.num_banos = num_banos;
	}

	public String getTipo_edificacion() {
		return tipo_edificacion;
	}

	public void setTipo_edificacion(String tipo_edificacion) {
		this.tipo_edificacion = tipo_edificacion;
	}

	public String getTipo_obra() {
		return tipo_obra;
	}

	public void setTipo_obra(String tipo_obra) {
		this.tipo_obra = tipo_obra;
	}

	public String getEquipamiento() {
		return equipamiento;
	}

	public void setEquipamiento(String equipamiento) {
		this.equipamiento = equipamiento;
	}

	public String getExteriores() {
		return exteriores;
	}

	public void setExteriores(String exteriores) {
		this.exteriores = exteriores;
	}

	public boolean isGaraje() {
		return garaje;
	}

	public void setGaraje(boolean garaje) {
		this.garaje = garaje;
	}

	public boolean isTrastero() {
		return trastero;
	}

	public void setTrastero(boolean trastero) {
		this.trastero = trastero;
	}

	public boolean isAscensor() {
		return ascensor;
	}

	public void setAscensor(boolean ascensor) {
		this.ascensor = ascensor;
	}

	public boolean isUltima_planta() {
		return ultima_planta;
	}

	public void setUltima_planta(boolean ultima_Planta) {
		this.ultima_planta = ultima_Planta;
	}

	public boolean isMascotas() {
		return mascotas;
	}

	public void setMascotas(boolean mascotas) {
		this.mascotas = mascotas;
	}
	
}
