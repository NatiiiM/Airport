use vuelos;
drop USER admin@localhost;
drop USER empleado@'%';
drop USER cliente@'%';
drop PROCEDURE realizarReservaSoloIda;
drop PROCEDURE  realizarReservaIdaVuelta;
drop DATABASE vuelos;

flush privileges;