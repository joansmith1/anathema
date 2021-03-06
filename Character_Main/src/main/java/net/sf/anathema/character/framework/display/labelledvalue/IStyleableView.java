package net.sf.anathema.character.framework.display.labelledvalue;

import net.sf.anathema.framework.ui.FontStyle;
import net.sf.anathema.framework.ui.RGBColor;

public interface IStyleableView {

  void setTextColor(RGBColor color);

  void setFontStyle(FontStyle style);
}