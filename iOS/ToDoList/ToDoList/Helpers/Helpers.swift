//
//  Helpers.swift
//  ToDoList
//
//  Created by Andrea Garcìa on 17/12/22.
//

import Foundation

func validateText(text : String) -> Bool {
    if (text.trimmingCharacters(in: .whitespaces).isEmpty) {
        return false
    }
    else{
        return true
    }
}
