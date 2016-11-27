package impl.model.sensor;

import contract.model.Sensor;
import impl.model.CharacterImpl;

public class SensorPutridImpl implements Sensor
{
	private CharacterImpl character;
	
	public SensorPutridImpl(CharacterImpl character)
	{
		this.character=character;
	}
	
	@Override
	public Object answer()
	{
		return character.getCurrentCase().isPutrid();
	}
}
