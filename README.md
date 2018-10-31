<H2>BIT-RARS : a RISC-V Simulator in Java </H2>


<H3>1. Introduction</H3>
<P>BIT-RARS is a RISC-V simulator for teaching developed by Yueyan Zhao, Anmin Li and Weixing Ji at Beijing Institute of Technology based on MARS, a MIPS simulator in Java. 

<H3>2. Supported Instructions</H3>
<P> The basic instructions in RV32I are mainly supported.
<P> Directives and macro are supported.
<P> A few pseudo-instructions are supportet. we noticed that it's not enough and will be added in the future version.
<H3>3. Installing and Running</H3>
<P> There is a .jar in bin, Java JRE required.
<P> The main class is in `Rars.java` and other sources are in `help`, `images` and `rars`, in case anyone want to compile or view the code
<H3>4. Examples</H3>
<P> (1. Open Rars.jar, find`Help→Rars→Examples`. Two example are added there.
<P> (2. Open file `asm_examples`. More examples will be found to implement Bubblesort and calculate fibonacci.
<H3>5. Future Work</H3>

<P>More pseudo-instrucions to be added.

<P>Syscall will be adjusted to follow the general use of register in Risc-V
  
<P>Please feel free to contact us(jwx@bit.edu.cn), if you have any questions about this project.
