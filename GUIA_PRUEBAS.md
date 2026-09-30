# Guía de Pruebas - TravelMate App Android

Esta guía explica paso a paso cómo probar la aplicación desde un celular Android para verificar que los registros de usuarios, viajes, destinos y gastos funcionen correctamente.

---

## Requisitos Previos

1. **Backend Spring Boot corriendo** en tu PC (puerto 8080)
2. **Base de datos PostgreSQL** configurada y funcionando
3. **App Android** instalada en el celular o emulador
4. **Celular y PC en la misma red WiFi** (si usas dispositivo físico)

---

## Paso 1: Configurar la URL del Servidor

### Si usas el Emulador de Android Studio:
La URL ya está configurada en `RetrofitClient.java`:
```java
private static final String BASE_URL = "http://10.0.2.2:8080/";
```
El emulador usa `10.0.2.2` para acceder al localhost de tu PC. **No necesitas cambiar nada.**

### Si usas un Celular Físico:
1. Abre `RetrofitClient.java` en Android Studio
2. Cambia la URL por la IP de tu PC en la red local:
```java
private static final String BASE_URL = "http://192.168.1.XXX:8080/";
```
3. Para encontrar tu PC en Windows: abre CMD y escribe `ipconfig`, busca la dirección IPv4

---

## Paso 2: Iniciar el Backend Spring Boot

1. Abre una terminal en la carpeta `travelmate-backend`
2. Ejecuta:
```bash
./mvnw spring-boot:run
```
3. Verifica que aparezca el mensaje: `Started TravelmateBackendApplication`
4. **No cierres esta terminal** mientras hagas las pruebas

---

## Paso 3: Instalar y Abrir la App en el Celular

1. Abre Android Studio
2. Conecta el celular por USB (con depuración USB activada) o inicia el emulador
3. Presiona el botón **Run** (▶️) para compilar e instalar la app
4. Abre la app **TravelMate** en el celular

---

## Paso 4: Probar el Registro de Usuario

1. En la app, toca el ícono **Cuenta** (último ícono de la barra inferior)
2. Toca el texto **"¿No tienes cuenta? Regístrate aquí"**
3. Completa los campos:
   - **Nombre completo**: Ej: "Juan Pérez"
   - **Correo electrónico**: Ej: "juan@test.com"
   - **Contraseña**: Ej: "password123"
4. Toca el botón **"Registrarse"**
5. **Resultado esperado**: Debe aparecer el mensaje *"¡Usuario registrado con éxito! Ahora inicia sesión."*

### Verificación en la Base de Datos:
Abre tu cliente PostgreSQL (pgAdmin, DBeaver, etc.) y ejecuta:
```sql
SELECT * FROM usuarios;
```
Deberías ver el usuario registrado con la contraseña encriptada (hash BCrypt).

---

## Paso 5: Probar el Login de Usuario

1. En la pantalla de cuenta, asegúrate de estar en modo **"Iniciar Sesión"**
2. Ingresa el email y contraseña que registraste antes
3. Toca el botón **"Iniciar Sesión"**
4. **Resultado esperado**: Debe aparecer *"¡Bienvenido, [tu nombre]!"*

### Probar credenciales incorrectas:
1. Ingresa un email o contraseña equivocado
2. Toca **"Iniciar Sesión"**
3. **Resultado esperado**: Debe aparecer *"Credenciales inválidas. Verifica tu email y contraseña."*

---

## Paso 6: Probar el Registro de Viajes

1. Toca el ícono **Viajes** en la barra inferior
2. Toca el botón flotante **"+"** (esquina inferior derecha)
3. Completa el formulario:
   - **Título del viaje**: Ej: "Vacaciones Cartagena"
   - **Descripción**: Ej: "Viaje familiar a la costa"
   - **Fecha de Inicio**: Ej: "2026-10-15"
   - **Fecha de Fin**: Ej: "2026-10-22"
4. Toca **"Guardar Viaje en Servidor"**
5. **Resultado esperado**: Debe aparecer *"¡Viaje 'Vacaciones Cartagena' registrado con éxito! (ID: 1)"*

### Verificación en la Base de Datos:
```sql
SELECT * FROM viajes;
```
Deberías ver el viaje registrado con su ID, título, fechas y el ID del usuario propietario.

---

