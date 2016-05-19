package hydraTester;

import java.util.Random;

public class IfaGenerator
{
	private static Random rand = new Random();

	public static String getIfa()
	{
		int num = rand.nextInt(100000000);
				
		return "AA040DFE7" + String.valueOf(num) + "D268D727D522834E0BB41";
	}
}
