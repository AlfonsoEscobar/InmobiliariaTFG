package com.tfg.inmobiliariatfg.modelos;

import java.util.LinkedList;
import java.util.List;

public class ValoresPredeterminadosInmueble {

    private List<String> listaTipoHabitacion;
    private List<String> listaEscalera;
    private List<String> listaTipoEdificacion;
    private List<String> listaExteriores;
    private List<String> listaTipoObra;

    public ValoresPredeterminadosInmueble(LinkedList<String> listaTipoHabitacion, LinkedList<String> listaEscalera,
                                          LinkedList<String> listaTipoEdificacion, LinkedList<String> listaExteriores, LinkedList<String> listaTipoObra) {
        this.listaTipoHabitacion = listaTipoHabitacion;
        this.listaEscalera = listaEscalera;
        this.listaTipoEdificacion = listaTipoEdificacion;
        this.listaExteriores = listaExteriores;
        this.listaTipoObra = listaTipoObra;
    }
    public ValoresPredeterminadosInmueble(){

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

}
