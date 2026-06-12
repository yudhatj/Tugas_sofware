<?php

header("Content-Type: application/json");

error_reporting(0);

include "koneksi.php";

// =========================
// VALIDASI
// =========================
if(

    !isset($_POST['user_id']) ||
    !isset($_POST['jenis']) ||
    !isset($_POST['tanggal']) ||
    !isset($_POST['waktu']) ||
    !isset($_POST['keterangan'])

){

    echo json_encode([

        "status" => "error",

        "message" => "Data tidak lengkap"

    ]);

    exit;
}

// =========================
// DATA
// =========================
$user_id =
        trim($_POST['user_id']);

$jenis =
        trim($_POST['jenis']);

$tanggal =
        trim($_POST['tanggal']);

$waktu =
        trim($_POST['waktu']);

$keterangan =
        trim($_POST['keterangan']);

// =========================
// INSERT PENGAJUAN
// =========================
$query = "
INSERT INTO pengajuan_izin
(

    user_id,
    jenis,
    tanggal,
    waktu,
    keterangan

)

VALUES
(

    '$user_id',
    '$jenis',
    '$tanggal',
    '$waktu',
    '$keterangan'

)
";

// =========================
// EXECUTE
// =========================
$result =
        pg_query($conn, $query);

// =========================
// JIKA BERHASIL
// =========================
if($result){

    // TITLE NOTIFIKASI
    $title =
            "Pengajuan " . $jenis;

    // MESSAGE NOTIFIKASI
    $message =
            "Pengajuan " . strtolower($jenis) . " berhasil dikirim";

    // TYPE NOTIFIKASI
    $type =
            "izin";

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
        '$type'

    )
    ";

    // EXECUTE NOTIF
    pg_query($conn, $notif);

    // RESPONSE SUCCESS
    echo json_encode([

        "status" => "success",

        "message" => "Pengajuan berhasil"

    ]);

}else{

    // RESPONSE ERROR
    echo json_encode([

        "status" => "error",

        "message" => pg_last_error($conn)

    ]);
}
?>