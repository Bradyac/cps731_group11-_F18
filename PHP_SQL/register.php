<?php
	require "connection.php";
	
	$forename = $_POST["forename"];
	$surname = $_POST["surname"];
	$age = $_POST["age"];
	$username = $_POST["username"];
	$password = $_POST["password"];
	$query = "INSERT INTO employee_data (name, surname, age, username, password) VALUES ('$forename', '$surname', '$age', '$username', '$password');";
	if($conn->query($query) === TRUE) {
		echo "Registration successful";
	} else {
		echo "error: " . $query . "<br>" . $conn->error;
	}
	$conn->close();
?>