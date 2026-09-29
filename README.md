# ✈️ TravelMate

## 📌 Descripción

**TravelMate** es una aplicación móvil orientada a la gestión y organización de viajes.

El proyecto integra una aplicación desarrollada en **Android Studio** con un backend construido en **Spring Boot** y una base de datos **PostgreSQL**, permitiendo administrar usuarios, viajes, destinos y actividades/gastos.

Además, la aplicación contempla almacenamiento local para funcionalidades sin conexión y el uso de recursos del dispositivo como cámara, ubicación y estado de conectividad.

---

## 🎯 Objetivo del proyecto

Desarrollar una aplicación móvil que permita organizar y administrar información relacionada con viajes mediante una arquitectura cliente-servidor.

El proyecto integra:

- 🌐 Persistencia remota mediante **PostgreSQL**.
- ⚙️ API REST desarrollada con **Spring Boot**.
- 📱 Aplicación móvil desarrollada en **Android Studio con Java**.
- 💾 Persistencia local mediante **SQLite / Room**.
- 🔐 Registro, inicio de sesión y protección de contraseñas.
- 🔄 Consumo de servicios REST mediante **Retrofit o Volley**.
- 📷 Acceso a la cámara del dispositivo.
- 📍 Servicios de ubicación.
- 📡 Validación del estado de conexión a Internet.

---

## 🛠️ Tecnologías utilizadas

| Área | Tecnologías |
|---|---|
| **Backend** | Java, Spring Boot, Spring Data JPA |
| **Base de datos remota** | PostgreSQL |
| **Seguridad** | BCrypt |
| **Pruebas de API** | Postman |
| **Frontend móvil** | Android Studio, Java, XML, Material Design |
| **Base de datos local** | SQLite / Room |
| **Consumo de API** | Retrofit / Volley |
| **Hardware móvil** | Cámara, GPS / Ubicación |
| **Conectividad** | ConnectivityManager |
| **Control de versiones** | Git |

---

## 🏗️ Arquitectura general

TravelMate utiliza una arquitectura cliente-servidor en la que la aplicación Android se comunica con una API REST desarrollada en Spring Boot.

```text
┌───────────────────────────────┐
│       APLICACIÓN ANDROID      │
│ Java + XML + Material Design  │
└──────────────┬────────────────┘
               │
        Retrofit / Volley
               │
               ▼
┌───────────────────────────────┐
│           API REST            │
│      Spring Boot + Java       │
└──────────────┬────────────────┘
               │
        Spring Data JPA
               │
               ▼
┌───────────────────────────────┐
│          PostgreSQL           │
│      Persistencia remota      │
└───────────────────────────────┘
```

Para las funcionalidades disponibles sin conexión:

```text
┌───────────────────────────────┐
│       APLICACIÓN ANDROID      │
└──────────────┬────────────────┘
               │
               ▼
┌───────────────────────────────┐
│        SQLite / Room          │
│       Persistencia local      │
└───────────────────────────────┘
```

---

# 👥 Integrantes y responsabilidades

## 👩‍💻 Gabriela — Backend, Base de Datos y Seguridad

**Rol:** Arquitectura del servidor y gestión de los datos principales.

### Tecnologías

`Spring Boot` · `Java` · `PostgreSQL` · `BCrypt` · `Postman`

### Responsabilidades

- Diseñar el modelo y las tablas de **Usuarios, Viajes, Destinos y Actividades/Gastos** en PostgreSQL.
- Definir claves primarias, claves foráneas y relaciones entre las tablas.
- Configurar la estructura del proyecto **Spring Boot**.
- Implementar las capas de entidades, repositorios, servicios y controladores.
- Implementar el registro e inicio de sesión de usuarios.
- Aplicar **BCrypt** para el almacenamiento seguro de contraseñas.
- Desarrollar los CRUD remotos de:
  - Viajes.
  - Destinos.
  - Actividades/Gastos.
- Probar los endpoints mediante **Postman**.
- Definir los contratos JSON utilizados para la comunicación entre Android y el backend.

### 📦 Entregable

> **API REST funcional con autenticación, conexión a PostgreSQL y CRUD remoto de Viajes, Destinos y Actividades/Gastos.**

---

## 👩‍🎨 Danna — Interfaz de Usuario y Almacenamiento Offline

**Rol:** Diseño visual, navegación y persistencia local sin conexión.

### Tecnologías

`Android Studio` · `Java` · `XML` · `Material Design` · `SQLite / Room`

### Responsabilidades

- Diseñar las interfaces de **Login y Registro**.
- Crear el listado principal de viajes mediante **RecyclerView**.
- Desarrollar formularios para el ingreso y edición de destinos.
- Desarrollar las vistas relacionadas con actividades y gastos.
- Configurar la navegación mediante **Activities o Fragments**.
- Implementar el CRUD local offline mediante **SQLite o Room**.
- Desarrollar listas y adaptadores para:
  - Checklist.
  - Notas.
  - Actividades pendientes.
- Permitir agregar, editar, marcar como realizadas y eliminar tareas locales.
- Gestionar la organización de ramas y pull requests del repositorio Git.

### 📦 Entregable

> **Interfaces funcionales de la aplicación Android y CRUD local de notas/checklist disponible sin conexión.**

---

## 👩‍🔧 Yudy — Integración de API, Hardware y Conectividad

**Rol:** Comunicación entre Android y el servidor, además del uso de recursos del dispositivo móvil.

