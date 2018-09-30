<?php

    class Database {
        // DB params
        private $host = 'localhost';
        private $db_name = 'vnhabit';
        private $username = 'root';
        private $password = '12345678';
        private $conn;

        public function connect() {
            $this->conn = null;

            try {
                $this->conn = new PDO('mysql:host=' . $this->host . ';dbname=' . $this->db_name,
                $this->username, $this->password);
                $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERROMODE_EXCEPTION);
            } catch(PDOException $e) {
                echo 'Connection Error: '. $e->getMessage();
            }
            return $this->conn;
        }
    }

?>