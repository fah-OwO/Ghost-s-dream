package story;

import logic.QuestObject;
import util.MediaData;
import util.Triple;

import static util.Util.*;

public class ChapterTutorial extends BaseChapter {
    @Override
    public void setUp() {
        amount = 0;
        setNextChapter(new ExampleChapter());
        QuestObject questObject = new QuestObject();
        questObject.setImage(MediaData.SQUID, 4);
        questObject.setConsumer((obj) -> changeChapter(getNextChapter()));
        questObject.setCo(new Triple(0,0,metreToCoord(5)));
        setFinalQuestObject(questObject);
    }
}
