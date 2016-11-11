package impl.view;

import javax.swing.JPanel;

import contract.model.CaseMap;
import contract.view.CaseView;

public class CaseViewImpl extends JPanel implements CaseView
{
	private static final long serialVersionUID = -3853422303860566481L;
	
	//Independant attributes between them
	private boolean spawnPoint;
	private boolean portalPoint;
	private boolean monstruous;
	private boolean fall;
	
	//Combinable attributes
	private boolean putrid;
	private boolean windy;

	public CaseViewImpl(CaseMap caseMap)
	{
		//Creation with caseMap attributes
	}
}
