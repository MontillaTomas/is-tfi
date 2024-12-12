# Sistema de Clínica Médica

Sistema de gestión clínica que permite el manejo de historias clínicas, evoluciones médicas, recetas digitales y pedidos de laboratorio.

## Créditos
Este proyecto fue desarrollado como Trabajo Final Integrador para la materia Ingeniería de Software.

**Universidad:** Universidad Tecnológica Nacional - Facultad Regional Tucumán
**Comisión:** 4K2
**Grupo:** 3

### Equipo de Desarrollo
- Alderete, Estefanía (50.048)
- Gallardo, Maximiliano (50.284)
- Lopez Asis, Juan Manuel (53.303)
- Montilla, Tomas (53.331)
- Nanterne Bachs, Facundo Esteban (53.346)

## Estructura del Proyecto

is_tfi/
├── frontend/               # Proyecto React
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.is_tfi/  # Código fuente backend
│   │   └── resources/
│   │       └── application.properties
│   └── test/              # Pruebas
├── target/                # Archivos compilados
├── pom.xml               # Configuración Maven
└── README.md

## Requisitos Previos
- Java 21
- Maven
- Node.js y npm

## Instalación y Ejecución

### Backend (Spring Boot)

# Desde la raíz del proyecto (is_tfi)
mvn clean install
mvn spring-boot:run

El servidor backend estará disponible en `http://localhost:8080`

### Frontend (React)

# Navegar al directorio frontend
cd frontend

# Instalar dependencias
npm install

# Iniciar el servidor de desarrollo
npm run dev

La aplicación frontend estará disponible en `http://localhost:5173`

## API Endpoints

### Autenticación

#### Login
- **URL:** `/api/v1/autenticacion/login`
- **Método:** `POST`
- **Body:**
- 
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