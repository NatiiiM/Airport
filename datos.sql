#-------------------------------------------------------------------------
# Carga de datos de Prueba

# ubicaciones (pais, estado, ciudad, huso)
INSERT INTO ubicaciones VALUES ("Argentina", "Buenos Aires", "Ezeiza", -3);
INSERT INTO ubicaciones VALUES ("Brasil", "Rio Grande do Sul", "Porto Alegre", -3);
INSERT INTO ubicaciones VALUES ("Chile", "Pudahuel", "Santiago de Chile", -4);
INSERT INTO ubicaciones VALUES ("Estados Unidos", "California", "San Francisco", -8);
INSERT INTO ubicaciones VALUES ("México", "Estado de México", "Toluca", -6);
INSERT INTO ubicaciones VALUES ("Espana", "Comunidad de Madrid", "Madrid", 1);
        
# aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad)
INSERT INTO aeropuertos VALUES ("SAEZAR", "Ministro Pistarini", "+54 (11) 5480 2500", "Autopista Tte. Gral. Ricchieri Km 33,5", "Argentina", "Buenos Aires", "Ezeiza");
INSERT INTO aeropuertos VALUES ("SBPABR", "Salgado Filho", "55 (0)51 33582000", "Av. Severo Dulius 90.010", "Brasil", "Rio Grande do Sul", "Porto Alegre");
INSERT INTO aeropuertos VALUES ("SCELCH", "Comodoro Arturo Merino Benítez", "+56 (0)2 690 1900", "Av. Armando Cortínez Norte 456", "Chile", "Pudahuel", "Santiago de Chile");
INSERT INTO aeropuertos VALUES ("KSFOES", "Aeropuerto Internacional San Francisco", "+1(0)650 821 5000", "PO Box 8097", "Estados Unidos", "California", "San Francisco");
INSERT INTO aeropuertos VALUES ("MMTOME", "Lic. Adolfo López Mateos", "+52 (01)722 2730982", "San Pedro Totoltepec. 50226", "México", "Estado de México", "Toluca");
INSERT INTO aeropuertos VALUES ("LEMDES", "Aeropuerto de Madrid-Barajas", "+34 902 404 704", "Avenida de la Hispanidad 789", "Espana", "Comunidad de Madrid", "Madrid");


# vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada)
INSERT INTO vuelos_programados VALUES ("A00101", "SAEZAR", "SBPABR");
INSERT INTO vuelos_programados VALUES ("100102", "SAEZAR", "LEMDES");
INSERT INTO vuelos_programados VALUES ("100103", "LEMDES", "SBPABR");
INSERT INTO vuelos_programados VALUES ("100104", "LEMDES", "SAEZAR");
INSERT INTO vuelos_programados VALUES ("100105", "MMTOME", "KSFOES");
INSERT INTO vuelos_programados VALUES ("100106", "MMTOME", "LEMDES");
INSERT INTO vuelos_programados VALUES ("100107", "KSFOES", "SAEZAR");
INSERT INTO vuelos_programados VALUES ("100108", "KSFOES", "SCELCH");
INSERT INTO vuelos_programados VALUES ("100109", "SBPABR", "MMTOME");
INSERT INTO vuelos_programados VALUES ("100110", "SBPABR", "SAEZAR");
INSERT INTO vuelos_programados VALUES ("100111", "SCELCH", "SAEZAR");
INSERT INTO vuelos_programados VALUES ("100112", "SCELCH", "LEMDES");
#caso de un avion con las reservas ya casi ocupadas
INSERT INTO vuelos_programados VALUES ("100113", "SAEZAR", "SBPABR");
INSERT INTO vuelos_programados VALUES ("100114", "SBPABR", "SAEZAR");
INSERT INTO vuelos_programados VALUES ("100115", "SAEZAR", "SBPABR");

# modelos_avion (modelo, fabricante, cabinas, cant_asientos)
INSERT INTO modelos_avion VALUES("340-300", "Airbus", 3, 280);
INSERT INTO modelos_avion VALUES("340-200", "Airbus", 3, 249);
INSERT INTO modelos_avion VALUES("737-700", "Boeing", 3, 128);
INSERT INTO modelos_avion VALUES("737-800", "Boeing", 3, 170);
INSERT INTO modelos_avion VALUES("Embraer 190", "Embraer", 3, 96);
INSERT INTO modelos_avion VALUES("787-8", "Boeing", 4, 242);
INSERT INTO modelos_avion VALUES("666-66", "Embraer", 3, 6);
INSERT INTO modelos_avion VALUES("Boeing 747", "Boeing", 3, 6);
INSERT INTO modelos_avion VALUES("Airbus A320", "Airbus", 3, 6);


