extends Area2D

var velocity = Vector2()

func _ready():
	set(true)
	pass

func _process(delta):
	translate(velocity * delta)
	
