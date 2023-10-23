<?php
    // Подключение к базе данных
    $conn = new mysqli("db", "user", "password", "appDB");

    if ($conn->connect_error) {
        die("Ошибка подключения к базе данных: " . $conn->connect_error);
    }
    // SQL-запрос для выборки всех товаров
    $sql = "SELECT id, name, description, price FROM products";
    $result = $conn->query($sql);

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        // Обработка отправленной формы для создания товара
        $name = $_POST["name"];
        $description = $_POST["description"];
        $price = $_POST["price"];
        
        // SQL-запрос для создания записи
        $sql = "INSERT INTO products (name, description, price) VALUES ('$name', '$description', '$price')";

        if ($conn->query($sql) === TRUE) {
            header('Location: delete.php');
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
    <title>Создать товар</title>
</head>
<body>
    <h2>Создать товар</h2>
    <form method="post">
        Название: <input type="text" name="name"><br>
        Описание: <textarea name="description"></textarea><br>
        Цена: <input type="text" name="price"><br>
        <input type="submit" value="Создать">
    </form>
    <h2>Список товаров</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Описание</th>
            <th>Цена</th>
        </tr>
        <?php
        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                echo "<tr>";
                echo "<td>" . $row["id"] . "</td>";
                echo "<td>" . $row["name"] . "</td>";
                echo "<td>" . $row["description"] . "</td>";
                echo "<td>" . $row["price"] . "</td>";
                echo "</tr>";
            }
        } else {
            echo "Нет данных.";
        }
        ?>
    </table>
</body>
</html>