# Sistema de Clínica Médica

Sistema de gestión clínica para el manejo de historias clínicas, evoluciones médicas, recetas digitales y pedidos de laboratorio.

## Datos del Proyecto
Este proyecto fue desarrollado como Trabajo Final Integrador para la materia Ingeniería de Software.

**Universidad:** Universidad Tecnológica Nacional - Facultad Regional Tucumán  
**Comisión:** 4K2  
**Grupo:** 3  
**Integrantes**:  
- Gallardo, Maximiliano - 50.284  
- Lopez Asis, Juan Manuel - 53.303  
- Montilla, Tomás - 53.331  
- Nanterne Bachs, Facundo Esteban - 53.346  

## Requisitos Previos

Para ejecutar el proyecto, es necesario tener instalados los siguientes programas:

- Java 21
- Maven
- Node.js y npm

## Instalación y Ejecución

Para ejecutar el proyecto, siga los siguientes pasos:

1. Clonar el repositorio

```bash
git clone https://github.com/MontillaTomas/is-tfi.git
```

2. Ingresar a la carpeta del proyecto

```bash
cd is-tfi
```

#### Backend (Spring Boot)

Para ejecutar el servidor backend, ejecute los siguientes comandos en la raíz del proyecto:

1. Ingresar a la carpeta `backend`:

```bash
cd backend
```

2. Instale las dependencias

```
mvn clean install
```

3. Ejecute la aplicación backend

```
mvn spring-boot:run
```

El servidor backend estará disponible en `http://localhost:8080`

#### Frontend (React)

Para ejecutar la aplicación frontend, ejecute los siguientes comandos en la raíz del proyecto:

1. Ingrese a la carpeta `frontend`:

```bash
cd frontend
```

2. Instale las dependencias

```
npm install
```

3. Ejecute la aplicación frontend

```
npm start
```

La aplicación frontend estará disponible en `http://localhost:5173`

## API Endpoints

### Autenticación

La aplicación utiliza autenticación JWT. Para obtener un token, envíe una solicitud POST a `/api/v1/autenticacion/login` con el email y la contraseña del usuario. Actualmente, el único usuario registrado es el siguiente:

#### Login
- **URL:** `/api/v1/autenticacion/login`
- **Método:** `POST`
- **Body:**
```json
{
    "email": "email@email.com",
    "contrasena": "12345"
}
```

### Pacientes
#### Búsqueda de Pacientes

- **URL:** `/api/v1/pacientes`
- **Método:** `GET`
- **Auth:** `Bearer Token JWT`
##### Parámetros Query:

- **busqueda** (opcional): texto para filtrar pacientes


- **Ejemplo:** `/api/v1/pacientes?busqueda=perez`

#### Obtener Paciente

- **URL:** `/api/v1/pacientes/{dni}`
- **Método:** `GET`
- **Auth:** `Bearer Token JWT`
##### Parámetros URL:

- **dni:** DNI del paciente


- **Ejemplo:** `/api/v1/pacientes/44747797`

#### Crear Paciente

- **URL:** `/api/v1/pacientes`
- **Método:** `POST`
- **Auth:** `Bearer Token JWT`
- **Body:**
- 
```json
{
  "dni": 12345678,
  "cuil": 20123456789,
  "nombre": "Juan Pérez",
  "fechaNacimiento": "1990-05-15",
  "email": "juan.perez@example.com",
  "telefono": "+54 9 381 1234567",
  "direccion": {
    "calle": "Av. Siempre Viva",
    "numero": 742,
    "piso": 3,
    "departamento": "B",
    "codigoPostal": "4000",
    "ciudad": "San Miguel de Tucumán"
  },
  "obraSocial": {
    "codigo": 123,
    "denominacion": "Obra Social Ejemplo",
    "sigla": "OSE"
  },
  "numeroAfiliadoObraSocial": 987654
}
```
### Diagnósticos

#### Agregar Diagnóstico
- **URL:** `/api/v1/pacientes/{dni}/diagnosticos`
- **Método:** `POST`
- **Auth:** `Bearer Token JWT`
##### Parámetros URL:
- **dni:** DNI del paciente

- **Body:**
```json
{
    "nombre": "Hipertensión"
}
```

### Evoluciones

#### Agregar Evolución
- **URL:** `/api/v1/pacientes/{dni}/diagnosticos/{nombreDiagnostico}/evoluciones`
- **Método:** `POST`
- **Auth:** `Bearer Token JWT`
##### Parámetros URL:
- **dni:** DNI del paciente
- **nombreDiagnostico:** Nombre del diagnóstico

- **Body:**
```json
{
    "informe": "informe"
}
```

### Recetas Digitales

#### Crear Receta Digital
- **URL:** `/api/v1/pacientes/{dni}/diagnosticos/{nombreDiagnostico}/evoluciones/{idEvolucion}/recetas-digitales`
- **Método:** `POST`
- **Auth:** `Bearer Token JWT`
##### Parámetros URL:
- **dni:** DNI del paciente
- **nombreDiagnostico:** Nombre del diagnóstico
- **idEvolucion:** ID de la evolución

- **Body:**
```json
[
 {
   "codigo": 101,
   "descripcion": "Paracetamol 500mg",
   "formato": "Tableta"
 },
 {
   "codigo": 202,
   "descripcion": "Ibuprofeno 200mg",
   "formato": "Cápsula"
 }
]
```

### Pedidos de Laboratorio

#### Crear Pedido de Laboratorio
- **URL:** `/api/v1/pacientes/{dni}/diagnosticos/{nombreDiagnostico}/evoluciones/{idEvolucion}/pedidos-laboratorio`
- **Método:** `POST`
- **Auth:** `Bearer Token JWT`
##### Parámetros URL:
- **dni:** DNI del paciente
- **nombreDiagnostico:** Nombre del diagnóstico
- **idEvolucion:** ID de la evolución

- **Body:**
```json
{
   "texto": "texto de ejemplo 123"
}
```