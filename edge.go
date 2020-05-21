package edge

import (
	"bytes"
	"crypto/aes"
	"crypto/cipher"
	"crypto/rand"
	"encoding/base64"
	"fmt"
	"io"
	"io/ioutil"
	"strings"
)

func Encrypt(plainText, key string) string {
	k := []byte(key)
	block, err := aes.NewCipher(k)
	if err != nil {
		panic(err)
	}

	origData := []byte(plainText)

	blockSize := block.BlockSize()

	iv := make([]byte, blockSize)

	if _, err := io.ReadFull(rand.Reader, iv); err != nil {
		panic(err)
	}

	origData = PKCS7Padding(origData, blockSize)

	blockMode := cipher.NewCBCEncrypter(block, iv)

	encrypted := make([]byte, len(origData))

	blockMode.CryptBlocks(encrypted, origData)

	arr := [][]byte{iv, encrypted}
	return base64.StdEncoding.EncodeToString(bytes.Join(arr, []byte{}))
}

func Decrypt(cipherText, key string) string {
	crypted, _ := base64.StdEncoding.DecodeString(cipherText)

	k := []byte(key)
	block, err := aes.NewCipher(k)
	if err != nil {
		panic(err)
	}

	blockSize := block.BlockSize()

	blockMode := cipher.NewCBCDecrypter(block, crypted[:blockSize])
	origData := make([]byte, len(crypted)-blockSize)

	blockMode.CryptBlocks(origData, crypted[blockSize:])
	origData = PKCS7UnPadding(origData)
	return string(origData)
}

func PKCS7Padding(ciphertext []byte, blockSize int) []byte {
	padding := blockSize - len(ciphertext)%blockSize
	padtext := bytes.Repeat([]byte{byte(padding)}, padding)
	return append(ciphertext, padtext...)
}

func PKCS7UnPadding(origData []byte) []byte {
	length := len(origData)
	unpadding := int(origData[length-1])
	return origData[:(length - unpadding)]
}

func GetEdgePass(dir string) string {
	passfile := fmt.Sprintf("%s/%s", dir, ".edgepass")

	bs, err := ioutil.ReadFile(passfile)
	if err != nil {
		return ""
	}
	return strings.TrimSpace(fmt.Sprintf("%s", bs))
}
