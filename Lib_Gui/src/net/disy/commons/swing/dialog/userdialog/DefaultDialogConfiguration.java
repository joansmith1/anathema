/**
 * Copyright (C) 2005, 2011 disy Informationssysteme GmbH and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package net.disy.commons.swing.dialog.userdialog;

import net.disy.commons.core.model.BooleanModel;
import net.disy.commons.swing.dialog.core.DialogHeaderPanelConfiguration;
import net.disy.commons.swing.dialog.core.IDialogHeaderPanelConfiguration;
import net.disy.commons.swing.dialog.core.IDialogResult;
import net.disy.commons.swing.dialog.core.IVetoDialogCloseHandler;
import net.disy.commons.swing.dialog.core.internal.AbstractGenericDialogConfiguration;
import net.disy.commons.swing.dialog.core.preferences.IDialogPreferences;
import net.disy.commons.swing.dialog.input.IRequestFinishListener;
import net.disy.commons.swing.dialog.input.check.CheckBoxSmartDialogPanel;
import net.disy.commons.swing.dialog.setting.IDialogVisibilitySetting;
import net.disy.commons.swing.dialog.userdialog.buttons.IDialogButtonConfiguration;
import net.disy.commons.swing.dialog.userdialog.page.IDialogPage;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class DefaultDialogConfiguration<P extends IDialogPage>
    extends
    AbstractGenericDialogConfiguration implements IDialogConfiguration<P> {

  private final class VisibilityCheckboxModel extends BooleanModel {

    private boolean visible = dialogVisibilitySetting.isDialogVisible();

    @Override
    public void setValue(boolean selected) {
      visible = !selected;
    }

    @Override
    public boolean getValue() {
      final boolean value = !visible;
      return value;
    }

    public void updateSetting() {
      if (visible != dialogVisibilitySetting.isDialogVisible()) {
        dialogVisibilitySetting.setDialogVisible(visible);
      }
    }
  }

  private final P dialogPage;
  private final Dimension customizedPreferedSize;
  private final JComponent[] additionalButtons;
  private IVetoDialogCloseHandler vetoDialogCloseHandler;
  private IDialogVisibilitySetting dialogVisibilitySetting;
  private final boolean updateVisibilitySettingOnCancel;
  private VisibilityCheckboxModel visibilityCheckboxModel;

  /**
   * @deprecated as of 21.12.2010 (beck), use {@link DefaultDialogConfigurationBuilder} instead
   */
  @Deprecated
  public DefaultDialogConfiguration(
      final P dialogPage,
      final IDialogButtonConfiguration buttonConfiguration) {
    this(dialogPage, buttonConfiguration, null, null);
  }

  public DefaultDialogConfiguration(
      final P dialogPage,
      final IDialogButtonConfiguration buttonConfiguration,
      final IDialogVisibilitySetting dialogVisibilitySetting) {
    this(
        dialogPage,
        buttonConfiguration,
        DialogHeaderPanelConfiguration.createVisibleWithoutIcon(),
        null,
        null,
        null,
        dialogVisibilitySetting);
  }

  /**
   * @deprecated as of 21.12.2010 (beck), use {@link DefaultDialogConfigurationBuilder} instead
   */
  @Deprecated
  public DefaultDialogConfiguration(
      final P dialogPage,
      final IDialogButtonConfiguration buttonConfiguration,
      final Dimension customizedPreferedSize,
      final IDialogPreferences preferences) {
    this(
        dialogPage,
        buttonConfiguration,
        DialogHeaderPanelConfiguration.createVisibleWithoutIcon(),
        customizedPreferedSize,
        preferences,
        null);
  }

  public DefaultDialogConfiguration(
          final P dialogPage,
          final IDialogButtonConfiguration buttonConfiguration,
          IDialogHeaderPanelConfiguration headerPanelConfiguration,
          final Dimension customizedPreferedSize,
          final IDialogPreferences preferences,
          final JComponent[] additionalButtons) {
    this(
            dialogPage,
            buttonConfiguration,
            headerPanelConfiguration,
            customizedPreferedSize,
            preferences,
            additionalButtons,
            (IDialogVisibilitySetting) null);
  }

  /**
   * Use {@link DefaultDialogConfigurationBuilder} to create a configuration 
   */
  public DefaultDialogConfiguration(
      final P dialogPage,
      final IDialogButtonConfiguration buttonConfiguration,
      IDialogHeaderPanelConfiguration headerPanelConfiguration,
      final Dimension customizedPreferedSize,
      final IDialogPreferences preferences,
      final JComponent[] additionalButtons,
      final IVetoDialogCloseHandler vetoDialogCloseHandler,
      final IDialogVisibilitySetting dialogVisibilitySetting,
      boolean updateVisibilitySettingOnCancel) {
    super(buttonConfiguration, headerPanelConfiguration, preferences);
    this.dialogPage = dialogPage;
    this.customizedPreferedSize = customizedPreferedSize;
    this.additionalButtons = additionalButtons;
    this.dialogVisibilitySetting = dialogVisibilitySetting;
    this.updateVisibilitySettingOnCancel = updateVisibilitySettingOnCancel;
    this.vetoDialogCloseHandler = new IVetoDialogCloseHandler() {

      @Override
      public boolean handleDialogAboutToClose(IDialogResult result, Component parentComponent) {
        updateDialogVisibilitySetting(result);
        return vetoDialogCloseHandler.handleDialogAboutToClose(result, parentComponent);
      }
    };
  }

  public DefaultDialogConfiguration(
      final P dialogPage,
      final IDialogButtonConfiguration buttonConfiguration,
      IDialogHeaderPanelConfiguration headerPanelConfiguration,
      final Dimension customizedPreferedSize,
      final IDialogPreferences preferences,
      final JComponent[] additionalButtons,
      IDialogVisibilitySetting dialogVisibilitySetting) {
    super(buttonConfiguration, headerPanelConfiguration, preferences);
    this.dialogPage = dialogPage;
    this.customizedPreferedSize = customizedPreferedSize;
    this.additionalButtons = additionalButtons;
    this.dialogVisibilitySetting = dialogVisibilitySetting;
    this.updateVisibilitySettingOnCancel = false;
    this.vetoDialogCloseHandler = new IVetoDialogCloseHandler() {
      @Override
      public boolean handleDialogAboutToClose(
          final IDialogResult result,
          final Component parentComponent) {
        updateDialogVisibilitySetting(result);
        if (result.isCanceled()) {
          return performCancel(parentComponent);
        }
        return performOk(parentComponent);
      }
    };

  }

  @Override
  public P getDialogPage() {
    return dialogPage;
  }

  @Override
  public void setUserDialogContainer(final IUserDialogContainer dialogContainer) {
    dialogPage.addRequestFinishListener(new IRequestFinishListener() {
      @Override
      public void requestFinish() {
        dialogContainer.requestFinish();
      }
    });
  }

  @Override
  public JComponent[] createAdditionalButtons() {
    if (additionalButtons == null) {
      return new JComponent[0];
    }
    return additionalButtons;
  }

  @Override
  public JComponent createOptionalButtonPanelLeftComponent() {
    if (dialogVisibilitySetting == null) {
      return null;
    }
    JPanel panel = new JPanel(new FlowLayout());
    visibilityCheckboxModel = new VisibilityCheckboxModel();
    final CheckBoxSmartDialogPanel checkBox = new CheckBoxSmartDialogPanel(
        visibilityCheckboxModel,
        dialogVisibilitySetting.getMessageText());
    checkBox.fillInto(panel, 2);
    return panel;
  }

  private void updateDialogVisibilitySetting(IDialogResult result) {
    if ((!result.isCanceled() || updateVisibilitySettingOnCancel)
        && this.visibilityCheckboxModel != null) {
      this.visibilityCheckboxModel.updateSetting();
    }
  }

  @Override
  public IVetoDialogCloseHandler getVetoCloseHandler() {
    return vetoDialogCloseHandler;
  }

  @Deprecated
  public boolean performOk(final Component parentComponent) {
    return true;
  }

  @Deprecated
  public boolean performCancel(final Component parentComponent) {
    return true;
  }

  @Override
  public Dimension getCustomizedPreferedSize() {
    return customizedPreferedSize;
  }

  @Override
  public boolean isVisible() {
    return dialogVisibilitySetting == null || dialogVisibilitySetting.isDialogVisible();
  }
}