
CREATE database inmoviliaria;

use inmoviliaria;

CREATE TABLE USUARIO(
	correo varchar(40) PRIMARY KEY,
	contrasena varchar(64) NOT NULL,
	id_usuario int(7) NOT NULL,
	nombre varchar(50) NOT NULL,
	telefono1 varchar(9) NOT NULL,
	telefono2 varchar(9),
	foto_perfil MEDIUMBLOB
) ENGINE=INNODB;

create index 'fk_a_usuario' on USUARIO(id_usuario);

CREATE TABLE INMUEBLE(
	provincia varchar(25),
	localidad varchar(25),
	calle varchar(50),
	numero int(2),
	piso int(2),
	puerta varchar(4),
	id_inmueble int(7),
	propietario int(7) not null,
	descripcion text NOT NULL,
	metros2 dec(5,2) NOT NULL, 
	num_habitaciones int(2) NOT NULL,
	num_banos int(2) NOT NULL,
	tipo_edificacion varchar(20),
	tipo_obra varchar(30),
	equipamiento varchar(100),
	exteriores varchar(30) default false,
	garaje bool default false,
	trastero bool default false,
	ascensor bool default false,
	ultima_planta bool default false,
	mascotas bool default false,
	CONSTRAINT 'pk_inmueble' PRIMARY KEY (provincia, localidad, calle, numero, piso, puerta),
	CONSTRAINT 'fk_inmueble_usuario' FOREIGN KEY (propietario) REFERENCES USUARIO(id_usuario)
) ENGINE=INNODB ;

create index 'fk_a_inmueble' on INMUEBLE(id_inmueble);

CREATE TABLE ANUNCIO(
	id_inmueble int(7),
	tipo_anuncio bool,
	precio dec(8,2) NOT NULL,
	fecha_anunciado date,
	fecha_ultima_actualizacion date,
	CONSTRAINT 'pk_anuncio' PRIMARY KEY (id_inmueble, tipo_anuncio),
	CONSTRAINT 'fk_anuncio_inmueble' FOREIGN KEY (id_inmueble) REFERENCES INMUEBLE(id_inmueble)
) ENGINE=INNODB;

create index 'fk_a_anuncio' on ANUNCIO(id_inmueble, tipo_anuncio);

CREATE TABLE FAVORITO(
	usuario_favorito int(7),
	inmueble_favorito int(7),
	tipo_anuncio bool,
	CONSTRAINT 'pk_favorito' PRIMARY KEY (usuario_favorito, inmueble_favorito, tipo_anuncio),
	CONSTRAINT 'fk_favorito_usuario' FOREIGN KEY (usuario_favorito) REFERENCES USUARIO(id_usuario),
	CONSTRAINT 'fk_favorito_anuncio' FOREIGN KEY (inmueble_favorito, tipo_anuncio) REFERENCES ANUNCIO(id_inmueble, tipo_anuncio)
) ENGINE=INNODB;

create index 'fk_a_favorito' on FAVORITO(usuario_favorito, inmueble_favorito, tipo_anuncio);

CREATE TABLE FOTOGRAFIA(
	ruta varchar(25),
	tipo_habitacion varchar2(20),
	inmueble varchar(8) NOT NULL,
	CONSTRAINT 'pk_fotografia' PRIMARY KEY (ruta),
	CONSTRAINT 'fk_fotografia_inmueble' FOREIGN KEY (inmueble) REFERENCES INMUEBLE(id_inmueble)
) ENGINE=INNODB;
