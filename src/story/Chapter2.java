package story;

import logic.Cloud;
import logic.GameObject;
import logic.QuestObject;
import util.GameMediaData;

import static util.Util.delay;

public class Chapter2 extends BaseChapter {


    @Override
    public void setUp() {
        setUpper(GameMediaData.EVENING);
        QuestObject questObject = (QuestObject) GameMediaData.getGameObject("squid");
        questObject.setPassive(false);
        questObject.setActiveRange(5);
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> changeChapter(null));
        setFinalQuestObject(questObject);
        QuestObject eye = (QuestObject) GameMediaData.getGameObject("eye");
        eye.setPassive(true);
        eye.setActiveRange(10);
        eye.setConsumer((obj) -> {
            for (int j = 1; j < 5; j++) {
                if (obj.isTriggered()) obj.setImage(GameMediaData.getAnimation("eye").get(j));
                else break;
                delay(100);
            }
            threadMain.addPreRun(() -> {
                obj.despawn();
                obj.setImage(GameMediaData.getAnimation("eye").get(0));
            });
        });
        for (int i = 0; i < 300; i++) {
            QuestObject newEye = eye.clone();
            newEye.spawnAnywhere();
            addGameObject(newEye);
        }
        for (int i = 0; i < 100; i++) {
            GameObject decorateObject = GameMediaData.getRandomDecoration("bush");
            decorateObject.spawnAnywhere();
            addGameObject(decorateObject);
        }
        Cloud cloud = new Cloud();
        cloud.setImage(GameMediaData.getRandomImage("bush"), 4);
        cloud.setMinSpawningRange(7);
        for (int i = 0; i < 20; i++) {
            Cloud newSquid = cloud.clone();
            newSquid.spawnAnywhere();
            addGameObject(newSquid);
        }
    }
}
