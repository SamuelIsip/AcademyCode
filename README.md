### **Capa De Presentación**

**Interfaces gráficas**

El diseño de las interfaces que forman la aplicación intenta ser lo más parecido a los diseños vistos en las aplicaciones más utilizadas actualmente. Un diseño actual, agradable a la vista y sobre todo intuitivo es lo que se ha intentado implementar en cada interfaz. Estas son muy distintas entre ellas ya que en cada una se desarrolla una actividad diferente, aunque en cuanto al diseño, tienen unas características comunes, como los colores, la forma de los botones, el tipo de letra, etc.

Se describirá el diseño de cada interfaz y las funcionalidades de estas:

[Interfaz de Inicio de la aplicación]

La pantalla de arranque se muestra cada vez que se abre la aplicación. En la pantalla se muestra una imagen de fondo y sobrepuesto, el logo de la aplicación y de la empresa que representa, además del nombre de esta.

Esta pantalla solo se muestra durante 2 segundos, después desaparece y da paso a otra interfaz.

![15](https://user-images.githubusercontent.com/61310905/84302951-719eef80-ab56-11ea-85a8-ad77b63a59b8.png)

[Interfaz Inicio Sesión/Registrar Usuario]

La pantalla siguiente a la de arranque es la de Inicio de sesión, que se compone del logo de la aplicación, 2 campos de texto necesarios para poder loguearse (email y contraseña) y 3 botones, un botón para iniciar sesión con cuenta creada exclusivamente en la aplicación, otro iniciar con una cuenta ya existente (de Google) y el último para acceder a otra pantalla de registro de usuario en caso de que no se disponga de una cuenta.

La pantalla de registro contiene más campos de texto, en los que se deben incluir lo datos del usuario, que posteriormente se guardarán en la base de datos (email, nombre de usuario, teléfono y contraseña). Cada campo comprueba el dato introducido que sea correcto según unas condiciones (pj: la contraseña tenga más de 8 caracteres, el nombre no exista en la base de datos, el email tenga una estructura válida, etc).

Se incluyen también 3 botones igual que en la pantalla anterior, el primero registra el usuario si cumple todas las condiciones, el segundo introduce los datos de la cuenta de Google seleccionada en los campos de texto para luego poder registrar, el tercer botón vuelve a la pantalla de inicio.

![17](https://user-images.githubusercontent.com/61310905/84302978-82e7fc00-ab56-11ea-90de-9d98ff1ccc1c.png)
![16](https://user-images.githubusercontent.com/61310905/84302981-83809280-ab56-11ea-969e-f0aa073ae510.png)

[Interfaz Principal/Menú/Ajustes]

La pantalla principal es la más gráfica y más compleja en cuanto al desarrollo de su diseño, ya que debe cumplir muchas funcionalidades. Muestra un título y el logo de la aplicación, las "card view" son las que representan otras pantallas en las que se podrá acceder para realizar distintas actividades. Al pulsarlas te dirigen a las pantallas correspondientes.

Deslizando de izquierda a derecha o pulsando el botón de la esquina izquierda, se despliega un menú. En este se mostrará la imagen de perfil del usuario, el nombre y email, además de varios botones (los 4 primeros cumplen la función de las "card view", redirigen a las mismas pantallas que estas), el botón de ajustes despliega una pantalla, que se mostrará a continuación, y el botón de cerrar sesión permite deloguearse y volver a la pantalla de inicio sesión.

La pantalla que se muestra en ajustes se compone de varios bloques, uno para actualizar el perfil (con campos: email, nombre, teléfono y un botón para aplicar cambios), otro para cambiar la contraseña (con campos: contraseña actual y la nueva, y un botón que aplica los cambios si la contraseña actual es correcta) y uno para borrar la cuenta actual, la que está logueada.

![18](https://user-images.githubusercontent.com/61310905/84303020-91ceae80-ab56-11ea-8bb4-fc3a255087db.jpg)
![19](https://user-images.githubusercontent.com/61310905/84303023-92674500-ab56-11ea-9939-2e5e38a64e9c.png)
![20](https://user-images.githubusercontent.com/61310905/84303026-92ffdb80-ab56-11ea-886b-58ab5934bdae.png)

[Interfaz Teoría]

Al entrar desde el menú principal a la card view de teoría, se despliega esta pantalla. Su diseño se basa en el del menú principal, pero cambiando las card view de tamaño, además, los elementos de esta lista ya no se deslizan de forma vertical sino horizontal. Cada card view está diseñada por el logo y el nombre de un lenguaje de programación.

Al pulsar uno de estos card view se despliega una lista de elementos, en este caso son PDF's con teroría del lenguaje de programación pulsado. Estos se cargan desde la base de datos del servidor, se puede actualizar la lista deslizando hacia abajo hasta que aparezca un "swipe".

El diseño de los elementos de la lista son una portada, el título del archivo, autor y la fecha de actualización del archivo.

Al pulsar en un elemnto de la lista, se abre otra ventada, que ya no está alojada en la aplicación sino en el navegador predeterminado del dispositivo movil. Dependiendo del navegador el diseño podrá variar y las funcionalidades de este también. En el caso de Google Chrome, el diseño es simple y se ajusta el diseño general de la aplicación, las funcionalidades que ofrece son: buscar por palabras en el archivo, descargar el archivo, añadir al Drive, compartir, imprimir, abrir con otra aplicación. Dado que estas funcionalidades ya vienen por defecto en el navegador, ya no se dio la necesidad de implementarlas por código.

![23](https://user-images.githubusercontent.com/61310905/84303047-9e530700-ab56-11ea-910a-8859242bccf1.jpg)
![21](https://user-images.githubusercontent.com/61310905/84303050-9f843400-ab56-11ea-8066-4bd13344fd38.jpg)
![22](https://user-images.githubusercontent.com/61310905/84303052-9f843400-ab56-11ea-9eb0-65d5711facf0.jpg)

[Interfaz Ejercicios]

En el cardview de los ejercicios, encontramos una pantalla con un diseño diferente en el de teoría, pero también se implementan las cardview con los mismos lenguajes de programación. Cada cardview representa una colección de ejercicios en formato pdf, estos ejercicos están relacionados al lenguaje seleccionado, además en la BD cada libro de teoría puede tener varios de estos ejercicios.

Dentro de cada cardview se comparte el mismo diseño que en el de libros de teoría, es una lista con los ejercicios, mostrando imagen, titulo, autor y fecha de actualización. Al seleccionar un elemento se abrirá en el navegador donde se dispondrá de diferentes funcionalidades.

![25](https://user-images.githubusercontent.com/61310905/84303081-aa3ec900-ab56-11ea-96fd-306f0e8abeb9.jpg)
![24](https://user-images.githubusercontent.com/61310905/84303082-aad75f80-ab56-11ea-83e7-056ed6a5d423.jpg)

[Interfaz Tutoriales Video]

Desde el menú se puede acceder a esta interfaz, en la que se encuentran varios videos enlazados de Youtube, estos se agrupan según el lenguaje de programación seleccionado en el menú desplazable de la parte superior de la pantalla.

Cada video se puede ver minimizado o en pantalla completa si se pulsa el botón de "fullscreen". Los videos están agrupados por cursos, pero mostrados de forma ascendente en cuanto a la dificultad de la teoría que estos desarrollan. Los videos no

pueden descargarse, al igual que no pueden ser descargados desde la plataforma Youtube, pero sí que se pueden compartir los videos o guardarlos en "ver más tarde" en Youtube, solo si estás registrado a la aplicación con la cuenta de Google.

![26](https://user-images.githubusercontent.com/61310905/84303100-b4f95e00-ab56-11ea-8699-884deada833e.jpg) ![27](https://user-images.githubusercontent.com/61310905/84303099-b460c780-ab56-11ea-9690-ff2c57f5eb54.jpg) 

[Interfaz Foro]

Al entrar al foro desde el menú, se visualizan directamente todos los mensajes escritos en el foro. Hay una imagen de fondo y los mensajes son cardviews que muestran la foto de perfil, el nombre de usuario, el email, la fecha de envío del mensaje y el mensaje en sí (que tiene 140 caracteres como máximo). El color de fondo de cada mensaje es verde, con una parte más oscura que la otra.

La imagen es la que tenga el usuario y si no tiene se muestra la de por defecto.

El cuadro de texto es de color azul con letras blancas y el botón de enviar es verde. La lista de mensajes se actualiza cada vez que es enviado un nuevo mensaje y siempre se muestra desde el último mensaje enviado, para ver los anteriores se debe hacer scroll hacia arriba.

![28](https://user-images.githubusercontent.com/61310905/84303127-bdea2f80-ab56-11ea-93bf-15647a2557eb.jpg)

[Interfaz Información del usuario]

A esta interfaz se puede acceder desde el foro (dando click en la parte superior del mensaje de cada usuario) o desde la interfaz de Amigos.

Su funcionalidad principal es mostrar la información más relevante del usuario seleccionado. También se permite añadir al usuario a tu lista de amigos o ver a todos tus amigos (a través de los botones).

La información que se muestra es: Nombre Usuario, Email, Teléfono, fecha de creación de cuenta e imagen del usuario.

![29](https://user-images.githubusercontent.com/61310905/84303163-ccd0e200-ab56-11ea-8340-10093052d30d.png)

[Interfaz Amigos]

Se puede acceder a la lista de amigos desde el menú principal o desde la interfaz de información de usuario, con el botón ver amigos.

Se muestran los usuarios agregados a amigos, su nombre de usuario, email, foto perfil y número de teléfono.

Al pulsar en cada usuario, se abrirá la interfaz en la que se detalla más la información de este.

![30](https://user-images.githubusercontent.com/61310905/84303202-d8240d80-ab56-11ea-9145-6510874f234f.jpg)

Casos De Uso 
------------

![11](https://user-images.githubusercontent.com/61310905/84303225-e6722980-ab56-11ea-87da-6d36f9086699.png)

Diseño Técnico 
--------------

La arquitectura del proyecto es un modelo cliente-servidor, en la que el cliente es la propia aplicación y el usuario que la utiliza, mientras que el servidor es el servicio que proporciona XAMPP (multiplataforma, Apache, MySQL, PHP, Perl).

También se cuenta con un hosting de NoIp.

![13](https://user-images.githubusercontent.com/61310905/84303333-15889b00-ab57-11ea-96e5-cec414d8835b.png)

### **Arquitectura Del Servicio** 

Apache es el servidor que se utiliza tanto para la API como para la BBDD.

La aplicación se conecta desde internet al servidor Apache, en el que está alojada la API y la BBDD. Através de la API (programada en PHP) se gestionan todas las peticiones hechas al servidor y a mysql. La explicaicón más sencilla para entender la funcionalidad de la API es compararla con un camarero, esta informa a la BBDD de las peticiones hechas por el cliente y le proporciona a este lo que la BBDD le envíe.

### **Arquitectura Física** 

Esta arquitectura es la común para la mayoría de aplicaciones básicas que no necesitan una gran cantidad de recursos.

El servidor y la BBDD se puede alojar en el propio portátil ya que la cantidad de información almacenada no necesita de más espacio. Aunque es recomendable que el dispositivo donde estos se alojen tenga suficiente memoria RAM para manejar los datos y la capacidad de estar siempre en funcionamiento.

### **Arquitectura Lógica** 

La aplicación tiene una arquitectura por presentación, la capa negocio y la capa de datos. La capa de negocio tiene todas las clases que permiten la ejecución de la aplicación en el dispositivo. La capa de presentación contiene todas las interfaces gráficas, con las que el usuario puede interactuar. Por último, la capa de datos es la que gestiona las peticiones al servidor y a la BBDD, se comunica directamente con la capa modelo.

Utilizar una arquitectura por capas tiene muchas ventajas, algunas de ellas son:

-Posibilita la reutilización de componentes de una sola capa, sin perjudicar al resto de capas. En nuestro caso si se necesitara cambiar el sistema de bases de datos, solo tendría que cambiar la capa de datos y no el resto.

-Permite probar los componentes de forma independiente.


### **Arquitectura De Bases De Datos** 

**Diagrama E/R de la base de datos/Relacional**

![31](https://user-images.githubusercontent.com/61310905/84303289-01449e00-ab57-11ea-9de9-6fb054271490.png)

