package impl.model.sensor;

import contract.model.Sensor;
import impl.model.CharacterImpl;

public class SensorLightImpl implements Sensor
{
	private CharacterImpl character;
	
	public SensorLightImpl(CharacterImpl character)
	{
		this.character=character;
	}
	
	@Override
	public Object answer()
	{
		return character.getCurrentCase().isPortalPoint();
	}
}
