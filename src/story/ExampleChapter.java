package story;

import logic.GameObject;
import logic.QuestObject;

import java.awt.*;

import static util.Util.TREE;
import static util.Util.threadMain;

public class ExampleChapter extends BaseChapter{
    public ExampleChapter() {
        setDecorateImage(TREE);
        QuestObject questObject = new QuestObject("file:res/image/mystic.jpg");
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setRunnable(() -> threadMain.clear());
        setFinalQuestObject(questObject);
    }
}
