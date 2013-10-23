package net.sf.anathema.framework.repository.access.printname;

import com.google.gson.Gson;
import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.view.PrintNameFile;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JsonPrintNameFileReader implements PrintNameFileReader {

  private final Gson gson = new Gson();

  @Override
  public PrintNameFile readPrintName(File file, IItemType itemType) throws IOException {
    String content = IOUtils.toString(new FileInputStream(file));
    PrintNameDto printNameDto = gson.fromJson(content, PrintNameDto.class);
    return new PrintNameFile(file, printNameDto.printName, printNameDto.repositoryId, itemType);
  }
}