package logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DecorateGameObject extends GameObject {
    private static String url = "file:res/image/Tree1.png";
    private static Image decorateImage = new Image(url);

    //    private ImageView decorateImageView;
    public DecorateGameObject() {
        super();
        imageView.setImage(decorateImage);// = new ImageView(url);
//        image=null;
//        imageView.setPreserveRatio(true);
    }

//    @Override
//    public ImageView getImageView() {
////        decorateImageView.setPreserveRatio(true);
//        return decorateImageView;
//    }
}
