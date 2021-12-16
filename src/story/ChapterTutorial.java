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
        questObject.setConsumer((obj) -> changeChapter(getNextChapter()));
        questObject.setCo(new Triple(0, 0, metreToCoord(5)));
        setFinalQuestObject(questObject);
    }
}
