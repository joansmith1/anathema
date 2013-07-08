package net.sf.anathema.hero.othertraits.display;

import javafx.scene.Node;
import net.miginfocom.layout.CC;
import net.sf.anathema.character.main.library.trait.Trait;
import net.sf.anathema.character.main.library.trait.view.fx.FxTraitView;
import net.sf.anathema.character.main.type.ICharacterType;
import net.sf.anathema.character.main.view.labelledvalue.IValueView;
import net.sf.anathema.framework.value.IIntValueView;
import net.sf.anathema.platform.fx.BorderedTitledPane;
import net.sf.anathema.platform.fx.NodeHolder;
import org.tbee.javafx.scene.layout.MigPane;

import static net.sf.anathema.lib.gui.layout.LayoutUtils.fillWithoutInsets;
import static net.sf.anathema.lib.gui.layout.LayoutUtils.withoutInsets;

public class FxOtherTraitsView implements AdvantageView, NodeHolder {

  private final MigPane virtuePanel = new MigPane(fillWithoutInsets().wrapAfter(2));
  private final MigPane willpowerPanel = new MigPane(fillWithoutInsets().wrapAfter(2));
  private final FxEssenceView essenceView = new FxEssenceView();
  private final MigPane content = new MigPane(withoutInsets().wrapAfter(2));

  @Override
  public void initGui(AdvantageViewProperties properties, ICharacterType characterType) {
    addTitledPanel(properties.getVirtueTitle(), content, virtuePanel, new CC().spanY(2).growY());
    addTitledPanel(properties.getWillpowerTitle(), content, willpowerPanel, new CC().pushX().alignY("top"));
    addTitledPanel(properties.getEssenceTitle(), content, essenceView.getNode(), new CC().pushX().alignY("top"));
  }

  @Override
  public IIntValueView addVirtue(String labelText, int value, int maxValue) {
    FxTraitView willpowerView = new FxTraitView(labelText, maxValue);
    willpowerView.addTo(virtuePanel);
    return willpowerView;
  }

  @Override
  public IIntValueView addWillpower(String labelText, int value, int maxValue) {
    FxTraitView willpowerView = new FxTraitView(labelText, maxValue);
    willpowerView.addTo(willpowerPanel);
    return willpowerView;
  }

  @Override
  public IIntValueView addEssenceView(String labelText, int value, int maxValue, Trait trait) {
    return essenceView.addEssenceView(labelText, maxValue);
  }

  @Override
  public IValueView<String> addPoolView(String labelText, String value) {
    return essenceView.addPoolView(labelText, value);
  }

  @Override
  public Node getNode() {
    return content;
  }

  private void addTitledPanel(String title, MigPane parent, Node content, CC constraints) {
    BorderedTitledPane pane = BorderedTitledPane.Create(title, content);
    parent.add(pane, constraints);
  }
}
