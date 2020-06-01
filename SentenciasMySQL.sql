
drop database inmobiliaria;

CREATE database inmobiliaria;

use inmobiliaria;

CREATE TABLE `tipo_habitacion`(
  `id_habitacion` int AUTO_INCREMENT,
  `tipo` varchar(20),
  CONSTRAINT `pk_tipo_habitacion` PRIMARY KEY (`id_habitacion`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `escalera`(
  `id_escalera` int AUTO_INCREMENT,
  `tipo` varchar(20),
  CONSTRAINT `pk_escalera` PRIMARY KEY (`id_escalera`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tipo_edificacion`(
  `id_edificacion` int AUTO_INCREMENT,
  `tipo` varchar(20),
  CONSTRAINT `pk_tipo_edificacion` PRIMARY KEY (`id_edificacion`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `exteriores`(
  `id_exterior` int AUTO_INCREMENT,
  `tipo` varchar(20),
  CONSTRAINT `pk_exteriores` PRIMARY KEY (`id_exterior`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tipo_obra`(
  `id_obra` int AUTO_INCREMENT,
  `tipo` varchar(20),
  CONSTRAINT `pk_tipo_obra` PRIMARY KEY (`id_obra`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `usuario` (
  `correo` varchar(40),
  `contrasena` varchar(64) NOT NULL,
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `telefono1` varchar(9) NOT NULL,
  `telefono2` varchar(9),
  `imagen_perfil` mediumblob,
  CONSTRAINT `pk_usuario` PRIMARY KEY (`correo`),
  CONSTRAINT `un_usuario_id_usuario` UNIQUE KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `inmueble` (
  `provincia` varchar(25),
  `localidad` varchar(25),
  `calle` varchar(50),
  `numero` int,
  `piso` int,
  `puerta` varchar(4),
  `escalera` varchar(10),
  `id_inmueble` int NOT NULL AUTO_INCREMENT,
  `propietario` int NOT NULL,
  `descripcion` text NOT NULL,
  `metros2` decimal(5,2) NOT NULL,
  `num_habitaciones` int NOT NULL,
  `num_banos` int NOT NULL,
  `tipo_edificacion` varchar(20),
  `tipo_obra` varchar(30),
  `equipamiento` varchar(100),
  `exteriores` varchar(30),
  `garaje` tinyint(1) DEFAULT '0',
  `trastero` tinyint(1) DEFAULT '0',
  `ascensor` tinyint(1) DEFAULT '0',
  `ultima_planta` tinyint(1) DEFAULT '0',
  `mascotas` tinyint(1) DEFAULT '0',
  CONSTRAINT `pk_inmueble` PRIMARY KEY (`provincia`,`localidad`,`calle`,`numero`,`piso`,`puerta`,`escalera`),
  CONSTRAINT `un_inmueble_id_inmueble` UNIQUE KEY (`id_inmueble`),
  CONSTRAINT `fk_inmueble_usuario` FOREIGN KEY (`propietario`) REFERENCES `usuario` (`id_usuario`)
  on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `anuncio` (
  `id_inmueble` int NOT NULL,
  `tipo_anuncio` varchar(20) NOT NULL,
  `precio` decimal(8,2) NOT NULL,
  `fecha_anunciado` date,
  `fecha_ultima_actualizacion` date,
  CONSTRAINT `pk_anuncio` PRIMARY KEY (`id_inmueble`,`tipo_anuncio`),
  CONSTRAINT `fk_anuncio_inmueble` FOREIGN KEY (`id_inmueble`) REFERENCES `inmueble` (`id_inmueble`)
  on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `favorito` (
  `usuario_favorito` int NOT NULL,
  `inmueble_favorito` int NOT NULL,
  `tipo_anuncio` varchar(20) NOT NULL,
  CONSTRAINT `pk_favorito` PRIMARY KEY (`usuario_favorito`,`inmueble_favorito`,`tipo_anuncio`),
  CONSTRAINT `fk_favorito_usuario` FOREIGN KEY (`usuario_favorito`) REFERENCES `usuario` (`id_usuario`)
  on delete cascade on update cascade,
  CONSTRAINT `fk_favorito_anuncio` FOREIGN KEY (`inmueble_favorito`, `tipo_anuncio`) REFERENCES `anuncio` (`id_inmueble`, `tipo_anuncio`)
  on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `fotografia` (
  `ruta` varchar(100) NOT NULL,
  `tipo_habitacion` varchar(20),
  `inmueble` int NOT NULL,
  CONSTRAINT `pk_fotografia` PRIMARY KEY (`ruta`),
  CONSTRAINT `fk_fotografia_inmueble` FOREIGN KEY (`inmueble`) REFERENCES `inmueble` (`id_inmueble`)
  on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into usuario values("alfonso@gmail.es", "a115168db2baaf8afae6387b2857b9096dd6ef159e449d8193bfbbe5e4fae550", 0, "Alfonso Escobar", "111111111", "222222222", null);
insert into usuario values("jorge@gmail.es", "jorge123", 0, "Jorge Sierra", "333333333", "444444444", null);
insert into usuario values("javier@gmail.es", "javier123", 0, "Javier Mencia", "555555555", null, null);


insert into inmueble values("Madrid", "Leganes", "indias", 9, 2, "A", "unica", 0, 1, "Este piso mola", 70, 3, 1, "piso", "segunda mano", "aire acondicionado", "terraza", 0, 0, 1, 0, 1);
insert into inmueble values("Madrid", "Fuenlabrada", "Falsa", 2, 4, "C", "unica", 0, 2, "Este piso es la leche", 95, 2, 1, "piso", "segunda mano", "aire acondicionado", "terraza", 1, 1, 1, 0, 1);
insert into inmueble values("Madrid", "Villaverde", "inventada", 15, 1, "B", "drch", 0, 3, "Este piso no esta mal", 85, 2, 1, "piso", "nuevo", "aire acondicionado", "terraza", 1, 1, 1, 0, 1);


insert into anuncio values(1, "alquiler", 500, null, null);
insert into anuncio values(2, "alquiler", 750, null, null);
insert into anuncio values(3, "alquiler", 650, null, null);
insert into anuncio values(3, "venta", 650, null, null);

insert into favorito values(1, 3, "venta");
insert into favorito values(1, 3, "alquiler");
insert into favorito values(2, 1, "alquiler");

insert into tipo_edificacion values(0, "Piso");
insert into tipo_edificacion values(0, "Chalet");
insert into tipo_edificacion values(0, "Ático");
insert into tipo_edificacion values(0, "Dúplex");
insert into tipo_edificacion values(0, "Buhardilla");
insert into tipo_edificacion values(0, "Apartamento");

insert into exteriores values(0, "Terraza");
insert into exteriores values(0, "Patio");
insert into exteriores values(0, "Piscina");
insert into exteriores values(0, "Jardín");
insert into exteriores values(0, "Ninguno");

insert into tipo_obra values(0, "Obra nueva");
insert into tipo_obra values(0, "Segunda mano");
insert into tipo_obra values(0, "Cocina amueblada");
insert into tipo_obra values(0, "Por amueblar");

insert into escalera values(0, "Derecha");
insert into escalera values(0, "Izquierda");
insert into escalera values(0, "Centro");
insert into escalera values(0, "Unica");

insert into tipo_habitacion values(0, "Salón");
insert into tipo_habitacion values(0, "Cocina");
insert into tipo_habitacion values(0, "Baño");
insert into tipo_habitacion values(0, "Dormitorio");
insert into tipo_habitacion values(0, "Escalera");
insert into tipo_habitacion values(0, "Recibidor");
insert into tipo_habitacion values(0, "Pasillo");
insert into tipo_habitacion values(0, "Garaje");
insert into tipo_habitacion values(0, "Trastero");
insert into tipo_habitacion values(0, "Terraza");
insert into tipo_habitacion values(0, "Exteriores");

