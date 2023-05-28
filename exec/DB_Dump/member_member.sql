CREATE DATABASE  IF NOT EXISTS `member` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `member`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: k8e103.p.ssafy.io    Database: member
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` bigint NOT NULL AUTO_INCREMENT,
  `point` int NOT NULL DEFAULT '0',
  `password` varchar(255) NOT NULL,
  `player_id` varchar(255) NOT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (2,0,'1234','paymong'),(3,0,'password','0'),(4,0,'test','0'),(6,0,'test1','0'),(7,0,'test12','0'),(8,0,'1234','0'),(9,0,'1234','0'),(10,0,'test333','0'),(11,-3550,'$2a$10$sxJnX.3ZzdDnCoMLmxjnxOeeRFdG.9oGyc1usOO8YNQfDsSfbbHaO','12345'),(12,5,'$2a$10$qJEV5.E.SnP0ORmI3JGKRe62KSNnxEL1uAPu1Z0WQXUiZd6b5PBWO','11111111111'),(13,14075,'$2a$10$dK46OCpxMGzBqSYgSctb/.n0gxal4E0BmvncVBPfzObruSM3kUXYu','user'),(14,0,'$2a$10$L6FY5ia60jiAadKRUXogAOlGbO7v8gbZaJoPJLnmz8qbTsi2tJakm','1234'),(15,96021,'$2a$10$D9kBIVvXlces3HIM9KohB.y/jfwAtKdhuSVfVpXcJWfXU2Y2nQV7K','115290842268313724769'),(17,0,'$2a$10$W6h0MnDO4I83lxriHYmqwORnvC99szGkCk5i8xRdTap75oiIHdJgK','12322245'),(18,0,'$2a$10$KCVN1jXdyN2RkVYy7JBnWu/rXbZWHkyZbv2DVep2KNO4CYfU7HZMe','12345678910'),(19,99950,'$2a$10$m2mRa1ddhDeZ.aWmBRtt..XUktTnm2UnoBhk1gp4wP/qQ4axrtusu','7894561232'),(22,18800,'$2a$10$MpapesvQik/8bH5qZj63.ebf5qarE6pChNrYo6R2yodKrQWd4dLva','a_6413840864035103640'),(23,10840,'$2a$10$lIx06ULAVk1gCAQXTJ3OIeEr1qpGECH55BkFlMGYrA7050CLFT.qm','a_6641482048740515693'),(24,-1001,'$2a$10$NQmyDlbCcgU2a8mx/HoaOOY1wUGgcAqK6ZN16SAxLOKguKNq6SFlG','moski'),(34,74370,'$2a$10$IZ.G/pL/px4rQ6L6qS.6feuOy7L.23n1cxnJfNLWhIZ9I9cf13yUa','a_704551220282600864'),(35,0,'$2a$10$8j4iJTyyyaeNGzC5mbK2Kee1FmtH5NCAgDuF85jQF.BDmGYk79g4W','123456789101'),(36,1660,'$2a$10$ZI9Mf.fNYS9.klAa4sJ.n.o6jspipLtdsE8NGsMuWctiDc3KanS4u','a_127942136197874432'),(37,500,'$2a$10$eqdW0IsSyu1/H.ku6ZdaGeFw7doLFX2Whr.lbKi9gfWF15hxMdMvO','a_563689729252209829'),(38,0,'$2a$10$Bu05As6CtVwiMNIenSDrh.LG.AMprr/UGZNlMwGT6hIEbbFjRz5py','a_561845764087624844'),(39,1748,'$2a$10$Amk.K/23/jbvEmJpXlbxBud7LRxQTLfVNLM0b./1RqwP6DYgcjMiq','a_5802829116809484487'),(40,0,'$2a$10$A3elgLJwsPAkOxcUQrEMhOW1.iLC/OGyE6CnAw0CQE.VpGEkkzooi','a_6805320313928968187'),(41,5314,'$2a$10$O4HRDUZNQlPPq9PKTPbzfOq.e2jZYrXffMGJHyVMRku7gv1wWH8em','a_5249625781761092128'),(42,19500,'$2a$10$Xc/02zMsh1VwIv2syvnTQuu313e3ivR4eVSFZC1VAh7OaBkfkttPK','a_3385503882129137502'),(43,0,'$2a$10$cC3Mm7K0X5PQN7.5Yq6TZ.9iMkf/FtvWoCsq3asiHKTj5hQaKxtU2','a_2358847106148615886'),(44,0,'$2a$10$PAr6StfnCjp3CK8EWBiJD.BUyjXknhlbFBx43ZWIbdafCuK3mdWyu','a_994553171193169299'),(45,700,'$2a$10$QxcL/AB9XTyHrLyPIY/PGuvHJzDsgVUCgmKqmoaF1pYX6FL3NTkmq','a_5462440795378522892'),(46,0,'$2a$10$.JxcJHpTdC3p3h.08l.FTenGInvpLsEeJjBsAOU2kV3KJWcl.lwPO','a_8676229499333428574'),(47,0,'$2a$10$4JPa559Bht6dIGyPg0jd9uK2stPr5bFH20P7IgxJ9b0TOyoL3Cm9G','a_787819186970266699');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-18 22:53:01