<?php

// Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../../config/Database.php';
include_once '../../models/Group.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

// Instantiate User object
$group = new Group($db);

// User query
$result = $group->read();

// get row count
$num = $result->rowCount();

// check if any users
if ($num > 0) {
    $group_arr = array();
    
    while($row = $result->fetch(PDO::FETCH_ASSOC)) {
        extract($row);
        $group_item = array(
            'group_id' => $group_id,
            'group_name' => $group_name,
            'parrent_id' => $parrent_id,
            'group_icon' => $group_icon,
            'group_description' => $group_description
        );

        // push to "data"
        array_push($group_arr, $group_item);
    }

    // turn to JSON
    echo json_encode(
        array(
            'result' => '1',
            'data' => $group_arr
        )
    );

} else {
    // no users
    echo json_encode(
        array(
            'result' => '0'
        )
    );
    die();
}

?>
