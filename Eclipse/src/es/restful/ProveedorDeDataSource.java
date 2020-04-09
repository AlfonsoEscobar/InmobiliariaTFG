package es.restful;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

@ApplicationScoped
public class ProveedorDeDataSource {
	
	@Resource(name="jdbc/MySqlinmobiliaria")
	@Produces
	private DataSource dataSource;
}