## Paso 7: Verificar la Lista de Viajes

1. Después de guardar un viaje, la app regresa automáticamente a la lista
2. **Resultado esperado**: Deberías ver el viaje que acabas de crear en la lista
3. Crea **2 o 3 viajes más** para verificar que todos se muestren correctamente

---

## Paso 8: Probar el Registro de Gastos/Actividades

1. Toca el ícono **Gastos** en la barra inferior
2. Selecciona una **categoría** del menú desplegable (ej: "COMIDA")
3. Completa los campos:
   - **Concepto**: Ej: "Almuerzo en el mercado"
   - **Monto**: Ej: "25000"
   - **Fecha**: Ej: "2026-10-16"
4. Toca **"Guardar Gasto"**
5. **Resultado esperado**: Debe aparecer *"Gasto 'Almuerzo en el mercado' registrado con éxito! (ID: 1)"*

### Verificación en la Base de Datos:
```sql
SELECT * FROM actividades_gastos;
```
Deberías ver el gasto registrado con su concepto, monto, categoría y fecha.

---

## Paso 9: Probar la Detección de Conexión

### Sin conexión a Internet:
1. Desactiva el WiFi o los datos móviles en el celular
2. Intenta iniciar sesión o registrar un viaje
3. **Resultado esperado**: Debe aparecer *"No hay conexión a Internet. Verifica tu red."*

### Con conexión a Internet:
1. Activa el WiFi o los datos móviles
2. Intenta la misma operación
3. **Resultado esperado**: La operación debe completarse exitosamente

---

## Paso 10: Probar la Persistencia de Sesión

1. Inicia sesión con tu usuario
2. Cierra completamente la app (desliza para cerrar desde las apps recientes)
3. Vuelve a abrir la app
4. Navega a la sección de **Viajes**
5. **Resultado esperado**: Los viajes deberían cargar automáticamente sin pedir login de nuevo

---

## Paso 11: Probar las Notas (Módulo Offline)

1. Toca el ícono **Notas** en la barra inferior
2. Crea una nota de prueba
3. **Resultado esperado**: Las notas se guardan localmente en SQLite (no necesitan Internet)

---

## Resumen de Verificación en Base de Datos

Al finalizar todas las pruebas, ejecuta estas consultas para verificar que todo esté funcionando:

```sql
-- Ver usuarios registrados
SELECT id, nombre, email FROM usuarios;

-- Ver viajes registrados
SELECT id, titulo, fecha_inicio, fecha_fin, usuario_id FROM viajes;

-- Ver gastos registrados
SELECT id, concepto, monto, categoria, fecha FROM actividades_gastos;

-- Ver destinos registrados (si los agregaste)
SELECT id, nombre, latitud, longitud, viaje_id FROM destinos;
```

---

## Solución de Problemas Comunes

| Problema | Solución |
|----------|----------|
| "Error de conexión" | Verifica que el backend esté corriendo y la URL sea correcta |
| "Credenciales inválidas" | Asegúrate de registrarte antes de hacer login |
| No carga la lista de viajes | Verifica que el usuario esté logueado y tenga viajes registrados |
| La app se cierra al abrir | Revisa el logcat en Android Studio para ver el error exacto |
| No guarda el viaje | Verifica que las fechas tengan el formato YYYY-MM-DD |

---

## Archivos Clave para las Pruebas

| Archivo | Ubicación | Función |
|---------|-----------|---------|
| `RetrofitClient.java` | `app/src/main/java/co/edu/ue/network/` | Configura la URL del servidor |
| `ApiService.java` | `app/src/main/java/co/edu/ue/network/` | Define todos los endpoints |
| `AccountFragment.java` | `app/src/main/java/co/edu/ue/` | Lógica de login/registro |
| `AddTripFragment.java` | `app/src/main/java/co/edu/ue/` | Formulario de viajes |
| `TripsFragment.java` | `app/src/main/java/co/edu/ue/` | Lista de viajes |
| `ExpenseFragment.java` | `app/src/main/java/co/edu/ue/` | Formulario de gastos |
| `SessionManager.java` | `app/src/main/java/co/edu/ue/utils/` | Maneja la sesión del usuario |
| `NetworkUtils.java` | `app/src/main/java/co/edu/ue/utils/` | Detecta la conexión a Internet |