# salidas (vuelo, dia, hora_sale, hora_llega, modelo_avion)
INSERT INTO salidas VALUES ("A00101", 'Do', '20:00', '21:12', "340-200");
INSERT INTO salidas VALUES ("100102", 'Lu', '08:00', '21:46', "787-8");
INSERT INTO salidas VALUES ("100103", 'Ma', '00:00', '12:46', "340-300");
INSERT INTO salidas VALUES ("100104", 'Mi', '21:00', '10:46', "787-8");
INSERT INTO salidas VALUES ("100105", 'Ju', '17:30', '21:00', "737-700");
INSERT INTO salidas VALUES ("100106", 'Vi', '05:00', '18:16', "Embraer 190");
INSERT INTO salidas VALUES ("100107", 'Sa', '07:30', '21:37', "737-800");
INSERT INTO salidas VALUES ("100108", 'Do', '09:00', '23:07', "737-700");
INSERT INTO salidas VALUES ("100109", 'Lu', '12:30', '14:00', "737-800");
INSERT INTO salidas VALUES ("100110", 'Ma', '21:00', '23:42', "787-8");
INSERT INTO salidas VALUES ("100111", 'Mi', '13:00', '14:37', "Embraer 190");
INSERT INTO salidas VALUES ("100112", 'Ju', '00:00', '14:29', "340-300");
INSERT INTO salidas VALUES ("100113", 'Mi', '13:00', '14:37', "Boeing 747");
INSERT INTO salidas VALUES ("100114", 'Ju', '00:00', '14:29', "Airbus A320");
INSERT INTO salidas VALUES ("100115", 'Mi', '13:00', '14:37', "Boeing 747");

# instancias_vuelo (vuelo, fecha, dia, estado)
INSERT INTO instancias_vuelo VALUES ("A00101", '2020/01/30', 'Do', "demorado");
INSERT INTO instancias_vuelo VALUES ("100102", '2020/01/23', 'Lu', "a tiempo");
INSERT INTO instancias_vuelo VALUES ("100103", '2019/12/02', 'Ma', "cancelado");
INSERT INTO instancias_vuelo VALUES ("100104", '2020/01/25', 'Mi', "a tiempo");
INSERT INTO instancias_vuelo VALUES ("100105", '2019/12/04', 'Ju', "cancelado");
INSERT INTO instancias_vuelo VALUES ("100106", '2020/01/27', 'Vi', "a tiempo");
INSERT INTO instancias_vuelo VALUES ("100107", '2019/12/06', 'Sa', "a tiempo");
INSERT INTO instancias_vuelo VALUES ("100108", '2020/01/30', 'Do', "a tiempo");
INSERT INTO instancias_vuelo VALUES ("100109", '2020/01/23', 'Lu', "demorado");
INSERT INTO instancias_vuelo VALUES ("100110", '2019/12/02', 'Ma', "a tiempo");
INSERT INTO instancias_vuelo VALUES ("100111", '2020/01/25', 'Mi', "cancelado");
INSERT INTO instancias_vuelo VALUES ("100112", '2019/12/04', 'Ju', "a tiempo");
INSERT INTO instancias_vuelo VALUES ("100113", '2020/01/27', 'Mi', "a tiempo");
INSERT INTO instancias_vuelo VALUES ("100114", '2020/01/28', 'Ju', "a tiempo");
INSERT INTO instancias_vuelo VALUES ("100115", '2020/01/27', 'Mi', "a tiempo");

# clases (nombre, porcentaje)
INSERT INTO clases VALUES ("Economy", 0.20);
INSERT INTO clases VALUES ("Premium Economy", 0.15);
INSERT INTO clases VALUES ("Premium Business", 0.10);


