-- CREATE DATABASE travelmate_db;
-- (Ya creada manualmente en pgAdmin, no volver a ejecutar esta línea)

-- 1. Tabla Usuarios
CREATE TABLE usuarios(
    id BIGSERIAL PRIMARY KEY,  --Identificador único del usuario, autoincremental, tipo entero grande.
    nombre VARCHAR(100) NOT NULL,  --Nombre del usuario, hasta 100 caracteres, obligatorio.
    email VARCHAR(120) UNIQUE NOT NULL,  --Correo electrónico, hasta 120 caracteres, único y obligatorio.
    password_hash VARCHAR(255) NOT NULL,  --Hash de la contraseña, nunca en texto plano, obligatorio.
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  --Fecha y hora de creación, se asigna automáticamente.
);

-- 2. Tabla Viajes (CRUD 1): registra los viajes creados por cada usuario
CREATE TABLE viajes(
    id BIGSERIAL PRIMARY KEY,  --Identificador único autoincremental y clave primaria del viaje.
    titulo VARCHAR(150) NOT NULL,  --Título descriptivo del viaje, obligatorio, máx 150 caracteres.
    descripcion TEXT,  --Detalles o notas adicionales, opcional.
    fecha_inicio DATE,  --Fecha estimada o real de inicio, opcional.
    fecha_fin DATE,  --Fecha de finalización, opcional.
    usuario_id BIGINT NOT NULL,  --Identificador foráneo que vincula el viaje con su propietario.
    CONSTRAINT fk_viaje_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE  --Si se elimina el usuario, se borran sus viajes.
);

-- 3. Tabla Destinos (CRUD 2): lugares planificados dentro de un viaje
CREATE TABLE destinos(
    id BIGSERIAL PRIMARY KEY,  --Identificador único autoincremental del destino.
    nombre VARCHAR(150) NOT NULL,  --Nombre de la ciudad/lugar/atracción, obligatorio.
    latitud DOUBLE PRECISION,  --Coordenada de latitud, opcional.
    longitud DOUBLE PRECISION,  --Coordenada de longitud, opcional.
    fecha_visita DATE,  --Día programado para visitar el destino, opcional.
    viaje_id BIGINT NOT NULL,  --Identificador foráneo que asocia el destino con un viaje.
    CONSTRAINT fk_destino_viaje FOREIGN KEY (viaje_id)
        REFERENCES viajes(id)
        ON DELETE CASCADE  --Si se borra el viaje, se eliminan sus destinos.
);

-- 4. Tabla Actividades/Gastos (CRUD 3): control financiero y actividades por viaje
CREATE TABLE actividades_gastos(
    id BIGSERIAL PRIMARY KEY,  --Identificador único autoincremental (¡corregido, antes decía "if"!).
    concepto VARCHAR(200) NOT NULL,  --Descripción corta del gasto/actividad, obligatorio.
    monto DECIMAL(12,2) DEFAULT 0.00,  --Valor monetario, hasta 12 dígitos, 2 decimales (¡corregido, antes era VARCHAR!).
    categoria VARCHAR(50),  --Clasificación (TRANSPORTE, COMIDA, HOSPEDAJE, ACTIVIDAD), opcional.
    fecha DATE,  --Día en que ocurrió, opcional.
    viaje_id BIGINT NOT NULL,  --Identificador foráneo que relaciona con un viaje.
    CONSTRAINT fk_actividad_viaje FOREIGN KEY (viaje_id)
        REFERENCES viajes(id)
        ON DELETE CASCADE  --Si se elimina el viaje, se borran sus gastos/actividades.
);