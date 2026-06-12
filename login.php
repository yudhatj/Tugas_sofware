<?php

header("Content-Type: application/json");

// HILANGKAN WARNING HTML
error_reporting(0);

include "koneksi.php";

// CEK POST
if(
    empty($_POST['email']) ||
    empty($_POST['password'])
){

    echo json_encode([

        "status" => "error",

        "message" => "Email dan password wajib diisi"

    ]);

    exit;
}

// AMBIL INPUT
$email =
        trim($_POST['email']);

$password =
        trim($_POST['password']);

// QUERY LOGIN
$query = "
SELECT
    id,
    nama,
    email,
    phone,
    position,
    password

FROM users

WHERE email = $1
";

// EXECUTE QUERY
$result =
        pg_query_params(
                $conn,
                $query,
                array($email)
        );

// ERROR QUERY
if(!$result){

    echo json_encode([

        "status" => "error",

        "message" => pg_last_error($conn)

    ]);

    exit;
}

// CEK USER
if(pg_num_rows($result) > 0){

    // AMBIL DATA
    $row =
            pg_fetch_assoc($result);

    // CEK PASSWORD
    if($password == $row['password']){

        echo json_encode([

            "status" => "success",

            "id" =>
                $row['id'],

            "nama" =>
                $row['nama'],

            "email" =>
                $row['email'],

            "phone" =>
                $row['phone'],

            "position" =>
                $row['position']

        ]);

    }else{

        echo json_encode([

            "status" => "failed",

            "message" =>
                "Password salah"

        ]);
    }

}else{

    echo json_encode([

        "status" => "failed",

        "message" =>
            "Email tidak ditemukan"

    ]);
}

?>