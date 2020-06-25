extends "res://engine/entity.gd"

const SPEED = 40
var DAMAGE = 10


var movetimer_length = 30
var movetimer = 0


func _ready():
	$anim.play("default")
	movedir = dir.rand()


func _physics_process(delta):
	damage_loop()
	movement_loop()
	if movetimer > 0:
		movetimer -= 1			
	if movetimer == 0 || is_on_wall():
		movedir = dir.rand()
		movetimer = movetimer_length
	



#
#export var mov_speed = 240
#
#
#
#var RayCastFinal
#var RayCastUp
#var RayCastDown
#var RayCastLeft
#var RayCastRight
#
#var PlayerAnimNode
#var anim = ""
#var animNew = ""
#
#func _ready():
#	set_process(true)
#
#	RayCastFinal = get_node("RayCastDown")
#	RayCastUp = get_node("RayCastUp")
#	RayCastDown = get_node("RayCastDown")
#	RayCastLeft = get_node("RayCastLeft")
#	RayCastRight = get_node("RayCastRight")
#
#	PlayerAnimNode = get_node("AnimatedSprite")
#
#func _stopanim():
#	if (RayCastFinal == RayCastUp):
#		anim = "stand_up"	
#	if (RayCastFinal == RayCastDown):
#		anim = "stand_down"
#	if (RayCastFinal == RayCastLeft):
#		anim = "stand_left"
#	if (RayCastFinal == RayCastRight):
#		anim = "stand_right"
#
##delta = intervalo de tempo
#func _process(delta):
#	var motion = Vector2()
#
#	#para saber a direçao
#	if Input.is_action_pressed("ui_up"):
#		motion += Vector2(0, -1)
#		RayCastFinal = RayCastUp
#	if Input.is_action_pressed("ui_down"):
#		motion += Vector2(0, 1)
#		RayCastFinal = RayCastDown
#	if Input.is_action_pressed("ui_left"):
#		motion += Vector2(-1, 0)
#		RayCastFinal = RayCastLeft
#	if Input.is_action_pressed("ui_right"):
#		motion += Vector2(1,0)
#		RayCastFinal = RayCastRight
#
#	#se existir movimento
#	if (motion.length() > 0):
#		if (Input.is_action_pressed("ui_up")):
#			anim = "up"
#		if (Input.is_action_pressed("ui_down")):
#			anim = "down"
#		if (Input.is_action_pressed("ui_left")):
#			anim = "left"
#		if (Input.is_action_pressed("ui_right")):
#			anim = "right"
#	#caso contrario parado na direçao anterior
#	else:
#		if (RayCastFinal == RayCastUp):
#			anim = "stand_up"
#		if (RayCastFinal == RayCastDown):
#			anim = "stand_down"
#		if (RayCastFinal == RayCastLeft):
#			anim = "stand_left"
#		if (RayCastFinal == RayCastRight):
#			anim = "stand_right"
#
##############################################################################			
#	if (motion.length() > 0):
#		if (Input.is_action_pressed("attack") && RayCastFinal == RayCastUp):
#			anim = "attackup"
#		if (Input.is_action_pressed("attack") && RayCastFinal == RayCastDown):
#			anim = "attackdown"
#		if (Input.is_action_pressed("attack") && RayCastFinal == RayCastLeft):
#			anim = "attackleft"
#		if (Input.is_action_pressed("attack") && RayCastFinal == RayCastRight):
#			anim = "attackright"
#	#caso contrario parado na direçao anterior
#	else:
#		if (RayCastFinal == RayCastUp):
#			anim = "stand_up"
#			if (Input.is_action_pressed("attack")):
#				anim = "attackup"
#		if (RayCastFinal == RayCastDown):
#			anim = "stand_down"
#			if (Input.is_action_pressed("attack") ):
#				anim = "attackdown"
#		if (RayCastFinal == RayCastLeft):
#			anim = "stand_left"
#			if (Input.is_action_pressed("attack")):
#				anim = "attackleft"
#		if (RayCastFinal == RayCastRight):
#			anim = "stand_right"
#			if (Input.is_action_pressed("attack")):
#				anim = "attackright"
#####################################################################################
#
#
#	if anim != animNew:
#		animNew = anim
#		get_node("AnimatedSprite").play(anim)
#
#	motion = motion.normalized()*mov_speed*delta*2 #velocidade do movimento
#	move_and_collide(motion)
#
#