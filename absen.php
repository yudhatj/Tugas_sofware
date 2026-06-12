<?php

header("Content-Type: application/json");

error_reporting(0);

include "koneksi.php";

// =========================
// VALIDASI DATA
// =========================
if(

    empty($_POST['user_id']) ||
    empty($_POST['type']) ||
    empty($_POST['date']) ||
    empty($_POST['time']) ||
    empty($_POST['address']) ||
    empty($_POST['latitude']) ||
    empty($_POST['longitude'])

){

    echo json_encode([

        "status" => "error",

        "message" => "Data tidak lengkap"

    ]);

    exit;
}

// =========================
// AMBIL DATA
// =========================
$user_id =
        trim($_POST['user_id']);

$type =
        trim($_POST['type']);

$date =
        trim($_POST['date']);

$time =
        trim($_POST['time']);

$address =
        trim($_POST['address']);

$latitude =
        trim($_POST['latitude']);

$longitude =
        trim($_POST['longitude']);

// =========================
// VALIDASI TYPE
// =========================
if(
    $type != "Check In" &&
    $type != "Check Out"
){

    echo json_encode([

        "status" => "error",

        "message" => "Type absensi tidak valid"

    ]);

    exit;
}

// =========================
// UPLOAD FOTO
// =========================
$photo_name = "";

if(isset($_FILES['photo'])){

    $file =
            $_FILES['photo'];

    // NAMA FILE
    $photo_name =
            time() . "_" .
            basename($file['name']);

    // TEMP FILE
    $tmp =
            $file['tmp_name'];

    // PATH
    $path =
            "uploads/" . $photo_name;

    // MOVE FILE
    if(move_uploaded_file($tmp, $path)){

        // BERHASIL
    }else{

        echo json_encode([

            "status" => "error",

            "message" => "Upload foto gagal"

        ]);

        exit;
    }
}

// =========================
// INSERT ABSENSI
// =========================
$query = "
INSERT INTO attendance
(

    user_id,
    type,
    date,
    time,
    address,
    latitude,
    longitude,
    photo

)

VALUES
(

    '$user_id',
    '$type',
    '$date',
    '$time',
    '$address',
    '$latitude',
    '$longitude',
    '$photo_name'

)
";

// EKSEKUSI ABSENSI
$result =
        pg_query($conn, $query);

// =========================
// JIKA GAGAL
// =========================
if(!$result){

    echo json_encode([

        "status" => "error",

        "message" => pg_last_error($conn)

    ]);

    exit;
}

// =========================
// DATA NOTIFIKASI
// =========================
if($type == "Check In"){

    $title =
            "Check In Berhasil";

    $message =
            "Anda berhasil melakukan check in";

    $notif_type =
            "checkin";

}else{

    $title =
            "Check Out Berhasil";

    $message =
            "Anda berhasil melakukan check out";

    $notif_type =
            "checkout";
}

// =========================
// INSERT NOTIFIKASI
// =========================
$notif = "
INSERT INTO notifications
(

    user_id,
    title,
    message,
    type

)

VALUES
(

    '$user_id',
    '$title',
    '$message',
    '$notif_type'

)
";

// EKSEKUSI NOTIF
$notif_result =
        pg_query($conn, $notif);

// =========================
// RESPONSE SUCCESS
// =========================
echo json_encode([

    "status" => "success",

    "message" => "Absensi berhasil",

    "photo" => $photo_name,

    "notif" => $notif_result ? "success" : "failed"

]);

?>