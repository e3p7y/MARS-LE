package mars.mips.instructions.customlangs;
import mars.simulator.*;
import mars.mips.hardware.*;
import mars.mips.instructions.*;
import mars.mips.instructions.syscalls.*;
import mars.*;
import mars.util.*;
import java.util.*;
import java.io.*;
import java.util.Random;

public class MathFunctions extends CustomAssembly {

    @Override
    public String getName() {
        return "Math Function Extension Package";
    }

    @Override
    public String getDescription() {
        return "A language extension for MIPS to perform enhanced mathematical functions.";
    }

    @Override
    protected void populate() {

        // 1. addi
        instructionList.add(
            new BasicInstruction("addi $t0, $t1, 100",
                "Adds a 16-bit signed immediate to register $rs and stores in $rt.",
                BasicInstructionFormat.I_FORMAT,
                "001000sssssfffffssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int imm = operands[2]; 
                        RegisterFile.updateRegister(operands[0], rs + imm);
                    }
                }));

        // 2. li
        instructionList.add(
            new BasicInstruction("li $t0, 100",
                "Loads a 16-bit immediate value into register $rt.",
                BasicInstructionFormat.I_FORMAT,
                "00100100000fffffssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        RegisterFile.updateRegister(operands[0], operands[1]);
                    }
                }));

        // 3. print
        instructionList.add(
            new BasicInstruction("print $t0",
                "Prints the integer value of register $rs to the console.",
                BasicInstructionFormat.R_FORMAT,
                "011111fffff00000000000000111111",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int val = RegisterFile.getValue(operands[0]);
                        SystemIO.printString(Integer.toString(val));
                        SystemIO.printString("\n");
                    }
                }));

        // 4. Plus
        instructionList.add(
            new BasicInstruction("plus $t0, $t1, $t2",
                "Adds the values in register $rs and $rt, then stores the result in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "000000ssssstttttfffff00000100000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int rt = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], rs + rt);
                    }
                }));

        // 5. Minus
        instructionList.add(
            new BasicInstruction("minus $t0, $t1, $t2",
                "Subtracts the values in register $rs and $rt, then stores the result in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "000000ssssstttttfffff00000100010",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int rt = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], rs - rt);
                    }
                }));

        // 6. Minimum
        instructionList.add(
            new BasicInstruction("min $t0, $t1, $t2",
                "Compares the values in $rs and $rt, then stores the smaller value in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "000000ssssstttttfffff00000000001",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int rt = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], Math.min(rs, rt));
                    }
                }));

        // 7. Maximum
        instructionList.add(
            new BasicInstruction("max $t0, $t1, $t2",
                "Compares the values in $rs and $rt, then stores the larger value in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "000000ssssstttttfffff00000000010",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int rt = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], Math.max(rs, rt));
                    }
                }));

        // 8. Mean
        instructionList.add(
            new BasicInstruction("mean $t0, $t1, $t2",
                "Calculates the average of two numbers, then stores the result in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "000000ssssstttttfffff00000000011",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int rt = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], (rs + rt) / 2);
                    }
                }));

        // 9. Multiply
        instructionList.add(
            new BasicInstruction("mul $t0, $t1, $t2",
                "Multiplies $rs by $rt and stores the result in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "000000ssssstttttfffff00000011000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int rt = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], rs * rt);
                    }
                }));

        // 10. Divide
        instructionList.add(
            new BasicInstruction("div $t0, $t1, $t2",
                "Divides $rs by $rt and stores the integer quotient in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "000000ssssstttttfffff00000011010",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int rt = RegisterFile.getValue(operands[2]);
                        if (rt == 0) throw new ProcessingException(statement, "Division by zero", Exceptions.DIVIDE_BY_ZERO_EXCEPTION);
                        RegisterFile.updateRegister(operands[0], rs / rt);
                    }
                }));

        // 11. Negative
        instructionList.add(
            new BasicInstruction("neg $t0, $t1",
                "Flips the sign of the value in $rs, then stores the result in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "000000sssss00000fffff00000000100",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        RegisterFile.updateRegister(operands[0], -rs);
                    }
                }));

        // 12. Increment
        instructionList.add(
            new BasicInstruction("inc $t0",
                "Increments the value in the destination register $rd by 1.",
                BasicInstructionFormat.R_FORMAT,
                "0000000000000000fffff00000000101",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rdVal = RegisterFile.getValue(operands[0]);
                        RegisterFile.updateRegister(operands[0], rdVal + 1);
                    }
                }));

        // 13. Square
        instructionList.add(
            new BasicInstruction("sqr $t0, $t1",
                "Squares the value in $rs, then stores the result in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "011111sssss00000fffff00000000001",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        RegisterFile.updateRegister(operands[0], rs * rs);
                    }
                }));

        // 14. Square Root
        instructionList.add(
            new BasicInstruction("sqrt $t0, $t1",
                "Square root the value in $rs, then stores the result in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "011111sssss00000fffff00000000010",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        RegisterFile.updateRegister(operands[0], (int) Math.sqrt(rs));
                    }
                }));

        // 15. Power
        instructionList.add(
            new BasicInstruction("pow $t0, $t1, $t2",
                "Calculate the result of $rs^$rt, then stores the result in $rd.",
                BasicInstructionFormat.R_FORMAT,
                "011111ssssstttttfffff00000000011",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int rt = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], (int) Math.pow(rs, rt));
                    }
                }));

        // 16. Summation
        instructionList.add(
            new BasicInstruction("sum $t0, $t1, $t2",
                "Calculates the summation of all integers from $rs to $rt.",
                BasicInstructionFormat.R_FORMAT,
                "011111ssssstttttfffff00000000100",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int start = RegisterFile.getValue(operands[1]);
                        int end = RegisterFile.getValue(operands[2]);
                        int sum = 0;
                        if(start <= end) for (int i = start; i <= end; i++) sum += i;
                        else for (int i = start; i >= end; i--) sum += i;
                        RegisterFile.updateRegister(operands[0], sum);
                    }
                }));

        // 17. Product
        instructionList.add(
            new BasicInstruction("prod $t0, $t1, $t2",
                "Calculates the product of the sequence of integers from $rs to $rt.",
                BasicInstructionFormat.R_FORMAT,
                "011111ssssstttttfffff00000000101",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int start = RegisterFile.getValue(operands[1]);
                        int end = RegisterFile.getValue(operands[2]);
                        int prod = 1;
                        if(start <= end) for (int i = start; i <= end; i++) prod *= i;
                        else for (int i = start; i >= end; i--) prod *= i;
                        RegisterFile.updateRegister(operands[0], prod);
                    }
                }));

        // 18. Factorial
        instructionList.add(
            new BasicInstruction("fact $t0, $t1",
                "Calculates the factorial of the value in $rs.",
                BasicInstructionFormat.R_FORMAT,
                "011111sssss00000fffff00000000110",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int val = RegisterFile.getValue(operands[1]);
                        int result = 1;
                        for (int i = 1; i <= val; i++) result *= i;
                        RegisterFile.updateRegister(operands[0], result);
                    }
                }));

        // 19. Average
        instructionList.add(
            new BasicInstruction("avg $t0, $t1, $t2",
                "Calculates the average of the values provided.",
                BasicInstructionFormat.R_FORMAT,
                "011111ssssstttttfffff00000000111",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int rs = RegisterFile.getValue(operands[1]);
                        int rt = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], (rs + rt) / 2);
                    }
                }));

        // 20. Absolute
        instructionList.add(
            new BasicInstruction("abs $t0, $t1",
                "Computes the absolute value of $rs.",
                BasicInstructionFormat.R_FORMAT,
                "011111sssss00000fffff00000001000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        RegisterFile.updateRegister(operands[0], Math.abs(RegisterFile.getValue(operands[1])));
                    }
                }));

        // 21. Hypotenuse
        instructionList.add(
            new BasicInstruction("hyp $t0, $t1, $t2",
                "Calculates the length of the hypotenuse where $rs and $rt are sides.",
                BasicInstructionFormat.R_FORMAT,
                "011111ssssstttttfffff00000001001",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        double rs = (double) RegisterFile.getValue(operands[1]);
                        double rt = (double) RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], (int) Math.sqrt((rs * rs) + (rt * rt)));
                    }
                }));

        // 22. Greatest Common Dividor (GCD)
        instructionList.add(
            new BasicInstruction("gcd $t0, $t1, $t2",
                "Calculates the Greatest Common Divisor of $rs and $rt.",
                BasicInstructionFormat.R_FORMAT,
                "011111ssssstttttfffff00000001010",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int n1 = Math.abs(RegisterFile.getValue(operands[1]));
                        int n2 = Math.abs(RegisterFile.getValue(operands[2]));
                        while (n1 != n2) { if(n1 > n2) n1 -= n2; else n2 -= n1; }
                        RegisterFile.updateRegister(operands[0], n2);
                    }
                }));

        // 23. Logarithm
        instructionList.add(
            new BasicInstruction("log $t0, $t1, $t2",
                "Calculates the logarithm of $rs using $rt as the base.",
                BasicInstructionFormat.R_FORMAT,
                "011111ssssstttttfffff00000001011",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        double value = (double) RegisterFile.getValue(operands[1]);
                        double base = (double) RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], (int) (Math.log(value) / Math.log(base)));
                    }
                }));
    }
}