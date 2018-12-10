README

# BIT-RARS : a RISC-V Simulator in Java 


## 1. Introduction
  BIT-RARS is a RISC-V simulator for teaching developed by Yueyan Zhao, Anmin Li and Weixing Ji at Beijing Institute of Technology based on MARS, a MIPS simulator in Java. <br>

## 2. Supported Instructions
  The basic instructions in RV32I are supported.<br>
  Directives and macro are supported.<br>
  A few pseudo-instructions are supportet. we noticed that it's not enough and will be enriched in the future version.<br>

## 3. Installing and Running
   Java JRE 1.6 or above is required. Download jar file in directory .\bin and Run it from console<br>
   java -jar Rars.jar <br>
  <br>
  ![1](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_1.jpg "Main UI")	<br>
  There are mainly four sections in the main UI. <br>
  The first and second section are menu and tool bar. <br>
  The third section is "Edit/Excute" section, in which you can edit your code or watch the simulation result. <br>
  The fourth section is a console to display the output and reports of assembling and simulation.<br>
  <br>
  Here is an exmaple to show you how to use the simulator:<br>
  Click **File→Open**, and follow the instructions in **section 4→(2)** to find the examples. Copy and paste the example in the edit page.<br>
  ![2](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_2.jpg "Open file")
  ![3](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_3.jpg "Select file")	<br>
  <br>
  Go to Run→Assemble to assemble the asm code. You will see the execute page, in which there are source code, basic format of each code, binary code, code address(section 1), the memory(section 2) of and registers(section 3).<br>
  ![4](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_4.jpg "Assemble file")	<br>
  ![5](https://github.com/jiweixing/bit-rars/raw/master/screen_shot/3_5.jpg "Assemble button in tool bar")	<br>
  Then all the work have been done. Just use run, step and other command in the tool bar(next to  the assemble button) to run the code and watch the outcomes.<br>
### About the source code：
  The main class is in Rars.java and other source codes are in help, images and rars, in case anyone want to compile or read the code
## 4. Examples
  (1. Open Rars.jar, find **Help→Rars→Examples**. Two example are presented there.<br>
  (2. Open file **asm_examples**. More examples will be found to implement Bubblesort and calculate fibonacci.<br>
## 5. Future Work
  More pseudo-instrucions to be added.<br>
  Syscall will be adjusted to follow the general use of register in Risc-V<br>
  <br>
  Please feel free to contact us(jwx@bit.edu.cn), if you have any questions about this project.<br>
h
