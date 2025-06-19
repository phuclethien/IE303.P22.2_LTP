package com.example.shoes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        int cols = 6;
        int rows = 3;
        int cardCount = cols * rows; //6 ảnh nên 18 card cho đều

        String[] names = {
            "4DFWD PULSE SHOES", "FORUM MID SHOES", "SUPERNOVA SHOES",
            "Adidas", "Adidas", "4DFWD PULSE SHOES"
        };
        String[] descriptions = {
            "This product is excluded from all promotional discounts and offers",
            "This product is excluded from all promotional discounts and offers",
            "NMD City Stock 2",
            "NMD City Stock 2",
            "NMD City Stock 2",
            "This product is excluded from all promotional discounts and offers"
        };
        String[] brands = {
            "Adidas", "Adidas", "Adidas",
            "Adidas", "Adidas", "Adidas"
        };
        String[] prices = {
            "$160.00", "$100.00", "$150.00",
            "$160.00", "$120.00", "$160.00"
        };

        VBox leftBox = new VBox();
        leftBox.setMinWidth(200);
        leftBox.setPrefWidth(200);
        leftBox.setPadding(new Insets(20));
        leftBox.setSpacing(10);

        ImageView selectedImage = new ImageView();
        selectedImage.setFitWidth(200);
        selectedImage.setFitHeight(200);
        selectedImage.setPreserveRatio(true);

        Line separator = new Line(0, 0, 200, 0);
        separator.setStrokeWidth(1);
        separator.setStyle("-fx-stroke: #CCCCCC;");

        Label selectedName = new Label("");
        selectedName.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label selectedPrice = new Label("");
        selectedPrice.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label selectedBrands = new Label("");
        selectedBrands.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
        Label selectedDesc = new Label("");
        selectedDesc.setWrapText(true);

        leftBox.getChildren().addAll(selectedImage, selectedName, selectedPrice, selectedBrands, selectedDesc);

        VBox[] selectedCard = {null};

        for (int i = 0; i < cardCount; i++) {
            VBox card = new VBox();
            card.setStyle("-fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: #EBEBEB;");
            card.setPadding(new Insets(10));
            card.setMinSize(180, 220);
            card.setMaxSize(180, 220);

            int imgIndex = (i % 6) + 1; // 1 đến 6
            String imgPath = "/assets/img" + imgIndex + ".png";
            Image image = null;
            try {
                image = new Image(getClass().getResourceAsStream(imgPath));
            } catch (Exception e) {
            }
            ImageView imageView = new ImageView();
            if (image != null && !image.isError()) {
                imageView.setImage(image);
            }
            imageView.setFitWidth(130);
            imageView.setFitHeight(130);
            imageView.setPreserveRatio(true);

            int infoIndex = (imgIndex - 1); 
            Label nameLabel = new Label(names[infoIndex]);
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            Label descriptionLabel = new Label(descriptions[infoIndex]);

            Label brandsLabel = new Label(brands[infoIndex]);
            brandsLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");

            Label priceLabel = new Label(prices[infoIndex]);
            priceLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            HBox infoBox = new HBox(10, brandsLabel, priceLabel);
            infoBox.setPadding(new Insets(5, 0, 0, 0));
            infoBox.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(priceLabel, Priority.ALWAYS);

            card.getChildren().addAll(nameLabel, descriptionLabel, imageView, infoBox);

            //Sự kiện click card
            int finalInfoIndex = infoIndex;
            card.setOnMouseClicked((MouseEvent event) -> {
                selectedImage.setImage(imageView.getImage());
                selectedName.setText(names[finalInfoIndex]);
                selectedPrice.setText(prices[finalInfoIndex]);
                selectedBrands.setText(brands[finalInfoIndex]);
                selectedDesc.setText(descriptions[finalInfoIndex]);

                if (selectedCard[0] != null) {
                    selectedCard[0].setStyle(" -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: #EBEBEB;");
                }
                card.setStyle("-fx-border-color: #2196F3; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: #EBEBEB;");
                selectedCard[0] = card;

                leftBox.getChildren().remove(separator);

                leftBox.getChildren().remove(selectedName);
                leftBox.getChildren().add(1, separator);
                leftBox.getChildren().add(2, selectedName);
            });

            int col = i % cols;
            int row = i / cols;
            grid.add(card, col, row);
        }

        HBox root = new HBox(leftBox, grid);
        root.setPrefWidth(800);
        root.setPrefHeight(600);

        HBox.setHgrow(leftBox, Priority.ALWAYS);
        HBox.setHgrow(grid, Priority.ALWAYS);
        leftBox.setMaxWidth(Double.MAX_VALUE);
        grid.setMaxWidth(Double.MAX_VALUE);

        root.setSpacing(0);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Danh sách sản phẩm");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}