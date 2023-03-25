//
//  Recipe.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 22/03/23.
//

import Foundation

// MARK: - Welcome
struct Recipe: Codable {
    let id: Int
    let title: String
    let image: String
    let servings, readyInMinutes: Int
    let pricePerServing: Double
    let analyzedInstructions: [AnalyzedInstruction]
    let diets: [String]
    let instructions: String
    let dishTypes: [String]
    let extendedIngredients: [ExtendedIngredient]
    let summary: String
}

// MARK: - AnalyzedInstruction
struct AnalyzedInstruction: Codable {
    let name: String
    let steps: [Step]
}

// MARK: - Step
struct Step: Codable {
    let number: Int
    let step: String
    let ingredients, equipment: [StepTool]
}

// MARK: - StepEquipment
struct StepTool: Codable {
    let id: Int
    let name, image: String
}

// MARK: - ExtendedIngredient
struct ExtendedIngredient: Codable {
    let id: Int
    let image: String
    let name, original: String
}

enum Consitency: String, Codable {
    case liquid = "liquid"
    case solid = "solid"
}

// MARK: - Measures
struct Measures: Codable {
    let metric, us: Metric
}

// MARK: - Metric
struct Metric: Codable {
    let amount: Double
    let unitLong, unitShort: String
}
