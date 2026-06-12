
<?php

header("Content-Type: application/json");

echo json_encode([
    "status" => "success",
    "checkin" => "01:51:07",
    "checkout" => "01:51:46",
    "izin" => 0,
    "sakit" => 0
]);