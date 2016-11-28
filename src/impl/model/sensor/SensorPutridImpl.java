package impl.model.sensor;

import contract.model.CaseMap;

public class SensorPutridImpl extends SensorImpl
{
	public SensorPutridImpl(CaseMap caseMap)
	{
		super(caseMap);
	}
	
	@Override
	public Object answer()
	{
		return caseMap.isPutrid();
	}
}
