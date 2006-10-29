package net.sf.anathema.character.view;

import net.sf.anathema.character.library.intvalue.IIconToggleButtonProperties;
import net.sf.anathema.character.library.intvalue.IToggleButtonTraitView;
import net.sf.anathema.framework.presenter.view.IInitializableContentView;

public interface IGroupedFavorableTraitConfigurationView extends IInitializableContentView<Object> {

  public IToggleButtonTraitView< ? > addTraitView(
      String labelText,
      int value,
      int maxValue,
      boolean selected,
      IIconToggleButtonProperties properties);

  public IToggleButtonTraitView< ? > addMarkerLessTraitView(
      String labelText,
      int value,
      int maxValue,
      boolean selected,
      IIconToggleButtonProperties properties);

  public void startNewTraitGroup(String groupLabel);
}