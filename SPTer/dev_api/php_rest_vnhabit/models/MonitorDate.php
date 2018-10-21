<?php

include_once '../../models/Model.php';

class MonitorDate extends Model {
    // db
    private $conn;
    private $table = 'monitor_date d';
    public $cols;
    private $params;

    public $monitor_id;
    public $habit_id;
    public $mon;
    public $tue;
    public $wed;
    public $thu;
    public $fri;
    public $sat;
    public $sun;

    public function __construct($db) {
        $this->conn = $db;
        $this->cols = $this->get_read_param(array('conn', 'table', 'cols', 'params'), 'd');
        $this->params = $this->get_query_param(array('monitor_id'));
    }

    // GET
    public function read() {
        $query = 'SELECT ' . $this->cols . ' FROM ' . $this->table . ' ORDER BY monitor_id ASC';
        // Prepare statement
        $stmt = $this->conn->prepare($query);
        // Execute query
        $stmt->execute();
        return $stmt;
    }

    // Create User
    public function create() {
        // create query
        $query = 'INSERT INTO ' . $this->table . ' SET ' . $this->params;
        
        // Prepare statement
        $stmt = $this->conn->prepare($query);

        // Bind data
        $stmt = $this->bind_param_exc($stmt, array('monitor_id'));

        // Execute query
        if ($stmt->execute()) {
            $this->monitor_id = $this->conn->lastInsertId();
            return true;
        }

        return false;
    }
}

?>