<?php

header("Content-Type: application/json");

date_default_timezone_set("Asia/Jakarta");

include "koneksi.php";

// VALIDASI USER ID
if(empty($_POST['user_id'])){

    echo json_encode([
        "status" => "error",
        "message" => "user_id kosong"
    ]);

    exit;
}

$user_id = trim($_POST['user_id']);

// CHECK IN TERAKHIR
$query_checkin = "
SELECT time
FROM attendance
WHERE user_id = $1
AND type = 'Check In'
ORDER BY id DESC
LIMIT 1
";

$result_checkin = pg_query_params(
    $conn,
    $query_checkin,
    array($user_id)
);

$data_checkin = pg_fetch_assoc($result_checkin);

// CHECK OUT TERAKHIR
$query_checkout = "
SELECT time
FROM attendance
WHERE user_id = $1
AND type = 'Check Out'
ORDER BY id DESC
LIMIT 1
";

$result_checkout = pg_query_params(
    $conn,
    $query_checkout,
    array($user_id)
);

$data_checkout = pg_fetch_assoc($result_checkout);

// TOTAL IZIN
$query_izin = "
SELECT COUNT(*) AS total
FROM pengajuan_izin
WHERE user_id = $1
AND jenis = 'Izin'
";

$result_izin = pg_query_params(
    $conn,
    $query_izin,
    array($user_id)
);

$data_izin = pg_fetch_assoc($result_izin);

// TOTAL SAKIT
$query_sakit = "
SELECT COUNT(*) AS total
FROM pengajuan_izin
WHERE user_id = $1
AND jenis = 'Sakit'
";

$result_sakit = pg_query_params(
    $conn,
    $query_sakit,
    array($user_id)
);

$data_sakit = pg_fetch_assoc($result_sakit);

// RESPONSE
echo json_encode([

    "status" => "success",

    "checkin" =>
        $data_checkin['time']
        ?? "Belum Absen",

    "checkout" =>
        $data_checkout['time']
        ?? "Belum Absen",

    "izin" =>
        $data_izin['total']
        ?? 0,

    "sakit" =>
        $data_sakit['total']
        ?? 0

]);

?>