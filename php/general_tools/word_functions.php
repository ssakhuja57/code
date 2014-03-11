<?php

$dict = '/usr/share/dict/words';

function get_word_count($word_length){
        return shell_exec('grep -c -P "^[a-z]{' . $word_length . '}$" ' . $GLOBALS['dict']);
}

function get_rand_word($word_length){
        $index = mt_rand(0, get_word_count($word_length));
        $word = shell_exec('grep -P "^[a-z]{' . $word_length . '}$" ' . $GLOBALS['dict'] . ' | sed -n \''. $index . 'p\'');
        // trim to get rid of eol character
        return array(trim($word), $index);
}

function get_word($word_length, $index){
        $word = shell_exec('grep -P "^[a-z]{' . $word_length . '}$" ' . $GLOBALS['dict'] . ' | sed -n \''. $index . 'p\'');
        // trim to get rid of eol character
        return trim($word);
}

function scramble($word){

        $word2 = $word;
        $length = strlen($word2);
        $word2 = str_split($word2);
        for ($x=0; $x<$length; $x++){
                $switch = mt_rand(0,$length-1);
                $temp = $word2[$x];
                $word2[$x] = $word2[$switch];
                $word2[$switch] = $temp;
        }

        $word2 = implode($word2);
        if ($word2 != $word)
                return $word2;
        else
                return scramble($word);
}

function get_hint($word_length, $index, $current){
        $word = get_word($word_length, $index);
        if ($current == $word)
                return $current;
        $word = str_split($word);
        $current = str_split($current);
        while (True){
                $hint_index = mt_rand(0, $word_length-1);
                if ($current[$hint_index] == '-')
                        break;
        }

        $current[$hint_index] = $word[$hint_index];
        return implode($current);
}

function check_word($answer, $letters){
        if (strlen($answer) != strlen($letters))
                return False;
        $answer = str_split($answer);
        $letters = str_split($letters);
        foreach($answer as $letter){
                $rem = array_search($letter, $letters);
                if ($rem !== False)
                        unset($letters[$rem]);
                else
                        return False;
        }
        $answer = implode($answer);
        if (shell_exec('grep -P "^' . $answer . '$" ' . $GLOBALS['dict']) != "")
                return True;
        return False;
}

?>
                                       
