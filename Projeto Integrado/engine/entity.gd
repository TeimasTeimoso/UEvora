extends KinematicBody2D 

const SPEED = 0

var knockdir = Vector2(0, 0)
var movedir = Vector2(0,0)
var spritedir = "down"

const TYPE = "Inimigo"
var hitstun = 0
var MAXHEALTH = 10
var health = MAXHEALTH

func movement_loop():
	var motion = movedir.normalized() * SPEED
	move_and_slide(motion, Vector2(0,0))
	
func spritedir_loop():
	match movedir:
		Vector2(-1,0):
			spritedir = "left"
		Vector2(1,0):
			spritedir = "right"
		Vector2(0, -1):
			spritedir = "up"
		Vector2(0, 1):
			spritedir = "down"
			
func anim_switch(animation):
	var newanim = str(animation,spritedir)
	if $anim.current_animation != newanim:
		$anim.play(newanim)
		
func damage_loop():
	if hitstun > 0:
		hitstun -= 1
	if TYPE == "Inimigo" && health <=0:
		queue_free()
	for area in $hitbox.get_overlapping_areas(): #Não pode ser body pois Node é Area
		var body = area.get_parent()
		if hitstun == 0 and body.get("DAMAGE") != null and body.get("TYPE") != TYPE:
			health -= body.get(("DAMAGE"))
			hitstun = 5
			knockdir = transform.origin - body.transform.origin

func use_item(item):
	var newitem = item.instance()
	newitem.add_to_group(str(newitem.get_name(), self))
	add_child(newitem)
	#Caso exista mais que um item vai eliminar os restantes
	if get_tree().get_nodes_in_group(str(newitem.get_name(), self)).size() > newitem.max_amount:
		newitem.queue_free()
			