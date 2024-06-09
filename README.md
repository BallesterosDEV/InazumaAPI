# Inazuma API
Mi nombre es Pablo Ballesteros lara y este proyecto se ha creado como trabajo final de curso de 2º Desarrollo de Aplicaciones Multiplataforma en la escuela profesional Santa Joaquina de Vedruna.

## Índice
1. Introducción
2. Tecnologías utilizadas.
3. Funcionalidades del proyecto.
4. Guía de instalación y uso.
5. Documentación.
6. Conclusiones.
7. Contribuciones, agradecimientos, referencias.
8. Licencias.
9. Contacto.

### 1 - Introducción

El proyecto consiste en una API de Inazuma Eleven basada en la consulta, flujo y manipulación de datos, con algunas mecánicas útiles para los videojuegos. Para entender de qué trata el proyecto primero es encesario entender de qué trata Inazuma Eleven. Para dar un poco de contexto, vamos a poner de ejemplo a Pokemon, una franquicia mucho más conocida y con mecánicas similares, Inazuma Eleven es una serie de fútbol, en la que los jugadores de fútbol tienen supertécnicas que vendrían a ser como las habilidades de los pokemons, existen habilidades de ataque, otras que te curan, otras que te aumentan las estadísticas, etc... De la misma forma, en Inazuma Eleven existen supertécnicas de tiro, regate, defensa y parada, además, cada jugador pertenece a un elemento, siendo un total de cuatro (fuego, tierra, aire, planta). La fusión de todas estas variables permiten determinar qué jugador es superior a otro jugador, si un delantero tiene supertécnicas de tiro, las aprovechará mejor que un defensa, y si un portero es de tipo fuego e intenta pararse una supertécnica de tiro de tipo naturaleza, será más fácil de pararlo. El competitivo de Inazuma Eleven está basado en los videojuegos, y los videojuegos en la serie, en la que hay una serie de equipos y jugadores predefinidos, y eres tú el encargado de decidir qué jugadores incluir en tú plantilla, cómo combinarlos lo mejor posible y cómos gestionas todas estas variables. Inazuma Eleven es una de las series más queridas de mi infancia y cualquier niño nacido en los 2000, y creo que utilizar esta temática y crear la api desde cero con todos sus datos y vistas era la mejor manera de darle mi toque personal.

