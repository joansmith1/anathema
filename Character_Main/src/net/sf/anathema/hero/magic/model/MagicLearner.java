package net.sf.anathema.hero.magic.model;

import net.sf.anathema.character.main.magic.model.magic.Magic;

import java.util.Collection;
import java.util.Set;

public interface MagicLearner {

  boolean handlesMagic(Magic magic);

  int getAdditionalBonusPoints(Magic magic);

  int getLearnCount(Magic magic, Set<Magic> alreadyHandledMagic);

  Collection<? extends Magic > getLearnedMagic(boolean experienced);
}
