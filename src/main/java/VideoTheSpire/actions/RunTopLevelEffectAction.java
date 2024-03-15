package VideoTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RunTopLevelEffectAction extends AbstractGameAction {
    private final AbstractGameEffect effect;

    public RunTopLevelEffectAction(AbstractGameEffect effect) {
        this.effect = effect;
        AbstractDungeon.topLevelEffectsQueue.add(effect);
    }

    @Override
    public void update() {
        this.isDone = effect.isDone;
    }
}
