<?php
    require "connection.php";
    
    $user = $_POST["user"];
    $type = $_POST["type"];
    $friend = $_POST["friend"];
    $amount = $_POST["amount"];
    $description = $_POST["description"];

    $mysql_query1 = "SELECT * FROM balances WHERE user_id1 = '$user' AND user_id2 = '$friend';";
    $result1 = mysqli_query($conn, $mysql_query1);
    $mysql_query2 = "SELECT * FROM balances WHERE user_id2 = '$user' AND user_id1 = '$friend';";
    $result2 = mysqli_query($conn, $mysql_query2);
    
    $balanceExists = FALSE;
    $userIsFirst = FALSE;
    $createTransaction = TRUE;
    $balance = 0;
    
    if(mysqli_num_rows($result1)>0){
        $balanceExists = TRUE;
        $userIsFirst = TRUE;
        $mysql_query3 = "SELECT balance FROM balances WHERE user_id1 = '$user' AND user_id2 = '$friend';";
        $result3 = mysqli_query($conn, $mysql_query3);
        $row = mysqli_fetch_assoc($result3);
        $balance = $row["balance"];
    }
    else if(mysqli_num_rows($result2)>0){
        $balanceExists = TRUE;
        $mysql_query3 = "SELECT balance FROM balances WHERE user_id2 = '$user' AND user_id1 = '$friend';";
        $result3 = mysqli_query($conn, $mysql_query3);
        $row = mysqli_fetch_assoc($result3);
        $balance = $row["balance"];
    }
    
    //user1 = user, user2 = friend, calculate new balance and swap columns if balance goes negative (balance is absolute)
    if($userIsFirst and $balanceExists){
        $newBalance = 0;
        if($type === "Pay" or $type === "Lend"){
            $newBalance = $balance - $amount;
        }
        else if($type==="Borrow"){
            $newBalance = $balance + $amount;
        }
        if($newBalance < 0){
            $newBalance = abs($newBalance);
            $sql = "UPDATE balances SET balance = '$newBalance', user_id1 = (@temp:=user_id1), user_id1 = user_id2, user_id2 = @temp WHERE user_id1 = '$user' AND user_id2 = '$friend'";
            if($conn->query($sql) === TRUE){
                echo "Balance Updated";
            }
        }
        else{
            $sql = "UPDATE balances SET balance = '$newBalance' WHERE user_id1 = '$user' AND user_id2 = '$friend'";
            if($conn->query($sql) === TRUE){
                echo "Balance Updated";
            }
        }
    }
    //user1 = friend, user2 = user, calculate new balance and swap columns if balance goes negative (balance is absolute)
    else if($balanceExists){
        $newBalance = 0;
        if($type === "Pay" or $type === "Lend"){
            $newBalance = $balance + $amount;
        }
        else if($type==="Borrow"){
            $newBalance = $balance - $amount;
        }
        if($newBalance < 0){
            $newBalance = abs($newBalance);
            $sql = "UPDATE balances SET balance = '$newBalance', user_id1 = (@temp:=user_id1), user_id1 = user_id2, user_id2 = @temp WHERE user_id1 = '$friend' AND user_id2 = '$user'";
            if($conn->query($sql) === TRUE){
                echo "Balance Updated";
            }
        }
        else{
            $sql = "UPDATE balances SET balance = '$newBalance' WHERE user_id1 = '$friend' AND user_id2 = '$user'";
            if($conn->query($sql) === TRUE){
                echo "Balance Updated";
            }
        }
    }
    //no payment if no balance exists
    else if($type==="Pay"){
        $createTransaction = FALSE;
        echo "No payment required";
    }
    //create new balance user owes friend
    else if($type==="Borrow"){
        $sql = "INSERT INTO balances(user_id1, user_id2, balance) VALUES('$user','$friend','$amount')";
        if($conn->query($sql) === TRUE){
            echo "New Balance Created";
        }
    }
    //create new balance friend owes user
    else if($type==="Lend"){
        $sql = "INSERT INTO balances(user_id1, user_id2, balance) VALUES('$friend','$user','$amount')";
        if($conn->query($sql) === TRUE){
            echo "New Balance Created";
        }
    }
    //create the transaction
    if($createTransaction){
        $dateArray = getdate();
        $date = $dateArray['year']."-".$dateArray['mon']."-".$dateArray['mday'];
        $sql = "INSERT INTO transactions(user_id1, user_id2, type, description, amount, date, picture) VALUES('$user','$friend','$type','$description','$amount','$date',NULL)";
        if($conn->query($sql) === TRUE){
            echo "New Transaction Created";
        }
    }
?>