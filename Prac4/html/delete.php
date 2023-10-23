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
        // Получение ID товара из запроса
        $id = $_POST["id"];

        // SQL-запрос для удаления товара по ID
        $sql = "DELETE FROM products WHERE id='$id'";

        if ($conn->query($sql) === TRUE) {
            header('Location: create.php');
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
    <title>Удалить товар</title>
</head>
<body>
    <h2>Удалить товар</h2>
    <form method="post">
        ID: <input type="text" name="id" value="<?php echo $id; ?>"><br>
        <input type="submit" value="Удалить">
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