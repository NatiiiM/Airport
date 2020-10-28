# Creacion Base De Datos

CREATE DATABASE vuelos;

USE vuelos;

#-------------------------------------------------------------------------
# Creación Tablas para las entidades

CREATE TABLE ubicaciones(
	pais VARCHAR(45) NOT NULL,
	estado VARCHAR(45) NOT NULL,
	ciudad VARCHAR(45) NOT NULL,
	huso SMALLINT NOT NULL,
	
	CONSTRAINT pk_ubicaciones_pec
	PRIMARY KEY (pais, estado, ciudad)
)ENGINE=InnoDB;

CREATE TABLE aeropuertos(
	codigo VARCHAR(6) NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	pais VARCHAR(45) NOT NULL,
	estado VARCHAR(45) NOT NULL,
	ciudad VARCHAR(45) NOT NULL,

	CONSTRAINT fk_aeropuertos_pac
	FOREIGN KEY (pais, estado, ciudad) REFERENCES ubicaciones (pais, estado, ciudad) 
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	CONSTRAINT pk_aeropuertos_codigo
	PRIMARY KEY (codigo)
)ENGINE=InnoDB;

CREATE TABLE vuelos_programados (
	numero VARCHAR(45),
	aeropuerto_salida VARCHAR(6) NOT NULL,
	aeropuerto_llegada VARCHAR(6) NOT NULL,
 
 	CONSTRAINT pk_vuelos_programados
 	PRIMARY KEY (numero),

 	CONSTRAINT fk_vuelos_programados_aeropuerto_salida
 	FOREIGN KEY (aeropuerto_salida) REFERENCES aeropuertos (codigo) 
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	CONSTRAINT fk_vuelos_programados_aeropuesto_llegada
   	FOREIGN KEY (aeropuerto_llegada) REFERENCES aeropuertos (codigo) 
   	ON DELETE CASCADE ON UPDATE CASCADE	
)ENGINE=InnoDB;

CREATE TABLE modelos_avion(
	modelo VARCHAR(45) NOT NULL,
	fabricante VARCHAR(45) NOT NULL,
	cabinas INT UNSIGNED NOT NULL,
	cant_asientos INT UNSIGNED NOT NULL,

	PRIMARY KEY (modelo)
)ENGINE=InnoDB;

CREATE TABLE salidas(
	vuelo VARCHAR(45),
	dia ENUM('Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'),
	hora_sale TIME NOT NULL,
	hora_llega TIME NOT NULL,
	modelo_avion VARCHAR(45) NOT NULL,

	CONSTRAINT fk_salidas_vuelo
	FOREIGN KEY (vuelo) REFERENCES vuelos_programados (numero) 
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	CONSTRAINT fk_salidas_modelo_avion
	FOREIGN KEY (modelo_avion) REFERENCES modelos_avion (modelo) 
   	ON DELETE CASCADE ON UPDATE CASCADE,

	PRIMARY KEY (vuelo, dia)
)ENGINE=InnoDB;

CREATE TABLE instancias_vuelo(
	vuelo VARCHAR(45),
	fecha DATE,
	dia ENUM('Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa') NOT NULL,
	estado VARCHAR(45),

	FOREIGN KEY (vuelo, dia) REFERENCES salidas (vuelo, dia) 
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	PRIMARY KEY (vuelo, fecha)	

)ENGINE=InnoDB;

CREATE TABLE clases(
	nombre VARCHAR(45),
	porcentaje DECIMAL(2,2) UNSIGNED NOT NULL,

	PRIMARY KEY(nombre)
)ENGINE=InnoDB;

CREATE TABLE comodidades(
	codigo INT UNSIGNED NOT NULL,
	descripcion TEXT NOT NULL,

	PRIMARY KEY(codigo)

)ENGINE=InnoDB;

CREATE TABLE empleados(
	legajo INT UNSIGNED,
	password VARCHAR(32) NOT NULL,
	doc_tipo VARCHAR(45) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL,
	apellido VARCHAR(45) NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,

	PRIMARY KEY (legajo)

)ENGINE=InnoDB;

CREATE TABLE pasajeros (
	doc_tipo VARCHAR(45) ,
	doc_nro INT UNSIGNED ,
	apellido VARCHAR(45) NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	nacionalidad VARCHAR(45) NOT NULL,

	PRIMARY KEY(doc_tipo, doc_nro)
)ENGINE=InnoDB;

