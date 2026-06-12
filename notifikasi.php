<?php

header("Content-Type: application/json");

error_reporting(0);

include "koneksi.php";

// =========================
// VALIDASI USER
// =========================
if(!isset($_POST['user_id'])){

    echo json_encode([

        "status" => "error",

        "message" => "user_id kosong"

    ]);

    exit;
}

// =========================
// USER LOGIN
// =========================
$user_id =
        trim($_POST['user_id']);

// =========================
// QUERY NOTIFIKASI
// =========================
$query = "
SELECT *
FROM notifications
WHERE user_id = '$user_id'
ORDER BY id DESC
";

// EKSEKUSI
$result =
        pg_query($conn, $query);

// VALIDASI QUERY
if(!$result){

    echo json_encode([

        "status" => "error",

        "message" => pg_last_error($conn)

    ]);

    exit;
}

// =========================
// ARRAY DATA
// =========================
$data = [];

// LOOP DATA
while($row = pg_fetch_assoc($result)){

    $data[] = [

        // ID NOTIF
        "id" =>
                $row['id'],

        // TITLE
        "title" =>
                $row['title'],

        // MESSAGE
        "message" =>
                $row['message'],

        // TYPE
        "type" =>
                $row['type'],

        // TANGGAL
        "time" =>
                date(

                        'd M Y • H:i',

                        strtotime(
                                $row['created_at']
                        )

                )
    ];
}

// =========================
// RESPONSE
// =========================
echo json_encode([

    "status" => "success",

    "data" => $data

]);

?>