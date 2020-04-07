package es.dao;

import java.util.List;

public interface DAO<O, V> {
	int insertar(O object) throws DAOException;
	
	int modificar(O object) throws DAOException;
	
	int elminiar (O object) throws DAOException;
	
	List<O> obtenerPorParametro(V value) throws DAOException; 
	
	O obtener (V value) throws DAOException;
	
	List<O> obtenerTodos() throws DAOException;
}
