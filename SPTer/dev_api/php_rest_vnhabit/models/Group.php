<?php

include_once '../../models/Model.php';

    class Group extends Model{
        private $conn;
        private $table = 'group';
        private $cols;
        private $params;
        private $colsArr = array(
            'group_id', 
            'parrent_id', 
            'group_name', 
            'group_icon', 
            'group_description'
        );

        public $group_id;
        public $parent_id;
        public $group_name;
        public $group_icon;
        public $group_description;

        public function __construct($db) {
            $this->conn = $db;
            $this->cols = $this->get_read_param(array('conn', 'table', 'cols', 'params'), NULL);
            $this->params = $this->get_query_param(array('habit_id'));
        }

        // Get all User
        public function read() {
            $query = 'SELECT ' . $this->cols . ' FROM `' . $this->table . '` ORDER BY group_id ASC';
            // Prepare statement
            $stmt = $this->conn->prepare($query);            
            // Execute query
            $stmt->execute();
            return $stmt;
        }

        // Get Single category by category_id
        public function read_single() {
            // Create query
            $query = 'SELECT ' . $this->cols . ' FROM ' . $this->table . 
                ' WHERE
                    category_id = :category_id 
                    LIMIT 0,1';

            // Prepare statement
            $stmt = $this->conn->prepare($query);

            // Bind params
            $stmt->bindParam(":category_id", $this->category_id);

            // Execute query
            $stmt->execute();
            // get row count
            $num = $stmt->rowCount();

            if ($num == 1) {
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                return $row;
            } else {
                return NULL;
            }
        }

        // Get Single category group by parrent
        public function read_childs() {
            // Create query
            $query = 'SELECT ' . $this->cols . ' FROM ' . $this->table . 
                ' WHERE
                    parent_id = :parent_id 
                    LIMIT 0,1';

            // Prepare statement
            $stmt = $this->conn->prepare($query);

            // Bind params
            $stmt->bindParam(":parent_id", $this->category_id);

            // Execute query
            $stmt->execute();
            // get row count
            $num = $stmt->rowCount();

            if ($num == 1) {
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                return $row;
            } else {
                return NULL;
            }
        }


    }
?>
