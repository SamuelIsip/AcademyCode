-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-09-2020 a las 14:45:42
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `academycodebd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `amigos`
--

CREATE TABLE `amigos` (
  `id_amigos` int(10) NOT NULL,
  `nombre_user` varchar(50) NOT NULL,
  `nombre_user_amigo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `amigos`
--

INSERT INTO `amigos` (`id_amigos`, `nombre_user`, `nombre_user_amigo`) VALUES
(1, 'Samuel Isip', 'samuelsamu');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foro`
--

CREATE TABLE `foro` (
  `id_foro` int(11) NOT NULL,
  `ip_usu` varchar(255) DEFAULT NULL,
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mensaje` varchar(255) DEFAULT NULL,
  `fecha` varchar(20) DEFAULT NULL,
  `foto_perfil` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `foro`
--

INSERT INTO `foro` (`id_foro`, `ip_usu`, `id_usuario`, `nombre_usuario`, `email`, `mensaje`, `fecha`, `foto_perfil`) VALUES
(21, 'ndc=', 39, 'samuelsamu', 'samuel.isip@gmail.com', 'xIp5Ug==', '20/05/2020 11:48', NULL),
(22, 'ndc=', 39, 'samuelsamu', 'samuel.isip@gmail.com', 'xIp5Ug==', '20/05/2020 11:48', NULL),
(23, 'ndc=', 39, 'samuelsamu', 'samuel.isip@gmail.com', 'wYB7QLKriQ==', '20/05/2020 13:18', NULL),
(24, 'ndc=', 39, 'samuelsamu', 'samuel.isip@gmail.com', 'wYw1XravnzpJDw==', '20/05/2020 13:27', NULL),
(25, 'ndc=', 39, 'samuelsamu', 'samuel.isip@gmail.com', 'w5FnXPOsiTVQC+aa', '20/05/2020 13:27', NULL),
(26, 'ndc=', 39, 'samuelsamu', 'samuel.isip@gmail.com', '1cV6R6GuzDZGBP+etYc=', '20/05/2020 13:28', NULL),
(58, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'yoFyVA==', '22/05/2020 13:09', NULL),
(59, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', '3JdgVrGg', '22/05/2020 13:31', NULL),
(60, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', '3JdgVrGg3g==', '22/05/2020 13:41', NULL),
(61, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', '3JdgVrGg3w==', '22/05/2020 13:44', NULL),
(62, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', '3JdgVrGg2A==', '22/05/2020 13:59', NULL),
(63, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', '3JdgVrGg2Q==', '22/05/2020 14:00', NULL),
(64, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', '3JdgVrGg2g==', '22/05/2020 14:01', NULL),
(65, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'mw==', '22/05/2020 14:03', NULL),
(66, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'lA==', '22/05/2020 14:05', NULL),
(67, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'lQ==', '22/05/2020 14:07', NULL),
(68, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'nQ==', '22/05/2020 14:08', NULL),
(69, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'ng==', '22/05/2020 14:10', NULL),
(70, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'nw==', '22/05/2020 14:14', NULL),
(71, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'mA==', '22/05/2020 14:16', NULL),
(72, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'yI9/QLilhj8=', '22/05/2020 14:31', NULL),
(73, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'xI50WLKpiyhIGQ==', '22/05/2020 14:32', NULL),
(74, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'mdAgBg==', '22/05/2020 14:37', NULL),
(75, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'mtMj', '22/05/2020 14:38', NULL),
(76, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'zol0', '22/05/2020 14:39', NULL),
(77, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'zQ==', '22/05/2020 14:39', NULL),
(78, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'xA==', '22/05/2020 16:08', NULL),
(79, 'ndwnHeL31HUSRPQ=', 39, 'samuelsamu', 'samuel.isip@gmail.com', '5KpZcg==', '22/05/2020 16:46', NULL),
(80, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'xIp5UuE=', '22/05/2020 16:46', NULL),
(81, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', '2g==', '22/05/2020 17:28', NULL),
(82, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'yw==', '22/05/2020 17:29', NULL),
(83, 'ndwnHeL31HUSRPQ=', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'yA==', '22/05/2020 17:30', NULL),
(84, 'ldA7BePv3moVRL/I', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', '2Q==', '17/08/2020 21:16', NULL),
(85, 'ldA7BePv3moVRL/I', 37, 'Samuel Isip', 'samuel.isip26@gmail.com', 'xIp5Ug==', '17/08/2020 21:47', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libro_ejercicios`
--

CREATE TABLE `libro_ejercicios` (
  `id_ejercicios` int(11) NOT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `tematica` varchar(100) DEFAULT NULL,
  `fecha` varchar(20) DEFAULT NULL,
  `autor` varchar(50) DEFAULT NULL,
  `portada` varchar(250) DEFAULT NULL,
  `url_pdf` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `libro_ejercicios`
--

INSERT INTO `libro_ejercicios` (`id_ejercicios`, `titulo`, `tematica`, `fecha`, `autor`, `portada`, `url_pdf`) VALUES
(1, 'Ejercicios java 1', 'java', '05/05/2020', 'Perez Montés', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://www.eduinnova.es/monografias2011/ene2011/java.pdf'),
(2, 'Programación estructurada Java', 'java', '06/05/2020', 'Santiago García', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'https://www.tlm.unavarra.es/pluginfile.php/25152/mod_resource/content/0/apuntes_java.pdf'),
(3, 'Fundamentos Java', 'java', '06/05/2020', 'Gorka Prieto', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'https://ocw.ehu.eus/file.php/119/program_estruc/practicas/practicas_java.pdf'),
(4, 'Ampliación sistemas Operativos', 'java', '06/05/2020', 'Rodrigo Santamaría', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://vis.usal.es/rodrigo/documentos/aso/java3h.pdf'),
(12, 'Condicionales, Bucles, Tablas y Funciones', 'java', '05/05/2020', 'Perez Montés', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://www.eduinnova.es/monografias2011/ene2011/java.pdf'),
(13, 'Programación estructurada Java', 'java', '06/05/2020', 'Santiago García', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'https://www.tlm.unavarra.es/pluginfile.php/25152/mod_resource/content/0/apuntes_java.pdf'),
(14, 'Fundamentos Java', 'java', '06/05/2020', 'Gorka Prieto', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'https://ocw.ehu.eus/file.php/119/program_estruc/practicas/practicas_java.pdf'),
(15, 'Ampliación sistemas Operativos', 'java', '06/05/2020', 'Rodrigo Santamaría', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://vis.usal.es/rodrigo/documentos/aso/java3h.pdf'),
(16, 'Clases y Objetos', 'java', '06/05/2020', 'Anónimo', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://www.inf-cr.uclm.es/www/cmunoz/mytp/ejercicios7.pdf'),
(17, 'Recursividad', 'java', '10/05/2020', 'Mariano Fernández López', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://www.cartagena99.com/recursos/alumnos/apuntes/ejercicios_resueltos.pdf'),
(18, 'Java con BD', 'java', '10/05/2020', 'Anónimo', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://profesores.fi-b.unam.mx/sun/Downloads/Java/Java-CursoCertificacion.pdf'),
(19, 'Java Avanzado', 'java', '10/05/2020', 'Anónimo', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://www.jtech.ua.es/j2ee/publico/lja-2012-13/wholesite.pdf'),
(20, 'Ejercicios practicos con soluciones', 'python', '10/05/2020', 'Anónimo', 'https://i.ibb.co/DWzvFFb/python.png', 'https://alfabetizaciondigital.fundacionesplai.org/pluginfile.php/9629/mod_resource/content/1/Ejercicios%20Python%20-%20con%20soluciones.pdf'),
(21, 'Algoritmos y Programación I', 'python', '10/05/2020', 'Anónimo', 'https://i.ibb.co/DWzvFFb/python.png', 'http://www.utic.edu.py/citil/images/Manuales/algoritmos-programacion-Python.pdf'),
(22, 'Python para principiantes', 'python', '10/05/2020', 'Eugenia Bahit', 'https://i.ibb.co/DWzvFFb/python.png', 'https://www.iaa.csic.es/python/curso-python-para-principiantes.pdf'),
(23, 'Conceptos básicos y ejercicios', 'python', '10/05/2020', 'Universitat Jaume I', 'https://i.ibb.co/DWzvFFb/python.png', 'http://www3.uji.es/~vjimenez/AULASVIRTUALES/PL-0910/python-by-vilar.pdf'),
(24, 'Fundamentos de programación', 'python', '10/05/2020', 'Alejandro Castán Salinas', 'https://i.ibb.co/DWzvFFb/python.png', 'http://www.xtec.cat/~acastan/textos/PB_Practicas_Python.pdf'),
(25, 'EJERCICIOS DE PYTHON', 'python', '10/05/2020', 'Anónimo', 'https://i.ibb.co/DWzvFFb/python.png', 'http://www.cartagena99.com/recursos/programacion/ejercicios/ejercicios_python_funciones.pdf'),
(26, 'Introducción al lenguaje', 'cplus', '10/05/2020', 'Anónimo', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://upcommons.upc.edu/bitstream/handle/2099.3/36408/9788483013380.pdf'),
(27, 'Algoritmos', 'cplus', '10/05/2020', 'Anónimo', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://deysimarin.files.wordpress.com/2010/08/ejercicios-de-programacion-lenguaje-c.pdf'),
(28, 'C++ paso a paso', 'cplus', '10/05/2020', 'Sergio Luján Mora', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://sergiolujanmora.es/materiales/cpp-muestra.pdf'),
(29, 'Soluciones y problemas para c++', 'cplus', '10/05/2020', 'Gary J. Bronson', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://ayudasingenieria.com/files/INFORMATICA/%5BLIBRO%5D%20Bronson%202ed.pdf'),
(30, 'Programación Gráfica C++', 'cplus', '10/05/2020', 'Gary J. Bronson', 'https://i.ibb.co/Zx91pTR/cplus.png', 'http://isa.uniovi.es/~ialvarez/Curso/descargas/002-ProgramacionGraficaQt.pdf'),
(31, 'Ejercicios Resueltos', 'javascript', '10/05/2020', 'Matías Salmon', 'https://i.ibb.co/n8rJNT8/javascript.png', 'https://lemus.webs.upv.es/wordpress/wp-content/uploads/2018/07/javascript-es6-ejercicios-resueltos-parte-1.pdf'),
(32, 'Ejercicios Web', 'javascript', '10/05/2020', 'Anónimo', 'https://i.ibb.co/n8rJNT8/javascript.png', 'http://www.colimbo.net/practicas/315/Ejercicios_JavaScript.pdf'),
(33, 'JavaScript Básico', 'javascript', '10/05/2020', 'w3schools', 'https://i.ibb.co/n8rJNT8/javascript.png', 'http://zeus.inf.ucv.cl/~ifigueroa/lib/exe/fetch.php/teaching/webeng/enunciado_rda_javascript.pdf'),
(34, 'Bases de JavaScript', 'javascript', '10/05/2020', 'Ana Nieves Rodríquez', 'https://i.ibb.co/n8rJNT8/javascript.png', 'http://www.programacionyrobotica.com/wordpress/wp-content/uploads/2016/01/conceptos-bsicos-de-javascript-con-ejemplos.pdf'),
(35, 'Manual', 'javascript', '10/05/2020', 'Anónimo', 'https://i.ibb.co/n8rJNT8/javascript.png', 'http://www.wiener.edu.pe/manuales/4to-ciclo/programacion-web-2/curso-practico-de-javascript.pdf'),
(36, '4 Actividades JS', 'javascript', '10/05/2020', 'Anónimo', 'https://i.ibb.co/n8rJNT8/javascript.png', 'https://israelmv.files.wordpress.com/2009/05/ejerciciojavascript.pdf'),
(37, 'Formularios y JavaScript', 'javascript', '10/05/2020', 'Anónimo', 'https://i.ibb.co/n8rJNT8/javascript.png', 'https://www.iit.comillas.edu/palacios/cursoAppWeb/cap02.pdf'),
(38, 'Ejercicios Y Prácticas', 'php', '10/05/2020', 'Sergio Hugo Sanchez', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'http://index-of.co.uk/Programming/Cuaderno-de-Ejercicios-y-Practicas-PHP.pdf'),
(39, 'Ejercicios resueltos PHP', 'php', '10/05/2020', 'Sergio Hugo Sanchez', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'http://www.clasespersonales.com/inf324/ejercicios1_php.pdf'),
(40, 'PHP y MySQL', 'php', '10/05/2020', 'Anónimo', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'https://www.um.es/docencia/barzana/DAWEB/2017-18/daweb-practica9.pdf'),
(41, 'Taller de PHP', 'php', '10/05/2020', 'Anónimo', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'http://www.wiener.edu.pe/manuales/6to-ciclo/PROGRAMACION-WEB-4/Taller-de-PHP.pdf'),
(42, 'Aplicaciones Web', 'php', '10/05/2020', 'Juan Pavón Mestres', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'https://www.fdi.ucm.es/profesor/jpavon/web/35-PHP-MySQL.pdf'),
(43, 'Programador PHP', 'php', '10/05/2020', 'Eugenia Bahit', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'http://46.101.4.154/Libros/El%20lenguaje%20PHP.pdf'),
(44, 'Ejemplos php', 'php', '10/05/2020', 'Eugenia Bahit', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'http://www.iesvelazquez.org/wiki/mediawiki1610/images/5/55/Ejercicios_php.pdf');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libro_teoria`
--

CREATE TABLE `libro_teoria` (
  `id_teoria` int(11) NOT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `id_ejercicios` int(11) DEFAULT NULL,
  `id_video` int(11) DEFAULT NULL,
  `tematica` varchar(100) DEFAULT NULL,
  `fecha` varchar(20) DEFAULT NULL,
  `autor` varchar(50) DEFAULT NULL,
  `portada` varchar(250) DEFAULT NULL,
  `url_pdf` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `libro_teoria`
--

INSERT INTO `libro_teoria` (`id_teoria`, `titulo`, `id_ejercicios`, `id_video`, `tematica`, `fecha`, `autor`, `portada`, `url_pdf`) VALUES
(20, 'Creación de clases', 1, NULL, 'java', '29/04/2020', 'Samuel', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://www.africau.edu/images/default/sample.pdf'),
(21, 'Fundamentos de la programación', NULL, NULL, 'java', '01/05/2020', 'Jorge Martínz', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'https://www.tesuva.edu.co/phocadownloadpap/Fundamentos%20de%20programcion%20en%20Java.pdf'),
(22, 'Java POO', NULL, NULL, 'java', '02/05/2020', 'Luis Guerra', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://laurel.datsi.fi.upm.es/_media/docencia/cursos/java/2010/poocolor.pdf'),
(23, 'Documentación Java', NULL, NULL, 'java', '10/05/2020', 'Iván Párraga García', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://dis.um.es/~bmoros/privado/bibliografia/cursoJava.pdf'),
(24, 'Concurrencia', NULL, NULL, 'java', '10/05/2020', 'Anónimo', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://informatica.uv.es/iiguia/LP/teoria/apuntes/cuatr1/tema3_2_concurrencia2.pdf'),
(25, 'Vectores y matrices', NULL, NULL, 'java', '10/05/2020', 'Fernando Berzal', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'https://elvex.ugr.es/decsai/java/pdf/6A-Arrays.pdf'),
(26, 'Aplicaciones Web con Java', NULL, NULL, 'java', '10/05/2020', 'Macario Polo', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://www.inf-cr.uclm.es/www/mpolo/asig/0708/tutorJavaWeb.pdf'),
(27, 'Manejo básico de archivos', NULL, NULL, 'java', '10/05/2020', 'Macario Polo', 'https://i.ibb.co/WVgkhyn/portadajava.jpg', 'http://ocw.udl.cat/enginyeria-i-arquitectura/programacio-2/continguts-1/4-manejo-bai81sico-de-archivos-en-java.pdf'),
(28, 'Python para principiantes', NULL, NULL, 'python', '01/05/2020', 'Eugenia Bahit', 'https://i.ibb.co/DWzvFFb/python.png', 'https://www.iaa.csic.es/python/curso-python-para-principiantes.pdf'),
(29, 'Python para todos', NULL, NULL, 'python', '10/05/2020', 'Charles R. Servence', 'https://i.ibb.co/DWzvFFb/python.png', 'http://do1.dr-chuck.com/pythonlearn/ES_es/pythonlearn.pdf'),
(30, 'Logica Python', NULL, NULL, 'python', '10/05/2020', 'Allen Downey', 'https://i.ibb.co/DWzvFFb/python.png', 'https://argentinaenpython.com/quiero-aprender-python/aprenda-a-pensar-como-un-programador-con-python.pdf'),
(31, 'Explorando Información', NULL, NULL, 'python', '10/05/2020', 'Charles Severance', 'https://i.ibb.co/DWzvFFb/python.png', 'http://do1.dr-chuck.net/py4inf/ES-es/book.pdf'),
(32, 'Guía de aprendizaje', NULL, NULL, 'python', '10/05/2020', 'Guido van Rossum', 'https://i.ibb.co/DWzvFFb/python.png', 'http://es.tldp.org/Tutoriales/Python/tut.pdf'),
(33, 'Sintaxis Python', NULL, NULL, 'python', '10/05/2020', 'Guillermo Andrée Oliva Mercado', 'https://i.ibb.co/DWzvFFb/python.png', 'http://gandreoliva.org/cursos/arch/Python-sintax.pdf'),
(34, 'Herramientas Python', NULL, NULL, 'python', '10/05/2020', 'D. Juan Pedro García Tendero', 'https://i.ibb.co/DWzvFFb/python.png', 'https://gredos.usal.es/bitstream/handle/10366/120148/TFM_GarciaTenderoJP_Desarrollo.pdf'),
(35, 'Python para economistas', NULL, NULL, 'python', '10/05/2020', 'Randall Romero-Aguilar', 'https://i.ibb.co/DWzvFFb/python.png', 'http://www.secmca.org/wp-content/uploads/2019/02/articulo93NOV2017.pdf'),
(36, 'Curso programación C++', NULL, NULL, 'cplus', '01/05/2020', 'Sergio Talens', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://www.uv.es/sto/cursos/c++/curso95.pdf'),
(37, 'Curso completo C++', NULL, NULL, 'cplus', '10/05/2020', 'Salvador Pozo Coronado', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://www.cimat.mx/~amor/Academic/Books/Curso_de_C++.pdf'),
(38, 'Arrays', NULL, NULL, 'cplus', '10/05/2020', 'José Luis Alfonzo', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://www.cimat.mx/~pepe/cursos/lenguaje_2010/slides/slide_31.pdf'),
(39, 'Fundamentos de Programación', NULL, NULL, 'cplus', '10/05/2020', 'Facultad Informática', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://www.fdi.ucm.es/profesor/luis/fp/fp.pdf'),
(40, 'Manual de Programación en Lenguaje C++', NULL, NULL, 'cplus', '10/05/2020', 'Linda I. Olivares Flores', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://paginas.matem.unam.mx/pderbf/images/mprogintc++.pdf'),
(41, 'Tutorial de C++', NULL, NULL, 'cplus', '10/05/2020', 'Pello Xabier Altadill Izura', 'https://i.ibb.co/Zx91pTR/cplus.png', 'http://es.tldp.org/Manuales-LuCAS/doc-tutorial-c++/doc-tutorial-c++.pdf'),
(42, 'Aprenda C++', NULL, NULL, 'cplus', '10/05/2020', 'Javier García de Jalón', 'https://i.ibb.co/Zx91pTR/cplus.png', 'http://lsi.vc.ehu.es/pablogn/docencia/manuales/C/Aprenda%20C++%20como%20si%20estuviera%20en%20Primero.pdf'),
(43, 'Guia Sintaxis c++', NULL, NULL, 'cplus', '10/05/2020', 'Anónimo', 'https://i.ibb.co/Zx91pTR/cplus.png', 'https://www.mhe.es/universidad/informatica/8448146433/archivos/c++b.pdf'),
(44, 'Curso JavaScript programación', NULL, NULL, 'javascript', '01/05/2020', 'Anónimo', 'https://i.ibb.co/n8rJNT8/javascript.png', 'http://www.vc.ehu.es/jiwotvim/ISOFT2009-2010/Teoria/BloqueIV/JavaScript.pdf'),
(45, 'Conceptos básicos JS', NULL, NULL, 'javascript', '10/05/2020', 'Rafael Menéndez-Barzanallana Asensio', 'https://i.ibb.co/n8rJNT8/javascript.png', 'https://www.um.es/docencia/barzana/DAWEB/Lenguaje-de-programacion-JavaScript-1.pdf'),
(46, 'JavaScript Manual de referencia', NULL, NULL, 'javascript', '10/05/2020', 'Anónimo', 'https://i.ibb.co/n8rJNT8/javascript.png', 'http://jaimesegundo.edu.gva.es/web_mestre.inf/treball/programacio/JavaScript/teoria/javascript.pdf'),
(47, 'El lenguaje JavaScript', NULL, NULL, 'javascript', '10/05/2020', 'Rafael Menéndez-Barzanallana Asensio', 'https://i.ibb.co/n8rJNT8/javascript.png', 'http://www.vc.ehu.es/jiwotvim/ISOFT2009-2010/Teoria/BloqueIV/JavaScript.pdf'),
(48, 'El gran libro de HTML5, CSS3 y Javascript', NULL, NULL, 'javascript', '10/05/2020', 'Juan Diego', 'https://i.ibb.co/n8rJNT8/javascript.png', 'https://gutl.jovenclub.cu/wp-content/uploads/2013/10/El+gran+libro+de+HTML5+CSS3+y+Javascrip.pdf'),
(49, 'Aplicaciones Web', NULL, NULL, 'php', '01/05/2020', 'Juan Pavón', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'https://www.fdi.ucm.es/profesor/jpavon/web/35-PHP-MySQL.pdf'),
(50, 'Nuesto primer php', NULL, NULL, 'php', '10/05/2020', 'Anónimo', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'https://cerpo.cl/_items/File_002_005_0012.pdf'),
(51, 'POO y MVC en PHP', NULL, NULL, 'php', '10/05/2020', 'Eugenia Bahit', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'http://www1.herrera.unt.edu.ar/biblcet/wp-content/uploads/2014/12/eugeniabahitpooymvcenphp.pdf'),
(52, 'Programación PHP', NULL, NULL, 'php', '10/05/2020', 'Armando Arce', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'https://readthedocs.org/projects/programacion-php/downloads/pdf/latest/'),
(53, 'Taller PHP', NULL, NULL, 'php', '10/05/2020', 'Anónimo', 'https://i.ibb.co/Sr6FWsv/phpp.png', 'http://tutoriales.altervista.org/trabajos/Manual_php_completo.pdf');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tuto_video`
--

CREATE TABLE `tuto_video` (
  `id_video` int(11) NOT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `tematica` varchar(100) DEFAULT NULL,
  `fecha` varchar(20) DEFAULT NULL,
  `url_video` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tuto_video`
--

INSERT INTO `tuto_video` (`id_video`, `titulo`, `tematica`, `fecha`, `url_video`) VALUES
(9, 'Presentación.', 'java', '10/05/2020', 'https://www.youtube.com/embed/coK4jM5wvko?list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk'),
(10, 'Instalación JRE y Eclipse.', 'java', '10/05/2020', 'https://www.youtube.com/embed/F0ILFYl8YgI?list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk'),
(11, ' Estructuras principales I.', 'java', '10/05/2020', 'https://www.youtube.com/embed/Lj97ujkeUXc?list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk'),
(12, 'Manipulación de cadenas.', 'java', '10/05/2020', 'https://www.youtube.com/embed/YMyJLs22uY4?list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk'),
(13, 'Acercamiento a la API Paquetes.', 'java', '10/05/2020', 'https://www.youtube.com/embed/0M6qHYdlqpc?list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk'),
(14, 'Entrada Salida datos I.', 'java', '10/05/2020', 'https://www.youtube.com/embed/Ng0_7uZyIoA?list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk'),
(15, 'Condicionales I.', 'java', '10/05/2020', 'https://www.youtube.com/embed/b2ZtZndiT1Y?list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk'),
(16, 'Bucles I.', 'java', '10/05/2020', 'https://www.youtube.com/embed/HQz8xwAjCsI?list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk'),
(17, 'Introducción.', 'python', '10/05/2020', 'https://www.youtube.com/embed/9ojhJsXNWCI?list=PLU8oAlHdN5BlvPxziopYZRd55pdqFwkeS'),
(18, 'Sintaxis Básica I.', 'python', '10/05/2020', 'https://www.youtube.com/embed/yppT6GPZMyo?list=PLU8oAlHdN5BlvPxziopYZRd55pdqFwkeS'),
(19, 'Condicionales I.', 'python', '10/05/2020', 'https://www.youtube.com/embed/iV-4F0jGWak?list=PLU8oAlHdN5BlvPxziopYZRd55pdqFwkeS'),
(20, 'Bucles I.', 'python', '10/05/2020', 'https://www.youtube.com/embed/GQGhU1526Oo?list=PLU8oAlHdN5BlvPxziopYZRd55pdqFwkeS'),
(21, 'Generadores I.', 'python', '10/05/2020', 'https://www.youtube.com/embed/TLVnoqXGWpY?list=PLU8oAlHdN5BlvPxziopYZRd55pdqFwkeS'),
(22, 'Excepciones I.', 'python', '10/05/2020', 'https://www.youtube.com/embed/2MaAs7XU2T0?list=PLU8oAlHdN5BlvPxziopYZRd55pdqFwkeS'),
(23, 'POO I.', 'python', '10/05/2020', 'https://www.youtube.com/embed/5Ohme4A2Weg?list=PLU8oAlHdN5BlvPxziopYZRd55pdqFwkeS'),
(24, 'POO II.', 'python', '10/05/2020', 'https://www.youtube.com/embed/2UNrSiKEI8w?list=PLU8oAlHdN5BlvPxziopYZRd55pdqFwkeS'),
(25, 'C++ - Introducción', 'cplus', ' 09/05/2020', 'https://www.youtube.com/embed/7x9369_-cWc?list=PL4A486BBFC5AD733B'),
(26, 'Hola Mundo', 'cplus', ' 09/05/2020', 'https://www.youtube.com/embed/ofemmXa_M5o?list=PL4A486BBFC5AD733B'),
(27, 'Variables Enteras', 'cplus', ' 09/05/2020', 'https://www.youtube.com/embed/Fh-7YQq02HQ?list=PL4A486BBFC5AD733B'),
(28, 'Variables Bool y Char', 'cplus', ' 09/05/2020', 'https://www.youtube.com/embed/eoOhncKQdoM?list=PL4A486BBFC5AD733B'),
(29, 'Operadores Lógicos', 'cplus', ' 09/05/2020', 'https://www.youtube.com/embed/4Y0MRHHkHXM?list=PL4A486BBFC5AD733B'),
(30, 'Switch - Case', 'cplus', ' 09/05/2020', 'https://www.youtube.com/embed/df1zLfljUMM?list=PL4A486BBFC5AD733B'),
(31, 'Introducción a Funciones', 'cplus', ' 09/05/2020', 'https://www.youtube.com/embed/ZYCTqYvDEI8?list=PL4A486BBFC5AD733B'),
(32, 'Punteros', 'cplus', ' 09/05/2020', 'https://www.youtube.com/embed/3ILkValvhyI?list=PL4A486BBFC5AD733B'),
(33, 'Sintaxis Básica I. Ubicación del código.', 'javascript', ' 09/05/2020', 'https://www.youtube.com/embed/zSYj95x5WrU?list=PLU8oAlHdN5BmpobVmj1IlneKlVLJ84TID'),
(34, 'POO I.', 'javascript', ' 09/05/2020', 'https://www.youtube.com/embed/6wB-0QSkk3Y?list=PLU8oAlHdN5BmpobVmj1IlneKlVLJ84TID'),
(35, 'Control flujo Condicional If I.', 'javascript', ' 09/05/2020', 'https://www.youtube.com/embed/jI2pp0ILs40?list=PLU8oAlHdN5BmpobVmj1IlneKlVLJ84TID'),
(36, 'Funciones I.', 'javascript', ' 09/05/2020', 'https://www.youtube.com/embed/uUfYLGVLHfI?list=PLU8oAlHdN5BmpobVmj1IlneKlVLJ84TID'),
(37, 'JQuery I.', 'javascript', ' 09/05/2020', 'https://www.youtube.com/embed/VEJZFbjOICs?list=PLU8oAlHdN5BmpobVmj1IlneKlVLJ84TID'),
(38, 'JQuery III.', 'javascript', ' 09/05/2020', 'https://www.youtube.com/embed/vovHwdwSUCY?list=PLU8oAlHdN5BmpobVmj1IlneKlVLJ84TID'),
(39, 'JQuery IV. Agregando contenido.', 'javascript', ' 09/05/2020', 'https://www.youtube.com/embed/8ZMPJSf5_GE?list=PLU8oAlHdN5BmpobVmj1IlneKlVLJ84TID'),
(40, 'Efectos y animaciones I.', 'javascript', ' 09/05/2020', 'https://www.youtube.com/embed/6BkKiiuTxo8?list=PLU8oAlHdN5BmpobVmj1IlneKlVLJ84TID'),
(41, 'Variables y comentarios.', 'php', ' 09/05/2020', 'https://www.youtube.com/embed/Ja9UVEgAzEw?list=PLU8oAlHdN5BkinrODGXToK9oPAlnJxmW_'),
(42, 'Variables estáticas.', 'php', ' 09/05/2020', 'https://www.youtube.com/embed/vfmmIYERvaQ?list=PLU8oAlHdN5BkinrODGXToK9oPAlnJxmW_'),
(43, 'Strings.', 'php', ' 09/05/2020', 'https://www.youtube.com/embed/IRKPW88SoYE?list=PLU8oAlHdN5BkinrODGXToK9oPAlnJxmW_'),
(44, 'Operadores comparación.', 'php', ' 09/05/2020', 'https://www.youtube.com/embed/gYbhDXhJz6E?list=PLU8oAlHdN5BkinrODGXToK9oPAlnJxmW_'),
(45, 'Funciones matemáticas y casting.', 'php', ' 09/05/2020', 'https://www.youtube.com/embed/2UOhNGXkros?list=PLU8oAlHdN5BkinrODGXToK9oPAlnJxmW_'),
(46, 'Bucles I While y Do While.', 'php', ' 09/05/2020', 'https://www.youtube.com/embed/oGC7XMYnAXo?list=PLU8oAlHdN5BkinrODGXToK9oPAlnJxmW_'),
(47, 'Programación Orientada a Objetos POO I.', 'php', ' 09/05/2020', 'https://www.youtube.com/embed/sqKWTBbLJ_M?list=PLU8oAlHdN5BkinrODGXToK9oPAlnJxmW_'),
(48, 'Programación Orientada a Objetos POO II.', 'php', ' 09/05/2020', 'https://www.youtube.com/embed/swtCq7eZh5w?list=PLU8oAlHdN5BkinrODGXToK9oPAlnJxmW_');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nombre_usuario` varchar(50) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `fecha` varchar(20) DEFAULT NULL,
  `foto_perfil` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `email`, `password`, `nombre_usuario`, `telefono`, `fecha`, `foto_perfil`) VALUES
(37, 'samuel.isip26@gmail.com', '$2y$10$hLsbvqH.xDtcBP4eypsRMuUSi5y8eNkULJJgdvpHIpbnObeT8xfaG', 'Samuel Isip', '+34', '27/04/2020', NULL),
(39, 'samuel.isip@gmail.com', '$2y$10$JsnaijZjTZRy2pKXrJr9x.P4s3zkTg0GN8QFZwPJmp6wujCZ17Mn2', 'samuelsamu', '+34 ', '29/04/2020', NULL),
(40, 'samuel.isip62@gmail.com', '$2y$10$k4N4Xs6znn58S5TqZnzJhutNRdsRCMUDlr2.BsPS9GLOjTVDbxj4.', 'Samuel ISP', '+34', '30/04/2020', NULL),
(41, 'isipfamilia@gmail.com', '$2y$10$ngJxKly9k3mnxyfxEy8Np.NLpOV34AxXf7XdAllGHGb6fIP0wNduq', 'Darius 108', '+34', '18/05/2020', NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `amigos`
--
ALTER TABLE `amigos`
  ADD PRIMARY KEY (`id_amigos`);

--
-- Indices de la tabla `foro`
--
ALTER TABLE `foro`
  ADD PRIMARY KEY (`id_foro`),
  ADD KEY `nom` (`id_usuario`);

--
-- Indices de la tabla `libro_ejercicios`
--
ALTER TABLE `libro_ejercicios`
  ADD PRIMARY KEY (`id_ejercicios`);

--
-- Indices de la tabla `libro_teoria`
--
ALTER TABLE `libro_teoria`
  ADD PRIMARY KEY (`id_teoria`),
  ADD KEY `eje` (`id_ejercicios`),
  ADD KEY `vid` (`id_video`);

--
-- Indices de la tabla `tuto_video`
--
ALTER TABLE `tuto_video`
  ADD PRIMARY KEY (`id_video`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `amigos`
--
ALTER TABLE `amigos`
  MODIFY `id_amigos` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `foro`
--
ALTER TABLE `foro`
  MODIFY `id_foro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- AUTO_INCREMENT de la tabla `libro_ejercicios`
--
ALTER TABLE `libro_ejercicios`
  MODIFY `id_ejercicios` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT de la tabla `libro_teoria`
--
ALTER TABLE `libro_teoria`
  MODIFY `id_teoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT de la tabla `tuto_video`
--
ALTER TABLE `tuto_video`
  MODIFY `id_video` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `foro`
--
ALTER TABLE `foro`
  ADD CONSTRAINT `nom` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `libro_teoria`
--
ALTER TABLE `libro_teoria`
  ADD CONSTRAINT `eje` FOREIGN KEY (`id_ejercicios`) REFERENCES `libro_ejercicios` (`id_ejercicios`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `vid` FOREIGN KEY (`id_video`) REFERENCES `tuto_video` (`id_video`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
