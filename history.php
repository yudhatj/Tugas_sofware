
<?php

header("Content-Type: application/json");

error_reporting(0);

include "koneksi.php";

// =========================
// VALIDASI
// =========================
if(!isset($_POST['user_id'])){

    echo json_encode([

        "status" => "error",

        "message" => "user_id kosong"

    ]);

    exit;
}

// USER LOGIN
$user_id =
        trim($_POST['user_id']);

// =========================
// SUMMARY HADIR
// =========================
$query_hadir = "
SELECT COUNT(*) as total
FROM attendance
WHERE user_id = '$user_id'
AND type = 'Check In'
";

$result_hadir =
        pg_query($conn, $query_hadir);

$data_hadir =
        pg_fetch_assoc($result_hadir);

$hadir =
        $data_hadir['total'] ?? 0;

// =========================
// SUMMARY PULANG
// =========================
$query_pulang = "
SELECT COUNT(*) as total
FROM attendance
WHERE user_id = '$user_id'
AND type = 'Check Out'
";

$result_pulang =
        pg_query($conn, $query_pulang);

$data_pulang =
        pg_fetch_assoc($result_pulang);

$pulang =
        $data_pulang['total'] ?? 0;

// =========================
// SUMMARY IZIN
// =========================
$query_izin = "
SELECT COUNT(*) as total
FROM pengajuan_izin
WHERE user_id = '$user_id'
AND jenis = 'Izin'
";

$result_izin =
        pg_query($conn, $query_izin);

if($result_izin){

    $data_izin =
            pg_fetch_assoc($result_izin);

    $izin =
            $data_izin['total'] ?? 0;

}else{

    $izin = 0;
}

// =========================
// SUMMARY SAKIT
// =========================
$query_sakit = "
SELECT COUNT(*) as total
FROM pengajuan_izin
WHERE user_id = '$user_id'
AND jenis = 'Sakit'
";

$result_sakit =
        pg_query($conn, $query_sakit);

if($result_sakit){

    $data_sakit =
            pg_fetch_assoc($result_sakit);

    $sakit =
            $data_sakit['total'] ?? 0;

}else{

    $sakit = 0;
}

// =========================
// HISTORY ABSENSI
// =========================
$query_history = "
SELECT *
FROM attendance
WHERE user_id = '$user_id'
ORDER BY id DESC
";

$result_history =
        pg_query($conn, $query_history);

// ARRAY DATA
$data = [];

// LOOP DATA
while($row = pg_fetch_assoc($result_history)){

    $data[] = [

        "id" =>
            $row['id'],

        "tanggal" =>
            $row['date'],

        "jam" =>
            $row['time'],

        "type" =>
            $row['type'],

        "lokasi" =>
            $row['address']

    ];
}

// =========================
// RESPONSE JSON
// =========================
echo json_encode([

    "status" => "success",

    "hadir" => $hadir,

    "pulang" => $pulang,

    "izin" => $izin,

    "sakit" => $sakit,

    "data" => $data

]);

?>