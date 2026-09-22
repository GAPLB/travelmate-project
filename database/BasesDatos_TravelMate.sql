CREATE DATEBASE travelmate_db;

-- 1. Tabla Usuarios
CREATE TABLE usuarios( 
id BIGSERIAL PRIMARY KEY,  --Identificador unico del usuario, autoincremental, tipo entero grande.
nombre VARCHAR(100) NOT NULL,  --Nombre del usuario, con capacidad de texto de hasta 100 caracteres, es obligatorio. 
email VARCHAR(120) UNIQUE NOT NULL,   --Correo electronico, con capacidad de hasta 120 caracteres y es unico y obligatorio.
password_hash VARCHAR(255) NOT NULL,   --hash de la contraseña, nunca la contrasepña en texto plano, es obligatorio 
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  --La fecha y la hora de la creacion del registro, esta se asigna automaticamente.
);

--2. Tabla viajes (CRUD): registra los viajes creados por cada usuario
CREATE TABLE viajes(
id BIGSERIAL PRIMARY KEY, --Identificador unico autoincremental y clave primaria del viaje.
titulo VARCHAR(150) NOT NULL, --Título descriptivo del viaje, campo obligatorio, max 150 caracteres.
descripcion TEXT, --Detalles o notas adicionales del viaje, texto de longitud variable opcional.
fecha_inicio DATE, --Fecha estimada o real en la que inicia el viaje (año/mes/dia), este es opcional.
fecha_fin DATE, --Fecha en la que concluye el viaje, tambien es opcional.
usuario_id BIGINT NOT NULL, --Identificador foráneo que vincula el viaje con su propietario (tabla de usuarios).
CONSTRAINT fk_viaje_usuario FOREING KEY (usuario_id) --Definicion de la restriccion de la clave foraneo sobre "usuario_id".
	REFERENCES usuarios(id) --Apunta al campo "id" de la tabla usuarios.
	ON DELETE CASCADE --Si se elimina el usuario, se borran automaticamente todos sus viajes asociados.

);

--3. Tabla Destinos (CRUD 2): Lugares o paradas panificadas dentro de un viaje especifico.
CREATE TABLE destinos (
id BIGSERIAL PRIMARY KEY, --Identificador unico autoincremental y clave primaria del destino.
nombre VARCHAR(150) NOT NULL, --nombre de la ciudad, atraccion o lugar a visitar, es obligatorio, max 150 caracteres.
latitud DOUBLE PRECISION, --Coordenada geografica de la latitud en punto flotante de doble precision, es opcional.
longitud DOUBLE PRECISION, --Coordenada geografica de longitud en punto flotante de doble presicion, es opcional.
fecha_visita DATE, --Dia programado para la visita al destino(año/mes/fecha), es opcional.
viaje_id BIGINT NOT NULL, --Identificador foraneo que asocia el destino con un vaije en particular.
CONSTRAINT fk_destino_viaje FOREING KEY (viaje_id) --Definición de la restriccion de la clave foranea sobre "viaje_id".
	REFERENCES viajes(id) --Apunta al campo "id" de la tabla "viajes"
	ON DELETE CASCADE --Si se borra el viaje, se eliminan en cascada todos sus destino.
);

--4. Tabla de Actividades/ Gastos(CRUD 3): control financiero y actividades agendadas por viaje.
CREATE TABLE actividades_gastos (
if BIGSERIAL PRIMARY KEY, --Identificador único autoincremental y clave primaria del gasto/actividad.
concepto VARCHAR(200) NOT NULL, --Descripción corta del gasto o la actividad realizada, obligatorio, máx. 200 caracteres.
monto VARCHAR(12, 2) DEFAULT 0.00, --Valor monetario (hasta 12 dígitos, 2 decimales); por defecto 0.00.
categoria VARCHAR(50), --Clasificación del gasto (ej. TRANSPORTE, COMIDA, HOSPEDAJE, ACTIVIDAD), opcional.
fecha DATE, --Día en que ocurrió el gasto o se realizó la actividad, opcional.
viaje_id BIGINT NOT NULL, --Identificador foráneo que relaciona el gasto con un viaje específico.
CONSTRAINT fk_actividad_viaje FOREING KEY (viaje_id) --Definición de la restricción de clave foránea sobre 'viaje_id'.
	REFERENCES viajes(id) --Apunta al campo 'id' de la tabla 'viajes'.
	ON DELETE CASCADE --Si se elimina el viaje, se borran todos sus registros de gastos y actividades.
);