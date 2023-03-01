//
//  DetailViewController.swift
//  Barman
//
//  Created by Andrea García Ruiz on 28/02/23.
//

import UIKit

class DetailViewController: UIViewController {

    @IBOutlet weak var drinkImageView: UIImageView!
    @IBOutlet weak var drinkNameLabel: UILabel!
    @IBOutlet weak var listOfIngredientsLabel: UILabel!
    @IBOutlet weak var instructionsLabel: UILabel!
    
    var drinkReceived: Drink?
    lazy var urlLocal: URL? = {
        var tmp = URL(string: "")
        if let documentsURL = FileManager.default.urls(for:.documentDirectory, in: .userDomainMask).first {
            tmp = documentsURL.appendingPathComponent(drinkReceived!.img)
        }
        return tmp
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        drinkNameLabel.text = drinkReceived?.name
        listOfIngredientsLabel.text = drinkReceived?.ingredients
        instructionsLabel.text = drinkReceived?.directions
        if doesImageExistLocally() {
            showImageLocally()
        } else {
            downloadImage(doOnSuccess: { data in
                self.showImageFromBackgroundThread(data: data)
                self.saveFile(data: data)
            })
        }
    }
    
    func doesImageExistLocally() -> Bool {
        return FileManager.default.fileExists(atPath:urlLocal?.path ?? "")
    }
    
    func showImageLocally() {
        let imageData = NSData(contentsOf: urlLocal!)
        self.drinkImageView.image = UIImage(data: imageData! as Data)
    }
    
    func downloadImage(doOnSuccess: @escaping (Data) -> Void) {
        if let laURL = URL(string: "http://janzelaznog.com/DDAM/iOS/drinksimages/\(drinkReceived?.img ?? "")") {
            let configuration = URLSessionConfiguration.ephemeral
            let session = URLSession(configuration: configuration)
            let elReq = URLRequest (url: laURL)
            let task = session.dataTask(with: elReq) { bytes, response, error in
                if error == nil {
                    guard let data = bytes else { return }
                    doOnSuccess(data)
                }
            }
            task.resume()
        }
    }
    
    func saveFile(data: Data) {
        do {
            try data.write(to:self.urlLocal!)
        }
        catch {
            print ("Error al guardar el archivo " + String(describing: error))
        }
    }
    
    func showImageFromBackgroundThread(data: Data) {
        DispatchQueue.main.async {
            self.drinkImageView.image = UIImage(data: data)
        }
    }
}
