<?php
header('Content-type: json/application');
require 'functions.php';
$connection = mysqli_connect("db-mysql", "user", "password", "appDB");

$method = $_SERVER['REQUEST_METHOD'];
$params = explode("/", $_GET["q"]);
$page = $params[0];
$id = $params[1];

if ($method === "GET") {
    if ($page === "students") {
        if ($id) {
            getStudentById($connection, $id);
        } else {
            getStudents($connection);
        }
    } elseif ($page === "universities") {
        if ($id) {
            getUniversityById($connection, $id);
        } else {
            getUniversities($connection);
        }
    } else {
        notFoundPage();
    }
} elseif ($method === "POST") {
    $data = json_decode(file_get_contents("php://input"), true);
    if ($page === "students") {
        createStudent($connection, $data);
    } elseif ($page === "universities") {
        createUniversity($connection, $data);
    } else {
        notFoundPage();
    }
} elseif ($method === "DELETE") {
    if ($page === "students" && $id) {
        deleteStudentById($connection, $id);
    } elseif ($page === "universities" && $id) {
        deleteUniversityById($connection, $id);
    } else {
        notFoundPage();
    }
} elseif ($method === "PUT") {
    $data = json_decode(file_get_contents("php://input"), true);
    if ($page === "students" && $id) {
        updateStudentById($connection, $id, $data);
    } elseif ($page === "universities" && $id) {
        updateUniversityById($connection, $id, $data);
    } else {
        notFoundPage();
    }
}
