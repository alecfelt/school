Object subclass: Complex [
	|real imag|
	real [ ^ real ]
	imag [ ^ imag ]
	Complex class >> new: r imag: i [
		|result|
		result := super new.
		result real: r.
		result imag: i.
		^ result
	[
	real: r [
		real := r.
	]
	imag: i [
		imag := i.
	]
]
