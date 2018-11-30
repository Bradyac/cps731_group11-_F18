<?php
	require "connection.php"; // Connection information
	
	// Receive username and password
	$email = $_POST["email"];
	$password = $_POST["password"];
	
	// Create and execute query to check users table for match
	$query = "SELECT * FROM users WHERE email like '$email' and password like '$password';";
	$result = mysqli_query($conn, $query);
	
	// Success: Get user_id from results and send to client
	// Failure: Send "failed" to client
	if(mysqli_num_rows($result) > 0) {
		$row = mysqli_fetch_assoc($result);
		$user_id = $row["user_id"];
		echo $user_id;
	} else {
		echo "failed";
	}
?>