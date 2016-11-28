package impl.model.sensor;

import contract.model.CaseMap;
import contract.model.Sensor;

public class SensorPutridImpl implements Sensor
{
	private CaseMap caseMap;
	
	public SensorPutridImpl(CaseMap caseMap)
	{
		this.caseMap=caseMap;
	}
	
	@Override
	public Object answer()
	{
		return caseMap.isPutrid();
	}
}
