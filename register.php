<?php

header("Content-Type: application/json");

// HILANGKAN WARNING HTML
error_reporting(0);

include "koneksi.php";

// AMBIL DATA
$nama = $_POST['nama'];
$email = $_POST['email'];
$phone = $_POST['phone'];
$position = $_POST['position'];
$password = $_POST['password'];

// CEK EMAIL SUDAH ADA
$check = "
SELECT * FROM users
WHERE email = '$email'
";

$checkResult =
        pg_query($conn, $check);

// EMAIL SUDAH DIGUNAKAN
if(pg_num_rows($checkResult) > 0){

    echo "email_exists";

    exit;
}

// QUERY REGISTER
$query = "
INSERT INTO users
(
    nama,
    email,
    phone,
    position,
    password
)

VALUES
(
    '$nama',
    '$email',
    '$phone',
    '$position',
    '$password'
)
";

// EKSEKUSI
$result =
        pg_query($conn, $query);

// RESPONSE
if($result){

    echo "success";

}else{

    echo pg_last_error($conn);
}
?>