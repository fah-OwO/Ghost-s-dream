package story;

import logic.QuestObject;
import util.GameMediaData;
import util.Triple;

import static util.Util.metreToCoord;

public class ChapterTutorial extends BaseChapter {
    @Override
    public void setUp() {
        setNextChapter(new Chapter1());
        QuestObject questObject = (QuestObject) GameMediaData.getGameObject("squid");
        questObject.setPassive(false);
        questObject.setActiveRange(5);
        questObject.setConsumer((obj) -> changeChapter(getNextChapter()));
        questObject.setCo(new Triple(0, 0, metreToCoord(5)));
        setFinalQuestObject(questObject);
    }
}