CREATE TABLE reservas(
	numero INT UNSIGNED AUTO_INCREMENT,
	fecha DATE NOT NULL,
	vencimiento DATE NOT NULL,
	estado VARCHAR(45) NOT NULL,
	doc_tipo VARCHAR(45) NOT NULL ,
	doc_nro INT UNSIGNED NOT NULL,
	legajo INT UNSIGNED NOT NULL,

	FOREIGN KEY (legajo) REFERENCES empleados (legajo) 
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	FOREIGN KEY (doc_tipo, doc_nro) REFERENCES pasajeros (doc_tipo, doc_nro) 
   	ON DELETE CASCADE ON UPDATE CASCADE,
		

	PRIMARY KEY(numero)
)ENGINE=InnoDB;

CREATE TABLE asientos_reservados(
	vuelo VARCHAR(45) NOT NULL, 
	fecha DATE NOT NULL,
	clase VARCHAR(45) NOT NULL,
	cantidad SMALLINT UNSIGNED NOT NULL,
	
	CONSTRAINT pk_asientos_reservados
	FOREIGN KEY (vuelo, fecha) REFERENCES instancias_vuelo (vuelo, fecha)
   		ON DELETE CASCADE ON UPDATE CASCADE,

   	FOREIGN KEY (clase) REFERENCES clases (nombre)
   		ON DELETE CASCADE ON UPDATE CASCADE,

	PRIMARY KEY(vuelo,fecha,clase)

)ENGINE=InnoDB;

#-------------------------------------------------------------------------
# Creación Tablas para las relaciones

CREATE TABLE posee(
	clase VARCHAR(45) NOT NULL,
	comodidad INT UNSIGNED NOT NULL,

	FOREIGN KEY (clase) REFERENCES clases (nombre)
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	FOREIGN KEY (comodidad) REFERENCES comodidades (codigo)
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	PRIMARY KEY(clase, comodidad)		

)ENGINE=InnoDB;

CREATE TABLE brinda(
	vuelo VARCHAR(45) NOT NULL,
	dia ENUM('Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa') NOT NULL,
	clase VARCHAR(45) NOT NULL,
	precio DECIMAL(7,2) UNSIGNED NOT NULL,
	cant_asientos INT UNSIGNED NOT NULL,

	FOREIGN KEY (vuelo, dia) REFERENCES salidas (vuelo, dia)
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	FOREIGN KEY (clase) REFERENCES clases (nombre)
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	PRIMARY KEY(vuelo, dia, clase)			
)ENGINE=InnoDB;

CREATE TABLE reserva_vuelo_clase(
	numero INT UNSIGNED NOT NULL,
	vuelo VARCHAR(45) NOT NULL,
	fecha_vuelo DATE NOT NULL,
	clase VARCHAR(45) NOT NULL,

	FOREIGN KEY (numero) REFERENCES reservas (numero)
   	ON DELETE CASCADE ON UPDATE CASCADE,
	
	FOREIGN KEY (vuelo, fecha_vuelo) REFERENCES instancias_vuelo (vuelo, fecha)
   	ON DELETE CASCADE ON UPDATE CASCADE,   	

   	FOREIGN KEY (clase) REFERENCES clases (nombre)
   	ON DELETE CASCADE ON UPDATE CASCADE,

   	PRIMARY KEY(numero, vuelo, fecha_vuelo)				
)ENGINE=InnoDB;

