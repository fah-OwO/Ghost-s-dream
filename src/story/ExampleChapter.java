package story;

import logic.GameObject;
import logic.QuestObject;

import static util.Util.*;

public class ExampleChapter extends BaseChapter{
    public ExampleChapter() {
        setUpper(EVENING);
        setDecorateImage(TREE);
        QuestObject questObject = new QuestObject("file:res/image/mystic.jpg");
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setRunnable(()->threadMain.clear());
        setFinalQuestObject(questObject);
    }
    public void dupe(){
        ExampleChapter exampleChapter=new ExampleChapter();
        exampleChapter.run();
    }
}
