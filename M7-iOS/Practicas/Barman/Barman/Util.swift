//
//  Util.swift
//  Barman
//
//  Created by Andrea García Ruiz on 28/02/23.
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
