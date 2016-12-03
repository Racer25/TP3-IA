package impl.model.sensor;

import contract.model.CaseMap;
import contract.model.Sensor;

public class SensorImpl implements Sensor
{
	protected CaseMap caseMap;
	
	public SensorImpl(CaseMap caseMap)
	{
		this.caseMap=caseMap;
	}
	
	@Override
	public Object answer()
	{
		return null;
	}

	public CaseMap getCaseMap()
	{
		return caseMap;
	}

	public void setCaseMap(CaseMap caseMap)
	{
		this.caseMap = caseMap;
	}
}
