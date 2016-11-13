<?php
    $host_name  = "db651682233.db.1and1.com";
    $database   = "db651682233";
    $user_name  = "dbo651682233";
    $password   = "trackyourway";
  
	// on se connecte à MySQL
	$db = mysql_connect($host_name, $user_name, $password);

	// on sélectionne la base
	mysql_select_db($database,$db);

	// on crée la requête SQL
	$sql = 'SELECT * FROM `TrackYourWay`';

	// on envoie la requête
	$req = mysql_query($sql) or die('Erreur SQL !<br>'.$sql.'<br>'.mysql_error());

	$rows = array();
	while($r = mysql_fetch_assoc($req)) {
	    $rows[] = $r;
	}
	print json_encode($rows);
	
?>
