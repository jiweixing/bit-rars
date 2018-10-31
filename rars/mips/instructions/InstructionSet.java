package rars.mips.instructions;

import rars.simulator.*;
import rars.mips.hardware.*;
import rars.mips.instructions.syscalls.*;
import rars.*;

import java.util.*;
import java.io.*;
	
	/*
Copyright (c) 2003-2013,  Pete Sanderson and Kenneth Vollmar

Developed by Pete Sanderson (psanderson@otterbein.edu)
and Kenneth Vollmar (kenvollmar@missouristate.edu)

Permission is hereby granted, free of charge, to any person obtaining 
a copy of this software and associated documentation files (the 
"Software"), to deal in the Software without restriction, including 
without limitation the rights to use, copy, modify, merge, publish, 
distribute, sublicense, and/or sell copies of the Software, and to 
permit persons to whom the Software is furnished to do so, subject 
to the following conditions:

The above copyright notice and this permission notice shall be 
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION 
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

(MIT license, http://www.opensource.org/licenses/mit-license.html)
 */

/**
 * The list of Instruction objects, each of which represents a MIPS instruction.
 * The instruction may either be basic (translates into binary machine code) or
 * extended (translates into sequence of one or more basic instructions).
 *
 * @author Pete Sanderson and Ken Vollmar
 * @version August 2003-5
 */

public class InstructionSet {
    private ArrayList instructionList;
    private ArrayList opcodeMatchMaps;
    private SyscallLoader syscallLoader;

    /**
     * Creates a new InstructionSet object.
     */
    public InstructionSet() {
        instructionList = new ArrayList();

    }

    /**
     * Retrieve the current instruction set.
     */
    public ArrayList getInstructionList() {
        return instructionList;

    }

