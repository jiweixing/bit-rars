.macro print_int (%x)
	addi	x2,zero,1
	add	x4,zero, %x					#pseudo if %x is a immediate
	syscall
	.endm
	
.macro print_str (%str)
.data
myLabel: .asciz %str
.text
	addi	x2,zero,4
	la	x4, myLabel
	syscall
	.endm

.macro for (%regIterator, %from, %to, %bodyMacroName)
	addi	%regIterator, zero, %from
	addi	t0, zero, %to
	Loop:
	%bodyMacroName ()
	addi	%regIterator, %regIterator, 1
	bge	t0, %regIterator, Loop
.endm

	print_str ("Follow is number ")				#"Follow is number " will be labeled with name "myLabel_M0"
	print_str ("from 1 to 10： ")				#"test2" will be labeled with name "myLabel_M1"
	#print an integer
	.macro body()
	print_int(t1)
	print_str("\n")
	.endm

	#printing 1 to 10:
	for	(t1, 1, 10, body)
