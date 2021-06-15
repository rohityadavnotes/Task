<?php
class DatabaseConnection {
    
    protected static $servername = "localhost";
    protected static $username   = "id14853975_username";
    protected static $password   = "Z1&XDJl5QIi*{8</";
    protected static $dbname     = "id14853975_item";
    
    public static function getConnection() {
        
        $servername = self::$servername;
        $username   = self::$username;
        $password   = self::$password;
        $dbname     = self::$dbname;
        
        /**
         * Create Connection
         */
        $connection = new mysqli($servername, $username, $password, $dbname);
        
        /**
         * Check Connection
         */
        if ($connection->connect_error) 
        {
            die("Connection failed: " . $connection->connect_error);
        }
        
        return $connection;
    } 	
}
?>
