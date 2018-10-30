# Bubble_Sort
# NOTE: syscall donot follow the general rules for the usage of register. This issue will be solved in the next version
# Limits: data should be signed 32-bit number

# main
.text
	jal	ra, get_array
	jal	ra, BubbleSort
	jal	ra, print_array
	li	x2, 10
	syscall					# syscall to exit

# main end

.data
str0:	.asciz	"please input how many number to be sorted:"
str1:	.asciz	"please input each number:\n"
str2:	.asciz	"the data sorted are:"
	.align	2				# align address to store word
data:	.zero	1				# empty space to store data, use a label to get address

.text
# input section
get_array:
	addi	x2, zero, 4
	la	x4, str0
	syscall					# print str0
	addi	x2, zero, 5			
	syscall					# get the number of data to be sorted
	add	a0, x2, zero			# we use a0 to store argument
	add	t0, x2, zero			# loop counter
	addi	x2, zero, 4
	la	x4, str1
	syscall					# print str1
	la	t1, data
loop_0:	
	addi	x2, zero, 5
	syscall					# get data
	sw	x2, t1, 0			# store data
	addi	t0, t0, -1
	addi	t1, t1, 4			# update loop counter and address
	bne	t0, zero, loop_0
	
	ret					# return

# bubble sort section
BubbleSort:
	addi	t0, a0, -1			# initial loop_1_1 counter
	
loop_1_1:
	addi	t1, t0, 0
	la	t2, data			# initial loop_1_2 counter and data addr
	
loop_1_2:
	lw	t3, t2, 0
	lw	t4, t2, 4			# load x[n] and x[n+1] to be compared
	blt	t3, t4, switch_done		# jump over switch if x[n]<x[n+1]
	sw	t3, t2, 4
	sw	t4, t2, 0
switch_done:
	addi	t2, t2, 4
	addi	t1, t1, -1
	bne	t1, zero, loop_1_2		# loop_1_2
	addi	t0, t0, -1
	bne	t0, zero, loop_1_1		# lopp_1_1
	ret					# return

# output section
print_array:
	addi	x2, zero, 4
	la	x4, str2
	syscall					# print str2
	add	t0, a0,zero
	la	t2, data			# initial loop_2 counter and data addr
loop_2:
	addi	x2, zero, 1
	lw	x4, t2, 0
	syscall					# load number and print
	addi	x2, zero, 11
	addi	x4, zero, ' '
	syscall					# print a space 
	addi	t0, t0, -1
	addi	t2, t2, 4			# update loop_2	counter and addr
	bne	t0, zero, loop_2
	ret					# return
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
