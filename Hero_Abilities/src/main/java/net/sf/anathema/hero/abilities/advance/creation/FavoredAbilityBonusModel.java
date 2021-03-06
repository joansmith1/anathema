package net.sf.anathema.hero.abilities.advance.creation;

import net.sf.anathema.hero.template.creation.ICreationPoints;
import net.sf.anathema.hero.advance.overview.model.AbstractSpendingModel;

public class FavoredAbilityBonusModel extends AbstractSpendingModel {
  private final AbilityCostCalculator abilityCalculator;
  private final ICreationPoints creationPoints;

  public FavoredAbilityBonusModel(AbilityCostCalculator abilityCalculator, ICreationPoints creationPoints) {
    super("Abilities", "FavoredDot");
    this.abilityCalculator = abilityCalculator;
    this.creationPoints = creationPoints;
  }

  @Override
  public Integer getValue() {
    return abilityCalculator.getFreePointsSpent(true);
  }

  @Override
  public int getSpentBonusPoints() {
    return 0;
  }

  @Override
  public int getAllotment() {
    return creationPoints.getAbilityCreationPoints().getFavoredDotCount();
  }
}