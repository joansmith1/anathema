package net.sf.anathema.charmtree.view;

import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.platform.tree.presenter.view.CascadeLoadedListener;
import net.sf.anathema.platform.tree.presenter.view.NodeProperties;
import net.sf.anathema.platform.tree.presenter.view.ToolTipProperties;

import javax.swing.JComponent;
import javax.swing.ListCellRenderer;
import java.awt.Dimension;

public interface ICascadeSelectionView {

  CharmTreeRenderer getCharmTreeRenderer();

  void addCascadeLoadedListener(CascadeLoadedListener listener);

  void addCharmTypeSelector(String title, Identifier[] types, ListCellRenderer renderer);

  void addCharmTypeSelectionListener(ObjectValueListener<Identifier> selectionListener);

  void addCharmFilterButton(SmartAction action, String titleText);

  void fillCharmGroupBox(Identifier[] charmGroups);

  void fillCharmTypeBox(Identifier[] cascadeTypes);

  void addCharmGroupSelector(String title, ListCellRenderer renderer, ICharmGroupChangeListener selectionListener, Dimension preferredSize);

  void addCharmCascadeHelp(String helpText);

  JComponent getCharmComponent();

  void initGui(ToolTipProperties treeProperties, NodeProperties properties);
}