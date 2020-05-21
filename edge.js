'use strict';

const crypto = require('crypto');

const IV_LENGTH = 16;

exports.Encrypt = (plainText, key, method = 'aes-256-cbc') => {
    let iv = crypto.randomBytes(IV_LENGTH);

    let cipher = crypto.createCipheriv(method, key, iv);

    let encrypted = cipher.update(plainText, "utf8", "binary");

    encrypted += cipher.final("binary");

    let final = Buffer.from(iv.toString("binary") + encrypted, "binary");

    return final.toString("base64");
}

exports.Decrypt = (cipherText, key, method = 'aes-256-cbc') => {
    let binaryData = Buffer.from(cipherText, "base64");

    let iv = binaryData.slice(0, 16)
    let text = binaryData.slice(16)

    let decipher = crypto.createDecipheriv(method, key, iv);

    let decrypted = decipher.update(text, 'binary', 'utf8');
    decrypted += decipher.final('utf8');

    return decrypted;
}