### Tecnologías

`Android Studio` · `Retrofit / Volley` · `Cámara` · `Ubicación` · `ConnectivityManager`

### Responsabilidades

- Configurar **Retrofit o Volley** para consumir la API REST.
- Conectar los formularios de registro y login de Android con Spring Boot.
- Gestionar la sesión activa del usuario.
- Implementar los permisos y uso de la cámara.
- Permitir la captura de fotografías asociadas a viajes o destinos.
- Implementar permisos y servicios de ubicación.
- Asociar coordenadas geográficas a los destinos.
- Detectar el estado de conexión mediante **ConnectivityManager**.
- Informar al usuario cuando no exista conexión a Internet.
- Facilitar el acceso al módulo offline cuando no exista conexión.

### 📦 Entregable

> **Integración de Android con la API REST, manejo de sesión, conectividad y funciones de cámara y ubicación.**

---

## 📋 Resumen de responsabilidades

| Integrante | Componente | Tecnologías principales | Entregable |
|---|---|---|---|
| **Gabriela** | Backend y Base de Datos | Spring Boot, Java, PostgreSQL, Postman | API REST, autenticación y 3 CRUD remotos |
| **Danna** | Frontend y Offline | Android Studio, Java, XML, SQLite/Room | Interfaces y CRUD local offline |
| **Yudy** | API y Hardware | Retrofit/Volley, Cámara, Ubicación | Integración de API, conectividad y hardware |

---

# 📊 Estado actual del proyecto

## Backend y Base de Datos

| Tarea | Estado |
|---|:---:|
| Diseño de tablas y relaciones | ✅ Completado |
| Configuración de Spring Boot | ✅ Completado |
| BCrypt y autenticación | ✅ Completado |
| Registro de usuarios | ✅ Completado |
| Login | ✅ Completado |
| CRUD Viajes | ✅ Completado |
| CRUD Destinos | ✅ Completado |
| CRUD Actividades/Gastos | ✅ Completado |
| Contratos JSON | ✅ Completado |
| Pruebas en Postman | ✅ Completado |
| Evidencias de pruebas | ✅ Completado |

> 🚧 **Siguiente etapa:** integración de la aplicación Android con la API REST.

---

# 🔗 Endpoints disponibles

### URL base del backend

```text
http://localhost:8080
```

## 👤 Usuarios

| Método | Endpoint | Función |
|---|---|---|
| `POST` | `/api/usuarios/registro` | Registrar usuario |
| `POST` | `/api/usuarios/login` | Iniciar sesión |

## 🧳 Viajes

| Método | Endpoint | Función |
|---|---|---|
| `POST` | `/api/viajes` | Crear viaje |
| `GET` | `/api/viajes/usuario/{usuarioId}` | Listar viajes del usuario |
| `GET` | `/api/viajes/{id}` | Consultar viaje |
| `PUT` | `/api/viajes/{id}` | Actualizar viaje |
| `DELETE` | `/api/viajes/{id}` | Eliminar viaje |

## 📍 Destinos

| Método | Endpoint | Función |
|---|---|---|
| `POST` | `/api/destinos` | Crear destino |
| `GET` | `/api/destinos/viaje/{viajeId}` | Listar destinos del viaje |
| `GET` | `/api/destinos/{id}` | Consultar destino |
| `PUT` | `/api/destinos/{id}` | Actualizar destino |
| `DELETE` | `/api/destinos/{id}` | Eliminar destino |

## 💰 Actividades / Gastos

| Método | Endpoint | Función |
|---|---|---|
| `POST` | `/api/actividades-gastos` | Crear actividad/gasto |
| `GET` | `/api/actividades-gastos/viaje/{viajeId}` | Listar actividades/gastos del viaje |
| `GET` | `/api/actividades-gastos/{id}` | Consultar actividad/gasto |
| `PUT` | `/api/actividades-gastos/{id}` | Actualizar actividad/gasto |
| `DELETE` | `/api/actividades-gastos/{id}` | Eliminar actividad/gasto |

---

# 📚 Documentación adicional

La API REST fue validada mediante **Postman**, incluyendo:

- Registro de usuarios.
- Inicio de sesión.
- CRUD de Viajes.
- CRUD de Destinos.
- CRUD de Actividades/Gastos.

Las capturas y evidencias de las pruebas realizadas se encuentran en:

```text
docs/pruebasProjectKellyn.docx
```

El documento contiene las evidencias de las diferentes operaciones realizadas contra la API y las verificaciones correspondientes en la base de datos.

---

# 🚀 Próxima etapa

Con el **backend, la base de datos y las pruebas de la API completadas**, la siguiente fase del proyecto corresponde a la integración de la aplicación Android con los servicios desarrollados.

El flujo de integración será:

```text
Android
   │
   │ Retrofit / Volley
   ▼
Spring Boot
   │
   │ Spring Data JPA
   ▼
PostgreSQL
```

Posteriormente se realizarán las pruebas conjuntas de:

- 🔐 Registro e inicio de sesión.
- 🧳 Gestión de viajes.
- 📍 Gestión de destinos.
- 💰 Gestión de actividades y gastos.
- 💾 Funcionamiento offline.
- 📷 Cámara.
- 📍 Ubicación.
- 📡 Estado de conectividad.
- 📱 Pruebas en emulador y dispositivo físico.

---

## ✈️ TravelMate

**Organiza tus viajes, destinos y actividades desde un solo lugar.**