#-------------------------------------------------------------------------
# Creacion vista vuelos_disponibles

	CREATE VIEW vuelos_disponibles AS
	SELECT DISTINCT
		s.vuelo,
		s.modelo_avion,
		s.hora_sale,
		s.hora_llega,
		s.dia,
		iv.fecha,
		CASE 
			WHEN CAST(TIMEDIFF(DATE_ADD(s.hora_llega, INTERVAL ubi_lleg.huso HOUR), DATE_ADD(s.hora_sale, INTERVAL ubi_sal.huso HOUR)) AS TIME) > 0 THEN
				CAST(TIMEDIFF(DATE_ADD(s.hora_llega, INTERVAL ubi_lleg.huso HOUR), DATE_ADD(s.hora_sale, INTERVAL ubi_sal.huso HOUR)) AS TIME)
			ELSE CAST(ADDTIME(SUBTIME("24:00",DATE_ADD(s.hora_sale, INTERVAL ubi_sal.huso HOUR)), DATE_ADD(s.hora_llega, INTERVAL ubi_lleg.huso HOUR)) AS TIME)
		END	AS tiempo_estimado_de_vuelo,
		vp.aeropuerto_salida,
		aero_sal.nombre AS nombre_aero_salida,
		aero_sal.ciudad AS ciudad_aero_salida,
		aero_sal.estado AS estado_aero_salida,
		aero_sal.pais AS pais_aero_salida,
		vp.aeropuerto_llegada,
		aero_lleg.nombre AS nombre_aero_llegada,
		aero_lleg.ciudad AS ciudad_aero_llegada,
		aero_lleg.estado AS estado_aero_llegada,
		aero_lleg.pais AS pais_aero_llegada,
		b.precio,
		b.clase,
		FLOOR(	(b.cant_asientos * (1 + c.porcentaje)- (  SELECT
															COUNT(*) 
														FROM 
															reserva_vuelo_clase
														WHERE
															(reserva_vuelo_clase.vuelo = b.vuelo) AND
															(reserva_vuelo_clase.clase = b.clase) AND
															(reserva_vuelo_clase.fecha_vuelo = iv.fecha)
														)
				)
			) AS asientos_disponibles
	FROM
		instancias_vuelo AS iv,
		salidas AS s,
		vuelos_programados AS vp,
		aeropuertos AS aero_sal,
		aeropuertos AS aero_lleg,
		brinda AS b,
		clases AS c,
        ubicaciones AS ubi_lleg, 
        ubicaciones AS ubi_sal

	WHERE
		(iv.vuelo = s.vuelo) AND
		(iv.dia = s.dia) AND
		(s.vuelo = vp.numero) AND
		(vp.aeropuerto_salida = aero_sal.codigo) AND
		(vp.aeropuerto_llegada = aero_lleg.codigo) AND
		(b.vuelo = s.vuelo) AND
		(b.dia = s.dia) AND
		(b.clase = c.nombre) AND
        (aero_lleg.pais = ubi_lleg.pais) AND 
        (aero_lleg.estado = ubi_lleg.estado) AND 
        (aero_lleg.ciudad = ubi_lleg.ciudad) AND 
        (aero_sal.pais = ubi_sal.pais) AND 
        (aero_sal.estado = ubi_sal.estado) AND 
        (aero_sal.ciudad = ubi_sal.ciudad)
	;
							

#-------------------------------------------------------------------------
# Creación de Procedures

