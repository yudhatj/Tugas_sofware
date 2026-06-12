<?php

header("Content-Type: application/json");

include "koneksi.php";

if(
    empty($_POST['user_id']) ||
    empty($_POST['photo'])
){

    echo json_encode([
        "status" => "error",
        "message" => "data kosong"
    ]);

    exit;
}

$user_id = $_POST['user_id'];

$base64 = $_POST['photo'];

// NAMA FILE
$file_name =
        "profile_" .
        time() .
        ".jpg";

$folder =
        "uploads_profile/";

$file_path =
        $folder .
        $file_name;

// DECODE BASE64
$image =
        base64_decode($base64);

// SIMPAN FILE
file_put_contents(
        $file_path,
        $image
);

// UPDATE DATABASE
$query = "
UPDATE users
SET photo = '$file_path'
WHERE id = '$user_id'
";

$result =
        pg_query($conn, $query);

if($result){

    echo json_encode([

        "status" => "success",

        "photo" => $file_path

    ]);

}else{

    echo json_encode([

        "status" => "error",

        "message" => pg_last_error($conn)

    ]);
}
?>