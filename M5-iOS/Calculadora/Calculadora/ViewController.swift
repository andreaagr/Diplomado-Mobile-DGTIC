//
//  ViewController.swift
//  Calculadora
//
//  Created by Andrea Garcìa on 28/12/22.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var operationLabel: UILabel!
    @IBOutlet weak var resultLabel: UILabel!
    
    var expression = "" {
        didSet {
            updateExpression()
        }
    }
    
    var leftParenthesisCount = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    @IBAction func clearOperationPanel(_ sender: Any) {
        expression = ""
        resultLabel.text = ""
        leftParenthesisCount = 0
    }
    
    @IBAction func addParenthesis(_ sender: Any) {
        if (isLastElementANumber(lastElement: expression.last ?? "_") && leftParenthesisCount > 0)
                || ((expression.last ?? "_" == ")" || expression.last ?? "_" == "%") && leftParenthesisCount > 0) {
            expression += ")"
            leftParenthesisCount -= 1
        } else {
            if (expression.last ?? "_" == ")" || expression.last ?? "_" == "(" || expression.last ?? "_" == "%" || !operators.contains(expression.last ?? "_")) && !expression.isEmpty {
                expression += "x"
            }
            expression += "("
            leftParenthesisCount += 1
        }
    }
    
    @IBAction func removeLast(_ sender: Any) {
        if !expression.isEmpty {
            if expression.count == 1 {
                expression = ""
                resultLabel.text = ""
            } else {
                let elementRemoved = expression.removeLast()
                if elementRemoved == "(" || elementRemoved == ")" {
                    leftParenthesisCount -= 1
                }
            }
        }
    }
    
    @IBAction func addPercentage(_ sender: Any) {
        if canPlaceOperator() {
            expression += "%"
        }
    }
    
    @IBAction func addDivision(_ sender: Any) {
        if canPlaceOperator() {
            expression += "/"
        }
    }
    
    @IBAction func addMultiplication(_ sender: Any) {
        if canPlaceOperator() {
            expression += "x"
        }
    }
    
    @IBAction func addSubstraction(_ sender: Any) {
        if canPlaceOperator() {
            expression += "-"
        }
    }
    
    @IBAction func addAddition(_ sender: Any) {
        if canPlaceOperator() {
            expression += "+"
        }
    }
    
    @IBAction func addNumberSeven(_ sender: Any) {
        shouldAddMult()
        expression += "7"
    }
    
    @IBAction func addNumberEight(_ sender: Any) {
        shouldAddMult()
        expression += "8"
    }
    
    @IBAction func addNumberNine(_ sender: Any) {
        shouldAddMult()
        expression += "9"
    }
    
    @IBAction func addNumberFour(_ sender: Any) {
        shouldAddMult()
        expression += "4"
    }
    
    @IBAction func addNumberFive(_ sender: Any) {
        shouldAddMult()
        expression += "5"
    }
    
    @IBAction func addNumberSix(_ sender: Any) {
        shouldAddMult()
        expression += "6"
    }
    
    @IBAction func addNumberOne(_ sender: Any) {
        shouldAddMult()
        expression += "1"
    }
    
    @IBAction func addNumberTwo(_ sender: Any) {
        shouldAddMult()
        expression += "2"
    }
    
    @IBAction func addNumberThree(_ sender: Any) {
        shouldAddMult()
        expression += "3"
    }
    
    @IBAction func addNumberZero(_ sender: Any) {
        shouldAddMult()
        expression += "0"
    }
    
    @IBAction func addPoint(_ sender: Any) {
        if !isLastElementANumber(lastElement: expression.last ?? "_") {
            expression += "0"
        }
        expression += "."
    }
    
    @IBAction func calculateTotal(_ sender: Any) {
        do {
            let posfixExpression = try infixToPosfix(expression: expression)
            try evalPosfix(expression: posfixExpression, resultLabel: resultLabel)
        } catch {
            resultLabel.text = "Error"
        }
    }
    
    func updateExpression() {
        operationLabel.text = expression
    }
    
    func isLastElementANumber(lastElement: Character) -> Bool {
        let numbers: [Character] = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]
        return numbers.contains(lastElement)
    }

    func canPlaceOperator() -> Bool {
        let lastElement = expression.last ?? "_"
        return isLastElementANumber(lastElement: lastElement) || lastElement == ")" || lastElement == "%"
    }
    
    func shouldAddMult() {
        if expression.last ?? "_" == ")" {
            expression += "x"
        }
    }
}

