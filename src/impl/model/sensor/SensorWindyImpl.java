package impl.model.sensor;

import contract.model.CaseMap;

public class SensorWindyImpl extends SensorImpl
{
	public SensorWindyImpl(CaseMap caseMap)
	{
		super(caseMap);
	}
	
	@Override
	public Object answer()
	{
		return caseMap.isWindy();
	}
}