# comodidades (codigo, descripcion)
INSERT INTO comodidades VALUES (101, "Televisión");
INSERT INTO comodidades VALUES (102, "Música");
INSERT INTO comodidades VALUES (103, "Comida");
INSERT INTO comodidades VALUES (104, "Asientos reclinables");
INSERT INTO comodidades VALUES (201, "Cabina exclusiva con atención personalizada para un publico reducido");
INSERT INTO comodidades VALUES (202, "Entrenteción exclusiva (conexión universal para cargar la batería de  computadores, amplia selección de revistas y diarios)");
INSERT INTO comodidades VALUES (203, "Internet");
INSERT INTO comodidades VALUES (301, "Asientos cama");
INSERT INTO comodidades VALUES (302, "Comida especial: menú especialmente elaborado por reconocido chefs");

# empleados (legago, password,  doc_tipo,  doc_nro,  apellido,  nombre,  direccion,  telefono)
INSERT INTO empleados VALUES (34095, md5("AS384"), "DNI", 27948384, "Alberti", "Santiago", "Necochea 392", "154653278");
INSERT INTO empleados VALUES (36128, md5("FL917"), "DNI", 30627917, "Franco", "Lucas", "Chiclana 1853", "4543127");
INSERT INTO empleados VALUES (29751, md5("PD487"), "DNI", 25398487, "Pardo", "Javier", "Cacique Venancio 537", "155672938");
INSERT INTO empleados VALUES (33954, md5("AN142"), "DNI", 29852142, "Aquino", "Noelia", "Undiano 1212", "154128350");
INSERT INTO empleados VALUES (25198, md5("RA123"), "DNI", 25582123, "Reyes", "Agostina", "Brickman 719", "4543780");
INSERT INTO empleados VALUES (35327, md5("BM348"), "DNI", 31791348, "Barbero", "Macarena", "Castelli 1834", "155888412");
INSERT INTO empleados VALUES (23847, md5("GC129"), "LE", 3487129, "Gaitan", "Cristian", "Soler 500", "4823765");
INSERT INTO empleados VALUES (20463, md5("GC521"), "LC", 3487521, "Gomez", "Carolina", "Darreguiera 1734", "4545082");
INSERT INTO empleados VALUES (28012, md5("OA111"), "LE", 4782111, "Ocampos", "Alan", "Bélgica 943", "154839001");
INSERT INTO empleados VALUES (37621, md5("RM977"), "LC", 4321977, "Roque", "Milena", "Córdoba 55", "155764327");
INSERT INTO empleados VALUES (27825, md5("PM318"), "LC", 5982318, "Perez", "Micaela", "Alsina 2001", "154987654");
INSERT INTO empleados VALUES (23175, md5("GG556"), "LE", 5234556, "Garcia", "Gonzalo", "Sarmiento 1467", "155817862");

