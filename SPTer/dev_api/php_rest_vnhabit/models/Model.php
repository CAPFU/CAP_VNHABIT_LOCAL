<?php

    class Model {

        public function make_query_param($arr) {
            $str = '';
            $length = count($arr);
            for ($i = 0; $i < $length; $i++) {
                $str = $str . $arr[$i] . '= :' . $arr[$i];
                if ($i < $length - 1) {
                    $str = $str . ', ';
                }
            }
            return $str;
        }

        public function get_read_param($excludeArr, $pre) {
            $array = get_object_vars($this);
            if (isset($excludeArr)){
                for ($i=0; $i < count($excludeArr); $i++) {
                    unset($array[$excludeArr[$i]]);
                }
            }
            $array = array_keys($array);
            if (isset($pre)) {
                for ($i = 0; $i < count($array); $i++) {
                    $array[$i] = $pre . '.' . $array[$i];
                }
            }
            return implode(", ", $array);
        }

        public function get_query_param($excludeArr) {
            $str = '';
            $array = get_object_vars($this);
            if (isset($excludeArr)){
                for ($i=0; $i < count($excludeArr); $i++) {
                    unset($array[$excludeArr[$i]]);
                }
            }
            $array = array_keys($array);
            $length = count($array);
            for ($i = 0; $i < $length; $i++) {
                $str = $str . $array[$i] . '= :' . $array[$i];
                if ($i < $length - 1) {
                    $str = $str . ', ';
                }
            }
            return $str;
        }

        public function bind_param_exc($stmt, $excludeArr) {
            $array = get_object_vars($this);
            if (isset($excludeArr)){
                for ($i=0; $i < count($excludeArr); $i++) {
                    unset($array[$excludeArr[$i]]);
                }
            }
            foreach($array as $key => $value) {
                $stmt->bindValue(':' . $key, $value);
            }
            return $stmt;
        }

        public function bind_param($stmt, $array) {
            foreach($array as $key => $value) {
                $stmt->bindValue(':' . $key, $value);
            }
            return $stmt;
        }
    }
?>