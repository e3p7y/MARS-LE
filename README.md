# MARS-LE (CS 240 Final Project)
This repository is a fork from https://github.com/johnaedelman/MARS-LE, which allows users to create their own custom language in MARS simulator.

## MARS Math Functions Extension

This project creates a custom language for the MARS simulator. It provides more math instructions for enhancing the ability to perform mathematical calculations in MIPS
## How to Use

1. **Compile the Language** Open Terminal in your project folder and run:
```java -jar BuildCustomLang.jar MathFunctions.java```

2. **Run MARS** Launch the simulator: ```java -jar Mars.jar```

3. Click ```Tools > Language Switcher > Select Language > Math Functions Extension Package```

4. Import example files or start coding your own program!

## Available Instructions

- `li $t0, 100` — Load immediate
- `addi $t0, $t1, 5` — Add immediate value to register
- `print $t0` — Print result to console
- `inc $t0` — Increment (add 1 to register)
- `plus $t0, $t1, $t2` — Addition
- `minus $t0, $t1, $t2` — Subtraction
- `mul $t0, $t1, $t2` — Multiplication
- `div $t0, $t1, $t2` — Division
- `neg $t0, $t1` — Flip sign (negative)
- `abs $t0, $t1` — Absolute value
- `fact $t0, $t1` — Factorial $n!$
- `pow $t0, $t1, $t2` — Power $x^y$
- `sqr $t0, $t1` — Square ($x^2$)
- `sqrt $t0, $t1` — Square Root ($\sqrt{x}$).
- `hyp $t0, $t1, $t2` — Hypotenuse $\sqrt{a^2+b^2}$
- `gcd $t0, $t1, $t2` — Greatest Common Divisor
- `log $t0, $t1, $t2` — Logarithm
- `min $t0, $t1, $t2` — Minimum
- `max $t0, $t1, $t2` - Maximum
- `mean $t0, $t1, $t2` — Average of two numbers
- `sum $t0, $t1, $t2` — Summation
- `prod $t0, $t1, $t2` — Product

## Examples
### 1. Calculating Combinations (nCr)
Ex. 6C3 = 20
```
li   $t0, 3
li   $t1, 4
hyp  $t2, $t0, $t1
plus $t3, $t0, $t1
plus $t3, $t3, $t2
print $t3
```
### 2. Calculating Perimeter of a Triangle
Ex. For a triangle with a = 3, b = 4, perimeter = 12.
```
li $t0, 3
li $t1, 4
hyp $t2, $t0, $t1
plus $t3, $t0, $t1
plus $t3, $t3, $t2
print $t3
```
