package impl.model.sensor;

import contract.model.CaseMap;
import contract.model.Sensor;

public class SensorDirectionsImpl implements Sensor
{
	private CaseMap caseMap;
	
	public SensorDirectionsImpl(CaseMap caseMap)
	{
		this.caseMap=caseMap;
	}
	
	@Override
	public Object answer()
	{
		return caseMap.getPossibleDirections();
	}

}
