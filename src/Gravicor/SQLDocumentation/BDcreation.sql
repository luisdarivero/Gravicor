CREATE DATABASE GRAVICOR;

USE GRAVICOR

CREATE TABLE TIPOCAMION(
IDTIPOCAMION INT NOT NULL IDENTITY PRIMARY KEY,
DESCRIPCION VARCHAR(15) NOT NULL,
CAPACIDAD INT  NOT NULL,
TONELADAS  INT NOT NULL
)

CREATE TABLE CAMION(
IDCAMION INT NOT NULL PRIMARY KEY,
OPERADOR VARCHAR(50) NOT NULL,
IDTIPOCAMION INT NOT NULL REFERENCES TIPOCAMION(IDTIPOCAMION),
COLOR VARCHAR(15) NOT NULL
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
	CONFIGURACIONCUENTAS BIT NOT NULL
)

CREATE TABLE VIAJE(
IDVIAJE INT NOT NULL IDENTITY PRIMARY KEY,
IDUSUARIO INTEGER NOT NULL REFERENCES USUARIO(IDUSUARIO),
IDCAMION INTEGER NOT NULL REFERENCES CAMION(IDCAMION),
FECHA DATE NOT NULL,
HORA TIME NOT NULL,
ESACTIVO BIT NULL
)
