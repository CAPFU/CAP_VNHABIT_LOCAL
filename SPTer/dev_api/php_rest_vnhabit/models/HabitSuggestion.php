<?php

include_once '../../models/Model.php';

class HabitSuggestion extends Model {
    // db
    private $conn;
    private $table = 'habit_suggestion';
    private $cols;
    private $params;

    public $habit_name_id;
    public $habit_name_uni;
    public $habit_name;
    public $habit_name_count;

    public function __construct($db) {
        $this->conn = $db;
        $this->cols = $this->get_read_param(NULL, NULL);
    }

    public function search($searck_key) {
        $query = 'SELECT ' . $this->cols . ' FROM ' . $this->table . ' WHERE habit_name LIKE "%' . $searck_key . '%" LIMIT 5';
        // var_dump($query);
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt;
    }

    public function create() {
        $query = 'INSERT INTO ' . $this->table . ' SET ' . $this->get_query_param(array('habit_name_id'));
        $stmt = $this->conn->prepare($query);
        $stmt = $this->bind_param_exc($stmt, array('habit_name_id'));
        if ($stmt->execute()) {
            return true;
        }
        return false;
    }

    public function find($searck_key) {
        $query = 'SELECT ' . $this->cols . ' FROM ' . $this->table . ' WHERE habit_name = "' . $searck_key . '"';
        // var_dump($query);
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt;
    }

    public function updateCount() {
        // create query
        $query = 'UPDATE ' . $this->table . ' SET habit_name_count = habit_name_count + 1 WHERE habit_name_id = :habit_name_id';

        $stmt = $this->conn->prepare($query);

        $stmt = $this->bind_param($stmt, array('habit_name_id' => $this->habit_name_id));

        // Execute query
        if ($stmt->execute()) {
            return true;
        }

        // Print error if something goes wrong
        printf("Error: %s.\n", $stmt->error);
        return false;
    }
}

?>
