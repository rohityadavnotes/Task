<?php
require_once("controller/database_connection.php");
require_once("controller/food.php");

$categoryId 	= $_POST['category_id'];
//$categoryId 	= "1";
$food = new Food();
$food->getItems($categoryId);
?>