CREATE DATABASE GRAVICOR;

USE GRAVICOR



CREATE TABLE ACTIVO(
IDACTIVO INT NOT NULL PRIMARY KEY,
DESCRIPCION VARCHAR(15) NOT NULL
)

CREATE TABLE TIPOCAMION(
IDTIPOCAMION INT NOT NULL IDENTITY PRIMARY KEY,
DESCRIPCION VARCHAR(15) NOT NULL,
CAPACIDAD INT  NOT NULL,
TONELADAS  INT NOT NULL
)

CREATE TABLE CAMION(
IDCAMION INT NOT NULL IDENTITY PRIMARY KEY,
OPERADOR VARCHAR(50) NOT NULL,
IDTIPOCAMION INT NOT NULL REFERENCES TIPOCAMION(IDTIPOCAMION),
COLOR VARCHAR(15) NOT NULL,
ACTIVO INT REFERENCES ACTIVO(IDACTIVO)
)


CREATE TABLE IMPRESION(
IDIMPRESION INT NOT NULL IDENTITY PRIMARY KEY,
DIA DATE NOT NULL,
HORA TIME NOT NULL,
CAMIONID INT NOT NULL REFERENCES CAMION(IDCAMION)
)

CREATE TABLE USUARIO(
	IDUSUARIO INT NOT NULL IDENTITY PRIMARY KEY,
	EMAIL VARCHAR(50) NOT NULL,
	CONTRASENA VARCHAR(50) NOT NULL,
	NOMBRES VARCHAR(50) NOT NULL,
	APELLIDOS VARCHAR(50) NOT NULL,
	GESTIONPIEDRAGRENA BIT NOT NULL,
	GESTIONVENTAS BIT NOT NULL,
	REPORTES BIT NOT NULL,
	COBRANZA BIT NOT NULL,
	TESORERIA BIT NOT NULL,
	GESTIONGASTOSGENERALES BIT NOT NULL,
	CONFIGURACIONCUENTAS BIT NOT NULL,
	ACTIVO INT NOT NULL REFERENCES ACTIVO(IDACTIVO),
	USERNAME VARCHAR(50) NOT NULL
)

CREATE TABLE VIAJE(
IDVIAJE INT NOT NULL IDENTITY PRIMARY KEY,
IDUSUARIO INTEGER NOT NULL REFERENCES USUARIO(IDUSUARIO),
IDCAMION INTEGER NOT NULL REFERENCES CAMION(IDCAMION),
FECHA DATE NOT NULL,
HORA TIME NOT NULL,
ESACTIVO BIT NULL
)

CREATE TABLE TIPOPLANTA(
TIPOPLANTAID INT NOT NULL IDENTITY PRIMARY KEY,
DESCRIPCION VARCHAR(50) NOT NULL
)

CREATE TABLE PLANTA(
PLANTAID INTEGER NOT NULL IDENTITY PRIMARY KEY,
NOMBREPLANTA VARCHAR(50) NOT NULL,
TIPOPLANTAID INT NOT NULL REFERENCES TIPOPLANTA(TIPOPLANTAID),
COSTOOPERATIVO FLOAT NOT NULL
)



CREATE TABLE INVENTARIOPLANTA (
FECHAINVENTARIO DATE NOT NULL PRIMARY KEY,
PLANTAID INT NOT NULL REFERENCES PLANTA(PLANTAID),
INVENTARIOPIEDRAGRENA INT NOT NULL,
INVENTARIOPIEDRAPRODUCIDA INT NOT NULL
)

CREATE TABLE CLIENTE (
CLIENTEID INT NOT NULL IDENTITY PRIMARY KEY,
NOMBRECLIENTE VARCHAR(50) NOT NULL
)

CREATE TABLE MATERIAL(
MATERIALID INT NOT NULL IDENTITY PRIMARY KEY,
DESCRIPCIONMATERIAL VARCHAR(50) NOT NULL
)

CREATE TABLE PRECIO_CLIENTE_MATERIAL(
CLIENTEID INT NOT NULL REFERENCES CLIENTE(CLIENTEID),
MATERIALID INT NOT NULL REFERENCES MATERIAL(MATERIALID),
PRECIO FLOAT NOT NULL
)


