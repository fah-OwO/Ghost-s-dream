package story;

import logic.QuestObject;
import util.GameMediaData;
import util.Triple;

import static util.Util.*;

public class ChapterTutorial extends BaseChapter {
    @Override
    public void setUp() {
        setNextChapter(new Chapter1());
        QuestObject questObject = new QuestObject();
        questObject.setImage(GameMediaData.SQUID, 4);
        questObject.setActiveRange(4);
        questObject.setConsumer(obj -> {
            for (double j = 0.0001; obj.getPos().y < height; j *= 1.1) {
                if (obj.isTriggered()) obj.setPosY(metreToCoord(j) / refreshPeriod);
                else break;
                delay(refreshPeriod);
            }
            obj.despawn();
            changeChapter(getNextChapter());
        });
        questObject.setCo(new Triple(0, 0, metreToCoord(5)));
        setFinalQuestObject(questObject);
    }
}
