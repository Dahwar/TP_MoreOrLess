package france.uha.ensisa.fl.moreorless;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.ScrollPane;
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
        final VBox rules = new VBox();
        BorderPane rulesTextContainer = new BorderPane();
        ScrollPane sp = new ScrollPane();
        
        //Set scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("More or Less");
        
        //Set model and init
        final MoreOrLessModel model = new MoreOrLessModel();
        final TransitionFactory tf = new TransitionFactory();
        model.init(100);        
        
        //Define all label, text, textfield, ...
        final ListView<String> list = new ListView<>();
        CheckBox cb = new CheckBox("Voir la liste des nombres testés");
        Button newGame = new Button("Nouveau");
        Button reset = new Button("Nouveau");
        Button getRules = new Button("Règles");
        Button returnToMenu = new Button("Menu");
        final TextField number = new TextField();
        final TextField interval = new TextField(Integer.toString(model.getMax()));
        final Label moreless = new Label("");
        final Label chosenInterval = new Label("");
        final Label intervalText = new Label(" Choisir un intervalle de 0 à ... ! " + "\n Maximum : 10000000 ");
        Text gameTitle = new Text("More or Less");
        Text credits = new Text("Copyright 2014 LACROIX Florent. Tous droits réservés.");
        Text rulesText = new Text("Règles du More or Less\n\nLe but du jeu est de trouver un chiffre généré aléatoirement "
                + "par l'ordinateur entre 0 et un nombre maximum fixé par l'utilisateur, en un minimum de coup.\n"
                + "Il faut tout d'abord choisir notre interval, allant de zéro "
                + "jusqu'à un chiffre déterminé par l'utilisateur (maximum : " + model.getLimit() + ").Ensuite, "
                + "il faut entrer un chiffre que l'on pense être le chiffre généré par l'ordinateur. En validant avec \"Entrée\", "
                + "on nous dit que le nombre secret est plus grand ou plus petit que le nombre choisi. On entre alors un autre chiffre, "
                + "et ainsi de suite jusq'à trouver le bon nomre.\n\n"
                + "Play & Enjoy :)");
        
        
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
        rulesTextContainer.setCenter(rulesText);
        sp.setContent(rulesTextContainer);
        
        buttonGame.getChildren().add(newGame);
        buttonGame.getChildren().add(getRules);
        
        rules.getChildren().add(sp);
        rules.getChildren().add(returnToMenu);
        enterNumber.getChildren().add(chosenInterval);
        enterNumber.getChildren().add(moreless);
        enterNumber.getChildren().add(number);
        enterNumber.getChildren().add(cb);
        enterNumber.getChildren().add(reset);
        setInterval.getChildren().add(intervalText);
        setInterval.getChildren().add(interval);
        setInterval.getChildren().add(buttonGame);
        
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
        buttonGame.setSpacing(10.0);
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
        sp.setPrefWidth(480.0);
        sp.setMaxWidth(480.0);
        sp.setMinWidth(480.0);
        sp.setPrefHeight(275.0);
        sp.setMaxHeight(275.0);
        sp.setMinHeight(275.0);
        rulesText.getStyleClass().add("textRules");
        rulesText.setTextAlignment(TextAlignment.CENTER);
        VBox.setMargin(sp, new Insets(20,0,15,0));
        BorderPane.setMargin(rulesText, new Insets(5.0, 0.0, 5.0, 15.0));
        rules.setAlignment(Pos.TOP_CENTER);
        rulesText.setWrappingWidth(450.0);
        
        // Animations
        final FadeTransition fadeTransitionLeftOff = tf.getFadeTransition(0.25, left, 1, 0, 1, false);
        final FadeTransition fadeTransitionLeftOn = tf.getFadeTransition(0.25, left, 0, 1, 1, false);
        final FadeTransition fadeTransitionMorelessOff = tf.getFadeTransition(0.15, moreless, 1, 0, 1, false);
        final FadeTransition fadeTransitionMorelessOn = tf.getFadeTransition(0.15, moreless, 0, 1, 1, false);
        final TranslateTransition translateTransitionXMorelessOn = tf.getTranslateTransitionX(0.15, moreless, -100, 0, 1, false);
        final TranslateTransition translateTransitionXMorelessOff = tf.getTranslateTransitionX(0.15, moreless, 0, 100, 1, false);
        
         //Controllers
         number.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                    if(number.getText().length()>10) {
                        model.setCurrentNumber(Integer.parseInt(number.getText().substring(0, 10)));
                    }
                    else {
                        model.setCurrentNumber(Integer.parseInt(number.getText()));
                    }
                    fadeTransitionMorelessOff.play();
                    fadeTransitionMorelessOff.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if(model.win()){
                                if(model.getListOfValue().size()==1) {
                                    moreless.setText(" C'est gagné en 1 coup ! ");
                                }
                                else {
                                    moreless.setText(" C'est gagné en " + model.getListOfValue().size() + " coups ! ");
                                }
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
                            fadeTransitionMorelessOn.play();
                        }
                    });
                }
            }
        });
        
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String temp;
                if(interval.getText().length()>10){
                    temp=interval.getText().substring(0,10);
                }
                else {
                    temp=interval.getText();
                }
                
                if(Integer.parseInt(temp)>model.getLimit() || Integer.parseInt(temp)<=0) {
                }
                else {
                    fadeTransitionLeftOff.play();
                    fadeTransitionLeftOff.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            model.init(Integer.parseInt(interval.getText()));
                            left.setCenter(game);
                            left.setRight(list);
                            interval.setText(Integer.toString(model.getMax()));
                            chosenInterval.setText(" Entrez un nombre entre 0 et " + model.getMax() + " \n et pressez \"Entrée\" ");
                            moreless.setText(" Entrez un chiffre ! ");
                            number.setText("");
                            number.setDisable(false);
                            fadeTransitionLeftOn.play();
                        }
                    });
                }
            }
        });

        
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                fadeTransitionLeftOff.play();
                fadeTransitionLeftOff.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        left.setCenter(beforeGame);
                        left.setRight(null);
                        fadeTransitionLeftOn.play();
                    }
                });
            }
        });
        
        getRules.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fadeTransitionLeftOff.play();
                fadeTransitionLeftOff.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        left.setCenter(rules);
                        fadeTransitionLeftOn.play();
                    }
                });
            }
        });
        
        returnToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fadeTransitionLeftOff.play();
                fadeTransitionLeftOff.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        left.setCenter(beforeGame);
                        fadeTransitionLeftOn.play();
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
