<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Обработка отправленной формы для обновления записи о студенте
    $id = $_GET["id"];
    $fio = $_POST["fio"];
    $stud_ticket = $_POST["stud_ticket"];

    // Подключение к базе данных
    $conn = new mysqli("db", "user", "password", "appDB");

    if ($conn->connect_error) {
        die("Ошибка подключения к базе данных: " . $conn->connect_error);
    }

    // SQL-запрос для обновления записи
    $sql = "UPDATE students SET fio='$fio', stud_ticket='$stud_ticket' WHERE id='$id'";

    if ($conn->query($sql) === TRUE) {
        // Перенаправление на страницу чтения записи о студенте после успешной вставки
        header("Location: read.php");
        exit;
    } else {
        echo "Ошибка: " . $sql . "<br>" . $conn->error;
    }

    $conn->close();
} else {
    // Получение ID студента из запроса
    $id = $_GET["id"];

    // Подключение к базе данных
    $conn = new mysqli("db", "user", "password", "appDB");

    if ($conn->connect_error) {
        die("Ошибка подключения к базе данных: " . $conn->connect_error);
    }

    // SQL-запрос для выборки товара по ID
    $sql = "SELECT * FROM students WHERE id='$id'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $fio = $row["fio"];
        $stud_ticket = $row["stud_ticket"];
    } else {
        echo "Товар не найден.";
    }

    $conn->close();
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Обновить запись о студенте</title>
</head>
<body>
    <h2>Обновить запись о студенте</h2>
    <form method="get">
        ID: <input type="text" name="id" value="<?php echo $id; ?>"><br>
        <input type="submit" value="Найти">
    </form>
    <form method="post">
        ФИО: <input type="text" name="fio" value="<?php echo $fio; ?>"><br>
        Номер студенческого билета: <input type="text" name="stud_ticket" value="<?php echo $stud_ticket; ?>"><br>
        <input type="submit" value="Обновить">
    </form>
</body>
</html>