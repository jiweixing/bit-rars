.section .data			# define in data section
fibs:	.word	0 : 12		# "array" of 12 words to contain fib values
size:	.word	12		# size of "array" 
.macro done			# here we define a macro to exit
	addi 	x2,zero,10	# x2 is the same as vsp. 
	syscall			# Note: the syscall is the same as MARS, not follow Risc-V standard for register-use
.endm
.text				# define in text section
	la	t0,fibs
	la	t5,size
	lw	t1,t5,0
	addi	t2,zero,1	# set t2 to 1
	sw	t2,t0,0		# F[0]=0
	sw	t2,t0,4		# F[1]=0
	addi	t1,t1,-3	# initial loop counter
loop:
	lw	t2,t0,0		# F[n]
	lw	t3,t0,4		# F[n+1]
	add	t2,t2,t3	# F[n+2]=F[n]+F[n+1]
	sw	t2,t0,8		# store F[n+2]
	addi	t1,t1,-1	# update loop counter
	addi	t0,t0,4		# update address of fibs
	bge	t1,zero,loop	# repeat if not end
	la	a0,fibs		# fisrt argument for print (address)
	lw	a1,t5,0		# second argument for print (size)
	jal	ra,print	# call print routine
	done
	lui	a0,0x32
#########  routine to print the numbers on one line. 

      .data
space:.asciz  " "		# space to insert between numbers
head: .asciz  "The Fibonacci numbers are:\n"
      .text
print:	add	t0,zero,a0	# starting address of array
	add	t1,zero,a1	# initialize loop counter to array size
	la	x4,head		# load address of print heading
	addi	x2,zero,4	# specify Print String service
	syscall			# print heading
out:	lw	x4,t0,0		# load fibonacci number for syscall
	addi	x2,zero,1	# specify Print Integer service
	syscall			# print fibonacci number
	la	x4,space	# load address of spacer for syscall
	addi	x2,,zero,4	# specify Print String service
	syscall			# output string
	addi	t0,t0,4		# increment address
	addi	t1,t1,-1	# decrement loop counter
	blt	zero,t1,out	# repeat if not finished
	jalr	zero,ra,0	# return
