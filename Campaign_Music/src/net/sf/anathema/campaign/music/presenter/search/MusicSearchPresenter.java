package net.sf.anathema.campaign.music.presenter.search;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.disy.commons.swing.action.SmartAction;
import net.sf.anathema.campaign.music.model.IMusicDatabase;
import net.sf.anathema.campaign.music.presenter.IMusicSearchControl;
import net.sf.anathema.campaign.music.presenter.ISearchParameter;
import net.sf.anathema.campaign.music.presenter.util.MusicCategorizationPresenter;
import net.sf.anathema.campaign.music.view.library.ILibraryControlView;
import net.sf.anathema.campaign.music.view.search.ISearchComponent;
import net.sf.anathema.lib.resources.IResources;

public class MusicSearchPresenter {

  private final ILibraryControlView controlView;
  private final IResources resources;
  private IMusicSearchControl searchControl;
  private Map<ISearchComponent, ISearchParameter> parametersByView = new HashMap<ISearchComponent, ISearchParameter>();

  public MusicSearchPresenter(ILibraryControlView controlView, IMusicDatabase dataBase, IResources resources) {
    this.controlView = controlView;
    this.resources = resources;
    this.searchControl = dataBase.getMusicSearchControl();
  }

  public void initPresentation() {
    ISearchParameter[] searchParameters = searchControl.getSearchParameters();
    for (ISearchParameter parameter : searchParameters) {
      ISearchComponent component = controlView.addSearchParameter(resources.getString(parameter.getDisplayKey()));
      parametersByView.put(component, parameter);
    }
    SmartAction searchAction = new SmartAction(resources.getString("Music.Buttons.Search.Text")) { //$NON-NLS-1$
      @Override
      protected void execute(Component parentComponent) {
        Map<ISearchParameter, String> constraintsByParamter = new HashMap<ISearchParameter, String>();
        for (ISearchComponent component : parametersByView.keySet()) {
          if (component.isSelected()) {
            ISearchParameter parameter = parametersByView.get(component);
            constraintsByParamter.put(parameter, component.getSearchString());
          }
        }
        searchControl.executeSearch(constraintsByParamter);
      }
    };
    controlView.setSearchAction(searchAction);
    initListening();
    new MusicCategorizationPresenter(
        searchControl.getMusicCategorizationModel(),
        controlView.getSearchMusicCategorizationView()).initPresentation();
  }

  private void initListening() {
    searchControl.addSearchResultChangedListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        controlView.getTrackListView().setListItems(searchControl.getSearchResult());
        controlView.getTrackListView().setListTitle(
            resources.getString("Music.Labels.LibraryTrackView.SearchResultsTitle")); //$NON-NLS-1$
      }
    });
  }
}
