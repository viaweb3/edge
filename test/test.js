const edge = require('../edge')

const origin = 'Leandre-ekPR9pfLln4WuNshIF6gEa8TX3D7iHCm';
const key = 'QykzJbTiofFY1escM9gOjLEwI0NntZH7';
const encrypted = 'JgnFh3APEX0Db6qjWutkMUZJZqThFOhkosCsejF2wtdx6QIK6AaK9OLlkVc9ktVbDVMyGoqwoEgHOrer8SA0Eg==';

const s = edge.Encrypt(origin, key)

console.log(s)

const o = edge.Decrypt(s, key)

console.log(origin === o)

const o1 = edge.Decrypt(encrypted, key)

console.log(origin === o1)