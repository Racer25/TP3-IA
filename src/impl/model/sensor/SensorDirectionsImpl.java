package impl.model.sensor;

import contract.model.CaseMap;

public class SensorDirectionsImpl extends SensorImpl
{
	public SensorDirectionsImpl(CaseMap caseMap)
	{
		super(caseMap);
	}
	
	@Override
	public Object answer()
	{
		return caseMap.getPossibleDirections();
	}
}
