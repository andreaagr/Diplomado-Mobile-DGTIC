//
//  DetailViewController.swift
//  NativeRequest
//
//  Created by Andrea Garcìa on 04/02/23.
//

import UIKit

class DetailViewController: UIViewController {
    
    var characterReceived: Result?
    
    @IBOutlet weak var CharacterImage: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        if let laURL = URL(string:String(characterReceived!.image)) {
            // Implementación de descarga en background con URLSession
            //1. Establecemos la configuracion para la sesión o usamos la basica
            let configuration = URLSessionConfiguration.ephemeral
            //2.Creamos la sesión de descarga, con la configuración elegida
            let session = URLSession(configuration: configuration)
            //3. Creamos el request para especificar lo que queremos obtener
            let elReq = URLRequest (url: laURL)
            //4. Creamos la tarea especifica de descarga
            let task = session.dataTask(with: elReq) { bytes, response, error in
            // Que queremos que pase al recibir el response:
                if error == nil {
                    guard let data = bytes else { return }
                    DispatchQueue.main.async {
                        self.CharacterImage.image = UIImage(data: data)
                    }
                }
            }
            // iniciamos la tarea
            task.resume()
        }
        
        // Do any additional setup after loading the view.
    }
}
    
    
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

