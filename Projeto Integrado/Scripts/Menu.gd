extends TextureRect

var index = 0 #posiçao da linha

func _ready():
	pass

func _process(delta):
	set_process(true)
	set_process_input(true)

func _input(event):
	if event.is_action("ui_up") && event.is_pressed() && !event.is_echo():
		if(index != 0):
			index -= 1
			var x = get_node("Linha").get_position().x
			var y = get_node("Linha").get_position().y - 110
			get_node("Linha").set_position(Vector2(x,y))
	if event.is_action("ui_down") && event.is_pressed() && !event.is_echo():
		if(index != 1 ):
			index += 1
			var x = get_node("Linha").get_position().x
			var y = get_node("Linha").get_position().y + 110
			get_node("Linha").set_position(Vector2(x,y))
	if event.is_action("ui_accept") && event.is_pressed() && !event.is_echo():
		if (index == 0):
			get_tree().change_scene("res://Scenes/Mundo.tscn")
		if(index == 1):
			get_tree().quit()
			
			
