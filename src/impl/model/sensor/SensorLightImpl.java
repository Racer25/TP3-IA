package impl.model.sensor;

import contract.model.CaseMap;

public class SensorLightImpl extends SensorImpl
{
	public SensorLightImpl(CaseMap caseMap)
	{
		super(caseMap);
	}
	
	@Override
	public Object answer()
	{
		return caseMap.isPortalPoint();
	}
}
