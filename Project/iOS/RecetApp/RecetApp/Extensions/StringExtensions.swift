//
//  StringExtensions.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 25/03/23.
//

import Foundation

extension String {
    func htmlToString() -> String {
        return  try! NSAttributedString(data: self.data(using: .utf8)!,
                                        options: [.documentType: NSAttributedString.DocumentType.html],
                                        documentAttributes: nil).string
    }
}
