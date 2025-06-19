package com.example.shoes.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShoeApp extends Application {

    private static final String API_URL = "http://localhost:8080/api/shoes";

    class Product {
        String id;
        String name;
        int price;
        String brand;
        String description;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<Product> products = fetchProducts();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        VBox leftBox = new VBox();
        leftBox.setMinWidth(200);
        leftBox.setPadding(new Insets(20));
        leftBox.setSpacing(10);

        ImageView selectedImage = new ImageView();
        selectedImage.setFitWidth(200);
        selectedImage.setFitHeight(200);
        selectedImage.setPreserveRatio(true);

        Label selectedName = new Label();
        selectedName.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label selectedPrice = new Label();
        selectedPrice.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label selectedBrand = new Label();
        selectedBrand.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
        Label selectedDesc = new Label();
        selectedDesc.setWrapText(true);

        Line separator = new Line(0, 0, 200, 0);
        separator.setStrokeWidth(1);
        separator.setStyle("-fx-stroke: #CCCCCC;");
        leftBox.getChildren().addAll(selectedImage, selectedName, selectedPrice, selectedBrand, selectedDesc);

        VBox[] selectedCard = {null};

        int i = 0;
        for (Product p : products) {
            VBox card = new VBox();
            card.setStyle("-fx-background-color: #EBEBEB; -fx-background-radius: 8;");
            card.setPadding(new Insets(10));
            card.setPrefWidth(180);  // hoặc 200 tùy ý
            card.setMaxWidth(180);
            card.setMinWidth(180);

            int imgIndex = (i % 6) + 1;
            String imgPath = "/assets/img" + imgIndex + ".png";
            Image image = new Image(getClass().getResourceAsStream(imgPath));
            ImageView imgView = new ImageView(image);
            imgView.setFitWidth(130);
            imgView.setFitHeight(130);
            imgView.setPreserveRatio(true);

            Label name = new Label(p.name);
            name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
            Label desc = new Label(p.description);
            Label brand = new Label(p.brand);
            brand.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
            Label price = new Label("$" + p.price);
            price.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            HBox infoBox = new HBox(10, brand, price);
            infoBox.setAlignment(Pos.CENTER_LEFT);

            card.getChildren().addAll(name, desc, imgView, infoBox);

            card.setOnMouseClicked((MouseEvent e) -> {
                selectedImage.setImage(imgView.getImage());
                selectedName.setText(p.name);
                selectedPrice.setText("$" + p.price);
                selectedBrand.setText(p.brand);
                selectedDesc.setText(p.description);

                if (selectedCard[0] != null)
                    selectedCard[0].setStyle("-fx-background-color: #EBEBEB; -fx-background-radius: 8;");
                card.setStyle("-fx-border-color: #2196F3; -fx-border-width: 1; -fx-background-radius: 8;");
                selectedCard[0] = card;
            });
            grid.add(card, i % 3, i / 3);
            i++;
        }

        HBox root = new HBox(leftBox, grid);
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Danh sách sản phẩm");
        primaryStage.show();
    }

    private List<Product> fetchProducts() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            return new Gson().fromJson(reader, new TypeToken<List<Product>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}