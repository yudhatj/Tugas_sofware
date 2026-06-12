<?php

header("Content-Type: application/json");

// HILANGKAN WARNING HTML
error_reporting(0);

include "koneksi.php";

// CEK USER ID
if(!isset($_POST['user_id'])){

    echo json_encode([

        "status" => "error",

        "message" => "user_id kosong"

    ]);

    exit;
}

// USER LOGIN
$user_id =
        $_POST['user_id'];

// QUERY ABSEN TERAKHIR
$query = "
SELECT
    attendance.*,
    users.nama,
    users.position

FROM attendance

JOIN users
ON users.id = attendance.user_id

WHERE attendance.user_id = $1

ORDER BY attendance.id DESC

LIMIT 1
";

// EXECUTE QUERY
$result =
        pg_query_params(
                $conn,
                $query,
                array($user_id)
        );

// ERROR QUERY
if(!$result){

    echo json_encode([

        "status" => "error",

        "message" => pg_last_error($conn)

    ]);

    exit;
}

// ADA DATA
if(pg_num_rows($result) > 0){

    $row =
            pg_fetch_assoc($result);

    echo json_encode([

        "status" => "success",

        // USER
        "nama" =>
            $row["nama"],

        "position" =>
            $row["position"],

        // ABSENSI
        "lokasi" =>
            $row["address"],

        "tanggal" =>
            $row["date"],

        "jam" =>
            $row["time"],

        "keterangan" =>
            $row["type"]

    ]);

}else{

    echo json_encode([

        "status" => "empty",

        "message" => "Data kosong"

    ]);
}
?>