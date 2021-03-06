package impl.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.JOptionPane;

import contract.model.AdventureMap;
import contract.model.AdventureMapGenerator;
import contract.model.CaseMap;
import utils.DirectionEnum;

public class AdventureMapGeneratorImpl extends Observable implements AdventureMapGenerator
{
	
	private AdventureMap adventureMap;
	private int taille;
	private int nbFall;
	private int nbMonstruous;
	private ArrayList<CaseMapImpl> casesList;
	
	//Les cases remarquables 
	private ArrayList<CaseMapImpl> fallPoints;
	private ArrayList<CaseMapImpl> monstruousPoints;
	private CaseMapImpl spawnPoint;

	public AdventureMapGeneratorImpl()
	{
		this.fallPoints = new ArrayList<CaseMapImpl>();
		this.monstruousPoints = new ArrayList<CaseMapImpl>();
	}
	
	public AdventureMapImpl createMap(int taille, int nbFall, int nbMonstruous)
	{
		//Initiation des variables
		this.taille = taille;
		this.nbFall = nbFall;
		fallPoints = new ArrayList<CaseMapImpl>();
		monstruousPoints = new ArrayList<CaseMapImpl>();
		this.nbMonstruous = nbMonstruous;
		//On verifie que la Map peut �tre generee
		if((taille*taille)-2 > nbFall+nbMonstruous)
		{
			//Creation de l'entite adventureMapImpl
			adventureMap = new AdventureMapImpl();
			adventureMap.setTaille(this.taille);
			//On creer son tableau de cases
			adventureMap.setCasesMap(new CaseMapImpl[taille][taille]);
			//Et on l'implemente
			for(int i = 0 ; i < taille ; i++)
			{
				for(int j = 0 ; j < taille ; j++)
				{
					adventureMap.getCasesMap()[i][j] = new CaseMapImpl();
					int[] coordonnees = new int[2];
					coordonnees[0]=i;
					coordonnees[1]=j;
					adventureMap.getCasesMap()[i][j].setCoords(coordonnees);
				}
			}
			//On definit les attributs independants
			SelectSpawnPoint();
			SelectPortalPoint();
			for(int a = 0 ; a < nbFall ; a++){
				SelectFallPoint();
			}
			for(int b = 0 ; b < nbMonstruous ; b++){
				SelectMonstruousPoint();
			}
			//On definit les attributs combinables 
			SetWindyCases();
			SetPutridCases();
			SetPossibleDirections();
			
			//Test � supprimer
			printMap();
			
			//On verifie si la map est faisable
			ReinitializeCasesList();
			if(isFeasible(spawnPoint))
			{
				notifyObservers(adventureMap);
				setChanged();
				return (AdventureMapImpl) adventureMap;
			}
			else
			{
				return createMap(this.taille,this.nbFall,this.nbMonstruous);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Le nombre de monstres/chutes est trop �lev� !","ATTENTION !",JOptionPane.WARNING_MESSAGE);
			return null;
		}
	
	}
	
	public void SelectSpawnPoint()
	{
		Random random = new Random();
		int i = random.nextInt(taille);
		int j = random.nextInt(taille);
		boolean fall = adventureMap.getCasesMap()[i][j].isFall();
		boolean portal = adventureMap.getCasesMap()[i][j].isPortalPoint();
		boolean monstruous = adventureMap.getCasesMap()[i][j].isMonstruous();
		if(fall == false && portal == false && monstruous == false)
		{
			adventureMap.getCasesMap()[i][j].setSpawnPoint(true);
			spawnPoint = (CaseMapImpl) adventureMap.getCasesMap()[i][j];
			adventureMap.setChangeReference(spawnPoint.getCoords());
		}
		else
		{
			SelectSpawnPoint();
		}
	}
	
	public void SelectPortalPoint()
	{
		Random random = new Random();
		int i = random.nextInt(taille);
		int j = random.nextInt(taille);
		boolean spawn = adventureMap.getCasesMap()[i][j].isSpawnPoint();
		boolean fall = adventureMap.getCasesMap()[i][j].isFall();
		boolean monstruous = adventureMap.getCasesMap()[i][j].isMonstruous();
		if(spawn == false && fall == false && monstruous == false)
		{
			adventureMap.getCasesMap()[i][j].setPortalPoint(true);
		}
		else
		{
			SelectPortalPoint();
		}
	}
	
	public void SelectFallPoint()
	{
		Random random = new Random();
		int i = random.nextInt(taille);
		int j = random.nextInt(taille);
		boolean spawn = adventureMap.getCasesMap()[i][j].isSpawnPoint();
		boolean portal = adventureMap.getCasesMap()[i][j].isPortalPoint();
		boolean fall = adventureMap.getCasesMap()[i][j].isFall();
		boolean monstruous = adventureMap.getCasesMap()[i][j].isMonstruous();
		if(spawn == false && portal == false && monstruous == false && fall == false)
		{
			adventureMap.getCasesMap()[i][j].setFall(true);
			fallPoints.add((CaseMapImpl) adventureMap.getCasesMap()[i][j]);
		}
		else
		{
			SelectFallPoint();
		}
	}
	
	public void SelectMonstruousPoint()
	{
		Random random = new Random();
		int i = random.nextInt(taille);
		int j = random.nextInt(taille);
		boolean spawn = adventureMap.getCasesMap()[i][j].isSpawnPoint();
		boolean fall = adventureMap.getCasesMap()[i][j].isFall();
		boolean portal = adventureMap.getCasesMap()[i][j].isPortalPoint();
		boolean monstruous = adventureMap.getCasesMap()[i][j].isMonstruous();
		if(spawn == false && fall == false && portal == false && monstruous == false)
		{
			adventureMap.getCasesMap()[i][j].setMonstruous(true);
			monstruousPoints.add((CaseMapImpl) adventureMap.getCasesMap()[i][j]);
		}
		else
		{
			SelectMonstruousPoint();
		}
	}
	
	public void SetWindyCases()
	{
		for(int a = 0 ; a < fallPoints.size() ; a++)
		{
			int i = fallPoints.get(a).getCoords()[0];
			int j = fallPoints.get(a).getCoords()[1];
			if(i > 0)
			{
				adventureMap.getCasesMap()[i - 1][j].setWindy(true);
			}
			if(i < taille - 1)
			{
				adventureMap.getCasesMap()[i + 1][j].setWindy(true);
			}
			if(j > 0)
			{
				adventureMap.getCasesMap()[i][j - 1].setWindy(true);
			}
			if(j < taille - 1)
			{
				adventureMap.getCasesMap()[i][j + 1].setWindy(true);
			}
		}
	}
	
	public void SetPutridCases()
	{
		for(int b = 0 ; b < monstruousPoints.size() ; b++)
		{
			int i = monstruousPoints.get(b).getCoords()[0];
			int j = monstruousPoints.get(b).getCoords()[1];
			if(i > 0)
			{
				adventureMap.getCasesMap()[i - 1][j].setPutrid(true);
			}
			if(i < taille - 1)
			{
				adventureMap.getCasesMap()[i + 1][j].setPutrid(true);
			}
			if(j > 0)
			{
				adventureMap.getCasesMap()[i][j - 1].setPutrid(true);
			}
			if(j < taille - 1)
			{
				adventureMap.getCasesMap()[i][j + 1].setPutrid(true);
			}
		}
	}
	

	
	public void SetPossibleDirections()
	{
		for(int i = 0 ; i < adventureMap.getTaille(); i++)
		{
			for(int j = 0 ; j < adventureMap.getTaille(); j++)
			{
				CaseMap maCase=this.adventureMap.getCasesMap()[i][j];
				if(i==0)
				{
					maCase.addPossibleDirection(DirectionEnum.UP, false);
				}
				else
				{
					maCase.addPossibleDirection(DirectionEnum.UP, true);
				}
				
				if(i==adventureMap.getTaille()-1)
				{
					maCase.addPossibleDirection(DirectionEnum.DOWN, false);
				}
				else
				{
					maCase.addPossibleDirection(DirectionEnum.DOWN, true);
				}
				if(j==0)
				{
					maCase.addPossibleDirection(DirectionEnum.LEFT, false);
				}
				else
				{
					maCase.addPossibleDirection(DirectionEnum.LEFT, true);
				}
				
				if(j==adventureMap.getTaille()-1)
				{
					maCase.addPossibleDirection(DirectionEnum.RIGHT, false);
				}
				else
				{
					maCase.addPossibleDirection(DirectionEnum.RIGHT, true);
				}
			}
		}
	}
	
	public void printMap()
	{
		for(int i = 0 ; i < taille ; i++)
		{
			for(int j = 0 ; j < taille ; j++)
			{
				boolean fall = adventureMap.getCasesMap()[i][j].isFall();
				String fa = "  ";
				if(fall == true)
				{
					fa = "fa";
				}
				boolean spawn = adventureMap.getCasesMap()[i][j].isSpawnPoint();
				String sp = "  ";
				if(spawn == true)
				{
					sp = "sp";
				}
				boolean portal = adventureMap.getCasesMap()[i][j].isPortalPoint();
				String po = "  ";
				if(portal == true)
				{
					po = "po";
				}
				boolean monstruous = adventureMap.getCasesMap()[i][j].isMonstruous();
				String mo = "  ";
				if(monstruous == true)
				{
					mo = "mo";
				}
				boolean windy = adventureMap.getCasesMap()[i][j].isWindy();
				String wi = "  ";
				if(windy == true)
				{
					wi = "wi";
				}
				boolean putrid = adventureMap.getCasesMap()[i][j].isPutrid();
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
	
	public void ReinitializeCasesList()
	{
		casesList = new ArrayList<CaseMapImpl>();
	}
	
	public boolean isFeasible(CaseMapImpl c)
	{
		if(!casesList.contains(c))
		{
			casesList.add(c);
			if(c.isPortalPoint())
			{
				return true;
			}
			else if(c.isFall())
			{
				return false;
			}
			else
			{
				CaseMapImpl up = new CaseMapImpl();
				CaseMapImpl down = new CaseMapImpl();
				CaseMapImpl left = new CaseMapImpl();
				CaseMapImpl right = new CaseMapImpl();
				if(c.getCoords()[0]>0)
				{
					up = (CaseMapImpl) adventureMap.getCasesMap()[c.getCoords()[0]-1][c.getCoords()[1]];
				}
				else
				{
					up.setFall(true);
				}
				if(c.getCoords()[0]< taille - 1)
				{
					down = (CaseMapImpl) adventureMap.getCasesMap()[c.getCoords()[0]+1][c.getCoords()[1]];
				}
				else
				{
					down.setFall(true);
				}
				if(c.getCoords()[1]< taille - 1)
				{
					right = (CaseMapImpl) adventureMap.getCasesMap()[c.getCoords()[0]][c.getCoords()[1]+1];
				}
				else
				{
					right.setFall(true);
				}
				if(c.getCoords()[1]>0)
				{
					left = (CaseMapImpl) adventureMap.getCasesMap()[c.getCoords()[0]][c.getCoords()[1]-1];
				}
				else
				{
					left.setFall(true);
				}
				return (isFeasible(up) || isFeasible(down) || isFeasible(right) || isFeasible(left));
			}
		}
		else
		{
			return false;
		}
	}
	
	public AdventureMap getAdventureMap()
	{
		return adventureMap;
	}

	public void setAdventureMap(AdventureMap adventureMap)
	{
		this.adventureMap = adventureMap;
	}
	
	public CaseMapImpl getSpawnPoint()
	{
		return spawnPoint;
	}

	public void setSpawnPoint(CaseMapImpl spawnPoint)
	{
		this.spawnPoint = spawnPoint;
	}
	
	public AdventureMap createTestMap()
	{
		//Initiation des variables
		this.taille = 4;
		this.nbFall = 2;
		fallPoints = new ArrayList<CaseMapImpl>();
		monstruousPoints = new ArrayList<CaseMapImpl>();
		this.nbMonstruous = 2;
		//On verifie que la Map peut �tre generee
		if((taille*taille)-2 > nbFall+nbMonstruous)
		{
			//Creation de l'entite adventureMapImpl
			this.adventureMap = new AdventureMapImpl();
			this.adventureMap.setTaille(this.taille);
			//On creer son tableau de cases
			this.adventureMap.setCasesMap(new CaseMapImpl[taille][taille]);
			//Et on l'implemente
			for(int i = 0 ; i < taille ; i++)
			{
				for(int j = 0 ; j < taille ; j++)
				{
					this.adventureMap.getCasesMap()[i][j] = new CaseMapImpl();
					int[] coordonnees = new int[2];
					coordonnees[0]=i;
					coordonnees[1]=j;
					adventureMap.getCasesMap()[i][j].setCoords(coordonnees);
				}
			}
			//On definit les attributs independants
			adventureMap.getCasesMap()[2][1].setSpawnPoint(true);
			spawnPoint=(CaseMapImpl)adventureMap.getCasesMap()[2][1];
			adventureMap.setChangeReference(spawnPoint.getCoords());
			adventureMap.getCasesMap()[0][1].setPortalPoint(true);
			adventureMap.getCasesMap()[3][3].setMonstruous(true);
			adventureMap.getCasesMap()[0][3].setMonstruous(true);
			adventureMap.getCasesMap()[0][2].setFall(true);
			adventureMap.getCasesMap()[0][0].setFall(true);
			//On definit les attributs combinables 
			fallPoints.add((CaseMapImpl) adventureMap.getCasesMap()[0][2]);
			fallPoints.add((CaseMapImpl) adventureMap.getCasesMap()[0][0]);
			monstruousPoints.add((CaseMapImpl) adventureMap.getCasesMap()[3][3]);
			monstruousPoints.add((CaseMapImpl) adventureMap.getCasesMap()[0][3]);
			
			SetWindyCases();
			SetPutridCases();
			SetPossibleDirections();
			
			//Test � supprimer
			printMap();
			
			return adventureMap;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Le nombre de monstres/chutes est trop �lev� !","ATTENTION !",JOptionPane.WARNING_MESSAGE);
			return null;
		}
	
	}
}
