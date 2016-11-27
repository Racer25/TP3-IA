package impl.model.sensor;

import contract.model.Sensor;
import impl.model.CharacterImpl;

public class SensorWindyImpl implements Sensor
{
	private CharacterImpl character;
	
	public SensorWindyImpl(CharacterImpl character)
	{
		this.character=character;
	}
	
	@Override
	public Object answer()
	{
		return character.getCurrentCase().isWindy();
	}
}
