package net.sf.anathema.character.platform.module;

import net.sf.anathema.character.CharacterPrintNameFileScanner;
import net.sf.anathema.character.generic.framework.resources.CharacterUI;
import net.sf.anathema.character.platform.module.repository.CharacterCreationTemplateFactory;
import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.presenter.view.AbstractItemTypeViewProperties;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.workflow.wizard.selection.DialogBasedTemplateFactory;

public class CharacterViewProperties extends AbstractItemTypeViewProperties {

  private final CharacterCreationTemplateFactory factory;

  public CharacterViewProperties(IItemType type, IResources resources, CharacterCreationTemplateFactory factory,
                                 CharacterPrintNameFileScanner scanner) {
    super(type, new CharacterUI(resources).getCharacterDescriptionTabIcon(), new CharacterTypeUi(resources, scanner));
    this.factory = factory;
  }

  @Override
  public DialogBasedTemplateFactory getNewItemWizardFactory() {
    return factory;
  }
}