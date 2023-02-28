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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        print("DetailViewC")
        print(drinkReceived)
        drinkNameLabel.text = drinkReceived?.name
        listOfIngredientsLabel.text = drinkReceived?.ingredients
        instructionsLabel.text = drinkReceived?.directions
        if let laURL = URL(string: "http://janzelaznog.com/DDAM/iOS/drinksimages/\(drinkReceived?.img ?? "")") {
            let configuration = URLSessionConfiguration.ephemeral
            let session = URLSession(configuration: configuration)
            let elReq = URLRequest (url: laURL)
            let task = session.dataTask(with: elReq) { bytes, response, error in
                if error == nil {
                    guard let data = bytes else { return }
                    DispatchQueue.main.async {
                        self.drinkImageView.image = UIImage(data: data)
                    }
                }
            }
            task.resume()
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
}
