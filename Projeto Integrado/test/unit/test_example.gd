extends 'res://addons/gut/test.gd'

class TestFeatureA:
	extends 'res://addons/gut/test.gd'

	var Obj = load('res://scripts/Player.gd')
	var _obj = null

	func before_each():
		_obj = Obj.new()

	func test_something():
		assert_true(_obj.movement_loop(), 'Should be cool.')

class TestFeatureB:
	extends 'res://addons/gut/test.gd'

	var Obj = load('res://scripts/Enemy1.gd')
	var _obj = null

	func before_each():
		_obj = Obj.new()

	func test_foobar():
		assert_eq(_obj._physics_process(delta), 'bar', 'Foo should return bar')