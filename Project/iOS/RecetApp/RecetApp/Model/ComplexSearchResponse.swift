//
//  ComplexSearchResponse.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 22/03/23.
//

import Foundation

// MARK: - ComplexSearchResponse
struct ComplexSearchResponse: Codable {
    let results: [BasicRecipeInfo]
}

// MARK: - Result
struct BasicRecipeInfo: Codable {
    let id: Int
    let title: String
    let image: String
}