CREATE TABLE FACTURA(
IDFACTURA VARCHAR(50) NOT NULL PRIMARY KEY,
MONTOFACTURA FLOAT NOT NULL,
FECHAFACTURA DATE NOT NULL,
ESPAGADO BIT NOT NULL,
FECHAPAGO DATE NULL,
PRECIOM3FACTURA FLOAT NOT NULL,
ESACTIVO INT NOT NULL REFERENCES ACTIVO(IDACTIVO)
)



CREATE TABLE VENTA(
VENTAID INT NOT NULL IDENTITY PRIMARY KEY,
CLIENTEID INT NOT NULL REFERENCES CLIENTE(CLIENTEID),
MATERIALID INT NOT NULL REFERENCES MATERIAL(MATERIALID),
FOLIOTRANSPORTISTA VARCHAR(50) NOT NULL,
MATRICULACAMION VARCHAR(50) NOT NULL,
NOMBRECHOFER VARCHAR(50) NOT NULL,
PLANTAID INTEGER NOT NULL REFERENCES PLANTA(PLANTAID),
FECHAVENTA DATE NOT NULL,
HORAVENTA TIME NOT NULL,
USUARIOID INT NOT NULL REFERENCES USUARIO(IDUSUARIO),
ACTIVO INT NOT NULL REFERENCES ACTIVO(IDACTIVO),
COSTOOPERATIVOPLANTAPRODUCTORA FLOAT NOT NULL,
ESFACTURADO BIT NOT NULL,
FACTURAID VARCHAR(50) NULL REFERENCES FACTURA(IDFACTURA),
PRECIOM3 FLOAT NOT NULL,
CANTIDADM3 INT NOT NULL
)





CREATE TABLE GASTOGENERAL(
GASTOGENERALID INT NOT NULL IDENTITY PRIMARY KEY,
IDFOLIOPAGO VARCHAR(50) NULL,
FECHAFOLIOPAGO DATE NULL,
FOLIOFACTURA VARCHAR(50) NOT NULL,
FECHAFACTURA DATE NOT NULL,
TOTAL FLOAT NOT NULL,
DESCRIPCION VARCHAR(50) NOT NULL,
EQUIPO VARCHAR(50) NOT NULL,
PROVEEDOR VARCHAR(50) NOT NULL,
ESACTIVO INT NOT NULL REFERENCES ACTIVO(IDACTIVO)
)

CREATE TABLE VENTAFANTASMA(
VANTAID INT NOT NULL IDENTITY PRIMARY KEY,
REFERENCIAVENTA INT NULL REFERENCES VENTA(VENTAID),
ESACTIVO INT NOT NULL REFERENCES ACTIVO(IDACTIVO),
IDFACTURA VARCHAR(50) NOT NULL REFERENCES FACTURA(IDFACTURA),
CANTIDADM3 INT NOT NULL,
ESCONCILIADA BIT NOT NULL
)



--PROCEDIMIENTO PARA A�ADIR UN NUEVO CLIENTE, CON LOS PRECIOS ASIGNADOS POR PRODUCTO, SIN QUE SE REPITA EL NOMBRE


USE GRAVICOR;
GO
CREATE PROCEDURE INSERTARCLIENTE
    @NOMBRECLIENTE varchar(50),   
    @PRECIO1 FLOAT,
	@PRECIO2 FLOAT,
	@PRECIO3 FLOAT 
AS   

IF EXISTS (SELECT CLIENTE.CLIENTEID  FROM CLIENTE WHERE CLIENTE.NOMBRECLIENTE = @NOMBRECLIENTE) 
	BEGIN
	   RETURN -1
	END
ELSE
	BEGIN
		BEGIN TRANSACTION
		DECLARE @ERROR INT;

		INSERT INTO CLIENTE VALUES (@NOMBRECLIENTE);
		DECLARE @IDCLIENTE INT;
		Select @IDCLIENTE = (select SCOPE_IDENTITY());
		INSERT INTO PRECIO_CLIENTE_MATERIAL VALUES (@IDCLIENTE,1,@PRECIO1);
		INSERT INTO PRECIO_CLIENTE_MATERIAL VALUES (@IDCLIENTE,2,@PRECIO2);
		INSERT INTO PRECIO_CLIENTE_MATERIAL VALUES (@IDCLIENTE,3,@PRECIO3);

		SET @ERROR = @@ERROR
		IF(@ERROR <> 0)
			BEGIN
				ROLLBACK
				RETURN 0
			END
		ELSE
			BEGIN
				COMMIT
				RETURN 1
			END
	END
