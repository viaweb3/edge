package main

import (
	"edge"
)

func main() {
	origin := "Leandre-ekPR9pfLln4WuNshIF6gEa8TX3D7iHCm"
	key := "QykzJbTiofFY1escM9gOjLEwI0NntZH7"
	encrypted := "JgnFh3APEX0Db6qjWutkMUZJZqThFOhkosCsejF2wtdx6QIK6AaK9OLlkVc9ktVbDVMyGoqwoEgHOrer8SA0Eg=="

	s := edge.Encrypt(origin, key)
	println(s)

	o := edge.Decrypt(s, key)
	println(o == origin)

	o1 := edge.Decrypt(encrypted, key)
	println(o1 == origin)
}
