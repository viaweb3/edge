<?php

namespace Edge;

use Exception;

class Edge
{
    public static function Encrypt($plainText, $key, $method = 'aes-256-cbc')
    {
        $length = openssl_cipher_iv_length($method);
        $iv     = openssl_random_pseudo_bytes($length, $isStrong);

        if (false === $iv && false === $isStrong) {
            throw new Exception('IV generate failed');
        }

        return base64_encode($iv . openssl_encrypt($plainText, $method, $key, OPENSSL_RAW_DATA, $iv));
    }

    public static function Decrypt($cipherText, $key, $method = 'aes-256-cbc')
    {
        $cipherText = base64_decode($cipherText);
        $length     = openssl_cipher_iv_length($method);
        return openssl_decrypt(substr($cipherText, $length), $method, $key, OPENSSL_RAW_DATA, substr($cipherText, 0, $length));
    }
}