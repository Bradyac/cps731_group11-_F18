<?php
	require "connection.php"; // Connection information
	


                // Receive user ID
                $user_id = $_POST["user_id"];
        
                // Create and execute query to check users table for match
                $query = "SELECT * FROM users WHERE user_id like '$user_id' LIMIT 1";
                $result = mysqli_query($conn, $query);
                
                // Success: Get email from results and send to client
                // Failure: Send "failed" to client
                if(mysqli_num_rows($result) > 0) {
                        $row = mysqli_fetch_assoc($result);
        
                        $userRecord = array('fname' => $row['forename'],'lname' => $row['surname'], 'email' => $row['email']);
                        echo json_encode($userRecord);
                } else {
                        echo "failed";
                }
        
?>