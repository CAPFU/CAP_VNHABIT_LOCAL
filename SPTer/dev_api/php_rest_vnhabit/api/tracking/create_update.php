<?php

// Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/Database.php';
include_once '../../models/Tracking.php';

// Instantiate DB & connect
$database = new Database();
$db = $database->connect();

// Instantiate
$tracker = new Tracking($db);

// Get raw posted data
$raw = json_decode(file_get_contents("php://input"));
$data = $raw->data;

$arrTrack = array();
for($i = 0; $i < count($data); $i++) {
    array_push($arrTrack, get_object_vars($data[$i]));
}

for($i = 0; $i < count($arrTrack); $i++) {
    $item = $arrTrack[$i];
    $row = $tracker->getTrackWithParam($item);

    // var_dump($row);

    if($row) {
        $tracker->updateWithParam($item);
    } else {
        $tracker->createWithParam($item);
    }
}

echo json_encode(
    array(
        'result' => '1',
        'id' => count($data)
    )
);

?>
