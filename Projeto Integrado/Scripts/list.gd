extends Control

const ListItem = preload("ListItem.tscn")

var listIndex = 0

func addItem(value):
	var item = ListItem.instance()
	listIndex += 1
	item.get_node("number").text = str(listIndex)
	item.get_node("label").text = value
	item.rect_min_size = Vector2(500, 40)
	
	$Panel/ScrollContainer/list.add_child(item)

func _ready():
	for i in range(20):
		addItem("Cona")