# pasajeros (doc_tipo, doc_nro, apellido, nombre, direccion, telefono, nacionalidad)
INSERT INTO pasajeros VALUES ("DNI", 34587563, "Prieto", "Florencia", "Soler 1284", "4818726", "Argentina");
INSERT INTO pasajeros VALUES ("DNI", 15092834, "Darin", "Ricardo", "Uruguay 124", "4543183", "Argentina");
INSERT INTO pasajeros VALUES ("DNI", 20472304, "Ortega" , "Ariel", "Casanova 230", "154124815", "Argentina");
INSERT INTO pasajeros VALUES ("DNI", 16239484, "Palacios", "Marcelo", "Caronti 1204", "155957194", "Argentina");
INSERT INTO pasajeros VALUES ("LC", 5234909, "Legrand", "Mirta", "Zapiola 2341", "4823462", "Argentina");
INSERT INTO pasajeros VALUES ("LC", 3858173, "Lagos", "Virginia", "Alsina 454", "155715832", "Argentina");
INSERT INTO pasajeros VALUES ("LC", 3244858, "Casan", "Moria", "Corrientes 463", "4550333", "Argentina");
INSERT INTO pasajeros VALUES ("LE", 4437326, "Ricardo", "Julio", "Mitre 873", "154123802", "Argentina");
INSERT INTO pasajeros VALUES ("LE", 4213135, "Pagani",  "Horacio", "Estomba 1204", "4807862", "Argentina");
INSERT INTO pasajeros VALUES ("LE", 5898523, "Apo", "Alejandro", "Florida 789", "155218320", "Argentina");
INSERT INTO pasajeros VALUES ("CI", 134567809, "Salas", "Marcelo", "Oriental 135", "4810981", "Chile");
INSERT INTO pasajeros VALUES ("CI", 146349234, "Gonzales", "Fernando", "El Valle 1432", "152874365", "Chile");
INSERT INTO pasajeros VALUES ("CI", 123345664, "Vicuna", "Benjamin", "Valle Hermoso 17", "3498274", "Chile");
INSERT INTO pasajeros VALUES ("CC", 456756723, "Gutierrez", "Teofilo", "Calle 7A", "4188726", "Colombia");
INSERT INTO pasajeros VALUES ("CC", 490893284, "Falcao", "Radamel", "Calle 10S", "154435656", "Colombia");
INSERT INTO pasajeros VALUES ("CC", 492348202, "Vergara", "Sofía", "Calle 4S", "4792372", "Colombia");
INSERT INTO pasajeros VALUES ("RG", 629875357, "Alves", "Dani", "R. Beck 123", "5682047", "Brasil");
INSERT INTO pasajeros VALUES ("RG", 680293849, "Carlos", "Roberto", "R. Nunes 874", "5689721", "Brasil");
INSERT INTO pasajeros VALUES ("RG", 690009284, "Ceni", "Rogerio", "R. Costa 989", "5891265", "Brasil");
INSERT INTO pasajeros VALUES ("DNI", 524234423, "Cruz", "Penelope", "Calle Segovia 324", "6352124", "Espana");
INSERT INTO pasajeros VALUES ("DNI", 598972374, "Gonzales", "Raul", "Calle Santa Maria 23", "6900821", "Espana");
INSERT INTO pasajeros VALUES ("DNI", 592747551, "Casillas", "Iker", "Calle de Cervantes 867", "6781222", "Espana");
INSERT INTO pasajeros VALUES ("CURP", 101230596, "Hernandez", "Javier", "Cólica 98", "7903123", "México");
INSERT INTO pasajeros VALUES ("CURP", 103475657, "Dos Santos", "Giovani", "Esperanza 123", "7123217", "México");
INSERT INTO pasajeros VALUES ("CURP", 107657453, "Luna", "Diego", "Plan de Ayala 754", "7111848", "México");
INSERT INTO pasajeros VALUES ("S/D", 823456789, "Cruise", "Tom", "OAK St 563", "345897","Estados Unidos");
INSERT INTO pasajeros VALUES ("S/D", 987654432, "Foster", "Jodie", "Waller St 821", "435797", "Estados Unidos");
INSERT INTO pasajeros VALUES ("S/D", 723784656, "Xavier", "Charles", "14th St 14", "348792", "Estados Unidos");
INSERT INTO pasajeros VALUES ("S/D", 555555555, "Parker", "Peter", "Valencia St 729", "438762","Estados Unidos");
INSERT INTO pasajeros VALUES ("S/D", 737373737, "Grey", "Jean", "Folsom St 642", "389057","Estados Unidos");

