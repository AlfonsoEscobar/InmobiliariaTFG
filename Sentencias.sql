
CREATE database inmobiliaria;

use inmobiliaria;

CREATE TABLE USUARIO(
	correo varchar(40) PRIMARY KEY,
	contrasena varchar(64) NOT NULL,
	id_usuario int(7) NOT NULL,
	nombre varchar(50) NOT NULL,
	telefono1 varchar(9) NOT NULL,
	telefono2 varchar(9),
	imagen_perfil MEDIUMBLOB
) ENGINE=INNODB;

ALTER TABLE `inmobiliaria`.`usuario` 
add index `fk_a_usuario`  (id_usuario asc) visible;
;

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
	exteriores varchar(30),
	garaje bool default false,
	trastero bool default false,
	ascensor bool default false,
	ultima_planta bool default false,
	mascotas bool default false,
	PRIMARY KEY (provincia, localidad, calle, numero, piso, puerta)
) ENGINE=INNODB ;

create index 'fk_a_inmueble' on INMUEBLE(id_inmueble);

ALTER TABLE `inmobiliaria`.`inmueble` 
add index `fk_a_inmueble`  (id_inmueble asc) visible;
;

CREATE TABLE ANUNCIO(
	id_inmueble int(7),
	tipo_anuncio varchar(20),
	precio dec(8,2) NOT NULL,
	fecha_anunciado date,
	fecha_ultima_actualizacion date,
	PRIMARY KEY (id_inmueble, tipo_anuncio)
) ENGINE=INNODB;

ALTER TABLE `inmobiliaria`.`anuncio` 
add index `fk_a_anuncio`  (usuario_favorito, inmueble_favorito, tipo_anuncio asc) visible;
;

CREATE TABLE FAVORITO(
	usuario_favorito int(7),
	inmueble_favorito int(7),
	tipo_anuncio varchar(20),
	PRIMARY KEY (usuario_favorito, inmueble_favorito, tipo_anuncio)
) ENGINE=INNODB;

ALTER TABLE `inmobiliaria`.`favorito` 
add index `fk_a_favorito`  (usuario_favorito, inmueble_favorito, tipo_anuncio asc) visible;
;

CREATE TABLE FOTOGRAFIA(
	ruta varchar(25),
	tipo_habitacion varchar(20),
	inmueble int(7) NOT NULL,
	PRIMARY KEY (ruta),
) ENGINE=INNODB;


Alter table `inmobiliaria`.`inmueble`
add constraint `fk_inmueble`
  foreign key (`propietario`)
  references `inmobiliaria`.`usuario` (`id_usuario`)
  on delete no action
  on delete no action;


ALTER TABLE `inmobiliaria`.`anuncio` 
ADD CONSTRAINT `fk_anuncio`
  FOREIGN KEY (`id_inmueble`)
  REFERENCES `inmobiliaria`.`inmueble` (`id_inmueble`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `inmobiliaria`.`fotografia` 
ADD CONSTRAINT `fk_fotografia`
  FOREIGN KEY (`inmueble`)
  REFERENCES `inmobiliaria`.`inmueble` (`id_inmueble`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;









