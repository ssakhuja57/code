<?php

function find_intersect($likes1, $likes2){

    sort($likes1);
    sort($likes2);

    $intersect = array();

    $i = 0;
    $j = 0;

    while ($i < count($likes1) and $j < count($likes2)){
        if ($likes1[$i] == $likes2[$j]){
            array_push($intersect, $likes1[$i]);
            $i++;
            $j++;
            }
        else if ($likes1[$i] < $likes2[$j])
            $i++;
        else
            $j++;
    }

    return $intersect;
}


?>
