//
//  Util.swift
//  Calculadora
//
//  Created by Andrea Garcìa on 29/12/22.
//

import Foundation
import UIKit

enum ExpressionError: Error {
    case incorrectExpression(message: String)
    case divideByZero
}

// Initialize stack
var stack: [Character] = []

// Set operators hierarchy
let operators: [Character] = ["%", "/", "x", "-", "+"]

var numberWasLastAdded = true
var posfixExpression: [String] = []

func infixToPosfix(expression: String) throws -> String {
    // Iteerate infix expression
    for character in expression {
        // When the character is equals to '(' add it to the stack
        if character == "(" {
            stack.append(character)
            numberWasLastAdded = false
        // When the character is equals to ')' extract and display all the values in stack until you find '('
        } else if(character == ")") {
            do {
                try findLeftParenthesis()
            } catch {
                print(error)
                throw error
            }
            numberWasLastAdded = false
        // When the character is an operator ...
        } else if operators.contains(character) {
            // If the stack is empty or the operator has higher priority then the latest element in stack, insert the operator in the stack
            placeOperator(newOperator: character)
            numberWasLastAdded = false
        // When the character is an operand, display ir
        } else {
            if numberWasLastAdded && !posfixExpression.isEmpty {
                posfixExpression[posfixExpression.count - 1].append("\(character)")
            } else {
                posfixExpression.append("\(character)")
            }
            
            numberWasLastAdded = true
        }
    }
    
    // At the end of the expression we should display the remaining elements of the stack
    for element in stack.reversed() {
        if element != "(" {
            posfixExpression.append("\(element)")
        }
    }
    
    let finalExpr = posfixExpression.joined(separator: " ")
    posfixExpression.removeAll()
    stack.removeAll()
    return finalExpr
}

func placeOperator(newOperator: Character) {
    var lastElement: Character
    repeat {
        if stack.isEmpty {
            stack.append(newOperator)
            break
        } else {
            lastElement = stack.last!
            if operators.contains(lastElement) {
                if operatorHasHighestPriority(newOperator: newOperator, previousOperator: lastElement) {
                    stack.append(newOperator)
                    break
                } else {
                    if stack.count > 0 {
                        posfixExpression.append("\(stack.removeLast())")
                    }
                }
            } else {
                stack.append(newOperator)
                break
            }
        }
    } while stack.count >= 0
}

func operatorHasHighestPriority(newOperator: Character, previousOperator: Character) -> Bool {
    let indexNew: Int = operators.firstIndex(of: newOperator) ?? 0
    let indexPrev: Int = operators.firstIndex(of: previousOperator) ?? 0
    return indexNew < indexPrev
}

func findLeftParenthesis() throws {
    var element: Character?
    repeat {
        element = stack.popLast()
        if element != "(" && element != nil {
            posfixExpression.append("\(element!)")
        } else {
            return
        }
    } while stack.count > 0
    throw ExpressionError.incorrectExpression(message: "Expression malformed, cannot find '('")
}


var intEvalStack: [Int] = []
var doubleEvalStack: [Double] = []

func evalPosfix(expression: String, resultLabel: UILabel) throws {
    let hasDecimals = expression.contains(".") || expression.contains("%") || expression.contains("/")
    let posfixExpr = expression.components(separatedBy: " ")
    for value in posfixExpr {
        if operators.contains(value) {
            if value == "%" {
                let operand = doubleEvalStack.removeLast()
                doubleEvalStack.append(operand/100)
            } else {
                do {
                    if hasDecimals {
                        doubleEvalStack.append(try applyOperator(exprOperator: value, operand1: doubleEvalStack.removeLast(), operand2: doubleEvalStack.removeLast()))
                    } else {
                        intEvalStack.append(applyOperator(exprOperator: value, operand1: intEvalStack.removeLast(), operand2: intEvalStack.removeLast()))
                    }
                } catch {
                    print(error)
                    throw error
                }
            }
        } else {
            if value.contains(".") || hasDecimals {
                doubleEvalStack.append(Double(value)!)
            } else {
                intEvalStack.append(Int(value)!)
            }
        }
    }
    if hasDecimals {
        resultLabel.text = String(doubleEvalStack.removeLast())
    } else {
        resultLabel.text = String(intEvalStack.removeLast())
    }
}

func applyOperator(exprOperator: String, operand1: Double, operand2: Double) throws -> Double {
    switch exprOperator {
        case "+":
            return operand1 + operand2
        case "-":
            return operand2 - operand1
        case "/":
            if operand1 == 0 {
                throw ExpressionError.divideByZero
            } else {
                return operand2 / operand1
            }
        default:
            return operand1 * operand2
    }
}

func applyOperator(exprOperator: String, operand1: Int, operand2: Int) -> Int {
    switch exprOperator {
        case "+":
            return operand1 + operand2
        case "-":
            return operand2 - operand1
        default:
            return operand1 * operand2
    }
}
