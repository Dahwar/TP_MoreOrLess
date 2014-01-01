package france.uha.ensisa.fl.moreorless;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Florent
 */
public class MoreOrLess extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        //Define containers
        BorderPane root = new BorderPane();
        final BorderPane left = new BorderPane();
        final VBox beforeGame = new VBox();
        final VBox game = new VBox();
        HBox buttonGame = new HBox();
        VBox enterNumber = new VBox();
        VBox setInterval = new VBox();
        
        //Set scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("More or Less");
        
        //Set model and init
        final MoreOrLessModel model = new MoreOrLessModel();
        model.init(100);
        
        //Define all label, text, textfield, ...
        final ListView<String> list = new ListView<>();
        CheckBox cb = new CheckBox("Voir la liste des nombres testés");
        Button newGame = new Button("Nouveau");
        Button reset = new Button("Nouveau");
        final TextField number = new TextField();
        final TextField interval = new TextField(Integer.toString(model.getMax()));
        final Label moreless = new Label("");
        final Label chosenInterval = new Label("");
        final Label intervalText = new Label(" Choisir un intervalle de 0 à ... ! " + "\n Maximum : 10000000 ");
        Text gameTitle = new Text("More or Less");
        Text credits = new Text("Copyright 2014 LACROIX Florent. Tous droits réservés.");
        
        
        //Load ressources
        Font.loadFont(getClass().getResourceAsStream("fonts/FREE.ttf"), 20);
        Font.loadFont(getClass().getResourceAsStream("fonts/SAQ.ttf"), 20);
        scene.getStylesheets().add(MoreOrLess.class.getResource("css/stylesheet.css").toString());
        
        //Set effect
        gameTitle.getStyleClass().add("gameName");
        root.getStyleClass().add("backgd");
        intervalText.getStyleClass().add("textGame");
        chosenInterval.getStyleClass().add("textGame");
        moreless.getStyleClass().add("textGameMoreLess");
        credits.getStyleClass().add("gameCredits");

        number.setPromptText("Entrez un nombre");
        
        //Fill containers
        buttonGame.getChildren().add(reset);
        
        enterNumber.getChildren().add(chosenInterval);
        enterNumber.getChildren().add(moreless);
        enterNumber.getChildren().add(number);
        enterNumber.getChildren().add(cb);
        enterNumber.getChildren().add(buttonGame);
        setInterval.getChildren().add(intervalText);
        setInterval.getChildren().add(interval);
        setInterval.getChildren().add(newGame);
        
        beforeGame.getChildren().add(setInterval);
        game.getChildren().add(enterNumber);
        
        left.setCenter(beforeGame);
        
        root.setTop(gameTitle);
        root.setCenter(left);
        root.setRight(null);
        root.setBottom(credits);
        
        //Make the application beauty :)
        enterNumber.setAlignment(Pos.CENTER);
        number.setMaxWidth(250.0);
        interval.setMaxWidth(250.0);
        setInterval.setAlignment(Pos.CENTER);
        intervalText.setTextAlignment(TextAlignment.CENTER);
        chosenInterval.setTextAlignment(TextAlignment.CENTER);
        buttonGame.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(gameTitle, Pos.CENTER);
        game.setAlignment(Pos.TOP_CENTER);
        list.setPrefWidth(120);
        number.setPrefWidth(100);
        interval.setPrefWidth(100);
        newGame.setPrefWidth(100);
        VBox.setMargin(interval, new Insets(10,0,15,0));
        VBox.setMargin(number, new Insets(10,0,15,0));
        VBox.setMargin(cb, new Insets(0,0,15,0));
        BorderPane.setAlignment(credits, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(credits, new Insets(5, 0, -5, 0));
        
        // Animations
        final FadeTransition fadeTransitionOff;
        fadeTransitionOff = FadeTransitionBuilder.create()
            .duration(Duration.seconds(0.25))
            .node(left)
            .fromValue(1)
            .toValue(0)
            .cycleCount(1)
            .autoReverse(false)
            .build();
        
         final FadeTransition fadeTransitionOn;
         fadeTransitionOn = FadeTransitionBuilder.create()
            .duration(Duration.seconds(0.25))
            .node(left)
            .fromValue(0)
            .toValue(1)
            .cycleCount(1)
            .autoReverse(false)
            .build();
        
         //Controllers
         number.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                    model.setCurrentNumber(Integer.parseInt(number.getText()));
                    if(model.win()){
                        moreless.setText(" C'est gagné en " + model.getListOfValue().size() + " coups ! ");
                        number.setDisable(true);
                    }
                    else {
                        if(model.getCurrentNumber()<0 || model.getCurrentNumber()>model.getMax()) {
                            moreless.setText(" En dehors des limites ! ");
                        }
                        else if(model.isMoreOrLess()== MoreOrLessModel.State.MORE) {
                            moreless.setText(" C'est plus ! ");
                        }
                        else if(model.isMoreOrLess()== MoreOrLessModel.State.LESS) {
                            moreless.setText(" C'est moins ! ");
                        }
                        number.setText("");
                    }
                }
            }
        });
        
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Integer.parseInt(interval.getText())>model.getLimit()) {
                }
                else {
                    fadeTransitionOff.play();
                    fadeTransitionOff.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            model.init(Integer.parseInt(interval.getText()));
                            left.setCenter(game);
                            left.setRight(list);
                            interval.setText(Integer.toString(model.getMax()));
                            chosenInterval.setText(" Entrez un nombre entre 0 et " + model.getMax() + " \n et pressez \"Entrée\" ");
                            moreless.setText("");
                            number.setText("");
                            number.setDisable(false);
                            fadeTransitionOn.play();
                        }
                    });
                }
            }
        });

        
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                fadeTransitionOff.play();
                fadeTransitionOff.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        left.setCenter(beforeGame);
                        left.setRight(null);
                        fadeTransitionOn.play();
                    }
                });
            }
        });
        
        cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if(!new_val) {
                    list.setItems(null);
                }
                else {
                    list.setItems(model.getListOfValue());
                }
            }
        });
        
        //Display the scene
        primaryStage.setMinHeight(600.0);
        primaryStage.setMinWidth(800.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
