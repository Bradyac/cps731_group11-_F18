<?php
	require "connection.php"; // Connection information

        // Receive user ID
        $user_id = $_POST["user_id"];
        
        $transactionList = array();

        // Create and execute query to check users table for match
        $query = "SELECT * FROM transactions WHERE user_id1 like '$user_id'";
        $result = mysqli_query($conn, $query);
        
        // Success: Get email from results and send to client
        // Failure: Send "failed" to client
        if(mysqli_num_rows($result) > 0) {
                while( $row = mysqli_fetch_assoc($result)){
                        $userRecord = array('description' => $row['description'], 'type' => $row['type'], 'amount' => $row['amount']);
                        array_push($transactionList, $userRecord);
                        }
                echo json_encode($transactionList);
        } else {
                echo "failed";
        }
        
?>