extends MarginContainer



func _on_Health_health_changed(health):
	emit_signal(health_changed(), health)
	pass # replace with function body
