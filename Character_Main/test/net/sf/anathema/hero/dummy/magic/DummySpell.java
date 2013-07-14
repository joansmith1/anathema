package net.sf.anathema.hero.dummy.magic;

import net.sf.anathema.character.main.magic.model.magic.attribute.MagicAttribute;
import net.sf.anathema.character.main.magic.model.magic.cost.ICostList;
import net.sf.anathema.character.main.magic.model.spells.CircleType;
import net.sf.anathema.character.main.magic.model.spells.Spell;
import net.sf.anathema.character.main.magic.parser.magic.IExaltedSourceBook;
import net.sf.anathema.character.main.traits.types.AbilityType;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.traits.TraitModelFetcher;
import net.sf.anathema.lib.exception.NotYetImplementedException;
import net.sf.anathema.lib.util.Identifier;

public class DummySpell implements Spell {

  public DummySpell() {
    // nothing to do
  }
  @Override
  public CircleType getCircleType() {
    return null;
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public MagicAttribute[] getAttributes() {
    return new MagicAttribute[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public IExaltedSourceBook[] getSources() {
    throw new NotYetImplementedException();
  }
  
  @Override
  public IExaltedSourceBook getPrimarySource() {
	throw new NotYetImplementedException();
  }

  @Override
  public ICostList getTemporaryCost() {
    throw new NotYetImplementedException();
  }

  @Override
  public boolean isFavored(Hero hero) {
    return TraitModelFetcher.fetch(hero).getTrait(AbilityType.Occult).isCasteOrFavored();
  }

  @Override
  public boolean hasAttribute(Identifier attribute) {
    return false;
  }

  @Override
  public String getAttributeValue(Identifier attribute) {
    return null;
  }

  @Override
  public String getTarget() {
    throw new NotYetImplementedException();
  }
}