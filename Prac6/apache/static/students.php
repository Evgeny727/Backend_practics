<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Hello, world!</title>
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<div class="m-4">
    <a class="btn btn-primary me-4" href="/">go home</a>
</div>

<table class="table">
    <thead>
    <tr>
        <th scope="col">id</th>
        <th scope="col">Name</th>
        <th scope="col">Surname</th>
        <th scope="col">Age</th>
        <th scope="col">Grade</th>
        <th scope="col">Id university</th>
    </tr>
    </thead>
    <tbody>


    <?php
    $mysqli = new mysqli("db-mysql", "user", "password", "appDB");
    $result = $mysqli->query("SELECT * FROM students");
    foreach ($result as $row) {
        echo "
        <tr>
            <th scope='row'>{$row['id']}</th>
            <td>{$row['name']}</td>
            <td>{$row['surname']}</td>
            <td>{$row['age']}</td>
            <td>{$row['grade']}</td>
            <td>{$row['id_university']}</td>
        </tr>";
    }
    ?>

    </tbody>
</table>

</body>
</html>