package gui;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import story.BaseChapter;
import story.Chapter1;
import story.Chapter2;
import story.ChapterTutorial;
import util.GameMediaData;

public class QuickMenu extends GridPane {

    private QuickMenu() {
        Main.setMouseVisible(true);
        setAlignment(Pos.CENTER);
        setVgap(8);
        setHgap(8);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        int i = 0;
        ChapterCell chapterTutorial = new ChapterCell(new ChapterTutorial(), "tutorial", GameMediaData.getImage("squid"));
        ObservableList<ChapterCell> chapterCells = FXCollections.observableArrayList();
        chapterCells.add(chapterTutorial);
        this.add(chapterTutorial, i++, 0);
        ChapterCell chapter1 = new ChapterCell(new Chapter1(), "chapter 1", GameMediaData.getImage("eye"));
        chapterCells.add(chapter1);
        this.add(chapter1, i++, 0);
        ChapterCell chapter2 = new ChapterCell(new Chapter2(), "chapter 2", GameMediaData.getImage("table"));
        chapterCells.add(chapter2);
        this.add(chapter2, i, 0);
        for (ChapterCell cell : chapterCells) {
            cell.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                BaseChapter chapter = cell.getChapter();
                chapter.run();
                Main.setMouseVisible(false);
            });
        }
        this.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public static QuickMenu initiate(Scene scene) {
        return new QuickMenu();
    }
}
