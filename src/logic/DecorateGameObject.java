package logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class DecorateGameObject extends GameObject {
    private static String url = "file:res/image/Tree1.png";
    private static Image decorateImage = new Image(url);
    private static final ObservableList<DecorateGameObject> decorateObjs = FXCollections.observableArrayList();

    public DecorateGameObject() {
        super();
        imageView.setImage(decorateImage);
        decorateObjs.add(this);
    }

    public static void setUrl(String url) {
        DecorateGameObject.url = url;
        decorateImage = new Image(url);
        for (DecorateGameObject decorateObj : decorateObjs) {
            decorateObj.imageView.setImage(decorateImage);
        }
    }

    @Override
    protected void onRemove() {
        super.onRemove();
        decorateObjs.remove(this);
    }
}