use vuelos;
delimiter ! 
CREATE PROCEDURE realizarReservaSoloIda(IN nroVuelo VARCHAR(45) ,IN fechaVuelo DATE, IN claseVuelo VARCHAR(45), 
IN docTipoCliente VARCHAR(45), IN docNroCliente INT UNSIGNED,IN legEmpleado INT UNSIGNED)
BEGIN
	
	-- here i can declare variables
	declare cantidadAsientosReservados SMALLINT UNSIGNED;
	declare porcentajeClaseVuelo decimal(2,2) UNSIGNED;
	declare cantAsientosBrindaVuelo INT UNSIGNED;

	START TRANSACTION;
		-- verificar que haya lugares disponibles en la clase y vuelo elegidos.
		#asientos_reservados(vuelo,fecha,clase,cantidad)
		-- asientos reservados mantiene la cantidad de asientos reservados para cada clase e instancia de vuelo
		-- La tabla asientos reservados se utilizara para calcular los lugares disponibles en una clase de un vuelo y
		-- debera ser actualizada durante cada reserva
		SELECT cantidad INTO cantidadAsientosReservados
		FROM asientos_reservados 
		WHERE vuelo=nroVuelo AND fecha=fechaVuelo AND clase=claseVuelo
		for UPDATE;

		SELECT porcentaje INTO porcentajeClaseVuelo
		FROM clases 
		WHERE nombre=claseVuelo;	

		#brinda(vuelo, dia, clase, precio, cant asientos)
		SELECT cant_asientos INTO cantAsientosBrindaVuelo 
		FROM brinda 
		WHERE vuelo=nroVuelo AND clase=claseVuelo;

		IF EXISTS (SELECT * FROM pasajeros AS p WHERE p.doc_tipo = docTipoCliente AND p.doc_nro = docNroCliente) THEN

			-- check que no intente reservar luego de la fecha de vencimiento
			IF( DATE_ADD(CURDATE(), INTERVAL 15 DAY) > fechaVuelo) THEN
				SELECT "Sin exito" AS Resultado, "La reserva no se pudo completar. Se debe realizar la reserva de Ida con 15 dias de anticipacion." AS Mensaje;
			-- Si la cantidad de reservas es menor a la cantidad de asientos que brinda, el estado de la reserva seria: confirmada.
			-- En caso contrario el estado de la reserva seria: en espera".¿¿¿¿¿?????
			ELSEIF (cantidadAsientosReservados<cantAsientosBrindaVuelo) THEN

				#reservas(numero, fecha, vencimiento, estado, doc tipo, doc nro, legajo)
				INSERT INTO reservas (fecha,vencimiento,estado,doc_tipo,doc_nro,legajo)
				VALUES (CURDATE(), DATE_SUB(fechaVuelo, INTERVAL 15 DAY),"confirmada",docTipoCliente,docNroCliente,legEmpleado);
			
				# reserva_vuelo_clase (numero, vuelo, fecha_vuelo, clase)
				INSERT INTO reserva_vuelo_clase (numero,vuelo, fecha_vuelo, clase) VALUES (LAST_INSERT_ID(),nroVuelo,fechaVuelo,claseVuelo);
				UPDATE asientos_reservados SET cantidad=cantidadAsientosReservados+1 WHERE vuelo=nroVuelo AND fecha=fechaVuelo AND clase=claseVuelo;

				SELECT "Exito" AS Resultado, "La reserva se realizo con exito." AS Mensaje;

		-- considero porcentaje de asientos descubiertos¿¿¿
			ELSEIF (cantidadAsientosReservados < cantAsientosBrindaVuelo*(1+porcentajeClaseVuelo) ) THEN

				#reservas(numero, fecha, vencimiento, estado, doc tipo, doc nro, legajo)
				INSERT INTO reservas (fecha,vencimiento,estado,doc_tipo,doc_nro,legajo)
				VALUES (CURDATE(), DATE_SUB(fechaVuelo, INTERVAL 15 DAY),"en espera",docTipoCliente,docNroCliente,legEmpleado);
			
				# reserva_vuelo_clase (numero, vuelo, fecha_vuelo, clase)
				INSERT INTO reserva_vuelo_clase (numero,vuelo, fecha_vuelo, clase) VALUES (LAST_INSERT_ID(),nroVuelo,fechaVuelo,claseVuelo);
				UPDATE asientos_reservados SET cantidad=cantidadAsientosReservados+1 WHERE vuelo=nroVuelo AND fecha=fechaVuelo AND clase=claseVuelo;

				SELECT "Exito" AS Resultado, "La reserva se realizo en estado de espera." AS Mensaje;

			ELSE
			
				SELECT "Sin exito" AS Resultado, "La reserva no se pudo realizar, ya que no hay mas asientos disponibles para dicha clase en el vuelo seleccionado." AS Mensaje;

			END IF;
		ELSE
	
			SELECT "Error!" AS Resultado, "El pasajero no existe en la base de datos." AS Mensaje;

		END IF; 
	
	COMMIT;	
END; !
delimiter ;

