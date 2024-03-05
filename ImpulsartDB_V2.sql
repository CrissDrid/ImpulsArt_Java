-- MariaDB dump 10.19  Distrib 10.4.28-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: impulsart_java
-- ------------------------------------------------------
-- Server version	10.4.28-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `despachos`
--

DROP TABLE IF EXISTS `despachos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `despachos` (
  `pk_cod_despacho` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_entrega` date DEFAULT NULL,
  `fecha_venta` date DEFAULT NULL,
  `comprobante` varchar(100) DEFAULT NULL,
  `estado` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pk_cod_despacho`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `despachos`
--

LOCK TABLES `despachos` WRITE;
/*!40000 ALTER TABLE `despachos` DISABLE KEYS */;
/*!40000 ALTER TABLE `despachos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `devoluciones`
--

DROP TABLE IF EXISTS `devoluciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devoluciones` (
  `pk_cod_devolucion` bigint(20) NOT NULL AUTO_INCREMENT,
  `comprobante_reembolso` varchar(200) DEFAULT NULL,
  `total_devolver` int(11) DEFAULT NULL,
  `total_reembolso` int(11) DEFAULT NULL,
  `fecha_devolucion` date DEFAULT NULL,
  PRIMARY KEY (`pk_cod_devolucion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devoluciones`
--

LOCK TABLES `devoluciones` WRITE;
/*!40000 ALTER TABLE `devoluciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `devoluciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domiciliarios`
--

DROP TABLE IF EXISTS `domiciliarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domiciliarios` (
  `pk_cod_domiciliario` bigint(20) NOT NULL AUTO_INCREMENT,
  `entregas_pendientes` int(11) DEFAULT NULL,
  `salarios` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk_cod_domiciliario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domiciliarios`
--

LOCK TABLES `domiciliarios` WRITE;
/*!40000 ALTER TABLE `domiciliarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `domiciliarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleados` (
  `pk_cod_empleado` bigint(20) NOT NULL AUTO_INCREMENT,
  `casos_pendientes` int(11) DEFAULT NULL,
  `salario` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk_cod_empleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidades`
--

DROP TABLE IF EXISTS `especialidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `especialidades` (
  `pk_especialidad` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(150) DEFAULT NULL,
  `nombre_especialidad` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`pk_especialidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidades`
--

LOCK TABLES `especialidades` WRITE;
/*!40000 ALTER TABLE `especialidades` DISABLE KEYS */;
/*!40000 ALTER TABLE `especialidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `obras`
--

DROP TABLE IF EXISTS `obras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `obras` (
  `pk_cod_producto` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL,
  `categoria` varchar(50) NOT NULL,
  `costo` int(11) NOT NULL,
  `descripcion` varchar(155) NOT NULL,
  `imagen` mediumtext DEFAULT NULL,
  `nombre_producto` varchar(200) DEFAULT NULL,
  `peso` varchar(10) NOT NULL,
  `tamano` varchar(10) NOT NULL,
  PRIMARY KEY (`pk_cod_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obras`
--

LOCK TABLES `obras` WRITE;
/*!40000 ALTER TABLE `obras` DISABLE KEYS */;
/*!40000 ALTER TABLE `obras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ofertas`
--

DROP TABLE IF EXISTS `ofertas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ofertas` (
  `pk_cod_oferta` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_oferta` date DEFAULT NULL,
  `hora_oferta` time(6) DEFAULT NULL,
  `monto` int(11) NOT NULL,
  PRIMARY KEY (`pk_cod_oferta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ofertas`
--

LOCK TABLES `ofertas` WRITE;
/*!40000 ALTER TABLE `ofertas` DISABLE KEYS */;
/*!40000 ALTER TABLE `ofertas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos_personalizados`
--

DROP TABLE IF EXISTS `pedidos_personalizados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedidos_personalizados` (
  `pk_cod_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `color` varchar(50) DEFAULT NULL,
  `contenido` varchar(250) NOT NULL,
  `estado_pedido` varchar(80) NOT NULL,
  `materiales` varchar(150) DEFAULT NULL,
  `tamano` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_cod_pedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos_personalizados`
--

LOCK TABLES `pedidos_personalizados` WRITE;
/*!40000 ALTER TABLE `pedidos_personalizados` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedidos_personalizados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pqrs`
--

DROP TABLE IF EXISTS `pqrs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pqrs` (
  `pk_cod_pqrs` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) DEFAULT NULL,
  `estado` varchar(200) DEFAULT NULL,
  `motivo` varchar(200) DEFAULT NULL,
  `respuesta` varchar(200) DEFAULT NULL,
  `fecha_cierre` date DEFAULT NULL,
  `fechapqrs` date DEFAULT NULL,
  PRIMARY KEY (`pk_cod_pqrs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pqrs`
--

LOCK TABLES `pqrs` WRITE;
/*!40000 ALTER TABLE `pqrs` DISABLE KEYS */;
/*!40000 ALTER TABLE `pqrs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrosdespachos`
--

DROP TABLE IF EXISTS `registrosdespachos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registrosdespachos` (
  `pk_cod_registro` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_entrega` date DEFAULT NULL,
  `fecha_salida` date DEFAULT NULL,
  `hora_entrega` time(6) DEFAULT NULL,
  PRIMARY KEY (`pk_cod_registro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrosdespachos`
--

LOCK TABLES `registrosdespachos` WRITE;
/*!40000 ALTER TABLE `registrosdespachos` DISABLE KEYS */;
/*!40000 ALTER TABLE `registrosdespachos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subastas`
--

DROP TABLE IF EXISTS `subastas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subastas` (
  `pk_cod_subasta` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado_subasta` varchar(50) NOT NULL,
  `fecha_finalizacion` date NOT NULL,
  `fecha_inicio` date NOT NULL,
  `precio_inicial` int(11) NOT NULL,
  PRIMARY KEY (`pk_cod_subasta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subastas`
--

LOCK TABLES `subastas` WRITE;
/*!40000 ALTER TABLE `subastas` DISABLE KEYS */;
/*!40000 ALTER TABLE `subastas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipopqrs`
--

DROP TABLE IF EXISTS `tipopqrs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipopqrs` (
  `pk_cod_tipopqrs` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(50) DEFAULT NULL,
  `nombre_tipo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pk_cod_tipopqrs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipopqrs`
--

LOCK TABLES `tipopqrs` WRITE;
/*!40000 ALTER TABLE `tipopqrs` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipopqrs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `pk_identificacion` int(11) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `contrasena` varchar(40) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `num_celular` varchar(15) NOT NULL,
  `tipo_usuario` varchar(50) NOT NULL,
  PRIMARY KEY (`pk_identificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_especialidades`
--

DROP TABLE IF EXISTS `usuarios_especialidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios_especialidades` (
  `fk_identificacion` int(11) NOT NULL,
  `fk_cod_especialidad` int(11) NOT NULL,
  KEY `FKdo5j3jso2ni8hlhdoy7bsl6pu` (`fk_identificacion`),
  KEY `FK60wdbtukqynvcf9mvqqp2hmmc` (`fk_cod_especialidad`),
  CONSTRAINT `FK60wdbtukqynvcf9mvqqp2hmmc` FOREIGN KEY (`fk_cod_especialidad`) REFERENCES `especialidades` (`pk_especialidad`),
  CONSTRAINT `FKdo5j3jso2ni8hlhdoy7bsl6pu` FOREIGN KEY (`fk_identificacion`) REFERENCES `usuarios` (`pk_identificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_especialidades`
--

LOCK TABLES `usuarios_especialidades` WRITE;
/*!40000 ALTER TABLE `usuarios_especialidades` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios_especialidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_obra`
--

DROP TABLE IF EXISTS `usuarios_obra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios_obra` (
  `fk_identificacion` int(11) NOT NULL,
  `fk_cod_producto` int(11) NOT NULL,
  KEY `FKl17pt29ii0n08568xw6j1sqph` (`fk_cod_producto`),
  KEY `FKpbogjh1y4croyrlh3xkovrjka` (`fk_identificacion`),
  CONSTRAINT `FKl17pt29ii0n08568xw6j1sqph` FOREIGN KEY (`fk_cod_producto`) REFERENCES `obras` (`pk_cod_producto`),
  CONSTRAINT `FKpbogjh1y4croyrlh3xkovrjka` FOREIGN KEY (`fk_identificacion`) REFERENCES `usuarios` (`pk_identificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_obra`
--

LOCK TABLES `usuarios_obra` WRITE;
/*!40000 ALTER TABLE `usuarios_obra` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios_obra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_pedido`
--

DROP TABLE IF EXISTS `usuarios_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios_pedido` (
  `fk_identificacion` int(11) NOT NULL,
  `fk_cod_pedido` int(11) NOT NULL,
  KEY `FKbxvhrqls7jooomilv7tuyan4w` (`fk_cod_pedido`),
  KEY `FK7t4ij3tcjijb3dy7m700sj802` (`fk_identificacion`),
  CONSTRAINT `FK7t4ij3tcjijb3dy7m700sj802` FOREIGN KEY (`fk_identificacion`) REFERENCES `usuarios` (`pk_identificacion`),
  CONSTRAINT `FKbxvhrqls7jooomilv7tuyan4w` FOREIGN KEY (`fk_cod_pedido`) REFERENCES `pedidos_personalizados` (`pk_cod_pedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_pedido`
--

LOCK TABLES `usuarios_pedido` WRITE;
/*!40000 ALTER TABLE `usuarios_pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_ventas`
--

DROP TABLE IF EXISTS `usuarios_ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios_ventas` (
  `fk_identificacion` int(11) NOT NULL,
  `fk_cod_venta` int(11) NOT NULL,
  KEY `FKb8csopeb3umohqtd0p3bo18jn` (`fk_cod_venta`),
  KEY `FK6exk2g3b6gbyxm95opuvwc41y` (`fk_identificacion`),
  CONSTRAINT `FK6exk2g3b6gbyxm95opuvwc41y` FOREIGN KEY (`fk_identificacion`) REFERENCES `usuarios` (`pk_identificacion`),
  CONSTRAINT `FKb8csopeb3umohqtd0p3bo18jn` FOREIGN KEY (`fk_cod_venta`) REFERENCES `ventas` (`pk_cod_venta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_ventas`
--

LOCK TABLES `usuarios_ventas` WRITE;
/*!40000 ALTER TABLE `usuarios_ventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios_ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehiculos` (
  `pk_placa` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad_vehiculo` int(11) DEFAULT NULL,
  `tipo_vehiculo` varchar(255) DEFAULT NULL,
  `marca` varchar(255) DEFAULT NULL,
  `modelo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_placa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos`
--

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ventas` (
  `pk_cod_venta` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL,
  `fecha_venta` datetime(6) NOT NULL,
  `metodo_pago` varchar(50) NOT NULL,
  `recibo_venta` varchar(150) NOT NULL,
  `total_pago` int(11) NOT NULL,
  `fk_cod_producto` int(11) NOT NULL,
  PRIMARY KEY (`pk_cod_venta`),
  KEY `FKr3r729u660l5irdonnsqlhcbl` (`fk_cod_producto`),
  CONSTRAINT `FKr3r729u660l5irdonnsqlhcbl` FOREIGN KEY (`fk_cod_producto`) REFERENCES `obras` (`pk_cod_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-04 22:00:48
