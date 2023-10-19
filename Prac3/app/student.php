<?php
header("Content-Type: application/json; charset=UTF-8");
$mysqli = new mysqli("db", "user", "password", "appDB");

if ($mysqli->connect_error) {
    die("Ошибка подключения к базе данных: " . $mysqli->connect_error);
}

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    if (isset($_GET['ID'])) {
        $ID = $_GET['ID'];
        $sql = "SELECT * FROM students WHERE ID = $ID";
        $result = $mysqli->query($sql);

        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            echo("Cтудент:");
            echo json_encode($row,JSON_UNESCAPED_UNICODE);
        } else {
            echo json_encode(["message" => "Студент не найден"],JSON_UNESCAPED_UNICODE);
        }
    } else {
        $sql = "SELECT * FROM students";
        $result = $mysqli->query($sql);

        $students = [];

        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $students[] = $row;
            }
        }

        echo("Список студентов:");
        echo json_encode($students,JSON_UNESCAPED_UNICODE);
    }
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $data = json_decode(file_get_contents("php://input"));

    if (isset($data->name) && isset($data->surname) && isset($data->course) && isset($data->id_inst)) {

        $name = $data->name;
        $surname = $data->surname;
        $course = $data->course;
        $id_inst = $data->id_inst;

        $sql = "INSERT INTO students (name, surname, course, id_inst) VALUES ('$name', '$surname', $course, $id_inst)";
        
        if ($mysqli->query($sql) === TRUE) {
            echo json_encode(["message" => "Студент успешно добавлен"],JSON_UNESCAPED_UNICODE);
        } else {
            echo json_encode(["error" => "Ошибка при добавлении студента: " . $mysqli->error],JSON_UNESCAPED_UNICODE);
        }

    } else {
        echo json_encode(["error" => "Отсутствуют данные для добавления студента"],JSON_UNESCAPED_UNICODE);
    }
}

if ($_SERVER['REQUEST_METHOD'] === 'PUT') {
    $data = json_decode(file_get_contents("php://input"));

    if (isset($data->ID) && isset($data->name) && isset($data->surname) && isset($data->course) && isset($data->id_inst)) {

        $ID = $data->ID;
        $name = $data->name;
        $surname = $data->surname;
        $course = $data->course;
        $id_inst = $data->id_inst;

        $sql = "UPDATE students SET name = '$name', surname = '$surname', course = $course, id_inst = $id_inst WHERE ID = $ID";
        
        if ($mysqli->query($sql) === TRUE) {
            echo json_encode(["message" => "Студент успешно обновлен"],JSON_UNESCAPED_UNICODE);
        } else {
            echo json_encode(["error" => "Ошибка при обновлении студента: " . $mysqli->error],JSON_UNESCAPED_UNICODE);
        }

    } else {
        echo json_encode(["error" => "Отсутствуют данные для обновления студента"],JSON_UNESCAPED_UNICODE);
    }
}

if ($_SERVER['REQUEST_METHOD'] === 'DELETE') {
    $data = json_decode(file_get_contents("php://input"));

    if (isset($data -> ID)){
        
        $ID = $data -> ID;

        $sql = "DELETE FROM students WHERE ID = $ID";
        $result = $mysqli->query($sql);
        
        if ($result === TRUE) {
            echo json_encode(["message" => "Студент успешно удален"],JSON_UNESCAPED_UNICODE);
        } else {
            echo json_encode(["error" => "Ошибка при удалении студента: " . $mysqli->error],JSON_UNESCAPED_UNICODE);
        }
    } else {
        echo json_encode(["error" => "Отсутствует ID студента"],JSON_UNESCAPED_UNICODE);
    }
}

$mysqli->close();

?>