# reservas (numero, fecha, vencimiento, estado, doc_tipo, doc_nro, legajo)
INSERT INTO reservas VALUES (1234, '2020/01/17', '2020/01/21', "pagada", "DNI", 34587563 , 34095);
INSERT INTO reservas VALUES (1235, '2020/01/15', '2020/01/21', "confirmada", "DNI", 15092834, 34095);
INSERT INTO reservas VALUES (1236, '2019/12/23', '2020/01/21', "en espera", "DNI", 20472304, 36128);
INSERT INTO reservas VALUES (1237, '2020/01/15', '2020/01/21', "confirmada", "DNI", 16239484, 36128);
INSERT INTO reservas VALUES (1238, '2019/12/11', '2020/01/30', "pagada", "LC", 5234909, 29751);
INSERT INTO reservas VALUES (1239, '2020/01/14', '2020/01/30', "pagada", "LC", 3858173, 29751);
INSERT INTO reservas VALUES (1240, '2020/01/14', '2020/01/30', "en espera", "LC", 3244858, 33954);
INSERT INTO reservas VALUES (1241, '2020/01/17', '2020/01/30', "confirmada", "LE", 4437326, 33954);
INSERT INTO reservas VALUES (1242, '2020/01/16', '2020/01/23', "confirmada", "LE", 4213135, 25198);
INSERT INTO reservas VALUES (1243, '2020/01/19', '2020/01/23', "en espera", "LE", 5898523, 25198);
INSERT INTO reservas VALUES (1244, '2020/01/20', '2020/01/23', "pagada", "CI", 134567809, 35327);
INSERT INTO reservas VALUES (1245, '2019/12/30', '2020/01/23', "pagada", "CI", 146349234, 35327);
INSERT INTO reservas VALUES (1246, '2020/01/11', '2019/12/02', "pagada", "CI", 123345664, 23847);
INSERT INTO reservas VALUES (1247, '2020/01/12', '2019/12/02', "pagada", "CC", 456756723, 23847);
INSERT INTO reservas VALUES (1248, '2020/01/14', '2019/12/02', "confirmada", "CC", 490893284, 20463);
INSERT INTO reservas VALUES (1249, '2019/12/31', '2019/12/02', "confirmada", "CC", 492348202, 20463);
INSERT INTO reservas VALUES (1250, '2020/01/04', '2020/01/25', "pagada", "RG", 629875357, 28012);
INSERT INTO reservas VALUES (1251, '2019/12/24', '2020/01/25', "en espera", "RG", 680293849, 28012);
INSERT INTO reservas VALUES (1252, '2020/01/01', '2020/01/25', "pagada", "RG", 690009284, 37621);
INSERT INTO reservas VALUES (1253, '2019/12/24', '2020/01/25', "en espera", "DNI", 524234423, 37621);
INSERT INTO reservas VALUES (1254, '2020/01/06', '2019/12/04', "en espera", "DNI", 598972374, 27825);
INSERT INTO reservas VALUES (1255, '2019/12/24', '2019/12/04', "confirmada", "DNI", 592747551, 27825);
INSERT INTO reservas VALUES (1256, '2020/01/08', '2019/12/04', "pagada", "CURP", 101230596, 23175);
INSERT INTO reservas VALUES (1257, '2020/01/14', '2019/12/04', "pagada", "CURP", 103475657, 34095);
INSERT INTO reservas VALUES (1258, '2019/12/29', '2019/12/04', "confirmada", "CURP", 107657453, 36128);
INSERT INTO reservas VALUES (1259, '2020/01/15', '2020/01/27', "en espera", "S/D", 823456789, 29751);
INSERT INTO reservas VALUES (1260, '2019/12/09', '2020/01/27', "pagada", "S/D", 987654432, 33954);
INSERT INTO reservas VALUES (1261, '2020/01/11', '2020/01/27', "pagada", "S/D", 723784656, 25198);
INSERT INTO reservas VALUES (1262, '2019/12/17', '2020/01/27', "en espera", "S/D", 555555555, 35327);
INSERT INTO reservas VALUES (1263, '2020/01/19', '2020/01/27', "confirmada", "S/D", 737373737, 23847);
INSERT INTO reservas VALUES (1264, '2019/12/09', '2020/01/27', "pagada", "S/D", 987654432, 33954);
INSERT INTO reservas VALUES (1265, '2020/01/11', '2020/01/27', "pagada", "S/D", 723784656, 25198);
INSERT INTO reservas VALUES (1266, '2019/12/17', '2020/01/27', "en espera", "S/D", 555555555, 35327);
INSERT INTO reservas VALUES (1267, '2020/01/19', '2020/01/27', "confirmada", "S/D", 737373737, 23847);

# asientos_reservados(vuelo, fecha,clase, cantidad)
INSERT INTO asientos_reservados VALUES ("A00101", '2020/01/30', "Economy", 1);
INSERT INTO asientos_reservados VALUES ("A00101", '2020/01/30', "Premium Economy", 1);
INSERT INTO asientos_reservados VALUES ("100102", '2020/01/23', "Economy", 0);
INSERT INTO asientos_reservados VALUES ("100102", '2020/01/23', "Premium Economy", 0);
INSERT INTO asientos_reservados VALUES ("100102", '2020/01/23', "Premium Business", 2);
INSERT INTO asientos_reservados VALUES ("100103", '2019/12/02', "Economy", 1);
INSERT INTO asientos_reservados VALUES ("100103", '2019/12/02', "Premium Economy", 1);
INSERT INTO asientos_reservados VALUES ("100104", '2020/01/25', "Economy", 0);
INSERT INTO asientos_reservados VALUES ("100104", '2020/01/25', "Premium Economy", 1);
INSERT INTO asientos_reservados VALUES ("100104", '2020/01/25', "Premium Business", 1);
INSERT INTO asientos_reservados VALUES ("100105", '2019/12/04', "Economy", 2);
INSERT INTO asientos_reservados VALUES ("100105", '2019/12/04', "Premium Economy", 0);
INSERT INTO asientos_reservados VALUES ("100106", '2020/01/27', "Economy", 2);
INSERT INTO asientos_reservados VALUES ("100106", '2020/01/27', "Premium Economy", 3);
INSERT INTO asientos_reservados VALUES ("100107", '2019/12/06', "Economy", 4);
INSERT INTO asientos_reservados VALUES ("100107", '2019/12/06', "Premium Economy", 1);
INSERT INTO asientos_reservados VALUES ("100108", '2020/01/30', "Economy", 2);
INSERT INTO asientos_reservados VALUES ("100108", '2020/01/30', "Premium Economy", 0);
INSERT INTO asientos_reservados VALUES ("100109", '2020/01/23', "Economy", 2);
INSERT INTO asientos_reservados VALUES ("100109", '2020/01/23', "Premium Economy", 0);
INSERT INTO asientos_reservados VALUES ("100110", '2019/12/02', "Economy", 1);
INSERT INTO asientos_reservados VALUES ("100110", '2019/12/02', "Premium Economy", 0);
INSERT INTO asientos_reservados VALUES ("100110", '2019/12/02', "Premium Business", 1);
INSERT INTO asientos_reservados VALUES ("100111", '2020/01/25', "Economy", 1);
INSERT INTO asientos_reservados VALUES ("100111", '2020/01/25', "Premium Economy", 1);
INSERT INTO asientos_reservados VALUES ("100112", '2019/12/04', "Economy", 2);
INSERT INTO asientos_reservados VALUES ("100112", '2019/12/04', "Premium Economy", 0);

