<?php
require_once("controller/database_connection.php");
require_once("controller/food.php");

$food = new Food();
$food->getCategories();
?>