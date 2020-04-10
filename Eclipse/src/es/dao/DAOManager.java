package es.dao;

public interface DAOManager {
	
	AnuncioDAO getAnuncioDAO();
	
	FavoritoDAO getFavoritoDAO();
	
	FotografiaDAO getFotografiaDAO();
	
	InmuebleDAO getInmuebleDAO();
	
	UsuarioDAO getUsuarioDAO();
}