# posee (clase, comodidad)
INSERT INTO posee VALUES ("Economy", 101);
INSERT INTO posee VALUES ("Economy", 102);
INSERT INTO posee VALUES ("Economy", 103);
INSERT INTO posee VALUES ("Economy", 104);
INSERT INTO posee VALUES ("Premium Economy", 101);
INSERT INTO posee VALUES ("Premium Economy", 102);
INSERT INTO posee VALUES ("Premium Economy", 103);
INSERT INTO posee VALUES ("Premium Economy", 104);
INSERT INTO posee VALUES ("Premium Economy", 201);
INSERT INTO posee VALUES ("Premium Economy", 202);
INSERT INTO posee VALUES ("Premium Economy", 203);
INSERT INTO posee VALUES ("Premium Business", 101);
INSERT INTO posee VALUES ("Premium Business", 102);
INSERT INTO posee VALUES ("Premium Business", 103);
INSERT INTO posee VALUES ("Premium Business", 201);
INSERT INTO posee VALUES ("Premium Business", 202);
INSERT INTO posee VALUES ("Premium Business", 203);
INSERT INTO posee VALUES ("Premium Business", 301);
INSERT INTO posee VALUES ("Premium Business", 302);


# brinda (vuelo, dia, clase, precio, cant_asientos)
INSERT INTO brinda VALUES ("A00101", 'Do', "Economy", 1089.00, 217);
INSERT INTO brinda VALUES ("A00101", 'Do', "Premium Economy", 1521.00, 32);
INSERT INTO brinda VALUES ("100102", 'Lu', "Economy", 4090.00, 200);
INSERT INTO brinda VALUES ("100102", 'Lu', "Premium Economy", 4969.00, 30);
INSERT INTO brinda VALUES ("100102", 'Lu', "Premium Business", 5490.00, 12);
INSERT INTO brinda VALUES ("100103", 'Ma', "Economy", 9197.00, 248);
INSERT INTO brinda VALUES ("100103", 'Ma', "Premium Economy", 10889.00, 32);
INSERT INTO brinda VALUES ("100104", 'Mi', "Economy", 5033.00, 200);
INSERT INTO brinda VALUES ("100104", 'Mi', "Premium Economy", 5569.00, 30);
INSERT INTO brinda VALUES ("100104", 'Mi', "Premium Business", 6490.00, 12);
INSERT INTO brinda VALUES ("100105", 'Ju', "Economy", 1485.00, 120);
INSERT INTO brinda VALUES ("100105", 'Ju', "Premium Economy", 2180.00, 8);
INSERT INTO brinda VALUES ("100106", 'Vi', "Economy", 4495.00, 88);
INSERT INTO brinda VALUES ("100106", 'Vi', "Premium Economy", 5490.00, 8);
INSERT INTO brinda VALUES ("100107", 'Sa', "Economy", 9438.50, 162);
INSERT INTO brinda VALUES ("100107", 'Sa', "Premium Economy", 11090.00, 8);
INSERT INTO brinda VALUES ("100108", 'Do', "Economy", 14585.00, 120);
INSERT INTO brinda VALUES ("100108", 'Do', "Premium Economy", 16800.00, 8);
INSERT INTO brinda VALUES ("100109", 'Lu', "Economy", 5438.10, 162);
INSERT INTO brinda VALUES ("100109", 'Lu', "Premium Economy", 7080.30, 8);
INSERT INTO brinda VALUES ("100110", 'Ma', "Economy", 1411.00, 200);
INSERT INTO brinda VALUES ("100110", 'Ma', "Premium Economy", 2169.00, 30);
INSERT INTO brinda VALUES ("100110", 'Ma', "Premium Business", 2790.00, 12);
INSERT INTO brinda VALUES ("100111", 'Mi', "Economy", 1953.00, 88);
INSERT INTO brinda VALUES ("100111", 'Mi', "Premium Economy", 2490.00, 8);
INSERT INTO brinda VALUES ("100112", 'Ju', "Economy", 6286.95, 248);
INSERT INTO brinda VALUES ("100112", 'Ju', "Premium Economy", 6990.00, 32);
INSERT INTO brinda VALUES ("100113", 'Mi', "Economy", 1089.00, 3);
INSERT INTO brinda VALUES ("100113", 'Mi', "Premium Economy", 1521.00, 3);
INSERT INTO brinda VALUES ("100114", 'Ju', "Economy", 4090.00, 3);
INSERT INTO brinda VALUES ("100114", 'Ju', "Premium Economy", 4969.00, 3);
INSERT INTO brinda VALUES ("100115", 'Mi', "Economy", 1089.00, 3);
INSERT INTO brinda VALUES ("100115", 'Mi', "Premium Economy", 1521.00, 3);

