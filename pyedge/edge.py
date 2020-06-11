from Crypto.Cipher import AES
from Crypto import Random
import base64

# import Crypto.Cipher.AES

def encrypt(plain_text, key):
    bs = AES.block_size

    pad = lambda s: s + (bs - len(s) % bs) * chr(bs - len(s) % bs)

    iv = Random.new().read(bs)

    cipher = AES.new(key.encode("utf-8"), AES.MODE_CBC, iv)

    encrypted = cipher.encrypt(pad(plain_text).encode("utf-8"))

    return bytes.decode(base64.b64encode(iv + encrypted))


def decrypt(cipher_text, key):
    bs = AES.block_size

    unpad = lambda s: s[0:-s[-1]]

    cipher_text = base64.b64decode(cipher_text)

    iv = cipher_text[:bs]

    cipher = AES.new(key.encode("utf-8"), AES.MODE_CBC, iv)

    decrypted = unpad(cipher.decrypt(cipher_text[bs:]))

    return bytes.decode(decrypted)


if __name__ == '__main__':
    origin = 'Leandre-ekPR9pfLln4WuNshIF6gEa8TX3D7iHCm'
    key = 'QykzJbTiofFY1escM9gOjLEwI0NntZH7'
    encrypted = 'JgnFh3APEX0Db6qjWutkMUZJZqThFOhkosCsejF2wtdx6QIK6AaK9OLlkVc9ktVbDVMyGoqwoEgHOrer8SA0Eg=='

    s = encrypt(origin, key)

    print(s)

    o = decrypt(s, key)

    print(o == origin)

    o1 = decrypt(encrypted, key)

    print(o1 == origin)
