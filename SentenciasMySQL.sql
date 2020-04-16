
CREATE database inmobiliaria;

use inmobiliaria;

CREATE TABLE `usuario` (
  `correo` varchar(40) NOT NULL,
  `contrasena` varchar(64) NOT NULL,
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `telefono1` varchar(9) NOT NULL,
  `telefono2` varchar(9) DEFAULT NULL,
  `imagen_perfil` mediumblob,
  PRIMARY KEY (`correo`),
  KEY `fk_a_idUsuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `inmueble` (
  `provincia` varchar(25) NOT NULL,
  `localidad` varchar(25) NOT NULL,
  `calle` varchar(50) NOT NULL,
  `numero` int NOT NULL,
  `piso` int NOT NULL,
  `puerta` varchar(4) NOT NULL,
  `escalera` varchar(10) NOT NULL,
  `id_inmueble` int NOT NULL AUTO_INCREMENT,
  `propietario` int NOT NULL,
  `descripcion` text NOT NULL,
  `metros2` decimal(5,2) NOT NULL,
  `num_habitaciones` int NOT NULL,
  `num_banos` int NOT NULL,
  `tipo_edificacion` varchar(20) DEFAULT NULL,
  `tipo_obra` varchar(30) DEFAULT NULL,
  `equipamiento` varchar(100) DEFAULT NULL,
  `exteriores` varchar(30) DEFAULT NULL,
  `garaje` tinyint(1) DEFAULT '0',
  `trastero` tinyint(1) DEFAULT '0',
  `ascensor` tinyint(1) DEFAULT '0',
  `ultima_planta` tinyint(1) DEFAULT '0',
  `mascotas` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`provincia`,`localidad`,`calle`,`numero`,`piso`,`puerta`,`escalera`),
  UNIQUE KEY `id_inmueble_UNIQUE` (`id_inmueble`),
  KEY `fk_inmueble_idx` (`propietario`),
  KEY `fk_a_inmueble` (`id_inmueble`),
  CONSTRAINT `fk_inmueble` FOREIGN KEY (`propietario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `anuncio` (
  `id_inmueble` int NOT NULL,
  `tipo_anuncio` varchar(20) NOT NULL,
  `precio` decimal(8,2) NOT NULL,
  `fecha_anunciado` date DEFAULT NULL,
  `fecha_ultima_actualizacion` date DEFAULT NULL,
  PRIMARY KEY (`id_inmueble`,`tipo_anuncio`),
  KEY `fk_a_anuncio` (`id_inmueble`,`tipo_anuncio`),
  CONSTRAINT `fk_anuncio` FOREIGN KEY (`id_inmueble`) REFERENCES `inmueble` (`id_inmueble`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `favorito` (
  `usuario_favorito` int NOT NULL,
  `inmueble_favorito` int NOT NULL,
  `tipo_anuncio` varchar(20) NOT NULL,
  PRIMARY KEY (`usuario_favorito`,`inmueble_favorito`,`tipo_anuncio`),
  KEY `fk_a_favorito` (`usuario_favorito`,`inmueble_favorito`,`tipo_anuncio`),
  KEY `fk_favorito_2_idx` (`inmueble_favorito`),
  KEY `fk_favorito_1_idx` (`inmueble_favorito`,`tipo_anuncio`),
  CONSTRAINT `fk_favorito` FOREIGN KEY (`usuario_favorito`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `fk_favorito_1` FOREIGN KEY (`inmueble_favorito`, `tipo_anuncio`) REFERENCES `anuncio` (`id_inmueble`, `tipo_anuncio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `fotografia` (
  `ruta` varchar(25) NOT NULL,
  `tipo_habitacion` varchar(20) DEFAULT NULL,
  `inmueble` int NOT NULL,
  PRIMARY KEY (`ruta`),
  KEY `fk_fotografia_idx` (`inmueble`),
  CONSTRAINT `fk_fotografia` FOREIGN KEY (`inmueble`) REFERENCES `inmueble` (`id_inmueble`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into usuario values("alfonso@gmail.es", "alfonso123", 0, "Alfonso Escobar", "111111111", "222222222", null);
insert into usuario values("jorge@gmail.es", "jorge123", 0, "Jorge Sierra", "333333333", "444444444", null);
insert into usuario values("javier@gmail.es", "javier123", 0, "Javier Mencia", "555555555", null, null);


insert into inmueble values("Madrid", "Leganes", "indias", 9, 2, "A", "unica", 0, 1, "Este piso mola", 70, 3, 1, "piso", "segunda mano", "aire acondicionado", "terraza", 0, 0, 1, 0, 1);
insert into inmueble values("Madrid", "Fuenlabrada", "Falsa", 2, 4, "C", "unica", 0, 2, "Este piso es la leche", 95, 2, 1, "piso", "segunda mano", "aire acondicionado", "terraza", 1, 1, 1, 0, 1);
insert into inmueble values("Madrid", "Villaverde", "inventada", 15, 1, "B", "drch", 0, 3, "Este piso no esta mal", 85, 2, 1, "piso", "nuevo", "aire acondicionado", "terraza", 1, 1, 1, 0, 1);


insert into anuncio values(1, "alquiler", 500, null, null);
insert into anuncio values(2, "alquiler", 750, null, null);
insert into anuncio values(3, "alquiler", 650, null, null);











