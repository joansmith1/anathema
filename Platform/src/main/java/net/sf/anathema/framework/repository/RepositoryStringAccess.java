package net.sf.anathema.framework.repository;

import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.repository.access.RepositoryReadAccess;
import net.sf.anathema.framework.repository.access.RepositoryWriteAccess;
import net.sf.anathema.lib.exception.PersistenceException;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RepositoryStringAccess {
  private final Repository repository;
  private final IItemType itemType;

  public RepositoryStringAccess(Repository repository, IItemType itemType) {
    this.repository = repository;
    this.itemType = itemType;
  }

  public void write(String id, String representation) {
    OutputStream stream = null;
    try {
      RepositoryWriteAccess writeAccess = repository.createWriteAccess(itemType, id);
      stream = writeAccess.createMainOutputStream();
      stream.write(representation.getBytes());
    } catch (IOException e) {
      throw new PersistenceException(e);
    } finally {
      IOUtils.closeQuietly(stream);
    }
  }

  public String read(String id) {
    InputStream stream = null;
    try {
      RepositoryReadAccess access = repository.openReadAccess(itemType, id);
      stream = access.openMainInputStream();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      IOUtils.copy(stream, outputStream);
      return new String(outputStream.toByteArray());
    } catch (IOException e) {
      throw new PersistenceException(e);
    } finally {
      IOUtils.closeQuietly(stream);
    }
  }
}