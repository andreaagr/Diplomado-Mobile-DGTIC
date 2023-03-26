//
//  UIImageExtension.swift
//  RecetApp
//
//  Created by Andrea García Ruiz on 24/03/23.
//

import Foundation
import UIKit

extension UIImageView {
    func loadFrom(URLAddress: String) {
        guard let url = URL(string: URLAddress) else {
            return
        }
        
        // TODO: Check internet connection
        let configuration = URLSessionConfiguration.ephemeral
        let session = URLSession(configuration: configuration)
        //3. Creamos el request para especificar lo que queremos obtener
        let elReq = URLRequest (url: url)
        //4. Creamos la tarea especifica de descarga
        let task = session.dataTask(with: elReq) { bytes, response, error in
            // Que queremos que pase al recibir el response:
            if error == nil {
                guard let data = bytes else { return }
                DispatchQueue.main.async {
                    self.image = UIImage(data: data)
                }
            } else {
                self.image = UIImage(named: "CategoryPlaceholder")
            }
        }
        // iniciamos la tarea
        task.resume()
    }
}
