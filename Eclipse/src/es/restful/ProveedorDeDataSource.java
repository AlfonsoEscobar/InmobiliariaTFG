package es.restful;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

@ApplicationScoped
public class ProveedorDeDataSource {
	
	@Resource(name="jdbc/")
	@Produces
	private DataSource dataSource;
}
