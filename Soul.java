//import block
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

//package import - not going to be used here

public class Soul{
	
	enum Ethic {EVIL, PRIDE, SLOTH, LUST, WRATH, GLUTTONY, ENVY, AVARICE, HUMILITY, DILLIGENCE, CHASTITY, PATIENCE, TEMPERANCE, KINDNESS, CHARITY, GOOD};
	enum Status {ARCHDEMON, DEMON, DAMNED, LIMBO, BLESSED, ANGEL, ARCHANGEL}; // Gotta admit, not in love with the term Archdemon.
	
	static int SCORE_CAP = 50;
	static int GENERIC_TOLERANCE = 5; 
	
	int[] classTiers = new int[]{0,15,35};
	int[] ethicScores;
	double average;
	Ethic DominantEthic;
	int DominantScore = 0;
	Status soulStatus;
	String Bio;
	
	SoulModel model = new SoulModel();
	int color; // might be able to get rid of this, since we're assigning it to the model
	int[] position = new int[]{0,0,0}; // Could possibly get away with 2d, but might as well be 3d
	
	public void UpdateStats(){
		int axis = -1;
		double sum = 0;
		for(int score=0;score<ethicScores.length;score++){
			sum += (double)ethicScores[score];
			if(Math.abs(ethicScores[score])>Math.abs(DominantScore)){
				axis = score;
				DominantScore = ethicScores[score];
			}
			average = sum/(double)ethicScores.length;
		}
		// now a series of splitters to determine which of the enumerated is the dominant. Check for generic GOOD/EVIL should come before others.
		if(Math.abs(DominantScore-average)< GENERIC_TOLERANCE && DominantScore < 0)
			DominantEthic = Ethic.EVIL;
		else if(Math.abs(DominantScore-average)< GENERIC_TOLERANCE && DominantScore > 0)
			DominantEthic = Ethic.GOOD;
		else if(axis==0 && DominantScore < 0)
			DominantEthic = Ethic.PRIDE;
		else if(axis==0 && DominantScore > 0)
			DominantEthic = Ethic.HUMILITY;
		else if(axis==1 && DominantScore < 0)
			DominantEthic = Ethic.SLOTH;
		else if(axis==1 && DominantScore > 0)
			DominantEthic = Ethic.DILLIGENCE;
		else if(axis==2 && DominantScore < 0)
			DominantEthic = Ethic.LUST;
		else if(axis==2 && DominantScore > 0)
			DominantEthic = Ethic.CHASTITY;
		else if(axis==3 && DominantScore < 0)
			DominantEthic = Ethic.WRATH;
		else if(axis==3 && DominantScore > 0)
			DominantEthic = Ethic.PATIENCE;
		else if(axis==4 && DominantScore < 0)
			DominantEthic = Ethic.GLUTTONY;
		else if(axis==4 && DominantScore > 0)
			DominantEthic = Ethic.TEMPERANCE;
		else if(axis==5 && DominantScore < 0)
			DominantEthic = Ethic.ENVY;
		else if(axis==5 && DominantScore > 0)
			DominantEthic = Ethic.KINDNESS;
		else if(axis==6 && DominantScore < 0)
			DominantEthic = Ethic.AVARICE;
		else if(axis==6 && DominantScore > 0)
			DominantEthic = Ethic.CHARITY;
		
		// Now a block for Status checking - consider changing it so that these cutoffs are public static values in an array.
		if(DominantScore<=-classTiers[2] && average<0)
			soulStatus = Status.ARCHDEMON;
		else if(DominantScore<=-classTiers[1] && average<0)
			soulStatus = Status.DEMON;
		else if(average<0)
			soulStatus = Status.DAMNED;
		else if(DominantScore>=classTiers[2] && average>0)
			soulStatus = Status.ARCHANGEL;
		else if(DominantScore>=classTiers[1] && average>0)
			soulStatus = Status.ANGEL;
		else if(average>0)
			soulStatus = Status.BLESSED;
		else
			soulStatus = Status.LIMBO;
	}
	
