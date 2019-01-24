/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageupdate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;



/**
 *
 * @author Red
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button diapoBouton;
    @FXML
    private Button imageBouton;
    @FXML
    private Button retourBouton;
    
     @FXML
    private Button stopBouton;
     @FXML
    private Button visualiser;
      @FXML
    private Button appliquer;
    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox list;                 
   @FXML
    private TableView tab; 
    @FXML
    private Timeline timeLine;
     @FXML         
   JOptionPane jop = new JOptionPane();
      @FXML
      private String ch;
       @FXML
      AnchorPane inchor;
      @FXML
      boolean testTimeLine=false;
       @FXML
      private MenuItem choisirImageMenu;
       
 
      @FXML
    private void about(ActionEvent event) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information sur l'application");
    alert.setHeaderText(null);
     alert.setContentText("Cette application permet de faire des modifications sur une image. \n"
             + "Pour cela, il suffit de choisir votre image, et d'appliquer le thème souhaité. \n"
             + "L'image sera modifier automatiquement en cliquant sur \"Appliquer\".");

      alert.showAndWait();
    }
      
   @FXML
    private void choisirImage(ActionEvent event) throws IOException {
            
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter fileExtensions = 
                new FileChooser.ExtensionFilter(
                    "Image", "*.jpg", "*.png");
                fileChooser.getExtensionFilters().add(fileExtensions);
            //Show open file dialog 
            File file = fileChooser.showOpenDialog(null);
                       
         if(file != null){
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image2 = SwingFXUtils.toFXImage(bufferedImage, null);
                if (imageView != null)
                 imageView.setImage(image2);  
                String filename = file.getAbsolutePath().toString() ;
               imageBouton.setText(filename);
         }
         
    }     
        
    @FXML
    private void visualiserFiltter(ActionEvent event) throws IOException {
      BufferedImage img = null;
      String chemin=imageBouton.getText().toString();
      int num=list.getSelectionModel().getSelectedIndex();
      
     if(chemin.equals(ch)){
     final Alert alert = new Alert(Alert.AlertType.INFORMATION);   
     alert.setTitle("Information"); 
     alert.setHeaderText("Vous n'avez pas choisi d'image ?"); 
     alert.setContentText("Veuillez choisir une image");
     alert.showAndWait();
     }
     else if((num==-1)){
      final Alert alert = new Alert(Alert.AlertType.INFORMATION);   
      alert.setTitle("Information"); 
      alert.setHeaderText("Vous n'avez pas choisi de thème ?"); 
      alert.setContentText("Veuillez choisir un thème.");
      alert.showAndWait();
     }     
      else{    
          modification(imageView,img,num,chemin);
      }
    } 
    
     @FXML
    private void appliquerFiltter(ActionEvent event) throws IOException {
      BufferedImage img = null;
      String chemin=imageBouton.getText().toString();
     int num=list.getSelectionModel().getSelectedIndex();
     if(chemin.equals(ch)){
     final Alert alert = new Alert(Alert.AlertType.INFORMATION);   
     alert.setTitle("Information"); 
     alert.setHeaderText("Vous n'avez pas choisi d'image ?"); 
     alert.setContentText("Veuillez choisir une image");
     alert.showAndWait();
     }
     else if((num==-1)){
      final Alert alert = new Alert(Alert.AlertType.INFORMATION);   
      alert.setTitle("Information"); 
      alert.setHeaderText("Vous n'avez pas choisi de thème ?"); 
      alert.setContentText("Veuillez choisir un thème.");
      alert.showAndWait();
     }
     else
    {
        
     Alert alert = new Alert(AlertType.CONFIRMATION);
     alert.setTitle("Confirmation");
     alert.setHeaderText("Enregistrer les modification !");
     alert.setContentText("Voulez-vous continuer ?");
     Optional<ButtonType> result = alert.showAndWait();
    
     if (result.get() == ButtonType.OK){
     modification(imageView,img,num,chemin);
     Image imageRec=imageView.getImage();
     saveToFile(imageRec, chemin);   
    }        
        

    }
    }
    
    @FXML
    private void Quitter(ActionEvent event){
    System.exit(0);
    }
         @FXML
    private void diapoFiltter(ActionEvent event){
     BufferedImage img = null;
     String chemin=imageBouton.getText().toString();
     int taille=list.getItems().size();
     
     if(!chemin.equals(ch))
    {
      Image imageRec=imageView.getImage(); 
       
      
     timeLine = TimelineBuilder.create()
        .cycleCount(1)
        .keyFrames(
            new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    modification(imageView,img,0,chemin);
                    list.getSelectionModel().select(0);
                }
            }),
            new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                     modification(imageView,img,1,chemin);
                      list.getSelectionModel().select(1);
                }
            }),
            new KeyFrame(Duration.seconds(6) ,new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                     modification(imageView,img,2,chemin);
                      list.getSelectionModel().select(2);
                }
            }),
            new KeyFrame(Duration.seconds(9), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    modification(imageView,img,3,chemin);
                     list.getSelectionModel().select(3);
                }
            }),
            new KeyFrame(Duration.seconds(12), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                     modification(imageView,img,4,chemin);
                      list.getSelectionModel().select(4);
                }
            }),
            new KeyFrame(Duration.seconds(15), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    modification(imageView,img,5,chemin);
                     list.getSelectionModel().select(5);
                }
            }),
            new KeyFrame(Duration.seconds(18), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                     modification(imageView,img,6,chemin);
                      list.getSelectionModel().select(6);
                }
            }),
            new KeyFrame(Duration.seconds(21), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                   modification(imageView,img,7,chemin);
                    list.getSelectionModel().select(7);
                }
            }),
            new KeyFrame(Duration.seconds(24), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    modification(imageView,img,8,chemin);
                     list.getSelectionModel().select(8);
                }
            }),
             new KeyFrame(Duration.seconds(27), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    modification(imageView,img,9,chemin);
                    list.getSelectionModel().select(9);
                }
            }),
              new KeyFrame(Duration.seconds(30), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                     modification(imageView,img,10,chemin);
                     list.getSelectionModel().select(10);
                }
            }),
               new KeyFrame(Duration.seconds(33), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                     modification(imageView,img,11,chemin);
                     list.getSelectionModel().select(11);
                }
            }),
                new KeyFrame(Duration.seconds(36), new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                     modification(imageView,img,12,chemin);
                     list.getSelectionModel().select(12);
                }
            })
               
            
        )
        .build();
        timeLine.play();  
        stopBouton.setDisable(false);
        diapoBouton.setDisable(true);
        imageBouton.setDisable(true);
        visualiser.setDisable(true);
        appliquer.setDisable(true);
        list.setDisable(true); 
        choisirImageMenu.setDisable(true);
        testTimeLine=true;
    }
   else {    
   final Alert alert = new Alert(Alert.AlertType.INFORMATION);   
   alert.setTitle("Information"); 
   alert.setHeaderText("Vous n'avez pas choisis d'image ?"); 
   alert.setContentText("Veuillez choisir une image");
   alert.showAndWait();   
    } 
   }
    @FXML
    private void stopDiapo(ActionEvent event) throws IOException, InterruptedException {
     timeLine.stop();
     stopBouton.setDisable(true);
     diapoBouton.setDisable(false);
     testTimeLine=false;
     imageBouton.setDisable(false);
     visualiser.setDisable(false);
     appliquer.setDisable(false);
     list.setDisable(false);
     choisirImageMenu.setDisable(false);
        
    }
    
    
    
     @FXML
    private void retourFiltter(ActionEvent event) {
        
     String chemin =imageBouton.getText().toString();
    // int num=list.getSelectionModel().getSelectedIndex();     
     if(chemin.equals(ch)){
     final Alert alert = new Alert(Alert.AlertType.INFORMATION);   
     alert.setTitle("Information"); 
     alert.setHeaderText("Vous n'avez pas choisi d'image ?"); 
     alert.setContentText("Veuillez choisir une image");
     alert.showAndWait();
    
     }    
     else if(testTimeLine)
    {           
    timeLine.stop();
    choisirImageFromChemin(chemin,imageView);
    list.getSelectionModel().select(-1);
    testTimeLine=false;
    diapoBouton.setDisable(false);
    stopBouton.setDisable(true);
    imageBouton.setDisable(false);
    visualiser.setDisable(false);
    appliquer.setDisable(false);
    list.setDisable(false);
    choisirImageMenu.setDisable(false);
    }
    else{
    choisirImageFromChemin(chemin,imageView);
    list.getSelectionModel().select(-1); 
     } 

    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> options = FXCollections.observableArrayList(
        "Rouge",
        "Vert",
        "Bleu",
        "Négatif",
        "Noir et blan", 
        "Sepia",
        "Miroir",
        "rotation 180",
        "rotation 90",
        "rotation 270",
        "rotation 360",
        "Nuit",
        "Crème"
        
    );
      list.setItems(options);
      ch=imageBouton.getText().toString();
      stopBouton.setDisable(true);
     
      
      
          
    }
    
    public void choisirImageFromChemin(String chemin,ImageView imageView) {
        BufferedImage img = null;
         File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = SwingFXUtils.toFXImage(img, null);
        imageView.setImage(image);
    }    
    
    

public static void saveToFile(Image image, String chemin) {
    File outputFile = new File(chemin);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
   
    try {
      ImageIO.write(bImage, "png", outputFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }






public void modification(ImageView imageView,BufferedImage img, int num, String chemin){
    
    //Filter Rouge
   
        if(num==0){
            File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            int p=img.getRGB(x, y);
            int a = (p>>24)&0xff;
            int r = (p>>16)&0xff;
            int g = 0;
            int b = 0;
           
            p=(a<<24)|(r<<16)|(0<<8)|0;
            img.setRGB(x, y, p);    
            }
         }
         Image image = SwingFXUtils.toFXImage(img, null);
         imageView.setImage(image);
       }
    //Filter vert
    if(num==1){
            File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++){
            int p=img.getRGB(x, y);
            int a = (p>>24)&0xff;
            int r = 0;
            int g = (p>>16)&0xff;
            int b = 0;
         
            p=(a<<24)|(r<<16)|(g<<8)|b;
            img.setRGB(x, y, p);    
            }
         }
         Image image = SwingFXUtils.toFXImage(img, null);
         imageView.setImage(image);
       }
    
        if(num==2){
            File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            int p=img.getRGB(x, y);
            int a = (p>>24)&0xff;
            int r = 0;
            int g = 0;
            int b = (p>>16)&0xff;
           
            p=(a<<24)|(r<<16)|(g<<8)|b;
            img.setRGB(x, y, p);    
            }
         }
         Image image = SwingFXUtils.toFXImage(img, null);
         imageView.setImage(image);
       }
    //Filter négative
            if(num==3){
            File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            int p=img.getRGB(x, y);
            int a = (p>>24)&0xff;
            int r = (p>>16)&0xff;
            int g = (p>>8)&0xff;
            int b = p&0xff;
            
           r=255-r;
           g=255-g;
           b=255-b;
 
            p=(a<<24)|(r<<16)|(g<<8)|b;
            img.setRGB(x, y, p);    
            }
         }
         Image image = SwingFXUtils.toFXImage(img, null);
         imageView.setImage(image);
       }
    
    //Filter noir et blanc
        if(num==4){
        File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            int p=img.getRGB(x, y);
            int a = (p>>24)&0xff;
            int r = (p>>16)&0xff;
            int g = (p>>8)&0xff;
            int b = p&0xff;
            int avg=(r+g+b)/3;
            p=(a<<24)|(avg<<16)|(avg<<8)|avg;
            img.setRGB(x, y, p);    
            }
         }
         Image image = SwingFXUtils.toFXImage(img, null);
         imageView.setImage(image);
       }
    //Filter Spatia
        if(num==5){
        File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            int p=img.getRGB(x, y);
            int a = (p>>24)&0xff;
            int r = (p>>16)&0xff;
            int g = (p>>8)&0xff;
            int b = p&0xff;
            
            int tr=(int)(0.393*r+0.769*g+0.189*b); 
            int tg=(int)(0.349*r+0.686*g+0.168*b);
            int tb=(int)(0.272*r+0.534*g+0.131*b);
            
            if(tr>255){ r=255;}else{r=tr;}
            if(tg>255){ g=255;}else{g=tg;}
            if(tb>255){ b=255;}else{r=tb;}
            p=(a<<24)|(r<<16)|(g<<8)|b;
            img.setRGB(x, y, p);    
            }
         }
         Image image = SwingFXUtils.toFXImage(img, null);
         imageView.setImage(image);
       }
    //Filter Miroir
    
            if(num==6){
         File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         BufferedImage imagemirroir= new BufferedImage(width*2, height,BufferedImage.TYPE_INT_ARGB);
              
         
         for (int y = 0; y < height; y++) {
            for (int lx = 0, rx=width*2-1; lx < width; lx++,rx--) {
            int p=img.getRGB(lx, y);
            imagemirroir.setRGB(lx, y, p);
            imagemirroir.setRGB(rx, y, p);
               
            }
         }
         Image image = SwingFXUtils.toFXImage(imagemirroir, null);
         imageView.setImage(image);
       } 
   

//Filter rotation 180
          
         if(num==7){
         File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         BufferedImage imageRotate= new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
           
         
         for (int y = 0; y < height; y++) {
            for (int lx = 0, rx=width-1; lx< width; lx++,rx--) {
            int p=img.getRGB(lx, y);
            imageRotate.setRGB(rx, y, p);               
            }
         }
         Image image = SwingFXUtils.toFXImage(imageRotate, null);
         imageView.setImage(image);
       }
         
   //rotation 90
   
            if(num==8){
         File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         BufferedImage imageRotate= new BufferedImage(height, width,BufferedImage.TYPE_INT_ARGB);
           
         
         for (int x = 0; x < width; x++) {
            for (int y = 0; y< height; y++) {
            int p=img.getRGB(x, y);
            imageRotate.setRGB(y, x, p);               
            }
         }
         Image image = SwingFXUtils.toFXImage(imageRotate, null);
         imageView.setImage(image);
       }
            
   // rotation 270
   
        if(num==9){
         File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            }
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         BufferedImage imageRotate= new BufferedImage(height, width,BufferedImage.TYPE_INT_ARGB);
           
         
         for (int x = 0; x < width; x++) {
            for (int y = 0; y< height; y++) {
            int p=img.getRGB(x, y);
            imageRotate.setRGB(height-y-1, width-x-1, p);               
            }
         }
         Image image = SwingFXUtils.toFXImage(imageRotate, null);
         imageView.setImage(image);
       }
        
  //rotation 360    

        if(num==10){
         File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            }
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         
         BufferedImage imageRotate= new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
           
         
         for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            int p=img.getRGB(x, y);
            imageRotate.setRGB(width-x-1,height-y-1, p);               
            }
         }
         Image image = SwingFXUtils.toFXImage(imageRotate, null);
        
         imageView.setImage(image);
       }
        
   // nuit
   
    if(num==11){
        File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            int p=img.getRGB(x, y);
            int a = (p>>24)&0xff;
            int r = (p>>16)&0xff;
            int g = (p>>8)&0xff;
            int b = p&0xff;
            int avg=(r+g+b)/3;
            p=(a<<24)|(r/2<<16)|(g/2<<8)|b/2;
            img.setRGB(x, y, p);    
            }
         }
         Image image = SwingFXUtils.toFXImage(img, null);
         imageView.setImage(image);
       }
    
    //plus luminiosite
    
    if(num==12){
        File f= new File(chemin);
        try {
            img=ImageIO.read(f);
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         int width = (int)  img.getWidth();
         int height = (int) img.getHeight();
         for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            int p=img.getRGB(x, y);
            int a = (p>>24)&0xff;
            int r = (p>>16)&0xff;
            int g = (p>>8)&0xff;
            int b = p&0xff;
            int avg=(r+g+b)/3;
            p=(a<<24)|(r+r/2<<16)|(g+g/2<<8)|b+b/2;
            img.setRGB(x, y, p);    
            }
         }
         Image image = SwingFXUtils.toFXImage(img, null);
         imageView.setImage(image);
       }        
    }
}
