<?php

// Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/config.php';
include_once '../../models/Tracking.php';
include_once '../../models/Reminder.php';
include_once '../../models/MonitorDate.php';
include_once '../../models/Habit.php';
include_once '../../models/HabitSuggestion.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

// Instantiate
$tracker = new Tracking($db);
$reminder = new Reminder($db);
$habit = new Habit($db);
$date = new MonitorDate($db);
$habitSuggestion = new HabitSuggestion($db);
$error = true;

// Get raw posted data
$data = json_decode(file_get_contents("php://input"));

// get habit info
$habit->habit_id = $data->habit_id;
$habit->user_id = $data->user_id; 
$habit->group_id = $data->group_id;
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

// reminder list
$arr_reminder = $data->reminder_list;

// tracking list
$arr_tracking = $data->tracking_list;

// save to habit_name_suggestion
$habitSuggestion->habit_name_id = $data->habit_name_id;
$habitSuggestion->habit_name_uni = $data->habit_name;
$habitSuggestion->habit_name_ascii = $data->habit_name_ascii;

if ($habitSuggestion->findNameAscii()) {
    $habitSuggestion->updateCount();
} else {
    $habitSuggestion->habit_name_count = 1;
    $habitSuggestion->total_track = 0;
    $habitSuggestion->success_track = 0;
    $habitSuggestion->create();
}

// save new habit
if ($habit->create()) {

    $date->monitor_id = $data->monitor_id;
    $date->habit_id = $data->habit_id;
    $date->mon = $data->mon;
    $date->tue = $data->tue;
    $date->wed = $data->wed;
    $date->thu = $data->thu;
    $date->fri = $data->fri;
    $date->sat = $data->sat;
    $date->sun = $data->sun;
    
    // save monitor_dates
    if ($date->create()) {

        $habit->monitor_id = $data->monitor_id;

        if ($habit->update()) {

            // reminder list
            for($i = 0; $i < count($arr_reminder); $i++) {
                $item = $arr_reminder[$i];
                $reminder->reminder_id = $item->server_id;
                $reminder->habit_id = $item->habit_id;
                $reminder->remind_start_time = $item->remind_start_time;
                $reminder->remind_end_time = $item->remind_end_time;
                $reminder->repeat_type = $item->repeat_type;
                $reminder->reminder_description = $item->reminder_description;
                if ($reminder->create()) {
                    $error = false;
                } else {
                    $error = true;
                    break;
                }
            }

            // tracking list
            for($i = 0; $i < count($arr_tracking); $i++) {
                $item = $tracker->getTrackWithParam((array) $arr_tracking[$i]);
                if($item) {
                    $tracker->updateWithParam((array) $arr_tracking[$i]);
                } else {
                    $tracker->createWithParam((array) $arr_tracking[$i]);
                }
            }
        }
    }
}

if (!$error) {
    echo json_encode(
        array(
            'result' => '1',
            'id' => $habit->habit_id
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
