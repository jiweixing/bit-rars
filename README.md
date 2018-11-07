README

# BIT-RARS : a RISC-V Simulator in Java 


## 1. Introduction
  BIT-RARS is a RISC-V simulator for teaching developed by Yueyan Zhao, Anmin Li and Weixing Ji at Beijing Institute of Technology based on MARS, a MIPS simulator in Java. <br>

## 2. Supported Instructions
  The basic instructions in RV32I are mainly supported.<br>
  Directives and macro are supported.<br>
  A few pseudo-instructions are supportet. we noticed that it's not enough and will be added in the future version.<br>
## 3. Installing and Running
  There is a .jar in bin, Java JRE required.<br>
  Now you are at the main UI of rars. <br>
  ![1](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_1.jpg "Main UI")	<br>
  As you can see, there are mainly four sections in the main UI. <br>
  The first and second section is menu and tool bar, will be used to control the software. <br>
  The third section is "Edit/Excute" section, in which you can edit your code or see the simulation result. <br>
  The fourth section is placed to display the output and reports of assembling and simulation.<br>
  The section on the right will be introduced in the lines follow.<br>
  <br>
  Before using it to write your own codes, load an example and have a look is recommeneded.<br>
  Click **File→Open**, and follow the directory in **section 4→(2)** to find the examples. You will file the example in the edit page.<br>
  Here we will show you the former way.<br>
  ![2](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_2.jpg "Open file")
  ![3](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_3.jpg "Select file")	<br>
  <br>
  After choosing the example you like, go to Run→Assemble to assemble the asm file. You will see the execute page, in which there are source code, basic format of each code, binary code, code address(section 1), the values memory(section 2) of and registers(section 3).<br>
  ![4](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_4.jpg "Assemble file")	<br>
  ![5](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_5.jpg "Assemble button in tool bar")	<br>
  Then all the work have been done. Just use run, step and other command in the tool bar(next to  the assemble button) to run the code and view the outcomes.<br>
###About the source code：
  The main class is in Rars.java and other source codes are in help, images and rars, in case anyone want to compile or read the code
## 4. Examples
  (1. Open Rars.jar, find **Help→Rars→Examples**. Two example are added there.<br>
  (2. Open file **asm_examples**. More examples will be found to implement Bubblesort and calculate fibonacci.<br>
## 5. Future Work
  More pseudo-instrucions to be added.<br>
  Syscall will be adjusted to follow the general use of register in Risc-V<br>
  <br>
  Please feel free to contact us(jwx@bit.edu.cn), if you have any questions about this project.<br>
