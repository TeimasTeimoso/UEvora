extends Area2D

onready var node = get_node("res://Scenes/inventory.tscn")

func _ready():
	connect("body_entered",self,"body_entered")

func body_entered(body):
	if body.name == "Player":
		queue_free()