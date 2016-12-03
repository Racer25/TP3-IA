package impl.model;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import contract.model.AdventureMap;
import contract.model.AdventureMapGenerator;

public class AdventureMapGeneratorImpl implements AdventureMapGenerator
{
	
	private AdventureMap adventureMap;
	private int taille;
	private int nbFall;
	private int nbMonstruous;
	private ArrayList<CaseImpl> casesList;
	
	//Les cases remarquables 
	private ArrayList<CaseImpl> fallPoints;
	private ArrayList<CaseImpl> monstruousPoints;
	private CaseImpl spawnPoint;

	public AdventureMapGeneratorImpl()
	{
		this.fallPoints = new ArrayList<CaseImpl>();
		this.monstruousPoints = new ArrayList<CaseImpl>();
	}
	
	public AdventureMapImpl createMap(int taille, int nbFall, int nbMonstruous)
	{
		//Initiation des variables
		this.taille = taille;
		this.nbFall = nbFall;
		this.nbMonstruous = nbMonstruous;
		//On verifie que la Map peut �tre generee
		if((taille*taille)-2 > nbFall+nbMonstruous)
		{
			//Creation de l'entite adventureMapImpl
			adventureMap = new AdventureMapImpl();
			adventureMap.setTaille(this.taille);
			//On creer son tableau de cases
			adventureMap.setCasesMap(new CaseImpl[taille][taille]);
			//Et on l'implemente
			for(int i = 0 ; i < taille ; i++)
			{
				for(int j = 0 ; j < taille ; j++)
				{
					adventureMap.getCasesMap()[i][j] = new CaseImpl();
					int[] coordonnees = new int[2];
					coordonnees[0]=i;
					coordonnees[1]=j;
					adventureMap.getCasesMap()[i][j].setCoordMap(coordonnees);
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
			
			//Test � supprimer
			printMap();
			
			//On verifie si la map est faisable
			ReinitializeCasesList();
			if(isFeasible(spawnPoint))
			{
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
			spawnPoint = (CaseImpl) adventureMap.getCasesMap()[i][j];
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
			fallPoints.add((CaseImpl) adventureMap.getCasesMap()[i][j]);
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
			monstruousPoints.add((CaseImpl) adventureMap.getCasesMap()[i][j]);
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
			int i = fallPoints.get(a).getCoordMap()[0];
			int j = fallPoints.get(a).getCoordMap()[1];
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
			int i = monstruousPoints.get(b).getCoordMap()[0];
			int j = monstruousPoints.get(b).getCoordMap()[1];
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
		casesList = new ArrayList<CaseImpl>();
	}
	
	public boolean isFeasible(CaseImpl c)
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
				CaseImpl up = new CaseImpl();
				CaseImpl down = new CaseImpl();
				CaseImpl left = new CaseImpl();
				CaseImpl right = new CaseImpl();
				if(c.getCoordMap()[0]>0)
				{
					up = (CaseImpl) adventureMap.getCasesMap()[c.getCoordMap()[0]-1][c.getCoordMap()[1]];
				}
				else
				{
					up.setFall(true);
				}
				if(c.getCoordMap()[0]< taille - 1)
				{
					down = (CaseImpl) adventureMap.getCasesMap()[c.getCoordMap()[0]+1][c.getCoordMap()[1]];
				}
				else
				{
					down.setFall(true);
				}
				if(c.getCoordMap()[1]< taille - 1)
				{
					right = (CaseImpl) adventureMap.getCasesMap()[c.getCoordMap()[0]][c.getCoordMap()[1]+1];
				}
				else
				{
					right.setFall(true);
				}
				if(c.getCoordMap()[1]>0)
				{
					left = (CaseImpl) adventureMap.getCasesMap()[c.getCoordMap()[0]][c.getCoordMap()[1]-1];
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
	
	public CaseImpl getSpawnPoint()
	{
		return spawnPoint;
	}

	public void setSpawnPoint(CaseImpl spawnPoint)
	{
		this.spawnPoint = spawnPoint;
	}
}