    /**
     * Adds all instructions to the set.  A given extended instruction may have
     * more than one Instruction object, depending on how many formats it can have.
     *
     * @see Instruction
     * @see BasicInstruction
     * @see ExtendedInstruction
     */
    public void populate() {
        /* Here is where the parade begins.  Every instruction is added to the set here.*/

        // ////////////////////////////////////   BASIC INSTRUCTIONS START HERE ////////////////////////////////
        instructionList.add(
                new BasicInstruction("addi t1,t2,-112",
                        "Addition immediate WITHOUT overflow : set t1 to (t2 plus signed 12-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 000 fffff 0010011",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = operands[2] << 20 >> 20;
                                int sum = add1 + add2;
                                RegisterFile.updateRegister(operands[0], sum);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("slti t1,t2,-112",
                        "Set less than immediate : If t2 is less than sign-extended 12-bit immediate, then set t1 to 1 else set t1 to 0",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 010 fffff 0010011",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                // 12 bit immediate value in operands[2] is sign-extended
                                RegisterFile.updateRegister(operands[0],
                                        (RegisterFile.getValue(operands[1])
                                                < (operands[2] << 20 >> 20))
                                                ? 1
                                                : 0);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("sltiu t1,t2,-112",
                        "Set less than immediate unsigned : If t2 is less than  sign-extended 12-bit immediate using unsigned comparison, then set t1 to 1 else set t1 to 0",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 011 fffff 0010011",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int first = RegisterFile.getValue(operands[1]);
                                // 12 bit immediate value in operands[2] is sign-extended
                                int second = operands[2] << 20 >> 20;
                                if (first >= 0 && second >= 0 || first < 0 && second < 0) {
                                    RegisterFile.updateRegister(operands[0],
                                            (first < second) ? 1 : 0);
                                } else {
                                    RegisterFile.updateRegister(operands[0],
                                            (first >= 0) ? 1 : 0);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("andi t1,t2,112",
                        "Bitwise AND immediate : Set t1 to bitwise AND of t2 and sign-extended 12-bit immediate",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 111 fffff 0010011",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                // 12 bit immediate value in operands[2] is sign-extended
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1])
                                                & (operands[2] << 20 >> 20));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("ori t1,t2,112",
                        "Bitwise OR immediate : Set t1 to bitwise OR of t2 and sign-extended 12-bit immediate",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 110 fffff 0010011",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                // 12 bit immediate value in operands[2] is sign-extended
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1])
                                                | (operands[2] << 20 >> 20));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("xori t1,t2,112",
                        "Bitwise XOR immediate : Set t1 to bitwise XOR of t2 and sign-extended 12-bit immediate",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 100 fffff 0010011",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                // 12 bit immediate value in operands[2] is sign-extended
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1])
                                                ^ (operands[2] << 20 >> 20));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("slli t1,t2,112",
                        "Shift left logical immediate : Set t1 to result of shifting t2 left by number of bits specified by value in low-order 5 bits of 12-bit immediate",
                        BasicInstructionFormat.I_FORMAT,
                        "0000000 ttttt sssss 001 fffff 0010011",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                // only low-order 5 bits of 12 bit immediate value is needed
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1])
                                                << (operands[2] & 0x0000001F));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("srli t1,t2,112",
                        "Shift right logical immediate : Set t1 to result of shifting t2 right by number of bits specified by value in low-order 5 bits of 12-bit immediate",
                        BasicInstructionFormat.I_FORMAT,
                        "0000000 ttttt sssss 101 fffff 0010011",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                //only low-order 5 bits of 12 bit immediate value is needed
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1])
                                                >>> (operands[2] & 0x0000001F));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("srai t1,t2,112",
                        "Shift right arithmetic immediate : Set t1 to result of shifting t2 right arithmetically by number of bits specified by value in low-order 5 bits of 12-bit immediate",
                        BasicInstructionFormat.I_FORMAT,
                        "0100000 ttttt sssss 101 fffff 00100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                // only low-order 5 bits of 12 bit immediate value is needed
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1])
                                                >> (operands[2] & 0x0000001F));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("lui t1,100020",
                        "Load upper immediate : Set high-order 20 bits of t1 to 20-bit immediate and low-order 12 bits to 0",
                        BasicInstructionFormat.U_FORMAT,
                        "ssssssssssssssssssss fffff 01101 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0], operands[1] << 12);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("auipc t1,100020",
                        "Add upper immediate to pc : add 20-bit immediate to pc's high-order 20 bites and copy the value of pc to t1",
                        BasicInstructionFormat.U_FORMAT,
                        "ssssssssssssssssssss fffff 01101 00",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                // not sure if minus 4 is needed here
                                processJump(
                                        RegisterFile.getProgramCounter() + (operands[1] << 12));
                                RegisterFile.updateRegister(operands[0], RegisterFile.getProgramCounter());

                            }
                        }));
        instructionList.add(
                new BasicInstruction("add t1,t2,t3",
                        "Addition WITHOUT overflow : set t1 to (t2 plus t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "0000000 ttttt sssss 000 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = RegisterFile.getValue(operands[2]);
                                int sum = add1 + add2;
                                RegisterFile.updateRegister(operands[0], sum);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("slt t1,t2,t3",
                        "Set less than : If t2 is less than t3, then set t1 to 1 else set t1 to 0",
                        BasicInstructionFormat.R_FORMAT,
                        "0000000 ttttt sssss 010 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0],
                                        (RegisterFile.getValue(operands[1]) < RegisterFile.getValue(operands[2])) ? 1 : 0);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("sltu t1,t2,t3",
                        "Set less than unsigned : If t2 is less than t3 using unsigned comparision, then set t1 to 1 else set t1 to 0",
                        BasicInstructionFormat.R_FORMAT,
                        "0000000 ttttt sssss 010 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int first = RegisterFile.getValue(operands[1]);
                                int second = RegisterFile.getValue(operands[2]);
                                if (first >= 0 && second >= 0 || first < 0 && second < 0) {
                                    RegisterFile.updateRegister(operands[0],
                                            (first < second) ? 1 : 0);
                                } else {
                                    RegisterFile.updateRegister(operands[0],
                                            (first >= 0) ? 1 : 0);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("and t1,t2,t3",
                        "Bitwise AND : Set t1 to bitwise AND of t2 and t3",
                        BasicInstructionFormat.R_FORMAT,
                        "0000000 ttttt sssss 111 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1])
                                                & RegisterFile.getValue(operands[2]));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("or t1,t2,t3",
                        "Bitwise OR : Set t1 to bitwise OR of t2 and t3",
                        BasicInstructionFormat.R_FORMAT,
                        "0000000 ttttt sssss 110 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1])
                                                | RegisterFile.getValue(operands[2]));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("xor t1,t2,t3",
                        "Bitwise XOR (exclusive OR) : Set t1 to bitwise XOR of t2 and t3",
                        BasicInstructionFormat.R_FORMAT,
                        "0000000 ttttt sssss 100 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1])
                                                ^ RegisterFile.getValue(operands[2]));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("sll t1,t2,t3",
                        "Shift left logical : Set t1 to result of shifting t2 left by low-order 5 bits of t3",
                        BasicInstructionFormat.R_FORMAT,
                        "0000000 ttttt sssss 001 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1]) << (RegisterFile.getValue(operands[2]) & 0x0000001F));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("srl t1,t2,t3",
                        "Shift right logical : Set t1 to result of shifting t2 right by low-order 5 bits of t3",
                        BasicInstructionFormat.R_FORMAT,
                        "0000000 ttttt sssss 001 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1]) >>> (RegisterFile.getValue(operands[2]) & 0x0000001F));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("sra t1,t2,t3",
                        "Shift right arithmetic : Set $t1 to result of sign-extended shifting $t2 right by low-order 5 bits of t3",
                        BasicInstructionFormat.R_FORMAT,
                        "0100000 ttttt sssss 101 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0],
                                        RegisterFile.getValue(operands[1]) >> (RegisterFile.getValue(operands[2]) & 0x0000001F));
                            }
                        }));
        instructionList.add(
                new BasicInstruction("sub t1,t2,t3",
                        "Subtraction with overflow : set t1 to (t2 minus t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "0100000 ttttt sssss 000 fffff 01100 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int sub1 = RegisterFile.getValue(operands[1]);
                                int sub2 = RegisterFile.getValue(operands[2]);
                                int dif = sub1 - sub2;
                                RegisterFile.updateRegister(operands[0], dif);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("nop",
                        "Null operation : machine code is all zeroes",
                        BasicInstructionFormat.I_FORMAT,
                        "000000000000 00000 000 00000 0010011",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                // Hey I like this so far!
                            }
                        }));
        instructionList.add(
                new BasicInstruction("jal t1,-100",
                        "Jump and link : Set t1 to Program Counter (return address) then add the immediate to the pc ",
                        BasicInstructionFormat.UJ_FORMAT,
                        "ssssssssssssssssssss fffff 11011 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                //processReturnAddress(31);// RegisterFile.updateRegister(31, RegisterFile.getProgramCounter());
                                RegisterFile.updateRegister(operands[0], RegisterFile.getProgramCounter());
                                //seems that the simulator plus 4 to pc automatically, so -4 manually
                                processJump(
                                        (RegisterFile.getProgramCounter() + operands[1]) - 4);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("jal t1,label",
                        "Jump and link : Set t1 to Program Counter (return address) then jump to label ",
                        BasicInstructionFormat.UJ_FORMAT,
                        "ssssssssssssssssssss fffff 11011 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                //processReturnAddress(31);// RegisterFile.updateRegister(31, RegisterFile.getProgramCounter());
                                RegisterFile.updateRegister(operands[0], RegisterFile.getProgramCounter());
                                //seems that the simulator plus 4 to pc automatically, so -4 manually
                                processJump(
                                        (RegisterFile.getProgramCounter() + operands[1]) - 4);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("jalr t1,t2,-112",
                        "Jump and link register : Set t1 to Program Counter (return address) then add the sum of immediate and t2 to the pc",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 000 fffff 11001 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0], RegisterFile.getProgramCounter());
                                processJump(
                                        ((RegisterFile.getValue(operands[1]) + operands[2])) & 0xfffffffe);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("beq t1,t2,-112",
                        "Branch if equal : Branch(add immediate as offset to pc) if t1 and t2 are equal",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 000 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        == RegisterFile.getValue(operands[1])) {
                                    processJump(RegisterFile.getProgramCounter() + operands[2] - 4);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("bne t1,t2,-112",
                        "Branch if not equal : Branch(add immediate as offset to pc) if t1 and t2 are not equal",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 001 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        != RegisterFile.getValue(operands[1])) {
                                    processJump(RegisterFile.getProgramCounter() + operands[2] - 4);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("blt t1,t2,-112",
                        "Branch if less than : Branch(add immediate as offset to pc) if t1 is less than t2",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 100 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        < RegisterFile.getValue(operands[1])) {
                                    processJump(RegisterFile.getProgramCounter() + operands[2] - 4);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("bltu t1,t2,-112",
                        "Branch if less than unsigned : Branch(add immediate as offset to pc) if t1 is less than t2 using unsigned comparision",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 110 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int first = RegisterFile.getValue(operands[0]);
                                int second = RegisterFile.getValue(operands[1]);
                                if (first >= 0 && second >= 0 || first < 0 && second < 0) {
                                    if (first < second) {
                                        processJump(RegisterFile.getProgramCounter() + operands[2] - 4);
                                    }
                                } else {
                                    if (first >= 0) {
                                        processJump(RegisterFile.getProgramCounter() + operands[2] - 4);
                                    }
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("bge t1,t2,-112",
                        "Branch if greater or equal : Branch(add immediate as offset to pc) if t1 is greater than or equal to t2",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 101 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        >= RegisterFile.getValue(operands[1])) {
                                    processJump(RegisterFile.getProgramCounter() + operands[2] - 4);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("bgeu t1,t2,-112",
                        "Branch if greater or equal unsigned : Branch(add immediate as offset to pc) if t1 is greater than or equal to t2 using unsigned comparision",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 111 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int first = RegisterFile.getValue(operands[0]);
                                int second = RegisterFile.getValue(operands[1]);
                                if (first >= 0 && second >= 0 || first < 0 && second < 0) {
                                    if (first >= second) {
                                        processJump(RegisterFile.getProgramCounter() + operands[2] - 4);
                                    }
                                } else {
                                    if (first < 0) {
                                        processJump(RegisterFile.getProgramCounter() + operands[2] - 4);
                                    }
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("beq t1,t2,label",
                        "Branch if equal : Branch(jump to label) if t1 and t2 are equal",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 000 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        == RegisterFile.getValue(operands[1])) {
                                    processBranch(operands[2]);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("bne t1,t2,label",
                        "Branch if not equal : Branch(jump to label) if t1 and t2 are not equal",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 001 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        != RegisterFile.getValue(operands[1])) {
                                    processBranch(operands[2]);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("blt t1,t2,label",
                        "Branch if less than : Branch(jump to label) if t1 is less than t2",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 100 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        < RegisterFile.getValue(operands[1])) {
                                    processBranch(operands[2]);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("bltu t1,t2,label",
                        "Branch if less than unsigned : Branch(jump to label) if t1 is less than t2 using unsigned comparision",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 110 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int first = RegisterFile.getValue(operands[0]);
                                int second = RegisterFile.getValue(operands[1]);
                                if (first >= 0 && second >= 0 || first < 0 && second < 0) {
                                    if (first < second) {
                                        processBranch(operands[2]);
                                    }
                                } else {
                                    if (first >= 0) {
                                        processBranch(operands[2]);
                                    }
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("bge t1,t2,label",
                        "Branch if greater or equal : Branch(jump to label) if t1 is greater than or equal to t2",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 101 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        >= RegisterFile.getValue(operands[1])) {
                                    processBranch(operands[2]);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("bgeu t1,t2,label",
                        "Branch if greater or equal unsigned : Branch(jump to label) if t1 is greater than or equal to t2 using unsigned comparision",
                        BasicInstructionFormat.SB_FORMAT,
                        "tttttttttttt sssss 111 fffff 11000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int first = RegisterFile.getValue(operands[0]);
                                int second = RegisterFile.getValue(operands[1]);
                                if (first >= 0 && second >= 0 || first < 0 && second < 0) {
                                    if (first >= second) {
                                        processBranch(operands[2]);
                                    }
                                } else {
                                    if (first < 0) {
                                        processBranch(operands[2]);
                                    }
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("lb t1,t2,-112",
                        "Load byte : Set t1 to sign-extended 8-bit value from effective memory byte address obtained by adding t2 to the sign-extended 12-bit offset",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 000 fffff 00000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                try {
                                    RegisterFile.updateRegister(operands[0],
                                            Globals.memory.getByte(
                                                    RegisterFile.getValue(operands[1])
                                                            + (operands[2] << 16 >> 16))
                                                    << 24
                                                    >> 24);
                                } catch (AddressErrorException e) {
                                    throw new ProcessingException(statement, e);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("lbu t1,t2,-112",
                        "Load byte unsigned : Set t1 to zero-extended 8-bit value from effective memory byte address obtained by adding t2 to the sign-extended 12-bit offset",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 100 fffff 00000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                try {
                                    RegisterFile.updateRegister(operands[0],
                                            Globals.memory.getByte(
                                                    RegisterFile.getValue(operands[1])
                                                            + (operands[2] << 16 >> 16))
                                                    & 0x000000ff);
                                } catch (AddressErrorException e) {
                                    throw new ProcessingException(statement, e);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("lh t1,t2,-112",
                        "Load halfword : Set t1 to sign-extended 16-bit value from effective memory halfword address obtained by adding t2 to the sign-extended 12-bit offset",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 001 fffff 00000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                try {
                                    RegisterFile.updateRegister(operands[0],
                                            Globals.memory.getHalf(
                                                    RegisterFile.getValue(operands[1])
                                                            + (operands[2] << 16 >> 16))
                                                    << 16
                                                    >> 16);
                                } catch (AddressErrorException e) {
                                    throw new ProcessingException(statement, e);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("lhu t1,t2,-112",
                        "Load halfword unsigned : Set t1 to zero-extended 16-bit value from effective memory halfword address obtained by adding t2 to the sign-extended 12-bit offset",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 101 fffff 00000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                try {
                                    // offset is sign-extended and loaded halfword value is zero-extended
                                    RegisterFile.updateRegister(operands[0],
                                            Globals.memory.getHalf(
                                                    RegisterFile.getValue(operands[1])
                                                            + (operands[2] << 16 >> 16))
                                                    & 0x0000ffff);
                                } catch (AddressErrorException e) {
                                    throw new ProcessingException(statement, e);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("lw t1,t2,-112",
                        "Load word : Set t1 to contents of effective memory word address obtained by adding t2 to the sign-extended 12-bit offset",
                        BasicInstructionFormat.I_FORMAT,
                        "tttttttttttt sssss 010 fffff 00000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                try {
                                    RegisterFile.updateRegister(operands[0],
                                            Globals.memory.getWord(
                                                    RegisterFile.getValue(operands[1]) + operands[2]));
                                } catch (AddressErrorException e) {
                                    throw new ProcessingException(statement, e);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("sb t1,t2,-112",
                        "Store byte : Store the low-order 8 bits of t1 into the effective memory byte address obtained by adding t2 to the sign-extended 12-bit offset",
                        BasicInstructionFormat.S_FORMAT,
                        "ttttttt sssss fffff 000 ttttt 01000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                try {
                                    Globals.memory.setByte(
                                            RegisterFile.getValue(operands[1])
                                                    + (operands[2] << 16 >> 16),
                                            RegisterFile.getValue(operands[0])
                                                    & 0x000000ff);
                                } catch (AddressErrorException e) {
                                    throw new ProcessingException(statement, e);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("sh t1,t2,-112",
                        "Store halfword : Store the low-order 16 bits of t1 into the effective memory halfword address obtained by adding t2 to the sign-extended 12-bit offset",
                        BasicInstructionFormat.S_FORMAT,
                        "ttttttt sssss fffff 001 ttttt 01000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                try {
                                    Globals.memory.setHalf(
                                            RegisterFile.getValue(operands[1])
                                                    + (operands[2] << 16 >> 16),
                                            RegisterFile.getValue(operands[0])
                                                    & 0x0000ffff);
                                } catch (AddressErrorException e) {
                                    throw new ProcessingException(statement, e);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("sw t1,t2,-112",
                        "Store word : Store contents of t1 into effective memory word address obtained by adding t2 to the sign-extended 12-bit offset",
                        BasicInstructionFormat.S_FORMAT,
                        "ttttttt sssss fffff 010 ttttt 01000 11",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                try {
                                    Globals.memory.setWord(
                                            RegisterFile.getValue(operands[1]) + operands[2],
                                            RegisterFile.getValue(operands[0]));
                                } catch (AddressErrorException e) {
                                    throw new ProcessingException(statement, e);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("syscall",
                        "Issue a system call : Execute the system call specified by value in $v0",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 00000 00000 00000 00000 001100",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                findAndSimulateSyscall(RegisterFile.getValue(2), statement);
                            }
                        }));
        //PC aligned to a four-byte problem is unsolved


        ////////////// READ PSEUDO-INSTRUCTION SPECS FROM DATA FILE AND ADD //////////////////////
        addPseudoInstructions();

        ////////////// GET AND CREATE LIST OF SYSCALL FUNCTION OBJECTS ////////////////////
        syscallLoader = new SyscallLoader();
        syscallLoader.loadSyscalls();

        // Initialization step.  Create token list for each instruction example.  This is
        // used by parser to determine user program correct syntax.
        for (int i = 0; i < instructionList.size(); i++) {
            Instruction inst = (Instruction) instructionList.get(i);
            inst.createExampleTokenList();
        }

        HashMap maskMap = new HashMap();
        ArrayList matchMaps = new ArrayList();
        for (int i = 0; i < instructionList.size(); i++) {
            Object rawInstr = instructionList.get(i);
            if (rawInstr instanceof BasicInstruction) {
                BasicInstruction basic = (BasicInstruction) rawInstr;
                Integer mask = Integer.valueOf(basic.getOpcodeMask());
                Integer match = Integer.valueOf(basic.getOpcodeMatch());
                HashMap matchMap = (HashMap) maskMap.get(mask);
                if (matchMap == null) {
                    matchMap = new HashMap();
                    maskMap.put(mask, matchMap);
                    matchMaps.add(new MatchMap(mask, matchMap));
                }
                matchMap.put(match, basic);
            }
        }
        Collections.sort(matchMaps);
        this.opcodeMatchMaps = matchMaps;
    }

    public BasicInstruction findByBinaryCode(int binaryInstr) {
        ArrayList matchMaps = this.opcodeMatchMaps;
        for (int i = 0; i < matchMaps.size(); i++) {
            MatchMap map = (MatchMap) matchMaps.get(i);
            BasicInstruction ret = map.find(binaryInstr);
            if (ret != null) return ret;
        }
        return null;
    }

    /*  METHOD TO ADD PSEUDO-INSTRUCTIONS
     */

    private void addPseudoInstructions() {
        InputStream is = null;
        BufferedReader in = null;
        try {
            // leading "/" prevents package name being prepended to filepath.
            is = this.getClass().getResourceAsStream("/PseudoOps.txt");
            in = new BufferedReader(new InputStreamReader(is));
        } catch (NullPointerException e) {
            System.out.println(
                    "Error: MIPS pseudo-instruction file PseudoOps.txt not found.");
            System.exit(0);
        }
        try {
            String line, pseudoOp, template, firstTemplate, token;
            String description;
            StringTokenizer tokenizer;
            while ((line = in.readLine()) != null) {
                // skip over: comment lines, empty lines, lines starting with blank.
                if (!line.startsWith("#") && !line.startsWith(" ")
                        && line.length() > 0) {
                    description = "";
                    tokenizer = new StringTokenizer(line, "\t");
                    pseudoOp = tokenizer.nextToken();
                    template = "";
                    firstTemplate = null;
                    while (tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken();
                        if (token.startsWith("#")) {
                            // Optional description must be last token in the line.
                            description = token.substring(1);
                            break;
                        }
                        if (token.startsWith("COMPACT")) {
                            // has second template for Compact (16-bit) memory config -- added DPS 3 Aug 2009
                            firstTemplate = template;
                            template = "";
                            continue;
                        }
                        template = template + token;
                        if (tokenizer.hasMoreTokens()) {
                            template = template + "\n";
                        }
                    }
                    ExtendedInstruction inst = (firstTemplate == null)
                            ? new ExtendedInstruction(pseudoOp, template, description)
                            : new ExtendedInstruction(pseudoOp, firstTemplate, template, description);
                    instructionList.add(inst);
                    //if (firstTemplate != null) System.out.println("\npseudoOp: "+pseudoOp+"\ndefault template:\n"+firstTemplate+"\ncompact template:\n"+template);
                }
            }
            in.close();
        } catch (IOException ioe) {
            System.out.println(
                    "Internal Error: MIPS pseudo-instructions could not be loaded.");
            System.exit(0);
        } catch (Exception ioe) {
            System.out.println(
                    "Error: Invalid MIPS pseudo-instruction specification.");
            System.exit(0);
        }

    }

    /**
     * Given an operator mnemonic, will return the corresponding Instruction object(s)
     * from the instruction set.  Uses straight linear search technique.
     *
     * @param name operator mnemonic (e.g. addi, sw,...)
     * @return list of corresponding Instruction object(s), or null if not found.
     */
    public ArrayList matchOperator(String name) {
        ArrayList matchingInstructions = null;
        // Linear search for now....
        for (int i = 0; i < instructionList.size(); i++) {
            if (((Instruction) instructionList.get(i)).getName().equalsIgnoreCase(name)) {
                if (matchingInstructions == null)
                    matchingInstructions = new ArrayList();
                matchingInstructions.add(instructionList.get(i));
            }
        }
        return matchingInstructions;
    }


    /**
     * Given a string, will return the Instruction object(s) from the instruction
     * set whose operator mnemonic prefix matches it.  Case-insensitive.  For example
     * "s" will match "sw", "sh", "sb", etc.  Uses straight linear search technique.
     *
     * @param name a string
     * @return list of matching Instruction object(s), or null if none match.
     */
    public ArrayList prefixMatchOperator(String name) {
        ArrayList matchingInstructions = null;
        // Linear search for now....
        if (name != null) {
            for (int i = 0; i < instructionList.size(); i++) {
                if (((Instruction) instructionList.get(i)).getName().toLowerCase().startsWith(name.toLowerCase())) {
                    if (matchingInstructions == null)
                        matchingInstructions = new ArrayList();
                    matchingInstructions.add(instructionList.get(i));
                }
            }
        }
        return matchingInstructions;
    }

    /*
     * Method to find and invoke a syscall given its service number.  Each syscall
     * function is represented by an object in an array list.  Each object is of
     * a class that implements Syscall or extends AbstractSyscall.
     */

    private void findAndSimulateSyscall(int number, ProgramStatement statement)
            throws ProcessingException {
        Syscall service = syscallLoader.findSyscall(number);
        if (service != null) {
            service.simulate(statement);
            return;
        }
        throw new ProcessingException(statement,
                "invalid or unimplemented syscall service: " +
                        number + " ", Exceptions.SYSCALL_EXCEPTION);
    }

    /*
     * Method to process a successful branch condition.  DO NOT USE WITH JUMP
     * INSTRUCTIONS!  The branch operand is a relative displacement in words
     * whereas the jump operand is an absolute address in bytes.
     *
     * The parameter is displacement operand from instruction.
     *
     * Handles delayed branching if that setting is enabled.
     */
    // 4 January 2008 DPS:  The subtraction of 4 bytes (instruction length) after
    // the shift has been removed.  It is left in as commented-out code below.
    // This has the effect of always branching as if delayed branching is enabled,
    // even if it isn't.  This mod must work in conjunction with
    // ProgramStatement.java, buildBasicStatementFromBasicInstruction() method near
    // the bottom (currently line 194, heavily commented).

    private void processBranch(int displacement) {
        if (Globals.getSettings().getDelayedBranchingEnabled()) {
            // Register the branch target address (absolute byte address).
            DelayedBranch.register(RegisterFile.getProgramCounter() + (displacement) - 4);
        } else {
            // Decrement needed because PC has already been incremented
            RegisterFile.setProgramCounter(
                    RegisterFile.getProgramCounter()
                            + (displacement) - 4); // - Instruction.INSTRUCTION_LENGTH);
        }
    }

    /*
     * Method to process a jump.  DO NOT USE WITH BRANCH INSTRUCTIONS!
     * The branch operand is a relative displacement in words
     * whereas the jump operand is an absolute address in bytes.
     *
     * The parameter is jump target absolute byte address.
     *
     * Handles delayed branching if that setting is enabled.
     */

    private void processJump(int targetAddress) {
        if (Globals.getSettings().getDelayedBranchingEnabled()) {
            DelayedBranch.register(targetAddress);
        } else {
            RegisterFile.setProgramCounter(targetAddress);
        }
    }

    /*
     * Method to process storing of a return address in the given
     * register.  This is used only by the "and link"
     * instructions: jal, jalr, bltzal, bgezal.  If delayed branching
     * setting is off, the return address is the address of the
     * next instruction (e.g. the current PC value).  If on, the
     * return address is the instruction following that, to skip over
     * the delay slot.
     *
     * The parameter is register number to receive the return address.
     */

    private void processReturnAddress(int register) {
        RegisterFile.updateRegister(register, RegisterFile.getProgramCounter() +
                ((Globals.getSettings().getDelayedBranchingEnabled()) ?
                        Instruction.INSTRUCTION_LENGTH : 0));
    }

    private static class MatchMap implements Comparable {
        private int mask;
        private int maskLength; // number of 1 bits in mask
        private HashMap matchMap;

        public MatchMap(int mask, HashMap matchMap) {
            this.mask = mask;
            this.matchMap = matchMap;

            int k = 0;
            int n = mask;
            while (n != 0) {
                k++;
                n &= n - 1;
            }
            this.maskLength = k;
        }

        public boolean equals(Object o) {
            return o instanceof MatchMap && mask == ((MatchMap) o).mask;
        }

        public int compareTo(Object other) {
            MatchMap o = (MatchMap) other;
            int d = o.maskLength - this.maskLength;
            if (d == 0) d = this.mask - o.mask;
            return d;
        }

        public BasicInstruction find(int instr) {
            int match = Integer.valueOf(instr & mask);
            return (BasicInstruction) matchMap.get(match);
        }
    }
}