GO
--ACABA PROCEDIMIENTO
--PROCEDIMIENTO PARA EDITAR UN CLIENTE
ALTER PROCEDURE EDITARCLIENTE
	@IDCLIENTE INT,
	@PRECIO1 FLOAT,
	@PRECIO2 FLOAT,
	@PRECIO3 FLOAT
AS
	
	IF NOT EXISTS (SELECT CLIENTE.CLIENTEID  FROM CLIENTE WHERE CLIENTE.CLIENTEID = @IDCLIENTE) 
		BEGIN
		   RETURN -1
		END
	ELSE
		BEGIN
			BEGIN TRANSACTION
			DECLARE @ERROR INT;
			
			UPDATE PRECIO_CLIENTE_MATERIAL SET PRECIO_CLIENTE_MATERIAL.PRECIO = @PRECIO1 WHERE PRECIO_CLIENTE_MATERIAL.CLIENTEID = @IDCLIENTE AND PRECIO_CLIENTE_MATERIAL.MATERIALID = 1
			UPDATE PRECIO_CLIENTE_MATERIAL SET PRECIO_CLIENTE_MATERIAL.PRECIO = @PRECIO2 WHERE PRECIO_CLIENTE_MATERIAL.CLIENTEID = @IDCLIENTE AND PRECIO_CLIENTE_MATERIAL.MATERIALID = 2
			UPDATE PRECIO_CLIENTE_MATERIAL SET PRECIO_CLIENTE_MATERIAL.PRECIO = @PRECIO3 WHERE PRECIO_CLIENTE_MATERIAL.CLIENTEID = @IDCLIENTE AND PRECIO_CLIENTE_MATERIAL.MATERIALID = 3
			

			SET @ERROR = @@ERROR
			IF(@ERROR <> 0)
				BEGIN
					ROLLBACK
					RETURN 0
				END
			ELSE
				BEGIN
					COMMIT
					RETURN 1
				END
		END
GO
--ACABA PROCEDIMIENTO
--EMPIEZA PROCEDIMIENTO PARA OBTENER EL PRECIO POR M3 DADO EL NOMBRE DEL CLIENTE Y EL NOMBRE DEL ARTICULO
ALTER PROCEDURE OBTENERPRECIOCLIENTEMATERIAL
    @NOMBRECLIENTE VARCHAR(50),   
    @NOMBREMATERIAL VARCHAR(50)
AS   
DECLARE @PRECIO FLOAT;
IF NOT EXISTS (SELECT CLIENTE.CLIENTEID  FROM CLIENTE WHERE CLIENTE.NOMBRECLIENTE = @NOMBRECLIENTE) 
	BEGIN
	   SELECT @PRECIO = -1.0;
	END
ELSE
	BEGIN
		IF NOT EXISTS(SELECT MATERIAL.MATERIALID FROM MATERIAL WHERE MATERIAL.DESCRIPCIONMATERIAL = @NOMBREMATERIAL)
			BEGIN
				SELECT @PRECIO = -1.0;
			END
		ELSE
			BEGIN
		
				DECLARE @ERROR INT;
				DECLARE @IDCLIENTE INT;
				DECLARE @IDMATERIAL INT;
		

				SELECT @IDCLIENTE = CLIENTE.CLIENTEID  FROM CLIENTE WHERE CLIENTE.NOMBRECLIENTE = @NOMBRECLIENTE;
				SELECT @IDMATERIAL = MATERIAL.MATERIALID FROM MATERIAL WHERE MATERIAL.DESCRIPCIONMATERIAL = @NOMBREMATERIAL;

				SELECT @PRECIO = P.PRECIO FROM PRECIO_CLIENTE_MATERIAL AS P WHERE P.CLIENTEID = @IDCLIENTE AND P.MATERIALID = @IDMATERIAL;


				SET @ERROR = @@ERROR
				IF(@ERROR <> 0)
					BEGIN
						
						SELECT @PRECIO = -2.0;
					END
				
			END
	END
SELECT @PRECIO;

