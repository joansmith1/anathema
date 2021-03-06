package net.sf.anathema.framework.repository.access;

import net.sf.anathema.framework.repository.RepositoryException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SingleFileWriteAccess implements RepositoryWriteAccess {

  private final File repositoryFile;

  public SingleFileWriteAccess(File repositoryfile) {
    this.repositoryFile = repositoryfile;
  }

  @Override
  public OutputStream createMainOutputStream() throws RepositoryException {
    try {
      return new FileOutputStream(repositoryFile);
    }
    catch (FileNotFoundException e) {
      throw new RepositoryException(e);
    }
  }

  @Override
  public OutputStream createSubOutputStream(String streamID) throws RepositoryException {
    throw new UnsupportedOperationException();
  }
}