//
//  AddDrinkViewController.swift
//  Barman
//
//  Created by Andrea García Ruiz on 28/02/23.
//

import UIKit
import AVFoundation

class AddDrinkViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    @IBOutlet weak var drinkNameTextField: UITextField!
    @IBOutlet weak var ingrdientsTextField: UITextField!
    @IBOutlet weak var directionsTextField: UITextField!
    @IBOutlet weak var imageViewContainer: UIImageView!
    
    var imgPickCon: UIImagePickerController?
    var newRecipe: Drink?
    lazy var urlLocal: URL? = {
        var tmp = URL(string: "")
        if let documentsURL = FileManager.default.urls(for:.documentDirectory, in: .userDomainMask).first {
            tmp = documentsURL.appendingPathComponent(newRecipe!.img)
        }
        return tmp
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        var perform = false
        if validateText(text: drinkNameTextField.text!) && validateText(text: directionsTextField.text!) && validateText(text: ingrdientsTextField.text!) {
            perform = true
        } else {
            let alert = UIAlertController(
                title: NSLocalizedString("Campos requeridos", comment: ""),
                message: NSLocalizedString("Todos los campos deben contener información", comment: ""),
                preferredStyle: .alert
            )
            alert.addAction(UIAlertAction(title: NSLocalizedString("ok", comment: ""), style: .default))
            self.present(alert, animated: true)
        }
        return perform
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let destination = segue.destination as! ViewController
        newRecipe = Drink(
            name: drinkNameTextField.text!,
            directions: directionsTextField.text!,
            ingredients: ingrdientsTextField.text!,
            img: "\(drinkNameTextField.text!).jpeg"
        )
        saveImageInMemory(data: (imageViewContainer.image?.jpegData(compressionQuality: 0.9))!)
        destination.newDrink = newRecipe
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        if let img = info[.originalImage] as? UIImage {
            imageViewContainer.image = img
        }
            picker.dismiss(animated: true)
        }
        
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true)
            imageViewContainer.image = UIImage(named: "DrinkPlaceholder")
        }
    
    @IBAction func takePhoto(_ sender: Any) {
        imgPickCon = UIImagePickerController()
        imgPickCon?.delegate = self
        if UIImagePickerController.isSourceTypeAvailable(.camera) {
            switch AVCaptureDevice.authorizationStatus(for:.video) {
            case .authorized: self.launchIMGPC(.camera)
            case .notDetermined: AVCaptureDevice.requestAccess (for: .video) { permiso in
                if permiso {
                    self.launchIMGPC(.camera)
                }
            }
            default:
                requestPermission()
                return
            }
        }
    }
    
    func requestPermission () {
        let ac = UIAlertController(title: "", message:"Se requiere permiso para usar la cámara. Puede configurarlo desde settings ahora", preferredStyle: .alert)
        let action = UIAlertAction(title: "SI", style: .default) { alertaction in
            if let laURL = URL(string:UIApplication.openSettingsURLString) {
                UIApplication.shared.open(laURL)
            }
        }
        let action2 = UIAlertAction(title: "NO", style: .destructive)
        ac.addAction(action)
        ac.addAction(action2)
        self.present(ac, animated: true)
    }
    
    func launchIMGPC (_ type:UIImagePickerController.SourceType) {
        DispatchQueue.main.async {
            self.imgPickCon?.sourceType = type
            self.present(self.imgPickCon!, animated: true)
        }
    }
    
    func saveImageInMemory(data: Data) {
        do {
            try data.write(to:self.urlLocal!)
        }
        catch {
            print ("Error al guardar el archivo " + String(describing: error))
        }
    }
}
