<?php
// Подключение к базе данных
$conn = new mysqli("db", "user", "password", "appDB");

if ($conn->connect_error) {
    die("Ошибка подключения к базе данных: " . $conn->connect_error);
}

// SQL-запрос для выборки всех студентов
$sql = "SELECT * FROM students";
$result = $conn->query($sql);
?>

<!DOCTYPE html>
<html>
<head>
    <title>Список студентов</title>
</head>
<body>
    <h2>Список студентов</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>ФИО</th>
            <th>Номер студенческого билета</th>
        </tr>
        <?php
        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                echo "<tr>";
                echo "<td>" . $row["id"] . "</td>";
                echo "<td>" . $row["fio"] . "</td>";
                echo "<td>" . $row["stud_ticket"] . "</td>";
                echo "</tr>";
            }
        } else {
            echo "Нет данных.";
        }
        ?>
    </table>
</body>
</html>