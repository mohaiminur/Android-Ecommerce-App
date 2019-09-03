<?php

$con=new mysqli("localhost","root","","SalesWeb");
$st_check=$con->prepare("insert into temp_order values(?,?,?)");
$st_check->bind_param("sii", $_GET["mobile"],$_GET["itemid"],$_GET["qty"]);
$st_check->execute();

