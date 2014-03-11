<?php

function insertRecord($file, $score, $user, $date){
        $record_new = $score . ';' . $user . ';' . $date;
        $file_old = explode("\n", file_get_contents($file));
        $file_new = array();
        $inserted = 0;
        $line_old = 0;
        if (filesize($file) == 0){
                array_push($file_new, $record_new);
                $inserted = 1;
        }
        else {
                foreach($file_old as $record){
                        $record_arr = explode(';', $record);
                        if ($inserted == 0 and $score <= $record_arr[0]){
                                array_push($file_new, $record_new);
                                $inserted = 1;
                        }
                        if (count($file_new) == 5)
                                break;
                        array_push($file_new, $record);
                }
        }

        $scores_count = count($file_old);
        if ($inserted == 0 and $scores_count <= 4){
                array_push($file_new, $record_new);
                $inserted = 1;
        }

        $file = fopen($file, 'w');
        fwrite($file, implode("\n", $file_new));
        fclose($file);
        if ($inserted == 1)
                return 'Congratulations! Your score made it onto the leaderboard.';
        return '';
}

function getScoreboard($file, $headings){
        $res = '<table class="scoreboard">';
        $res .= '<tr class="scoreboard">';
        $res .= '<th class="scoreboard">Rank</th>';
        $res .= '<th class="scoreboard">';
        $res .= implode('</th><th class="scoreboard">', $headings);
        $res .= '</th></tr>';
        $file_records = explode("\n", file_get_contents($file));
        $rank = 1;
        foreach($file_records as $record){
                $res .= '<tr class="scoreboard">';
                $res .= '<th class="scoreboard">' . $rank . '</th>';
                $record_arr = explode(';', $record);
                foreach($record_arr as $field)
                        $res .= '<td class="scoreboard">' . $field . '</td>';
                $res .= '</tr>';
                $rank++;
        }
        $res .= '</table>';
        return $res;
}

?>

