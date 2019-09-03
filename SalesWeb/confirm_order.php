<?php

$con=new mysqli("localhost","root","","SalesWeb");
$st=$con->prepare("select * from temp_order where mobile=?");
$st->bind_param("s", $_GET["mobile"]);
$st->execute();
$rs=$st->get_result();

$st2=$con->prepare("insert into bill(mobile) values(?)");
$st2->bind_param("s", $_GET["mobile"]);
$st2->execute();

$st4=$con->prepare("select max(bill_no) as bno from bill where mobile=?");
$st4->bind_param("s", $_GET["mobile"]);
$st4->execute();
$rs2=$st4->get_result();
$row_max=$rs2->fetch_assoc();

while($row=$rs->fetch_assoc())
{
    $st3=$con->prepare("insert into bill_det values(?,?,?)");
    $st3->bind_param("iii", $row_max["bno"],$row["itemid"],$row["qty"]);
    $st3->execute();
}

$st5=$con->prepare("delete from temp_order where mobile=?");
    $st5->bind_param("s", $_GET["mobile"]);
    $st5->execute();


echo $row_max["bno"];