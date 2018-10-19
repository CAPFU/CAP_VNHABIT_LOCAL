<?php

// Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/Database.php';
include_once '../../models/Habit.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

// Instantiate
$habit = new Habit($db);

// Get raw posted data
$data = json_decode(file_get_contents("php://input"));

$habit->user_id = $data->user_id; 
$habit->group_id = $data->group_id;
$habit->monitor_id = $data->monitor_id;
$habit->habit_name = $data->habit_name;
$habit->habit_target = $data->habit_target;
$habit->habit_type = $data->habit_type;
$habit->monitor_type = $data->monitor_type;
$habit->monitor_unit = $data->monitor_unit;
$habit->monitor_number = $data->monitor_number; 
$habit->start_date = $data->start_date;
$habit->end_date = $data->end_date;
$habit->created_date = $data->created_date;
$habit->habit_color = $data->habit_color;
$habit->habit_description = $data->habit_description;

// Create user
if ($habit->create()) {
    echo json_encode(
        array(
            'result' => '1'
        )
    );
} else {
    echo json_encode(
        array(
            'result' => '0'
        )
    );
}

?>
