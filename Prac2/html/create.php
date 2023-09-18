<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Обработка отправленной формы для добавления студента
    $fio = $_POST["fio"];
    $stud_ticket = $_POST["stud_ticket"];
    
    // Подключение к базе данных
    $conn = new mysqli("db", "user", "password", "appDB");

    if ($conn->connect_error) {
        die("Ошибка подключения к базе данных: " . $conn->connect_error);
    }

    // SQL-запрос для создания записи
    $sql = "INSERT INTO students (fio, stud_ticket) VALUES ('$fio', '$stud_ticket')";

    if ($conn->query($sql) === TRUE) {
        // Перенаправление на страницу чтения студента после успешной вставки
        header("Location: read.php");
        exit;
    } else {
        echo "Ошибка: " . $sql . "<br>" . $conn->error;
    }

    $conn->close();
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Добавить студента</title>
</head>
<body>
    <h2>Добавить студента</h2>
    <form method="post">
        ФИО: <input type="text" name="fio"><br>
        Номер студенческого билета: <input type="text" name="stud_ticket"><br>
        <input type="submit" value="Создать">
    </form>
</body>
</html>