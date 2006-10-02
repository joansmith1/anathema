package net.sf.anathema.character.equipment.item;

import java.awt.Component;

import net.disy.commons.swing.action.SmartAction;
import net.sf.anathema.character.equipment.item.model.IEquipmentStatsCreationFactory;
import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateEditModel;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;
import net.sf.anathema.framework.presenter.resources.BasicUi;
import net.sf.anathema.lib.control.objectvalue.IObjectValueChangedListener;
import net.sf.anathema.lib.gui.selection.IObjectSelectionView;
import net.sf.anathema.lib.resources.IResources;

public final class AddNewStatsAction extends SmartAction {
  private final IEquipmentStatsCreationFactory statsFactory;
  private final IResources resources;
  private final IEquipmentTemplateEditModel editModel;
  private final IObjectSelectionView<IExaltedRuleSet> ruleSetView;

  public AddNewStatsAction(
      IResources resources,
      IEquipmentTemplateEditModel editModel,
      IObjectSelectionView<IExaltedRuleSet> ruleSetView,
      IEquipmentStatsCreationFactory statsFactory) {
    super(new BasicUi(resources).getAddIcon());
    this.resources = resources;
    this.editModel = editModel;
    this.ruleSetView = ruleSetView;
    this.statsFactory = statsFactory;
    ruleSetView.addObjectSelectionChangedListener(new IObjectValueChangedListener<IExaltedRuleSet>() {
      public void valueChanged(IExaltedRuleSet newValue) {
        updateEnabled(newValue);
      }
    });
    updateEnabled(ruleSetView.getSelectedObject());
    setToolTipText(resources.getString("Equipment.Creation.Stats.AddActionTooltip")); //$NON-NLS-1$
  }

  private void updateEnabled(IExaltedRuleSet selectedObject) {
    setEnabled(selectedObject != null);
  }

  @Override
  protected void execute(Component parentComponent) {
    IEquipmentStats equipmentStats = statsFactory.createNewStats(parentComponent, resources);
    if (equipmentStats == null) {
      return;
    }
    editModel.addStatistics(ruleSetView.getSelectedObject(), equipmentStats);
  }
}