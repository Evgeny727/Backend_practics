<?php
function getStudents($connection)
{
    $students = mysqli_query($connection, "SELECT * FROM `students`");
    $studentsList = [];
    while ($student = mysqli_fetch_assoc($students)) {
        $studentsList[] = $student;
    }
    echo json_encode($studentsList);
}


function getUniversities($connection)
{
    $universities = mysqli_query($connection, "SELECT * FROM `universities`");
    $universitiesList = [];
    while ($university = mysqli_fetch_assoc($universities)) {
        $universitiesList[] = $university;
    }

    echo json_encode($universitiesList);
}

function getUniversityById($connection, $id)
{
    $university = mysqli_fetch_assoc(mysqli_query($connection, "SELECT * FROM `universities` where id=$id"));
    if (!$university) {
        notFound();
    } else {
        echo json_encode($university);

    }
}

function getStudentById($connection, $id)
{
    $student = mysqli_fetch_assoc(mysqli_query($connection, "SELECT * FROM `students` where id=$id"));
    if (!$student) {
        notFound();
    } else {
        echo json_encode($student);

    }
}

function notFound()
{
    http_response_code(404);
    $message = [
        "message" => "Entity not found",
    ];
    echo json_encode($message);
}

function notFoundPage()
{
    http_response_code(404);
    $message = [
        "message" => "Not found",
    ];
    echo json_encode($message);
}

function createStudent($connection, $data) {
    $name = $data["name"];
    $surname = $data["surname"];
    $age = $data["age"];
    $grade = $data["grade"];
    $id_university = $data["id_university"];
    mysqli_query($connection, "INSERT INTO `students` (name, surname, age, grade, id_university) VALUES ('$name','$surname', '$age', '$grade', '$id_university');");

    $studentId = mysqli_insert_id($connection);
    $student = mysqli_fetch_assoc(mysqli_query($connection, "SELECT * FROM `students` WHERE id=$studentId"));

    if (!$student) {
        http_response_code(400);
        $message = [
            "name" => "is required",
            "surname" => "is required",
            "age" => "is required",
            "grade" => "is required",
            "id_university" => "is required",
        ];
        echo json_encode($message);
    } else {
        http_response_code(201);
        echo json_encode($student);
    }
}

function createUniversity($connection, $data) {
    $name = $data["name"];
    $address = $data["address"];
    mysqli_query($connection, "INSERT INTO `universities` (name, address) VALUES ('$name','$address');");

    $universityId = mysqli_insert_id($connection);
    $university = mysqli_fetch_assoc(mysqli_query($connection, "SELECT * FROM `universities` WHERE id=$universityId"));

    if (!$university) {
        http_response_code(400);
        $message = [
            "name" => "is required",
            "address" => "is required",
        ];
        echo json_encode($message);
    } else {
        http_response_code(201);
        echo json_encode($university);
    }
}

function deleteStudentById($connection, $id) {
    $deleteResult = mysqli_query($connection, "DELETE FROM `students` WHERE id=$id");
    http_response_code(204);
}


function deleteUniversityById($connection, $id) {
    $deleteResult = mysqli_query($connection, "DELETE FROM `universities` WHERE id=$id");
    http_response_code(204);
}

function updateStudentById($connection, $id, $data) {
    $name = $data["name"];
    $surname = $data["surname"];
    $age = $data["age"];
    $grade = $data["grade"];
    $id_university = $data["id_university"];

    mysqli_query($connection, "UPDATE `students` SET name='$name', surname='$surname', age='$age', grade='$grade', id_university='$id_university' WHERE id=$id");
    getStudentById($connection, $id);
}

function updateUniversityById($connection, $id, $data) {
    $name = $data["name"];
    $address = $data["address"];

    mysqli_query($connection, "UPDATE `universities` SET name='$name', address='$address' WHERE id=$id");
    getUniversityById($connection, $id);
}