delimiter !
CREATE PROCEDURE realizarReservaIdaVuelta(IN nroVueloIda VARCHAR(45) ,IN fechaVueloIda DATE, IN claseVueloIda VARCHAR(45),
IN nroVueloVuelta VARCHAR(45) ,IN fechaVueloVuelta DATE, IN claseVueloVuelta VARCHAR(45), IN docTipoCliente VARCHAR(45), IN docNroCliente INT UNSIGNED,
IN legEmpleado INT UNSIGNED)
BEGIN
	-- here i can declare variables--
	declare cantidadAsientosReservadosIda,cantidadAsientosReservadosVuelta SMALLINT UNSIGNED;
	declare porcentajeClaseVueloIda,porcentajeClaseVueloVuelta decimal(2,2) UNSIGNED;
	declare cantAsientosBrindaVueloIda,cantAsientosBrindaVueloVuelta INT UNSIGNED;

	START TRANSACTION;
		-- verificar que haya lugares disponibles en la clase y vuelo elegidos.--
		#asientos_reservados(vuelo,fecha,clase,cantidad)
		-- asientos reservados mantiene la cantidad de asientos reservados para cada clase e instancia de vuelo--
		-- La tabla asientos reservados se utilizara para calcular los lugares disponibles en una clase de un vuelo y--
		-- debera ser actualizada durante cada reserva--
		SELECT cantidad INTO cantidadAsientosReservadosIda
		FROM asientos_reservados 
		WHERE vuelo=nroVueloIda AND fecha=fechaVueloIda AND clase=claseVueloIda
		for UPDATE;

		SELECT cantidad INTO cantidadAsientosReservadosVuelta
		FROM asientos_reservados 
		WHERE vuelo=nroVueloVuelta AND fecha=fechaVueloVuelta AND clase=claseVueloVuelta
		for UPDATE;

		SELECT porcentaje INTO porcentajeClaseVueloIda
		FROM clases 
		WHERE nombre=claseVueloIda;	

		SELECT porcentaje INTO porcentajeClaseVueloVuelta
		FROM clases 
		WHERE nombre=claseVueloVuelta;	

		#brinda(vuelo, dia, clase, precio, cant asientos)
		SELECT cant_asientos INTO cantAsientosBrindaVueloIda 
		FROM brinda 
		WHERE vuelo=nroVueloIda AND clase=claseVueloIda;

		SELECT cant_asientos INTO cantAsientosBrindaVueloVuelta 
		FROM brinda 
		WHERE vuelo=nroVueloVuelta AND clase=claseVueloVuelta;
		
		IF EXISTS (SELECT * FROM pasajeros AS p WHERE p.doc_tipo = docTipoCliente AND p.doc_nro = docNroCliente) THEN

			IF ( DATE_ADD(CURDATE(), INTERVAL 15 DAY) > fechaVueloIda) THEN
				SELECT "Sin exito" AS Resultado, "La reserva no se pudo completar. Se debe realizar la reserva de Ida con 15 dias de anticipacion." AS Mensaje;
			-- Si la cantidad de reservas es menor a la cantidad de asientos que brinda, el estado de la reserva seria: confirmada.-- 
			-- En caso contrario el estado de la reserva seria: en espera".¿¿¿¿¿?????--
			ELSEIF (cantidadAsientosReservadosIda<cantAsientosBrindaVueloIda AND cantidadAsientosReservadosVuelta<cantAsientosBrindaVueloVuelta) THEN
				# reservas (numero, fecha, vencimiento, estado, doc_tipo, doc_nro, legajo)
				-- El numero de reserva sera uno solo y estara asociado a ambos vuelos.--
				INSERT INTO reservas(fecha,vencimiento,estado,doc_tipo,doc_nro,legajo) 
				VALUES (CURDATE(),DATE_SUB(fechaVueloIda, INTERVAL 15 DAY),"confirmada",docTipoCliente,docNroCliente,legEmpleado);
			
				# reserva_vuelo_clase (numero, vuelo, fecha_vuelo, clase)
				INSERT INTO reserva_vuelo_clase(numero,vuelo, fecha_vuelo, clase) VALUES (LAST_INSERT_ID(),nroVueloIda,fechaVueloIda,claseVueloIda);
				INSERT INTO reserva_vuelo_clase VALUES (LAST_INSERT_ID(),nroVueloVuelta,fechaVueloVuelta,claseVueloVuelta);
			
				UPDATE asientos_reservados SET cantidad=cantidadAsientosReservadosIda+1 WHERE vuelo=nroVueloIda AND fecha=fechaVueloIda AND clase=claseVueloIda;
				UPDATE asientos_reservados SET cantidad=cantidadAsientosReservadosVuelta+1 WHERE vuelo=nroVueloVuelta AND fecha=fechaVueloVuelta AND clase=claseVueloVuelta;
			
				SELECT  "Exito" AS Resultado, "La reserva se realizo con exito." AS Mensaje;

			-- considero porcentaje de asientos descubiertos. Tiene asientos disponibles pero la capacidad fisica del de ida o vuelta esta colmada--
			ELSEIF (cantidadAsientosReservadosIda < cantAsientosBrindaVueloIda*(1+porcentajeClaseVueloIda) AND  
			cantidadAsientosReservadosVuelta < cantAsientosBrindaVueloVuelta*(1+porcentajeClaseVueloVuelta) ) THEN
			
				-- El numero de reserva sera uno solo y estara asociado a ambos vuelos.--
				INSERT INTO reservas(fecha,vencimiento,estado,doc_tipo,doc_nro,legajo) 
				VALUES (CURDATE(),DATE_SUB(fechaVueloIda, INTERVAL 15 DAY),"en espera",docTipoCliente,docNroCliente,legEmpleado);
			
				# reserva_vuelo_clase (numero, vuelo, fecha_vuelo, clase)
				INSERT INTO reserva_vuelo_clase(numero,vuelo, fecha_vuelo, clase) VALUES (LAST_INSERT_ID(),nroVueloIda,fechaVueloIda,claseVueloIda);
				INSERT INTO reserva_vuelo_clase VALUES (LAST_INSERT_ID(),nroVueloVuelta,fechaVueloVuelta,claseVueloVuelta);
			
				UPDATE asientos_reservados SET cantidad=cantidadAsientosReservadosIda+1 WHERE vuelo=nroVueloIda AND fecha=fechaVueloIda AND clase=claseVueloIda;
				UPDATE asientos_reservados SET cantidad=cantidadAsientosReservadosVuelta+1 WHERE vuelo=nroVueloVuelta AND fecha=fechaVueloVuelta AND clase=claseVueloVuelta;
				-- check de cual vuelo tiene capacidad fisica colmada--
				IF (cantidadAsientosReservadosIda >= cantAsientosBrindaVueloIda) THEN
					SELECT "Exito" AS Resultado, "La reserva se realizo con exito, esta en estado de espera debido a que el vuelo de ida no cuenta con los suficientes asientos fisicos disponibles." AS Mensaje;
				-- sino la condicion que no se cumplio es que cantidadAsientosReservadosVuelta<cantAsientosBrindaVueloVuelta--
				ELSE
					SELECT "Exito" AS Resultado, "La reserva se realizo con exito, esta en estado de espera debido que el vuelo de vuelta no cuenta con los suficientes asientos fisicos disponibles." AS Mensaje;
				END IF;	

			ELSE
				IF (cantidadAsientosReservadosIda >= cantAsientosBrindaVueloIda*(1+porcentajeClaseVueloIda)) THEN
					SELECT "Sin exito" AS Resultado, "La reserva no se pudo realizar debido a que no hay mas asientos disponibles para el vuelo de ida." AS Mensaje;
				ELSE
					-- sino la condicion que no se cumplio es que cantidadAsientosReservadosVuelta<cantAsientosBrindaVueloVuelta+descubiertos--
					SELECT "Sin exito" AS Resultado, "La reserva no se pudo realizar debido a que no hay mas asientos disponibles para el vuelo de vuelta." AS Mensaje;
				END IF;

			END IF;
		
		ELSE
	
			SELECT "Error!" AS Resultado, "El pasajero no existe en la base de datos." AS Mensaje;

		END IF; 

	COMMIT;	
