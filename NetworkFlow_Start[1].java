package fordFulkerson;

public class NetworkFlow_Start {

	public static void main(String[] args) throws Exception
	{
		if((args[0]).equals("-f"))
			FordFulkerson.main(args);
		else if((args[0]).equals("-b"))
			GraphConstruct.main(args);
		else if((args[0]).equals("-i"))
			ImageSegmentation.main(args);
		else
			System.out.println("Invalid Arguments");
	}
}
