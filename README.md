Crear una aplicacion de series

La aplicacion tiene que estar conectada a una base de datos para poder registrar series y plataformas, tienen que estar conectadas por una clave foránea y los requisitos son los siguientes:

•   Consulta: Buscar series por distintos filtros (título, género, plataforma).

•   Modificación: Editar datos de series o plataformas existentes.

•   Eliminación: Eliminar registros de forma segura.

•   Creación: Crear series o plataformas de forma segura
## Crear una aplicacion de series

La aplicacion tiene que estar conectada a una base de datos para poder registrar series y plataformas, tienen que estar conectadas por una clave foránea y los requisitos son los siguientes:

    •	Consulta: Buscar series por distintos filtros (título, género, plataforma).

    •	Modificación: Editar datos de series o plataformas existentes.

    •	Eliminación: Eliminar registros de forma segura.

    •   Creación: Crear series o plataformas de forma segura

## Progaramacion Orientada a Objetos

La aplicación debe tener estos requisitos:

    •   Encapsulamiento
    •   Herencias
    •   Polimorfismo
    •   Abstraccion
    
Las clases tienen que estar representadas claramente.

Se debe implementar la estructura MVC(Modelo-Vista-Controlador) para mejorar la organizacion de codigo.

## Conexion con Base De Datos

Uso de JDBC para conectar Base de Datos Oracle.

La aplicacion debe tener un usario con permisos DBD para crear tablas si no existen el la BBDD.

## Estructura de la Base De Datos

Crear una base de datos que contenga las siguientes tablas: 

    •   Serie: ID, titulo, genero, nº temporadas, año lanzamiento, ID_Plataforma (FK)
    •   Plataforma: ID, nombre, pais de origen.
    
Establecer relaciones adecuadas.

## Interfaz gráfica para ingreso de datos

Desarrollar una interfaz amigable y validar los datos antes de guardarlos

## Interfaz gráfica para operaciones CRUD:
Crear interfaces gráficas para realizar operaciones de:

    •	Consulta: Buscar series por distintos filtros (título, género, plataforma).
    •	Modificación: Editar datos de series o plataformas existentes.
    •	Eliminación: Eliminar registros de forma segura.

Los cambios deben actualizarse directamente en la base de datos.

## Organizacion y Entrega
El código debe estar estructurado y comentado adecuadamente.
Debe entregarse un archivo comprimido (.zip o .rar) que incluya:

    •	Código fuente completo organizado en paquetes.
    •	Archivo SQL si se desea incluir estructura de base de datos alternativa.
    •	Archivo README con instrucciones claras para ejecutar el proyecto.