END; !
delimiter ;

#-------------------------------------------------------------------------
# Creación Usuarios y Otorgamiento de privilegios
    
    CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
	GRANT ALL PRIVILEGES ON vuelos.* TO admin@localhost WITH GRANT OPTION;
	
	#DROP USER ''@'localhost';
    CREATE USER 'empleado'@'%' IDENTIFIED BY 'empleado'; 
    GRANT SELECT ON vuelos.* TO 'empleado'@'%';
    GRANT SELECT, INSERT, UPDATE, DELETE ON vuelos.reservas TO 'empleado'@'%';
    GRANT SELECT, INSERT, UPDATE, DELETE ON vuelos.pasajeros TO 'empleado'@'%';
    GRANT SELECT, INSERT, UPDATE, DELETE ON vuelos.reserva_vuelo_clase TO 'empleado'@'%';
	GRANT INSERT, DELETE, UPDATE on vuelos.asientos_reservados TO 'empleado'@'%';
	GRANT EXECUTE ON PROCEDURE vuelos.realizarReservaSoloIda TO 'empleado'@'%';
	GRANT EXECUTE ON PROCEDURE vuelos.realizarReservaIdaVuelta TO 'empleado'@'%';

    CREATE USER 'cliente'@'%' IDENTIFIED BY 'cliente';
    GRANT SELECT ON vuelos.vuelos_disponibles TO cliente@'%';