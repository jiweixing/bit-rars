#Bubble_Sort
#NOTE: this code do NOT follow the general rules for the usage of register, just writen for a simple instruction check 
#only basic instruction is used.

#Input_section
#first number is the total number N to be sorted, stored in t6. NOTE that N must greater than 1
#then get N numbers by loop N times, store in ram with address of -4,-8,etc.
addi    x2,zero,5
syscall
add     t6,x2,zero
add     t0,x2,zero
add     t1,zero,zero
#where loop begins, t0 is used for temp loop counter, t1 is used for temp address
addi    x2,zero,5     #loop_1
syscall
addi    t1,t1,-4
sw      x2,t1,0
addi    t0,t0,-1
bne     t0,zero,-20   #loop_1



#Bubbble_sort
#s0 is counter for loop_1, s1 is counter for loop_2, s2 is the memory address, t0 and t1 is for temp store
#使用s0做第一层循环，使用s1做第二册循环,s2用做地址，t0,t1用做temp
add     s0,zero,t6
add     s1,zero,s0    #loop_1
addi    s2,zero,-4
lw      t0,s2,0       #loop_2
lw      t1,s2,-4
bge     t0,t1,12

#switch t0, t1 and store
sw      t0,s2,-4
sw      t1,s2,0

addi    s1,s1,-1
addi    s2,s2,-4
bne     s1,zero,-28   #loop_2
addi    s0,s0,-1
bne     s0,zero,-44   #loop_1


#Output_Section
add     t0,t6,zero
add     t1,zero,zero
#where loop begins, t0 is used for temp loop counter, t1 is used for temp address
addi    t1,t1,-4      #loop_1
addi    t0,t0,-1
lw      x4,t1,0
addi    x2,zero,1
syscall
beq     t0,zero,16
addi    x4,zero,','
addi    x2,zero,11
syscall
bne     t0,zero,-36   #loop_1





