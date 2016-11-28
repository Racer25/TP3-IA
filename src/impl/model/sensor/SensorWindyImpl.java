package impl.model.sensor;

import contract.model.CaseMap;
import contract.model.Sensor;

public class SensorWindyImpl implements Sensor
{
	private CaseMap caseMap;
	
	public SensorWindyImpl(CaseMap caseMap)
	{
		this.caseMap=caseMap;
	}
	
	@Override
	public Object answer()
	{
		return caseMap.isWindy();
	}
}
