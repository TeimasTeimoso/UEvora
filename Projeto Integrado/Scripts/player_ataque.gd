extends Area2D

var vel = Vector2()
export var speed = 1000

func _ready():
	set_fixed_process(true)
	

#Função que define a posição inicial do ataque
func start_at(dir, pos):
	set_rot(dir)
	set_pos(pos)
	vel = Vector2(speed, 0).rotated(dir - PI/2)

func set_fixed_process(delta):
	print(position)
	position = position + vel * speed
	print(position)

func _on_Timer_timeout():
	queue_free()