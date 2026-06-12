<?php

header("Content-Type: application/json");

error_reporting(0);

include "koneksi.php";

// =========================
// VALIDASI USER ID
// =========================
if(empty($_POST['user_id'])){

    echo json_encode([

        "status" => "error",

        "message" => "user_id kosong"

    ]);

    exit;
}

// =========================
// AMBIL USER ID
// =========================
$user_id =
        trim($_POST['user_id']);

// =========================
// QUERY PROFILE
// =========================
$query = "
SELECT

    id,
    nama,
    email,
    phone,
    position,
    photo

FROM users

WHERE id = '$user_id'
";

// =========================
// EKSEKUSI QUERY
// =========================
$result =
        pg_query($conn, $query);

// =========================
// VALIDASI QUERY
// =========================
if(!$result){

    echo json_encode([

        "status" => "error",

        "message" => pg_last_error($conn)

    ]);

    exit;
}

// =========================
// JIKA USER ADA
// =========================
if(pg_num_rows($result) > 0){

    $row =
            pg_fetch_assoc($result);

    // FOTO DEFAULT
    $photo =
            $row['photo'];

    if(

        $photo == null ||
        $photo == ""

    ){

        $photo =
                "";
    }

    // RESPONSE SUCCESS
    echo json_encode([

        "status" => "success",

        "data" => [

            "id" =>
                    $row['id'],

            "nama" =>
                    $row['nama'],

            "email" =>
                    $row['email'],

            "phone" =>
                    $row['phone'],

            "position" =>
                    $row['position'],

            "photo" =>
                    $photo

        ]

    ]);

}else{

    // =========================
    // USER TIDAK DITEMUKAN
    // =========================
    echo json_encode([

        "status" => "error",

        "message" => "User tidak ditemukan"

    ]);
}
?>