GO
--TERMINA PROCEDIMIENTO
--FUNCION COMPLEMENTARIA AL PROCEDIMIENTO PASADO
CREATE FUNCTION DBO.OBTENERPRECIO(@NOMBRECLIENTE VARCHAR(50), @NOMBREMATERIAL VARCHAR(50))  
RETURNS FLOAT   
AS   
-- Returns the stock level for the product.  
BEGIN  
     DECLARE @PRECIO FLOAT;
	 IF NOT EXISTS (SELECT CLIENTE.CLIENTEID  FROM CLIENTE WHERE CLIENTE.NOMBRECLIENTE = @NOMBRECLIENTE) 
		BEGIN
		   SELECT @PRECIO = -1.0;
		   
		END
	
	ELSE
		BEGIN
			IF NOT EXISTS(SELECT MATERIAL.MATERIALID FROM MATERIAL WHERE MATERIAL.DESCRIPCIONMATERIAL = @NOMBREMATERIAL)
				BEGIN
					SELECT @PRECIO = -1.0;
				
				END
			ELSE
				BEGIN
					DECLARE @ERROR INT;
					DECLARE @IDCLIENTE INT;
					DECLARE @IDMATERIAL INT;
		

					SELECT @IDCLIENTE = CLIENTE.CLIENTEID  FROM CLIENTE WHERE CLIENTE.NOMBRECLIENTE = @NOMBRECLIENTE;
					SELECT @IDMATERIAL = MATERIAL.MATERIALID FROM MATERIAL WHERE MATERIAL.DESCRIPCIONMATERIAL = @NOMBREMATERIAL;

					SELECT @PRECIO = P.PRECIO FROM PRECIO_CLIENTE_MATERIAL AS P WHERE P.CLIENTEID = @IDCLIENTE AND P.MATERIALID = @IDMATERIAL;


					SET @ERROR = @@ERROR
					IF(@ERROR <> 0)
						BEGIN
							SELECT @PRECIO = -2.0;
						END
				END
		END
	RETURN @PRECIO;
END; 

--TERMINA FUNCION

--PROCEDIMIENTO QUE REGRESA EL ID  DE UN CLIENTE DADO SU NOMBRE
create PROCEDURE OBTENERCLIENTE
	@NOMBRECLIENTE VARCHAR(50)
AS
	
	IF NOT EXISTS (SELECT CLIENTE.CLIENTEID  FROM CLIENTE WHERE CLIENTE.NOMBRECLIENTE = @NOMBRECLIENTE) 
		BEGIN
		   RETURN -1
		END
	ELSE
		BEGIN
			DECLARE @CLIENTEID INT;
			DECLARE @ERROR INT;
			
			SELECT @CLIENTEID = CLIENTE.CLIENTEID FROM CLIENTE WHERE CLIENTE.NOMBRECLIENTE = @NOMBRECLIENTE;

			SET @ERROR = @@ERROR
			IF(@ERROR <> 0)
				BEGIN
					RETURN -2
				END
			RETURN @CLIENTEID;
		END
GO

--TERMINA PROCEDIMIENTO
--PROCEDIMIENTO QUE REGRESA EL ID DE UNA MATERIAL DADO SU NOMBRE
create PROCEDURE OBTENERMATERIAL
	@NOMBREMATERIAL VARCHAR(50)
AS
	
	IF NOT EXISTS (SELECT MATERIAL.MATERIALID  FROM MATERIAL WHERE MATERIAL.DESCRIPCIONMATERIAL = @NOMBREMATERIAL) 
		BEGIN
		   RETURN -1
		END
	ELSE
		BEGIN
			DECLARE @MATERIALID INT;
			DECLARE @ERROR INT;
			
			SELECT @MATERIALID = MATERIAL.MATERIALID FROM MATERIAL WHERE MATERIAL.DESCRIPCIONMATERIAL = @NOMBREMATERIAL;

			SET @ERROR = @@ERROR
			IF(@ERROR <> 0)
				BEGIN
					RETURN -2
				END
			RETURN @MATERIALID;
		END
