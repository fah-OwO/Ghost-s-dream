package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import story.BaseChapter;
import util.GameMediaData;

import static util.Util.*;


public class ChapterCell extends StackPane {

    private final BaseChapter chapter;

    public ChapterCell(BaseChapter chapter, String name, Image image) {
        this.chapter = chapter;
        if (name.isBlank()) name = "secret chapter";
        if (image == null) image = GameMediaData.BLACK;
        this.setPrefSize(width, height);
        this.setPadding(new Insets(8, 8, 8, 8));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(100);
        this.getChildren().add(imageView);
        setAlignment(imageView, Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Text text = new Text(name);
        text.setFont(font);
        text.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(text);
        setAlignment(text, Pos.BOTTOM_CENTER);
        this.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    }

    public BaseChapter getChapter() {
        return chapter;
    }
}
