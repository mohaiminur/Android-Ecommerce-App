<?php

$con=new mysqli("localhost","root","","SalesWeb");
$st=$con->prepare("select price,qty from items inner join bill_det on items.id=bill_det.itemid where bill_det.bill_no=?");
$st->bind_param("i", $_GET["bill_no"]);
$st->execute();
$rs=$st->get_result();
$total=0;
while($row=$rs->fetch_assoc())
{
    $total=$total + ($row["price"]*$row["qty"]);
}

echo $total;


