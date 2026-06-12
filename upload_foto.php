
<?php

$image = $_POST['image'];

$name = "IMG_".time().".jpg";

file_put_contents(
    "uploads/".$name,
    base64_decode($image)
);

echo $name;
?>