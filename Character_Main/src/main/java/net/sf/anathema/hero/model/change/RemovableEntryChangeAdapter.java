package net.sf.anathema.hero.model.change;

import net.sf.anathema.character.framework.library.removableentry.RemovableEntryListener;

public class RemovableEntryChangeAdapter<E> implements RemovableEntryListener<E> {
  private final ChangeAnnouncer announcer;

  public RemovableEntryChangeAdapter(ChangeAnnouncer announcer) {
    this.announcer = announcer;
  }

  @Override
  public void entryAdded(E entry) {
    announcer.announceChangeOf(ChangeFlavor.UNSPECIFIED);
  }

  @Override
  public void entryRemoved(E entry) {
    announcer.announceChangeOf(ChangeFlavor.UNSPECIFIED);
  }

  @Override
  public void entryAllowed(boolean complete) {
    // nothing to do
  }
}
