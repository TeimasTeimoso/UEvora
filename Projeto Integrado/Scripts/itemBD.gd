extends Node
#criar uma base de dados dos items
const icon_path = "res://texture/"

const items = {
	"potion": {
		"icon": icon_path + "potion.png",
		"slot": "NONE"
	},
	"dagger": {
		"icon": icon_path + "dagger.png",
		"slot": "weaponSlot"
	},
	"swordBasic": {
		"icon": icon_path + "sword1.png",
		"slot": "weaponSlot"
	},
	"sword2": {
		"icon": icon_path + "sword2.png",
		"slot": "weaponSlot"
	},
	"sword3": {
		"icon": icon_path + "sword3.png",
		"slot": "weaponSlot"
	},
	"sword4": {
		"icon": icon_path + "sword4.png",
		"slot": "weaponSlot"
	},
	"sword5": {
		"icon": icon_path + "sword5.png",
		"slot": "weaponSlot"
	},
	"sword6": {
		"icon": icon_path + "potion.png",
		"slot": "weaponSlot"
	},
	"sword7": {
		"icon": icon_path + "potion.png",
		"slot": "weaponSlot"
	},
	"error": {
		"icon": icon_path + "error.png",
		"slot": "NONE"
	}
}

func get_item(item_id):
	if item_id in items:
		return items[item_id]
	else:
		return items["error"]
