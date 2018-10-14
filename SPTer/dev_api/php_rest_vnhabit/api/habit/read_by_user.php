<?php

// Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../../config/Database.php';
include_once '../../models/Habit.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

// Instantiate Habit object
$habit = new Habit($db);

// Get username and password
$habit->user_id = isset($_GET['user_id']) ? $_GET['user_id'] : die();

// get habits by user_id
$result = $habit->read_by_user();

// get row count
$num = $result->rowCount();

if ($num > 0) {
    $habits_arr = array();
    while($row = $result->fetch(PDO::FETCH_ASSOC)) {
        extract($row);
        $habit_item = array(
            'habit_id' => $habit_id, 
            'user_id' => $user_id, 
            'category_id' => $category_id, 
            'habit_name' => $habit_name, 
            'habit_type' => $habit_type, 
            'count_type' => $count_type, 
            'unit' => $unit, 
            'goal_number' => $goal_number,
            'goal_time' => $goal_time,
            'start_date' => $start_date, 
            'end_date' => $end_date, 
            'created_date' => $created_date, 
            'habit_color' => $habit_color, 
            'habit_description' => $habit_description
        );

        // push to "data"
        array_push($habits_arr, $habit_item);
    }
        // turn to JSON
        echo json_encode(
            array(
                'result' => '1',
                'data' => $habits_arr
            )
        );

    }  else {
        // no users
        echo json_encode(
            array(
                'result' => '0'
            )
        );
        die();
    }
  
?>
