
<?php

header("Content-Type: application/json");

error_reporting(0);

include "koneksi.php";

// VALIDASI
if(

    empty($_POST['user_id']) ||
    empty($_POST['nama']) ||
    empty($_POST['email']) ||
    empty($_POST['phone']) ||
    empty($_POST['position'])

){

    echo json_encode([

        "status" => "error",

        "message" => "Data tidak lengkap"

    ]);

    exit;
}

// AMBIL DATA
$user_id =
        trim($_POST['user_id']);

$nama =
        trim($_POST['nama']);

$email =
        trim($_POST['email']);

$phone =
        trim($_POST['phone']);

$position =
        trim($_POST['position']);

// QUERY
$query = "
UPDATE users
SET

    nama = '$nama',
    email = '$email',
    phone = '$phone',
    position = '$position'

WHERE id = '$user_id'
";

// EKSEKUSI
$result =
        pg_query($conn, $query);

// RESPONSE
if($result){

    echo json_encode([

        "status" => "success",

        "message" => "Profile berhasil diupdate"

    ]);

}else{

    echo json_encode([

        "status" => "error",

        "message" => pg_last_error($conn)

    ]);
}
?>