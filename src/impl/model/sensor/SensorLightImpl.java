package impl.model.sensor;

import contract.model.CaseMap;
import contract.model.Sensor;

public class SensorLightImpl implements Sensor
{
	private CaseMap caseMap;
	
	public SensorLightImpl(CaseMap caseMap)
	{
		this.caseMap=caseMap;
	}
	
	@Override
	public Object answer()
	{
		return caseMap.isPortalPoint();
	}
}
