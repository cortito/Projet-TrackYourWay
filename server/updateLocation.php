<?php
    $host_name  = "db651682233.db.1and1.com";
    $database   = "db651682233";
    $user_name  = "dbo651682233";
    $password   = "trackyourway";

	$idBib = $_GET['idBib'];
	$latitude = $_GET['latitude'];
	$longitude = $_GET['longitude'];

	// on se connecte à MySQL
	$db = mysql_connect($host_name, $user_name, $password);

	// on sélectionne la base
	mysql_select_db($database,$db);

	// on crée la requête SQL
	$sql = 'UPDATE `TrackYourWay` SET `latitude`= '.$latitude.',`longitude`= '.$longitude.' WHERE `idBib`='.'"'.$idBib.'"';

	// on envoie la requête
	$req = mysql_query($sql) or die('Erreur SQL !<br>'.$sql.'<br>'.mysql_error());

	
?>