	public void WriteBio(){
		Bio = "";
		Bio += "Soul is" + ((soulStatus==Status.DAMNED || soulStatus==Status.BLESSED)?"":" a") + ((soulStatus==Status.ARCHANGEL || soulStatus==Status.ARCHDEMON || soulStatus==Status.ANGEL)?"n ":" ") + soulStatus + "\n";
		Bio += "Soul is " + ((average>0)?"rewarded ":"punished ") + (((DominantScore>0&&average>0)||(DominantScore<0&&average<0))?"for ":"in spite of ") + DominantEthic;
		//The above is obviously just some generic stuff. If we felt like writing more in depth Bios, the logic for the writer would be here.
		
	}
	
	public void UpdateAppearance(){
		// This is where things get a little weird. For the most part, this is color and sundries, but if ever we want to have visual cues on a soul for something else, this would be where it get's activated.
		//model = new SoulModel();
		int[] colorRGB = new int[]{127,127,127};//This is both GOOD and EVIL, Gray
		if((DominantScore>0&&average<0)||(DominantScore<0&&average>0))
			//do nothing - this is an 'in spite of' soul, which we're coloring as grey.
			colorRGB[0]=127;
		else if(DominantEthic==Ethic.PRIDE||DominantEthic==Ethic.HUMILITY){ // Purple, {64,0,128}; Turns out even amounts is more of a pink (IE magenta)
			colorRGB[0]=64;
			colorRGB[1] = 0;
		}
		else if(DominantEthic==Ethic.SLOTH||DominantEthic==Ethic.DILLIGENCE) // Cyan, {0,128,128};
			colorRGB[0] = 0;
		else if(DominantEthic==Ethic.LUST||DominantEthic==Ethic.CHASTITY){ // Blue, {0,0,128};
			colorRGB[0] = 0;
			colorRGB[1] = 0;
		}
		else if(DominantEthic==Ethic.WRATH||DominantEthic==Ethic.PATIENCE){ // Red, {128,0,0}
			colorRGB[1] = 0;
			colorRGB[2] = 0;
		}
		else if(DominantEthic==Ethic.GLUTTONY||DominantEthic==Ethic.TEMPERANCE){ // This is the weird one. Keep an eye on it. Orange, {128,64,0}
			colorRGB[1] = 64;
			colorRGB[2] = 0;
		}
		else if(DominantEthic==Ethic.ENVY||DominantEthic==Ethic.KINDNESS){ // Green, {0,128,0};
			colorRGB[0] = 0;
			colorRGB[2] = 0;
		}
		else if(DominantEthic==Ethic.AVARICE||DominantEthic==Ethic.CHARITY) // Yellow, {128,128,0);
			colorRGB[2]=0;
		//This is where the color is scaled somehow
		if(soulStatus==Status.ARCHANGEL){
			for(int channel = 0; channel<colorRGB.length;channel++)
				colorRGB[channel] *= 2;
		}
		else if(soulStatus==Status.ANGEL){
			for(int channel = 0; channel<colorRGB.length;channel++)
				colorRGB[channel] *= 1.5;
		}
		else if(soulStatus==Status.DEMON){
			for(int channel = 0; channel<colorRGB.length;channel++)
				colorRGB[channel] /= 1.5;
		}
		else if(soulStatus==Status.ARCHDEMON){
			for(int channel = 0; channel<colorRGB.length;channel++)
				colorRGB[channel] /= 2;
		}
		
		//This is where color is converted and assigned
		color = (colorRGB[0]<<16) + (colorRGB[1]<<8) + (colorRGB[2]);
		model.UpdateBaseColor(color);
		if(soulStatus==Status.ANGEL || soulStatus == Status.ARCHANGEL)
			model.AddHalo();
		if(soulStatus==Status.DEMON || soulStatus == Status.ARCHDEMON)
			model.AddHorns();
		//This is where Sundries are handled
	}
	
	public SoulModel GetModel(){
		return model;
	}
	
	Soul(int[] means, int[] deviances){
		ethicScores = new int[7];
		for(int score=0; score < ethicScores.length;score++){
			ethicScores[score] = (int)((ThreadLocalRandom.current().nextGaussian())*deviances[score]) + means[score];
			if(ethicScores[score]>SCORE_CAP)
				ethicScores[score]=SCORE_CAP;
		}
		UpdateStats();
		WriteBio();
		UpdateAppearance();
	}
}