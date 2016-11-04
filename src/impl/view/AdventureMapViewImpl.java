package impl.view;

import java.util.List;

import contract.view.AdventureMapView;
import contract.view.CaseView;

public class AdventureMapViewImpl implements AdventureMapView
{
	private List<CaseView> casesView;

	public List<CaseView> getCasesView()
	{
		return casesView;
	}

	public void setCasesView(List<CaseView> casesView)
	{
		this.casesView = casesView;
	}
}
