-- MySQL dump 10.13  Distrib 5.5.40, for osx10.9 (x86_64)
--
-- Host: localhost    Database: project_centre
-- ------------------------------------------------------
-- Server version	5.5.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authz_role`
--

DROP TABLE IF EXISTS `authz_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authz_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL,
  `roleName` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `authz_role_ibfk_1` (`personId`),
  CONSTRAINT `authz_role_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `person` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Map authorization roles to persons';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authz_role`
--

LOCK TABLES `authz_role` WRITE;
/*!40000 ALTER TABLE `authz_role` DISABLE KEYS */;
INSERT INTO `authz_role` VALUES (1,4,'ROLE_ADMIN');
/*!40000 ALTER TABLE `authz_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `division`
--

DROP TABLE IF EXISTS `division`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `division` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_code` (`code`),
  KEY `K_parentId` (`parentId`),
  CONSTRAINT `K_parentId` FOREIGN KEY (`parentId`) REFERENCES `division` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8 COMMENT='List of all possible affiliations of a person or a project';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `division`
--

LOCK TABLES `division` WRITE;
/*!40000 ALTER TABLE `division` DISABLE KEYS */;
INSERT INTO `division` VALUES (1,'uoa','University of Auckland',NULL),(2,'uoo','University of Otago',NULL),(3,'aut','Auckland University of Technology',NULL),(4,'landcare','Landcare Research',NULL),(5,'vuw','Victoria University of Wellington',NULL),(6,'pfr','Plant & Food Research',NULL),(7,'massey','Massey University',NULL),(8,'uow','University of Waikato',NULL),(9,'uwa','University of Western Australia',NULL),(10,'adelaide','University of Adelaide',NULL),(11,'nyu','New York University',NULL),(12,'uc','University of Canterbury',NULL),(13,'gns','GNS Science',NULL),(14,'anl','Argonne National Laboratory',NULL),(15,'lincoln','Lincoln University',NULL),(16,'metservice','Meteorological Service of New Zealand',NULL),(17,'callaghan','Callaghan Innovation',NULL),(18,'usyd','University of Sydney',NULL),(19,'niwa','National Institute of Water and Atmospheric Research',NULL),(20,'esr','Institute of Environmental Science and Research',NULL),(21,'lic','Livestock Improvement Corporation',NULL),(22,'scion','Scion',NULL),(23,'other','Other',NULL),(24,'monash','Monash University',NULL),(25,'aberystwyth','Aberystwyth University',NULL),(26,'doc','Department of Conservation',NULL),(27,'nasajpl','NASA Jet Propulsion Laboratory',NULL),(28,'iitg','Indian Institute of Technology Guwahati',NULL),(29,'oslo','University of Oslo',NULL),(30,'nesi','NZ eScience Infrastructure',NULL),(31,'bionics','The Bionics Institute',NULL),(32,'stockholm','Stockholm University',NULL),(33,'agresearch','AgResearch',NULL),(34,'nzgl','New Zealand Genomics Limited',NULL),(35,'bods','Bodeker Scientific',NULL),(36,'caldera','Caldera Health',NULL),(37,'compac','Compac Sorting',NULL),(38,'aquac','Aquacoustics',NULL),(39,'fos','Faculty of Science',1),(40,'foe','Faculty of Engineering',1),(41,'fmhs','Faculty of Medical and Health Sciences',1),(42,'foed','Faculty of Education and Social Work',1),(43,'liggins','Liggins Institute',1),(44,'dos','Division of Sciences',2),(45,'dohs','Division of Health Sciences',2),(46,'rg','Research Groups',5),(47,'vuwfos','Faculty of Science',5),(48,'vbs','Victoria Business School',5),(49,'bprc','Bio-Protection Research Centre',7),(50,'nctl','National Centre for Teaching and Learning',7),(51,'masseycos','College of Sciences',7),(52,'cor','Centres of Research',7),(53,'ins','Institute of Natural Sciences',7),(54,'fse','Faculty of Science and Engineering',8),(55,'fcm','Faculty of Computing and Mathematics',8),(56,'ccpp','Centre for Cosmology and Particle Physics',11),(57,'uccos','College of Science',12),(58,'bluefern','Bluefern Team',12),(59,'agrilife','Agriculture and Life Sciences',15),(60,'commerce','Commerce',15),(61,'society','Environment, Society and Design',15),(62,'engineering','College of Engineering',12),(63,'arts','College of Arts',12),(64,'businesslaw','College of Business and Law',12),(65,'education','College of Education',12),(66,'foa','Faculty of Arts',1),(67,'its','ITS',1),(68,'pharma','Faculty of Pharmacy and Pharmaceutical Sciences',24),(69,'geo','Department of Geography and Earth Sciences',25),(70,'fad','Faculty of Architecture and Design',5),(71,'vetsci','Institute of Veterinary, Animal & Biomedical Sciences',7),(72,'sbs','Stockholm Business School',32),(73,'freshwater','Freshwater Science',19),(74,'uoaedu','Faculty of Education',1),(75,'arcbio','ARC Centre of Excellence in Plant Energy Biology',9),(76,'b&e','Faculty of Business and Economics',1),(77,'bii','Bioinformatics Institute',39),(78,'ceres','Centre for eResearch',39),(79,'compsci','Department of Computer Science',39),(80,'math','Department of Mathematics',39),(81,'phys','Department of Physics',39),(82,'psych','Department of Psychology',39),(83,'sport','Department of Sport and Exercise Science',39),(84,'stat','Department of Statistics',39),(85,'iese','Institute of Earth Science and Engineering',39),(86,'lmrc','Light Metals Research Centre',39),(87,'sobs','School of Biological Sciences',39),(88,'chem','School of Chemical Sciences',39),(89,'env','School of Environment',39),(90,'marine','Leigh Marine Laboratory',39),(91,'optometry','Department of Optometry and Vision Science',39),(92,'cacm','Centre for Advanced Composite Materials',40),(93,'cee','Department of Civil and Environmental Engineering',40),(94,'ece','Department of Electrical and Computer Engineering',40),(95,'engsci','Department of Engineering Science',40),(96,'mech','Department of Mechanical Engineering',40),(97,'se','Department of Software Engineering',40),(98,'chemeng','Department of Chemical and Materials Engineering',40),(99,'abi','Auckland Bioengineering Institute',40),(100,'acsrc','Auckland Cancer Society Research Centre',41),(101,'sms','School of Medical Sciences',41),(102,'sm','School of Medicine',41),(103,'sph','School of Population Health',41),(104,'pharmacy','School of Pharmacy',41),(105,'anaest','Anaesthesiology',41),(106,'optvissci','Optometry & Vision Science',41),(107,'cap','Curriculum and Pedagogy',42),(108,'uoocompsci','Department of Computer Science',44),(109,'marinesci','Department of Marine Science',44),(110,'mathstats1','Department of Mathematics and Statistics',44),(111,'phys2','Department of Physics',44),(112,'zoo','Department of Zoology',44),(113,'otagochem','Department of Chemistry',44),(114,'biochem','Department of Biochemistry',45),(115,'gemmell','Gemmell Lab',45),(116,'phgp','Public Health and General Practice',45),(117,'pharmacy2','School of Pharmacy',45),(118,'dsm','Dunedin School of Medicine',45),(119,'popl','Department of Population Health',45),(120,'nanotech','Computational Nanotechnology',46),(121,'esnm','Electronic Structure of Nanomaterials',46),(122,'chemphys','School of Chemical and Physical Sciences',47),(123,'sim','School of Information Management',48),(124,'ifs','Institute of Fundamental Sciences',51),(125,'imb','Institute of Molecular Biosciences',51),(126,'imbs','IMBS BioMedical Research',52),(127,'ctcp','Centre for Theoretical Chemistry and Physics',53),(128,'waikatochem','Department of Chemistry',54),(129,'waikatocompsci','Department of Computer Science',55),(130,'ccchem','Department of Chemistry',57),(131,'biosci','Department of Biological Sciences',57),(132,'geography','Department of Geography',57),(133,'geosci','Department of Geological Sciences',57),(134,'phys3','Department of Physics and Astronomy',57),(135,'psychology','Department of Psychology',57),(136,'commdisorder','Department of Communications Disorders',57),(137,'waterways','Waterways Centre for Freshwater Management',57),(138,'agrisci','Department of Agricultural Sciences',59),(139,'ecology','Department of Ecology',59),(140,'soil','Department of Soil and Physical Sciences',59),(141,'wine','Department of Wine, Food and Molecular Biosciences',59),(142,'economics','Accounting, Economics and Finance Department',60),(143,'agriprops','Agricultural Management and Property Studies Department',60),(144,'business','Business Management, Law and Marketing Department',60),(145,'appcomputing','Department of Applied Computing',61),(146,'envmanag','Department of Environmental Management',61),(147,'softeng','Department of Computer Science and Software Engineering',62),(148,'chemproceng','Department of Chemical and Process Engineering',62),(149,'naturaleng','Department of Civil and Natural Resources Engineering',62),(150,'compeng','Department of Electrical and Computer Engineering',62),(151,'mathstats2','Department of Mathematics and Statistics',62),(152,'mecheng','Department of Mechanical Engineering',62),(153,'forestry','School of Forestry',62),(154,'anthro','Department of Anthropology',66),(155,'arch','School of Architecture',70),(156,'mepilab','mEpiLab',71),(157,'finance','Finance',72),(158,'hydro','Hydrological Processes',73),(159,'ped','Curriculum and Pedagogy',74),(160,'isom','Information Systems and Operations Management',76);
/*!40000 ALTER TABLE `division` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `division_children`
--

DROP TABLE IF EXISTS `division_children`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `division_children` (
  `divisionId` int(11) NOT NULL,
  `childId` int(11) DEFAULT NULL,
  KEY `division_children_ibfk_1` (`divisionId`),
  CONSTRAINT `division_children_ibfk_1` FOREIGN KEY (`divisionId`) REFERENCES `division` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='List of children of a division';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `division_children`
--

LOCK TABLES `division_children` WRITE;
/*!40000 ALTER TABLE `division_children` DISABLE KEYS */;
INSERT INTO `division_children` VALUES (1,39),(1,40),(1,41),(1,42),(1,43),(2,44),(2,45),(5,46),(5,47),(5,48),(7,49),(7,50),(7,51),(7,52),(7,53),(8,54),(8,55),(11,56),(12,57),(12,58),(15,59),(15,60),(15,61),(12,62),(12,63),(12,64),(12,65),(1,66),(1,67),(24,68),(25,69),(5,70),(7,71),(32,72),(19,73),(1,74),(9,75),(1,76),(39,77),(1,77),(39,78),(1,78),(39,79),(1,79),(39,80),(1,80),(39,81),(1,81),(39,82),(1,82),(39,83),(1,83),(39,84),(1,84),(39,85),(1,85),(39,86),(1,86),(39,87),(1,87),(39,88),(1,88),(39,89),(1,89),(39,90),(1,90),(39,91),(1,91),(40,92),(1,92),(40,93),(1,93),(40,94),(1,94),(40,95),(1,95),(40,96),(1,96),(40,97),(1,97),(40,98),(1,98),(40,99),(1,99),(41,100),(1,100),(41,101),(1,101),(41,102),(1,102),(41,103),(1,103),(41,104),(1,104),(41,105),(1,105),(41,106),(1,106),(42,107),(1,107),(44,108),(2,108),(44,109),(2,109),(44,110),(2,110),(44,111),(2,111),(44,112),(2,112),(44,113),(2,113),(45,114),(2,114),(45,115),(2,115),(45,116),(2,116),(45,117),(2,117),(45,118),(2,118),(45,119),(2,119),(46,120),(5,120),(46,121),(5,121),(47,122),(5,122),(48,123),(5,123),(51,124),(7,124),(51,125),(7,125),(52,126),(7,126),(53,127),(7,127),(54,128),(8,128),(55,129),(8,129),(57,130),(12,130),(57,131),(12,131),(57,132),(12,132),(57,133),(12,133),(57,134),(12,134),(57,135),(12,135),(57,136),(12,136),(57,137),(12,137),(59,138),(15,138),(59,139),(15,139),(59,140),(15,140),(59,141),(15,141),(60,142),(15,142),(60,143),(15,143),(60,144),(15,144),(61,145),(15,145),(61,146),(15,146),(62,147),(12,147),(62,148),(12,148),(62,149),(12,149),(62,150),(12,150),(62,151),(12,151),(62,152),(12,152),(62,153),(12,153),(66,154),(1,154),(70,155),(5,155),(71,156),(7,156),(72,157),(32,157),(73,158),(19,158),(74,159),(1,159),(76,160),(1,160);
/*!40000 ALTER TABLE `division_children` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `divisional_role`
--

DROP TABLE IF EXISTS `divisional_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `divisional_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='List of roles in a division a person can have';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `divisional_role`
--

LOCK TABLES `divisional_role` WRITE;
/*!40000 ALTER TABLE `divisional_role` DISABLE KEYS */;
INSERT INTO `divisional_role` VALUES (4,'Intern'),(3,'Non-PhD Student'),(2,'PhD Student'),(1,'Staff or Post Doc'),(5,'Visitor');
/*!40000 ALTER TABLE `divisional_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facility`
--

DROP TABLE IF EXISTS `facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facility` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='List of available clusters/facilities';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facility`
--

LOCK TABLES `facility` WRITE;
/*!40000 ALTER TABLE `facility` DISABLE KEYS */;
INSERT INTO `facility` VALUES (8,'Beatrice'),(5,'BeSTGRID'),(4,'BlueGene'),(10,'CeR VM-Farm'),(2,'FitzRoy'),(9,'Kerr'),(1,'Pan'),(3,'Power7'),(7,'Unknown'),(6,'Visualisation Cluster');
/*!40000 ALTER TABLE `facility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `identity`
--

DROP TABLE IF EXISTS `identity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `identity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `service` varchar(100) NOT NULL,
  `token` varchar(100) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `expires` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_service_username` (`service`,`username`),
  KEY `identity_ibfk_1` (`personId`),
  CONSTRAINT `identity_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `person` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `identity`
--

LOCK TABLES `identity` WRITE;
/*!40000 ALTER TABLE `identity` DISABLE KEYS */;
INSERT INTO `identity` VALUES (1,4,'mfel395','cer_project_db','$2a$08$J2kp//SuTzvc7Pn7N8H3SO2lXfCUfkLrzoSXzkWlSZ2/Zd/aQTkS2','2016-03-02 01:55:16','2018-12-31 11:00:00');
/*!40000 ALTER TABLE `identity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kpi`
--

DROP TABLE IF EXISTS `kpi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kpi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL,
  `type` varchar(100) NOT NULL,
  `title` text NOT NULL,
  `measures` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_number_type` (`number`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='List of key performance indicators';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kpi`
--

LOCK TABLES `kpi` WRITE;
/*!40000 ALTER TABLE `kpi` DISABLE KEYS */;
INSERT INTO `kpi` VALUES (1,2,'NESI','Grow advanced skills that can apply high-tech capabilities to challenging research questions','# of NeSI users report significant benefit to any project from services'),(2,8,'NESI','Number of users with computations scaled up through parallelisation of code','% of CPU core hour utilisation categorised by System Threshold (where thresholds are inherent within each computing system e.g. Core, CPU, Board, Node, Partition and CPU core hour utilisation is calculated by categorising each Job as withi a System Threshold)'),(3,9,'NESI','Increase in throughput of computations','# of NeSI users get on average an order of magnitude scale up (measured by scale-up of any limiting factor as a power of two is for use with any service that achieves improvement for any current limiting factor(s) of a user. Measured as a power of 2 (i.e. an order of magnitude in base 2), this can reflect a scale up to more CPUs, or it could relate to memory, input/output or other factors in order to increase the throughput of computations');
/*!40000 ALTER TABLE `kpi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kpicategory`
--

DROP TABLE IF EXISTS `kpicategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kpicategory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kpiId` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `kpiId` (`kpiId`),
  CONSTRAINT `kpicategory_ibfk_1` FOREIGN KEY (`kpiId`) REFERENCES `kpi` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COMMENT='List of kpi sub-categories';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kpicategory`
--

LOCK TABLES `kpicategory` WRITE;
/*!40000 ALTER TABLE `kpicategory` DISABLE KEYS */;
INSERT INTO `kpicategory` VALUES (1,3,'throughput'),(2,3,'cpucores'),(3,3,'memory'),(4,3,'optimization'),(5,3,'diskspace'),(6,3,'gpu');
/*!40000 ALTER TABLE `kpicategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullName` varchar(100) DEFAULT NULL,
  `preferredName` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `pictureUrl` varchar(300) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `notes` text,
  `statusId` int(11) DEFAULT NULL,
  `lastModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'A unix timestamp indicating the last time the person was edited',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='Basic information about a person';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Jackman Lin','','jackmanlin0@gmail.com','+64 (0) 9 373 7599','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-03-19','2014-11-22','adsfadsfad',2,'2016-06-20 22:07:06'),(2,'Grant Covic','','ga.covic@auckland.ac.nz','+64 (0) 9 923 8102','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-03-19','2014-11-21','',2,'2016-03-02 01:55:07'),(3,'Gene Soudlenkov',NULL,'g.soudlenkov@auckland.ac.nz','ext 89834','https://web.ceres.auckland.ac.nz/project_management/pics/gene-soudlenkov.jpg','2010-09-01',NULL,'',1,'2016-03-02 01:55:07'),(4,'Martin Feller',NULL,'m.feller@auckland.ac.nz','+64 (0) 9 923 2099 ext 82099','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2011-09-07',NULL,'',1,'2016-06-20 18:19:29'),(5,'Bart Verleye',NULL,'b.verleye@auckland.ac.nz','+64 (0)9 9239740 ext 89740','http://img1.wikia.nocookie.net/__cb20140207172458/simpsons/images/6/65/Bart_Simpson.png','2013-09-04','2015-03-31','',2,'2016-03-02 01:55:07'),(6,'Yechun Zhang','George','yechun.zhang@auckland.ac.nz','+64 (0) 9 373 7599','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-05-01','2016-05-01','',1,'2016-03-02 01:55:07'),(7,'Brent Young','','b.young@auckland.ac.nz','+64 (9) 923-5606','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-05-01','2016-05-01','',1,'2016-03-02 01:55:07'),(8,'Benjamin Roberts',NULL,'ben.roberts@nesi.org.nz','+64 (0) 9 923 6895','https://web.ceres.auckland.ac.nz/project_management/pics/ben-roberts.png','2012-02-05',NULL,'',1,'2016-03-02 01:55:07'),(9,'Chris Van Houtte','','chris.van-houtte@auckland.ac.nz','87216','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2013-04-30','2017-04-29','',1,'2016-03-02 01:55:07'),(10,'Thomas Larkin','','t.larkin@auckland.ac.nz','+64 (0) 9 923 8183','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2013-10-30','2017-10-30','Tam',1,'2016-03-02 01:55:07'),(11,'John Walter Rugis',NULL,'j.rugis@auckland.ac.nz','','https://www.cs.auckland.ac.nz/~john-rugis/image/jr_sm.jpg','2013-09-11',NULL,'See http://cluster.ceres.auckland.ac.nz/pm/html/viewresearcher?id=136',1,'2016-03-02 01:55:07'),(12,'Ashley Hinton','','ahin017@aucklanduni.ac.nz','Ext. 85392','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-06-19','2016-06-19','',1,'2016-03-02 01:55:07'),(13,'Paul Murrell','','p.murrell@auckland.ac.nz','ext 85392','https://www.stat.auckland.ac.nz/images/people/paulmurrell.jpg','2015-05-28','2017-05-28','None',1,'2016-03-02 01:55:07'),(14,'Sina Masoud-Ansari',NULL,'s.ansari@auckland.ac.nz','ext 89369','https://web.ceres.auckland.ac.nz/project_management/pics/sina-masoud-ansari.jpg','2011-12-01',NULL,'<script>\r\n$( document ).ready(function() {\r\n  $(\"#myTable>tbody>tr\").filter(\":not(:contains(Primary Adviser)),:contains(Closed)\").hide();\r\n  console.log(\"Filtering is enabled\");\r\n});\r\n</script>',1,'2016-03-02 01:55:07'),(15,'Mark Gahegan','','m.gahegan@auckland.ac.nz','018','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2012-02-01','2018-02-01','',1,'2016-03-02 01:55:07'),(16,'Benjamin Adams','Ben','b.adams@auckland.ac.nz','+64 9 373 7599 ext 89007','https://wiki.auckland.ac.nz/download/attachments/54198310/small_ben_pic.jpg','2014-07-03','2016-07-03','',1,'2016-03-02 01:55:07'),(17,'Richard Hosking','','r.hosking@auckland.ac.nz','0211574197','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-09-08','2016-10-07','',1,'2016-03-02 01:55:07'),(18,'Simon Holdaway','','sj.holdaway@auckland.ac.nz','+ 64 9 373 7599','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-08-04','2016-08-04','',1,'2016-03-02 01:55:07'),(19,'Josh Emmitt','','josh.emmitt@auckland.ac.nz','+64 (0) 9 373 7599','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-09-30','2016-09-30','',1,'2016-03-02 01:55:07'),(20,'Mark Battley','','m.battley@auckland.ac.nz','+64 9 923 9055','http://www.mech.auckland.ac.nz/webdav/site/mech/shared/about/our-staff/images/cp-mark-battley.jpg','2013-08-07','2017-08-07','',1,'2016-03-02 01:55:07'),(21,'Henry Ling','','henry.ling@auckland.ac.nz','0277012741 ','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-06-10','2016-07-09','',1,'2016-03-02 01:55:07'),(22,'Zhenrong Chen','Jeremy','zhenrong.chen@auckland.ac.nz','+64 9 373 7599 x83820','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-11-24','2016-11-24','',1,'2016-03-02 01:55:07'),(23,'Karl Stol','','k.stol@auckland.ac.nz','+64 (0) 9 923 9671  ext 89671','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-11-24','2016-11-24','',1,'2016-03-02 01:55:07'),(24,'Brian Mace','','b.mace@auckland.ac.nz','ext 88145','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-11-24','2016-11-24','',1,'2016-03-02 01:55:07'),(25,'Donna Rose Addis','','d.addis@auckland.ac.nz','+64 9 923 8552','http://www.psych.auckland.ac.nz/webdav/site/psych/shared/about/our-people/images/cp-donna-addis.jpg','2013-08-07','2017-08-07','',1,'2016-03-02 01:55:07'),(26,'Gjurgjica Badzakova Trajkov','','g.badzakova@auckland.ac.nz','0211555951','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-09-19','2016-10-18','None',1,'2016-03-02 01:55:07'),(27,'Aleea Devitt','','aleea.devitt@auckland.ac.nz','+64 (0) 9 373 7599','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-03','2016-12-03','',1,'2016-03-02 01:55:07'),(28,'Kristina Wiebels','','kwie508@aucklanduni.ac.nz','+64 (0) 9 373 7599','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-03','2016-12-03','',1,'2016-03-02 01:55:07'),(29,'Laura Ewens','','laura.ewens@auckland.ac.nz','+64 (0) 9 373 7599','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-03','2016-12-03','',1,'2016-03-02 01:55:07'),(30,'Lynette Tippett','','l.tippett@auckland.ac.nz','ext 88551','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-03','2016-12-03','',1,'2016-03-02 01:55:07'),(31,'Reece Roberts','','r.roberts@auckland.ac.nz','ext 86793','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-03','2016-12-03','',1,'2016-03-02 01:55:07'),(32,'Sylvia Hach','','s.hach@auckland.ac.nz','ext 85711','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-03','2016-12-03','',1,'2016-03-02 01:55:07'),(33,'Eleanor Moloney','','emol572@aucklanduni.ac.nz','+64 (0) 9 373 7599','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-03','2016-12-03','',1,'2016-03-02 01:55:07'),(34,'Peter Yoo','','syoo066@aucklanduni.ac.nz','x83072','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-05','2016-12-05','',1,'2016-03-02 01:55:07'),(35,'Inge Strauss','','istr005@aucklanduni.ac.nz','x83072','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-05','2016-12-05','',1,'2016-03-02 01:55:07'),(36,'Ding Cheng Peng','','dpen466@aucklanduni.ac.nz','x83072','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-05','2016-12-05','',1,'2016-03-02 01:55:07'),(37,'Rachael Sumner','','rsum009@aucklanduni.ac.nz','x83072','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2014-12-05','2016-12-05','',1,'2016-03-02 01:55:07'),(38,'John Thompson','','j.thompson@auckland.ac.nz','+64 9 923 6433','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2015-03-23','2017-03-23','',1,'2016-03-02 01:55:07'),(39,'Markus Binsteiner',NULL,'m.binsteiner@auckland.ac.nz','ext 83089','https://web.ceres.auckland.ac.nz/project_management/pics/markus-binsteiner.jpg','2010-10-01',NULL,'',1,'2016-03-02 01:55:07'),(40,'Robert Burrowes',NULL,'r.burrowes@auckland.ac.nz','ext 82308','https://web.ceres.auckland.ac.nz/project_management/pics/rob-burrowes.jpg','2012-05-01',NULL,'',1,'2016-03-02 01:55:07'),(41,'Alireza Nasiri Minab','','alireza.nasiri-minab@auckland.ac.nz','+64221923874','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2015-03-11','2017-07-20','None',1,'2016-03-02 01:55:07'),(42,'Sing Kiong Nguang','','sk.nguang@auckland.ac.nz','+64 9 923 9421 ext 89421','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2015-07-20','2017-07-20','',1,'2016-03-02 01:55:07'),(43,'Morteza Biglari-Abhari','','m.abhari@auckland.ac.nz','+64 9 923 4534 ext 84534','https://web.ceres.auckland.ac.nz/project_management/pics/m-abhari.jpg','2015-07-20','2017-07-20','',1,'2016-03-02 01:55:07'),(44,'Craig Sutherland','','cj.sutherland@auckland.ac.nz','+64 9 923 2514 ext 82514','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2015-09-01','2017-09-01','',1,'2016-03-02 01:55:08'),(45,'Beryl Plimmer','','b.plimmer@auckland.ac.nz','+64 9 923 2285 ext 82285','https://directory.auckland.ac.nz/people/imageraw/b-plimmer/10300624/large','2015-09-01','2017-09-01','',1,'2016-03-02 01:55:08'),(46,'Andrew John McDaid','','andrew.mcdaid@auckland.ac.nz','+64 9 923 1898 ext 81898','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2015-09-25','2017-09-25','',1,'2016-03-02 01:55:08'),(47,'Tobias Looker','','tloo450@aucklanduni.ac.nz','00 N/A','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2015-09-25','2017-09-25','Phone number is not known. Contact his supervisor Andrew John McDaid.<br><br>',1,'2016-03-02 01:55:08'),(48,'Tianyou Hu','','t.hu@auckland.ac.nz','+64 9 373 7999','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2015-11-11','2019-11-10','',1,'2016-03-02 01:55:08'),(49,'Arvind K Tripathi','','a.tripathi@auckland.ac.nz','+64 9 923 4922','https://projects.nesi.org.nz/sites/default/files/nesi_avatar.png','2015-11-11','2017-11-11','',1,'2016-03-02 01:55:08'),(50,'Steffen Klaere','','s.klaere@auckland.ac.nz','+64 9 373 7599 ext 85237','https://www.stat.auckland.ac.nz/images/people/steffenklaere.jpg','2014-06-05','2016-07-16','',1,'2016-03-02 01:55:08'),(51,'Tom Cruise',NULL,'bla@laber.com','1234',NULL,'2016-06-14',NULL,NULL,1,'2016-06-13 21:53:59');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_affiliation`
--

DROP TABLE IF EXISTS `person_affiliation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_affiliation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL,
  `divisionId` int(11) NOT NULL,
  `divisionalRoleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `person_affiliation_ibfk_1` (`personId`),
  KEY `person_affiliation_ibfk_2` (`divisionId`),
  CONSTRAINT `person_affiliation_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `person` (`id`) ON DELETE CASCADE,
  CONSTRAINT `person_affiliation_ibfk_2` FOREIGN KEY (`divisionId`) REFERENCES `division` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='Map divisions to persons';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_affiliation`
--

LOCK TABLES `person_affiliation` WRITE;
/*!40000 ALTER TABLE `person_affiliation` DISABLE KEYS */;
INSERT INTO `person_affiliation` VALUES (1,1,94,2),(2,2,94,1),(3,3,30,1),(4,4,78,1),(5,5,40,1),(6,6,98,2),(7,7,98,1),(8,8,30,1),(9,9,93,2),(10,10,93,1),(11,11,30,1),(12,12,84,2),(13,13,84,1),(14,14,78,1),(15,15,79,1),(16,16,79,1),(17,17,78,2),(18,18,154,1),(19,19,154,2),(20,20,92,1),(21,21,96,2),(22,22,96,2),(23,23,96,1),(24,24,96,1),(25,25,82,1),(26,26,101,1),(27,27,82,2),(28,28,82,1),(29,29,82,2),(30,30,82,1),(31,31,82,1),(32,32,82,1),(33,33,82,2),(34,34,82,3),(35,35,82,3),(36,36,82,3),(37,37,82,3),(38,38,102,1),(39,39,78,1),(40,40,78,1),(41,41,94,2),(42,42,94,1),(43,43,94,1),(44,44,79,2),(45,45,79,1),(46,46,96,1),(47,47,96,3),(48,48,160,1),(49,49,160,1),(50,50,84,1),(51,51,1,4);
/*!40000 ALTER TABLE `person_affiliation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_project`
--

DROP TABLE IF EXISTS `person_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL,
  `projectId` int(11) NOT NULL,
  `personRoleId` int(11) DEFAULT NULL,
  `notes` text,
  PRIMARY KEY (`id`),
  KEY `personId` (`personId`),
  KEY `projectId` (`projectId`),
  CONSTRAINT `person_project_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `person` (`id`) ON DELETE CASCADE,
  CONSTRAINT `person_project_ibfk_2` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='Link persons to projects';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_project`
--

LOCK TABLES `person_project` WRITE;
/*!40000 ALTER TABLE `person_project` DISABLE KEYS */;
INSERT INTO `person_project` VALUES (1,1,1,1,''),(2,2,1,2,''),(3,3,1,8,''),(4,4,1,6,''),(5,6,2,1,''),(6,7,2,2,''),(7,8,2,6,''),(8,9,3,1,''),(9,10,3,2,''),(10,11,3,6,''),(11,12,4,1,''),(12,13,4,2,''),(13,14,4,6,''),(14,15,5,2,''),(15,16,5,1,''),(16,4,5,6,''),(17,15,6,2,''),(18,17,6,1,''),(19,4,6,6,''),(20,18,7,2,''),(21,19,7,1,''),(22,14,7,6,''),(23,20,8,2,''),(24,21,8,1,''),(25,8,8,6,''),(26,22,9,1,''),(27,23,9,2,''),(28,24,9,2,''),(29,4,9,6,''),(30,5,9,7,''),(31,25,10,1,''),(32,26,10,3,''),(33,27,10,3,''),(34,28,10,3,''),(35,29,10,3,''),(36,30,10,3,''),(37,31,10,3,''),(38,32,10,3,''),(39,33,10,3,''),(40,34,10,3,''),(41,35,10,3,''),(42,36,10,3,''),(43,37,10,3,''),(44,14,10,7,''),(45,4,10,6,''),(46,38,11,1,''),(47,39,11,6,''),(48,40,11,7,''),(49,39,12,1,''),(50,39,13,1,''),(51,13,14,1,''),(52,4,14,6,''),(53,39,15,1,''),(54,39,16,1,''),(55,41,17,1,''),(56,42,17,2,''),(57,4,17,6,''),(58,43,18,1,''),(59,4,18,6,''),(60,44,19,1,''),(61,45,19,2,''),(62,4,19,6,''),(63,46,20,2,''),(64,47,20,1,''),(65,4,20,6,''),(66,48,21,1,''),(67,49,21,2,''),(68,4,21,6,''),(69,50,22,1,''),(70,4,22,6,''),(71,4,3,7,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
/*!40000 ALTER TABLE `person_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_properties`
--

DROP TABLE IF EXISTS `person_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL DEFAULT '0',
  `propname` varchar(255) NOT NULL DEFAULT '',
  `propvalue` varchar(255) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `personId` (`personId`),
  CONSTRAINT `person_properties_ibfk_2` FOREIGN KEY (`personId`) REFERENCES `person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=latin1 COMMENT='Person properties not captured in person table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_properties`
--

LOCK TABLES `person_properties` WRITE;
/*!40000 ALTER TABLE `person_properties` DISABLE KEYS */;
INSERT INTO `person_properties` VALUES (1,1,'eppn','flin036@auckland.ac.nz','2016-06-20 05:37:01'),(2,2,'eppn','gcov001@auckland.ac.nz','2015-06-24 22:37:21'),(3,3,'tuakiriSharedToken','FxreQkk5UID8ZzwxpKR9tB7Tw1Q','2014-04-23 05:47:47'),(4,3,'linuxUsername','gsou008','2014-05-22 01:35:01'),(5,3,'linuxUID','5009','2014-05-22 01:35:01'),(6,3,'linuxGID','5000','2014-05-22 01:35:01'),(7,3,'linuxShell','/bin/bash','2014-05-22 01:35:01'),(8,3,'linuxHome','/home/gsou008','2014-05-22 01:35:01'),(9,3,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Gene Soudlenkov FxreQkk5UID8ZzwxpKR9tB7Tw1Q','2014-05-22 01:35:01'),(10,3,'linuxUsername','gene.soudlenkov','2014-05-22 01:35:06'),(11,3,'linuxUID','5526','2014-05-22 01:35:06'),(12,3,'linuxGID','5000','2014-05-22 01:35:06'),(13,3,'linuxShell','/bin/bash','2014-05-22 01:35:06'),(14,3,'linuxHome','/home/gene.soudlenkov','2014-05-22 01:35:06'),(15,3,'DN','/C=TW/O=AP/OU=GRID/CN=Gene Soudlenkov 398470','2014-05-22 01:35:06'),(16,4,'tuakiriSharedToken','g4eVuEfx26afmQq3O3mQdUzdiu8','2014-04-23 05:47:47'),(17,4,'linuxUsername','mfel395','2014-05-22 01:35:02'),(18,4,'linuxUID','5096','2014-05-22 01:35:02'),(19,4,'linuxGID','5000','2014-05-22 01:35:02'),(20,4,'linuxShell','/bin/bash','2014-05-22 01:35:02'),(21,4,'linuxHome','/home/mfel395','2014-05-22 01:35:02'),(22,4,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Martin Feller g4eVuEfx26afmQq3O3mQdUzdiu8','2014-05-22 01:35:02'),(23,4,'linuxUsername','martin.feller','2014-05-22 01:35:03'),(24,4,'linuxUID','5289','2014-05-22 01:35:03'),(25,4,'linuxGID','5000','2014-05-22 01:35:03'),(26,4,'linuxShell','/bin/bash','2014-05-22 01:35:03'),(27,4,'linuxHome','/home/martin.feller','2014-05-22 01:35:03'),(28,4,'DN','/C=TW/O=AP/OU=GRID/CN=Martin Feller 145519','2014-05-22 01:35:03'),(29,4,'eppn','mfel395@auckland.ac.nz','2015-12-14 02:34:59'),(30,5,'tuakiriSharedToken','AzUdrdIpbSlt3YcGyoBIEwp_0AI','2014-04-23 05:47:47'),(31,5,'linuxUsername','bver018','2014-05-22 01:35:07'),(32,5,'linuxUID','5553','2014-05-22 01:35:07'),(33,5,'linuxGID','5000','2014-05-22 01:35:07'),(34,5,'linuxShell','/bin/bash','2014-05-22 01:35:07'),(35,5,'linuxHome','/home/bver018','2014-05-22 01:35:07'),(36,5,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Bart Verleye AzUdrdIpbSlt3YcGyoBIEwp_0AI','2014-05-22 01:35:07'),(37,5,'eppn','bver018@auckland.ac.nz','2015-12-14 02:36:18'),(38,6,'tuakiriSharedToken','9LUppEpyHxzFCd1NGH7pDAj0gyo','2014-09-21 22:22:43'),(39,6,'eppn','yzha759@auckland.ac.nz','2015-06-27 03:33:30'),(40,7,'eppn','byou031@auckland.ac.nz','2015-06-24 22:37:21'),(41,8,'tuakiriSharedToken','6rsRBGKwBJW6Wq2HU2g3_Vrxxfo','2014-04-23 05:47:47'),(42,8,'linuxUsername','brob695','2014-05-22 01:35:02'),(43,8,'linuxUID','5155','2014-05-22 01:35:02'),(44,8,'linuxGID','5000','2014-05-22 01:35:02'),(45,8,'linuxShell','/bin/bash','2014-05-22 01:35:02'),(46,8,'linuxHome','/home/brob695','2014-05-22 01:35:02'),(47,8,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Benjamin Roberts 6rsRBGKwBJW6Wq2HU2g3_Vrxxfo','2014-05-22 01:35:02'),(48,8,'linuxUsername','benjamin.roberts','2014-05-22 01:35:04'),(49,8,'linuxUID','5332','2014-05-22 01:35:04'),(50,8,'linuxGID','5000','2014-05-22 01:35:04'),(51,8,'linuxShell','/bin/bash','2014-05-22 01:35:04'),(52,8,'linuxHome','/home/benjamin.roberts','2014-05-22 01:35:04'),(53,8,'DN','/C=TW/O=AP/OU=GRID/CN=Benjamin Roberts 195011','2014-05-22 01:35:04'),(54,9,'tuakiriSharedToken','ooRfGPsHn6ObPBtvq7cdmH5Q7Gg','2014-04-23 05:37:46'),(55,9,'linuxUsername','cvan071','2014-05-22 01:35:03'),(56,9,'linuxUID','5272','2014-05-22 01:35:03'),(57,9,'linuxGID','5000','2014-05-22 01:35:03'),(58,9,'linuxShell','/bin/bash','2014-05-22 01:35:03'),(59,9,'linuxHome','/home/cvan071','2014-05-22 01:35:03'),(60,9,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Chris Van Houtte ooRfGPsHn6ObPBtvq7cdmH5Q7Gg','2014-05-22 01:35:03'),(61,9,'eppn','cvan071@auckland.ac.nz','2015-06-24 23:04:56'),(62,10,'eppn','tlar007@auckland.ac.nz','2015-06-24 22:37:21'),(63,11,'tuakiriSharedToken','1BRdEiDOVWqpWigi87KlkEybdsE','2014-04-23 05:47:47'),(64,11,'linuxUsername','jrug001','2014-05-22 01:35:02'),(65,11,'linuxUID','5118','2014-05-22 01:35:02'),(66,11,'linuxGID','5000','2014-05-22 01:35:02'),(67,11,'linuxShell','/bin/bash','2014-05-22 01:35:02'),(68,11,'linuxHome','/home/jrug001','2014-05-22 01:35:02'),(69,11,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=John Walter Rugis 1BRdEiDOVWqpWigi87KlkEybdsE','2014-05-22 01:35:02'),(70,12,'eppn','ahin017@auckland.ac.nz','2015-06-24 22:37:21'),(71,13,'tuakiriSharedToken','jbTht0p_GMnFhxEDgNKZLRCSmlw','2015-05-27 20:58:35'),(72,13,'DN','/DC=nz/DC=org/DC=nesi/DC=myproxyplus/O=University of Auckland/CN=Paul Murrell jbTht0p_GMnFhxEDgNKZLRCSmlw','2015-05-27 20:58:35'),(73,13,'linuxUsername','pmur002','2015-05-27 21:15:13'),(74,13,'linuxUID','5867','2015-05-27 21:15:13'),(75,13,'linuxGID','5000','2015-05-27 21:15:13'),(76,13,'linuxShell','/bin/bash','2015-05-27 21:15:13'),(77,13,'linuxHome','/home/pmur002','2015-05-27 21:15:13'),(78,13,'eppn','pmur002@auckland.ac.nz','2015-06-24 23:04:56'),(79,14,'tuakiriSharedToken','heFoyC7yr-AzWI_BFXZvoegRPU8','2014-04-23 05:47:47'),(80,14,'linuxUsername','smas036','2014-05-22 01:35:01'),(81,14,'linuxUID','5010','2014-05-22 01:35:01'),(82,14,'linuxGID','5000','2014-05-22 01:35:01'),(83,14,'linuxShell','/bin/bash','2014-05-22 01:35:01'),(84,14,'linuxHome','/home/smas036','2014-05-22 01:35:01'),(85,14,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Sina Masoud-Ansari heFoyC7yr-AzWI_BFXZvoegRPU8','2014-05-22 01:35:01'),(86,14,'linuxUsername','sina.masoud-ansari','2014-05-22 01:35:05'),(87,14,'linuxUID','5423','2014-05-22 01:35:05'),(88,14,'linuxGID','5000','2014-05-22 01:35:05'),(89,14,'linuxShell','/bin/bash','2014-05-22 01:35:05'),(90,14,'linuxHome','/home/sina.masoud-ansari','2014-05-22 01:35:05'),(91,14,'DN','/C=TW/O=AP/OU=GRID/CN=Sina Masoud-Ansari 310272','2014-05-22 01:35:05'),(92,14,'linuxUsername','rjmtest','2015-05-12 05:05:10'),(93,14,'linuxUID','5856','2015-05-12 05:05:10'),(94,14,'linuxHome','/home/rjmtest','2015-05-12 05:05:10'),(95,14,'eppn','smas036@auckland.ac.nz','2015-12-14 02:35:31'),(96,15,'eppn','mgah001@auckland.ac.nz','2015-06-24 22:37:21'),(97,16,'tuakiriSharedToken','6QVgTniq62B8XgkxZym53NcIs1w','2015-03-02 05:32:53'),(98,16,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Benjamin Adams 6QVgTniq62B8XgkxZym53NcIs1w','2015-03-03 03:57:19'),(99,16,'linuxUsername','bada041','2015-03-02 05:36:42'),(100,16,'linuxUID','5813','2015-03-02 05:36:42'),(101,16,'linuxGID','5000','2015-03-02 05:36:42'),(102,16,'linuxShell','/bin/bash','2015-03-02 05:36:42'),(103,16,'linuxHome','/home/bada041','2015-03-02 05:36:42'),(104,16,'eppn','bada041@auckland.ac.nz','2015-06-24 23:04:56'),(105,17,'linuxUsername','rhos012','2014-09-07 21:45:09'),(106,17,'linuxUID','5723','2014-09-07 21:45:09'),(107,17,'linuxGID','5000','2014-09-07 21:45:09'),(108,17,'linuxShell','/bin/bash','2014-09-07 21:45:09'),(109,17,'linuxHome','/home/rhos012','2014-09-07 21:45:09'),(110,17,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Richard Hosking rgkEhlRdAommwY311bAyAJcaOEQ','2014-09-07 21:45:09'),(111,17,'tuakiriSharedToken','rgkEhlRdAommwY311bAyAJcaOEQ','2014-11-25 00:18:10'),(112,17,'eppn','rhos012@auckland.ac.nz','2015-06-24 23:04:56'),(113,18,'eppn','shol058@auckland.ac.nz','2015-06-24 22:37:21'),(114,19,'eppn','jemm008@auckland.ac.nz','2015-06-24 22:37:21'),(115,20,'tuakiriSharedToken','dmAvw6yLgc8nVAbm-cApKRz5X9M','2014-11-26 21:32:45'),(116,20,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Mark Battley dmAvw6yLgc8nVAbm-cApKRz5X9M','2015-02-19 03:35:27'),(117,20,'linuxUsername','mbat009','2014-11-26 21:35:25'),(118,20,'linuxUID','5765','2014-11-26 21:35:25'),(119,20,'linuxGID','5000','2014-11-26 21:35:25'),(120,20,'linuxShell','/bin/bash','2014-11-26 21:35:25'),(121,20,'linuxHome','/home/mbat009','2014-11-26 21:35:25'),(122,20,'eppn','mbat009@auckland.ac.nz','2015-06-24 23:04:56'),(123,21,'linuxUsername','hlin082','2014-06-10 01:35:09'),(124,21,'linuxUID','5686','2014-06-10 01:35:09'),(125,21,'linuxGID','5000','2014-06-10 01:35:09'),(126,21,'linuxShell','/bin/bash','2014-06-10 01:35:09'),(127,21,'linuxHome','/home/hlin082','2014-06-10 01:35:09'),(128,21,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Henry Ling 0U5qDgRuYGUKaAVzVqMcw_kOFps','2014-06-10 01:35:09'),(129,21,'tuakiriSharedToken','0U5qDgRuYGUKaAVzVqMcw_kOFps','2014-06-25 03:57:22'),(130,21,'eppn','hlin082@auckland.ac.nz','2015-06-24 23:04:56'),(131,22,'eppn','zche092@auckland.ac.nz','2015-06-24 22:37:21'),(132,23,'eppn','ksto036@auckland.ac.nz','2015-06-24 22:37:21'),(133,24,'eppn','bmac039@auckland.ac.nz','2015-06-24 22:37:21'),(134,25,'eppn','dadd001@auckland.ac.nz','2015-06-24 22:37:21'),(135,26,'tuakiriSharedToken','4q8ahxFWg_fxjwnCZPyEwVrhuuo','2014-09-19 02:10:52'),(136,26,'linuxUsername','gbad001','2014-09-19 02:25:13'),(137,26,'linuxUID','5735','2014-09-19 02:25:13'),(138,26,'linuxGID','5000','2014-09-19 02:25:13'),(139,26,'linuxShell','/bin/bash','2014-09-19 02:25:13'),(140,26,'linuxHome','/home/gbad001','2014-09-19 02:25:13'),(141,26,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Gjurgjica Badzakova Trajkov 4q8ahxFWg_fxjwnCZPyEwVrhuuo','2014-09-19 02:25:13'),(142,26,'eppn','gbad001@auckland.ac.nz','2015-06-24 23:04:56'),(143,27,'eppn','adev038@auckland.ac.nz','2015-06-24 22:37:21'),(144,28,'eppn','kwie508@auckland.ac.nz','2015-06-24 22:37:21'),(145,29,'eppn','lewe004@auckland.ac.nz','2015-06-24 22:37:21'),(146,29,'linuxUsername','lewe004','2015-10-20 23:55:10'),(147,29,'linuxUID','5956','2015-10-20 23:55:10'),(148,29,'linuxGID','5000','2015-10-20 23:55:10'),(149,29,'linuxShell','/bin/bash','2015-10-20 23:55:10'),(150,29,'linuxHome','/home/lewe004','2015-10-20 23:55:10'),(151,30,'eppn','ltip001@auckland.ac.nz','2015-06-24 22:37:21'),(152,31,'eppn','rrob042@auckland.ac.nz','2015-06-24 22:37:21'),(153,32,'eppn','shac005@auckland.ac.nz','2015-06-24 22:37:21'),(154,33,'eppn','emol572@auckland.ac.nz','2015-06-24 22:37:21'),(155,34,'eppn','syoo066@auckland.ac.nz','2015-06-24 22:37:21'),(156,35,'eppn','istr005@auckland.ac.nz','2015-06-24 22:37:21'),(157,36,'eppn','dpen466@auckland.ac.nz','2015-06-24 22:37:21'),(158,37,'eppn','rsum009@auckland.ac.nz','2015-06-24 22:37:21'),(159,38,'eppn','jtho004@auckland.ac.nz','2015-06-24 22:37:21'),(160,39,'tuakiriSharedToken','AuRes2VA6kfAfcj7LngWF7Hn3Jo','2014-04-23 05:47:47'),(161,39,'tuakiriSharedToken','_bK32o4Lh58A3vo9kKBcoKrJ7ZY','2014-04-23 06:34:18'),(162,39,'linuxUsername','mbin029','2014-05-22 01:35:01'),(163,39,'linuxUID','5005','2014-05-22 01:35:01'),(164,39,'linuxGID','5000','2014-05-22 01:35:01'),(165,39,'linuxShell','/bin/bash','2014-05-22 01:35:01'),(166,39,'linuxHome','/home/mbin029','2014-05-22 01:35:01'),(167,39,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Markus Binsteiner _bK32o4Lh58A3vo9kKBcoKrJ7ZY','2014-05-22 01:35:01'),(168,39,'linuxUsername','test1.ceres.auckland.ac.nz','2014-05-22 01:35:04'),(169,39,'linuxUID','5300','2014-05-22 01:35:04'),(170,39,'linuxGID','5000','2014-05-22 01:35:04'),(171,39,'linuxShell','/bin/bash','2014-05-22 01:35:04'),(172,39,'linuxHome','/home/test1.ceres.auckland.ac.nz','2014-05-22 01:35:04'),(173,39,'DN','/C=TW/O=AP/OU=GRID/CN=test1.ceres.auckland.ac.nz','2014-05-22 01:35:04'),(174,39,'linuxUsername','markus.binsteiner.5418','2014-05-22 01:35:05'),(175,39,'linuxUID','5418','2014-05-22 01:35:05'),(176,39,'linuxGID','5000','2014-05-22 01:35:05'),(177,39,'linuxShell','/bin/bash','2014-05-22 01:35:05'),(178,39,'linuxHome','/home/markus.binsteiner.5418','2014-05-22 01:35:05'),(179,39,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=University of Canterbury/CN=Markus Binsteiner AuRes2VA6kfAfcj7LngWF7Hn3Jo','2014-05-22 01:35:05'),(180,39,'linuxUsername','markus.binsteiner','2014-05-22 01:35:05'),(181,39,'linuxUID','5429','2014-05-22 01:35:05'),(182,39,'linuxGID','5000','2014-05-22 01:35:05'),(183,39,'linuxShell','/bin/bash','2014-05-22 01:35:05'),(184,39,'linuxHome','/home/markus.binsteiner','2014-05-22 01:35:05'),(185,39,'DN','/C=TW/O=AP/OU=GRID/CN=Markus Binsteiner 125883','2014-05-22 01:35:05'),(186,39,'eppn','mbin029@auckland.ac.nz','2015-12-14 02:35:17'),(187,40,'tuakiriSharedToken','wWY9MA2JXcU14ppo0UlWHUOfmnE','2014-04-23 05:47:47'),(188,40,'linuxUsername','rbur004','2014-05-22 01:35:02'),(189,40,'linuxUID','5163','2014-05-22 01:35:02'),(190,40,'linuxGID','5000','2014-05-22 01:35:02'),(191,40,'linuxShell','/bin/bash','2014-05-22 01:35:02'),(192,40,'linuxHome','/home/rbur004','2014-05-22 01:35:02'),(193,40,'DN','/DC=nz/DC=org/DC=bestgrid/DC=slcs/O=The University of Auckland/CN=Robert Burrowes wWY9MA2JXcU14ppo0UlWHUOfmnE','2014-05-22 01:35:02'),(194,40,'eppn','rbur004@auckland.ac.nz','2015-12-14 02:35:48'),(195,41,'tuakiriSharedToken','bD6uRKsfrxX2_Pc3Slon3XBEK2A','2015-03-11 03:09:58'),(196,41,'DN','/DC=nz/DC=org/DC=nesi/DC=myproxyplus/O=University of Auckland/CN=Alireza Nasiri Minab bD6uRKsfrxX2_Pc3Slon3XBEK2A','2015-03-11 03:09:58'),(197,41,'linuxUsername','anas033','2015-03-11 03:15:11'),(198,41,'linuxUID','5819','2015-03-11 03:15:11'),(199,41,'linuxGID','5000','2015-03-11 03:15:11'),(200,41,'linuxShell','/bin/bash','2015-03-11 03:15:11'),(201,41,'linuxHome','/home/anas033','2015-03-11 03:15:11'),(202,41,'eppn','anas033@auckland.ac.nz','2015-06-24 23:04:56'),(203,42,'eppn','sngu008@auckland.ac.nz','2015-07-20 01:07:56'),(204,43,'eppn','mbig003@auckland.ac.nz','2015-07-19 22:25:35'),(205,44,'eppn','csut017@auckland.ac.nz','2015-12-14 02:31:31'),(206,45,'eppn','bpli001@auckland.ac.nz','2015-12-14 02:32:42'),(207,50,'eppn','skla006@auckland.ac.nz','2015-06-24 22:37:21');
/*!40000 ALTER TABLE `person_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personrole`
--

DROP TABLE IF EXISTS `personrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='Roles a person can have on a project';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personrole`
--

LOCK TABLES `personrole` WRITE;
/*!40000 ALTER TABLE `personrole` DISABLE KEYS */;
INSERT INTO `personrole` VALUES (8,'CeR Contact'),(4,'Contact Person'),(5,'Pending'),(6,'Primary Adviser'),(9,'Primary Reviewer'),(1,'Project Owner'),(3,'Project Team Member'),(10,'Reviewer'),(2,'Supervisor'),(7,'Support');
/*!40000 ALTER TABLE `personrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personstatus`
--

DROP TABLE IF EXISTS `personstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personstatus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COMMENT='List of states a person can be in';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personstatus`
--

LOCK TABLES `personstatus` WRITE;
/*!40000 ALTER TABLE `personstatus` DISABLE KEYS */;
INSERT INTO `personstatus` VALUES (1,'Active'),(2,'Closed'),(6,'Incomplete'),(9,'Marked for closure'),(5,'Marked for deletion'),(4,'Pending'),(3,'Suspended');
/*!40000 ALTER TABLE `personstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL DEFAULT '',
  `typeId` int(11) NOT NULL DEFAULT '0',
  `title` varchar(200) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `startDate` date NOT NULL,
  `nextReviewDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `requirements` text,
  `notes` text,
  `todo` text,
  `statusId` int(11) NOT NULL DEFAULT '0',
  `lastModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'A unix timestamp indicating the last time the project was edited',
  `creationDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='Basic information about a project';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'rvmf00001',1,'Hybrid J2954  Wireless Charging Task Force','In this project we simulate magnetic models which is important for research of magnetic pad sizings to be used in inductive power transfer.','2014-03-19','2016-09-25','2014-11-21','Virtual machine cerjmagprd01.uoa.auckland.ac.nz','','',4,'2015-09-25 00:47:35','2014-03-18 11:00:00'),(2,'rvmf00002',1,'Modelling dual reflux pressure swing adsorption (DR-PSA) units in natural gas processing','My PhD project is to model dual reflux pressure swing adsorption (DR-PSA) units in natural gas processing. Pressure swing adsorption is an alternative gas separation method using adsorption to split components in a gas mixture. DR-PSA is an advanced configuration of pressure swing adsorption with two product refluxes and an internal loop and can in theory achieve perfect separation. As dynamic modelling of adsorption units is already hard: material balance, energy balance, heat transfer, mass transfer has to be considered, rigorous models are often built using a dedicated simulator: Aspen Adsorption. Aspen Adsorption is a module in Aspen Suite and has built-in partial differential equations for mass balance, energy balance as well as adsorption isotherms. It also guarantees rigorous pressure-flow relationships. \r\nConventional PSA usually contain two columns working in parallel - one in adsorption mode and the other in regeneration mode. To simplify the model in Aspen Adsorption, it introduced an \"interaction unit\" to decrease computational intensity. However DR-PSA has two refluxes and four adsorption parts thus four adsorption columns need to be independently and simultaneously simulated while the pressure-flow relationships of the system has to be guaranteed. Besides, the stiff manner of the system require the integrator step size to be very small (as low as 1E-5 seconds), otherwise important information would be lost or even the integrator would crash. As the dynamic model of DR-PSA has never been built before, a very large number of runs would be required to study the effect of each operation parameter. All these contributed to extra computational resource required. On the current simulation environment (3.3G quad core Intel i5 processor with 8G ram), one simulation scenario would take more than 10 hours to finish and the system only allow 3 scenarios to be run at once (each occupies 100% of core usage) and leave one core idle to handle other software. During last phase, around 70 different scenarios were run and the whole work took more than 4 months to finish. We consider extra computational source would significantly speed up the whole simulation process.','2014-05-01','2015-05-01',NULL,'Windows 7, 50GB OS, 300GB data (T3), 8 CPU\'s and 64GB RAM. VM will run Apsen Suite software.','','',1,'2015-07-31 04:46:21','2014-04-30 12:00:00'),(3,'rvmf00003',1,'Earthquake data analysis','<br>See project uoa00074.<br>','2014-06-05','2015-06-05',NULL,'cervanprd01.cer.auckland.ac.nz<br>','','',1,'2015-08-20 23:13:12','2014-06-04 12:00:00'),(4,'rvmf00004',1,'Facilitating the use of Open Data sets','<br>This research project is aimed at facilitating the use of Open Data sets (e.g., data released by government agencies), particularly by encouraging greater engagement with Open Data by a broader range of end users.<br><br>The current situation is that data sets are being made available in a variety of formats, most of which require technical expertise of some sort to access.&nbsp; Furthermore, domain expertise is required to understand what the data mean and how to effectively make use of the data.&nbsp; Finally, programming skills and possibly design skills are required to create end products and services from the data.<br><br>This combination of knowledge, skills, and experience is unlikely to occur in a single individual, so the challenge becomes how to allow individuals with some subset of skills to usefully contribute a partial solution.&nbsp; A further challenge is how to combine partial solutions together to create end products and services.<br><br>The solution proposed by this research project is to define a system that can be used to create a wrapper for any partial solution that an individual creates (a data set, a data processing script, a web service, etc) - this wrapper, called a \"module\", specifies what inputs the module requires and what outputs the module generates.&nbsp; A separate part of the system is used to describe how a collection of modules are connected - this creates a \"pipeline\".&nbsp; Pipelines can themselves be&nbsp; combined with modules or other pipelines into still larger pipelines.<br><br>The system is designed so that the authors of partial solutions, modules, and pipelines can all be distinct individuals and need not coordinate their actions, nor require permissions to conduct their own activities.&nbsp; Furthermore, data sets, partial solutions, modules, and pipelines may be distributed across file systems, networks, or the<br>internet.<br><br>The project work involves producing a specification for modules and pipelines as well as developing software to support the execution of modules and pipelines to produce useful outputs.&nbsp; Additional layers of software convenience, such as visual programming environments and repositories of modules and pipelines, may also be worked on.<br><br>The request for a VM with many cores and a large RAM is so that we can generate multiple VM instances within that host VM in order to test out various scenarios involving modules and pipelines that are cross-platform and distributed across multiple systems.<br><br><br>','2014-06-19','2015-06-19',NULL,'cermurprd01.cer.auckland.ac.nz','','',1,'2015-12-01 20:36:14','2014-06-18 12:00:00'),(5,'rvmf00005',1,'Map-based exploration of knowledge','Extremely rich and diverse knowledge about places across the world is available online in a variety of forms, including structured data, image, and natural language description.<br>Map-based exploration of this knowledge has the potential to aid a number of applications from scientific research to education to marketing. In light of this, in this research we are developing and evaluating a system to map geographic regions associated with arbitrary keywords, phrases, and texts by computing surface distributions over the Earth\'s surface for topics derived from unstructured natural language text.<br>Our methodology combines natural language processing and geostatistics and uses freely available open-source tools. We train our system on Wikipedia and Travel blog entries and we demonstrate its application in a general-purpose geographic knowledge exploration tool.<br>','2014-07-03','2015-07-03',NULL,'Machine: ceradaprd01.cer.auckland.ac.nz<br><br>Frankenplace Dev:&nbsp;<a href=\"http://ceradaprd01.cer.auckland.ac.nz/fpdev/\" target=\"_blank\" rel=\"\" title=\"Link: http://ceradaprd01.cer.auckland.ac.nz/fpdev/\">http://ceradaprd01.cer.auckland.ac.nz/fpdev/</a><br>','','',1,'2016-06-08 23:03:53','2014-07-02 12:00:00'),(6,'rvmf00006',1,'Hosking virtual machine','The nature of the research does not fit into the batch model of scientific computing, therefore is unsuitable for Pan. <br>The research, in part relies on interactivity through a web interface. It will be long running, bursty, and at times requires large amounts of memory (~100gb) making it best suited for a CeR VM. <br>The software uses http and https, so requires port 80 and 443 to be opened for both incoming and outgoing traffic.<br>I also require ssh to be opened for outgoing traffic','2014-09-24','2015-09-24',NULL,'','','',1,'2015-07-20 22:11:00','2014-09-23 12:00:00'),(7,'rvmf00007',1,'ArcGIS','ArcGIS is Windows only software that is required for point cloud data processing. A large amount of memory (estimated as 64gb initially) is required to process the point cloud data into tin and raster formats. The cluster is Linux and therefore doesn’t support ArcGIS and standard ITS VMs do not provide this amount of memory. In addition the point cloud which was created for this purpose is too large to decompress from its current state with standard Arts faculty computers (with 16GB ram) and will require to be built again from a DSM raster.','2014-09-30','2015-09-30',NULL,'','','',1,'2015-08-20 01:55:20','2014-09-29 11:00:00'),(8,'rvmf00008',1,'Fibre reinforced composites','Fibre reinforced composites are known to be sensitive to impact damage. Out of plane impacts, even at low energies, can significantly decrease the mechanical properties of the material and cause unexpected failure. This has limited their use in primary load-bearing applications. Characterisation of these materials, understanding and improving their strength after impact damage is therefore of high importance. However, impact testing of composite materials is complex as results are very dependent on the experimental set up. There are a number of test variables including impact energy, impact velocity, specimen size, specimen thickness, and impactor geometry. Numerical simulations of impact testing can be used to investigate how experimental set up influences results, and help gain a deeper understanding of the complex material response. Current modelling has progressed to the simulation of individual ply damage using multi constituent theory, and delamination modelled using cohesive layers. This is simulated using the Abaqus dynamic implicit solver and the Autodesk composites analysis add-on. The current simulation has a high number of plies, each with a simulated cohesive layer between. This results in a relatively large number of elements through the thickness of the part and hence high number of elements. Current run times are unrealistic to be solved on my computer at The University of Auckland, especially as temporary files created are too large for the hard drive (HD) and the model has to be solved on an external HD.','2014-10-24','2015-10-24',NULL,'','','',1,'2015-08-20 02:16:45','2014-10-23 11:00:00'),(9,'rvmf00009',1,'Wind turbine design','The most visible trend in wind turbine design over the past few years has been the increasing size of the turbines themselves. Larger turbines offer several advantages, such as increased generation capacity, the ability to maximise land usage (e.g. for turbines placed on ridgelines) and the ability to take advantage of increased wind speeds at higher altitudes due to wind shear.<br><br>One of the challenges facing designers of large turbines is the increasing mass of turbine components with scale. This ‘square-cube’ law places an upper limit on turbine size, meaning that innovative methods for reducing turbine component mass are important for the future development of larger turbines. <br><br>Of particular interest in this research are the blades of the turbines. Advanced load reduction methods, such as the use of individual blade pitching (IPC), whereby the blades of the turbines are pitched independently of one another, have been shown to be capable of reducing fatigue loads in the blade root which originate from low frequency disturbances, such as that from wind shear. Newer concepts such as trailing edge flaps (TEF), collectively termed ‘Advanced Aerodynamic Load Control’, have been shown to be capable of reducing loads stemming from higher frequency disturbances due to factors such as turbulence. <br><br>These methods of load control have been investigated on turbines with ratings up to 5MW, though future turbines are expected to be significantly larger. With increasing scale, the performance of current load reduction methods may be affected by factors such as lower natural frequencies, or by the increased variation in wind speed throughout the swept area of the rotor. As such, the focus of this research currently is to assess the performance of load control using individual pitch and trailing edge flaps on upscaled wind turbines.<br><br>10-20MW wind turbine models are generated from a baseline 5MW wind turbine design using geometric self-similarity (known as classical scaling), whereby all dimensions of the turbine are scaled up by a constant factor. Scale invariant IPC and TEF controllers, used to ensure ‘fair’ control effort regardless of scale, are designed for turbines using linear models generated via system identification. The performance of the load control methods are assessed based on fatigue loads and actuator usage using simulations with the HAWC2 aeroservoelastic code. <br><br>Another objective of this research will involve developing lighter blade models for turbines which integrate known load reductions from classically scaled turbines, to see if the additional flexibility expected from lighter blades has an impact on the performance of IPC and TEF. The overall goal of this research is to attempt to quantify the benefits of IPC and TEF (realised through development of lighter blades and the potential for improved turbine performance due to reduced rotor inertia) to see if these load control methods (in particular, the use of TEF) are viable on current and larger turbines.','2014-11-24','2016-12-15',NULL,'','','',1,'2015-12-14 20:58:52','2014-11-23 11:00:00'),(10,'rvmf00010',1,'Brain research programme','This VM underpins a Brain Research programme in the Dept of Pyschology, Faculty of Science. Large amounts of data are captured, analysed and processed using a suite of specialized software for analyzing and manipulating data. These servers store some data, allow for print outputs, authenticate users and acts as software licence server.','2014-11-24','2015-11-24',NULL,'','This VM also has a TIER 3 disk space of 4TB, which can be enlarged to 20TB. If I\'m correct the department (faculty?) will be charged by ITS for that disk space.','',1,'2015-12-03 20:54:54','2014-11-23 11:00:00'),(11,'rvmf00011',1,'Cleft lip and palate','This study has two main objectives in relation to cleft lip and palate (CLP). Firstly it will investigate the health care delivery pathways from the time of diagnosis to the primary surgery for children with a cleft lip and/or palate and their families. This will ensure that CLP patients are receiving health delivery to the standard expected and required. Secondly it will provide the first consistently collected outcome data in relation to cleft lip and palate in New Zealand, including surgical outcomes, speech, dental care and importantly quality of life. This data will be compared to a parallel study in Australia, as well as to data from the UK to determine how cleft lip and palate patients in New Zealand fare compared to their contemporaries worldwide. This will allow any deficiencies to be identified and processes put in place to ensure improved outcomes in the future.<br><br>','2015-03-23','2016-03-23',NULL,'','','',1,'2015-07-20 05:40:29','2015-04-15 23:14:15'),(12,'rvmf00012',1,'Research Data Management Services - CKAN','We are working on a pilot project to trial a research data management service called ‘figshare’ amongst social science researchers. As part of this project we want to setup a competing product, ‘CKAN’, on our own infrastructure in order to compare features of both, and give our test subjects the change to try out different UIs.<br>','2015-03-17','2016-03-17',NULL,'','','',1,'2015-04-15 23:39:06','2015-04-15 23:29:23'),(13,'rvmf00013',1,'Research Data Management Services - Dataverse','We are working on a pilot project to trial a research data management <br>service called ‘figshare’ amongst social science researchers. As part of<br> this project we want to setup a competing product, ‘Dataverse’, on our own <br>infrastructure in order to compare features of both, and give our test <br>subjects the change to try out different UIs.<br>','2015-03-17','2016-03-17',NULL,'','','',1,'2015-04-15 23:44:21','2015-04-15 23:40:43'),(14,'rvmf00014',1,'University course: Data Science Practice','<div>In order to run the course described below in semester 2 2015, 3 large VM are required.<br>Semester 2 starts on Monday 20 July 2015, and ends on Monday 16 November 2015.<br><br>Course description:<br><br>STATS 769 Data Science Practice (15 points)</div><div>Taught: Semester Two, City Campus</div><div>For advice: Paul Murrell (x85392)  Alan Lee (x88749)</div><div><br>STATS 769 is intended to provide students with computing skills involved in the acquisition and manipulation of large and/or complex data sets. <br>A secondary aim is to give students practice in applying modern data mining techniques; there will be an emphasis on practice rather than theory.</div><div><br>Possible topics include: data mining tools, working with database APIs and web APIs, parallel computing, and large memory computing.</div><div><br>The course will assume a certain amount of basic computing knowledge, such as might be obtained by completion of STATS 220, STATS 380 or STATS 779.  Some familiarity with data mining tools will also be assumed.  The course will be largely based on R.</div><div><br>The course will be assessed on assignment work and a written test. There will be no final exam.</div><br><br>','2015-05-28','2016-12-09','2015-11-20','','','',4,'2015-12-08 19:52:54','2015-05-27 22:37:15'),(15,'rvmf00015',1,'Owncloud trial','As part of our (CeR) investigating the research data management space, we are setting up two file sharing services on a trial basis. This here is for owncloud (http://owncloud.org)<br>','2015-06-05','2016-06-05',NULL,'','','',1,'2015-06-07 22:20:06','2015-06-04 22:46:08'),(16,'rvmf00016',1,'Seafile trial','As part of our (CeR) investigating the research data management space, we <br><div>are setting up two file sharing services on a trial basis. This here is <br>for seafile (<a rel=\"\" target=\"\" href=\"http://seafile.com\">http://seafile.com</a>)</div><br>','2015-06-05','2016-06-05',NULL,'','','',1,'2015-06-07 22:20:31','2015-06-04 22:55:17'),(17,'rvmf00017',1,'Fault tolerant control of nonlinear systems','Due to the complexity of modern engineering systems, it is increasingly important to ensure their reliability. This has motivated researchers to concentrate on Fault tolerant control (FTC), which is primarily meant to ensure safety, that is, the stability of a system after the occurrence of a fault in the system.<br>There are two approaches to synthesize controllers that are tolerant to system faults. One approach, known as passive FTC, aims at designing a controller which is a priori robust to some given expected faults. Another approach, known as active FTC, relies on the availability of a fault detection and diagnosis (FDD) block that gives, in real-time, information about the nature and the intensity of the fault. This information is then used by a control reconfiguration block to adjust online the control effort in such a way to maintain stability and to optimize the performance of the faulty system.<br>In the last decades there have been a myriad of results on FTC. Many of those work concentrated initially on linear FTC, and more and more researches started focusing on the nonlinear FTC problems, the later being more challenging than the linear FTC because of the difficulties intrinsic to nonlinear systems. However, many encouraging results have been obtained. This study deals with robust FTC for some class of nonlinear systems represented by Takagi-Sugeno (T-S) fuzzy models and polynomial fuzzy models. A motivation of this work is to provide an entry point to the rapidly expanding field of FTC in nonlinear process control. In short order, the research objectives can be described as:<br><ul><li>To develop a passive FTC for handling actuator faults for nonlinear systems using Sliding Mode approach.</li><li>To develop a Fault Detection and Diagnosis algorithm for T-S fuzzy models using non-monotonic Lyapunov functions.</li><li>To develop integrated fault-detection and isolation and fault-tolerant control methods for T-S fuzzy models using non-monotonic Lyapunov functions.</li><li>To develop a Fault Detection and Diagnosis algorithm for for polynomial fuzzy models.</li><li>To develop integrated fault-detection and isolation and fault-tolerant control methods for polynomial fuzzy models<br></li></ul>','2015-07-16','2016-11-13','2015-11-13','','MF: Called Alireza - project can be closed.<br>','',4,'2015-11-13 03:03:05','2015-07-16 01:19:21'),(18,'rvmf00018',1,'Virtual Platform Development for Rapid Architectural Exploration of Safety Critical Embedded Systems','<span>Multi-processor systems on chip (MPSoC) are an efficient approach to exploit the available billions of transistors on a single chip for HW/SW co-design of safety critical<br>embedded systems. This also results in optimising energy consumption and achieving higher performance to meet the hard real-time design constraints. Rapid architectural exploration for HW/SW co-design requires a virtual platform, which can model the system at different abstraction levels to trade-off simulation speed with respect of the model accuracy.&nbsp; In addition, virtual platforms are essential for rapid SW development before the final HW implementation is available. <br>The aim of this research is (a) to develop SystemC based Virtual platforms which include the system models at different abstraction levels and (b) to develop proper interfacing to Matlab/Simulink for co-simulation of some safety critical applications.</span><br>','2015-07-20','2016-07-20',NULL,'','','',1,'2015-07-19 22:37:11','2015-07-19 22:24:18'),(19,'rvmf00019',1,'Digital Ink','Digital ink is about capturing freeform, pen-based input using a stylus on an electronic device. One area of investigation is the ability to combine electronic documents with digital ink: freeform annotations. To make this work we need to recognise the type of annotation. Rather than hand-coding the rules for recognising each type we have built a toolkit that generates recognisers based on the characteristics (features) of the ink stroke. This toolkit uses machine learning on a dataset to generate the recogniser. However the current version of the toolkit only uses features based on the ink itself.<br>My work is now extending the toolkit to include additional features about the underlying textual content. These additional features should improve the recognition rate. We have written the code for extracting the new features and collected a dataset to test whether the new features improve accuracy. However we now need to generate the actual recognisers. We will use ten-fold cross validation to estimate the accuracy of the recogniser both with and without the new features. Therefore we need to generate a total of twenty recognisers using machine learning. Unfortunately, even generating a single recogniser is a computationally expensive task. Therefore we are looking at using some resources from the Centre for eResearch to speed up this process.<br>The toolkit (RATA - <a rel=\"\" target=\"\" href=\"https://www.cs.auckland.ac.nz/research/hci/digital_ink/ink_recognition/rata_recognizers.shtml\">https://www.cs.auckland.ac.nz/research/hci/digital_ink/ink_recognition/rata_recognizers.shtml</a>) is written for the .NET 4.5 runtime and uses a Windows environment.<br><br><br>','2015-09-01','2016-06-01',NULL,'','','',1,'2015-09-09 02:44:00','2015-08-31 21:39:48'),(20,'rvmf00020',1,'Serious game exoskeleton interface for wrist strength, stretching and range-of-motion therapy','Objective:<br>To develop a serious game interface for upper limb robotic exoskeletons that can be used for strength, stretching and range-of-motion training, as well as act as a diagnosis tool for children with cerebral palsy. The secondary aim of the interface is to provide entertainment to increase engagement and participation in therapy.<br><br>Design:<br>This was a preliminary study, focused on the design, development and integration of a serious game interface with a novel robotic device to gain insight to the feasibility of the system to be deployed for in-home therapy.<br><br>Method:<br>A gaming interface was developed where the participant’s wrist joint movement, while wearing the exoskeleton, translates to the horizontal (radial/ulnar deviations) and vertical (flexion/extension) movement of a bumblebee character on the screen. The aim of the game is to fly around and collect pollen from flowers that are placed at specific locations on the screen, without being caught by the frog. When the frog catches the bumblebee, the participant has to fight to get away.<br><br>The wrist exoskeleton has been implemented with a control system that is able to apply a desired force in any direction to the participant’s wrist. As such during the game the control mode can be varied to either aid or resist the participant playing the game through the application of ‘force-fields’. Other game parameters can be updated dynamically, such as size and location of the flowers. This is used to set the difficultly of the game and target wrist movements towards the specific locations in the participants range-of-motion that are most in need of therapy. <br><br>The results are then destined to be forwarded to a database to be interpreted by a web interface.<br><br>Results:<br>The bumblebee game was successfully interfaced with the exoskeleton and was tested on healthy people, which has validated the performance. The system can collect clinical data such strength, stiffness and range-of-motion of the joint. It also has the ability to target therapy to the desired locations as set by a clinician.<br><br>The full implementation of the database is outstanding with a hope for full integration in the near future.<br><br>Conclusion: <br>A serious game was developed and tested with a wrist exoskeleton. The system has proved to have the required performance and the game clearly demonstrates an increased engagement in repetitive therapy tasks. Future work is to deploy the system in a clinical trial to determine it efficacy in a clinical population.<br>','2015-09-25','2016-09-25',NULL,'','<span>This is a Part IV project which will continue towards a summer project and towards a clinical trial of the unit which the students/researcher is developing.<br><br>The VM will run a WAMP server (Apache/MySQL/PHP) instance that will collect and display data from the Robotic Unit within the university(for a start ) and later moving on to clinical trials outside the university.</span><br>','',1,'2015-09-25 04:27:25','2015-09-25 04:18:05'),(21,'rvmf00021',1,'Social Media impact on stock market liquidy','Recent studies have investigated the effect of investors’ opinions, or peer-based advice, available on social media platforms or stock discussion forums on the financial market. The researchers have been focusing on stock earnings surprises, abnormal returns and trading volume. But the jury is still out on when and how the discussion on social media impacts financial markets, especially market liquidity. For example, are social media platforms heterogeneous in the value they offer to investors? Further, we do not know if peer-based advice contains information that will be reflected by the market liquidity. We aim to shed light on these concerns by evaluating whether sentiments from messages on online discussion forums could influence the short sales activities in the Australian market. We employ textual analysis techniques to extract sentiments from these platforms. Practical implications for the markets have been discussed.<br>The rapid development of textual analysis techniques has paved the way for automatic sentiment analysis. Many studies have been focusing on using sentiments disclosed from social media to predict the financial market performance. However, the underlying value of the messages collected from social media is still not fully understood due to lack of comprehensive apprehending of the performance for different textual analysis methods. In this research, we shed light on this problem by comparing the different classification accuracy of dictionaries and machine-learning techniques in the financial context.<br>Textual analysis, especially sentiment analysis, is a domain dependent problem. An expression that has a clear sentiment in one domain may be ambiguous in other domains. This issue is particularly strong in the financial context analysis, as there are specialized concepts and limited use of effective words. It has been shown that dictionaries developed for other disciplines misclassify common words in the financial context (Loughran and Mcdonald, 2011). For instance, liability is a neutral word in financial context. The L&amp;M dictionary developed by Loughran and Mcdonald (2011) has been widely used (Chen et al., 2014) in financial context analysis since its first appearance in academia. <br>Besides the L&amp;M dictionary, researchers have used other machine learning algorithms for classification. For example, Leung and Ton (2015) use Naïve Bayes (NB) algorithm to classify message sentiments and find that sentiments positively relate to the returns of small-cap stocks. Malo et al. (2013) compare the performance of the support vector machine (SVM) with varied underlying pattern analysis algorithms and find that performance is significantly improved when different algorithms are combined. Tirunillai and Tellis (2012) implement both NB and SVM to show that SVM outperform NB in five markets and underperform in one market.<br><br>','2015-11-11','2016-11-11','2018-12-31','','','',1,'2015-11-12 02:28:54','2015-11-11 03:45:16'),(22,'rvmf00022',1,'Vineyard Ecosystems programme','The Vineyard Ecosystems programme is co-funded by the New Zealand wine industry via NZ Winegrowers and the Ministry of Business, Innovation &amp; Employment (MBIE) through its Science and Innovation Partnership programme. Funding partnerships are for long-term research programmes and are aimed at increasing the competitiveness of New Zealand industries.<br><br>The programme represents a shift in the way we look at vineyards, moving toward an integrated understanding of the ecology of the vineyard as a whole.<br><br>Some of the main outcomes we are seeking involve: improved vineyard longevity; better management of pests, including grass grub beetles and mealybugs; better management of diseases; reduced reliance on chemical interventions; and, a strong science foundation for our sustainability credentials.<br><br>In other words, we want to create a new knowledge network that illustrates the inter-relatedness of vineyard practices on multiple diseases, the ecosystem, invertebrate pests (vectors) and vine health over time.<br><br>Plant &amp; Food Research and the University of Auckland are the main research providers, but the programme will also involve international collaboration. The lead scientist is Dr Mat Goddard, University of Auckland, who pioneered the study of variance in vineyard and wine microbes. Mat led the way in reporting on next-generation DNA sequencing of vineyard samples - a technology that will also be employed in the current research.<br><br>The programme will also develop and/or maintain active international associations with leading scientists conducting research in related fields, including: virologist Professor Gerhard Pietersen (University of Pretoria, SA) - at the forefront of international epidemiological research into Grapevine leafroll-associated virus 3; Dr Kent Daane and Associate Professor Rodrigo Almeida (UC Berkeley, USA) - researchers focusing on mealybug (vector) and/or virus, biology, ecology and control; and Dr Mark Sosnowski (SARDI, Australia) - leading research to optimise eradication strategies for eutypa dieback of grapevines.<br>','2015-11-12','2016-11-12',NULL,'','','',1,'2015-12-14 00:44:11','2015-11-12 03:24:28'),(23,'rvmf00042',1,'some title','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaadsfadsfads sdaf asdf asdf asdf adf asd fasd fa sdf asdf asdf asdf asdf adsfasdfasdfasd fasf asdfasd fasdf as fasfa sdf asdfasdfasdfadf','2015-11-12','2016-11-12',NULL,NULL,NULL,NULL,1,'2016-03-20 22:57:36','0000-00-00 00:00:00'),(24,'rvmf00050',1,'some title','bbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb  bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbsdaf asdf asdf asdf adf asd fasd fa sdf asdf asdf asdf asdf adsfasdfasdfasd fasf asdfasd fasdf as fasfa sdf asdfasdfasdfadf','2016-12-04','2016-11-12',NULL,NULL,'bla',NULL,1,'2016-06-14 23:48:04','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_action`
--

DROP TABLE IF EXISTS `project_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectId` int(11) NOT NULL,
  `personId` int(11) NOT NULL,
  `actionTypeId` int(11) NOT NULL,
  `date` date NOT NULL,
  `notes` text,
  PRIMARY KEY (`id`),
  KEY `project_action_ibfk_1` (`projectId`),
  CONSTRAINT `project_action_ibfk_1` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='Actions on projects';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_action`
--

LOCK TABLES `project_action` WRITE;
/*!40000 ALTER TABLE `project_action` DISABLE KEYS */;
INSERT INTO `project_action` VALUES (2,1,1,2,'2015-07-31','adfad adf asd adf'),(4,2,5,2,'2014-11-20','This VM has a show case, and is still heavily used. <br>'),(5,2,6,4,'2015-07-31','Performance Improvements:<br>I can now run up to 8 jobs at the same time, which is 4 times more concurrent jobs than before.<br><br>Views:<br>I feel comfortable recommending these services to colleagues: Strongly agree<br>These services meet my needs: Strongly agree<br>I receive adequate support when using these services: Strongly agree<br><br>Feedback:<br>N/A<br><br>Future Needs:<br>More disk space.<br><br>I would like you to follow up with me: Yes'),(6,3,5,2,'2014-11-20','Asked if the VM is still used. <br>'),(7,3,9,4,'2015-08-21','Performance Improvements:<br>My jobs run 2 times faster than before, thanks to: Distributed memory parallelisation.<br><br>Views:<br>I feel comfortable recommending these services to colleagues: Neutral<br>These services meet my needs: Neutral<br>I receive adequate support when using these services: Strongly agree<br><br>Feedback:<br>I received excellent help from Bart, however, the cluster didn\'t help speed up my work so much. I can\'t remember the reason, but it was something to do with the file system. I had tens of thousands of files and the cluster struggled to handle the i/o.\r\nHowever I must stress that I received excellent help from the technicians.\r\nThanks<br><br>Future Needs:<br>N/A<br><br>I would like you to follow up with me: No'),(8,4,5,2,'2014-11-20','The VM has been working fine, thank you. We have not used the VM very<br>frequently just yet, as we are not yet at the stage of doing very<br>frequent testing. I have found it capable of handling the tests I have<br>done with it so far.<br><br>Paul gave a Stats Department seminar on the openapi Project earlier this<br>year, the resources from which can be found at<br>https://www.stat.auckland.ac.nz/~paul/Talks/OpenAPI2014/. We will be<br>producing a technical report on the project in the near future, which I<br>will make sure I send you a link to.'),(9,4,12,4,'2015-08-25','Performance Improvements:<br>I can run larger jobs now, up to 8 times larger than before, thanks to: More memory available.<br><br>Views:<br>I feel comfortable recommending these services to colleagues: Agree<br>These services meet my needs: Agree<br>I receive adequate support when using these services: Agree<br><br>Feedback:<br>N/A<br><br>Future Needs:<br>N/A<br><br>I would like you to follow up with me: No'),(10,6,17,4,'2015-07-21','Performance Improvements:<br>I can run larger jobs now, up to 4 times larger than before, thanks to: More memory available.<br><br>Views:<br>I feel comfortable recommending these services to colleagues: Neutral<br>These services meet my needs: Agree<br>I receive adequate support when using these services: Agree<br><br>Feedback:<br>Streamline the process for making administrative changes to the VM (opening ports, setting up sudo etc)<br><br>Future Needs:<br>N/A<br><br>I would like you to follow up with me: No'),(11,7,19,4,'2015-07-21','Performance Improvements:<br>I can run larger jobs now, up to 10 times larger than before, thanks to: More memory available. More disk space.<br><br>Views:<br>I feel comfortable recommending these services to colleagues: Strongly agree<br>These services meet my needs: Strongly agree<br>I receive adequate support when using these services: Strongly agree<br><br>Feedback:<br>The support we recieve meets our needs<br><br>Future Needs:<br>More CPU cores per cluster node to run larger multi-threaded jobs. More memory per compute node. More disk space. Advice on how to parallelise/scale/tune my software.<br><br>I would like you to follow up with me: No'),(12,8,21,4,'2015-08-13','Performance Improvements:<br>My jobs run 20 times faster than before, thanks to: Distributed memory parallelisation. Shared memory parallelisation. I can run larger jobs now, up to 100 times larger than before, thanks to: More memory available. More disk space.<br><br>Views:<br>I feel comfortable recommending these services to colleagues: Strongly agree<br>These services meet my needs: Strongly agree<br>I receive adequate support when using these services: Strongly agree<br><br>Feedback:<br>N/A<br><br>Future Needs:<br>N/A<br><br>I would like you to follow up with me: No'),(13,9,4,3,'2015-01-28','Running his software failed, because libraries msvcr100d.dll and msvcp100d.dll were missing.<br><br>Details:<br>msvcr100d.dll and msvcp100d.dll were nowhere to be seen on the system, <br>even after re-installing the Microsoft Visual C++ Redistributable.<br>Only msvcr100.dll and msvcp100.dll were found in the Windows system32 <br>folder.<br><br>(The d at the end seems to stand for \"debug version\")<br><br>Perhaps the developer of hawc2 made an error by making the program <br>require the debug dlls depend on it.<br>If that is correct, it could be that msvcr100d.dll and msvcp100d.dll are <br>only distributed with Visual Studio, and not the runtime libraries.<br><br>I fixed the issue by downloading the 2 missing dlls from a third party <br>dll repository and by putting them into the hawc2 program directories.<br>'),(14,9,4,3,'2015-02-02','The VM will be shutdown (request via Staff Service Centre with a few questions, because Jeremy mentioned he may need the VM again in a few months)<br><br>AskIT ticket id: REQ0597229<br><br>(<br>Q: We would like to keep the VM image though, because the researcher who used it will likely use it again in a few months. Is that possible?<br>A: Yes.<br>Q: How long do you keep VM images? Do you notify the owner of a stored VM image in case you have to delete it (e.g. in case you are running low on disk space and the image has not been used for N months and the owner may have forgotten about it)?<br>A: Each cluster is a different reality, the one where this VM is located dc02ere03_TDC is pretty empty with lots of resources available, it belongs to e-Research only, there is only this VM powered off and I made a note asking not to delete this VM until further notice, so it will be fine.<br><br>Q: If so: How do I reference the image if I request to launch a VM from it in a few months?<br>A: We will keep the same name and mention this request number as a noting in this VM description.<br><br>Q: There\'s a software installed on the VM that is licensed on the MAC address. In case we request to launch a VM from the image later on: Can it be launched with the original MAC address?<br>A: Yes, it can.<br>)<br><br><br>'),(15,9,4,3,'2015-06-12','Jeremy asked for the VM to be re-launched, with following changes:<br>CPUs: increase from 12 to 14<br>RAM: increase from 10 to 16<br>Disk space: increase from 110GB to 150GB<br><br>Barry Fullerton from FoE installed Matlab.<br>'),(16,9,22,4,'2015-07-02','Performance Improvements:<br>I can now run up to 11 jobs at the same time, which is 5 times more concurrent jobs than before.<br><br>Views:<br>I feel comfortable recommending these services to colleagues: Strongly agree<br>These services meet my needs: Strongly agree<br>I receive adequate support when using these services: Strongly agree<br><br>Feedback:<br>N/A<br><br>Future Needs:<br>N/A<br><br>I would like you to follow up with me: No'),(17,10,25,4,'2015-07-24','Performance Improvements:<br>N/A<br><br>Views:<br>I feel comfortable recommending these services to colleagues: Agree<br>These services meet my needs: Disagree<br>I receive adequate support when using these services: Strongly agree<br><br>Feedback:<br>It would be great to have an infrastructure established for neuroimaging research that is tailored to the unique needs of MRI storage and analysis. \r\n\r\nI would like to note we have been working with Martin Feller, who has provided excellent support in setting up our VM and getting it up and running (and helping us to set up tests which will provide more specific information about our computational needs).<br><br>Future Needs:<br>More CPU cores per cluster node to run larger multi-threaded jobs. More memory per compute node. Advice on how to parallelise/scale/tune my software. Other: As per my recent email to Mark Gahegan, we need (for neuroimaging/MRI research) a set-up where we have a cluster that has large storage capacity (due to the nature of our research, we have thousands of files per analysis, and output files are unusable once moved off the cluster; however, the current aspirational VM is too slow). However, there may also be ways to tune our software to make better use of the hardware available.<br><br>I would like you to follow up with me: Yes'),(18,11,38,4,'2015-07-20','Performance Improvements:<br>N/A<br><br>Views:<br>I feel comfortable recommending these services to colleagues: Strongly agree<br>These services meet my needs: Strongly agree<br>I receive adequate support when using these services: Strongly agree<br><br>Feedback:<br>The cloud service we are using through the centre for e-research is proving to be valuable in receiving information from our collaborative units around the country in a timely manner. Issues have been dealt with promptly.<br><br>Future Needs:<br>N/A<br><br>I would like you to follow up with me: No'),(19,17,4,3,'2015-07-20','Note: Alireza uses a VM owned by Henry Ling (rvmf00008). This was the fastest way to get Alireza onto a VM to run memory-intensive Matlab simulations.<br>');
/*!40000 ALTER TABLE `project_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_division`
--

DROP TABLE IF EXISTS `project_division`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_division` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectId` int(11) NOT NULL,
  `divisionId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `project_division_ibfk_1` (`projectId`),
  KEY `project_division_ibfk_2` (`divisionId`),
  CONSTRAINT `project_division_ibfk_1` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `project_division_ibfk_2` FOREIGN KEY (`divisionId`) REFERENCES `division` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='Map divisions to projects';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_division`
--

LOCK TABLES `project_division` WRITE;
/*!40000 ALTER TABLE `project_division` DISABLE KEYS */;
INSERT INTO `project_division` VALUES (1,1,94),(2,2,98),(3,3,93),(4,4,84),(5,5,79),(6,6,78),(7,7,154),(8,8,96),(9,9,96),(10,10,82),(11,11,102),(12,12,78),(13,13,78),(14,14,84),(15,15,78),(16,16,78),(17,17,94),(18,18,94),(19,19,79),(20,20,96),(21,21,160),(22,22,84),(23,23,84),(24,23,160),(25,24,1),(26,24,2),(27,24,3),(28,1,93);
/*!40000 ALTER TABLE `project_division` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_externalreference`
--

DROP TABLE IF EXISTS `project_externalreference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_externalreference` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `projectId` int(11) NOT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `project_external_reference_ibfk_1` (`projectId`),
  CONSTRAINT `project_external_reference_ibfk_1` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='External references of projects';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_externalreference`
--

LOCK TABLES `project_externalreference` WRITE;
/*!40000 ALTER TABLE `project_externalreference` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_externalreference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_facility`
--

DROP TABLE IF EXISTS `project_facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_facility` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectId` int(11) NOT NULL,
  `facilityId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `projectId` (`projectId`),
  CONSTRAINT `project_facility_ibfk_1` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='Facilities used by projects';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_facility`
--

LOCK TABLES `project_facility` WRITE;
/*!40000 ALTER TABLE `project_facility` DISABLE KEYS */;
INSERT INTO `project_facility` VALUES (1,1,10),(2,2,10),(3,3,10),(4,4,10),(5,5,10),(6,6,10),(7,7,10),(8,8,10),(9,9,10),(10,10,10),(11,11,10),(12,12,10),(13,13,10),(14,14,10),(15,15,10),(16,16,10),(17,17,10),(18,18,10),(19,19,10),(20,20,10),(21,21,10);
/*!40000 ALTER TABLE `project_facility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_kpi`
--

DROP TABLE IF EXISTS `project_kpi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_kpi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kpiId` int(11) NOT NULL,
  `projectId` int(11) NOT NULL,
  `date` date NOT NULL,
  `personId` int(11) NOT NULL,
  `value` float DEFAULT NULL,
  `notes` text,
  `kpiCategoryId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_kpi_ibfk_1` (`projectId`),
  CONSTRAINT `project_kpi_ibfk_1` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COMMENT='KPIs linked to projects';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_kpi`
--

LOCK TABLES `project_kpi` WRITE;
/*!40000 ALTER TABLE `project_kpi` DISABLE KEYS */;
INSERT INTO `project_kpi` VALUES (1,3,2,'2015-08-20',4,4,'I can now run up to 8 jobs at the same time, which is 4 times more concurrent jobs than before.',1),(2,3,3,'2015-08-24',4,2,'My jobs run 2 times faster than before, thanks to: Distributed memory parallelisation.',2),(3,3,4,'2015-08-26',4,8,'I can run larger jobs now, up to 8 times larger than before, thanks to: More memory available.<br>',3),(4,3,6,'2015-08-20',4,4,'I can run larger jobs now, up to 4 times larger than before, thanks to: More memory available.',3),(5,3,7,'2015-08-20',4,10,'I can run larger jobs now, up to 10 times larger than before, thanks to: More memory available. More disk space.<br>',3),(6,3,8,'2015-08-20',4,20,'My jobs run 20 times faster than before, thanks to: Distributed memory parallelisation. Shared memory parallelisation.',2),(7,3,8,'2015-08-20',4,100,'I can run larger jobs now, up to 100 times larger than before, thanks to: More memory available. More disk space.<br>',3),(8,3,9,'2015-08-20',4,5,'I can now run up to 11 jobs at the same time, which is 5 times more concurrent jobs than before.',1);
/*!40000 ALTER TABLE `project_kpi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_properties`
--

DROP TABLE IF EXISTS `project_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectId` int(11) NOT NULL DEFAULT '0',
  `facilityId` int(11) NOT NULL DEFAULT '1',
  `propname` varchar(255) NOT NULL DEFAULT '',
  `propvalue` varchar(255) NOT NULL DEFAULT '',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `propname_2` (`propname`),
  KEY `propname_3` (`propname`),
  KEY `projectId` (`projectId`),
  KEY `facilityId` (`facilityId`),
  CONSTRAINT `project_properties_ibfk_1` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `project_properties_ibfk_3` FOREIGN KEY (`facilityId`) REFERENCES `facility` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=latin1 COMMENT='Project properties not captured in project table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_properties`
--

LOCK TABLES `project_properties` WRITE;
/*!40000 ALTER TABLE `project_properties` DISABLE KEYS */;
INSERT INTO `project_properties` VALUES (1,1,10,'VM','cerjmagprd01.uoa.auckland.ac.nz','2016-03-02 01:55:07'),(2,1,10,'RAM','32','2016-03-02 01:55:07'),(3,1,10,'Disk space','200GB','2016-06-20 18:18:07'),(4,1,10,'CPUs','8','2016-03-02 01:55:07'),(5,2,10,'VM','ceraspprd01.uoa.auckland.ac.nz','2016-03-02 01:55:07'),(6,2,10,'RAM','64','2016-03-02 01:55:07'),(7,2,10,'Disk space','300GB','2016-03-02 01:55:07'),(8,2,10,'CPUs','8','2016-03-02 01:55:07'),(9,3,10,'VM','cervanprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(10,3,10,'RAM','20','2016-03-02 01:55:07'),(11,3,10,'CPUs','10','2016-03-02 01:55:07'),(12,3,10,'Disk space','3000GB','2016-03-02 01:55:07'),(13,4,10,'VM','cermurprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(14,4,10,'RAM','32','2016-03-02 01:55:07'),(15,4,10,'Disk space','200GB','2016-03-02 01:55:07'),(16,4,10,'CPUs','8','2016-03-02 01:55:07'),(17,5,10,'VM','ceradaprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(18,5,10,'CPUs','8','2016-03-02 01:55:07'),(19,5,10,'Disk space','1000GB','2016-03-02 01:55:07'),(20,5,10,'RAM','64','2016-03-02 01:55:07'),(21,6,10,'CPUs','8','2016-03-02 01:55:07'),(22,6,10,'RAM','100','2016-03-02 01:55:07'),(23,6,10,'Disk space','150GB','2016-03-02 01:55:07'),(24,6,10,'VM','cerhosprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(25,7,1,'CPUs','4','2016-03-02 01:55:07'),(26,7,1,'Disk space','200GB','2016-03-02 01:55:07'),(27,7,1,'RAM','128','2016-03-02 01:55:07'),(28,7,10,'VM','cerarcgprd01.uoa.auckland.ac.nz','2016-03-02 01:55:07'),(29,8,10,'CPUs','8','2016-03-02 01:55:07'),(30,8,10,'RAM','64','2016-03-02 01:55:07'),(31,8,10,'VM','cerhlinprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(32,8,10,'Disk space','1000GB','2016-03-02 01:55:07'),(33,9,10,'CPUs','14','2016-03-02 01:55:07'),(34,9,10,'RAM','16','2016-03-02 01:55:07'),(35,9,10,'Disk space','115GB','2016-03-02 01:55:07'),(36,9,10,'VM','cerzcheprd01.uoa.auckland.ac.nz','2016-03-02 01:55:07'),(37,10,10,'RAM','24','2016-03-02 01:55:07'),(38,10,10,'Disk space','8TB','2016-03-02 01:55:07'),(39,10,10,'CPUs','6','2016-03-02 01:55:07'),(40,10,10,'VM','foshcntst01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(41,11,10,'VM','sdsclpprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(42,11,1,'CPUs','2','2016-03-02 01:55:07'),(43,11,1,'RAM','4','2016-03-02 01:55:07'),(44,11,1,'Disk Space','500GB','2016-03-02 01:55:07'),(45,12,10,'VM','fosckaprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(46,12,1,'CPUs','2','2016-03-02 01:55:07'),(47,12,1,'RAM','4','2016-03-02 01:55:07'),(48,12,1,'Disk space','1000GB','2016-03-02 01:55:07'),(49,13,10,'VM','fosdatprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(50,13,1,'CPUs','2','2016-03-02 01:55:07'),(51,13,1,'RAM','4','2016-03-02 01:55:07'),(52,13,1,'Disk space','1000GB','2016-03-02 01:55:07'),(53,14,10,'vm','stadspprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(54,14,10,'CPUs','20, 20, 20','2016-03-02 01:55:07'),(55,14,10,'RAM','200, 200, 200','2016-03-02 01:55:07'),(56,14,10,'Disk space','500GB, 500GB, 500GB','2016-03-02 01:55:07'),(57,14,10,'vm','stadspprd02.cer.auckland.ac.nz','2016-03-02 01:55:07'),(58,14,10,'vm','stadspprd03.cer.auckland.ac.nz','2016-03-02 01:55:07'),(59,15,10,'VM','cerocsprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(60,15,10,'CPUs','2','2016-03-02 01:55:07'),(61,15,10,'RAM','4','2016-03-02 01:55:07'),(62,15,10,'Disk space','1TB','2016-03-02 01:55:07'),(63,15,10,'CNAME','owncloud.cer.auckland.ac.nz','2016-03-02 01:55:07'),(64,16,10,'VM','cersfsprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(65,16,10,'CPUs','2','2016-03-02 01:55:07'),(66,16,10,'RAM','4','2016-03-02 01:55:07'),(67,16,10,'Disk space','1TB','2016-03-02 01:55:07'),(68,16,10,'CNAME','seafile.cer.auckland.ac.nz','2016-03-02 01:55:07'),(69,17,10,'VM','cerhlinprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(70,17,10,'CPUs','8','2016-03-02 01:55:07'),(71,17,10,'RAM','64GB','2016-03-02 01:55:07'),(72,17,10,'Disk Space','1000GB','2016-03-02 01:55:07'),(73,18,10,'VM','cerfpgaprd01.uoa.auckland.ac.nz','2016-03-02 01:55:07'),(74,18,10,'CPUs','8','2016-03-02 01:55:07'),(75,18,10,'RAM','24GB','2016-03-02 01:55:07'),(76,18,10,'Disk space','500GB','2016-03-02 01:55:07'),(77,19,10,'VM','cersutprd01.cer.auckland.ac.nz','2016-03-02 01:55:07'),(78,19,10,'RAM','40GB','2016-03-02 01:55:07'),(79,19,10,'Disk space','40GB','2016-03-02 01:55:07'),(80,19,10,'CPUs','10','2016-03-02 01:55:07'),(81,20,10,'VM','cerwampprd01.uoa.auckland.ac.nz','2016-03-02 01:55:08'),(82,20,10,'CPUs','4','2016-03-02 01:55:08'),(83,20,10,'RAM','8GB','2016-03-02 01:55:08'),(84,20,10,'Disk space','100GB','2016-03-02 01:55:08'),(85,21,10,'VM','certhuprd01.uoa.auckland.ac.nz','2016-03-02 01:55:08'),(86,21,10,'RAM','20GB','2016-03-02 01:55:08'),(87,21,10,'Disk space','20GB','2016-03-02 01:55:08'),(88,21,10,'CPUs','2','2016-03-02 01:55:08'),(89,22,10,'VM','cersklaprd01.its.auckland.ac.nz','2016-03-02 01:55:08'),(90,22,10,'RAM','32 GB','2016-03-02 01:55:08'),(91,22,10,'CPUs','3','2016-03-02 01:55:08'),(92,22,10,'Disk Space','3.7 TB','2016-03-02 01:55:08');
/*!40000 ALTER TABLE `project_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_researchoutput`
--

DROP TABLE IF EXISTS `project_researchoutput`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_researchoutput` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectId` int(11) NOT NULL,
  `typeId` int(11) DEFAULT NULL,
  `description` text,
  `uri` text,
  `doi` text,
  `dateReported` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_researchoutput_ibfk_1` (`projectId`),
  CONSTRAINT `project_researchoutput_ibfk_1` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='Research output of projects';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_researchoutput`
--

LOCK TABLES `project_researchoutput` WRITE;
/*!40000 ALTER TABLE `project_researchoutput` DISABLE KEYS */;
INSERT INTO `project_researchoutput` VALUES (1,1,4,'Evaluation of Magnetic Pad Sizes and Topologies for Electric Vehicle Charging in IEEE Transactions on Power Electronics','','','2015-07-31'),(2,2,2,'(planned conference paper)\r\nA Numerical  Approach for Modelling Dual Reflux PSA Separation of N2 and CH4 in LNG Production, 2014, CHEMECA conference, Perth Australia.','','','2014-09-22'),(3,2,4,'Non-isothermal Numerical Model of Experimental Dual Reflux Pressure Swing Adsorption Cycles for Separating N2 + CH4, under internal review','','','2015-07-31'),(4,4,10,'The openapi Project','https://www.stat.auckland.ac.nz/~paul/Talks/OpenAPI2014/','','2015-01-12'),(5,4,9,'Statistics Technical Blog: Introducing OpenAPI','http://stattech.wordpress.fos.auckland.ac.nz/2015-01-introducing-openapi/','','2015-03-09'),(6,4,6,'UseR! 2015 conference: \'The conduit Package\'.\r\nhttp://user2015.math.aau.dk/poster_session','','','2015-08-25'),(7,4,9,'Technical report: \'2015-04 Helping people to connect with data\'\r\nUoA Statistics Technical Blog\r\nhttp://stattech.wordpress.fos.auckland.ac.nz/2015-04-connect-with-data/','','','2015-08-25'),(8,6,2,'Hosking, R., Gahegan, M., & Dobbie, G. (2014). An eScience Tool for Understanding Copyright in Data Driven Sciences (Vol. 1, pp. 145–152). Presented at the e-Science (e-Science), 2014 IEEE 10th International Conference on, IEEE. http://doi.org/10.1109/eScience.2014.37','','','2015-07-21'),(9,7,6,'Emmitt, J.J., Phillipps, R.S., Holdaway. S.J., Masoud-Ansari, S., Gahegan, M. (2015) “Archaeological data management in the Fayum”. International Commission of the Later Prehistory of Northeastern Africa International Symposium: Desert and the Nile. Late Prehistory of the Nile Basin and the Sahara, 1-4 July June 2015, Poznań.\r\n','','','2015-07-21'),(10,7,6,'Emmitt, J.J., Phillipps, R.S., Holdaway. S.J., Masoud-Ansari, S., Gahegan, M. (2015) “Data Management on the Ahuahu/Great Mercury Island archaeology project”. 61st Conference for the New Zealand Archaeological Association, 17-20 June 2015, Waitangi.','','','2015-07-21'),(11,7,6,'Emmitt, J.J., Masoud-Ansari, S., Phillipps, R.S., Holdaway. S.J., Gahegan, M. (2013) “Archaeology eResearch Collaborative International (A.R.C.I)” Opus International Consultants Poster Session. 59th Conference for the New Zealand Archaeological Association, 19-22 June 2013, Cambridge.','','','2015-07-21'),(12,8,8,'I have been able to solve large FEA problems for my research that aren\'t even possible on my desktop machine ','','','2015-08-13'),(13,10,6,'Roberts, R.P., Hach, S., Tippett, L., Addis, D.R. Is intraindividual BOLD signal variability independent from mean BOLD signal? Poster presented at the Organisation for Human Brain Mapping Annual Meeting, Hawai’i, June 2015.','','','2015-07-24');
/*!40000 ALTER TABLE `project_researchoutput` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projectactiontype`
--

DROP TABLE IF EXISTS `projectactiontype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projectactiontype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='List of types of a project action';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectactiontype`
--

LOCK TABLES `projectactiontype` WRITE;
/*!40000 ALTER TABLE `projectactiontype` DISABLE KEYS */;
INSERT INTO `projectactiontype` VALUES (3,'AdviserAction'),(2,'FollowUp'),(1,'Review'),(4,'Survey');
/*!40000 ALTER TABLE `projectactiontype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projectstatus`
--

DROP TABLE IF EXISTS `projectstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projectstatus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COMMENT='List of states a project can be in';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectstatus`
--

LOCK TABLES `projectstatus` WRITE;
/*!40000 ALTER TABLE `projectstatus` DISABLE KEYS */;
INSERT INTO `projectstatus` VALUES (4,'Closed'),(5,'Incomplete'),(11,'Marked for closure'),(1,'Open'),(6,'Open-Not Public'),(7,'Pending'),(10,'Received'),(9,'Rejected'),(2,'Reopened'),(3,'Suspended'),(8,'Withdrawn');
/*!40000 ALTER TABLE `projectstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projecttype`
--

DROP TABLE IF EXISTS `projecttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projecttype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='List of types a project can have';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projecttype`
--

LOCK TABLES `projecttype` WRITE;
/*!40000 ALTER TABLE `projecttype` DISABLE KEYS */;
INSERT INTO `projecttype` VALUES (2,'Commercial'),(1,'Institution');
/*!40000 ALTER TABLE `projecttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `researchoutputtype`
--

DROP TABLE IF EXISTS `researchoutputtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `researchoutputtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='List of research output types';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `researchoutputtype`
--

LOCK TABLES `researchoutputtype` WRITE;
/*!40000 ALTER TABLE `researchoutputtype` DISABLE KEYS */;
INSERT INTO `researchoutputtype` VALUES (1,'Book'),(2,'Conference Proceedings'),(3,'Grant'),(4,'Journal Article'),(9,'Other'),(5,'Patent'),(6,'Poster'),(7,'Software Package'),(10,'Talk'),(8,'Thesis');
/*!40000 ALTER TABLE `researchoutputtype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-22  9:39:07