GO
--TERMINA PROCEDIMIENTO
--PROCEDIMIENTO PARA A�ADIR UNA VENTA
USE GRAVICOR;
GO
CREATE PROCEDURE INSERTARVENTA
    @CLIENTEID INT, 
	@MATERIALID INT,
	@FOLIOTRANSPORTISTA VARCHAR(50),
	@MATRICULACAMION VARCHAR(50),
	@NOMBRECHOFER VARCHAR(50),
	@PLANTAID INT,
	@USUARIOID INT,
	@COSTOOPERATIVOPLANTA FLOAT,
	@ESFACTURADO BIT,
	@PRECIOM3 FLOAT,
	@CANTIDADM3 INT
AS  
	BEGIN TRANSACTION
	DECLARE @ERROR INT;
	IF(@ESFACTURADO = 1)
		BEGIN
			INSERT INTO VENTA VALUES (@CLIENTEID,@MATERIALID, @FOLIOTRANSPORTISTA, @MATRICULACAMION, @NOMBRECHOFER,
										@PLANTAID, GETDATE(),GETDATE(),@USUARIOID, 1, @COSTOOPERATIVOPLANTA,
										1, NULL, @PRECIOM3, @CANTIDADM3);
		END
	ELSE
		BEGIN
			INSERT INTO VENTA VALUES (@CLIENTEID,@MATERIALID, @FOLIOTRANSPORTISTA, @MATRICULACAMION, @NOMBRECHOFER,
										@PLANTAID, GETDATE(),GETDATE(),@USUARIOID, 1, @COSTOOPERATIVOPLANTA,
										0, NULL, @PRECIOM3, @CANTIDADM3);
		END
	SET @ERROR = @@ERROR
	IF(@ERROR <> 0)
		BEGIN
			ROLLBACK
			RETURN -1
		END
	ELSE
		BEGIN
			COMMIT
			RETURN 1
		END

GO

--TERMINA PROCEDIMIENTO
--PROCEDIMIENTO QUE REGRESA EL ID DE UNA PLANTA
create PROCEDURE OBTENERPLANTA
	@NOMBREPLANTA VARCHAR(50)
AS
	
	IF NOT EXISTS (SELECT PLANTA.PLANTAID FROM PLANTA WHERE PLANTA.NOMBREPLANTA = @NOMBREPLANTA) 
		BEGIN
		   RETURN -1
		END
	ELSE
		BEGIN
			DECLARE @PLANTAID INT;
			DECLARE @ERROR INT;
			
			SELECT @PLANTAID = PLANTA.PLANTAID FROM PLANTA WHERE PLANTA.NOMBREPLANTA = @NOMBREPLANTA;

			SET @ERROR = @@ERROR
			IF(@ERROR <> 0)
				BEGIN
					RETURN -2
				END
			RETURN @PLANTAID;
		END
GO

--TERMINA PROCEDIMIENTO
--EMPIEZA PROCEDIMIENTO QUE REGRESA EL ID DE UN USUARIO DADO SU USERNAME
create PROCEDURE OBTENERUSUARIO
	@USERNAME VARCHAR(50)
AS
	
	IF NOT EXISTS (SELECT USUARIO.IDUSUARIO FROM USUARIO WHERE USUARIO.USERNAME = @USERNAME) 
		BEGIN
		   RETURN -1
		END
	ELSE
		BEGIN
			DECLARE @USUARIOID INT;
			DECLARE @ERROR INT;
			
			SELECT @USUARIOID = USUARIO.IDUSUARIO FROM USUARIO WHERE USUARIO.USERNAME = @USERNAME;

			SET @ERROR = @@ERROR
			IF(@ERROR <> 0)
				BEGIN
					RETURN -2
				END
			RETURN @USUARIOID;
		END
GO
--TERMINA PROCEDIMIENTO

USE GRAVICOR

SELECT * FROM USUARIO
DECLARE @V INT;
EXEc @V = OBTENERUSUARIO'jaquiiiii';
SELECT @V;

SELECT PLANTA.PLANTAID FROM PLANTA WHERE PLANTA.NOMBREPLANTA = 'gravicor 3'
SELECT * FROM PLANTA

DECLARE @V INT;
EXEc @V = OBTENERPLANTA'gravicor ';
SELECT @V;

DECLARE @VAR INT;
EXEC @VAR =  INSERTARVENTA 1,1,'20232','5256','JUANCHOPEREZ',1,1,0,1,54.5,NULL;
SELECT @VAR;

SELECT * FROM VENTA

SELECT * FROM PRECIO_CLIENTE_MATERIAL