![image](https://github.com/BallesterosDEV/InazumaAPI/assets/118269269/977286f8-6fbc-4d84-8b2b-fbaf15677150)

### 2 - Tecnologías utilizadas

- Java 21 + SpringBoot 3.2.4: Spring Boot contiene todas las herramientas
para las funcionalidades de la aplicación, y utiliza Java como lenguaje de
programación, es una de las mejores opciones para el tipo de proyecto
que deseo hacer.

- MySQL: Para almacenar los datos se necesita una base de datos, en mi
caso estoy más familiarizado con SQL.

- Módulos de Spring: Spring MVC (Para las vistas, a través de un motor de
plantillas html llamado Thymeleaf), Spring Data JPA (Para los métodos y
funcionalidades a través del repositorio), Spring Security (Para la
seguridad y la relación de usuario administrador a la hora de registrar y loguear usuarios) y JasonWebToken (para asegurar la autentificación del usuario).

- Otras dependencias: MySQL Server (Para la gestión de la BBDD),
SonnarLint (Mejorar la calidad estandarizada del código), Lombok
(Automatizar y facilitar código), Swagger (Documentación de la API),
JUnit5 + Mockito (Testing del proyecto) y SLF4J + Logback (Registros y
logs del proyecto).

### 3 - Funcionalidades

La API tiene un total de 5 secciones en la página principal, en cada sección hay una serie de funcionalidades con las que se pueden trabajar y manipular los datos.

`Show Players`
Te permite ver la lista de jugadores guardados actualmente, puedes ordenar esa lista por nombre, por equipo, por elemento (FIRE-EARTH-WOOD-WIND) o por posición del jugador (FW-MF-DF-GK).

`Show Players -> Details`
Si pinchas en el nombre de la card de un jugador, se abrirá una página con los detalles del jugador (estadísticas, elemento, posición, equipo).

`Show Players -> Edit Player`
Si pinchas en el botón de "Edit Player" en la card de un jugador, se abrirá un formulario con los datos del jugadore setteados, pudiendo editar el campo que desees.

`Show Players -> Delete Player`
Si pinchas en el botón de "Delete" en la card del jugador, se actualizará la página y ya no se verá el jugador eliminado.

`Create Player`
Te permite crear un jugador por medio de un formulario.

`Versus Mode`
El versus es una característica fundamental de los juegos de Inazuma Eleven, consisten en combates de 1vs1 en el que diversos factores como las stats del jugador, el elemento y la supertécnica seleccionada determinan el ganador en los duelos, esta funcionalidad es muy útil para que los jugadores de competitivo sepan qué jugadores incluir en su equipo según los jugadores que esté utilizando su rival.

![image](https://github.com/BallesterosDEV/InazumaAPI/assets/118269269/fba290b2-b119-4ed0-9c6f-fdddf9f95c6b)

`Inazuma Stars` 
Esta sección se trata de una colección de jugadores, el usuario con el que te has logueado tiene una colección de jugadores, pudiendo incluir un máximo de 5 jugadores por posición, también puedes eliminar jugadores de tu colección, cada colección es personal de cada usuario, no es una sola para todo el mundo.

`Search`
La última sección es un filtro de búsqueda, te permite buscar un jugador por nombre, buscar todos los jugadores que pertenezcan a un solo equipo, y buscar una supertécnica por su nombre, para que te muestre el poder de dicha supertécnica.

### 4 - Guía de instalación y uso

- La aplicación está desplegada gracias a Azure con una máquina virtual y un servidor de base de datos, para ingresar a la aplicación basta con entrar en el siguiente enlace: 52.174.111.26:8080
- Una vez ingreses, la aplicación te redirigirá a la página de login, puedes registrar un nuevo usuario o loguearte con el usuario existente por defecto: `Username: InazumaUser` `Password: Abcd1234`
- Cuando estés logueado, ya podrás acceder a todas las funcionalidades que te ofrece la aplicación, ten en cuenta que los tokens expiran tras una hora de uso para mejorar la seguridad, así que al cabo de una hora deberás volver a loguearte.

### 5 - Documentación

- Para acceder a la documentación automática del proyecto basta con ingresar a: 52.174.111.26:8080/swagger-ui/index.html
- Para acceder a la documentación personalizada, basta con clickar en los archivos que vienen a continuación.
  
[Documentación.pdf](https://github.com/user-attachments/files/15752778/Documentacion.pdf)

### 6 - Conclusiones

Realizar este proyecto ha sido muy enriquecedor para mí tanto a nivel técnico por todas las tecnologías que he aprendido, obstáculos a los que me he enfrentado y diferentes situaciones de estrés como a nivel personal, para adentrarme a un nuevo mundo y trabajar con tecnologías nuevas para mí, sin contar la satisfacción que supone llevar a cabo con éxito la idea que tenías en tu cabeza y que no te sentías del todo capaz de lograr.

### 7 - Contribuciones, agradecimientos, referencias

__Contribuciones__
Este proyecto es abierto a contribuciones. Si deseas colaborar, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama `(git checkout -b feature/nueva-funcionalidad)`.
3. Realiza tus cambios y haz commit `(git commit -m 'Añadir nueva funcionalidad')`.
4. Haz push a la rama `(git push origin feature/nueva-funcionalidad)`.
5. Abre un pull request.

__Agradecimientos__
Agradezco a mis profesores de la escuela profesional Santa Joaquina de Vedruna por su apoyo y orientación a lo largo del curso, a mi familia y amigos por su apoyo constante y a la comunidad de desarrolladores de Spring Boot y Java por sus valiosos recursos y guías.

__Referencias__
- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Documentación de MySQL](https://dev.mysql.com/doc/)
- [Thymeleaf](https://www.thymeleaf.org/)
- [Swagger](https://swagger.io/)
- [SonarLint](https://www.sonarlint.org/)
- [Lombok](https://projectlombok.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [SLF4J](http://www.slf4j.org/)

### 8 - Licencias

Este proyecto está licenciado bajo la Licencia MIT, puedes ver más detalles en el archivo LICENSE del repositorio.

### 9 - Contacto

Si tienes alguna pregunta o sugerencia sobre el proyecto, puedes contactarme a través de:

- Correo electrónico: `pabloballesteroslara2004@gmail.com`
- GitHub: `BallesterosDEV`
- Instagram: `_@pballesteros`

