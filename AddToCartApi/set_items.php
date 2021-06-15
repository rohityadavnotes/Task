<?php
require_once("controller/database_connection.php");
require_once("controller/food.php");

$itemName 	        = $_POST['name'];
$itemCategory 		= $_POST['category'];
$itemRate		    = $_POST['rate'];
$itemImage          = "https://backend24.000webhostapp.com/image/photo_place_holder.png";

$food = new Food();
$food->setItem($itemName,$itemCategory,$itemRate,$itemImage);
?>