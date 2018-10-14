<?php

include_once '../../models/Model.php';

    class Habit extends Model {
        // db
        private $conn;
        private $table = 'habit';
        private $cols;
        private $params;
        private $colsArr = array(
            'habit_id', 
            'user_id', 
            'category_id', 
            'habit_name', 
            'habit_type', 
            'count_type', 
            'goal_number',
            'goal_time',
            'unit', 
            'start_date', 
            'end_date', 
            'created_date', 
            'habit_color', 
            'habit_description'
        );

        // habit
        public $habit_id;
        public $user_id;
        public $category_id;
        public $habit_name;
        public $habit_type;
        public $unit;
        public $count_type;
        public $start_date;
        public $end_date;
        public $created_date;
        public $habit_color;
        public $habit_description;

        public function __construct($db) {
            $this->conn = $db;
            $this->cols = implode(", ", $this->colsArr);
            $this->params = $this->make_query_param($this->colsArr);
        }

        // Get all Habit
        public function read() {
            $query = 'SELECT ' . $this->cols . ' FROM ' . $this->table . ' ORDER BY habit_id ASC';

            // Prepare statement
            $stmt = $this->conn->prepare($query);
            
            // Execute query
            $stmt->execute();

            return $stmt;
        }

        public function read_by_user() {
            // Create query
            $query = 'SELECT ' . $this->cols . ' FROM ' . $this->table . ' WHERE user_id = :user_id';
            // Prepare statement
            $stmt = $this->conn->prepare($query);
            // Bind params
            $stmt->bindParam(":user_id", $this->user_id);
            // Execute query
            $stmt->execute();

            return $stmt;
        }

        // Create Habit
        public function create() {
            // create query
            $query = 'INSERT INTO ' . $this->table . ' SET ' . $this->params;
            
            // Prepare statement
            $stmt = $this->conn->prepare($query);

            // Bind data
            $stmt->bindParam(':user_id', $this->user_id);
            $stmt->bindParam(':category_id', $this->category_id);
            $stmt->bindParam(':habit_name', $this->habit_name);
            $stmt->bindParam(':habit_type', $this->habit_type);
            $stmt->bindParam(':unit', $this->unit);
            $stmt->bindParam(':count_type', $this->count_type);
            $stmt->bindParam(':start_date', $this->start_date);
            $stmt->bindParam(':end_date', $this->end_date);
            $stmt->bindParam(':created_date', $this->created_date);
            $stmt->bindParam(':habit_color', $this->habit_color);
            $stmt->bindParam(':habit_description', $this->habit_description);

            // Execute query
            if ($stmt->execute()) {
                return true;
            }

            // Print error if something goes wrong
            printf("Error: %s.\n", $stmt->error);
            return false;
        }

        // Update Habit
        public function update() {
            // create query
            $query = 'UPDATE ' . $this->table . ' SET ' . $this->params . ' WHERE habit_id = :habit_id';

            // Prepare statement
            $stmt = $this->conn->prepare($query);

            // Bind data
            $stmt->bindParam(':habit_id', $this->habit_id);
            $stmt->bindParam(':user_id', $this->user_id);
            $stmt->bindParam(':category_id', $this->category_id);
            $stmt->bindParam(':habit_name', $this->habit_name);
            $stmt->bindParam(':habit_type', $this->habit_type);
            $stmt->bindParam(':unit', $this->unit);
            $stmt->bindParam(':count_type', $this->count_type);
            $stmt->bindParam(':start_date', $this->start_date);
            $stmt->bindParam(':end_date', $this->end_date);
            $stmt->bindParam(':created_date', $this->created_date);
            $stmt->bindParam(':habit_color', $this->habit_color);
            $stmt->bindParam(':habit_description', $this->habit_description);

            // Execute query
            if ($stmt->execute()) {
                return true;
            }

            // Print error if something goes wrong
            printf("Error: %s.\n", $stmt->error);
            return false;
        }

        // Detele Habit
        public function delete() {
            // create query
            $query = 'DELETE FROM ' . $this->table . ' WHERE habit_id = :habit_id';

            // Prepare statement
            $stmt = $this->conn->prepare($query);

            // Clean data
            $this->user_id = htmlspecialchars(strip_tags($this->habit_id));

            // Bind data
            $stmt->bindParam(':habit_id', $this->habit_id);

            // Execute query
            if($stmt->execute()) {
                return true;
            }
            // Print error if something goes wrong
            printf("Error: %s.\n", $stmt->error);
            return false;
        }
    }

?>