# reserva_vuelo_clase (numero, vuelo, fecha_vuelo, clase)
INSERT INTO reserva_vuelo_clase VALUES (1234, "A00101", '2020/01/30', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1235, "A00101", '2020/01/30', "Premium Economy");
INSERT INTO reserva_vuelo_clase VALUES (1236, "100108", '2020/01/30', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1237, "100108", '2020/01/30', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1238, "100102", '2020/01/23', "Premium Business");
INSERT INTO reserva_vuelo_clase VALUES (1239, "100102", '2020/01/23', "Premium Business");
INSERT INTO reserva_vuelo_clase VALUES (1240, "100109", '2020/01/23', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1241, "100109", '2020/01/23', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1242, "100103", '2019/12/02', "Premium Economy");
INSERT INTO reserva_vuelo_clase VALUES (1243, "100103", '2019/12/02', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1244, "100110", '2019/12/02', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1245, "100110", '2019/12/02', "Premium Business");
INSERT INTO reserva_vuelo_clase VALUES (1246, "100104", '2020/01/25', "Premium Economy");
INSERT INTO reserva_vuelo_clase VALUES (1247, "100104", '2020/01/25', "Premium Business");
INSERT INTO reserva_vuelo_clase VALUES (1248, "100111", '2020/01/25', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1249, "100111", '2020/01/25', "Premium Business");
INSERT INTO reserva_vuelo_clase VALUES (1250, "100105", '2019/12/04', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1251, "100105", '2019/12/04', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1252, "100112", '2019/12/04', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1253, "100112", '2019/12/04', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1254, "100106", '2020/01/27', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1255, "100106", '2020/01/27', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1256, "100106", '2020/01/27', "Premium Economy");
INSERT INTO reserva_vuelo_clase VALUES (1257, "100106", '2020/01/27', "Premium Economy");
INSERT INTO reserva_vuelo_clase VALUES (1258, "100106", '2020/01/27', "Premium Economy");
INSERT INTO reserva_vuelo_clase VALUES (1259, "100107", '2019/12/06', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1260, "100107", '2019/12/06', "Premium Economy");
INSERT INTO reserva_vuelo_clase VALUES (1261, "100107", '2019/12/06', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1262, "100107", '2019/12/06', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1263, "100107", '2019/12/06', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1264, "100113", '2020/01/27', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1265, "100113", '2020/01/27', "Economy");
INSERT INTO reserva_vuelo_clase VALUES (1266, "100113", '2020/01/27', "Premium Economy");
INSERT INTO reserva_vuelo_clase VALUES (1267, "100113", '2020/01/27', "Premium Economy");















