<?php
	require "connection.php"; // Connection information
	
	$user_id = $_POST["user_id"];
	$totalBorrowed = 0.00;
	$totalLent = 0.00;
	
	$query1 = "SELECT balance FROM balances WHERE user_id1 = '$user_id';";
	$result1 = mysqli_query($conn, $query1);
	$query2 = "SELECT balance FROM balances WHERE user_id2 = '$user_id';";
	$result2 = mysqli_query($conn, $query2);

	if(mysqli_num_rows($result1) > 0) {
        while($row = mysqli_fetch_array($result1)) {
			$totalBorrowed += $row["balance"];
		}
	}
	
	if(mysqli_num_rows($result2) > 0) {
        while($row = mysqli_fetch_array($result2)) {
			$totalLent += $row["balance"];
		}
	}
	
	echo $totalBorrowed . "," . $totalLent;
?>