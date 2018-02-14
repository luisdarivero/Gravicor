use gravicor

select convert(varchar, viaje.FECHA, 106) as Fecha,  CONVERT(VARCHAR, VIAJE.HORA, 108) as Hora from viaje




INSERT INTO VIAJE (IDUSUARIO,IDCAMION,FECHA,HORA,ESACTIVO)
VALUES (1,1, GETDATE(), GETdATE(), 1); 

SELECT convert(varchar, getdate(), 106)

Select CONVERT(VARCHAR, VIAJE.HORA, 108) as Hora from viaje where VIAJE.IDCAMION = 1

SELECT USUARIO.IDUSUARIO FROM USUARIO WHERE USUARIO.USERNAME = 'luisdaniel'

SELECT CONVERT(VARCHAR, GETDATE(), 108) as HoraActual

Select CONVERT(VARCHAR, VIAJE.HORA, 108) as Hora from viaje where VIAJE.IDCAMION = 1 and viaje.FECHA = convert(varchar, getDate(), 106)

Select  convert(varchar, viaje.FECHA, 106) as Fecha,  CONVERT(VARCHAR, VIAJE.HORA, 108) as Hora from VIAJE WHERE VIAJE.IDCAMION = 2 and VIAJE.ESACTIVO = 1