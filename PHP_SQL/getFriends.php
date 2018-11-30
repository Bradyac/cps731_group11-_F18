<?php
    require "connection.php";

    function fetchAssocStatement($stmt)
    {
    if($stmt->num_rows>0)
    {
        $result = array();
        $md = $stmt->result_metadata();
        $params = array();
        while($field = $md->fetch_field()) {
            $params[] = &$result[$field->name];
        }
        call_user_func_array(array($stmt, 'bind_result'), $params);
        if($stmt->fetch())
            return $result;
    }
    return null;
    }

    $user = $_POST['user'];
    
    $mysql_query1 = $conn->prepare("SELECT user_id2 FROM friends WHERE user_id1 = '$user';");
    $mysql_query1->execute();
    $mysql_query1->store_result();
    $friends1 = [];
    
    while($assoc_array = fetchAssocStatement($mysql_query1)){
        $friends1[] = $assoc_array['user_id2'];
    }
    
    $mysql_query2 = $conn->prepare("SELECT user_id1 FROM friends WHERE user_id2 = '$user';");
    $mysql_query2->execute();
    $mysql_query2->store_result();
    $friends2 = [];
    
    while($assoc_array = fetchAssocStatement($mysql_query2)){
        $friends2[] = $assoc_array['user_id1'];
    }
    
    $friendids = array_merge($friends1, $friends2);
    $friends = [];
    foreach($friendids as $friend_id){
        $mysql_query3 = $conn->prepare("SELECT * FROM users WHERE user_id = '$friend_id'");
        $mysql_query3->execute();
        $mysql_query3->store_result();
        while($assoc_array = fetchAssocStatement($mysql_query3)){
        $friends[] = $assoc_array['forename']." ".$assoc_array['surname'];
        }
    }
    $stringFriends = "FRIEND";
    for($i = 0; $i < sizeof($friends)-1; $i++){
        $stringFriends .= $friends[$i].",";
        $stringFriends .= $friendids[$i].",";
    }
    $stringFriends .= $friends[sizeof($friends)-1].",";
    $stringFriends .= $friendids[sizeof($friends)-1];
    echo $stringFriends;
?>