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
    var imgPickCon : UIImagePickerController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
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
    
    @IBAction func saveItems(_ sender: Any) {
        
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
}
