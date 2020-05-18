package es.dao.util;

import java.util.LinkedList;
import java.util.List;


public class ValoresPredeterminadosInmueble {

	private List<String> listaTipoHabitacion;
	private List<String> listaEscalera;
	private List<String> listaTipoEdificacion;
	private List<String> listaExteriores;
	private List<String> listaTipoObra;

	public ValoresPredeterminadosInmueble() {
		
	}
	
	public ValoresPredeterminadosInmueble(LinkedList<String> listaTipoHabitacion, LinkedList<String> listaEscalera,
			LinkedList<String> listaTipoEdificacion, LinkedList<String> listaExteriores, LinkedList<String> listaTipoObra) {
		this.listaTipoHabitacion = listaTipoHabitacion;
		this.listaEscalera = listaEscalera;
		this.listaTipoEdificacion = listaTipoEdificacion;
		this.listaExteriores = listaExteriores;
		this.listaTipoObra = listaTipoObra;
	}

	public List<String> getListaTipoHabitacion() {
		return listaTipoHabitacion;
	}

	public List<String> getListaEscalera() {
		return listaEscalera;
	}

	public List<String> getListaTipoEdificacion() {
		return listaTipoEdificacion;
	}

	public List<String> getListaExteriores() {
		return listaExteriores;
	}

	public List<String> getListaTipoObra() {
		return listaTipoObra;
	}

	public void setListaTipoHabitacion(List<String> listaTipoHabitacion) {
		this.listaTipoHabitacion = listaTipoHabitacion;
	}

	public void setListaEscalera(List<String> listaEscalera) {
		this.listaEscalera = listaEscalera;
	}

	public void setListaTipoEdificacion(List<String> listaTipoEdificacion) {
		this.listaTipoEdificacion = listaTipoEdificacion;
	}

	public void setListaExteriores(List<String> listaExteriores) {
		this.listaExteriores = listaExteriores;
	}

	public void setListaTipoObra(List<String> listaTipoObra) {
		this.listaTipoObra = listaTipoObra;
	}
	
	
	
 	
}
