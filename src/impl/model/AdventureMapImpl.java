package impl.model;

import java.util.Observable;
import java.util.Observer;

import contract.model.AdventureMap;
import contract.model.CaseMap;

public class AdventureMapImpl implements AdventureMap
{
	private int[] changeReference;//pass from CoordCharacter to CoordMap
	private CaseMapImpl[][] cases;
	private int taille;

	public int[] getChangeReference()
	{
		return changeReference;
	}

	public void setChangeReference(int[] changeReference)
	{
		this.changeReference = changeReference;
	}

	@Override
	public CaseMap[][] getCasesMap()
	{
		return (CaseMap[][])this.cases;
	}

	@Override
	public void setCasesMap(CaseMap[][] cases)
	{
		this.cases=(CaseMapImpl[][]) cases;
	}

	@Override
	public int getTaille()
	{
		return this.taille;
	}

	@Override
	public void setTaille(int taille)
	{
		this.taille=taille;
	}
	
	public void printMap()
	{
		for(int i = 0 ; i < taille ; i++)
		{
			for(int j = 0 ; j < taille ; j++)
			{
				boolean fall = this.getCasesMap()[i][j].isFall();
				String fa = "  ";
				if(fall == true)
				{
					fa = "fa";
				}
				boolean spawn = this.getCasesMap()[i][j].isSpawnPoint();
				String sp = "  ";
				if(spawn == true)
				{
					sp = "sp";
				}
				boolean portal = this.getCasesMap()[i][j].isPortalPoint();
				String po = "  ";
				if(portal == true)
				{
					po = "po";
				}
				boolean monstruous = this.getCasesMap()[i][j].isMonstruous();
				String mo = "  ";
				if(monstruous == true)
				{
					mo = "mo";
				}
				boolean windy = this.getCasesMap()[i][j].isWindy();
				String wi = "  ";
				if(windy == true)
				{
					wi = "wi";
				}
				boolean putrid = this.getCasesMap()[i][j].isPutrid();
				String pu = "  ";
				if(putrid == true)
				{
					pu = "pu";
				}
				System.out.print("| "+fa+po+mo+sp+wi+pu+" |");
			}
			System.out.println();
		}
	}

}
