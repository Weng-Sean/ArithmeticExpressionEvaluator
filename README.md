# Arithmetic Expression Evaluator

This repository contains a Java application for evaluating arithmetic expressions. It supports the conversion of infix expressions to postfix notation and the evaluation of postfix expressions. The code is designed to handle a variety of arithmetic operations and can be a valuable tool for various applications involving mathematical expressions.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Examples](#examples)

## Features

- Converts infix arithmetic expressions to postfix notation.
- Evaluates postfix expressions to obtain the result.
- Supports basic arithmetic operations: addition, subtraction, multiplication, and division.
- Handles parentheses to control operator precedence.
- Provides a flexible and extensible framework for working with arithmetic expressions.

## Getting Started

### Prerequisites

Before you can use this application, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or higher

### Installation

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/your-username/ArithmeticExpressionEvaluator.git
    ```
2. Build the Java application:

   ```cd ArithmeticExpressionEvaluator
      javac *.java
    ```
### Usage
To use the Arithmetic Expression Evaluator, you can create an instance of the ArithmeticExpression class and then use the provided methods to convert and evaluate expressions programmatically.

```bash
ArithmeticExpression expression = new ArithmeticExpression("2 + 3 * (4 - 1)");
Converter converter = new ToPostfixConverter();
String postfixExpression = converter.convert(expression);
System.out.println("Postfix Expression: " + postfixExpression);

Evaluator evaluator = new PostfixEvaluator();
double result = evaluator.evaluate(postfixExpression);
System.out.println("Result: " + result);
```
## Examples
Here are some example expressions you can try with this evaluator:

```bash
2 + 3 * (4 - 1) (Infix)
5 2 * 3 + (Postfix)
```


   
