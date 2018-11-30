<?php
	require "connection.php"; 
	
	$user_id1 = $_POST["user_id1"];

	
	$query = "SELECT * FROM transactions WHERE user_id1 like '$user_id1'";
	$result = mysqli_query($conn, $query);

	while($row = mysqli_fetch_array($result)) {
                $transaction_id = $row["transaction_id"];
		$user_id1 = $row["user_id1"];
		$user_id2 = $row["user_id2"];
		$type = $row["type"];
		$description = $row["description"];
		$amount = $row["amount"];
		$date = $row["date"];
		echo nl2br($transaction_id . "," . $user_id1 . "," . $user_id2 . "," . $type . "," . $description . "," . $amount  . "," . $date . "\n");
       }
?>