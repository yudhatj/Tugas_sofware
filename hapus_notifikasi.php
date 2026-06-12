
<?php

header("Content-Type: application/json");

error_reporting(0);

include "koneksi.php";

// =========================
// VALIDASI ID
// =========================
if(!isset($_POST['id'])){

    echo json_encode([

        "status" => "error",

        "message" => "ID notifikasi kosong"

    ]);

    exit;
}

// =========================
// AMBIL ID
// =========================
$id =
        trim($_POST['id']);

// =========================
// QUERY HAPUS
// =========================
$query = "
DELETE FROM notifications
WHERE id = '$id'
";

// =========================
// EKSEKUSI
// =========================
$result =
        pg_query($conn, $query);

// =========================
// JIKA BERHASIL
// =========================
if($result){

    echo json_encode([

        "status" => "success",

        "message" => "Notifikasi berhasil dihapus"

    ]);

}else{

    // =========================
    // JIKA GAGAL
    // =========================
    echo json_encode([

        "status" => "error",

        "message" => pg_last_error($conn)

    ]);
}
?>